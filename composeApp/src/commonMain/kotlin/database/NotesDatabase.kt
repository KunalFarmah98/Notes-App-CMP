package database

import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase

class NotesDatabase: RoomDatabase() {
    override fun clearAllTables() {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }
}