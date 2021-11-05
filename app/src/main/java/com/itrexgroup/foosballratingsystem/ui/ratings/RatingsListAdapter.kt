package com.itrexgroup.foosballratingsystem.ui.ratings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itrexgroup.foosballratingsystem.R
import com.itrexgroup.foosballratingsystem.databinding.ItemRatingBinding
import com.itrexgroup.foosballratingsystem.databinding.ItemTopRatingBinding
import com.itrexgroup.foosballratingsystem.repo.PlayerRating

class RatingsListAdapter(private val onClick: (PlayerRating) -> Unit) :
    ListAdapter<PlayerRating, RatingsListAdapter.BaseRatingViewHolder>(PlayerRatingDiffCallback) {

    abstract class BaseRatingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: PlayerRating)
    }

    class PlayerRatingViewHolder(
        val binding: ItemRatingBinding,
        val onClick: (PlayerRating) -> Unit
    ) :
        BaseRatingViewHolder(binding.root) {
        override fun bind(item: PlayerRating) {
            with(binding) {

                root.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onClick(item)
                    }
                }
                playerRating.text = item.rating.toString()
                playerName.text = item.playerName
                playerGames.text = item.gamesCount.toString()
                playerWins.text = item.wins.toString()
                ratingPosition.text = item.ratingPosition.toString()
            }

        }
    }

    class TopPlayerRatingViewHolder(
        val binding: ItemTopRatingBinding,
        val onClick: (PlayerRating) -> Unit
    ) :
        BaseRatingViewHolder(binding.root) {
        override fun bind(item: PlayerRating) {
            with(binding)
            {

                root.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onClick(item)
                    }
                }

                playerRating.text = item.rating.toString()
                playerName.text = item.playerName
                playerGames.text = item.gamesCount.toString()
                playerWins.text = item.wins.toString()
                iconCrown.imageTintList = when (item.ratingPosition) {
                    1 -> AppCompatResources.getColorStateList(root.context, R.color.gold)
                    2 -> AppCompatResources.getColorStateList(root.context, R.color.silver)
                    else -> AppCompatResources.getColorStateList(root.context, R.color.bronze)
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseRatingViewHolder {
        return when (viewType) {
            TOP_RATING_VIEW_TYPE -> {
                val viewBinding = ItemTopRatingBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                TopPlayerRatingViewHolder(viewBinding, onClick)
            }
            else -> {
                val viewBinding = ItemRatingBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                PlayerRatingViewHolder(viewBinding, onClick)
            }
        }
    }


    override fun onBindViewHolder(viewHolder: BaseRatingViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            in 0..2 -> TOP_RATING_VIEW_TYPE
            else -> RATING_VIEW_TYPE
        }
    }

    companion object {
        const val TOP_RATING_VIEW_TYPE = 1
        const val RATING_VIEW_TYPE = 2
    }
}

object PlayerRatingDiffCallback : DiffUtil.ItemCallback<PlayerRating>() {
    override fun areItemsTheSame(oldItem: PlayerRating, newItem: PlayerRating): Boolean {
        return oldItem.playerName == newItem.playerName
    }

    override fun areContentsTheSame(oldItem: PlayerRating, newItem: PlayerRating): Boolean {
        return oldItem == newItem
    }
}