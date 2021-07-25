package nl.ndat.tvlauncher.ui.toolbar

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Rect
import android.provider.Settings
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import nl.ndat.tvlauncher.R

class ToolbarSettingsView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
) : AppCompatImageButton(context, attrs) {
	private val normalColor = ContextCompat.getColor(context, R.color.lb_grey)
	private val focusColor = ContextCompat.getColor(context, R.color.lb_tv_white)

	init {
		setBackgroundResource(R.color.transparent)
		setImageResource(R.drawable.ic_settings)
		contentDescription = context.getString(R.string.settings)
		imageTintList = ColorStateList.valueOf(normalColor)
		isClickable = true
	}

	override fun onFocusChanged(gainFocus: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
		super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)

		// TODO move this to XML
		val color = if (gainFocus) focusColor else normalColor
		val animator = ValueAnimator.ofArgb(imageTintList!!.defaultColor, color)
		animator.addUpdateListener {
			imageTintList = ColorStateList.valueOf(it.animatedValue as Int)
		}
		animator.duration = resources.getInteger(R.integer.button_animation_duration).toLong()
		animator.start()
	}

	override fun performClick(): Boolean {
		if (super.performClick()) return true

		context.startActivity(Intent(Settings.ACTION_SETTINGS))

		return true
	}
}
