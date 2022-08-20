package nl.ndat.tvlauncher.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import nl.ndat.tvlauncher.data.model.ToolbarLocation

class PreferenceRepository {
	// TODO persist & allow user to change the value
	val toolbarLocation = MutableStateFlow(ToolbarLocation.END)
}
