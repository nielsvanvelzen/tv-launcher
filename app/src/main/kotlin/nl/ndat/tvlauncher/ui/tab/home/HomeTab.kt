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
import androidx.compose.ui.unit.dp
import nl.ndat.tvlauncher.ui.component.row.AppCardRow
import nl.ndat.tvlauncher.ui.component.row.ChannelProgramCardRow
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeTab(
	modifier: Modifier = Modifier
) {
	val viewModel = koinViewModel<HomeTabViewModel>()
	val apps by viewModel.apps.collectAsState()
	val channels by viewModel.channels.collectAsState()

	LazyColumn(
		verticalArrangement = Arrangement.spacedBy(8.dp),
		modifier = modifier
			.fillMaxSize()
	) {
		item {
			AppCardRow(
				apps = apps
			)
		}

		items(channels) { channel ->
			val app = remember(channel.packageName, apps) {
				apps.firstOrNull { app -> app.packageName == channel.packageName }
			}
			val programs by viewModel.channelPrograms(channel).collectAsState(initial = emptyList())

			ChannelProgramCardRow(
				channel = channel,
				app = app,
				programs = programs,
			)
		}
	}
}
