package nl.ndat.tvlauncher.ui.toolbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Toolbar(
	modifier: Modifier = Modifier,
) {
	val focusRequester = remember { FocusRequester() }
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.focusRestorer(focusRequester)
			.then(modifier),
		horizontalArrangement = Arrangement.spacedBy(10.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		ToolbarTabs(
			modifier = Modifier
				.focusRequester(focusRequester)
		)
		Spacer(modifier = Modifier.weight(1f))
		ToolbarInputsButton()
		ToolbarSettingsButton()
		ToolbarClock()
	}
}
