package nl.ndat.tvlauncher.ui.component.row

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.tv.foundation.lazy.list.items
import nl.ndat.tvlauncher.data.repository.AppRepository
import nl.ndat.tvlauncher.ui.component.card.AppCard
import org.koin.androidx.compose.get

@Composable
fun AppCardRow() {
	val appRepository: AppRepository = get()
	val apps by appRepository.getApps().collectAsState(initial = emptyList())

	CardRow {
		items(apps) { app ->
			AppCard(app = app)
		}
	}
}
