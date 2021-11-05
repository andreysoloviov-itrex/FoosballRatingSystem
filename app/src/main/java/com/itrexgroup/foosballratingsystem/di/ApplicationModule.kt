package com.itrexgroup.foosballratingsystem.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itrexgroup.foosballratingsystem.ui.game.GameViewModel
import com.itrexgroup.foosballratingsystem.ui.pager.MainViewModel
import com.itrexgroup.foosballratingsystem.ui.ratings.RatingsListViewModel
import com.itrexgroup.foosballratingsystem.ui.results.GamesListViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
abstract class ApplicationModule {

    @Binds
    @Singleton
    abstract fun bindContext(application: Application): Context

    @Binds
    @Singleton
    abstract fun bindBaseViewModelFactory(baseViewModelFactory: BaseViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GamesListViewModel::class)
    abstract fun bindGamesListViewModel(mainViewModel: GamesListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel::class)
    abstract fun bindGameViewModel(gameViewModel: GameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RatingsListViewModel::class)
    abstract fun bindRatingsListViewModel(ratingsListViewModel: RatingsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

}

@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ViewModelKey(val viewModelClass: KClass<out ViewModel>)
