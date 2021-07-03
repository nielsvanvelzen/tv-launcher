package nl.ndat.tvlauncher.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import nl.ndat.tvlauncher.data.entity.CollectionTile
import nl.ndat.tvlauncher.data.entity.RichCollectionTile
import nl.ndat.tvlauncher.data.entity.Tile

@Dao
interface CollectionDao {
	@Query("SELECT * FROM collection_tile, tile WHERE collectionType = :type AND tile.id = collection_tile.tileId ORDER BY `order` ASC")
	fun getCollection(type: CollectionTile.CollectionType): LiveData<List<RichCollectionTile>>

	@Query("SELECT MAX(`order`) FROM collection_tile WHERE collectionType = :type")
	suspend fun getMaxOrder(type: CollectionTile.CollectionType): Int?

	@Insert
	suspend fun insert(collectionTile: CollectionTile)

	suspend fun insertTile(type: CollectionTile.CollectionType, tile: Tile) {
		val order = (getMaxOrder(type) ?: 0) + 1
		insert(CollectionTile(tile.id, type, order))
	}
}
