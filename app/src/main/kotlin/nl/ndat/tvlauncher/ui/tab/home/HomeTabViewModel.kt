package nl.ndat.tvlauncher.ui.tab.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import nl.ndat.tvlauncher.data.repository.AppRepository
import nl.ndat.tvlauncher.data.repository.ChannelRepository
import nl.ndat.tvlauncher.data.sqldelight.Channel

class HomeTabViewModel(
	appRepository: AppRepository,
	private val channelRepository: ChannelRepository,
) : ViewModel() {
	val apps = appRepository.getApps()
		.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

	val channels = channelRepository.getChannels()
		.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

	fun channelPrograms(channel: Channel) = channelRepository.getProgramsByChannel(channel)
}
