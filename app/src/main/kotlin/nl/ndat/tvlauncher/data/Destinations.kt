package nl.ndat.tvlauncher.data

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable
import nl.ndat.tvlauncher.ui.tab.apps.AppsTab
import nl.ndat.tvlauncher.ui.tab.home.HomeTab

interface Destination {
	@Composable
	fun Content()
}

object Destinations {
	@Serializable
	object Home : Destination {
		@Composable
		override fun Content() = HomeTab()
	}

	@Serializable
	object Apps : Destination {
		@Composable
		override fun Content() = AppsTab()
	}
}

val DefaultDestination = Destinations.Home
