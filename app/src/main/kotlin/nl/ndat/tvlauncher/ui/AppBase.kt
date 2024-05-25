package nl.ndat.tvlauncher.ui

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.ndat.tvlauncher.ui.indication.FocusScaleIndication
import nl.ndat.tvlauncher.ui.page.LauncherPage

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
		CompositionLocalProvider(
			LocalIndication provides FocusScaleIndication
		) {
			Surface {
				LauncherPage()
			}
		}
	}
}
