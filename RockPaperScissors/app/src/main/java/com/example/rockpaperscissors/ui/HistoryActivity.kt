package com.example.rockpaperscissors.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.database.HistoryGameRepository
import com.example.rockpaperscissors.model.PlayedGame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity() {
    private lateinit var historyGameRepository: HistoryGameRepository
    private val coroutine =  CoroutineScope(Dispatchers.Main)
    private var allGames = arrayListOf<PlayedGame>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        historyGameRepository = HistoryGameRepository(this)
        getShoppingListFromDatabase()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_game_activity, menu)
        return true
    }


    private fun getShoppingListFromDatabase() {
        // corountine is een soort light weighted threath
        coroutine.launch {
            val playedGames: List<PlayedGame> = withContext(Dispatchers.IO) {
                historyGameRepository.getAllGames()
            }
            this@HistoryActivity.allGames.clear()
            this@HistoryActivity.allGames.addAll(playedGames)
            // notify adapter

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.go_to_history -> {
                coroutine.launch {
                    withContext(Dispatchers.IO){
                        historyGameRepository.deleteAllGames()
                    }
                }
                true
            }
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}