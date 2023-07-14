package com.neaniesoft.warami.data.repositories.instance

import com.neaniesoft.warami.data.db.InstanceQueries
import com.neaniesoft.warami.data.di.DatabaseScope
import me.tatarka.inject.annotations.Inject
import okhttp3.OkHttpClient

@DatabaseScope
@Inject
class InstanceRepository(private val instanceQueries: InstanceQueries, private val client: OkHttpClient) {



}
