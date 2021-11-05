package com.itrexgroup.foosballratingsystem.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.itrexgroup.foosballratingsystem.db.AppDatabase
import com.itrexgroup.foosballratingsystem.db.GameDao
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.roundToInt

class RatingsRepo @Inject constructor(
    private val appDatabase: AppDatabase,
    private val gameDao: GameDao
) {

    fun getRatings(): LiveData<List<PlayerRating>> =
        gameDao.getAllGames().map { games ->
            val players = hashMapOf<String, PlayerRating>()

            games.forEach {
                var homePlayerCurrentRating =
                    players[it.playerNameHome] ?: PlayerRating(it.playerNameHome)
                var awayPlayerCurrentRating =
                    players[it.playerNameAway] ?: PlayerRating(it.playerNameAway)

                when {
                    it.isHomePlayerWins -> {
                        homePlayerCurrentRating =
                            homePlayerCurrentRating.copy(
                                gamesCount = homePlayerCurrentRating.gamesCount + 1,
                                wins = homePlayerCurrentRating.wins + 1
                            )
                        awayPlayerCurrentRating = awayPlayerCurrentRating.copy(
                            gamesCount = awayPlayerCurrentRating.gamesCount + 1,
                        )
                    }
                    it.isAwayPlayerWins -> {
                        homePlayerCurrentRating = homePlayerCurrentRating.copy(
                            gamesCount = homePlayerCurrentRating.gamesCount + 1,
                        )
                        awayPlayerCurrentRating =
                            awayPlayerCurrentRating.copy(
                                gamesCount = awayPlayerCurrentRating.gamesCount + 1,
                                wins = awayPlayerCurrentRating.wins + 1
                            )
                    }
                    else -> {
                        homePlayerCurrentRating = homePlayerCurrentRating.copy(
                            gamesCount = homePlayerCurrentRating.gamesCount + 1,
                        )
                        awayPlayerCurrentRating =
                            awayPlayerCurrentRating.copy(
                                gamesCount = awayPlayerCurrentRating.gamesCount + 1,
                            )
                    }
                }

                players[it.playerNameHome] = homePlayerCurrentRating
                players[it.playerNameAway] = awayPlayerCurrentRating
            }

            players.values.toList().sortedByDescending { it.rating }
                .mapIndexed { index, playerRating -> playerRating.copy(ratingPosition = index + 1) }
        }

}

data class PlayerRating(
    val playerName: String,
    val wins: Int = 0,
    val gamesCount: Int = 0,
    val ratingPosition: Int = 0
) {
    val rating: Int get() = ((wins.div(gamesCount.toFloat())) * 100 * 1.1.pow(gamesCount / 10)).roundToInt()
}