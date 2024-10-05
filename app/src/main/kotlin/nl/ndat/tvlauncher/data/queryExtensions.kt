package nl.ndat.tvlauncher.data

import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers

fun <T : Any> Query<T>.executeAsListFlow() = asFlow().mapToList(Dispatchers.IO)
