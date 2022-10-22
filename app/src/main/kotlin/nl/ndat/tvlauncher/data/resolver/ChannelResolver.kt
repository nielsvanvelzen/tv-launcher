package nl.ndat.tvlauncher.data.resolver

import android.content.Context
import android.database.CursorIndexOutOfBoundsException
import android.net.Uri
import android.util.Base64
import androidx.tvprovider.media.tv.PreviewChannel
import androidx.tvprovider.media.tv.PreviewProgram
import androidx.tvprovider.media.tv.TvContractCompat
import androidx.tvprovider.media.tv.WatchNextProgram
import nl.ndat.tvlauncher.data.entity.Channel
import nl.ndat.tvlauncher.data.entity.ChannelProgram
import nl.ndat.tvlauncher.data.model.ChannelProgramAspectRatio
import nl.ndat.tvlauncher.data.model.ChannelProgramInteractionType
import nl.ndat.tvlauncher.data.model.ChannelProgramType
import nl.ndat.tvlauncher.data.model.ChannelType
import org.json.JSONObject
import timber.log.Timber

class ChannelResolver {
	companion object {
		const val CHANNEL_ID_PREFIX = "channel:"
		const val CHANNEL_ID_WATCH_NEXT = "$CHANNEL_ID_PREFIX@watch_next"
		const val CHANNEL_PROGRAM_ID_PREFIX = "channel_program:"
	}

	fun getPreviewChannels(context: Context): List<Channel> {
		val cursor = context.contentResolver.query(
			TvContractCompat.Channels.CONTENT_URI,
			PreviewChannel.Columns.PROJECTION,
			null,
			null,
			null
		) ?: return emptyList()

		return buildList {
			if (!cursor.moveToFirst()) {
				Timber.w("Unable to move cursor")
				return emptyList()
			}

			do {
				try {
					if (cursor.getString(PreviewChannel.Columns.COL_APP_LINK_INTENT_URI).isNullOrEmpty()) {
						Timber.d("Ignoring channel ${cursor.getString(PreviewChannel.Columns.COL_PACKAGE_NAME)} due to missing intent uri")
					} else if (cursor.getString(PreviewChannel.Columns.COL_DISPLAY_NAME).isNullOrEmpty()) {
						Timber.d("Ignoring channel ${cursor.getString(PreviewChannel.Columns.COL_PACKAGE_NAME)} due to missing display name")
					} else {
						add(PreviewChannel.fromCursor(cursor).toChannel())
					}
				} catch (err: NullPointerException) {
					Timber.e(err, "Unable to parse channel")
				} catch (err: CursorIndexOutOfBoundsException) {
					Timber.e(err, "Unable to parse channel")
				}
			} while (cursor.moveToNext())

			cursor.close()
		}
	}

	fun getChannelPrograms(context: Context, channelId: Long): List<ChannelProgram> {
		val cursor = context.contentResolver.query(
			TvContractCompat.buildPreviewProgramsUriForChannel(channelId),
			PreviewProgram.PROJECTION,
			null,
			null,
			null
		) ?: return emptyList()

		return buildList {
			if (!cursor.moveToFirst()) {
				Timber.w("Unable to move cursor")
				return emptyList()
			}

			do {
				try {
					add(PreviewProgram.fromCursor(cursor).toChannelProgram())
				} catch (err: NullPointerException) {
					Timber.e(err, "Unable to parse channel program")
				} catch (err: CursorIndexOutOfBoundsException) {
					Timber.e(err, "Unable to parse channel program")
				}
			} while (cursor.moveToNext())

			cursor.close()
		}
	}

	fun getWatchNextPrograms(context: Context): List<ChannelProgram> {
		val cursor = context.contentResolver.query(
			TvContractCompat.WatchNextPrograms.CONTENT_URI,
			WatchNextProgram.PROJECTION,
			null,
			null,
			null
		) ?: return emptyList()

		return buildList {
			if (!cursor.moveToFirst()) {
				Timber.w("Unable to move cursor")
				return emptyList()
			}

			do {
				try {
					add(WatchNextProgram.fromCursor(cursor).toChannelProgram())
				} catch (err: NullPointerException) {
					Timber.e(err, "Unable to parse channel program")
				} catch (err: CursorIndexOutOfBoundsException) {
					Timber.e(err, "Unable to parse channel program")
				}
			} while (cursor.moveToNext())

			cursor.close()
		}
	}

	private fun PreviewChannel.toChannel() = Channel(
		id = "$CHANNEL_ID_PREFIX$id",
		type = ChannelType.PREVIEW,
		channelId = id,
		displayName = displayName.toString(),
		description = description?.toString(),
		packageName = packageName,
		appLinkIntentUri = appLinkIntentUri.toString(),
	)

