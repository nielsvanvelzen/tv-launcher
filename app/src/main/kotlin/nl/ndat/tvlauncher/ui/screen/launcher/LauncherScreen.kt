package nl.ndat.tvlauncher.ui.screen.launcher

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import nl.ndat.tvlauncher.data.DefaultDestination
import nl.ndat.tvlauncher.data.createDestinationsGraph
import nl.ndat.tvlauncher.ui.toolbar.Toolbar
import nl.ndat.tvlauncher.util.modifier.autoFocus

@Composable
fun LauncherScreen() {
	val navController = rememberNavController()
	val navGraph = remember(navController) {
		navController.createGraph(DefaultDestination) {
			createDestinationsGraph()
		}
	}

	Column(
		modifier = Modifier
			.fillMaxSize()
	) {
		Toolbar(
			navController = navController,
			navGraph = navGraph,
			modifier = Modifier
				.padding(
					vertical = 27.dp,
					horizontal = 48.dp,
				)
		)

		Box(
			modifier = Modifier
				.autoFocus()
		) {
			NavHost(
				navController = navController,
				graph = navGraph
			)
		}
	}
}
