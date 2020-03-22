package com.example.rockpaperscissors.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.database.HistoryGameDatabase
import com.example.rockpaperscissors.database.HistoryGameRepository
import com.example.rockpaperscissors.model.PlayedGame
import kotlinx.android.synthetic.main.game_activity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


const val SWITCH_TO_HISTORY = 100
class GameActivity : AppCompatActivity() {
    private val ROCK = "ROCK"
    private val PAPER = "PAPER"
    private val SCISSORS = "SCISSORS"
    private val gamePictures = arrayListOf(
        R.drawable.rock,
        R.drawable.paper,
        R.drawable.scissors
    )

    private lateinit var historyGameRepository: HistoryGameRepository
    private var allGames = arrayListOf<PlayedGame>()
    private val coroutine =  CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)

        imgButtonRock.setOnClickListener { checkAwnser(ROCK) }
        imgButtonPaper.setOnClickListener { checkAwnser(PAPER) }
        imgButtonScissors.setOnClickListener { checkAwnser(SCISSORS) }

        historyGameRepository = HistoryGameRepository(this)
        getShoppingListFromDatabase()

    }

    private fun checkAwnser(awnser: String){
        when(awnser){
            ROCK -> {}
            PAPER -> {}
            SCISSORS -> {}
        }
    }


    private fun getShoppingListFromDatabase() {
        // corountine is een soort light weighted threath
        coroutine.launch {
            val playedGames: List<PlayedGame> = withContext(Dispatchers.IO) {
                historyGameRepository.getAllGames()
            }

            this@GameActivity.allGames.clear()
            this@GameActivity.allGames.addAll(playedGames)

        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.go_to_history -> {
                startActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun startActivity() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivityForResult(intent, SWITCH_TO_HISTORY)
    }
}
