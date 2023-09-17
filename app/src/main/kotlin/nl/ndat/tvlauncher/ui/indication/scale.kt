package nl.ndat.tvlauncher.ui.indication

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.scale

private class ScaleIndicationInstance(
	private val scale: Float,
) : IndicationInstance {
	override fun ContentDrawScope.drawIndication() {
		scale(scale) {
			this@drawIndication.drawContent()
		}
	}
}

class FocusScaleIndication(
	private val focusedScale: Float,
) : Indication {
	@Composable
	override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
		val interaction by interactionSource.interactions.collectAsState(initial = null)
		val currentScale = when (interaction) {
			is FocusInteraction.Focus -> focusedScale
			else -> 1.0f
		}

		val scale by animateFloatAsState(currentScale)
		return remember(scale) { ScaleIndicationInstance(scale) }
	}
}