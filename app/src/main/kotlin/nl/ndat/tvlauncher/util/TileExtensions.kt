package nl.ndat.tvlauncher.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.media.tv.TvInputManager
import androidx.core.content.getSystemService
import nl.ndat.tvlauncher.data.entity.App
import nl.ndat.tvlauncher.data.entity.Input

fun App.createDrawable(context: Context): Drawable {
	val packageManager = context.packageManager
	val intent = Intent.parseUri(launchIntentUriLeanback ?: launchIntentUriDefault, 0)

	return try {
		packageManager.getActivityBanner(intent) ?: packageManager.getActivityIcon(intent)
	} catch (err: PackageManager.NameNotFoundException) {
		packageManager.defaultActivityIcon
	}
}

fun Input.createDrawable(context: Context): Drawable {
	val tvInputManager = context.getSystemService<TvInputManager>()
	val tvInput = tvInputManager?.getTvInputInfo(inputId)
	return tvInput.loadBanner(context)
}
