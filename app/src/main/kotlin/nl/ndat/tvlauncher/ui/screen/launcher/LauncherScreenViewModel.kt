package nl.ndat.tvlauncher.ui.screen.launcher

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LauncherScreenViewModel : ViewModel() {
	private val _tabIndex = MutableStateFlow(0)
	val tabIndex = _tabIndex.asStateFlow()

	fun setTabIndex(index: Int) {
		_tabIndex.value = index
	}
}
