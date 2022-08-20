package nl.ndat.tvlauncher.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import nl.ndat.tvlauncher.data.entity.Tile

@Dao
interface TileDao {
	@Query("SELECT * FROM tile")
	fun getAll(): Flow<List<Tile>>

	@Query("SELECT * FROM tile WHERE id = :id LIMIT 1")
	suspend fun getById(id: String): Tile?

	@Insert
	suspend fun insert(vararg tiles: Tile)

	@Update
	suspend fun update(vararg tiles: Tile)

	@Query("DELETE FROM tile WHERE packageId = :packageId")
	suspend fun removeByPackageId(packageId: String)

	@Query("DELETE FROM tile WHERE type = :type AND id NOT IN (:ids)")
	suspend fun removeNotIn(type: Tile.TileType, ids: List<String>)
}
