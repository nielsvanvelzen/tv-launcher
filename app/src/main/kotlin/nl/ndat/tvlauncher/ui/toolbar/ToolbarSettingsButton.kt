package nl.ndat.tvlauncher.ui.toolbar

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import nl.ndat.tvlauncher.R

@Composable
fun ToolbarSettingsButton() = Box {
	val context = LocalContext.current
	var expand by remember { mutableStateOf(false) }

	IconButton(
		onClick = { expand = true }
	) {
		Icon(
			imageVector = Icons.Outlined.Settings,
			contentDescription = stringResource(id = R.string.settings),
			tint = Color.Gray,
		)
	}

	DropdownMenu(
		expanded = expand,
		onDismissRequest = { expand = false },
	) {
		// TODO implement app settings
//		DropdownMenuItem(
//			text = { Text(stringResource(R.string.settings_launcher)) },
//			onClick = { expand = false }
//		)

		DropdownMenuItem(
			text = { Text(stringResource(R.string.settings_system)) },
			onClick = {
				context.startActivity(Intent(Settings.ACTION_SETTINGS))
				expand = false
			}
		)
	}
}
