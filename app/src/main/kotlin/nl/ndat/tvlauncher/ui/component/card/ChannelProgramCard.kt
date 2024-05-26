package nl.ndat.tvlauncher.ui.component.card

import android.content.Intent
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import coil.compose.AsyncImage
import nl.ndat.tvlauncher.data.sqldelight.ChannelProgram

@Composable
fun ChannelProgramCard(
	program: ChannelProgram,
	modifier: Modifier = Modifier,
	baseHeight: Dp = 100.dp,
) {
	val context = LocalContext.current

	Card(
		modifier = modifier
			.height(baseHeight)
			.aspectRatio(program.posterArtAspectRatio?.floatValue ?: 1f),
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
			contentScale = ContentScale.Crop
		)
	}
}
