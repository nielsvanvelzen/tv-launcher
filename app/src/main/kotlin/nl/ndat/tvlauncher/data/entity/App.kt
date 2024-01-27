package nl.ndat.tvlauncher.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
	tableName = "app",
)
data class App(
	@PrimaryKey var id: String,

	val displayName: String,
	@ColumnInfo(index = true) val packageName: String,

	val launchIntentUriDefault: String?,
	val launchIntentUriLeanback: String?,

	@ColumnInfo(defaultValue = "false") val isFavorite: Boolean,
)

data class AppSystemDetails(
	var id: String,
	val displayName: String,
	val packageName: String,
	val launchIntentUriDefault: String?,
	val launchIntentUriLeanback: String?,
)

data class AppFavorite(
	var id: String,
	val isFavorite: Boolean,
)
