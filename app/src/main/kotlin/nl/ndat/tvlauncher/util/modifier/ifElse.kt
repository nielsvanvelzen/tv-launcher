package nl.ndat.tvlauncher.util.modifier

import androidx.compose.ui.Modifier

/**
 * Used to apply modifiers conditionally.
 */
fun Modifier.ifElse(
	condition: () -> Boolean,
	positiveModifier: Modifier,
	negativeModifier: Modifier = Modifier,
): Modifier = then(if (condition()) positiveModifier else negativeModifier)

/**
 * Used to apply modifiers conditionally.
 */
fun Modifier.ifElse(
	condition: Boolean,
	positiveModifier: Modifier,
	negativeModifier: Modifier = Modifier,
): Modifier = then(if (condition) positiveModifier else negativeModifier)
