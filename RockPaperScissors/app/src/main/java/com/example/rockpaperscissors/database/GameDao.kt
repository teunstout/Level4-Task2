package com.example.rockpaperscissors.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rockpaperscissors.model.PlayedGame

@Dao
interface GameDao {

    @Query("SELECT * FROM HistoryGame")
    suspend fun getAllGames(): List<PlayedGame>

    @Query("DELETE FROM HistoryGame")
    suspend fun deleteAllGames()

    @Insert
    suspend fun saveGame(playedGame: PlayedGame)
}