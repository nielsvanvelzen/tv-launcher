package nl.ndat.tvlauncher.ui.toolbar

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.get
import androidx.tv.material3.Tab
import androidx.tv.material3.TabRow
import androidx.tv.material3.Text
import kotlinx.coroutines.flow.map
import nl.ndat.tvlauncher.R
import nl.ndat.tvlauncher.data.Destinations
import nl.ndat.tvlauncher.util.composition.LocalNavController
import nl.ndat.tvlauncher.util.composition.LocalNavGraph

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ToolbarTabs(
	modifier: Modifier,
) {
	val navController = LocalNavController.current
	val navGraph = LocalNavGraph.current
	val currentDestinationId by navController.currentBackStackEntryFlow.map { it.destination.id }.collectAsState(null)
	val tabs = mapOf(
		Destinations.Home to stringResource(R.string.tab_home),
		Destinations.Apps to stringResource(R.string.tab_apps),
	)

	TabRow(
		selectedTabIndex = tabs.keys.indexOfFirst { destination -> navGraph[destination].id == currentDestinationId },
		modifier = modifier.focusRestorer(),
	) {
		tabs.toList().forEachIndexed { index, (destination, name) ->
			key(index) {
				val selected = navGraph[destination].id == currentDestinationId
				Tab(
					selected = selected,
					onFocus = {
						if (!selected) {
							navController.navigate(destination) {
								if (currentDestinationId != null) {
									popUpTo(currentDestinationId!!) {
										inclusive = true
									}
								}
							}
						}
					},
					modifier = Modifier.padding(16.dp, 8.dp)
				) {
					Text(name)
				}
			}
		}
	}
}
