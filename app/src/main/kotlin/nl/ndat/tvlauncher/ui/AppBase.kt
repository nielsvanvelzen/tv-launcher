package nl.ndat.tvlauncher.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Shapes
import androidx.tv.material3.Surface
import androidx.tv.material3.darkColorScheme
import nl.ndat.tvlauncher.ui.screen.LauncherScreen

@Composable
fun AppBase() {
	MaterialTheme(
		colorScheme = darkColorScheme(
			background = Color.Black,
			surface = Color.Black,
		),
		shapes = Shapes(
			small = RoundedCornerShape(4.dp),
			medium = RoundedCornerShape(4.dp),
			large = RoundedCornerShape(8.dp)
		),
	) {
		Surface {
			LauncherScreen()
		}
	}
}
