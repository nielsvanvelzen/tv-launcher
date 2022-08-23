package nl.ndat.tvlauncher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import nl.ndat.tvlauncher.data.model.ChannelType

@Entity(
	tableName = "channel",
)
data class Channel(
	@PrimaryKey var id: String,
	val type: ChannelType,
	val channelId: Long,
	val displayName: String,
	val description: String?,
	val packageName: String,
	val appLinkIntentUri: String?,
)
