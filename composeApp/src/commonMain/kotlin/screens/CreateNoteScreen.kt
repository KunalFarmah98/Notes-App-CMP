package screens

import CreateNoteScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.toRoute
import dataMapper.DateTimeUtils
import database.Note
import viewmodel.NotesViewModel

@Composable
fun CreateNoteScreen(
    notesViewModel: NotesViewModel,
    navController: NavController,
    backStackEntry: NavBackStackEntry
) {
    val args = backStackEntry.toRoute<CreateNoteScreen>()
    val titleState = rememberSaveable {
        mutableStateOf("")
    }
    val contentState = rememberSaveable {
        mutableStateOf("")
    }
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
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputTextField(state = titleState, label = "Title", placeholder = "Enter Title")
            Spacer(modifier = Modifier.height(10.dp))
            InputTextField(state = contentState, label = "contentState", placeholder = "Enter Note Content", modifier = Modifier.height(350.dp))
            Button(
                modifier = Modifier.padding(20.dp).fillMaxWidth(),
                shape = RoundedCornerShape(40.dp),
                onClick = {
                    notesViewModel.upsertNote(
                        Note(
                            title = titleState.value,
                            content = contentState.value,
                            Note.generateRandomColor(),
                            DateTimeUtils.toEpochMillis(DateTimeUtils.now())
                        )
                    )
                    navController.popBackStack()
                }) {
                Text(
                    text = "Add Note",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }

    }
}

@Composable
fun InputTextField(state: MutableState<String>, label: String, placeholder: String, modifier: Modifier = Modifier){
    TextField(
        value = state.value,
        label = { Text(text = label)},
        placeholder = {Text(text = placeholder)},
        onValueChange = { newValue: String -> state.value = newValue },
        colors = TextFieldDefaults.textFieldColors(textColor = Color.Black, unfocusedIndicatorColor = Color.Transparent, focusedIndicatorColor = MaterialTheme.colors.primary, backgroundColor = Color(0xFFF2F2F2)),
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(
                Color.White
            )
            .border(1.dp, Color.Gray, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp)
    )
}