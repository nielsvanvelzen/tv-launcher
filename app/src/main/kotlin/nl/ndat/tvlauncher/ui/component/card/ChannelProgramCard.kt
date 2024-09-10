package nl.ndat.tvlauncher.ui.component.card

import android.content.Intent
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import androidx.tv.material3.Border
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.Glow
import androidx.tv.material3.MaterialTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest
import nl.ndat.tvlauncher.data.sqldelight.ChannelProgram

@Composable
fun ChannelProgramCard(
	program: ChannelProgram,
	modifier: Modifier = Modifier,
	baseHeight: Dp = 100.dp,
) {
	val context = LocalContext.current
	var imagePrimaryColor by remember { mutableStateOf<Color?>(null) }

	Card(
		modifier = modifier
			.height(baseHeight)
			.aspectRatio(program.posterArtAspectRatio?.floatValue ?: 1f),
		glow = CardDefaults.glow(
			focusedGlow = Glow(
				elevationColor = imagePrimaryColor ?: MaterialTheme.colorScheme.border,
				elevation = 10.dp
			)
		),
		border = CardDefaults.border(focusedBorder = Border.None),
		onClick = {
			if (program.intentUri != null) {
				context.startActivity(Intent.parseUri(program.intentUri, 0))
			}
		},
	) {
		AsyncImage(
			modifier = Modifier.fillMaxSize(),
			model = ImageRequest.Builder(LocalContext.current)
				.data(program.posterArtUri)
				.allowHardware(false)
				.build(),
			contentDescription = null,
			contentScale = ContentScale.Crop,
			onSuccess = {
				val palette = Palette.from(it.result.drawable.toBitmap()).generate()
				imagePrimaryColor = palette.dominantSwatch?.rgb?.let(::Color)
			}
		)
	}
}
