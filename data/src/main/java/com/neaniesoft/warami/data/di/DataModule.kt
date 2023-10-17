package com.neaniesoft.warami.data.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.neaniesoft.warami.data.R
import com.neaniesoft.warami.data.db.CommentQueries
import com.neaniesoft.warami.data.db.CommunityQueries
import com.neaniesoft.warami.data.db.Database
import com.neaniesoft.warami.data.db.InstanceQueries
import com.neaniesoft.warami.data.db.PersonQueries
import com.neaniesoft.warami.data.db.PostAggregateQueries
import com.neaniesoft.warami.data.db.PostQueries
import com.neaniesoft.warami.data.db.PostRemoteKeyQueries
import com.neaniesoft.warami.data.db.PostSearchParamsQueries
import com.neaniesoft.warami.data.db.SubscribedCommunityQueries
import com.neaniesoft.warami.data.repositories.adapters.ZonedDateTimeFromLocalTimeAdapter
import com.neaniesoft.warami.data.repositories.post.PostTransactor
import com.squareup.moshi.Moshi
import com.squareup.moshi.addAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.time.Clock
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

typealias IODispatcher = CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
class DataModule() {
    @Provides
    @Singleton
    fun provideClock(): Clock = Clock.systemDefaultZone()

    @Provides
    @Singleton
    fun providePostTransactor(postQueries: PostQueries): PostTransactor = postQueries

    @Provides
    @Singleton
    fun providePostQueries(db: Database): PostQueries = db.postQueries

    @Provides
    @Singleton
    fun providePostRemoteKeyQueries(db: Database): PostRemoteKeyQueries = db.postRemoteKeyQueries

    @Provides
    @Singleton
    fun provideCommunityQueries(db: Database): CommunityQueries = db.communityQueries

    @Provides
    @Singleton
    fun providePersonQueries(db: Database): PersonQueries = db.personQueries

    @Provides
    @Singleton
    fun providePostAggregateQueries(db: Database): PostAggregateQueries =
        db.postAggregateQueries

    @Provides
    @Singleton
    fun providePostSearchParamQueries(db: Database): PostSearchParamsQueries =
        db.postSearchParamsQueries

    @Provides
    @Singleton
    fun provideInstanceQueries(db: Database): InstanceQueries = db.instanceQueries

    @Provides
    @Singleton
    fun provideCommentQueries(db: Database): CommentQueries = db.commentQueries

    @Provides
    @Singleton
    fun provideSubscribedCommunityQueries(db: Database): SubscribedCommunityQueries = db.subscribedCommunityQueries

    @Provides
    @Singleton
    fun provideLocalDateTimeFormatter(): DateTimeFormatter =
        DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @Provides
    @Singleton
    fun provideIODispatcher(): IODispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideDatabase(driver: SqlDriver): Database {
        return Database(driver)
    }

    @Provides
    @Singleton
    fun provideSqlDriver(
        @ApplicationContext context: Context,
    ): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "warami.db")
    }

    @Provides
    @Singleton
    fun remoteConfigSettings(): FirebaseRemoteConfigSettings = com.google.firebase.remoteconfig.ktx.remoteConfigSettings {
        minimumFetchIntervalInSeconds = 3600
    }

    @Provides
    @Singleton
    fun provideFirebaseRemoteConfig(settings: FirebaseRemoteConfigSettings): FirebaseRemoteConfig =
        Firebase.remoteConfig.apply {
            setDefaultsAsync(R.xml.remote_config_defaults)
            setConfigSettingsAsync(settings)
        }

    @OptIn(ExperimentalStdlibApi::class)
    @Provides
    @Singleton
    fun provideMoshi(zonedDateTimeFromLocalTimeAdapter: ZonedDateTimeFromLocalTimeAdapter): Moshi {
        return Moshi.Builder()
            .addAdapter(zonedDateTimeFromLocalTimeAdapter)
            .build()
    }
}
