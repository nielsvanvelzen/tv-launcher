package nl.ndat.tvlauncher.ui.toolbar

import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.unit.dp
import nl.ndat.tvlauncher.data.model.ToolbarLocation
import nl.ndat.tvlauncher.data.repository.PreferenceRepository
import org.koin.compose.koinInject

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Toolbar(
	modifier: Modifier = Modifier,
) {
	val preferenceRepository = koinInject<PreferenceRepository>()
	val location by preferenceRepository.toolbarLocation.collectAsState()

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.focusGroup()
			.focusRestorer()
	) {
		Row(
			modifier = modifier
				.align(
					when (location) {
						ToolbarLocation.START -> Alignment.Start
						ToolbarLocation.CENTER -> Alignment.CenterHorizontally
						ToolbarLocation.END -> Alignment.End
					}
				),
			horizontalArrangement = Arrangement.spacedBy(8.dp),
			verticalAlignment = Alignment.CenterVertically,
		) {
			ToolbarTitle()
			ToolbarInputsButton()
			ToolbarSettingsButton()
			ToolbarClock()
		}
	}
}
