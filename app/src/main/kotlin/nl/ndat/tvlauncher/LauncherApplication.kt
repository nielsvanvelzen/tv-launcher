package nl.ndat.tvlauncher

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.DebugLogger
import nl.ndat.tvlauncher.data.DatabaseContainer
import nl.ndat.tvlauncher.data.repository.AppRepository
import nl.ndat.tvlauncher.data.repository.ChannelRepository
import nl.ndat.tvlauncher.data.repository.InputRepository
import nl.ndat.tvlauncher.data.resolver.AppResolver
import nl.ndat.tvlauncher.data.resolver.ChannelResolver
import nl.ndat.tvlauncher.data.resolver.InputResolver
import nl.ndat.tvlauncher.ui.screen.launcher.LauncherScreenViewModel
import nl.ndat.tvlauncher.ui.tab.apps.AppsTabViewModel
import nl.ndat.tvlauncher.ui.tab.home.HomeTabViewModel
import nl.ndat.tvlauncher.util.DefaultLauncherHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import timber.log.Timber

private val launcherModule = module {
	single { DefaultLauncherHelper(get()) }

	single { AppRepository(get(), get(), get()) }
	single { AppResolver() }

	single { ChannelRepository(get(), get(), get()) }
	single { ChannelResolver() }

	single { InputRepository(get(), get(), get()) }
	single { InputResolver() }

	viewModel { LauncherScreenViewModel() }
	viewModel { HomeTabViewModel(get(), get()) }
	viewModel { AppsTabViewModel(get()) }
}

private val databaseModule = module {
	// Create database(s)
	single { DatabaseContainer(get()) }
}

class LauncherApplication : Application(), ImageLoaderFactory {
	override fun onCreate() {
		super.onCreate()

		Timber.plant(Timber.DebugTree())

		startKoin {
			androidLogger(level = if (BuildConfig.DEBUG) Level.DEBUG else Level.INFO)
			androidContext(this@LauncherApplication)

			modules(launcherModule, databaseModule)
		}
	}

	override fun newImageLoader() = ImageLoader.Builder(this)
		.logger(DebugLogger())
		.build()
}
