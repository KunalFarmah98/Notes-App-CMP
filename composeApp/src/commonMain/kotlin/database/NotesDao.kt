package database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface NotesDao {
    @Query("Select * from notes")
    fun getAllNotes(): List<Note>

    @Query("Select * from notes where id = :id")
    fun getNoteById(id: Int): Note

    @Query("Select * from notes where title like :title")
    fun getNoteByTitle(title: String): Note

    @Upsert
    fun upsert(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNoteById(id: Int)

    @Delete
    fun deleteNote(note: Note)

}