package nl.ndat.tvlauncher.ui.component.card

import android.content.Intent
import android.view.KeyEvent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import org.koin.compose.rememberKoinInject
import nl.ndat.tvlauncher.R
import nl.ndat.tvlauncher.data.entity.App
import nl.ndat.tvlauncher.data.repository.AppRepository
import nl.ndat.tvlauncher.ui.indication.FocusScaleIndication
import nl.ndat.tvlauncher.util.createDrawable
import nl.ndat.tvlauncher.util.ifElse
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppCard(
	app: App,
	modifier: Modifier = Modifier,
) {
	val context = LocalContext.current
	val appRepository = rememberKoinInject<AppRepository>()
	val coroutineScope = rememberCoroutineScope()

	val image = remember(app.packageName) { app.createDrawable(context) }
	val interactionSource = remember { MutableInteractionSource() }
	val focused = interactionSource.interactions.collectAsState(initial = null).value is FocusInteraction.Focus
	val expanded = remember { mutableStateOf(false) }
	val scrollState = rememberScrollState()

	val launchIntentUri = app.launchIntentUriLeanback ?: app.launchIntentUriDefault

	Column(
		modifier = modifier
			.width(160.dp)
			.focusable(true, interactionSource)
			.indication(interactionSource, FocusScaleIndication(1.125f))
			.onKeyEvent(onKeyEvent = {
				if (it.nativeKeyEvent.action == KeyEvent.ACTION_UP)
					when(it.nativeKeyEvent.keyCode) {
						KeyEvent.KEYCODE_MENU -> {
							expanded.value = true;
							true
						}
						else -> false
					}
				else false;
			})
			.combinedClickable(
				enabled = launchIntentUri != null,
				onClick = {
					if (launchIntentUri != null) context.startActivity(
						Intent.parseUri(
							launchIntentUri,
							0
						)
					)
				},
				onLongClick = {
					expanded.value = !expanded.value;
				},
			),
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

		DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
        scrollState = scrollState
    ) {
			DropdownMenuItem(
				text = { Text(stringResource(if(app.isFavorite) R.string.favorites_remove else R.string.favorites_add)) },
				onClick = {
					expanded.value = false;
					coroutineScope.launch {
						appRepository.updateFavorite(app, !app.isFavorite)
					}
				},
			)
    }
	}
}
