package nl.ndat.tvlauncher.ui

import android.Manifest
import android.animation.ValueAnimator
import android.app.WallpaperManager
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import nl.ndat.tvlauncher.R
import nl.ndat.tvlauncher.data.repository.TileRepository
import nl.ndat.tvlauncher.databinding.FragmentLauncherBinding
import nl.ndat.tvlauncher.ui.adapter.TileListAdapter
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

		binding.settings.setOnFocusChangeListener { _, hasFocus ->
			val color = ContextCompat.getColor(requireContext(), if (hasFocus) R.color.lb_tv_white else R.color.lb_grey)
			val animator = ValueAnimator.ofArgb(binding.settings.imageTintList!!.defaultColor, color)
			animator.addUpdateListener {
				binding.settings.imageTintList = ColorStateList.valueOf(it.animatedValue as Int)
			}
			animator.duration = resources.getInteger(R.integer.button_animation_duration).toLong()
			animator.start()
		}

		binding.settings.setOnClickListener {
			startActivity(
				Intent(Settings.ACTION_SETTINGS),
				ActivityOptionsCompat.makeScaleUpAnimation(
					binding.settings,
					0,
					0,
					binding.settings.width,
					binding.settings.height
				).toBundle()
			)
		}

		val tileAdapter = TileListAdapter(requireContext())
		with(binding.tiles) {
			adapter = tileAdapter
			requestFocus()
		}

		lifecycleScope.launch {
			tileRepository.getAllApps().observe(viewLifecycleOwner) { tiles ->
				tileAdapter.items = tiles
			}
		}

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}
