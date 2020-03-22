package com.example.rockpaperscissors.database

import android.content.Context
import com.example.rockpaperscissors.model.PlayedGame

class HistoryGameRepository(context: Context) {

    private var itemDao: GameDao

    init {
        val database = HistoryGameDatabase.getDatabase(context)
        itemDao = database!!.gameDao()
    }

    suspend fun getAllGames(): List<PlayedGame> =
     itemDao.GetAllGames()

    suspend fun deleteAllGames() =
     itemDao.DeleteAllGames()

    suspend fun saveGame(playedGame: PlayedGame) =
     itemDao.saveGame(playedGame)

}