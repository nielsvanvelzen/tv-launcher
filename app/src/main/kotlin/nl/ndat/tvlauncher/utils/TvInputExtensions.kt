package nl.ndat.tvlauncher.utils

import android.content.Context
import android.media.tv.TvInputInfo

fun TvInputInfo.loadPreferredLabel(context: Context): String {
	val customLabel = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
		loadCustomLabel(context)
	} else {
		null
	}

	val preferredLabel = customLabel ?: loadLabel(context)
	return preferredLabel.toString()
}

