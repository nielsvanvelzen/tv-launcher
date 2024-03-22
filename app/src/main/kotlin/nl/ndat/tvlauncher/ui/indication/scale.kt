package nl.ndat.tvlauncher.ui.indication

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private class FocusScaleIndicationNode(
	private val interactionSource: InteractionSource,
	private val focusedScale: Float,
) : Modifier.Node(), DrawModifierNode {
	val animatedScalePercent = Animatable(1f)

	private suspend fun scaleTo(scale: Float) {
		animatedScalePercent.animateTo(scale, spring())
	}

	override fun onAttach() {
		coroutineScope.launch {
			interactionSource.interactions.collectLatest { interaction ->
				when (interaction) {
					is FocusInteraction.Focus -> scaleTo(focusedScale)
					else -> scaleTo(1f)
				}
			}
		}
	}

	override fun ContentDrawScope.draw() {
		scale(
			scale = animatedScalePercent.value
		) {
			this@draw.drawContent()
		}
	}
}

class FocusScaleIndicationNodeFactory(private val focusedScale: Float) : IndicationNodeFactory {
	override fun create(interactionSource: InteractionSource): DelegatableNode =
		FocusScaleIndicationNode(interactionSource, focusedScale)

	override fun hashCode(): Int = -1

	override fun equals(other: Any?) = other === this
}

val FocusScaleIndication = FocusScaleIndicationNodeFactory(1.125f)
