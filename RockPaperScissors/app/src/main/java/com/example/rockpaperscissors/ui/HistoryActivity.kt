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
    private lateinit var historyGameRepository: HistoryGameRepository   // Repository --> controls database
    private val coroutine = CoroutineScope(Dispatchers.Main) // fake light weight threat
    private var allGames = arrayListOf<PlayedGame>()    // list with all the played games
    private val gameAdapter = GameAdapter(allGames) // adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // set the back menu button

        historyGameRepository = HistoryGameRepository(this) // init repository

        initView()
    }

    /**
     * init the view with the adapter and decoration
     */
    private fun initView() {
        getPlayedGamesFromDatabase()
        rvPlayedGames.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvPlayedGames.adapter = gameAdapter
    }


    /**
     * get all the data from the database
     */
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

    /**
     * set the menu
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_game_activity, menu)
        return true
    }

    /**
     *  Determen what happens when you click a item
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // delete items
            R.id.delete_all_items -> {
                coroutine.launch {
                    withContext(Dispatchers.IO) {
                        historyGameRepository.deleteAllGames()
                        getPlayedGamesFromDatabase()
                    }
                }
                true
            }
            // go back to other screen
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}