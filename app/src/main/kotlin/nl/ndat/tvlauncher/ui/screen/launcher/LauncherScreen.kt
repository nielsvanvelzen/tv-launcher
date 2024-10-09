package nl.ndat.tvlauncher.ui.screen.launcher

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import nl.ndat.tvlauncher.ui.tab.apps.AppsTab
import nl.ndat.tvlauncher.ui.tab.home.HomeTab
import nl.ndat.tvlauncher.ui.toolbar.Toolbar
import org.koin.androidx.compose.koinViewModel

@Composable
fun LauncherScreen() {
	val viewModel = koinViewModel<LauncherScreenViewModel>()
	val tabIndex by viewModel.tabIndex.collectAsState()

	Column(
		modifier = Modifier
			.fillMaxSize()
	) {
		val contentFocusRequester = remember { FocusRequester() }
		LaunchedEffect(contentFocusRequester) {
			contentFocusRequester.requestFocus()
		}

		Toolbar(
			modifier = Modifier
				.padding(
					vertical = 27.dp,
					horizontal = 48.dp,
				)
		)

		AnimatedContent(
			targetState = tabIndex,
			modifier = Modifier
				.focusRequester(contentFocusRequester),
			label = "content"
		) { tabIndex ->
			if (tabIndex == 0) HomeTab()
			else if (tabIndex == 1) AppsTab()
		}
	}
}
