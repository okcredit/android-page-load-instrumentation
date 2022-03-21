package tech.okcredit.page_load

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver

class FirstDrawListener(
    private val view: View,
    private val firstDrawCallback: OnFirstDrawCallback
) : ViewTreeObserver.OnDrawListener {
    private val mainHandler: Handler = Handler(Looper.getMainLooper())
    private var onDrawInvoked = false

    init {
        registerFirstDrawListener()
    }

    interface OnFirstDrawCallback {
        fun onDrawingFinish()
    }

    private fun registerFirstDrawListener() {
        if (view.viewTreeObserver.isAlive && view.isAttachedToWindow) {
            view.viewTreeObserver.addOnDrawListener(this@FirstDrawListener)
        } else {
            view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View) {
                    if (view.viewTreeObserver.isAlive) {
                        view.viewTreeObserver.addOnDrawListener(this@FirstDrawListener)
                    }

                    view.removeOnAttachStateChangeListener(this)
                }

                override fun onViewDetachedFromWindow(v: View) {
                }
            })
        }
    }

    override fun onDraw() {
        if (!onDrawInvoked) {
            onDrawInvoked = true

            mainHandler.postAtFrontOfQueue { firstDrawCallback.onDrawingFinish() }

            mainHandler.post {
                if (view.viewTreeObserver.isAlive) {
                    view.viewTreeObserver.removeOnDrawListener(this@FirstDrawListener)
                }
            }
        }
    }
}
