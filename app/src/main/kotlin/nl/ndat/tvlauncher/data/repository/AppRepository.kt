package nl.ndat.tvlauncher.data.repository

import android.content.Context
import nl.ndat.tvlauncher.data.DatabaseContainer
import nl.ndat.tvlauncher.data.executeAsListFlow
import nl.ndat.tvlauncher.data.resolver.AppResolver
import nl.ndat.tvlauncher.data.sqldelight.App

class AppRepository(
	private val context: Context,
	private val appResolver: AppResolver,
	private val database: DatabaseContainer,
) {
	private suspend fun commitApps(apps: Collection<App>) = database.transaction {
		// Remove missing apps from database
		val currentIds = apps.map { it.id }
		database.apps.removeNotIn(currentIds)

		// Upsert apps
		apps.map { app -> commitApp(app) }
	}

	private fun commitApp(app: App) {
		database.apps.upsert(
			displayName = app.displayName,
			packageName = app.packageName,
			launchIntentUriDefault = app.launchIntentUriDefault,
			launchIntentUriLeanback = app.launchIntentUriLeanback,
			id = app.id
		)
	}

	suspend fun refreshAllApplications() {
		val apps = appResolver.getApplications(context)
		commitApps(apps)
	}

	suspend fun refreshApplication(packageName: String) {
		val app = appResolver.getApplication(context, packageName)

		if (app == null) database.apps.removeByPackageName(packageName)
		else commitApp(app)
	}

	fun getApps() = database.apps.getAll().executeAsListFlow()
	fun getFavoriteApps() = database.apps.getAllFavorites(::App).executeAsListFlow()
	suspend fun getByPackageName(packageName: String) = database.apps.getByPackageName(packageName).executeAsOneOrNull()

	fun favorite(id: String) = database.apps.updateFavoriteAdd(id)
	fun unfavorite(id: String) = database.apps.updateFavoriteRemove(id)
	fun updateFavoriteOrder(id: String, order: Int) = database.apps.updateFavoriteOrder(id, order.toLong())
}
