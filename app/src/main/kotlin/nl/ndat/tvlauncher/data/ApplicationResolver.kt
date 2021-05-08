package nl.ndat.tvlauncher.data

import android.content.Context
import android.content.Intent
import android.media.tv.TvInputManager
import androidx.core.content.getSystemService
import nl.ndat.tvlauncher.utils.createSwitchIntent
import nl.ndat.tvlauncher.utils.loadBanner
import nl.ndat.tvlauncher.utils.loadPreferredLabel

class ApplicationResolver {
	fun getApps(context: Context): List<AppInfo> {
		val intent = Intent(Intent.ACTION_MAIN, null).apply {
			addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER)
		}

		val packageManager = context.packageManager
		val activities = packageManager.queryIntentActivities(intent, 0)

		// Add leanback apps
		return activities.sortedBy {
			it.activityInfo.loadLabel(packageManager).toString()
		}.map { resolveInfo ->
			val banner = resolveInfo.activityInfo.loadBanner(packageManager)
				?: resolveInfo.activityInfo.loadIcon(packageManager)

			val appIntent =
				packageManager.getLeanbackLaunchIntentForPackage(resolveInfo.activityInfo.packageName)
					?: packageManager.getLaunchIntentForPackage(resolveInfo.activityInfo.packageName)

			AppInfo(
				label = resolveInfo.activityInfo.loadLabel(packageManager).toString(),
				banner = banner,
				intent = appIntent,
			)
		}
	}

	fun getInputs(context: Context): List<AppInfo> {
		val tvInputManager = context.getSystemService<TvInputManager>()
		val tvInputs = tvInputManager?.tvInputList.orEmpty()

		return tvInputs.map { tvInputInfo ->
			AppInfo(
				label = tvInputInfo.loadPreferredLabel(context),
				banner = tvInputInfo.loadBanner(context),
				intent = tvInputInfo.createSwitchIntent()
			)
		}
	}
}
