package com.neaniesoft.warami.signin.di

import com.neaniesoft.warami.common.navigation.SignInNavigator
import com.neaniesoft.warami.data.di.DatabaseComponent
import com.neaniesoft.warami.domain.di.DomainComponent
import com.neaniesoft.warami.signin.InstanceSelectionViewModel
import com.neaniesoft.warami.signin.SignInScope
import com.neaniesoft.warami.signin.SignInScreen
import com.neaniesoft.warami.signin.SignInViewModel
import com.neaniesoft.warami.signin.destinations.SignInScreenDestination
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@SignInScope
abstract class SignInComponent(
    @Component val databaseComponent: DatabaseComponent,
    @Component val domainComponent: DomainComponent,
    @get:Provides @get:SignInScope val signInNavigator: SignInNavigator,
) {
    @get:SignInScope
    abstract val signInViewModelProvider: () -> SignInViewModel

    @get:SignInScope
    abstract val instanceSelectionViewModelProvider: () -> InstanceSelectionViewModel

    @Provides
    @SignInScope
    fun provideSignInDestination(): SignInScreenDestination = SignInScreenDestination
}
