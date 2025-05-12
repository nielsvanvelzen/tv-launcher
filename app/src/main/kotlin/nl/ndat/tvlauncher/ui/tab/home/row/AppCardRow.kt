package nl.ndat.tvlauncher.ui.tab.home.row

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import nl.ndat.tvlauncher.data.sqldelight.App
import nl.ndat.tvlauncher.ui.component.card.AppCard
import nl.ndat.tvlauncher.ui.tab.home.AppPopup
import nl.ndat.tvlauncher.ui.tab.home.HomeTabViewModel
import nl.ndat.tvlauncher.util.modifier.ifElse
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppCardRow(
	apps: List<App>,
	modifier: Modifier = Modifier,
) {
	val viewModel = koinViewModel<HomeTabViewModel>()

	CardRow(
		modifier = modifier,
	) { childFocusRequester ->
		itemsIndexed(
			items = apps,
			key = { _, app -> app.id },
		) { index, app ->
			Box(
				modifier = Modifier
					.animateItem()
			) {
				AppCard(
					app = app,
					modifier = Modifier
						.ifElse(
							condition = index == 0,
							positiveModifier = Modifier.focusRequester(childFocusRequester)
						),
					popupContent = {
						AppPopup(
							isFirst = index == 0,
							isLast = index == apps.size - 1,
							isFavorite = app.favoriteOrder != null,
							onToggleFavorite = { favorite ->
								viewModel.favoriteApp(app, favorite)
							},
							onMove = { relativePosition ->
								val newIndex = index + relativePosition
								viewModel.setFavoriteOrder(app, newIndex)
							}
						)
					}
				)
			}
		}
	}
}
