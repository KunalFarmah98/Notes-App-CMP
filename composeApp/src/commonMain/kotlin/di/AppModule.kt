package di

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import database.NotesDatabase
import org.koin.dsl.module

// all injected items need to be returned here
val appModule = module {
    single {
        getNotesDatabase(context = get()).noteDao()
    }
}


fun getNotesDatabase(context: Context): NotesDatabase {
    val dbFile = context.getDatabasePath("notes.db")
    return Room.databaseBuilder<NotesDatabase>(context, dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .build()
}