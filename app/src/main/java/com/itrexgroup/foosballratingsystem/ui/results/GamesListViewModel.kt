package com.itrexgroup.foosballratingsystem.ui.results

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.itrexgroup.foosballratingsystem.repo.GamesRepo
import javax.inject.Inject

class GamesListViewModel @Inject constructor(
    private val gamesRepo: GamesRepo,
) : ViewModel() {

    private val playerNameLiveData = MutableLiveData<String?>()
    val games = Transformations.switchMap(playerNameLiveData) {
        if (it == null) gamesRepo.getAllGames() else gamesRepo.getPlayerGames(it)
    }

    fun initGames(playerName: String? = null) {
        playerNameLiveData.postValue(playerName)
    }

}