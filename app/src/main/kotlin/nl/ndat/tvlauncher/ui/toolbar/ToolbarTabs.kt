package nl.ndat.tvlauncher.ui.toolbar

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Tab
import androidx.tv.material3.TabRow
import androidx.tv.material3.Text
import nl.ndat.tvlauncher.R
import nl.ndat.tvlauncher.ui.screen.launcher.LauncherScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ToolbarTabs() {
	val viewModel = koinViewModel<LauncherScreenViewModel>()
	val selectedTabIndex by viewModel.tabIndex.collectAsState()

	val tabs = listOf(
		stringResource(R.string.tab_home),
		stringResource(R.string.tab_apps),
	)

	TabRow(selectedTabIndex) {
		tabs.forEachIndexed { tabIndex, name ->
			Tab(
				selected = selectedTabIndex == tabIndex,
				onFocus = { viewModel.setTabIndex(tabIndex) },
				modifier = Modifier.padding(16.dp, 8.dp)
			) {
				Text(name)
			}
		}
	}
}
