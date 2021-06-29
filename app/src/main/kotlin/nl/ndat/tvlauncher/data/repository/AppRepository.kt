package nl.ndat.tvlauncher.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import nl.ndat.tvlauncher.data.model.AppInfo
import nl.ndat.tvlauncher.data.service.ApplicationResolverService

class AppRepository(
	private val context: Context,
	private val applicationResolver: ApplicationResolverService,
) {

	fun getAllApps(): LiveData<List<AppInfo>> = liveData {
		val apps = applicationResolver.getApps(context)
		val inputs = applicationResolver.getInputs(context)

		emit(apps + inputs)
	}
}
