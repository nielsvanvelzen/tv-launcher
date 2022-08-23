package nl.ndat.tvlauncher.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import nl.ndat.tvlauncher.data.entity.ChannelProgram

@Dao
interface ChannelProgramDao {
	@Query("SELECT * FROM channel_program")
	fun getAll(): Flow<List<ChannelProgram>>

	@Query("SELECT * FROM channel_program WHERE id = :id LIMIT 1")
	suspend fun getById(id: String): ChannelProgram?

	@Insert
	suspend fun insert(vararg channelPrograms: ChannelProgram)

	@Update
	suspend fun update(vararg channelPrograms: ChannelProgram)

	@Query("DELETE FROM channel_program WHERE channelId = :channelId AND id NOT IN (:ids)")
	suspend fun removeNotIn(channelId: String, ids: List<String>)
}
