package nl.ndat.tvlauncher.ui.component.card

import android.content.Intent
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.StandardCardContainer
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import nl.ndat.tvlauncher.data.sqldelight.App
import nl.ndat.tvlauncher.ui.component.PopupContainer
import nl.ndat.tvlauncher.util.createDrawable
import nl.ndat.tvlauncher.util.ifElse

@Composable
fun AppCard(
	app: App,
	modifier: Modifier = Modifier,
	baseHeight: Dp = 90.dp,
	popupContent: (@Composable () -> Unit)? = null,
) {
	val context = LocalContext.current
	val image = remember(app.id) { app.createDrawable(context) }
	var imagePrimaryColor by remember { mutableStateOf<Color?>(null) }
	val interactionSource = remember { MutableInteractionSource() }
	val focused by interactionSource.collectIsFocusedAsState()

	val launchIntentUri = app.launchIntentUriLeanback ?: app.launchIntentUriDefault

	var menuVisible by remember { mutableStateOf(false) }

	PopupContainer(
		visible = menuVisible && popupContent != null,
		onDismiss = { menuVisible = false },
		content = {
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
						border = CardDefaults.border(
							focusedBorder = Border(
								border = BorderStroke(2.dp, imagePrimaryColor ?: MaterialTheme.colorScheme.border),
							)
						),
						onClick = {
							if (launchIntentUri != null) {
								context.startActivity(Intent.parseUri(launchIntentUri, 0))
							}
						},
						onLongClick = {
							if (popupContent != null) {
								menuVisible = true
							}
						}
					) {
						AsyncImage(
							modifier = Modifier.fillMaxSize(),
							model = image,
							contentDescription = app.displayName,
							onSuccess = {
								val palette = Palette.from(it.result.drawable.toBitmap()).generate()
								imagePrimaryColor = palette.mutedSwatch?.rgb?.let(::Color)
							}
						)
					}
				}
			)
		},
		popupContent = {
			if (popupContent != null) popupContent()
		}
	)
}
