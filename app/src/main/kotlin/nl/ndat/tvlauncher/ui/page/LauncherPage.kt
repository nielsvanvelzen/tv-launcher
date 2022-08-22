package nl.ndat.tvlauncher.ui.page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.ndat.tvlauncher.data.repository.ChannelRepository
import nl.ndat.tvlauncher.ui.component.row.AppCardRow
import nl.ndat.tvlauncher.ui.component.row.ChannelProgramCardRow
import nl.ndat.tvlauncher.ui.toolbar.Toolbar
import org.koin.androidx.compose.inject

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LauncherPage() {
	MaterialTheme(
		colors = darkColors(
			background = Color.Black,
			surface = Color.Black,
		),
		shapes = Shapes(
			small = RoundedCornerShape(4.dp),
			medium = RoundedCornerShape(4.dp),
			large = RoundedCornerShape(8.dp)
		),
	) {
		CompositionLocalProvider(
			// Disable overscroll
			LocalOverscrollConfiguration provides null
		) {
			Surface(
				modifier = Modifier.padding(vertical = 27.dp)
			) {
				val scrollState = rememberScrollState()
				Column(
					verticalArrangement = Arrangement.spacedBy(8.dp),
					modifier = Modifier
						.verticalScroll(scrollState)
						.fillMaxSize(),
				) {
					Toolbar(
						modifier = Modifier.padding(horizontal = 48.dp)
					)
					AppCardRow()
					ChannelProgramCardRows()
				}
			}
		}
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
