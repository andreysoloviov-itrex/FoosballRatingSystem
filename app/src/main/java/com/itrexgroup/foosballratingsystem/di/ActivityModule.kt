package com.itrexgroup.foosballratingsystem.di

import com.itrexgroup.foosballratingsystem.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Scope

@Module
abstract class ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [GamesListModule::class, GameModule::class, MainModule::class, RatingsListModule::class])
    abstract fun provideMainActivity(): MainActivity
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope
