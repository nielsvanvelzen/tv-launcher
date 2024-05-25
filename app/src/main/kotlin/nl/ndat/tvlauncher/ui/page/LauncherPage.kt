package nl.ndat.tvlauncher.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.items
import nl.ndat.tvlauncher.data.repository.ChannelRepository
import nl.ndat.tvlauncher.ui.component.row.AppCardRow
import nl.ndat.tvlauncher.ui.component.row.ChannelProgramCardRow
import nl.ndat.tvlauncher.ui.toolbar.Toolbar
import org.koin.compose.koinInject

@Composable
fun LauncherPage() {
	val channelRepository = koinInject<ChannelRepository>()
	val channels by channelRepository.getChannels().collectAsState(initial = emptyList())
	val defaultFocusRequester = remember { FocusRequester() }

	TvLazyColumn(
		verticalArrangement = Arrangement.spacedBy(8.dp),
		modifier = Modifier
			.fillMaxSize()
			.focusProperties {
				enter = { defaultFocusRequester }
			}
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
				modifier = Modifier.focusRequester(defaultFocusRequester)
			)
		}

		items(channels) { channel ->
			ChannelProgramCardRow(channel = channel)
		}
	}
}
