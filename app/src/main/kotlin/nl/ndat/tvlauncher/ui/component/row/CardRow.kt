package nl.ndat.tvlauncher.ui.component.row

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardRow(
	title: String? = null,
	content: LazyListScope.() -> Unit,
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

	LazyRow(
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
