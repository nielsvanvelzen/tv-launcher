package nl.ndat.tvlauncher.ui.toolbar

import android.content.Intent
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import nl.ndat.tvlauncher.R
import nl.ndat.tvlauncher.data.repository.InputRepository
import org.koin.compose.rememberKoinInject

@Composable
fun ToolbarInputsButton() {
	val inputRepository = rememberKoinInject<InputRepository>()
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
			tint = Color.Gray,
		)
	}

	DropdownMenu(
		expanded = expand,
		onDismissRequest = { expand = false },
	) {
		for (input in inputs) {
			DropdownMenuItem(
				text = { Text(input.displayName) },
				onClick = {
					if (input.switchIntentUri != null) context.startActivity(Intent.parseUri(input.switchIntentUri, 0))
					expand = false
				},
			)
		}
	}
}
