package nl.ndat.tvlauncher.ui.page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.ndat.tvlauncher.data.entity.CollectionTile
import nl.ndat.tvlauncher.data.repository.TileRepository
import nl.ndat.tvlauncher.ui.component.TileCard
import nl.ndat.tvlauncher.ui.toolbar.Toolbar
import org.koin.androidx.compose.inject

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LauncherPage() {
	MaterialTheme(
		colors = darkColors(
			background = Color.Black,
			surface = Color.Black,
		),
		shapes = Shapes(
			small = RoundedCornerShape(4.dp),
			medium = RoundedCornerShape(4.dp),
			large = RoundedCornerShape(8.dp)
		),
	) {
		CompositionLocalProvider(
			// Disable overscroll
			LocalOverscrollConfiguration provides null
		) {
			Surface(
				modifier = Modifier.padding(vertical = 27.dp)
			) {
				Column(
					verticalArrangement = Arrangement.spacedBy(8.dp)
				) {
					Toolbar(
						modifier = Modifier.padding(horizontal = 48.dp)
					)
					AppGrid(CollectionTile.CollectionType.HOME)
				}
			}
		}
	}
}

@Composable
fun AppGrid(
	collection: CollectionTile.CollectionType,
) {
	val tileRepository: TileRepository by inject()
	val tiles by tileRepository.getCollectionTiles(collection).collectAsState(initial = emptyList())

	LazyVerticalGrid(
		modifier = Modifier
			.fillMaxSize(),
		contentPadding = PaddingValues(
			vertical = 16.dp,
			horizontal = 48.dp,
		),
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp),
		columns = GridCells.Adaptive(160.dp)
	) {
		items(tiles) { tile ->
			TileCard(
				tile = tile,
			)
		}
	}
}

