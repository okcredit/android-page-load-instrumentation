package tech.okcredit.page_load

import android.view.View
import java.util.concurrent.Executors

class PageLoadTracker constructor(
    private val view: View
) {

    private var firstDrawListener: FirstDrawListener? = null
    private var onStartInvoked = false

    private fun registerFirstDrawListener(
        view: View,
        firstDrawCallback: FirstDrawListener.OnFirstDrawCallback,
    ): FirstDrawListener {
        return FirstDrawListener(view, firstDrawCallback)
    }

    fun onPageLoadListener(onFirstDraw: () -> Unit) {
        Executors.newSingleThreadExecutor().execute {

            firstDrawListener =
                registerFirstDrawListener(
                    view,
                    object : FirstDrawListener.OnFirstDrawCallback {
                        override fun onDrawingFinish() {
                            onFirstDraw.invoke()
                            destroy()
                        }
                    }
                )
            onStartInvoked = true
        }
    }

    fun destroy() {
        firstDrawListener = null
    }
}
