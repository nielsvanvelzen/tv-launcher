package nl.ndat.tvlauncher.ui.adapter

import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import nl.ndat.tvlauncher.data.model.ToolbarItem
import nl.ndat.tvlauncher.ui.toolbar.ToolbarClockView
import nl.ndat.tvlauncher.ui.toolbar.ToolbarNetworkStateView
import nl.ndat.tvlauncher.ui.toolbar.ToolbarSettingsView

class ToolbarAdapter : ListAdapter<ToolbarItem, ToolbarAdapter.ViewHolder>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val container = FrameLayout(parent.context, null).apply {
			layoutParams = ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT
			)
			foregroundGravity = Gravity.CENTER_VERTICAL
		}
		return ViewHolder(container)
	}

	override fun onBindViewHolder(holder: ViewHolder, item: ToolbarItem) {
		val view = when (item) {
			is ToolbarItem.Clock -> ToolbarClockView(holder.container.context)
			is ToolbarItem.Settings -> ToolbarSettingsView(holder.container.context)
			is ToolbarItem.Network -> ToolbarNetworkStateView(holder.container.context)
		}

		// Keep existing view if the type didn't change
		val currentView = holder.container.children.firstOrNull()
		if (currentView != null && currentView::class != view::class) {
			holder.container.removeViewAt(0)
		}

		holder.container.addView(view)
	}

	class ViewHolder(val container: FrameLayout) : RecyclerView.ViewHolder(container)
}
