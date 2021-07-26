package nl.ndat.tvlauncher.data.repository

import androidx.lifecycle.MutableLiveData
import nl.ndat.tvlauncher.data.model.ToolbarLocation

class PreferenceRepository {
	// TODO persist & allow user to change the value
	val toolbarLocation = MutableLiveData(ToolbarLocation.END)
}
