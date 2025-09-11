package nl.ndat.tvlauncher.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import nl.ndat.tvlauncher.data.repository.AppRepository
import nl.ndat.tvlauncher.service.AutoStartService
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
	private val autoStartService: AutoStartService by inject()

	override fun onReceive(context: Context, intent: Intent) {
		val pendingIntent = goAsync()

		@OptIn(DelicateCoroutinesApi::class)
		GlobalScope.launch {
			try {
				when (intent.action) {
					Intent.ACTION_BOOT_COMPLETED -> {
						// 系统启动完成，启动自启动应用
						autoStartService.startAutoStartApps(context)
					}
					in packageActions -> {
						val packageName = if (intent.data?.scheme == "package") {
							intent.data?.schemeSpecificPart
						} else null

						if (packageName != null) appRepository.refreshApplication(packageName)
						else appRepository.refreshAllApplications()
					}
				}
			} finally {
				pendingIntent.finish()
			}
		}
	}
}
