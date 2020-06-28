package eu.acolombo.roxiemon.util

import androidx.recyclerview.widget.RecyclerView


/**
 * Add an action which will be invoked when the recycler is scrolled.
 *
 * @return the [RecyclerView.OnScrollListener] added to the RecyclerView
 */
inline fun RecyclerView.doOnScrolled(
    crossinline action: (
        recyclerView: RecyclerView?, dx: Int, dy: Int
    ) -> Unit
) = addScrollListener(onScrolled = action)

/**
 * Add an action which will be invoked when the scroll state changes.
 *
 * @return the [RecyclerView.OnScrollListener] added to the RecyclerView
 */
inline fun RecyclerView.doOnScrollStateChanged(
    crossinline action: (
        recyclerView: RecyclerView?, newState: Int
    ) -> Unit
) = addScrollListener(onScrollStateChanged = action)

/**
 * Add a scroll listener to this RecyclerView using the provided actions
 *
 * @return the [RecyclerView.OnScrollListener] added to the RecyclerView
 */
inline fun RecyclerView.addScrollListener(
    crossinline onScrolled: (
        recyclerView: RecyclerView?, dx: Int, dy: Int
    ) -> Unit = { _, _, _ -> },
    crossinline onScrollStateChanged: (
        recyclerView: RecyclerView?, newState: Int
    ) -> Unit = { _, _ -> }
): RecyclerView.OnScrollListener {
    val textWatcher = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            onScrolled(recyclerView, dx, dy)
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            onScrollStateChanged(recyclerView, newState)
        }
    }
    addOnScrollListener(textWatcher)

    return textWatcher
}