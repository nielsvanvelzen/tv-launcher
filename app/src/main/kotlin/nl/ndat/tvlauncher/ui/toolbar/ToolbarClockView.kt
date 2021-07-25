package nl.ndat.tvlauncher.ui.toolbar

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextClock
import androidx.core.content.ContextCompat
import nl.ndat.tvlauncher.R

class ToolbarClockView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
) : TextClock(context, attrs) {
	init {
		setTextColor(ContextCompat.getColor(context, R.color.grey))
		setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
	}
}
