package nl.ndat.tvlauncher.ui.theme

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object NoRippleTheme : RippleTheme {
	@Composable
	override fun defaultColor(): Color = Color.Transparent

	@Composable
	override fun rippleAlpha(): RippleAlpha = RippleAlpha(
		draggedAlpha = 0f,
		focusedAlpha = 0f,
		hoveredAlpha = 0f,
		pressedAlpha = 0f,
	)
}
