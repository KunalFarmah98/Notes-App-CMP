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
import screens.CreateNoteScreen
import screens.NotesScreen
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
            NavHost(navController = navController, startDestination = NotesScreen) {
                composable<NotesScreen> {
                   NotesScreen(notesViewModel, navController, notes, scope)
                }
                composable<CreateNoteScreen> {
                    CreateNoteScreen(notesViewModel, navController, it)
                }
            }
        }
    }
}


@Serializable
object NotesScreen

@Serializable
data class CreateNoteScreen(
    val title: String
)