	private fun PreviewProgram.toChannelProgram() = ChannelProgram(
		id = "$CHANNEL_PROGRAM_ID_PREFIX$id",
		channelId = "$CHANNEL_ID_PREFIX$channelId",
		packageName = packageName,
		weight = weight,
		posterArtUri = decodeArtUri(posterArtUri),
		posterArtAspectRatio = when (posterArtAspectRatio) {
			TvContractCompat.PreviewPrograms.ASPECT_RATIO_16_9 -> ChannelProgramAspectRatio.AR16_9
			TvContractCompat.PreviewPrograms.ASPECT_RATIO_3_2 -> ChannelProgramAspectRatio.AR3_2
			TvContractCompat.PreviewPrograms.ASPECT_RATIO_4_3 -> ChannelProgramAspectRatio.AR4_3
			TvContractCompat.PreviewPrograms.ASPECT_RATIO_1_1 -> ChannelProgramAspectRatio.AR1_1
			TvContractCompat.PreviewPrograms.ASPECT_RATIO_2_3 -> ChannelProgramAspectRatio.AR2_3
			TvContractCompat.PreviewPrograms.ASPECT_RATIO_MOVIE_POSTER -> ChannelProgramAspectRatio.AR1000_1441
			else -> null
		},
		lastPlaybackPositionMillis = lastPlaybackPositionMillis,
		durationMillis = durationMillis,
		type = when (type) {
			TvContractCompat.PreviewPrograms.TYPE_MOVIE -> ChannelProgramType.MOVIE
			TvContractCompat.PreviewPrograms.TYPE_TV_SERIES -> ChannelProgramType.TV_SERIES
			TvContractCompat.PreviewPrograms.TYPE_TV_SEASON -> ChannelProgramType.TV_SEASON
			TvContractCompat.PreviewPrograms.TYPE_TV_EPISODE -> ChannelProgramType.TV_EPISODE
			TvContractCompat.PreviewPrograms.TYPE_CLIP -> ChannelProgramType.CLIP
			TvContractCompat.PreviewPrograms.TYPE_EVENT -> ChannelProgramType.EVENT
			TvContractCompat.PreviewPrograms.TYPE_CHANNEL -> ChannelProgramType.CHANNEL
			TvContractCompat.PreviewPrograms.TYPE_TRACK -> ChannelProgramType.TRACK
			TvContractCompat.PreviewPrograms.TYPE_ALBUM -> ChannelProgramType.ALBUM
			TvContractCompat.PreviewPrograms.TYPE_ARTIST -> ChannelProgramType.ARTIST
			TvContractCompat.PreviewPrograms.TYPE_PLAYLIST -> ChannelProgramType.PLAYLIST
			TvContractCompat.PreviewPrograms.TYPE_STATION -> ChannelProgramType.STATION
			TvContractCompat.PreviewPrograms.TYPE_GAME -> ChannelProgramType.GAME
			else -> null
		},
		releaseDate = releaseDate,
		itemCount = itemCount,
		live = isLive ?: false,
		interactionType = when (interactionType) {
			TvContractCompat.PreviewPrograms.INTERACTION_TYPE_LISTENS -> ChannelProgramInteractionType.LISTENS
			TvContractCompat.PreviewPrograms.INTERACTION_TYPE_FOLLOWERS -> ChannelProgramInteractionType.FOLLOWERS
			TvContractCompat.PreviewPrograms.INTERACTION_TYPE_FANS -> ChannelProgramInteractionType.FANS
			TvContractCompat.PreviewPrograms.INTERACTION_TYPE_LIKES -> ChannelProgramInteractionType.LIKES
			TvContractCompat.PreviewPrograms.INTERACTION_TYPE_THUMBS -> ChannelProgramInteractionType.THUMBS
			TvContractCompat.PreviewPrograms.INTERACTION_TYPE_VIEWS -> ChannelProgramInteractionType.VIEWS
			TvContractCompat.PreviewPrograms.INTERACTION_TYPE_VIEWERS -> ChannelProgramInteractionType.VIEWERS
			else -> null
		},
		interactionCount = interactionCount,
		author = author,
		genre = genre,
		startTimeUtcMillis = startTimeUtcMillis,
		endTimeUtcMillis = endTimeUtcMillis,
		title = title,
		episodeTitle = episodeTitle,
		seasonNumber = seasonNumber,
		episodeNumber = episodeNumber,
		description = description,
		intentUri = intentUri?.toString()
	)

