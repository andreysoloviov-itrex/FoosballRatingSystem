package com.itrexgroup.foosballratingsystem

import com.itrexgroup.foosballratingsystem.di.AppComponent
import com.itrexgroup.foosballratingsystem.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class FoosballApp : DaggerApplication() {

    private val appComponent = DaggerAppComponent.builder()
        .application(this)
        .build()

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        component = appComponent
        return appComponent
    }

    companion object {
        var component: AppComponent? = null
    }

}
