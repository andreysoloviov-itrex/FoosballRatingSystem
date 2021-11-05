package com.itrexgroup.foosballratingsystem.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itrexgroup.foosballratingsystem.db.Game
import com.itrexgroup.foosballratingsystem.repo.GamesRepo
import org.joda.time.DateTime
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

class GameViewModel @Inject constructor(
    private val gamesRepo: GamesRepo,
) : ViewModel() {

    private var isNeedToBeSaved = false

    private val _game = MediatorLiveData<Game>()
    val game: LiveData<Game>
        get() = _game

    val events = MutableLiveData<Event>()

    fun initGame(gameId: Int) {
        val gameByIdLiveData = gamesRepo.getGame(gameId)
        _game.addSource(gameByIdLiveData) {
            if (it != null) isNeedToBeSaved = true
            _game.postValue(it ?: Game())
            _game.removeSource(gameByIdLiveData)
        }
    }

    fun onHomePlayerNameChanged(name: String) {
        _game.value = _game.value?.copy(playerNameHome = name)
        isNeedToBeSaved = true
    }

    fun onAwayPlayerNameChanged(name: String) {
        _game.value = _game.value?.copy(playerNameAway = name)
        isNeedToBeSaved = true
    }

    fun onHomePlayerScored() {
        val gameValue = _game.value
        _game.value = gameValue?.copy(scoreHome = min(5, gameValue.scoreHome + 1))
    }

    fun onAwayPlayerScored() {
        val gameValue = _game.value
        _game.value = gameValue?.copy(scoreAway = min(5, gameValue.scoreAway + 1))
    }

    fun onHomeScoreDecreased() {
        val gameValue = _game.value

        _game.value = gameValue?.copy(scoreHome = max(0, gameValue.scoreHome - 1))
    }

    fun onAwayScoreDecreased() {
        val gameValue = _game.value
        _game.value = gameValue?.copy(scoreAway = max(0, gameValue.scoreAway - 1))
    }

    fun onDeleteGameClicked() {
        isNeedToBeSaved = false
        _game.value?.let { gamesRepo.deleteGame(it).subscribe() }
        events.postValue(CloseScreenEvent())
    }

    fun onDatePickerClicked() {
        events.postValue(OpenDatePickerEvent(_game.value?.dateTime ?: DateTime()))
    }

    fun onDatePicked(dateTime: DateTime) {
        _game.value = _game.value?.copy(dateTime = dateTime)
    }

    override fun onCleared() {
        super.onCleared()
        if (isNeedToBeSaved) _game.value?.let { gamesRepo.saveGame(it).subscribe() }
    }
}

class OpenDatePickerEvent(val dateTime: DateTime) : Event()
class CloseScreenEvent : Event()
sealed class Event