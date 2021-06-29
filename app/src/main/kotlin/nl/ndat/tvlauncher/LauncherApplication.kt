package nl.ndat.tvlauncher

import android.app.Application
import nl.ndat.tvlauncher.data.SharedDatabase
import nl.ndat.tvlauncher.data.repository.TileRepository
import nl.ndat.tvlauncher.data.service.TileResolver
import nl.ndat.tvlauncher.util.DefaultLauncherHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

private val launcherModule = module {
	single { DefaultLauncherHelper(get()) }
	single { TileRepository(get(), get(), get()) }
	single { TileResolver() }
}

private val databaseModule = module {
	// Create database(s)
	single { SharedDatabase.build(get()) }

	// Add DAOs for easy access
	single { get<SharedDatabase>().virtualAppDao() }
}

@Suppress("unused")
class LauncherApplication : Application() {
	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidLogger(level = if (BuildConfig.DEBUG) Level.DEBUG else Level.INFO)
			androidContext(this@LauncherApplication)
			modules(launcherModule, databaseModule)
		}
	}
}
