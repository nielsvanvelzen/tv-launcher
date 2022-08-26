package nl.ndat.tvlauncher.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.ndat.tvlauncher.ui.page.LauncherPage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppBase() {
	MaterialTheme(
		colors = darkColors(
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
			LocalOverscrollConfiguration provides null,
		) {
			Surface {
				LauncherPage()
			}
		}
	}
}
