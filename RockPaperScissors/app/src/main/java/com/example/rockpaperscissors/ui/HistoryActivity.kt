package com.example.rockpaperscissors.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.database.HistoryGameRepository
import com.example.rockpaperscissors.model.PlayedGame
import kotlinx.android.synthetic.main.history_activity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity() {
    private lateinit var historyGameRepository: HistoryGameRepository
    private val coroutine = CoroutineScope(Dispatchers.Main)
    private var allGames = arrayListOf<PlayedGame>()
    private val gameAdapter = GameAdapter(allGames)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        historyGameRepository = HistoryGameRepository(this)

        initView()
    }

    private fun initView() {
        getPlayedGamesFromDatabase()
        rvPlayedGames.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvPlayedGames.adapter = gameAdapter
    }

    private fun getPlayedGamesFromDatabase() {
        coroutine.launch {
            val playedGames: List<PlayedGame> = withContext(Dispatchers.IO) {
                historyGameRepository.getAllGames()
            }
            this@HistoryActivity.allGames.clear()
            this@HistoryActivity.allGames.addAll(playedGames)
            gameAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_game_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_items -> {
                coroutine.launch {
                    withContext(Dispatchers.IO) {
                        historyGameRepository.deleteAllGames()
                        gameAdapter.notifyDataSetChanged()
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