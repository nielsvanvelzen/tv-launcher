package nl.ndat.tvlauncher.util

import android.content.pm.PackageManager
import android.content.pm.ResolveInfo

fun ResolveInfo.createLaunchIntent(packageManager: PackageManager) =
	packageManager.getLeanbackLaunchIntentForPackage(activityInfo.packageName)
		?: packageManager.getLaunchIntentForPackage(activityInfo.packageName)
