package com.itrexgroup.foosballratingsystem.ui.pager

import androidx.lifecycle.ViewModel
import com.itrexgroup.foosballratingsystem.repo.GamesRepo
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val gamesRepo: GamesRepo,
) : ViewModel() {

    fun onNewSeasonClicked() {
        gamesRepo.resetDatabase().subscribe()
    }
}
