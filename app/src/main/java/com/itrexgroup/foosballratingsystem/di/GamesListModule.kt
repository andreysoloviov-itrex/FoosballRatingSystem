package com.itrexgroup.foosballratingsystem.di

import com.itrexgroup.foosballratingsystem.ui.results.GamesListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class GamesListModule {

    @FragmentScope
    @ContributesAndroidInjector()
    abstract fun provideGamesListFragment(): GamesListFragment
}
