package nl.ndat.tvlauncher

import android.app.Application
import nl.ndat.tvlauncher.data.SharedDatabase
import nl.ndat.tvlauncher.data.TileResolver
import nl.ndat.tvlauncher.data.repository.PreferenceRepository
import nl.ndat.tvlauncher.data.repository.TileRepository
import nl.ndat.tvlauncher.util.DefaultLauncherHelper
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

private val launcherModule = module {
	single { DefaultLauncherHelper(get()) }
	single { TileRepository(get(), get(), get(), get()) }
	single { PreferenceRepository() }
	single { TileResolver() }
}

private val databaseModule = module {
	// Create database(s)
	single { SharedDatabase.build(get()) }

	// Add DAOs for easy access
	single { get<SharedDatabase>().tileDao() }
	single { get<SharedDatabase>().collectionDao() }
}

@Suppress("unused")
class LauncherApplication : Application() {
	override fun onCreate() {
		super.onCreate()

		startKoin {
			// TODO: Re-enable when Koin is updated for Kotlin 1.6.x
			// androidLogger(level = if (BuildConfig.DEBUG) Level.DEBUG else Level.INFO)
			androidContext(this@LauncherApplication)
			modules(launcherModule, databaseModule)
		}
	}
}
