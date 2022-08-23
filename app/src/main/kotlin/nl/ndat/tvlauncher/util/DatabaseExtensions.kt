package nl.ndat.tvlauncher.util

import androidx.room.RoomDatabase
import androidx.room.withTransaction

suspend fun RoomDatabase.withSingleTransaction(body: suspend () -> Unit) {
	if (inTransaction()) body()
	else withTransaction { body() }
}
