package nl.ndat.tvlauncher.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import androidx.tv.material3.Glow
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import androidx.tv.material3.SurfaceDefaults

private class AlignedPositionProvider : PopupPositionProvider {
	override fun calculatePosition(
		anchorBounds: IntRect,
		windowSize: IntSize,
		layoutDirection: LayoutDirection,
		popupContentSize: IntSize
	): IntOffset {
		val anchorAlignmentPoint = Alignment.BottomCenter.align(
			IntSize.Zero,
			anchorBounds.size,
			layoutDirection
		)

		val popupAlignmentPoint = -Alignment.BottomCenter.align(
			IntSize.Zero,
			IntSize(popupContentSize.width, 0),
			layoutDirection
		)

		return anchorBounds.topLeft + anchorAlignmentPoint + popupAlignmentPoint
	}
}

@Composable
fun PopupContainer(
	visible: Boolean,
	onDismiss: () -> Unit,
	content: @Composable () -> Unit,
	popupContent: @Composable () -> Unit
) {
	Box {
		content()

		if (visible) {
			Popup(
				popupPositionProvider = AlignedPositionProvider(),
				onDismissRequest = onDismiss,
				properties = PopupProperties(
					focusable = true,
					dismissOnBackPress = true,
					dismissOnClickOutside = true,
				)
			) {
				Surface(
					colors = SurfaceDefaults.colors(
						containerColor = MaterialTheme.colorScheme.secondaryContainer,
						contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
					),
					shape = RoundedCornerShape(8.dp),
					glow = Glow(Color.White, 6.dp),
				) {
					Box(modifier = Modifier.padding(8.dp)) {
						popupContent()
					}
				}
			}
		}
	}
}
