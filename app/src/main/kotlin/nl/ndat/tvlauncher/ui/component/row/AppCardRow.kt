package nl.ndat.tvlauncher.ui.component.row

import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import nl.ndat.tvlauncher.data.repository.AppRepository
import nl.ndat.tvlauncher.ui.component.card.AppCard
import nl.ndat.tvlauncher.util.ifElse
import org.koin.compose.koinInject

@Composable
fun AppCardRow(
	modifier: Modifier = Modifier,
) {
	val appRepository = koinInject<AppRepository>()
	val apps by appRepository.getApps().collectAsState(initial = emptyList())

	CardRow(
		modifier = modifier,
	) { childFocusRequester ->
		itemsIndexed(apps) { index, app ->
			AppCard(
				app = app,
				modifier = Modifier
					.ifElse(
						condition = index == 0,
						positiveModifier = Modifier.focusRequester(childFocusRequester)
					),
			)
		}
	}
}
