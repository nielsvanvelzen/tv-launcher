package nl.ndat.tvlauncher.ui.recyclerview

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import kotlin.math.max

class AutoGridLayoutManager(
	context: Context,
	private var columnWidth: Int,
) : GridLayoutManager(context, 1) {
	private var lastColumnWidth = 0
	private var lastWidth = 0
	private var lastHeight = 0

	override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
		val safeValues = columnWidth > 0 && width > 0 && height > 0
		val valuesChanged = columnWidth != lastColumnWidth || width != lastWidth || height != lastHeight

		if (safeValues && valuesChanged) {
			val totalSpace = width - paddingRight - paddingLeft

			spanCount = max(1, totalSpace / columnWidth)

			lastColumnWidth = columnWidth
			lastWidth = width
			lastHeight = height
		}

		super.onLayoutChildren(recycler, state)
	}

	override fun calculateExtraLayoutSpace(state: RecyclerView.State, extraLayoutSpace: IntArray) {
		// Use the size of the first child
		// assuming all items have equals sizes
		val firstChild = getChildAt(0) ?: return super.calculateExtraLayoutSpace(state, extraLayoutSpace)


		// Request 1 additional item to enable scrolling
		extraLayoutSpace[0] = firstChild.height // Start spacing (top/left)
		extraLayoutSpace[1] = firstChild.height // End spacing (bottom/right)
	}

	private fun View.getRecyclerview(): RecyclerView {
		var view = parent
		while (view !is RecyclerView) view = view.parent
		return view
	}

	override fun onInterceptFocusSearch(focused: View, direction: Int): View? {
		val recyclerView = focused.getRecyclerview()
		val currentPosition = recyclerView.getChildLayoutPosition(focused)

		val nextPosition = when (direction) {
			// UP - DOWN
			View.FOCUS_UP -> currentPosition - spanCount
			View.FOCUS_DOWN -> currentPosition + spanCount
			// NEXT - PREVIOUS
			View.FOCUS_FORWARD -> currentPosition + 1
			View.FOCUS_BACKWARD -> currentPosition - 1
			// LEFT - RIGHT
			View.FOCUS_LEFT -> if (currentPosition.floorDiv(spanCount) == (currentPosition - 1).floorDiv(spanCount)) currentPosition - 1 else currentPosition
			View.FOCUS_RIGHT -> if (currentPosition.floorDiv(spanCount) == (currentPosition + 1).floorDiv(spanCount)) currentPosition + 1 else currentPosition

			// Unknown direction
			else -> currentPosition
		}

		// Scroll to new position if it exists
		if (nextPosition != currentPosition && nextPosition in 0..itemCount) {
			val nextView = findViewByPosition(nextPosition)
			return nextView ?: focused
		}

		return super.onInterceptFocusSearch(focused, direction)
	}
}
