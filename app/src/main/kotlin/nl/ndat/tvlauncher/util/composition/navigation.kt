package nl.ndat.tvlauncher.util.composition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.staticCompositionLocalOf
import nl.ndat.tvlauncher.data.DefaultDestination
import nl.ndat.tvlauncher.data.Destination

val LocalBackStack = staticCompositionLocalOf<SnapshotStateList<Destination>> {
	error("LocalBackStack not provided")
}
@Composable
fun ProvideNavigation(
	backStack: SnapshotStateList<Destination> = remember { mutableStateListOf<Destination>(DefaultDestination) },
	content: @Composable () -> Unit,
) = CompositionLocalProvider(
	LocalBackStack provides backStack,
	content = content,
)
