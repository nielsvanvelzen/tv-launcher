package nl.ndat.tvlauncher.ui.component.row

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.tv.foundation.lazy.list.items
import nl.ndat.tvlauncher.R
import nl.ndat.tvlauncher.data.model.ChannelType
import nl.ndat.tvlauncher.data.repository.AppRepository
import nl.ndat.tvlauncher.data.repository.ChannelRepository
import nl.ndat.tvlauncher.data.sqldelight.App
import nl.ndat.tvlauncher.data.sqldelight.Channel
import nl.ndat.tvlauncher.ui.component.card.ChannelProgramCard
import org.koin.compose.koinInject

@Composable
fun ChannelProgramCardRow(
	modifier: Modifier = Modifier,
	channel: Channel,
) {
	val channelRepository = koinInject<ChannelRepository>()
	val appRepository = koinInject<AppRepository>()
	val programs by channelRepository.getProgramsByChannel(channel).collectAsState(initial = emptyList())

	var app by remember { mutableStateOf<App?>(null) }
	LaunchedEffect(channel.packageName) { app = appRepository.getByPackageName(channel.packageName) }

	val title = when (channel.type) {
		ChannelType.WATCH_NEXT -> stringResource(R.string.channel_watch_next)
		ChannelType.PREVIEW -> stringResource(
			R.string.channel_preview,
			app?.displayName ?: channel.packageName,
			channel.displayName
		)
	}

	if (programs.isNotEmpty()) {
		CardRow(
			title = title,
			modifier = modifier,
		) {
			items(programs) { program ->
				ChannelProgramCard(program)
			}
		}
	}
}
