package nl.ndat.tvlauncher.data.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.ndat.tvlauncher.data.DatabaseContainer
import nl.ndat.tvlauncher.data.executeAsListFlow
import nl.ndat.tvlauncher.data.resolver.InputResolver
import nl.ndat.tvlauncher.data.sqldelight.Input

class InputRepository(
	private val context: Context,
	private val inputResolver: InputResolver,
	private val database: DatabaseContainer
) {
	private suspend fun commitInputs(inputs: Collection<Input>) = withContext(Dispatchers.IO) {
		database.transaction {
			// Remove missing inputs from database
			val currentIds = inputs.map { it.id }
			database.inputs.removeNotIn(currentIds)

			// Upsert inputs
			inputs.map { input -> commitInput(input) }
		}
	}

	private suspend fun commitInput(input: Input) = withContext(Dispatchers.IO) {
		database.inputs.upsert(
			id = input.id,
			inputId = input.id,
			displayName = input.displayName,
			packageName = input.packageName,
			type = input.type,
			switchIntentUri = input.switchIntentUri
		)
	}

	suspend fun refreshAllInputs() {
		val inputs = inputResolver.getInputs(context)
		commitInputs(inputs)
	}

	fun getInputs() = database.inputs.getAll().executeAsListFlow()
}
