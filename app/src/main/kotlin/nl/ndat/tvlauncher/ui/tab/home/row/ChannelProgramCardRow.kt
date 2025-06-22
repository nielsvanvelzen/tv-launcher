package nl.ndat.tvlauncher.ui.tab.home.row

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import nl.ndat.tvlauncher.data.sqldelight.App
import nl.ndat.tvlauncher.data.sqldelight.ChannelProgram
import nl.ndat.tvlauncher.ui.component.card.ChannelProgramCard
import nl.ndat.tvlauncher.util.modifier.ifElse

@Composable
fun ChannelProgramCardRow(
	modifier: Modifier = Modifier,
	title: String,
	programs: List<ChannelProgram>,
	app: App?,
) {
	var focusedProgram by remember { mutableStateOf<ChannelProgram?>(null) }

	if (programs.isNotEmpty()) {
		CardRow(
			title = title,
			modifier = modifier,
		) { childFocusRequester ->
			itemsIndexed(
				items = programs,
				key = { _, program -> program.id },
			) { index, program ->
				Box(
					modifier = Modifier
						.animateItem()
				) {
					ChannelProgramCard(
						program = program,
						modifier = Modifier
							.ifElse(
								condition = index == 0,
								positiveModifier = Modifier.focusRequester(childFocusRequester)
							)
							.onFocusChanged { state ->
								if (state.hasFocus && focusedProgram != program) focusedProgram = program
								else if (!state.hasFocus && focusedProgram == program) focusedProgram = null
							},
					)
				}
			}
		}

		AnimatedContent(
			targetState = focusedProgram,
			label = "ChannelProgramCardRow",
			contentKey = { program -> program?.id },
			transitionSpec = {
				if (initialState == null && targetState != null) slideInVertically() togetherWith fadeOut()
				else if (initialState != null && targetState == null) fadeIn() togetherWith slideOutVertically()
				else fadeIn() togetherWith fadeOut()
			}
		) { program ->
			if (program != null) {
				ChannelProgramCardDetails(program, app)
			}
		}
	}
}
