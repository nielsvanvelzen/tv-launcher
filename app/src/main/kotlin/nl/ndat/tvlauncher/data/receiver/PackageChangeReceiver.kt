package nl.ndat.tvlauncher.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.runBlocking
import nl.ndat.tvlauncher.data.repository.TileRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PackageChangeReceiver : BroadcastReceiver(), KoinComponent {
	private val tileRepository: TileRepository by inject()

	override fun onReceive(context: Context, intent: Intent) {
		val packageId =
			if (intent.action in packageActions && intent.data?.scheme == "package") intent.data?.schemeSpecificPart
			else null

		runBlocking {
			if (packageId != null) tileRepository.refreshApplication(packageId)
			else tileRepository.refreshAllApplications()
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
