package nl.ndat.tvlauncher.ui.toolbar

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import nl.ndat.tvlauncher.R

@RequiresApi(Build.VERSION_CODES.N)
class ToolbarNetworkStateView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
) : AppCompatImageView(context, attrs) {
	private val connectivityManager = context.getSystemService<ConnectivityManager>()
	private val networkCallback = object : ConnectivityManager.NetworkCallback() {
		override fun onAvailable(network: Network) {
			super.onAvailable(network)

			imageTintList = ColorStateList.valueOf(Color.GREEN)
		}

		override fun onLost(network: Network) {
			super.onLost(network)

			imageTintList = ColorStateList.valueOf(Color.RED)
		}
	}

	init {
		setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_input))
	}

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()

		connectivityManager?.registerDefaultNetworkCallback(networkCallback)
	}

	override fun onDetachedFromWindow() {
		super.onDetachedFromWindow()

		connectivityManager?.unregisterNetworkCallback(networkCallback)
	}
}
