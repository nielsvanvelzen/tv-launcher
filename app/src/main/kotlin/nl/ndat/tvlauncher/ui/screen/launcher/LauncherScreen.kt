package nl.ndat.tvlauncher.ui.screen.launcher

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import nl.ndat.tvlauncher.ui.toolbar.Toolbar
import nl.ndat.tvlauncher.util.composition.LocalBackStack
import nl.ndat.tvlauncher.util.composition.ProvideNavigation
import nl.ndat.tvlauncher.util.modifier.autoFocus

@Composable
fun LauncherScreen() {
	ProvideNavigation {
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
				val backStack = LocalBackStack.current
				NavDisplay(
					backStack = backStack,
					entryProvider = { route ->
						NavEntry(route) {
							route.Content()
						}
					},
				)
			}
		}
	}
}
