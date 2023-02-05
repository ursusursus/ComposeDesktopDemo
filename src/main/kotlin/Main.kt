import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        title = "Hello",
        onCloseRequest = ::exitApplication
    ) {
        var checked by remember { mutableStateOf(false) }
        MenuBar {
            Menu("Actions", mnemonic = 'A') {
                CheckboxItem(
                    "Advanced settings",
                    checked = checked
                ) {
                    checked = it
                }

                Separator()
//                Item("About", icon = AboutIcon, onClick = { action = "Last action: About" })
//                Item("Exit", onClick = { isOpen = false }, shortcut = KeyShortcut(Key.Escape), mnemonic = 'E')
            }
        }
        App()
    }
}

@Composable
fun App() {
    MaterialTheme(
        colors = if (isSystemInDarkTheme()) darkColors() else lightColors()
    ) {
        Surface {
            MainScreen()
        }
    }
}
