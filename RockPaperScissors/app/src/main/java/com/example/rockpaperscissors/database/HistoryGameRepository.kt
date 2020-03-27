package com.example.rockpaperscissors.database

import android.content.Context
import com.example.rockpaperscissors.model.PlayedGame

class HistoryGameRepository(context: Context) {

    private var gameDao: GameDao

    init {
        val database = HistoryGameDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    suspend fun getAllGames(): List<PlayedGame> =
     gameDao.getAllGames()

    suspend fun deleteAllGames() =
     gameDao.deleteAllGames()

    suspend fun saveGame(playedGame: PlayedGame) =
     gameDao.saveGame(playedGame)
}