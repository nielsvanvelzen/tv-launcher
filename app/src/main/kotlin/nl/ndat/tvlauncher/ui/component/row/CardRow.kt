package nl.ndat.tvlauncher.ui.component.row

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.foundation.lazy.list.TvLazyListScope
import androidx.tv.foundation.lazy.list.TvLazyRow
import nl.ndat.tvlauncher.ui.theme.NoRippleTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardRow(
	title: String? = null,
	content: TvLazyListScope.() -> Unit,
) = Column(
	modifier = Modifier.focusGroup()
) {
	CompositionLocalProvider(
		LocalRippleTheme provides NoRippleTheme,
	) {
		if (title != null) {
			Text(
				text = title,
				fontSize = 18.sp,
				modifier = Modifier.padding(
					vertical = 4.dp,
					horizontal = 48.dp,
				)
			)
		}

		TvLazyRow(
			modifier = Modifier.fillMaxWidth(),
			contentPadding = PaddingValues(
				vertical = 16.dp,
				horizontal = 48.dp,
			),
			horizontalArrangement = Arrangement.spacedBy(14.dp),
		) {
			content()
		}
	}
}
