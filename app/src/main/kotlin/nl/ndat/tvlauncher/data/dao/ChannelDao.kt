package nl.ndat.tvlauncher.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import nl.ndat.tvlauncher.data.entity.Channel
import nl.ndat.tvlauncher.data.model.ChannelType

@Dao
interface ChannelDao {
	@Query("SELECT * FROM channel")
	fun getAll(): Flow<List<Channel>>

	@Query("SELECT * FROM channel WHERE id = :id LIMIT 1")
	suspend fun getById(id: String): Channel?

	@Insert
	suspend fun insert(vararg channels: Channel)

	@Update
	suspend fun update(vararg channels: Channel)

	@Query("DELETE FROM channel WHERE type = :type AND id NOT IN (:ids)")
	suspend fun removeNotIn(type: ChannelType, ids: List<String>)
}
