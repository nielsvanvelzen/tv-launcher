package nl.ndat.tvlauncher.data

import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.asFlow
import kotlinx.coroutines.flow.map

fun <T : Any> Query<T>.executeAsListFlow() = asFlow().map { it.executeAsList() }
