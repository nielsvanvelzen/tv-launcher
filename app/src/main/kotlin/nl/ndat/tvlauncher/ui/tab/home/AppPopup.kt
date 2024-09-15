package nl.ndat.tvlauncher.ui.tab.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Icon
import androidx.tv.material3.IconButton
import androidx.tv.material3.IconButtonDefaults

@Composable
fun AppPopup(
	isFirst: Boolean,
	isLast: Boolean,
	isFavorite: Boolean,
	onToggleFavorite: (favorite: Boolean) -> Unit,
	onMove: (relativePosition: Int) -> Unit,
) {
	Row(
		horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
	) {
		IconButton(
			enabled = !isFirst,
			modifier = Modifier.size(IconButtonDefaults.SmallButtonSize),
			onClick = { onMove(-1) },
		) {
			Icon(
				imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
				contentDescription = null,
				modifier = Modifier.size(IconButtonDefaults.SmallIconSize)
			)
		}

		IconButton(
			modifier = Modifier.size(IconButtonDefaults.SmallButtonSize),
			onClick = { onToggleFavorite(!isFavorite) }
		) {
			Icon(
				imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
				contentDescription = null,
				modifier = Modifier.size(IconButtonDefaults.SmallIconSize)
			)
		}

		IconButton(
			enabled = !isLast,
			modifier = Modifier.size(IconButtonDefaults.SmallButtonSize),
			onClick = { onMove(+1) },
		) {
			Icon(
				imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight, contentDescription = null,
				modifier = Modifier.size(IconButtonDefaults.SmallIconSize)
			)
		}
	}
}
