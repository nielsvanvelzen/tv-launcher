package nl.ndat.tvlauncher.ui.toolbar

import android.provider.Settings
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp

@Composable
fun ToolbarTitle() {
	val context = LocalContext.current
	val name = remember(context) {
		Settings.Global.getString(context.contentResolver, Settings.Global.DEVICE_NAME).orEmpty()
	}

	Text(
		fontSize = 20.sp,
		color = Color.Gray,
		text = name,
	)
}
