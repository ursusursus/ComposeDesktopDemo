import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .padding(16.dp)
    ) {
        val expanded by remember { Foo() }.collectAsState(false)
        println("exp=${expanded}")

        Foo(expanded, onClick = {
            scope.launch {
                println("${System.currentTimeMillis()} launching t=${Thread.currentThread().name}")
                val result = AppComponent.apiClient.foo()
                println("${System.currentTimeMillis()} res=$result t=${Thread.currentThread().name}")
            }
        })
    }
}

@Composable
private fun Foo(expanded: Boolean, onClick: () -> Unit) {
    Text(text = "Hello title", style = MaterialTheme.typography.h2)
    Spacer(modifier = Modifier.height(16.dp))

    val width by animateDpAsState(if (expanded) 200.dp else 100.dp)
    val height by animateDpAsState(if (expanded) 100.dp else 50.dp)

    Button(
        modifier = Modifier
            .height(height)
            .width(width),
        onClick = onClick
    ) {
        Text("Click")
    }
}

private fun Foo(): Flow<Boolean> {
    return flow {
        emit(false)
        delay(1000)
        emit(true)
        delay(1000)
        emit(false)
        delay(1000)
        emit(true)
        delay(1000)
        emit(false)
    }
}