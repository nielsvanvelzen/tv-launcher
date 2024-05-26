package nl.ndat.tvlauncher.ui.toolbar

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.tv.material3.Icon
import androidx.tv.material3.IconButton
import nl.ndat.tvlauncher.R

@Composable
fun ToolbarSettingsButton() = Box {
	val context = LocalContext.current

	IconButton(
		onClick = { context.startActivity(Intent(Settings.ACTION_SETTINGS)) }
	) {
		Icon(
			imageVector = Icons.Outlined.Settings,
			contentDescription = stringResource(id = R.string.settings),
		)
	}
}
