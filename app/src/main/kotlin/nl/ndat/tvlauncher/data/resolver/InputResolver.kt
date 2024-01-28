package nl.ndat.tvlauncher.data.resolver

import android.content.Context
import android.media.tv.TvInputInfo
import android.media.tv.TvInputManager
import androidx.core.content.getSystemService
import nl.ndat.tvlauncher.data.sqldelight.Input
import nl.ndat.tvlauncher.util.createSwitchIntent
import nl.ndat.tvlauncher.util.getInputType
import nl.ndat.tvlauncher.util.loadPreferredLabel

class InputResolver {
	companion object {
		const val INPUT_ID_PREFIX = "input:"
	}

	fun getInputs(context: Context): List<Input> {
		val tvInputManager = context.getSystemService<TvInputManager>()
		val tvInputs = tvInputManager?.tvInputList.orEmpty()

		return tvInputs.map { it.toInput(context) }
	}

	private fun TvInputInfo.toInput(context: Context) = Input(
		id = "$INPUT_ID_PREFIX$id",
		inputId = id,

		displayName = loadPreferredLabel(context),
		packageName = serviceInfo?.packageName,
		type = getInputType(),

		switchIntentUri = createSwitchIntent().toUri(0),
	)

}
