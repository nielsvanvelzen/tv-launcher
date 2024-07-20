package nl.ndat.tvlauncher.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import nl.ndat.tvlauncher.data.repository.AppRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PackageChangeReceiver : BroadcastReceiver(), KoinComponent {
	companion object {
		private val packageActions = arrayOf(
			Intent.ACTION_PACKAGE_ADDED,
			Intent.ACTION_PACKAGE_CHANGED,
			Intent.ACTION_PACKAGE_REPLACED,
			Intent.ACTION_PACKAGE_REMOVED,
		)
	}

	private val appRepository: AppRepository by inject()

	override fun onReceive(context: Context, intent: Intent) {
		val pendingIntent = goAsync()

		@OptIn(DelicateCoroutinesApi::class)
		GlobalScope.launch {
			try {
				val packageName = when {
					intent.action in packageActions && intent.data?.scheme == "package" -> intent.data?.schemeSpecificPart
					else -> null
				}

				if (packageName != null) appRepository.refreshApplication(packageName)
				else appRepository.refreshAllApplications()
			} finally {
				pendingIntent.finish()
			}
		}
	}
}
