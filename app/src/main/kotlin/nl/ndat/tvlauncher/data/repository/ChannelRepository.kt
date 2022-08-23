package nl.ndat.tvlauncher.data.repository

import android.content.Context
import nl.ndat.tvlauncher.data.SharedDatabase
import nl.ndat.tvlauncher.data.dao.ChannelDao
import nl.ndat.tvlauncher.data.dao.ChannelProgramDao
import nl.ndat.tvlauncher.data.entity.Channel
import nl.ndat.tvlauncher.data.entity.ChannelProgram
import nl.ndat.tvlauncher.data.model.ChannelType
import nl.ndat.tvlauncher.data.resolver.ChannelResolver
import nl.ndat.tvlauncher.util.withSingleTransaction

class ChannelRepository(
	private val context: Context,
	private val channelResolver: ChannelResolver,
	private val database: SharedDatabase,
	private val channelDao: ChannelDao,
	private val channelProgramDao: ChannelProgramDao,
) {
	private suspend fun commitChannels(type: ChannelType, channels: Collection<Channel>) =
		database.withSingleTransaction {
			// Remove missing channels from database
			val currentIds = channels.map { it.id }
			channelDao.removeNotIn(type, currentIds)

			// Upsert channels
			channels.map { channel -> commitChannel(channel) }
		}

	private suspend fun commitChannel(channel: Channel) {
		val current = channelDao.getById(channel.id)

		if (current != null) channelDao.update(channel)
		else channelDao.insert(channel)
	}

	private suspend fun commitChannelPrograms(
		channelId: String,
		programs: Collection<ChannelProgram>,
	) = database.withSingleTransaction {
		// Remove missing channels from database
		val currentIds = programs.map { it.id }
		channelProgramDao.removeNotIn(channelId, currentIds)

		// Upsert channels
		programs.map { program -> commitChannelProgram(program) }
	}

	private suspend fun commitChannelProgram(program: ChannelProgram) {
		val current = channelProgramDao.getById(program.id)

		if (current != null) channelProgramDao.update(program)
		else channelProgramDao.insert(program)
	}

	suspend fun refreshAllChannels() {
		refreshPreviewChannels()
		refreshWatchNextChannels()
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
}
