package com.example.rockpaperscissors.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.database.HistoryGameRepository
import com.example.rockpaperscissors.model.PlayedGame
import kotlinx.android.synthetic.main.game_activity.*
import kotlinx.android.synthetic.main.game_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.math.floor


const val SWITCH_TO_HISTORY = 100


class GameActivity : AppCompatActivity() {
    private val ROCK = 2
    private val PAPER = 1
    private val SCISSORS = 0
    private lateinit var historyGameRepository: HistoryGameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)

        historyGameRepository = HistoryGameRepository(this) // Instantiate the repository

        // Click listeners
        imgButtonRock.setOnClickListener { saveGame(ROCK) }
        imgButtonPaper.setOnClickListener { saveGame(PAPER) }
        imgButtonScissors.setOnClickListener { saveGame(SCISSORS) }
    }

    /**
     * Save the game to the database
     */
    private fun saveGame(playerThrow: Int) {
        val computerThrow = floor(Math.random() * 3).toInt()    // Computer throw 0-2
        val thisGame = PlayedGame(
            returnRightPicture(computerThrow),
            returnRightPicture(playerThrow),
            getWinnerText(playerThrow - computerThrow),
            Date().toString()
        )

        updateView(thisGame) // set the view

        // save the game to database
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                historyGameRepository.saveGame(thisGame)
            }
        }
    }

    /**
     * update the view
     */
    private fun updateView(thisGame: PlayedGame) {
        textWinner.text = thisGame.winner
        imgComputer.setImageResource(thisGame.computerThrow)
        imgPlayer.setImageResource(thisGame.playerThrow)
    }

    /**
     * get the winner of the game
     */
    private fun getWinnerText(awnserInIntForm: Int): String {
        return if (awnserInIntForm == 2 || awnserInIntForm == -1) "YOU WIN"
        else if (awnserInIntForm == 1 || awnserInIntForm == -2) "COMPUTER WINS"
        else "DRAW"
    }

    /**
     * return right drawable int
     */
    private fun returnRightPicture(idFromPicture: Int): Int {
        return when (idFromPicture) {
            SCISSORS -> R.drawable.scissors
            PAPER -> R.drawable.paper
            else -> R.drawable.rock
        }
    }

    /***
     * set menu toolbar
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * menu actions
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.go_to_history -> {
                startActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * start the history intent
     */
    private fun startActivity() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivityForResult(intent, SWITCH_TO_HISTORY)
    }
}
