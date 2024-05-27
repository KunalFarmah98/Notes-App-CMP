package database

import androidx.room.Database
import androidx.room.RoomDatabase

// different impl are in android and ios
@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}