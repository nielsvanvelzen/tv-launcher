package nl.ndat.tvlauncher.util.composition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import nl.ndat.tvlauncher.data.DefaultDestination
import nl.ndat.tvlauncher.data.createDestinationsGraph

val LocalNavController = staticCompositionLocalOf<NavHostController> {
	error("NavController not provided")
}

val LocalNavGraph = staticCompositionLocalOf<NavGraph> {
	error("NavGraph not provided")
}

@Composable
fun ProvideNavigation(
	navController: NavHostController = rememberNavController(),
	navGraph: NavGraph = remember(navController) {
		navController.createGraph(DefaultDestination) {
			createDestinationsGraph()
		}
	},
	content: @Composable () -> Unit,
) = CompositionLocalProvider(
	LocalNavController provides navController,
	LocalNavGraph provides navGraph,
	content = content,
)
