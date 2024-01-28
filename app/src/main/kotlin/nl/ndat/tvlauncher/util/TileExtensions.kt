package nl.ndat.tvlauncher.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import nl.ndat.tvlauncher.data.sqldelight.App

fun App.createDrawable(context: Context): Drawable {
	val packageManager = context.packageManager
	val intent = Intent.parseUri(launchIntentUriLeanback ?: launchIntentUriDefault, 0)

	return try {
		packageManager.getActivityBanner(intent) ?: packageManager.getActivityIcon(intent)
	} catch (err: PackageManager.NameNotFoundException) {
		packageManager.defaultActivityIcon
	}
}
