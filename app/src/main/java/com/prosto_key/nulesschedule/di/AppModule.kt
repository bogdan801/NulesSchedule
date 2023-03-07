package com.prosto_key.nulesschedule.di

import android.content.Context
import androidx.room.Room
import com.prosto_key.nulesschedule.data.local.database.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.prosto_key.nulesschedule.data.repository.RepositoryImpl
import com.prosto_key.nulesschedule.domain.repository.Repository

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context) = Room
        .databaseBuilder(app, Database::class.java, "database.db")
        //.fallbackToDestructiveMigration()
        .createFromAsset("database/database.db")
        .build()

    @Provides
    fun provideDao(db: Database) = db.dbDao

    @Provides
    @Singleton
    fun provideRepository(db: Database): Repository {
        return RepositoryImpl(db.dbDao)
    }
}