package tech.okcredit.page_load

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.OnAttachStateChangeListener
import android.view.ViewTreeObserver.OnDrawListener
import java.lang.Exception


internal class NextDrawListener(
    private val view: View,
    private val onDrawCallback: () -> Unit
) : OnDrawListener, OnAttachStateChangeListener {
    private val mainHandler by lazy { Handler(Looper.getMainLooper()) }

    private var onDrawInvoked = false


    override fun onDraw() {
        if (onDrawInvoked) return
        onDrawInvoked = true
        view.removeOnAttachStateChangeListener(this)
        mainHandler.post {
            view.viewTreeObserver.let { viewTreeObserver ->
                if (viewTreeObserver.isAlive) {
                    viewTreeObserver.removeOnDrawListener(this)
                }
            }
        }
        onDrawCallback()
    }

    fun safelyRegisterForNextDraw(): NextDrawListener {
        /***  AddOnDrawListener can't be called inside onDraw. Due to some raise conditions,
         * there is a crash for 0.005% of the time. So we are doing a post call for fixing this.*/
        mainHandler.post {
            try {
                if (Build.VERSION.SDK_INT >= 26 || (view.viewTreeObserver.isAlive && view.isAttachedToWindowCompat)) {
                    view.viewTreeObserver.addOnDrawListener(this)
                } else {
                    view.addOnAttachStateChangeListener(this)
                }
            } catch (e: Exception) {
            }
        }
        return this
    }

    override fun onViewAttachedToWindow(view: View) {
        view.viewTreeObserver.addOnDrawListener(this)
        view.removeOnAttachStateChangeListener(this)
    }

    override fun onViewDetachedFromWindow(view: View) {
        view.viewTreeObserver.removeOnDrawListener(this)
        view.removeOnAttachStateChangeListener(this)
    }

    companion object {
        fun View.onNextDraw(onDrawCallback: () -> Unit): NextDrawListener {
            val nextDrawListener = NextDrawListener(this, onDrawCallback)
            return nextDrawListener.safelyRegisterForNextDraw()
        }
    }
}

private val View.isAttachedToWindowCompat: Boolean
    get() {
        return isAttachedToWindow
    }