package nl.ndat.tvlauncher.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
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
import nl.ndat.tvlauncher.data.entity.Tile
import nl.ndat.tvlauncher.ui.theme.NoRippleTheme
import nl.ndat.tvlauncher.util.createDrawable
import nl.ndat.tvlauncher.util.getIntent

@Composable
fun TileCard(
	tile: Tile,
) {
	val context = LocalContext.current
	val image = remember { tile.createDrawable(context) }
	var focused by remember { mutableStateOf(false) }
	val scale = animateFloatAsState(if (focused) 1.125f else 1.0f)

	CompositionLocalProvider(
		LocalRippleTheme provides NoRippleTheme
	) {
		Column(
			modifier = Modifier
				.width(160.dp)
				.scale(scale.value)
				.onFocusChanged { focused = it.hasFocus }
				.clickable(enabled = tile.uri != null) {
					if (tile.uri != null) context.startActivity(tile.getIntent())
				},
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Box(
				modifier = Modifier
					.requiredSize(160.dp, 90.dp)
					.clip(MaterialTheme.shapes.medium),
			) {
				AsyncImage(
					modifier = Modifier
						.fillMaxSize()
						.background(colorResource(id = R.color.banner_background)),
					model = image,
					contentDescription = tile.name
				)
			}

			Text(
				modifier = Modifier.padding(8.dp),
				text = tile.name,
				fontSize = 13.sp,
				maxLines = 1,
				overflow = TextOverflow.Ellipsis,
			)
		}
	}
}
