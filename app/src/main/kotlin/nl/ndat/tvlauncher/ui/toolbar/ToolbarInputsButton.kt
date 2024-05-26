package nl.ndat.tvlauncher.ui.toolbar

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.tv.material3.Icon
import androidx.tv.material3.IconButton
import androidx.tv.material3.ListItem
import androidx.tv.material3.Text
import nl.ndat.tvlauncher.R
import nl.ndat.tvlauncher.data.repository.InputRepository
import org.koin.compose.koinInject

@Composable
fun ToolbarInputsButton() {
	val inputRepository = koinInject<InputRepository>()
	val inputs by inputRepository.getInputs().collectAsState(initial = emptyList())
	val context = LocalContext.current

	// TODO: When toolbar is configurable this should be removed
	if (inputs.isEmpty()) return

	var expand by remember { mutableStateOf(false) }

	IconButton(
		onClick = { expand = true },
		enabled = inputs.isNotEmpty()
	) {
		Icon(
			painter = painterResource(id = R.drawable.ic_input),
			contentDescription = stringResource(id = R.string.input_switch),
		)
	}

	if (expand) {
		// TODO Use a dropdown
		Dialog(onDismissRequest = { expand = false }) {
			Column {
				for (input in inputs) {
					ListItem(
						selected = false,
						headlineContent = { Text(input.displayName) },
						onClick = {
							if (input.switchIntentUri != null) {
								context.startActivity(Intent.parseUri(input.switchIntentUri, 0))
							}

							expand = false
						},
					)
				}
			}
		}
	}
}
