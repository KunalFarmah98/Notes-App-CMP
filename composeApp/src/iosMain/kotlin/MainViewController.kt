import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {
    val dao = remember {
        getNotesDatabase().noteDao()
    }
    App(dao)
}