package screens

import CreateNoteScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.toRoute
import viewmodel.NotesViewModel

@Composable
fun CreateNoteScreen(
    notesViewModel: NotesViewModel,
    navController: NavController,
    backStackEntry: NavBackStackEntry
) {
    val args = backStackEntry.toRoute<CreateNoteScreen>()
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = args.title)
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
    }) {
        Text(modifier = Modifier.padding(it), text = "Create Note ${args.title}")

    }
}