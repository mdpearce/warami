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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent = buildAppComponent()

        setContent {
            WaramiApp(
                appComponent.feedComponent.feedViewModelProvider,
                appComponent.signInComponent.instanceSelectionViewModelProvider,
                appComponent.signInComponent.signInViewModelProvider,
                appComponent.uiComponent.homeViewModelProvider,
                appComponent.feedComponent.commentsViewModelProvider,
            )
        }
    }

    private fun buildAppComponent(): AppComponent {
        val apiComponent = ApiComponent::class.create(WaramiApplication.getInstance())
        val databaseComponent = DatabaseComponent::class.create(apiComponent)
        val domainComponent = DomainComponent::class.create(databaseComponent)
        val feedComponent = FeedComponent::class.create(domainComponent, WaramiNavigator)
        val signInComponent = SignInComponent::class.create(databaseComponent, domainComponent, WaramiNavigator)
        val uiComponent = UiComponent::class.create(feedComponent, signInComponent, WaramiNavigator)

        return AppComponent::class.create(
            databaseComponent = databaseComponent,
            feedComponent = feedComponent,
            signInComponent = signInComponent,
            uiComponent = uiComponent,
        )
    }
}
