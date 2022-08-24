package nl.ndat.tvlauncher.ui.component.card

import android.content.Intent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import nl.ndat.tvlauncher.R
import nl.ndat.tvlauncher.data.entity.ChannelProgram
import nl.ndat.tvlauncher.ui.theme.NoRippleTheme

@Composable
fun ChannelProgramCard(
	program: ChannelProgram,
) {
	val context = LocalContext.current
	var focused by remember { mutableStateOf(false) }
	val scale = animateFloatAsState(if (focused) 1.125f else 1.0f)

	CompositionLocalProvider(
		LocalRippleTheme provides NoRippleTheme
	) {
		Column(
			modifier = Modifier
				.width(90.dp * (program.posterArtAspectRatio?.floatValue ?: 1f))
				.scale(scale.value)
				.onFocusChanged { focused = it.hasFocus }
				.clickable(enabled = program.intentUri != null) {
					if (program.intentUri != null) context.startActivity(Intent.parseUri(program.intentUri, 0))
				},
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Box(
				modifier = Modifier
					.requiredHeight(90.dp)
					.aspectRatio(program.posterArtAspectRatio?.floatValue ?: 1f)
					.clip(MaterialTheme.shapes.medium),
			) {
				AsyncImage(
					modifier = Modifier
						.fillMaxSize()
						.background(colorResource(id = R.color.banner_background)),
					model = program.posterArtUri,
					contentDescription = program.packageName
				)
			}

			Text(
				modifier = Modifier.padding(8.dp),
				text = buildString {
					// TODO build a proper title based on type
					if (program.episodeTitle != null) append(program.episodeTitle)
					if (program.episodeNumber != null) append(program.episodeNumber)
					if (program.title != null) append(program.title)
					if (program.seasonNumber != null) append(program.seasonNumber)
				},
				fontSize = 13.sp,
				maxLines = 1,
				overflow = TextOverflow.Ellipsis,
			)
		}
	}
}
