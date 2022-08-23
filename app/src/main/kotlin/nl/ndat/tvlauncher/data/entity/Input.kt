package nl.ndat.tvlauncher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import nl.ndat.tvlauncher.data.model.InputType

@Entity(
	tableName = "input",
)
data class Input(
	@PrimaryKey var id: String,
	val inputId: String,

	val displayName: String,
	val packageName: String?,
	val type: InputType,

	val switchIntentUri: String?,
)
