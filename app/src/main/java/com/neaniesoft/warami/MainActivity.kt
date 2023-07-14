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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent = buildAppComponent()

        setContent {
            WaramiApp(
                appComponent.feedComponent.feedViewModelProvider,
                appComponent.signInComponent.instanceSelectionViewModelProvider,
            )
        }
    }

    private fun buildAppComponent(): AppComponent {
        val apiComponent = ApiComponent::class.create(WaramiApplication.getInstance())
        val databaseComponent = DatabaseComponent::class.create(apiComponent)

        return AppComponent::class.create(
            databaseComponent = databaseComponent,
            feedComponent = FeedComponent::class.create(
                DomainComponent::class.create(
                    dataComponent = databaseComponent,
                ),
            ),
            signInComponent = SignInComponent::class.create(
                databaseComponent,
            ),
        )
    }
}
