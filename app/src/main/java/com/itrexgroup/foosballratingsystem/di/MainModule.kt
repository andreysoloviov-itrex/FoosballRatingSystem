package com.itrexgroup.foosballratingsystem.di

import com.itrexgroup.foosballratingsystem.ui.pager.PagerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    @FragmentScope
    @ContributesAndroidInjector()
    abstract fun provideMainFragment(): PagerFragment
}
