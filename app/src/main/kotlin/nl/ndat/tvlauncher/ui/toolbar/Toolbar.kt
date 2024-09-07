package nl.ndat.tvlauncher.ui.toolbar

import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Toolbar(
	modifier: Modifier = Modifier,
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.focusGroup()
			.focusRestorer()
			.then(modifier),
		horizontalArrangement = Arrangement.spacedBy(10.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		ToolbarTabs()
		Spacer(modifier = Modifier.weight(1f))
		ToolbarInputsButton()
		ToolbarSettingsButton()
		ToolbarClock()
	}
}
