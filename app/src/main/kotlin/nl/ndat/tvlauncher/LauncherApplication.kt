package nl.ndat.tvlauncher

import android.app.Application
import nl.ndat.tvlauncher.data.SharedDatabase
import nl.ndat.tvlauncher.data.repository.AppRepository
import nl.ndat.tvlauncher.data.repository.InputRepository
import nl.ndat.tvlauncher.data.repository.PreferenceRepository
import nl.ndat.tvlauncher.data.resolver.AppResolver
import nl.ndat.tvlauncher.data.resolver.InputResolver
import nl.ndat.tvlauncher.util.DefaultLauncherHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

private val launcherModule = module {
	single { DefaultLauncherHelper(get()) }

	single { AppRepository(get(), get(), get()) }
	single { AppResolver() }

	single { InputRepository(get(), get(), get()) }
	single { InputResolver() }

	single { PreferenceRepository() }
}

private val databaseModule = module {
	// Create database(s)
	single { SharedDatabase.build(get()) }

	// Add DAOs for easy access
	single { get<SharedDatabase>().appDao() }
	single { get<SharedDatabase>().inputDao() }
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
