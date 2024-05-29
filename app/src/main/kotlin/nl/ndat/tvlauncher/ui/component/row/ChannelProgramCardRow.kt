package nl.ndat.tvlauncher.ui.component.row

import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import nl.ndat.tvlauncher.R
import nl.ndat.tvlauncher.data.model.ChannelType
import nl.ndat.tvlauncher.data.sqldelight.App
import nl.ndat.tvlauncher.data.sqldelight.Channel
import nl.ndat.tvlauncher.data.sqldelight.ChannelProgram
import nl.ndat.tvlauncher.ui.component.card.ChannelProgramCard
import nl.ndat.tvlauncher.util.ifElse

@Composable
fun ChannelProgramCardRow(
	modifier: Modifier = Modifier,
	channel: Channel,
	app: App?,
	programs: List<ChannelProgram>,
) {
	val title = when (channel.type) {
		ChannelType.WATCH_NEXT -> stringResource(R.string.channel_watch_next)
		ChannelType.PREVIEW -> stringResource(
			R.string.channel_preview,
			app?.displayName ?: channel.packageName,
			channel.displayName
		)
	}

	if (programs.isNotEmpty()) {
		CardRow(
			title = title,
			modifier = modifier,
		) { childFocusRequester ->
			itemsIndexed(programs) { index, program ->
				ChannelProgramCard(
					program = program,
					modifier = Modifier
						.ifElse(
							condition = index == 0,
							positiveModifier = Modifier.focusRequester(childFocusRequester)
						),
				)
			}
		}
	}
}
