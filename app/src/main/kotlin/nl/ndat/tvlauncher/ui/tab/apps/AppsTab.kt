package nl.ndat.tvlauncher.ui.tab.apps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.unit.dp
import nl.ndat.tvlauncher.ui.component.card.AppCard
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppsTab(
	modifier: Modifier = Modifier
) {
	val viewModel = koinViewModel<AppsTabViewModel>()
	val apps by viewModel.apps.collectAsState()

	LazyVerticalGrid(
		contentPadding = PaddingValues(
			vertical = 16.dp,
			horizontal = 48.dp,
		),
		verticalArrangement = Arrangement.spacedBy(14.dp),
		horizontalArrangement = Arrangement.spacedBy(14.dp),
		columns = GridCells.Adaptive(90.dp * (16f / 9f)),
		modifier = modifier
			.focusRestorer()
			.fillMaxSize()
	) {
		items(
			items = apps,
			key = { app -> app.id },
		) { app ->
			Box(
				modifier = Modifier
					.animateItem()
			) {
				AppCard(
					app = app,
					popupContent = {
						AppPopup(
							isFavorite = app.favoriteOrder != null,
							onToggleFavorite = { favorite ->
								viewModel.favoriteApp(app, favorite)
							},
							isAutoStart = app.autoStartOrder != null,
							onToggleAutoStart = { autoStart ->
								viewModel.toggleAutoStart(app, autoStart)
							}
						)
					}
				)
			}
		}
	}
}
