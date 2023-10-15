package nl.ndat.tvlauncher.ui.component.row

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.tv.foundation.lazy.list.items
import nl.ndat.tvlauncher.data.entity.App
import nl.ndat.tvlauncher.ui.component.card.AppCard
import org.koin.compose.rememberKoinInject

@Composable
fun AppCardRow(
	title: String? = null,
	apps: List<App>
) {
	CardRow(title) {
		items(apps) { app ->
			AppCard(app = app)
		}
	}
}
