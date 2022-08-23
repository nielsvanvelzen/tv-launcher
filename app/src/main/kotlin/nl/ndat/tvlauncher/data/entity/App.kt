package nl.ndat.tvlauncher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
	tableName = "app",
)
data class App(
	@PrimaryKey var id: String,

	val displayName: String,
	val packageName: String,

	val launchIntentUriDefault: String?,
	val launchIntentUriLeanback: String?,
)
