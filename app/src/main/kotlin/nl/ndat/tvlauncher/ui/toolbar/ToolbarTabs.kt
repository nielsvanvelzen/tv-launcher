package nl.ndat.tvlauncher.ui.toolbar

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Tab
import androidx.tv.material3.TabRow
import androidx.tv.material3.Text
import nl.ndat.tvlauncher.R
import nl.ndat.tvlauncher.data.Destinations
import nl.ndat.tvlauncher.util.composition.LocalBackStack

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ToolbarTabs(
	modifier: Modifier,
) {
	val backStack = LocalBackStack.current
	val currentDestination = backStack.lastOrNull()
	val tabs = mapOf(
		Destinations.Home to stringResource(R.string.tab_home),
		Destinations.Apps to stringResource(R.string.tab_apps),
	)

	TabRow(
		selectedTabIndex = tabs.keys.indexOfFirst { destination -> destination == currentDestination },
		modifier = modifier.focusRestorer(),
	) {
		tabs.toList().forEachIndexed { index, (destination, name) ->
			key(index) {
				Tab(
					selected = destination == currentDestination,
					onFocus = {
						if (destination == currentDestination) return@Tab
						if (currentDestination != Destinations.Home) backStack.removeLastOrNull()

						backStack.add(destination)
					},
					modifier = Modifier.padding(16.dp, 8.dp)
				) {
					Text(name)
				}
			}
		}
	}
}
