package nl.ndat.tvlauncher.ui.component.card

import android.content.Intent
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.StandardCardContainer
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import nl.ndat.tvlauncher.data.sqldelight.ChannelProgram
import nl.ndat.tvlauncher.util.ifElse

@Composable
fun ChannelProgramCard(
	program: ChannelProgram,
	modifier: Modifier = Modifier,
	baseHeight: Dp = 110.dp,
) {
	val context = LocalContext.current
	val interactionSource = remember { MutableInteractionSource() }
	val focused by interactionSource.collectIsFocusedAsState()

	StandardCardContainer(
		modifier = modifier
			.width(baseHeight * (program.posterArtAspectRatio?.floatValue ?: 1f)),
		interactionSource = interactionSource,
		title = {
			Text(
				text = buildString {
					// TODO build a proper title based on type
					if (program.episodeTitle != null) append(program.episodeTitle)
					if (program.episodeNumber != null) append(program.episodeNumber)
					if (program.title != null) append(program.title)
					if (program.seasonNumber != null) append(program.seasonNumber)
				},
				maxLines = 1,
				overflow = TextOverflow.Clip,
				softWrap = false,
				style = MaterialTheme.typography.bodyMedium.copy(
					fontWeight = FontWeight.SemiBold
				),
				modifier = Modifier
					.ifElse(
						focused,
						Modifier.basicMarquee(
							iterations = Int.MAX_VALUE,
							initialDelayMillis = 0,
						),
					)
					.padding(top = 6.dp),
			)
		},
		imageCard = { _ ->
			Card(
				modifier = Modifier
					.height(baseHeight)
					.aspectRatio(program.posterArtAspectRatio?.floatValue ?: 1f),
				interactionSource = interactionSource,
				onClick = {
					if (program.intentUri != null) {
						context.startActivity(Intent.parseUri(program.intentUri, 0))
					}
				},
			) {
				AsyncImage(
					modifier = Modifier.fillMaxSize(),
					model = program.posterArtUri,
					contentDescription = null,
				)
			}
		}
	)
}
