package com.neaniesoft.warami.data.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.neaniesoft.warami.api.di.ApiComponent
import com.neaniesoft.warami.data.R
import com.neaniesoft.warami.data.db.CommunityQueries
import com.neaniesoft.warami.data.db.Database
import com.neaniesoft.warami.data.db.InstanceQueries
import com.neaniesoft.warami.data.db.PersonQueries
import com.neaniesoft.warami.data.db.PostAggregateQueries
import com.neaniesoft.warami.data.db.PostQueries
import com.neaniesoft.warami.data.db.PostRemoteKeyQueries
import com.neaniesoft.warami.data.db.PostSearchParamsQueries
import com.neaniesoft.warami.data.repositories.ApiRepository
import com.neaniesoft.warami.data.repositories.AuthRepository
import com.neaniesoft.warami.data.repositories.adapters.ZonedDateTimeFromLocalTimeAdapter
import com.neaniesoft.warami.data.repositories.instance.InstanceSettingsRepository
import com.neaniesoft.warami.data.repositories.post.PostRepository
import com.neaniesoft.warami.data.repositories.post.PostTransactor
import com.squareup.moshi.Moshi
import com.squareup.moshi.addAdapter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import okhttp3.OkHttpClient
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
    abstract val instanceSettingsRepository: InstanceSettingsRepository
    abstract val zonedDateTimeFromLocalTimeAdapter: ZonedDateTimeFromLocalTimeAdapter
    abstract val apiRepository: ApiRepository

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
    fun provideInstanceQueries(db: Database): InstanceQueries = db.instanceQueries

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

    @DatabaseScope
    @Provides
    fun remoteConfigSettings(): FirebaseRemoteConfigSettings = com.google.firebase.remoteconfig.ktx.remoteConfigSettings {
        minimumFetchIntervalInSeconds = 3600
    }

    @DatabaseScope
    @Provides
    fun provideFirebaseRemoteConfig(settings: FirebaseRemoteConfigSettings): FirebaseRemoteConfig =
        Firebase.remoteConfig.apply {
            setDefaultsAsync(R.xml.remote_config_defaults)
            setConfigSettingsAsync(settings)
        }

    @OptIn(ExperimentalStdlibApi::class)
    @DatabaseScope
    @Provides
    fun provideMoshi(zonedDateTimeFromLocalTimeAdapter: ZonedDateTimeFromLocalTimeAdapter): Moshi {
        return Moshi.Builder()
            .addAdapter(zonedDateTimeFromLocalTimeAdapter)
            .build()
    }

    @Provides
    @DatabaseScope
    fun provideOkHttpClient(): OkHttpClient = apiComponent.provideOkHttpClient(apiComponent.provideOkHttpClientBuilder())
}
