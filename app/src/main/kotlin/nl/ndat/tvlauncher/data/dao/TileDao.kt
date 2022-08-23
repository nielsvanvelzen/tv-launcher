package nl.ndat.tvlauncher.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import nl.ndat.tvlauncher.data.entity.App

@Dao
interface AppDao {
	@Query("SELECT * FROM app")
	fun getAll(): Flow<List<App>>

	@Query("SELECT * FROM app WHERE id = :id LIMIT 1")
	suspend fun getById(id: String): App?

	@Insert
	suspend fun insert(vararg inputs: App)

	@Update
	suspend fun update(vararg inputs: App)

	@Query("DELETE FROM app WHERE packageName = :packageName")
	suspend fun removeByPackageName(packageName: String)

	@Query("DELETE FROM app WHERE id NOT IN (:ids)")
	suspend fun removeNotIn(ids: List<String>)
}
