package nl.ndat.tvlauncher

import android.app.Application
import nl.ndat.tvlauncher.data.repository.AppRepository
import nl.ndat.tvlauncher.data.service.ApplicationResolverService
import nl.ndat.tvlauncher.util.DefaultLauncherHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

private val launcherModule = module {
	single { DefaultLauncherHelper(get()) }
	single { AppRepository(get(), get()) }
	single { ApplicationResolverService() }
}

@Suppress("unused")
class LauncherApplication : Application() {
	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidLogger(level = if (BuildConfig.DEBUG) Level.DEBUG else Level.INFO)
			androidContext(this@LauncherApplication)
			modules(launcherModule)
		}
	}
}
