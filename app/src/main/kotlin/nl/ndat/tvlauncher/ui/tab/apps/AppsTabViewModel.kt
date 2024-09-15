package nl.ndat.tvlauncher.ui.tab.apps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import nl.ndat.tvlauncher.data.repository.AppRepository
import nl.ndat.tvlauncher.data.sqldelight.App

class AppsTabViewModel(
	private val appRepository: AppRepository,
) : ViewModel() {
	val apps = appRepository.getApps()
		.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

	fun favoriteApp(app: App, favorite: Boolean) {
		if (favorite) appRepository.favorite(app.id)
		else appRepository.unfavorite(app.id)
	}
}
