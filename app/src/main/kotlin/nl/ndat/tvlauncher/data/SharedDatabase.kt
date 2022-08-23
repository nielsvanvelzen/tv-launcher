package nl.ndat.tvlauncher.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import nl.ndat.tvlauncher.data.dao.AppDao
import nl.ndat.tvlauncher.data.dao.ChannelDao
import nl.ndat.tvlauncher.data.dao.ChannelProgramDao
import nl.ndat.tvlauncher.data.dao.InputDao
import nl.ndat.tvlauncher.data.entity.App
import nl.ndat.tvlauncher.data.entity.Channel
import nl.ndat.tvlauncher.data.entity.ChannelProgram
import nl.ndat.tvlauncher.data.entity.Input

/**
 * Primary database of the app.
 */
@Database(
	version = 1,
	entities = [
		App::class,
		Channel::class,
		ChannelProgram::class,
		Input::class,
	],
)
abstract class SharedDatabase : RoomDatabase() {
	companion object {
		const val name = "shared"

		fun build(context: Context) = Room
			.databaseBuilder(context, SharedDatabase::class.java, name)
			// TODO add proper migrations on release
			.fallbackToDestructiveMigration()
			.build()
	}

	abstract fun appDao(): AppDao
	abstract fun channelDao(): ChannelDao
	abstract fun channelProgramDao(): ChannelProgramDao
	abstract fun inputDao(): InputDao
}
