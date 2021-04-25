package nl.ndat.tvlauncher

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import nl.ndat.tvlauncher.databinding.FragmentLauncherBinding

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
		activities.forEach { resolveInfo ->
			val card = CardView(requireContext())
			val image = ImageView(requireContext()).apply {
				setImageDrawable(resolveInfo.activityInfo.loadBanner(packageManager))
				scaleType = ImageView.ScaleType.FIT_CENTER
			}
			card.addView(image)

			card.isFocusable = true
			card.radius = 32f
			card.cardElevation = 1f
			card.contentDescription =
				"${resolveInfo.loadLabel(packageManager)} (${resolveInfo.activityInfo.packageName})"
			card.layoutParams = LinearLayout.LayoutParams(320, 180).apply {
				setMargins(32)
			}
			card.setPadding(0)
			card.setOnFocusChangeListener { _, hasFocus ->
				val scale = if (hasFocus) 1.1f else 1.0f
				card.animate().scaleX(scale).scaleY(scale).setDuration(200).start()
			}
			card.setOnClickListener {
				val intent = packageManager.getLaunchIntentForPackage(resolveInfo.activityInfo.packageName)
				requireContext().startActivity(intent)
			}
			binding.apps.addView(card)
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}
