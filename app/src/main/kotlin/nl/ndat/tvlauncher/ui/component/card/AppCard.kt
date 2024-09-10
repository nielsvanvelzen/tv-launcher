package nl.ndat.tvlauncher.ui.component.card

import android.content.Intent
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import androidx.tv.material3.Glow
import androidx.tv.material3.Icon
import androidx.tv.material3.IconButton
import androidx.tv.material3.IconButtonDefaults
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
) {
	val context = LocalContext.current
	val image = remember { app.createDrawable(context) }
	var imagePrimaryColor by remember { mutableStateOf<Color?>(null) }
	val interactionSource = remember { MutableInteractionSource() }
	val focused by interactionSource.collectIsFocusedAsState()

	val launchIntentUri = app.launchIntentUriLeanback ?: app.launchIntentUriDefault

	var menuVisible by remember { mutableStateOf(false) }

	PopupContainer(
		visible = menuVisible,
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
						glow = CardDefaults.glow(
							focusedGlow = Glow(
								elevationColor = imagePrimaryColor ?: MaterialTheme.colorScheme.border,
								elevation = 10.dp
							)
						),
						border = CardDefaults.border(focusedBorder = Border.None),
						onClick = {
							if (launchIntentUri != null) {
								context.startActivity(Intent.parseUri(launchIntentUri, 0))
							}
						},
						onLongClick = {
							// TODO Menu is not finalized
							// menuVisible = true
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
			Row(
				horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
			) {
				IconButton(modifier = Modifier.size(IconButtonDefaults.SmallButtonSize), onClick = {}) {
					Icon(
						imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
						contentDescription = null,
						modifier = Modifier.size(IconButtonDefaults.SmallIconSize)
					)
				}

				IconButton(modifier = Modifier.size(IconButtonDefaults.SmallButtonSize), onClick = {}) {
					Icon(
						imageVector = Icons.Default.Favorite,
						contentDescription = null,
						modifier = Modifier.size(IconButtonDefaults.SmallIconSize)
					)
				}

				IconButton(modifier = Modifier.size(IconButtonDefaults.SmallButtonSize), onClick = {}) {
					Icon(
						imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight, contentDescription = null,
						modifier = Modifier.size(IconButtonDefaults.SmallIconSize)
					)
				}
			}
		}
	)
}
