package com.example.rockpaperscissors

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.game_activity.*

class GameActivity : AppCompatActivity() {
    private val ROCK = "ROCK"
    private val PAPER = "PAPER"
    private val SCISSORS = "SCISSORS"
    private val gamePictures = arrayListOf(R.drawable.rock, R.drawable.paper, R.drawable.scissors)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)

        imgButtonRock.setOnClickListener { checkAwnser(ROCK) }
        imgButtonPaper.setOnClickListener { checkAwnser(PAPER) }
        imgButtonScissors.setOnClickListener { checkAwnser(SCISSORS) }

        initView()
    }

    private fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun checkAwnser(awnser: String){
        when(awnser){
            ROCK -> {}
            PAPER -> {}
            SCISSORS -> {}
        }
    }
}
