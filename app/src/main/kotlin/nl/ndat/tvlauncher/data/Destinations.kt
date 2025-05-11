package nl.ndat.tvlauncher.data

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import nl.ndat.tvlauncher.ui.tab.apps.AppsTab
import nl.ndat.tvlauncher.ui.tab.home.HomeTab

object Destinations {
	@Serializable
	object Home

	@Serializable
	object Apps
}

val DefaultDestination = Destinations.Home

fun NavGraphBuilder.createDestinationsGraph() {
	composable<Destinations.Home> { HomeTab() }
	composable<Destinations.Apps> { AppsTab() }
}
