package nl.ndat.tvlauncher.ui.component.row

import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import nl.ndat.tvlauncher.data.sqldelight.App
import nl.ndat.tvlauncher.ui.component.card.AppCard
import nl.ndat.tvlauncher.util.ifElse

@Composable
fun AppCardRow(
	apps: List<App>,
	modifier: Modifier = Modifier,
) {
	CardRow(
		modifier = modifier,
	) { childFocusRequester ->
		itemsIndexed(apps) { index, app ->
			AppCard(
				app = app,
				modifier = Modifier
					.ifElse(
						condition = index == 0,
						positiveModifier = Modifier.focusRequester(childFocusRequester)
					),
			)
		}
	}
}
