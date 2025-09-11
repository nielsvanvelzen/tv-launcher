package nl.ndat.tvlauncher.ui.tab.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.PlayArrow
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
	isAutoStart: Boolean,
	onToggleAutoStart: (autoStart: Boolean) -> Unit,
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
				contentDescription = "向左移动",
				modifier = Modifier.size(IconButtonDefaults.SmallIconSize)
			)
		}

		IconButton(
			modifier = Modifier.size(IconButtonDefaults.SmallButtonSize),
			onClick = { onToggleFavorite(!isFavorite) }
		) {
			Icon(
				imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
				contentDescription = if (isFavorite) "取消收藏" else "添加到收藏",
				modifier = Modifier.size(IconButtonDefaults.SmallIconSize)
			)
		}

		IconButton(
			modifier = Modifier.size(IconButtonDefaults.SmallButtonSize),
			onClick = { onToggleAutoStart(!isAutoStart) }
		) {
			Icon(
				imageVector = if (isAutoStart) Icons.Default.PlayArrow else Icons.Outlined.PlayArrow,
				contentDescription = if (isAutoStart) "取消开机自启动" else "设置开机自启动",
				modifier = Modifier.size(IconButtonDefaults.SmallIconSize)
			)
		}

		IconButton(
			enabled = !isLast,
			modifier = Modifier.size(IconButtonDefaults.SmallButtonSize),
			onClick = { onMove(+1) },
		) {
			Icon(
				imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight, 
				contentDescription = "向右移动",
				modifier = Modifier.size(IconButtonDefaults.SmallIconSize)
			)
		}
	}
}
