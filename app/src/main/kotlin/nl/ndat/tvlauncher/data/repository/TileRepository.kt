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
	private suspend fun refreshTiles() {
		// FIXME: should use upsert
		val apps = tileResolver.getApplications(context)
		tileDao.insert(*apps.toTypedArray())

		val inputs = tileResolver.getInputs(context)
		tileDao.insert(*inputs.toTypedArray())
	}

	suspend fun getAllApps(): LiveData<List<Tile>> {
		refreshTiles()

		return tileDao.getAll()
	}
}
