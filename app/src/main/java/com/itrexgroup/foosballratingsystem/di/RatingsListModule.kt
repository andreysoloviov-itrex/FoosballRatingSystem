package com.itrexgroup.foosballratingsystem.di

import com.itrexgroup.foosballratingsystem.ui.ratings.RatingsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RatingsListModule {

    @FragmentScope
    @ContributesAndroidInjector()
    abstract fun provideRatingsListFragment(): RatingsListFragment
}
