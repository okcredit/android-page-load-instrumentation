package tech.okcredit.page_load

import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.*
import java.lang.ref.WeakReference

class PageLoadTracker {

    private var firstDrawListener: FirstDrawListener? = null

    private fun registerFirstDrawListener(
        view: View,
        firstDrawCallback: FirstDrawListener.OnFirstDrawCallback,
    ): FirstDrawListener {
        return FirstDrawListener(view, firstDrawCallback)
    }

    suspend fun onFirstDrawListener(view: WeakReference<View>, onFirstDraw: () -> Unit) =
        withContext(Dispatchers.Default) {
            suspendCancellableCoroutine<Unit> {
                it.invokeOnCancellation {
                    firstDrawListener = null
                }
                if (view.get() == null) {
                    it.cancel()
                    return@suspendCancellableCoroutine
                }
                firstDrawListener =
                    registerFirstDrawListener(
                        view.get()!!,
                        object : FirstDrawListener.OnFirstDrawCallback {
                            override fun onDrawingFinish() {
                                onFirstDraw.invoke()
                                it.cancel()
                            }
                        }
                    )
            }

        }
}


fun LifecycleCoroutineScope.firstDrawListener(
    view: View,
    onFirstDraw: () -> Unit
) {
    this.launch {
        PageLoadTracker().onFirstDrawListener(WeakReference(view)) {
            onFirstDraw.invoke()
        }
    }
}