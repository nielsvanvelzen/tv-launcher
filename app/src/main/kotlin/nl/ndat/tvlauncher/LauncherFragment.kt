package nl.ndat.tvlauncher

import android.animation.ValueAnimator
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
		binding.button.setOnFocusChangeListener { _, hasFocus ->
			val color = ContextCompat.getColor(requireContext(), if (hasFocus) R.color.lb_tv_white else R.color.lb_grey)
			val animator = ValueAnimator.ofArgb(binding.button.imageTintList!!.defaultColor, color)
			animator.addUpdateListener {
				binding.button.imageTintList = ColorStateList.valueOf(it.animatedValue as Int)
			}
			animator.duration = 200
			animator.start()
		}

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
		activities.sortBy { it.activityInfo.loadLabel(packageManager).toString() }

		binding.apps.adapter = AppListAdapter(requireContext(), packageManager, activities)
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}

