package com.example.rockpaperscissors.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.model.PlayedGame
import kotlinx.android.synthetic.main.game_layout.view.*

class GameAdapter(val allGamePlayed: List<PlayedGame>):
    RecyclerView.Adapter<GameAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(game: PlayedGame) {
            itemView.textWinner.text = game.winner
            itemView.textTimestamp.text = game.playedDate
            itemView.imgComputer.setImageResource(game.computerThrow)
            itemView.imgPlayer.setImageResource(game.playerThrow)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.game_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return allGamePlayed.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(allGamePlayed[allGamePlayed.lastIndex - position])
    }

}