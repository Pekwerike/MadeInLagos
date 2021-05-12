package com.pekwerike.madeinlagos.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pekwerike.madeinlagos.database.MadeInLagosLocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseDIModule {

    @Provides
    @Singleton
    fun getMadeInLagosLocalDatabaseInstance(@ApplicationContext context: Context): RoomDatabase {
        return Room.databaseBuilder(
            context,
            MadeInLagosLocalDatabase::class.java,
            "made_in_lagos_local_database"
        ).build()
    }
}