	private fun WatchNextProgram.toChannelProgram() = ChannelProgram(
		id = "$CHANNEL_PROGRAM_ID_PREFIX$id",
		channelId = CHANNEL_ID_WATCH_NEXT,
		packageName = packageName,
		weight = -1,
		posterArtUri = decodeArtUri(posterArtUri),
		posterArtAspectRatio = when (posterArtAspectRatio) {
			TvContractCompat.PreviewPrograms.ASPECT_RATIO_16_9 -> ChannelProgramAspectRatio.AR16_9
			TvContractCompat.PreviewPrograms.ASPECT_RATIO_3_2 -> ChannelProgramAspectRatio.AR3_2
			TvContractCompat.PreviewPrograms.ASPECT_RATIO_4_3 -> ChannelProgramAspectRatio.AR4_3
			TvContractCompat.PreviewPrograms.ASPECT_RATIO_1_1 -> ChannelProgramAspectRatio.AR1_1
			TvContractCompat.PreviewPrograms.ASPECT_RATIO_2_3 -> ChannelProgramAspectRatio.AR2_3
			TvContractCompat.PreviewPrograms.ASPECT_RATIO_MOVIE_POSTER -> ChannelProgramAspectRatio.AR1000_1441
			else -> null
		},
		lastPlaybackPositionMillis = lastPlaybackPositionMillis,
		durationMillis = durationMillis,
		type = when (type) {
			TvContractCompat.PreviewPrograms.TYPE_MOVIE -> ChannelProgramType.MOVIE
			TvContractCompat.PreviewPrograms.TYPE_TV_SERIES -> ChannelProgramType.TV_SERIES
			TvContractCompat.PreviewPrograms.TYPE_TV_SEASON -> ChannelProgramType.TV_SEASON
			TvContractCompat.PreviewPrograms.TYPE_TV_EPISODE -> ChannelProgramType.TV_EPISODE
			TvContractCompat.PreviewPrograms.TYPE_CLIP -> ChannelProgramType.CLIP
			TvContractCompat.PreviewPrograms.TYPE_EVENT -> ChannelProgramType.EVENT
			TvContractCompat.PreviewPrograms.TYPE_CHANNEL -> ChannelProgramType.CHANNEL
			TvContractCompat.PreviewPrograms.TYPE_TRACK -> ChannelProgramType.TRACK
			TvContractCompat.PreviewPrograms.TYPE_ALBUM -> ChannelProgramType.ALBUM
			TvContractCompat.PreviewPrograms.TYPE_ARTIST -> ChannelProgramType.ARTIST
			TvContractCompat.PreviewPrograms.TYPE_PLAYLIST -> ChannelProgramType.PLAYLIST
			TvContractCompat.PreviewPrograms.TYPE_STATION -> ChannelProgramType.STATION
			TvContractCompat.PreviewPrograms.TYPE_GAME -> ChannelProgramType.GAME
			else -> null
		},
		releaseDate = releaseDate,
		itemCount = itemCount,
		live = isLive ?: false,
		interactionType = when (interactionType) {
			TvContractCompat.PreviewPrograms.INTERACTION_TYPE_LISTENS -> ChannelProgramInteractionType.LISTENS
			TvContractCompat.PreviewPrograms.INTERACTION_TYPE_FOLLOWERS -> ChannelProgramInteractionType.FOLLOWERS
			TvContractCompat.PreviewPrograms.INTERACTION_TYPE_FANS -> ChannelProgramInteractionType.FANS
			TvContractCompat.PreviewPrograms.INTERACTION_TYPE_LIKES -> ChannelProgramInteractionType.LIKES
			TvContractCompat.PreviewPrograms.INTERACTION_TYPE_THUMBS -> ChannelProgramInteractionType.THUMBS
			TvContractCompat.PreviewPrograms.INTERACTION_TYPE_VIEWS -> ChannelProgramInteractionType.VIEWS
			TvContractCompat.PreviewPrograms.INTERACTION_TYPE_VIEWERS -> ChannelProgramInteractionType.VIEWERS
			else -> null
		},
		interactionCount = interactionCount,
		author = author,
		genre = genre,
		startTimeUtcMillis = startTimeUtcMillis,
		endTimeUtcMillis = endTimeUtcMillis,
		title = title,
		episodeTitle = episodeTitle,
		seasonNumber = seasonNumber,
		episodeNumber = episodeNumber,
		description = description,
		intentUri = intentUri?.toString()
	)

	private fun decodeArtUri(posterArtUri: Uri?): String? {
		return try {
			// Decode art Uri for Geforce Now
			val uri = Uri.parse(posterArtUri.toString()).lastPathSegment
			val bas64 = Base64.decode(uri, Base64.DEFAULT).toString(charset("UTF-8"))
			JSONObject(bas64).getString("URL").toString()
		}catch (err : Exception){
			posterArtUri?.toString()
		}
	}
}
