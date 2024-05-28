package di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import database.NotesDatabase
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

actual val appModule = module {
    single {
        getNotesDatabase().noteDao()
    }
}

fun getNotesDatabase(): NotesDatabase {
    val dbFile = NSHomeDirectory() + "/notes.db"
    return Room.databaseBuilder<NotesDatabase>(
        name = dbFile,
        factory = { NotesDatabase::class.instantiateImpl() }
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}