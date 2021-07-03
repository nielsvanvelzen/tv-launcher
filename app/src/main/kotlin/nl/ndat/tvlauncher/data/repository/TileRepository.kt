package nl.ndat.tvlauncher.data.repository

import android.content.Context
import nl.ndat.tvlauncher.data.TileResolver
import nl.ndat.tvlauncher.data.dao.TileDao
import nl.ndat.tvlauncher.data.entity.Tile

class TileRepository(
	private val context: Context,
	private val tileResolver: TileResolver,
	private val tileDao: TileDao,
) {
	private suspend fun commitTiles(type: Tile.TileType, tiles: Array<Tile>) {
		// Remove missing tiles from database
		val currentIds = tiles.map { it.id }
		tileDao.removeNotIn(type, currentIds)

		// Upsert tiles
		tiles.map { tile -> commitTile(tile) }
	}

	private suspend fun commitTile(tile: Tile) {
		val current = tileDao.getById(tile.id)
		if (current != null) tileDao.update(tile)
		else tileDao.insert(tile)
	}

	suspend fun refreshAllInputs() {
		val tiles = tileResolver.getInputs(context)
		commitTiles(Tile.TileType.INPUT, tiles.toTypedArray())
	}

	suspend fun refreshAllApplications() {
		val tiles = tileResolver.getApplications(context)
		commitTiles(Tile.TileType.APPLICATION, tiles.toTypedArray())
	}

	suspend fun refreshApplication(packageId: String) {
		val tile = tileResolver.getApplication(context, packageId)

		if (tile == null) tileDao.removeByPackageId(packageId)
		else commitTile(tile)
	}

	fun getAllApps() = tileDao.getAll()
}
