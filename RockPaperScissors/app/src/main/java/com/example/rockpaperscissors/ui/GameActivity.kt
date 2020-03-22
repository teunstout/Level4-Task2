package com.example.rockpaperscissors.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.database.HistoryGameRepository
import com.example.rockpaperscissors.model.PlayedGame
import kotlinx.android.synthetic.main.game_activity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.math.floor


const val SWITCH_TO_HISTORY = 100
const val ROCK = 2
const val PAPER = 1
const val SCISSORS = 0

class GameActivity : AppCompatActivity() {
    private lateinit var historyGameRepository: HistoryGameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)

        historyGameRepository = HistoryGameRepository(this)

        imgButtonRock.setOnClickListener { saveGame(ROCK) }
        imgButtonPaper.setOnClickListener { saveGame(PAPER) }
        imgButtonScissors.setOnClickListener { saveGame(SCISSORS) }
    }

    private fun saveGame(playerThrow: Int) {
        val computerThrow = floor(Math.random() * 3).toInt()

        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                historyGameRepository.saveGame(
                    PlayedGame(
                        returnRightPicture(computerThrow),
                        returnRightPicture(playerThrow),
                        getWinnerText(playerThrow - computerThrow),
                        Date().toString()
                    )
                )
            }
        }
    }

    private fun getWinnerText(awnserInIntForm: Int): String {
        return if (awnserInIntForm == 2 || awnserInIntForm == -1) "YOU WIN"
        else if (awnserInIntForm == 1 || awnserInIntForm == -2) "COMPUTER WINS"
        else "DRAW"
    }

    private fun returnRightPicture(idFromPicture: Int): Int {
        return when (idFromPicture) {
            SCISSORS -> R.drawable.scissors
            PAPER -> R.drawable.paper
            else -> R.drawable.rock
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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
