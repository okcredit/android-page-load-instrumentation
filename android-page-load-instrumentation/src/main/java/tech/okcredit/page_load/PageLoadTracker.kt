package tech.okcredit.page_load

import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.*
import tech.okcredit.page_load.NextDrawListener.Companion.onNextDraw
import java.lang.ref.WeakReference

class PageLoadTracker {

    private var nextDrawListener: NextDrawListener? = null

    suspend fun onFirstDrawListener(view: WeakReference<View>, onFirstDraw: () -> Unit) =
        withContext(Dispatchers.Default) {
            suspendCancellableCoroutine<Unit> {
                it.invokeOnCancellation {
                    nextDrawListener = null
                }
                if (view.get() == null) {
                    it.cancel()
                    return@suspendCancellableCoroutine
                }
                nextDrawListener = view.get()!!.onNextDraw {
                    onFirstDraw.invoke()
                }
            }

        }
}

/**
 * Used for tracking next drawing of the view.
 * @param onFirstDraw invokes on next onDraw of the view.
 * to cancel listening on view destruction, it needs [lifecycleCoroutineScope]
 **/
fun View.firstDrawListener(
    lifecycleCoroutineScope: LifecycleCoroutineScope,
    onFirstDraw: () -> Unit
) {
    val view = this
    lifecycleCoroutineScope.launch {
        PageLoadTracker().onFirstDrawListener(WeakReference(view)) {
            onFirstDraw.invoke()
        }
    }
}