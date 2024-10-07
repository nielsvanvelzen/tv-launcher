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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.unit.dp
import nl.ndat.tvlauncher.ui.tab.apps.AppsTab
import nl.ndat.tvlauncher.ui.tab.home.HomeTab
import nl.ndat.tvlauncher.ui.toolbar.Toolbar
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LauncherScreen() {
	val viewModel = koinViewModel<LauncherScreenViewModel>()
	val tabIndex by viewModel.tabIndex.collectAsState()

	Column(
		modifier = Modifier
			.fillMaxSize()
	) {
		val (first, second) = remember { FocusRequester.createRefs() }

		LaunchedEffect(Unit) {
			first.requestFocus()
		}

		Toolbar(
			modifier = Modifier
				.padding(
					vertical = 27.dp,
					horizontal = 48.dp,
				)
				.focusRequester(second)
				.focusProperties { next = first }
		)

		AnimatedContent(
			targetState = tabIndex,
			modifier = Modifier
				.focusRequester(first)
				.focusProperties { next = second },
			label = "content"
		) { tabIndex ->
			if (tabIndex == 0) HomeTab()
			else if (tabIndex == 1) AppsTab(modifier = Modifier.focusRestorer())
		}
	}
}
