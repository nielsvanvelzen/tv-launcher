package nl.ndat.tvlauncher.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import nl.ndat.tvlauncher.data.dao.TileDao
import nl.ndat.tvlauncher.data.entity.Tile
import nl.ndat.tvlauncher.data.service.TileResolver

class TileRepository(
	private val context: Context,
	private val tileResolver: TileResolver,
	private val tileDao: TileDao,
) {
	private suspend fun refreshInputs() {
		// FIXME: should use upsert
		val tiles = tileResolver.getInputs(context)
		tileDao.insert(*tiles.toTypedArray())
	}

	suspend fun refreshAllApplications() {
		// FIXME: should use upsert
		val tiles = tileResolver.getApplications(context)
		tileDao.insert(*tiles.toTypedArray())
	}

	suspend fun refreshApplication(packageId: String) {
		// FIXME: should use upsert
		val tile = tileResolver.getApplication(context, packageId)

		if (tile == null) tileDao.removeByPackageId(packageId)
		else tileDao.insert(tile)
	}

	suspend fun getAllApps(): LiveData<List<Tile>> {
		refreshInputs()
		refreshAllApplications()

		return tileDao.getAll()
	}
}
