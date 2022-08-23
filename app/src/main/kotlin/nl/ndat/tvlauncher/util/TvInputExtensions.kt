package nl.ndat.tvlauncher.util

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.tv.TvInputInfo
import androidx.core.content.ContextCompat
import androidx.tvprovider.media.tv.TvContractCompat
import nl.ndat.tvlauncher.R
import nl.ndat.tvlauncher.data.model.InputType

fun TvInputInfo.loadPreferredLabel(context: Context): String {
	val customLabel = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
		loadCustomLabel(context)
	} else {
		null
	}

	val preferredLabel = customLabel ?: loadLabel(context)
	return preferredLabel.toString()
}

fun TvInputInfo.getInputType() = when (this.type) {
	TvInputInfo.TYPE_TUNER -> InputType.TUNER
	TvInputInfo.TYPE_OTHER -> InputType.OTHER
	TvInputInfo.TYPE_COMPOSITE -> InputType.COMPOSITE
	TvInputInfo.TYPE_SVIDEO -> InputType.SVIDEO
	TvInputInfo.TYPE_SCART -> InputType.SCART
	TvInputInfo.TYPE_COMPONENT -> InputType.COMPONENT
	TvInputInfo.TYPE_VGA -> InputType.VGA
	TvInputInfo.TYPE_DVI -> InputType.DVI
	TvInputInfo.TYPE_HDMI -> InputType.HDMI
	TvInputInfo.TYPE_DISPLAY_PORT -> InputType.DISPLAY_PORT
	else -> InputType.OTHER
}

fun TvInputInfo?.loadBanner(context: Context): Drawable = this?.loadIcon(context) ?: when (this?.type) {
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
	TvContractCompat.buildChannelUriForPassthroughInput(id)
).apply {
	addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
}
