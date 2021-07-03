package nl.ndat.tvlauncher.data.entity

import androidx.room.Embedded

data class RichCollectionTile(
	@Embedded val collectionTile: CollectionTile,
	@Embedded val tile: Tile,
)
