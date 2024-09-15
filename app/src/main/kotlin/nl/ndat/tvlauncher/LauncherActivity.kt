package nl.ndat.tvlauncher

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.SystemClock
import android.view.KeyEvent
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

	@SuppressLint("RestrictedApi")
	override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
		// Map "menu" key to a long press on the dpad because the compose TV library doesn't do that yet
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			event.startTracking()
			val longPressEvent = KeyEvent(
				SystemClock.uptimeMillis(),
				SystemClock.uptimeMillis(),
				KeyEvent.ACTION_DOWN,
				KeyEvent.KEYCODE_DPAD_CENTER,
				1
			)
			return dispatchKeyEvent(longPressEvent)
		}

		return super.onKeyDown(keyCode, event)
	}
}
