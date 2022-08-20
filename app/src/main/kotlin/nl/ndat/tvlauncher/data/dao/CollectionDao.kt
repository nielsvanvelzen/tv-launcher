package nl.ndat.tvlauncher.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import nl.ndat.tvlauncher.data.entity.CollectionTile
import nl.ndat.tvlauncher.data.entity.RichCollectionTile
import nl.ndat.tvlauncher.data.entity.Tile

@Dao
interface CollectionDao {
	@Query("SELECT * FROM collection_tile, tile WHERE collectionType = :type AND tile.id = collection_tile.tileId ORDER BY `order` ASC")
	fun getByType(type: CollectionTile.CollectionType): Flow<List<RichCollectionTile>>

	@Query("SELECT * FROM collection_tile, tile WHERE collectionType = :type AND tileId = :tileId AND tile.id = collection_tile.tileId LIMIT 1")
	suspend fun getByTileId(type: CollectionTile.CollectionType, tileId: String): RichCollectionTile?

	@Query("SELECT * FROM collection_tile, tile WHERE collectionType = :type AND `order` = :order AND tile.id = collection_tile.tileId LIMIT 1")
	suspend fun getByOrder(type: CollectionTile.CollectionType, order: Int): RichCollectionTile?

	@Insert
	suspend fun insert(collectionTile: CollectionTile)

	@Update
	suspend fun update(vararg tiles: CollectionTile)

	@Query("SELECT MAX(`order`) FROM collection_tile WHERE collectionType = :type")
	suspend fun getMaxOrder(type: CollectionTile.CollectionType): Int?

	suspend fun insertTile(type: CollectionTile.CollectionType, tile: Tile) {
		val order = (getMaxOrder(type) ?: 0) + 1
		insert(CollectionTile(tile.id, type, order))
	}

	suspend fun setOrder(type: CollectionTile.CollectionType, tile: Tile, movement: Int) {
		val collectionTile = getByTileId(type, tile.id)?.collectionTile ?: return
		val maxPosition = getMaxOrder(type) ?: 1
		val newPosition = (collectionTile.order + movement).coerceIn(1, maxPosition)
		val currentAtNewPosition = getByOrder(type, newPosition)

		// Swap positions
		if (currentAtNewPosition != null) update(currentAtNewPosition.collectionTile.copy(order = collectionTile.order))

		update(collectionTile.copy(order = newPosition))
	}
}
