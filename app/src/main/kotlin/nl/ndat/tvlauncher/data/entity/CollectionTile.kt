package nl.ndat.tvlauncher.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
	tableName = "collection_tile",
	primaryKeys = ["collectionType", "tileId"],
	foreignKeys = [
		ForeignKey(
			entity = Tile::class,
			parentColumns = ["id"],
			childColumns = ["tileId"],
			onDelete = ForeignKey.CASCADE,
		)
	]
)
data class CollectionTile(
	val tileId: String,
	val collectionType: CollectionType,
	val order: Int,
) {
	enum class CollectionType {
		HOME,
		DRAWER
	}
}

