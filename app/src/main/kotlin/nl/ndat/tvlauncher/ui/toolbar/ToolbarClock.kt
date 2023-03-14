package nl.ndat.tvlauncher.ui.toolbar

import android.content.Intent
import android.content.IntentFilter
import android.icu.text.DateTimePatternGenerator
import android.os.Build
import android.text.format.DateFormat
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.core.os.ConfigurationCompat
import nl.ndat.tvlauncher.util.composable.SystemBroadcastReceiver
import java.util.Calendar

@Composable
fun ToolbarClock() {
	val context = LocalContext.current
	val pattern = remember(context) {
		val locale = requireNotNull(ConfigurationCompat.getLocales(context.resources.configuration)[0])
		val is24HourFormat = DateFormat.is24HourFormat(context)
		val pattern = when {
			is24HourFormat -> "Hm"
			else -> "hm"
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			DateTimePatternGenerator.getInstance(locale).getBestPattern(pattern)
		} else {
			pattern
		}
	}

	fun getTime() = DateFormat.format(pattern, Calendar.getInstance()).toString()
	var time by remember(pattern) { mutableStateOf(getTime()) }

	SystemBroadcastReceiver(IntentFilter().apply {
		addAction(Intent.ACTION_TIME_CHANGED)
		addAction(Intent.ACTION_TIMEZONE_CHANGED)
		addAction(Intent.ACTION_TIME_TICK)
	}) { _, _ -> time = getTime() }

	Text(
		fontSize = 20.sp,
		color = Color.Gray,
		text = time,
	)
}
