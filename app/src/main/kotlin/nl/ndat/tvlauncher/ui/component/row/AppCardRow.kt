package nl.ndat.tvlauncher.ui.component.row

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.tv.foundation.lazy.list.items
import nl.ndat.tvlauncher.data.repository.AppRepository
import nl.ndat.tvlauncher.ui.component.card.AppCard
import org.koin.compose.koinInject

@Composable
fun AppCardRow(
	modifier: Modifier = Modifier,
) {
	val appRepository = koinInject<AppRepository>()
	val apps by appRepository.getApps().collectAsState(initial = emptyList())

	CardRow(
		modifier = modifier,
	) {
		items(apps) { app ->
			AppCard(app = app)
		}
	}
}
