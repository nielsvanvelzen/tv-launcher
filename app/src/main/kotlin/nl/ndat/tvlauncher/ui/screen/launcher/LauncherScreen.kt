package nl.ndat.tvlauncher.ui.screen.launcher

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import nl.ndat.tvlauncher.ui.toolbar.Toolbar
import nl.ndat.tvlauncher.util.composition.LocalNavController
import nl.ndat.tvlauncher.util.composition.LocalNavGraph
import nl.ndat.tvlauncher.util.composition.ProvideNavigation
import nl.ndat.tvlauncher.util.modifier.autoFocus

@Composable
fun LauncherScreen() {
	ProvideNavigation() {
		Column(
			modifier = Modifier
				.fillMaxSize()
		) {
			Toolbar(
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
					navController = LocalNavController.current,
					graph = LocalNavGraph.current,
					enterTransition = { fadeIn() },
					exitTransition = { fadeOut() }
				)
			}
		}
	}
}
