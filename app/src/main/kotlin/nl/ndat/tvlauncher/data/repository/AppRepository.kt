package nl.ndat.tvlauncher.data.repository

import android.content.Context
import nl.ndat.tvlauncher.data.dao.AppDao
import nl.ndat.tvlauncher.data.entity.App
import nl.ndat.tvlauncher.data.resolver.AppResolver

class AppRepository(
	private val context: Context,
	private val appResolver: AppResolver,
	private val appDao: AppDao,
) {
	private suspend fun commitApps(apps: Collection<App>) {
		// Remove missing apps from database
		val currentIds = apps.map { it.id }
		appDao.removeNotIn(currentIds)

		// Upsert apps
		apps.map { app -> commitApp(app) }
	}

	private suspend fun commitApp(app: App) {
		val current = appDao.getById(app.id)

		if (current != null) appDao.update(app)
		else appDao.insert(app)
	}

	suspend fun refreshAllApplications() {
		val apps = appResolver.getApplications(context)
		commitApps(apps)
	}

	suspend fun refreshApplication(packageName: String) {
		val app = appResolver.getApplication(context, packageName)

		if (app == null) appDao.removeByPackageName(packageName)
		else commitApp(app)
	}

	fun getApps() = appDao.getAll()
}
