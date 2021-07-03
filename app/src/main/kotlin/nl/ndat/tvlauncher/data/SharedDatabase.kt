package nl.ndat.tvlauncher.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import nl.ndat.tvlauncher.data.dao.CollectionDao
import nl.ndat.tvlauncher.data.dao.TileDao
import nl.ndat.tvlauncher.data.entity.CollectionTile
import nl.ndat.tvlauncher.data.entity.Tile

/**
 * Primary database of the app.
 */
@Database(
	version = 1,
	entities = [
		Tile::class,
		CollectionTile::class
	],
)
abstract class SharedDatabase : RoomDatabase() {
	companion object {
		const val name = "shared"

		fun build(context: Context) = Room
			.databaseBuilder(context, SharedDatabase::class.java, name)
			.build()
	}

	abstract fun tileDao(): TileDao
	abstract fun collectionDao(): CollectionDao
}
