package nl.ndat.tvlauncher.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.runBlocking
import nl.ndat.tvlauncher.data.repository.AppRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PackageChangeReceiver : BroadcastReceiver(), KoinComponent {
	private val appRepository: AppRepository by inject()

	override fun onReceive(context: Context, intent: Intent) {
		val packageName =
			if (intent.action in packageActions && intent.data?.scheme == "package") intent.data?.schemeSpecificPart
			else null

		runBlocking {
			if (packageName != null) appRepository.refreshApplication(packageName)
			else appRepository.refreshAllApplications()
		}
	}

	companion object {
		private val packageActions = arrayOf(
			Intent.ACTION_PACKAGE_ADDED,
			Intent.ACTION_PACKAGE_CHANGED,
			Intent.ACTION_PACKAGE_FULLY_REMOVED,
			Intent.ACTION_PACKAGE_REMOVED
		)
	}
}
