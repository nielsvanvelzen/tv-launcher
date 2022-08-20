package nl.ndat.tvlauncher.ui.toolbar

import android.content.Intent
import android.provider.Settings
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import nl.ndat.tvlauncher.R

@Composable
fun ToolbarSettingsButton() {
	val context = LocalContext.current
	IconButton(
		onClick = { context.startActivity(Intent(Settings.ACTION_SETTINGS)) }
	) {
		Icon(
			imageVector = Icons.Default.Settings,
			contentDescription = stringResource(id = R.string.settings),
			tint = Color.Gray,
		)
	}
}
