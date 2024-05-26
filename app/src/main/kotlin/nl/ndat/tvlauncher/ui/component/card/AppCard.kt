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
import nl.ndat.tvlauncher.data.sqldelight.App
import nl.ndat.tvlauncher.util.createDrawable
import nl.ndat.tvlauncher.util.ifElse

@Composable
fun AppCard(
	app: App,
	modifier: Modifier = Modifier,
	baseHeight: Dp = 90.dp,
) {
	val context = LocalContext.current
	val image = remember { app.createDrawable(context) }
	val interactionSource = remember { MutableInteractionSource() }
	val focused by interactionSource.collectIsFocusedAsState()

	val launchIntentUri = app.launchIntentUriLeanback ?: app.launchIntentUriDefault

	StandardCardContainer(
		modifier = modifier
			.width(baseHeight * (16f / 9f)),
		interactionSource = interactionSource,
		title = {
			Text(
				text = app.displayName,
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
					.aspectRatio(16f / 9f),
				interactionSource = interactionSource,
				onClick = {
					if (launchIntentUri != null) {
						context.startActivity(Intent.parseUri(launchIntentUri, 0))
					}
				},
			) {
				AsyncImage(
					modifier = Modifier.fillMaxSize(),
					model = image,
					contentDescription = app.displayName,
				)
			}
		}
	)
}
