package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import database.Note
import database.NoteDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NotesViewModel(private val noteDao: NoteDao): ViewModel() {
    private var _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.asStateFlow()

    init{
        viewModelScope.launch {
            noteDao.getAllNotes().distinctUntilChanged().collect{
                _notes.value = it
            }
        }
    }

    fun upsertNote(note: Note) = viewModelScope.launch {
        noteDao.upsert(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteDao.deleteNote(note)
    }
}