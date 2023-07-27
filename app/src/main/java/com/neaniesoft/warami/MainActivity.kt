package com.neaniesoft.warami

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.neaniesoft.warami.api.di.ApiComponent
import com.neaniesoft.warami.api.di.create
import com.neaniesoft.warami.data.di.DatabaseComponent
import com.neaniesoft.warami.data.di.create
import com.neaniesoft.warami.di.AppComponent
import com.neaniesoft.warami.di.create
import com.neaniesoft.warami.domain.di.DomainComponent
import com.neaniesoft.warami.domain.di.create
import com.neaniesoft.warami.featurefeed.di.FeedComponent
import com.neaniesoft.warami.featurefeed.di.create
import com.neaniesoft.warami.signin.di.SignInComponent
import com.neaniesoft.warami.signin.di.create
import com.neaniesoft.warami.ui.WaramiApp
import com.neaniesoft.warami.ui.di.UiComponent
import com.neaniesoft.warami.ui.di.create
import com.neaniesoft.warami.ui.nav.WaramiNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaramiApp()
        }
    }
}
