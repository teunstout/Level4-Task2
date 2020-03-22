package com.example.rockpaperscissors.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rockpaperscissors.model.PlayedGame

@Database(entities = [PlayedGame::class], version = 1, exportSchema = false)
abstract class HistoryGameDatabase: RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        private const val DATABASE_NAME = "HISTORY_GAME_DATABASE" // name database

        @Volatile
        private var shoppingListRoomDatabaseInstance: HistoryGameDatabase? = null

        // check if database is null and build one if that is true
        fun getDatabase(context: Context): HistoryGameDatabase? {
            if (shoppingListRoomDatabaseInstance == null) {
                synchronized(HistoryGameDatabase::class.java) {
                    if (shoppingListRoomDatabaseInstance == null) {
                        shoppingListRoomDatabaseInstance =
                            Room.databaseBuilder(
                                context.applicationContext,
                                HistoryGameDatabase::class.java,
                                DATABASE_NAME
                            )
                                .build()
                    }
                }
            }
            return shoppingListRoomDatabaseInstance
        }
    }

}