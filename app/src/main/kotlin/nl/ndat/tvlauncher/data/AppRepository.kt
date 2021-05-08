package nl.ndat.tvlauncher.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData

class AppRepository(
	private val context: Context
) {
	private val applicationResolver = ApplicationResolver()

	fun getAllApps(): LiveData<List<AppInfo>> = liveData {
		val apps = applicationResolver.getApps(context)
		val inputs = applicationResolver.getInputs(context)

		emit(apps + inputs)
	}
}
