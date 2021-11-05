package com.itrexgroup.foosballratingsystem.di

import android.content.Context
import androidx.room.Room
import com.itrexgroup.foosballratingsystem.db.AppDatabase
import com.itrexgroup.foosballratingsystem.db.GameDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideGameDao(appDatabase: AppDatabase): GameDao =
        appDatabase.getGameDao()

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "foosball_ratings.db")
            .createFromAsset("database/foosball_ratings_db.db")
            .build()


}
