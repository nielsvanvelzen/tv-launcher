package nl.ndat.tvlauncher.ui

import android.Manifest
import android.app.WallpaperManager
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import nl.ndat.tvlauncher.R
import nl.ndat.tvlauncher.data.entity.CollectionTile
import nl.ndat.tvlauncher.data.entity.Tile
import nl.ndat.tvlauncher.data.model.ToolbarItem
import nl.ndat.tvlauncher.data.repository.TileRepository
import nl.ndat.tvlauncher.databinding.FragmentLauncherBinding
import nl.ndat.tvlauncher.ui.adapter.TileListAdapter
import nl.ndat.tvlauncher.ui.adapter.ToolbarAdapter
import nl.ndat.tvlauncher.util.getIntent
import org.koin.android.ext.android.inject

class LauncherFragment : Fragment() {
	private var _binding: FragmentLauncherBinding? = null
	private val binding get() = _binding!!

	private val backgroundContract = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
		binding.container.background = WallpaperManager.getInstance(requireContext()).drawable
	}

	private val tileRepository: TileRepository by inject()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentLauncherBinding.inflate(inflater, container, false)
		backgroundContract.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

		val toolbarAdapter = ToolbarAdapter().apply {
			items = listOf(ToolbarItem.Settings, ToolbarItem.Clock)
		}
		binding.toolbar.apply {
			addItemDecoration(SpacingItemDecoration(12, 0))
			adapter = toolbarAdapter
		}

		val tileAdapter = TileListAdapter(requireContext()).apply {
			onActivate = { tile: Tile, view: View ->
				if (tile.uri != null) startActivity(
					tile.getIntent(),
					ActivityOptionsCompat.makeScaleUpAnimation(
						view,
						0,
						0,
						view.width,
						view.height
					).toBundle()
				)
			}

			onMenu = { tile: Tile, view: View ->
				// FIXME: Add more fancy menu design
				PopupMenu(requireContext(), view, Gravity.BOTTOM).apply {
					menu.add(0, 2, 2, R.string.move_left)
					menu.add(0, 3, 3, R.string.move_right)
					setOnMenuItemClickListener { item ->
						lifecycleScope.launch {
							when (item.itemId) {
								2 -> tileRepository.moveCollectionTile(CollectionTile.CollectionType.HOME, tile, -1)
								3 -> tileRepository.moveCollectionTile(CollectionTile.CollectionType.HOME, tile, 1)
							}
						}

						true
					}
				}.show()
			}
		}

		tileRepository.getHomeTiles().observe(viewLifecycleOwner) { tiles ->
			tileAdapter.items = tiles
		}

		binding.tiles.adapter = tileAdapter
		binding.tiles.requestFocus()

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}
