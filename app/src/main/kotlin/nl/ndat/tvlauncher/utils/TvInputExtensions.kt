package nl.ndat.tvlauncher.utils

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.tv.TvContract
import android.media.tv.TvInputInfo
import androidx.core.content.ContextCompat
import nl.ndat.tvlauncher.R

fun TvInputInfo.loadPreferredLabel(context: Context): String {
	val customLabel = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
		loadCustomLabel(context)
	} else {
		null
	}

	val preferredLabel = customLabel ?: loadLabel(context)
	return preferredLabel.toString()
}

fun TvInputInfo.loadBanner(context: Context): Drawable = loadIcon(context) ?: when (type) {
	TvInputInfo.TYPE_TUNER -> R.drawable.banner_input // FIXME: Add banner
	TvInputInfo.TYPE_OTHER -> R.drawable.banner_input // FIXME: Add banner
	TvInputInfo.TYPE_COMPOSITE -> R.drawable.banner_composite
	TvInputInfo.TYPE_SVIDEO -> R.drawable.banner_svideo
	TvInputInfo.TYPE_SCART -> R.drawable.banner_scart
	TvInputInfo.TYPE_COMPONENT -> R.drawable.banner_composite
	TvInputInfo.TYPE_VGA -> R.drawable.banner_input // FIXME: Add banner
	TvInputInfo.TYPE_DVI -> R.drawable.banner_input // FIXME: Add banner
	TvInputInfo.TYPE_HDMI -> R.drawable.banner_hdmi
	TvInputInfo.TYPE_DISPLAY_PORT -> R.drawable.banner_input // FIXME: Add banner
	else -> R.drawable.banner_input
}.let { ContextCompat.getDrawable(context, it) as Drawable }

fun TvInputInfo.createSwitchIntent(): Intent = Intent(
	Intent.ACTION_VIEW,
	TvContract.buildChannelUriForPassthroughInput(id)
).apply {
	addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
}
