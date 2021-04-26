package nl.ndat.tvlauncher

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import nl.ndat.tvlauncher.databinding.FragmentLauncherBinding
import nl.ndat.tvlauncher.databinding.ViewCardAppBinding

class LauncherFragment : Fragment() {
	private var _binding: FragmentLauncherBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentLauncherBinding.inflate(inflater, container, false)
		addEventListeners()
		addApps()
		return binding.root
	}

	private fun addEventListeners() {
		binding.button.setOnClickListener {
			startActivity(Intent(Settings.ACTION_SETTINGS))
		}
	}

	private fun addApps() {
		val intent = Intent(Intent.ACTION_MAIN, null).apply {
			addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER)
		}

		val packageManager = requireContext().packageManager
		val activities = packageManager.queryIntentActivities(intent, 0)
		activities.sortedBy { it.activityInfo.loadLabel(packageManager).toString() }.forEach { resolveInfo ->
			val appCard = ViewCardAppBinding.inflate(layoutInflater)
			appCard.banner.setImageDrawable(resolveInfo.activityInfo.loadBanner(packageManager))
			appCard.label.text = resolveInfo.loadLabel(packageManager)

			appCard.container.setOnFocusChangeListener { _, hasFocus ->
				val scale = if (hasFocus) 1.1f else 1.0f

				appCard.container.animate().apply {
					scaleX(scale)
					scaleY(scale)
					duration = 200
					withLayer()
				}.start()

				appCard.label.isSelected = hasFocus
			}

			appCard.container.setOnClickListener {
				val appIntent = packageManager.getLaunchIntentForPackage(resolveInfo.activityInfo.packageName)
				requireContext().startActivity(appIntent)
			}

			binding.apps.addView(appCard.root)
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}
