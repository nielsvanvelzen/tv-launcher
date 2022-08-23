package nl.ndat.tvlauncher.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import nl.ndat.tvlauncher.data.entity.Input

@Dao
interface InputDao {
	@Query("SELECT * FROM input")
	fun getAll(): Flow<List<Input>>

	@Query("SELECT * FROM input WHERE id = :id LIMIT 1")
	suspend fun getById(id: String): Input?

	@Insert
	suspend fun insert(vararg inputs: Input)

	@Update
	suspend fun update(vararg inputs: Input)

	@Query("DELETE FROM input WHERE packageName = :packageName")
	suspend fun removeByPackageName(packageName: String)

	@Query("DELETE FROM input WHERE id NOT IN (:ids)")
	suspend fun removeNotIn(ids: List<String>)
}
