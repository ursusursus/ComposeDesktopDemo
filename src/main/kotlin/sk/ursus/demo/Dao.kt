package sk.ursus.demo

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import sk.ursus.demo.db.Player
import sk.ursus.demo.db.PlayerQueries

class Dao(private val queries: PlayerQueries) {
    fun players(): Flow<List<Player>> {
        return queries.allPlayers().asFlow().mapToList()
    }

    suspend fun savePlayer(player: Player) {
        withContext(Dispatchers.IO) {
            queries.insertPlayer(player.name)
        }
    }

    suspend fun deletePlayer(id: Long) {
        withContext(Dispatchers.IO) {
            queries.deletePlayer(id)
        }
    }
}