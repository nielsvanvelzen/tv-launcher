package nl.ndat.tvlauncher.data.repository

import android.content.Context
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.ndat.tvlauncher.data.DatabaseContainer
import nl.ndat.tvlauncher.data.executeAsListFlow
import nl.ndat.tvlauncher.data.resolver.AppResolver
import nl.ndat.tvlauncher.data.sqldelight.App

class AppRepository(
	private val context: Context,
	private val appResolver: AppResolver,
	private val database: DatabaseContainer,
) {
	private suspend fun commitApps(apps: Collection<App>) = withContext(Dispatchers.IO) {
		database.transaction {
			// Remove apps found in database but not in committed list
			database.apps.getAll()
				.executeAsList()
				.map { it.id }
				.subtract(apps.map { it.id }.toSet())
				.map { id -> database.apps.removeById(id) }

			// Upsert all found
			apps.map { app -> commitApp(app) }
		}
	}

	private suspend fun commitApp(app: App) = withContext(Dispatchers.IO) {
		database.apps.upsert(
			displayName = app.displayName,
			packageName = app.packageName,
			launchIntentUriDefault = app.launchIntentUriDefault,
			launchIntentUriLeanback = app.launchIntentUriLeanback,
			id = app.id
		)
	}

	suspend fun refreshAllApplications() = withContext(Dispatchers.IO) {
		val apps = appResolver.getApplications(context)
		commitApps(apps)
	}

	suspend fun refreshApplication(packageName: String) = withContext(Dispatchers.IO) {
		val app = appResolver.getApplication(context, packageName)

		if (app == null) database.apps.removeByPackageName(packageName)
		else commitApp(app)
	}

	fun getApps() = database.apps.getAll().executeAsListFlow()
	fun getFavoriteApps() = database.apps.getAllFavorites(::App).executeAsListFlow()
	suspend fun getByPackageName(packageName: String) = withContext(Dispatchers.IO) { database.apps.getByPackageName(packageName).awaitAsOneOrNull() }

	suspend fun favorite(id: String) = withContext(Dispatchers.IO) { database.apps.updateFavoriteAdd(id) }
	suspend fun unfavorite(id: String) = withContext(Dispatchers.IO) { database.apps.updateFavoriteRemove(id) }
	suspend fun updateFavoriteOrder(id: String, order: Int) = withContext(Dispatchers.IO) { database.apps.updateFavoriteOrder(id, order.toLong()) }
}
