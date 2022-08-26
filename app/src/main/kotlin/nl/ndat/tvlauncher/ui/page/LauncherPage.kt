package nl.ndat.tvlauncher.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.ndat.tvlauncher.data.repository.ChannelRepository
import nl.ndat.tvlauncher.ui.component.row.AppCardRow
import nl.ndat.tvlauncher.ui.component.row.ChannelProgramCardRow
import nl.ndat.tvlauncher.ui.toolbar.Toolbar
import org.koin.androidx.compose.inject

@Composable
fun LauncherPage() {
	val scrollState = rememberScrollState()
	Column(
		verticalArrangement = Arrangement.spacedBy(8.dp),
		modifier = Modifier
			.verticalScroll(scrollState)
			.fillMaxSize(),
	) {
		Toolbar(
			modifier = Modifier.padding(
				vertical = 27.dp,
				horizontal = 48.dp,
			)
		)
		AppCardRow()
		ChannelProgramCardRows()
	}
}

@Composable
fun ChannelProgramCardRows() {
	val channelRepository: ChannelRepository by inject()
	val channels by channelRepository.getChannels().collectAsState(initial = emptyList())

	for (channel in channels) {
		ChannelProgramCardRow(channel)
	}
}
