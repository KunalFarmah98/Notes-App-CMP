import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dataMapper.DateTimeUtils
import database.Note
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext
import viewmodel.NotesViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinContext {
            val notesViewModel: NotesViewModel = koinViewModel()
            val notes by notesViewModel.notes.collectAsState(initial = emptyList())
            val scope = rememberCoroutineScope()
            val navController = rememberNavController()
            LaunchedEffect(key1 = true) {
                val notesList = listOf(
                    Note(
                        "note1",
                        "note1",
                        Note.generateRandomColor(),
                        DateTimeUtils.toEpochMillis(DateTimeUtils.now())
                    ),
                    Note(
                        "note2",
                        "note2",
                        Note.generateRandomColor(),
                        DateTimeUtils.toEpochMillis(DateTimeUtils.now())
                    ),
                    Note(
                        "note3",
                        "note3",
                        Note.generateRandomColor(),
                        DateTimeUtils.toEpochMillis(DateTimeUtils.now())
                    )
                )
                notesList.forEach {
                    notesViewModel.upsertNote(it)
                }
            }
            NavHost(navController = navController, startDestination = NotesList) {
                composable<NotesList> {
                    Scaffold(topBar = { TopAppBar(title = { Text(text = "Notes") }) },
                        floatingActionButton = {
                            FloatingActionButton(onClick = { /*TODO*/ }) {
                                Text(
                                    text = "+",
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(notes) { note ->
                                NoteItem(notesViewModel, note, scope)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NoteItem(notesViewModel: NotesViewModel, note: Note, scope: CoroutineScope) {
    Row {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(color = Color(note.colorHex), shape = RoundedCornerShape(20.dp))
                .border(width = 1.dp, color = Color.Transparent, shape = RoundedCornerShape(20.dp))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(0.9f),
                    text = note.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(modifier = Modifier
                    .clickable {
                        scope.launch { notesViewModel.deleteNote(note) }
                    }
                    .alignByBaseline()
                    .alignByBaseline()
                    .padding(top = 10.dp, end = 10.dp),
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "delete",
                    tint = Color.Black
                )
            }

            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                text = note.content,
                textAlign = TextAlign.Start,
                fontSize = 16.sp
            )
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                text = DateTimeUtils.formatNoteDate(DateTimeUtils.fromEpochMillis(note.created)),
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                textAlign = TextAlign.Right
            )

        }
    }

}

@Serializable
object NotesList

@Serializable
data class CreateNote(
    val title: String
)