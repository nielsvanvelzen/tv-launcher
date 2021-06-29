package nl.ndat.tvlauncher.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nl.ndat.tvlauncher.data.entity.Tile

@Dao
interface TileDao {
	@Query("SELECT * FROM tile")
	fun getAll(): LiveData<List<Tile>>

	@Query("SELECT * FROM tile WHERE id = :id LIMIT 1")
	fun getById(id: Int): LiveData<Tile?>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(vararg tiles: Tile)
}
