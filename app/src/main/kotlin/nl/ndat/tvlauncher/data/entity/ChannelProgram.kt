package nl.ndat.tvlauncher.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import nl.ndat.tvlauncher.data.model.ChannelProgramAspectRatio
import nl.ndat.tvlauncher.data.model.ChannelProgramInteractionType
import nl.ndat.tvlauncher.data.model.ChannelProgramType

@Entity(
	tableName = "channel_program",
	foreignKeys = [
		ForeignKey(
			entity = Channel::class,
			parentColumns = ["id"],
			childColumns = ["channelId"],
			onDelete = ForeignKey.CASCADE,
			onUpdate = ForeignKey.CASCADE,
		)
	]
)
data class ChannelProgram(
	@PrimaryKey var id: String,
	val channelId: String,
	val packageName: String,
	val weight: Int,
	val type: ChannelProgramType?,
	val posterArtUri: String?,
	val posterArtAspectRatio: ChannelProgramAspectRatio?,
	val lastPlaybackPositionMillis: Int?,
	val durationMillis: Int?,
	val releaseDate: String?,
	val itemCount: Int?,
	val interactionType: ChannelProgramInteractionType?,
	val interactionCount: Long,
	val author: String?,
	val genre: String?,
	val live: Boolean,
	val startTimeUtcMillis: Long,
	val endTimeUtcMillis: Long,
	val title: String?,
	val episodeTitle: String?,
	val seasonNumber: String?,
	val episodeNumber: String?,
	val description: String?,
	val intentUri: String?,
)
