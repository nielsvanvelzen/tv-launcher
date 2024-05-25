package nl.ndat.tvlauncher.ui.component.card

import android.content.Intent
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import nl.ndat.tvlauncher.R
import nl.ndat.tvlauncher.data.sqldelight.App
import nl.ndat.tvlauncher.util.createDrawable
import nl.ndat.tvlauncher.util.ifElse

@Composable
fun AppCard(
	app: App,
	modifier: Modifier = Modifier,
) {
	val context = LocalContext.current
	val image = remember { app.createDrawable(context) }
	val interactionSource = remember { MutableInteractionSource() }
	val focused by interactionSource.collectIsFocusedAsState()

	val launchIntentUri = app.launchIntentUriLeanback ?: app.launchIntentUriDefault

	Column(
		modifier = modifier
			.width(160.dp)
			.focusable(true, interactionSource)
			.indication(interactionSource, LocalIndication.current)
			.clickable(enabled = launchIntentUri != null) {
				if (launchIntentUri != null) context.startActivity(
					Intent.parseUri(
						launchIntentUri,
						0
					)
				)
			},
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Box(
			modifier = Modifier
				.requiredSize(160.dp, 90.dp),
		) {
			AsyncImage(
				modifier = Modifier
					.fillMaxSize()
					.clip(MaterialTheme.shapes.medium)
					.background(colorResource(id = R.color.banner_background)),
				model = image,
				contentDescription = app.displayName
			)
		}

		Text(
			modifier = Modifier
				.padding(3.dp, 6.dp)
				.ifElse(
					focused,
					Modifier.basicMarquee(
						iterations = Int.MAX_VALUE,
						initialDelayMillis = 0,
					),
				),
			text = app.displayName,
			fontSize = 12.sp,
			maxLines = 1,
			overflow = TextOverflow.Clip,
			softWrap = false,
		)
	}
}
