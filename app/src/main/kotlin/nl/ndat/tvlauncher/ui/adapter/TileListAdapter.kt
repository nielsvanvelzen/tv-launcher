package nl.ndat.tvlauncher.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import nl.ndat.tvlauncher.R
import nl.ndat.tvlauncher.data.entity.Tile
import nl.ndat.tvlauncher.databinding.ViewCardAppBinding
import nl.ndat.tvlauncher.util.createDrawable
import nl.ndat.tvlauncher.util.getIntent

class TileListAdapter(
	private val context: Context,
) : ListAdapter<Tile, TileListAdapter.ViewHolder>() {
	override fun areItemsTheSame(old: Tile, new: Tile): Boolean = old.id == new.id

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val inflater = LayoutInflater.from(context)
		val appCard = ViewCardAppBinding.inflate(inflater)
		return ViewHolder(appCard)
	}

	override fun onBindViewHolder(holder: ViewHolder, tile: Tile) {
		// Set info
		holder.banner.setImageDrawable(tile.createDrawable(holder.banner.context))
		holder.name.text = tile.name

		// Set animation
		holder.container.setOnFocusChangeListener { _, hasFocus ->
			val scale = if (hasFocus) ResourcesCompat.getFloat(context.resources, R.dimen.app_scale_focused)
			else ResourcesCompat.getFloat(context.resources, R.dimen.app_scale_default)

			holder.container.animate().apply {
				scaleX(scale)
				scaleY(scale)
				duration = context.resources.getInteger(R.integer.app_animation_duration).toLong()
				withLayer()
			}.start()

			holder.name.isSelected = hasFocus
		}

		// Set click action
		holder.container.setOnClickListener {
			if (tile.uri != null) context.startActivity(
				tile.getIntent(),
				ActivityOptionsCompat.makeScaleUpAnimation(
					holder.banner,
					0,
					0,
					holder.banner.width,
					holder.banner.height
				).toBundle()
			)
		}
	}

	class ViewHolder(binding: ViewCardAppBinding) : RecyclerView.ViewHolder(binding.root) {
		val container = binding.container
		val banner = binding.banner
		val name = binding.name
	}
}
