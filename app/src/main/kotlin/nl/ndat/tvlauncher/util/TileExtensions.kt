package nl.ndat.tvlauncher.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.media.tv.TvInputManager
import androidx.core.content.getSystemService
import nl.ndat.tvlauncher.data.TileResolver
import nl.ndat.tvlauncher.data.entity.Tile

fun Tile.createDrawable(context: Context): Drawable = when (type) {
	Tile.TileType.INPUT -> {
		val tvInputManager = context.getSystemService<TvInputManager>()
		val tvInput = tvInputManager?.getTvInputInfo(id.removePrefix(TileResolver.INPUT_ID_PREFIX))
		tvInput.loadBanner(context)
	}

	Tile.TileType.APPLICATION -> {
		val packageManager = context.packageManager
		val intent = Intent.parseUri(uri, 0)

		try {
			packageManager.getActivityBanner(intent) ?: packageManager.getActivityIcon(intent)
		} catch (err: PackageManager.NameNotFoundException) {
			packageManager.defaultActivityIcon
		}
	}
}

fun Tile.getIntent(): Intent = Intent.parseUri(uri, 0)
