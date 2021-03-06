package nl.ndat.tvlauncher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
	tableName = "tile",
)
data class Tile(
	/**
	 * Tile id.
	 */
	@PrimaryKey var id: String,

	/**
	 * Tile type.
	 */
	val type: TileType,

	/**
	 * Id of the package containing this application or input.
	 */
	val packageId: String?,

	/**
	 * Intent uri.
	 */
	val uri: String?,

	/**
	 * The name for this tile from the operating system.
	 */
	val name: String,
) {
	/**
	 * Type of tile, used to determine behavior for resolving drawables or launching the intent.
	 */
	enum class TileType {
		/**
		 * Android app installed on device.
		 */
		APPLICATION,

		/**
		 * Video input like a HDMI port.
		 */
		INPUT,
	}

}
