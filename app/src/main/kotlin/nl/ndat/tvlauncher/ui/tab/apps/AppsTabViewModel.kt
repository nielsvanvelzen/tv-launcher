package nl.ndat.tvlauncher.ui.tab.apps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import nl.ndat.tvlauncher.data.repository.AppRepository

class AppsTabViewModel(
	appRepository: AppRepository,
) : ViewModel() {
	val apps = appRepository.getApps()
		.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}
