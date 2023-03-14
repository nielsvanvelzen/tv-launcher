package nl.ndat.tvlauncher

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.tvprovider.media.tv.TvContractCompat
import kotlinx.coroutines.launch
import nl.ndat.tvlauncher.data.repository.AppRepository
import nl.ndat.tvlauncher.data.repository.ChannelRepository
import nl.ndat.tvlauncher.data.repository.InputRepository
import nl.ndat.tvlauncher.ui.AppBase
import nl.ndat.tvlauncher.util.DefaultLauncherHelper
import org.koin.android.ext.android.inject

class LauncherActivity : ComponentActivity() {
	private val defaultLauncherHelper: DefaultLauncherHelper by inject()
	private val appRepository: AppRepository by inject()
	private val inputRepository: InputRepository by inject()
	private val channelRepository: ChannelRepository by inject()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			AppBase()
		}

		validateDefaultLauncher()

		lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.RESUMED) {
				appRepository.refreshAllApplications()
				inputRepository.refreshAllInputs()
				channelRepository.refreshAllChannels()
			}
		}

		if (checkCallingOrSelfPermission(TvContractCompat.PERMISSION_READ_TV_LISTINGS) != PackageManager.PERMISSION_GRANTED)
			ActivityCompat.requestPermissions(this, arrayOf(TvContractCompat.PERMISSION_READ_TV_LISTINGS), 0)
	}

	private fun validateDefaultLauncher() {
		if (!defaultLauncherHelper.isDefaultLauncher() && defaultLauncherHelper.canRequestDefaultLauncher()) {
			val intent = defaultLauncherHelper.requestDefaultLauncherIntent()
			@Suppress("DEPRECATION")
			if (intent != null) startActivityForResult(intent, 0)
		}
	}
}
