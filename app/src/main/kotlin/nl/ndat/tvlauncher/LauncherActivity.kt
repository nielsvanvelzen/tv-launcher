package nl.ndat.tvlauncher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import nl.ndat.tvlauncher.data.repository.TileRepository
import nl.ndat.tvlauncher.ui.page.LauncherPage
import nl.ndat.tvlauncher.util.DefaultLauncherHelper
import org.koin.android.ext.android.inject

class LauncherActivity : ComponentActivity() {
	private val defaultLauncherHelper: DefaultLauncherHelper by inject()
	private val tileRepository: TileRepository by inject()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			LauncherPage()
		}

		validateDefaultLauncher()
		lifecycleScope.launchWhenResumed {
			with(tileRepository) {
				refreshAllApplications()
				refreshAllInputs()
			}
		}
	}

	private fun validateDefaultLauncher() {
		if (!defaultLauncherHelper.isDefaultLauncher() && defaultLauncherHelper.canRequestDefaultLauncher()) {
			@Suppress("DEPRECATION")
			startActivityForResult(defaultLauncherHelper.requestDefaultLauncherIntent(), 0)
		}
	}
}
