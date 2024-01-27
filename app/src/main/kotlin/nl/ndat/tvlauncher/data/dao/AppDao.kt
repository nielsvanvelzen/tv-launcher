package nl.ndat.tvlauncher.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import nl.ndat.tvlauncher.data.entity.App
import nl.ndat.tvlauncher.data.entity.AppSystemDetails
import nl.ndat.tvlauncher.data.entity.AppFavorite

@Dao
interface AppDao {
	@Query("SELECT * FROM app")
	fun getAll(): Flow<List<App>>

	@Query("SELECT * FROM app WHERE id = :id LIMIT 1")
	suspend fun getById(id: String): App?

	@Query("SELECT * FROM app WHERE packageName = :packageName LIMIT 1")
	suspend fun getByPackageName(packageName: String): App?

	@Insert(entity = App::class)
	suspend fun insert(vararg inputs: AppSystemDetails)

	@Update(entity = App::class)
	suspend fun update(vararg inputs: AppSystemDetails)

	@Update(entity = App::class)
	suspend fun updateFavorite(vararg inputs: AppFavorite)

	@Query("DELETE FROM app WHERE packageName = :packageName")
	suspend fun removeByPackageName(packageName: String)

	@Query("DELETE FROM app WHERE id NOT IN (:ids)")
	suspend fun removeNotIn(ids: List<String>)
}
