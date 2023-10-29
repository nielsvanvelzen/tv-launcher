package nl.ndat.tvlauncher.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.items
import nl.ndat.tvlauncher.R
import nl.ndat.tvlauncher.data.repository.ChannelRepository
import nl.ndat.tvlauncher.data.repository.AppRepository
import nl.ndat.tvlauncher.ui.component.row.AppCardRow
import nl.ndat.tvlauncher.ui.component.row.ChannelProgramCardRow
import nl.ndat.tvlauncher.ui.toolbar.Toolbar
import org.koin.compose.rememberKoinInject

@Composable
fun LauncherPage() {
	val channelRepository = rememberKoinInject<ChannelRepository>()
	val channels by channelRepository.getChannels().collectAsState(initial = emptyList())
	
	val appRepository = rememberKoinInject<AppRepository>()
	val apps by appRepository.getApps().collectAsState(initial = emptyList())
	val favorites = apps.filter { it.isFavorite }

	TvLazyColumn(
		verticalArrangement = Arrangement.spacedBy(8.dp),
		modifier = Modifier
			.fillMaxSize()
	) {
		item {
			Toolbar(
				modifier = Modifier.padding(
					vertical = 27.dp,
					horizontal = 48.dp,
				)
			)
		}

		if (favorites.size > 0) item { AppCardRow(stringResource(R.string.favorites), favorites) }
		item { AppCardRow(stringResource(R.string.all_apps), apps) }
		items(channels) { channel -> ChannelProgramCardRow(channel) }
	}
}
