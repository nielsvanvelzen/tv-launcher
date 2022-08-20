package nl.ndat.tvlauncher.util.composable

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun SystemBroadcastReceiver(
	filter: IntentFilter,
	onBroadcastReceive: (context: Context?, intent: Intent?) -> Unit
) {
	val context = LocalContext.current
	DisposableEffect(context, filter, onBroadcastReceive) {
		val receiver = object : BroadcastReceiver() {
			override fun onReceive(context: Context?, intent: Intent?) = onBroadcastReceive(context, intent)
		}

		context.registerReceiver(receiver, filter)
		onDispose { context.unregisterReceiver(receiver) }
	}
}
