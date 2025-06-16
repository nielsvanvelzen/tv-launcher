package nl.ndat.tvlauncher.ui.tab.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nl.ndat.tvlauncher.R
import nl.ndat.tvlauncher.ui.tab.home.row.AppCardRow
import nl.ndat.tvlauncher.ui.tab.home.row.ChannelProgramCardRow
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeTab(
	modifier: Modifier = Modifier
) {
	val viewModel = koinViewModel<HomeTabViewModel>()
	val apps by viewModel.apps.collectAsState()
	val channels by viewModel.channels.collectAsState()
	val watchNext by viewModel.watchNext.collectAsState()

	LazyColumn(
		verticalArrangement = Arrangement.spacedBy(8.dp),
		modifier = modifier
			.fillMaxSize()
	) {
		item(key = "apps") {
			AppCardRow(
				apps = apps
			)
		}

		items(
			items = watchNext,
			key = { channel -> channel.id }
		) { channel ->
			val programs by viewModel.channelPrograms(channel).collectAsState(initial = emptyList())

			ChannelProgramCardRow(
				title = stringResource(R.string.channel_watch_next),
				programs = programs,
			)
		}

		items(
			items = channels,
			key = { channel -> channel.id }
		) { channel ->
			val app = remember(channel.packageName, apps) {
				apps.firstOrNull { app -> app.packageName == channel.packageName }
			}
			val programs by viewModel.channelPrograms(channel).collectAsState(initial = emptyList())

			if (app != null) {
				val title = stringResource(R.string.channel_preview, app.displayName, channel.displayName)

				ChannelProgramCardRow(
					title = title,
					programs = programs,
				)
			}
		}
	}
}
