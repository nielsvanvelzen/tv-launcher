package nl.ndat.tvlauncher

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import nl.ndat.tvlauncher.databinding.ActivityLauncherBinding
import nl.ndat.tvlauncher.ui.LauncherFragment
import nl.ndat.tvlauncher.util.DefaultLauncherHelper

class LauncherActivity : FragmentActivity() {
	private lateinit var binding: ActivityLauncherBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityLauncherBinding.inflate(layoutInflater)
		setContentView(binding.root)

		supportFragmentManager.commit {
			replace<LauncherFragment>(R.id.content)
		}

		validateDefaultLauncher()
	}

	private fun validateDefaultLauncher() {
		val defaultLauncherHelper = DefaultLauncherHelper(applicationContext)
		if (!defaultLauncherHelper.isDefaultLauncher() && defaultLauncherHelper.canRequestDefaultLauncher()) {
			@Suppress("DEPRECATION")
			startActivityForResult(defaultLauncherHelper.requestDefaultLauncherIntent(), 0)
		}
	}
}
