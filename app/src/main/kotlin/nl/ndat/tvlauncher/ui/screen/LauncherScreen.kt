package nl.ndat.tvlauncher.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.unit.dp
import nl.ndat.tvlauncher.data.repository.AppRepository
import nl.ndat.tvlauncher.data.repository.ChannelRepository
import nl.ndat.tvlauncher.ui.component.row.AppCardRow
import nl.ndat.tvlauncher.ui.component.row.ChannelProgramCardRow
import nl.ndat.tvlauncher.ui.toolbar.Toolbar
import org.koin.compose.koinInject

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LauncherScreen() {
	val channelRepository = koinInject<ChannelRepository>()
	val appRepository = koinInject<AppRepository>()
	val channels by channelRepository.getChannels().collectAsState(initial = emptyList())
	val apps by appRepository.getApps().collectAsState(initial = emptyList())

	val childFocusRequester = remember { FocusRequester() }

	LazyColumn(
		verticalArrangement = Arrangement.spacedBy(8.dp),
		modifier = Modifier
			.fillMaxSize()
			.focusRestorer { childFocusRequester }
	) {
		item {
			Toolbar(
				modifier = Modifier.padding(
					vertical = 27.dp,
					horizontal = 48.dp,
				)
			)
		}

		item {
			AppCardRow(
				apps,
				modifier = Modifier
					.focusRequester(childFocusRequester)
			)
		}

		items(channels) { channel ->
			ChannelProgramCardRow(channel = channel)
		}
	}
}
