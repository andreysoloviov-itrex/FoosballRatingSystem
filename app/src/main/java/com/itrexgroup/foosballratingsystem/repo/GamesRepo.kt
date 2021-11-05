package com.itrexgroup.foosballratingsystem.repo

import androidx.lifecycle.LiveData
import com.itrexgroup.foosballratingsystem.db.AppDatabase
import com.itrexgroup.foosballratingsystem.db.Game
import com.itrexgroup.foosballratingsystem.db.GameDao
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GamesRepo @Inject constructor(
    private val appDatabase: AppDatabase,
    private val gameDao: GameDao
) {

    fun getAllGames(): LiveData<List<Game>> =
        gameDao.getAllGames()

    fun getGame(gameId: Int): LiveData<Game> =
        gameDao.getGame(gameId)

    fun getPlayerGames(playerName: String): LiveData<List<Game>> =
        gameDao.getAllGames(playerName)

    fun saveGame(game: Game): Completable {
        return Completable.fromAction {
            gameDao.insertGame(game)
        }
            .subscribeOn(Schedulers.io())
    }

    fun deleteGame(game: Game): Completable {
        return Completable.fromAction {
            gameDao.delete(game)
        }
            .subscribeOn(Schedulers.io())
    }

    fun resetDatabase(): Completable {
        return Completable.fromAction {
            gameDao.deleteAll()
        }
            .subscribeOn(Schedulers.io())
    }
}
