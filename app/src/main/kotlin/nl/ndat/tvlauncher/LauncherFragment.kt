package nl.ndat.tvlauncher

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.setMargins
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
		activities.sortedBy { it.activityInfo.loadLabel(packageManager).toString() }.forEach { resolveInfo ->
			val container = LinearLayout(requireContext()).apply {
				isFocusable = true
				orientation = LinearLayout.VERTICAL
				layoutParams = LinearLayout.LayoutParams(320, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
					setMargins(16)
				}
			}

			val card = CardView(requireContext()).apply {
				radius = 16f
//				cardElevation = 0f
				layoutParams = LinearLayout.LayoutParams(320, 180)

				container.addView(this)
			}

			val image = ImageView(requireContext()).apply {
				setImageDrawable(resolveInfo.activityInfo.loadBanner(packageManager))
				scaleType = ImageView.ScaleType.FIT_CENTER

				card.addView(this)
			}

			val label = TextView(requireContext()).apply {
				text = resolveInfo.loadLabel(packageManager)
				setPadding(16, 8, 16, 8)
				maxLines = 1
				ellipsize = TextUtils.TruncateAt.END

				container.addView(this)
			}

			container.setOnFocusChangeListener { _, hasFocus ->
				val scale = if (hasFocus) 1.1f else 1.0f

				container.animate().apply {
					scaleX(scale)
					scaleY(scale)
					duration = 200
					withLayer()
				}.start()
			}

			container.setOnClickListener {
				val intent = packageManager.getLaunchIntentForPackage(resolveInfo.activityInfo.packageName)
				requireContext().startActivity(intent)
			}

			binding.apps.addView(container)
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}
