package sk.ursus.demo

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sk.ursus.demo.db.Player

@Composable
fun MainScreen() {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        val expanded by remember { Foo() }.collectAsState(false)
        println("exp=${expanded}")

        Container(
            expanded = expanded,
            onClick = {
                scope.launch {
                    log("launching t=${Thread.currentThread().name}")
                    val result = withContext(Dispatchers.IO) {
                        AppComponent.apiClient.foo()
                    }
                    log("res=$result t=${Thread.currentThread().name}")
                }
                scope.launch {
                    val players = AppComponent.dao.players()
                    log("players=$players")
                }
            },
            onSaveClick = {
                scope.launch {
                    AppComponent.dao.savePlayer(Player(1, "foo"))
                }
            },
            onDeleteClick = {
                scope.launch {
                    AppComponent.dao.deletePlayer(it)
                }
            }
        )
    }
}

private fun log(message: String) {
    println("${System.currentTimeMillis()} $message")
}

@Composable
private fun Container(expanded: Boolean, onClick: () -> Unit, onSaveClick: () -> Unit, onDeleteClick: (Long) -> Unit) {
    Text(text = "Hello title", style = MaterialTheme.typography.h2)
    Spacer(modifier = Modifier.height(16.dp))

    val players by remember { AppComponent.dao.players() }.collectAsState(emptyList())
    if (players.isEmpty()) {
        Text("No players")
    } else {
        Column(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
//                .animateContentSize()
        ) {
            for (player in players) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable {
                            onDeleteClick(player.id)
                        }
                        .padding(16.dp),
                    text = "player=$player"
                )
                Divider()
            }
        }
    }
    Spacer(modifier = Modifier.height(32.dp))

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
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = onSaveClick
    ) {
        Text("Save")
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