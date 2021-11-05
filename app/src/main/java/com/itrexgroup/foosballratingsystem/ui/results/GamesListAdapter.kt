package com.itrexgroup.foosballratingsystem.ui.results

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itrexgroup.foosballratingsystem.databinding.ItemGameBinding
import com.itrexgroup.foosballratingsystem.db.Game
import org.joda.time.format.DateTimeFormat

class GamesListAdapter(private val onClick: (Game) -> Unit) :
    ListAdapter<Game, GamesListAdapter.GameViewHolder>(GameDiffCallback) {

    class GameViewHolder(val binding: ItemGameBinding, val onClick: (Game) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gameItem: Game) {
            with(binding) {
                root.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onClick(gameItem)
                    }
                }
                playerNameHome.text = gameItem.playerNameHome
                playerNameAway.text = gameItem.playerNameAway
                playerScoreHome.text = gameItem.scoreHome.toString()
                playerScoreAway.text = gameItem.scoreAway.toString()
                gameDate.text =
                    DateTimeFormat.shortDateTime().print(gameItem.dateTime)
                winnerHome.isVisible = false
                winnerAway.isVisible = false

                when {
                    gameItem.isHomePlayerWins -> winnerHome.isVisible = true
                    gameItem.isAwayPlayerWins -> winnerAway.isVisible = true
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GameViewHolder {
        val viewBinding =
            ItemGameBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return GameViewHolder(viewBinding, onClick)
    }

    override fun onBindViewHolder(viewHolder: GameViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }
}

object GameDiffCallback : DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem == newItem
    }
}