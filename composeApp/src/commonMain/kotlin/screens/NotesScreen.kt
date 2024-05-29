package screens

import CreateNoteScreen
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dataMapper.DateTimeUtils
import database.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import viewmodel.NotesViewModel

@Composable

fun NotesScreen(notesViewModel: NotesViewModel, navController: NavController, notes: List<Note>, scope: CoroutineScope){
    val isLoading = notesViewModel.isLoading.collectAsState()
    Scaffold(topBar = { TopAppBar(title = { Text(text = "Notes") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(CreateNoteScreen(title="Create Note")) }) {
                Text(
                    text = "+",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }) {
        if(isLoading.value){
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
        }
        else {
            if (notes.isEmpty()) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "No Notes Found !!")
                }
            } else {
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
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