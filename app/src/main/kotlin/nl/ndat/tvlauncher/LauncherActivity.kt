package nl.ndat.tvlauncher

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import nl.ndat.tvlauncher.databinding.ActivityLauncherBinding
import nl.ndat.tvlauncher.ui.LauncherFragment
import nl.ndat.tvlauncher.util.DefaultLauncherHelper
import org.koin.android.ext.android.inject

class LauncherActivity : FragmentActivity() {
	private val defaultLauncherHelper: DefaultLauncherHelper by inject()
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
		if (!defaultLauncherHelper.isDefaultLauncher() && defaultLauncherHelper.canRequestDefaultLauncher()) {
			@Suppress("DEPRECATION")
			startActivityForResult(defaultLauncherHelper.requestDefaultLauncherIntent(), 0)
		}
	}
}
