package com.itrexgroup.foosballratingsystem.ui.ratings

import androidx.lifecycle.ViewModel
import com.itrexgroup.foosballratingsystem.repo.RatingsRepo
import javax.inject.Inject

class RatingsListViewModel @Inject constructor(
    private val ratingsRepo: RatingsRepo,
) : ViewModel() {

    val ratings = ratingsRepo.getRatings()
}
