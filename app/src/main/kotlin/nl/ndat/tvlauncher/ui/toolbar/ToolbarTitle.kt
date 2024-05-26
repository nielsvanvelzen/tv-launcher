package nl.ndat.tvlauncher.ui.toolbar

import android.os.Build
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text

@Composable
fun ToolbarTitle() {
	val context = LocalContext.current
	val name = remember(context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
			Settings.Global.getString(context.contentResolver, Settings.Global.DEVICE_NAME).orEmpty()
		} else {
			Build.MODEL
		}
	}

	Text(
		fontSize = 20.sp,
		color = Color.Gray,
		text = name,
	)
}
