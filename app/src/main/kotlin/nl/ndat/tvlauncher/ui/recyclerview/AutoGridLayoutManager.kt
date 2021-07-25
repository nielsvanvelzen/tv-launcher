package nl.ndat.tvlauncher.ui.recyclerview

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import kotlin.math.max

class AutoGridLayoutManager(
	context: Context,
	var columnWidth: Int,
) : GridLayoutManager(context, 1) {
	private var lastColumnWidth = 0
	private var lastWidth = 0
	private var lastHeight = 0

	override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
		val safeValues = columnWidth > 0 && width > 0 && height > 0
		val valuesChanged = columnWidth != lastColumnWidth || width != lastWidth || height != lastHeight

		if (safeValues && valuesChanged) {
			val totalSpace = if (orientation == VERTICAL) width - paddingRight - paddingLeft
			else height - paddingTop - paddingBottom

			spanCount = max(1, totalSpace / columnWidth)

			lastColumnWidth = columnWidth
			lastWidth = width
			lastHeight = height
		}

		super.onLayoutChildren(recycler, state)
	}
}
