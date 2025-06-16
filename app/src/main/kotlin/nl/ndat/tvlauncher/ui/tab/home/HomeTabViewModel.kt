package nl.ndat.tvlauncher.ui.tab.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nl.ndat.tvlauncher.data.repository.AppRepository
import nl.ndat.tvlauncher.data.repository.ChannelRepository
import nl.ndat.tvlauncher.data.sqldelight.App
import nl.ndat.tvlauncher.data.sqldelight.Channel

class HomeTabViewModel(
	private val appRepository: AppRepository,
	private val channelRepository: ChannelRepository,
) : ViewModel() {
	val apps = appRepository.getFavoriteApps()
		.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

	val channels = channelRepository.getFavoriteAppChannels()
		.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

	val watchNextPrograms = channelRepository.getWatchNextPrograms()
		.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

	fun channelPrograms(channel: Channel) = channelRepository.getProgramsByChannel(channel)

	fun favoriteApp(app: App, favorite: Boolean) = viewModelScope.launch {
		// Return is state is already satisfied
		if ((app.favoriteOrder != null) == favorite) return@launch

		if (favorite) appRepository.favorite(app.id)
		else appRepository.unfavorite(app.id)
	}

	fun setFavoriteOrder(app: App, order: Int) = viewModelScope.launch {
		// Make sure app is favorite first
		if (app.favoriteOrder == null) appRepository.favorite(app.id)
		appRepository.updateFavoriteOrder(app.id, order)
	}
}
