package com.itrexgroup.foosballratingsystem.di

import com.itrexgroup.foosballratingsystem.ui.game.GameFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class GameModule {

    @FragmentScope
    @ContributesAndroidInjector()
    abstract fun provideGameFragment(): GameFragment
}
