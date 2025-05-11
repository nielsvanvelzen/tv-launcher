package nl.ndat.tvlauncher.ui.screen.launcher

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import nl.ndat.tvlauncher.data.DefaultDestination
import nl.ndat.tvlauncher.data.createDestinationsGraph
import nl.ndat.tvlauncher.ui.toolbar.Toolbar

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
		val contentFocusRequester = remember { FocusRequester() }
		LaunchedEffect(contentFocusRequester) {
			contentFocusRequester.requestFocus()
		}

		Toolbar(
			navController = navController,
			navGraph = navGraph,
			modifier = Modifier
				.padding(
					vertical = 27.dp,
					horizontal = 48.dp,
				)
		)

		NavHost(
			navController = navController,
			graph = navGraph,
			modifier = Modifier
				.focusRequester(contentFocusRequester)
		)
	}
}
