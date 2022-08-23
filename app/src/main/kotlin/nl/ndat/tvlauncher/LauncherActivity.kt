package nl.ndat.tvlauncher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import nl.ndat.tvlauncher.data.repository.AppRepository
import nl.ndat.tvlauncher.data.repository.InputRepository
import nl.ndat.tvlauncher.ui.page.LauncherPage
import nl.ndat.tvlauncher.util.DefaultLauncherHelper
import org.koin.android.ext.android.inject

class LauncherActivity : ComponentActivity() {
	private val defaultLauncherHelper: DefaultLauncherHelper by inject()
	private val appRepository: AppRepository by inject()
	private val inputRepository: InputRepository by inject()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			LauncherPage()
		}

		validateDefaultLauncher()

		lifecycleScope.launchWhenResumed {
			appRepository.refreshAllApplications()
			inputRepository.refreshAllInputs()
		}
	}

	private fun validateDefaultLauncher() {
		if (!defaultLauncherHelper.isDefaultLauncher() && defaultLauncherHelper.canRequestDefaultLauncher()) {
			@Suppress("DEPRECATION")
			startActivityForResult(defaultLauncherHelper.requestDefaultLauncherIntent(), 0)
		}
	}
}
