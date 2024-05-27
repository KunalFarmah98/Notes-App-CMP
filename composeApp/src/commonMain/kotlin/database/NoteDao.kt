package database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface NoteDao {
    @Query("Select * from notes")
    fun getAllNotes(): Flow<List<Note>>

    @Query("Select * from notes where id = :id")
    fun getNoteById(id: Int): Flow<Note>

    @Query("Select * from notes where title like :title")
    fun getNoteByTitle(title: String): Flow<Note>

    @Upsert
    suspend fun upsert(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

}