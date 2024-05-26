import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dataMapper.DateTimeUtils
import database.Note
import database.NoteDao
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@Preview
fun App(noteDao: NoteDao) {
    MaterialTheme {
        val notes by noteDao.getAllNotes().collectAsState(initial = emptyList())
        val scope = rememberCoroutineScope()

        LaunchedEffect(key1 = true) {
            val notesList = listOf(
                Note("note1", "note1", Note.generateRandomColor(), DateTimeUtils.toEpochMillis(DateTimeUtils.now())),
                        Note("note2", "note2", Note.generateRandomColor(), DateTimeUtils.toEpochMillis(DateTimeUtils.now())),
            Note("note3", "note3",  Note.generateRandomColor(), DateTimeUtils.toEpochMillis(DateTimeUtils.now()))
            )
            notesList.forEach{
                noteDao.upsert(it)
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ){
            items(notes){ note ->
                NoteItem(noteDao,note, scope)
            }
        }
    }
}

@Composable
fun NoteItem(noteDao: NoteDao, note: Note, scope: CoroutineScope){
    Column(
        modifier = Modifier.fillMaxWidth().clickable {
            scope.launch {
                noteDao.deleteNote(note)
            }
        }.padding(16.dp).
        background(color = Color.Yellow)
            .border(width = 1.dp, color = Color.Red, shape = RoundedCornerShape(20.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = note.title)
        Text(text = note.content)
        Text(text = DateTimeUtils.formatNoteDate(DateTimeUtils.fromEpochMillis(note.created)))
    }
}