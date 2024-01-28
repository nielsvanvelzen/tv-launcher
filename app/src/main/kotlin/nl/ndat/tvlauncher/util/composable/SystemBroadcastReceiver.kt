package nl.ndat.tvlauncher.util.composable

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

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

		ContextCompat.registerReceiver(context, receiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED)
		onDispose { context.unregisterReceiver(receiver) }
	}
}
