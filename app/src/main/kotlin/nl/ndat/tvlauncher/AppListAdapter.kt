package nl.ndat.tvlauncher

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nl.ndat.tvlauncher.databinding.ViewCardAppBinding

class AppListAdapter(
	private val context: Context,
	private val packageManager: PackageManager,
	private val activities: List<ResolveInfo>
) : RecyclerView.Adapter<AppListAdapter.ViewHolder>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val inflater = LayoutInflater.from(context)
		val appCard = ViewCardAppBinding.inflate(inflater)
		return ViewHolder(appCard)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val resolveInfo = activities[position]

		// Set info
		holder.banner.setImageDrawable(resolveInfo.activityInfo.loadBanner(packageManager))
		holder.label.text = resolveInfo.loadLabel(packageManager)

		// Set animation
		holder.container.setOnFocusChangeListener { _, hasFocus ->
			val scale = if (hasFocus) 1.1f else 1.0f

			holder.container.animate().apply {
				scaleX(scale)
				scaleY(scale)
				duration = 200
				withLayer()
			}.start()

			holder.label.isSelected = hasFocus
		}

		// Set click action
		holder.container.setOnClickListener {
			val appIntent = packageManager.getLaunchIntentForPackage(resolveInfo.activityInfo.packageName)
			context.startActivity(appIntent)
		}
	}

	override fun getItemCount(): Int = activities.size

	class ViewHolder(binding: ViewCardAppBinding) : RecyclerView.ViewHolder(binding.root) {
		val container = binding.container
		val banner = binding.banner
		val label = binding.label
	}
}
