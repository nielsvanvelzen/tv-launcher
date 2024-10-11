package nl.ndat.tvlauncher.data.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.ndat.tvlauncher.data.DatabaseContainer
import nl.ndat.tvlauncher.data.executeAsListFlow
import nl.ndat.tvlauncher.data.model.ChannelType
import nl.ndat.tvlauncher.data.resolver.ChannelResolver
import nl.ndat.tvlauncher.data.sqldelight.Channel
import nl.ndat.tvlauncher.data.sqldelight.ChannelProgram

class ChannelRepository(
	private val context: Context,
	private val channelResolver: ChannelResolver,
	private val database: DatabaseContainer,
) {
	private suspend fun commitChannels(type: ChannelType, channels: Collection<Channel>) = withContext(Dispatchers.IO) {
		database.transaction {
			// Remove channels found in database but not in committed list
			database.channels.getByType(type)
				.executeAsList()
				.map { it.id }
				.subtract(channels.map { it.id }.toSet())
				.map { id -> database.channels.removeById(id) }

			// Upsert channels
			channels.map { channel -> commitChannel(channel) }
		}
	}

	private suspend fun commitChannel(channel: Channel) = withContext(Dispatchers.IO) {
		database.channels.upsert(
			id = channel.id,
			type = channel.type,
			channelId = channel.channelId,
			displayName = channel.displayName,
			description = channel.description,
			packageName = channel.packageName,
			appLinkIntentUri = channel.appLinkIntentUri,
		)
	}

	private suspend fun commitChannelPrograms(
		channelId: String,
		programs: Collection<ChannelProgram>,
	) =  withContext(Dispatchers.IO) {
		database.transaction {
			// Remove channels found in database but not in committed list
			database.channelPrograms.getByChannel(channelId)
				.executeAsList()
				.map { it.id }
				.subtract(programs.map { it.id }.toSet())
				.map { id -> database.channelPrograms.removeById(id) }

			// Upsert channels
			programs.map { program -> commitChannelProgram(program) }
		}
	}

	private suspend fun commitChannelProgram(program: ChannelProgram) = withContext(Dispatchers.IO) {
		database.channelPrograms.upsert(
			id = program.id,
			channelId = program.channelId,
			packageName = program.packageName,
			weight = program.weight,
			type = program.type,
			posterArtUri = program.posterArtUri,
			posterArtAspectRatio = program.posterArtAspectRatio,
			lastPlaybackPositionMillis = program.lastPlaybackPositionMillis,
			durationMillis = program.durationMillis,
			releaseDate = program.releaseDate,
			itemCount = program.itemCount,
			interactionType = program.interactionType,
			interactionCount = program.interactionCount,
			author = program.author,
			genre = program.genre,
			live = program.live,
			startTimeUtcMillis = program.startTimeUtcMillis,
			endTimeUtcMillis = program.endTimeUtcMillis,
			title = program.title,
			episodeTitle = program.episodeTitle,
			seasonNumber = program.seasonNumber,
			episodeNumber = program.episodeNumber,
			description = program.description,
			intentUri = program.intentUri,
		)
	}

	suspend fun refreshAllChannels() {
		refreshWatchNextChannels()
		refreshPreviewChannels()
	}

	suspend fun refreshPreviewChannels() {
		val channels = channelResolver.getPreviewChannels(context)
		commitChannels(ChannelType.PREVIEW, channels)

		for (channel in channels) refreshChannelPrograms(channel)
	}

	suspend fun refreshWatchNextChannels() {
		val channel = Channel(
			id = ChannelResolver.CHANNEL_ID_WATCH_NEXT,
			type = ChannelType.WATCH_NEXT,
			channelId = -1,
			displayName = "",
			description = null,
			packageName = "",
			appLinkIntentUri = null,
		)
		val programs = channelResolver.getWatchNextPrograms(context)

		commitChannel(channel)
		commitChannelPrograms(channel.id, programs)
	}

	suspend fun refreshChannelPrograms(channel: Channel) {
		val programs = channelResolver.getChannelPrograms(context, channel.channelId)
		commitChannelPrograms(channel.id, programs)
	}

	fun getChannels() = database.channels.getAll().executeAsListFlow()
	fun getFavoriteAppChannels() = database.channels.getFavoriteAppChannels(::Channel).executeAsListFlow()
	fun getProgramsByChannel(channel: Channel) = database.channelPrograms.getByChannel(channel.id).executeAsListFlow()
}
