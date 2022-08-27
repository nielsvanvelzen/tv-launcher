package nl.ndat.tvlauncher.data.repository

import android.content.Context
import nl.ndat.tvlauncher.data.SharedDatabase
import nl.ndat.tvlauncher.data.dao.InputDao
import nl.ndat.tvlauncher.data.entity.Input
import nl.ndat.tvlauncher.data.resolver.InputResolver
import nl.ndat.tvlauncher.util.withSingleTransaction

class InputRepository(
	private val context: Context,
	private val inputResolver: InputResolver,
	private val database: SharedDatabase,
	private val inputDao: InputDao,
) {
	private suspend fun commitInputs(inputs: Collection<Input>) = database.withSingleTransaction {
		// Remove missing inputs from database
		val currentIds = inputs.map { it.id }
		inputDao.removeNotIn(currentIds)

		// Upsert inputs
		inputs.map { input -> commitInput(input) }
	}

	private suspend fun commitInput(input: Input) {
		val current = inputDao.getById(input.id)

		if (current != null) inputDao.update(input)
		else inputDao.insert(input)
	}

	suspend fun refreshAllInputs() {
		val inputs = inputResolver.getInputs(context)
		commitInputs(inputs)
	}

	fun getInputs() = inputDao.getAll()
}
