package com.example.rockpaperscissors.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.model.PlayedGame
import kotlinx.android.synthetic.main.game_layout.view.*

class GameAdapter(val gamePlayed: List<PlayedGame>) :
    RecyclerView.Adapter<GameAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(game: PlayedGame) {
            itemView.textWinner.text = "winner"
            itemView.textTimestamp.text = "winner"
            itemView.imgComputer.setImageResource(R.drawable.paper)
            itemView.imgPlayer.setImageResource(R.drawable.scissors)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.game_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return gamePlayed.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(gamePlayed[position])
    }

}