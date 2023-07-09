package com.neaniesoft.warami.data.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.neaniesoft.warami.api.di.ApiComponent
import com.neaniesoft.warami.data.db.CommunityQueries
import com.neaniesoft.warami.data.db.Database
import com.neaniesoft.warami.data.db.PersonQueries
import com.neaniesoft.warami.data.db.PostAggregateQueries
import com.neaniesoft.warami.data.db.PostQueries
import com.neaniesoft.warami.data.db.PostRemoteKeyQueries
import com.neaniesoft.warami.data.db.PostSearchParamsQueries
import com.neaniesoft.warami.data.repositories.AuthRepository
import com.neaniesoft.warami.data.repositories.post.PostRepository
import com.neaniesoft.warami.data.repositories.post.PostTransactor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import java.time.Clock
import java.time.format.DateTimeFormatter

typealias IODispatcher = CoroutineDispatcher

@Component
@DatabaseScope
abstract class DatabaseComponent(
    @Component val apiComponent: ApiComponent,
) {

    abstract val postRepository: PostRepository
    abstract val authRepository: AuthRepository

    @Provides
    @DatabaseScope
    fun provideContext(): Context = apiComponent.context

    @Provides
    @DatabaseScope
    fun provideClock(): Clock = Clock.systemDefaultZone()

    @Provides
    @DatabaseScope
    fun providePostTransactor(postQueries: PostQueries): PostTransactor = postQueries

    @Provides
    @DatabaseScope
    fun providePostQueries(db: Database): PostQueries = db.postQueries

    @Provides
    @DatabaseScope
    fun providePostRemoteKeyQueries(db: Database): PostRemoteKeyQueries = db.postRemoteKeyQueries

    @Provides
    @DatabaseScope
    fun provideCommunityQueries(db: Database): CommunityQueries = db.communityQueries

    @Provides
    @DatabaseScope
    fun providePersonQueries(db: Database): PersonQueries = db.personQueries

    @Provides
    @DatabaseScope
    fun providePostAggregateQueries(db: Database): PostAggregateQueries =
        db.postAggregateQueries

    @Provides
    @DatabaseScope
    fun providePostSearchParamQueries(db: Database): PostSearchParamsQueries =
        db.postSearchParamsQueries

    @Provides
    @DatabaseScope
    fun provideLocalDateTimeFormatter(): DateTimeFormatter =
        DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @Provides
    @DatabaseScope
    fun provideIODispatcher(): IODispatcher = Dispatchers.IO

    @Provides
    @DatabaseScope
    fun provideDatabase(driver: SqlDriver): Database {
        return Database(driver)
    }

    @Provides
    @DatabaseScope
    fun provideSqlDriver(context: Context): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "warami.db")
    }
}
