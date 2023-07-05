package com.neaniesoft.warami.data.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.neaniesoft.warami.data.db.CommunityQueries
import com.neaniesoft.warami.data.db.Database
import com.neaniesoft.warami.data.db.PersonQueries
import com.neaniesoft.warami.data.db.PostAggregateQueries
import com.neaniesoft.warami.data.db.PostQueries
import com.neaniesoft.warami.data.db.PostSearchParamsQueries
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.time.format.DateTimeFormatter
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    companion object {
        const val DISPATCHER_IO = "IO"
    }

    @Provides
    @Singleton
    fun providePostQueries(db: Database): PostQueries = db.postQueries

    @Provides
    @Singleton
    fun provideCommunityQueries(db: Database): CommunityQueries = db.communityQueries

    @Provides
    @Singleton
    fun providePersonQueries(db: Database): PersonQueries = db.personQueries

    @Provides
    @Singleton
    fun providePostAggregateQueries(db: Database): PostAggregateQueries = db.postAggregateQueries

    @Provides
    @Singleton
    fun providePostSearchParamQueries(db: Database): PostSearchParamsQueries =
        db.postSearchParamsQueries

    @Provides
    @Singleton
    fun provideLocalDateTimeFormatter(): DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @Provides
    @Named(DISPATCHER_IO)
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideDatabase(driver: SqlDriver): Database {
        return Database(driver)
    }

    @Provides
    @Singleton
    fun provideSqlDriver(@ApplicationContext context: Context): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "warami.db")
    }
}
