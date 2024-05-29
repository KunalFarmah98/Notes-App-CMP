import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
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
            val notes by notesViewModel.notes.collectAsState()
            val scope = rememberCoroutineScope()
            val navController = rememberNavController()
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