package nl.ndat.tvlauncher.ui.tab.home.row

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import nl.ndat.tvlauncher.R
import nl.ndat.tvlauncher.data.model.ChannelType
import nl.ndat.tvlauncher.data.sqldelight.App
import nl.ndat.tvlauncher.data.sqldelight.Channel
import nl.ndat.tvlauncher.data.sqldelight.ChannelProgram
import nl.ndat.tvlauncher.ui.component.card.ChannelProgramCard
import nl.ndat.tvlauncher.util.modifier.ifElse

@Composable
fun ChannelProgramCardRow(
	modifier: Modifier = Modifier,
	channel: Channel,
	app: App,
	programs: List<ChannelProgram>,
) {
	val title = when (channel.type) {
		ChannelType.WATCH_NEXT -> stringResource(R.string.channel_watch_next)
		ChannelType.PREVIEW -> stringResource(
			R.string.channel_preview,
			app.displayName,
			channel.displayName
		)
	}

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
			contentKey = { program -> program?.id }
		) { program ->
			if (program != null) {
				Column(
					modifier = Modifier
						.padding(horizontal = 48.dp)
						.widthIn(max = 600.dp),
					verticalArrangement = Arrangement.spacedBy(6.dp),
				) {
					program.title?.let { title ->
						Text(
							text = title,
							style = MaterialTheme.typography.labelLarge,
							maxLines = 1,
						)
					}

					program.description?.let { description ->
						Text(
							text = description,
							style = MaterialTheme.typography.labelSmall,
							maxLines = 3,
						)
					}
				}
			}
		}
	}
}
