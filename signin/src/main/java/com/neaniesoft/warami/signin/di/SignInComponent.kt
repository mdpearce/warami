package com.neaniesoft.warami.signin.di

import com.neaniesoft.warami.data.di.DatabaseComponent
import com.neaniesoft.warami.signin.InstanceSelectionScreen
import com.neaniesoft.warami.signin.InstanceSelectionViewModel
import com.neaniesoft.warami.signin.SignInScope
import com.neaniesoft.warami.signin.SignInScreen
import com.neaniesoft.warami.signin.SignInViewModel
import me.tatarka.inject.annotations.Component

@Component
@SignInScope
abstract class SignInComponent(
    @Component val databaseComponent: DatabaseComponent,
) {
    abstract val signInScreen: SignInScreen
    abstract val instanceSelectionScreen: InstanceSelectionScreen

    @get:SignInScope
    abstract val signInViewModelProvider: () -> SignInViewModel

    @get:SignInScope
    abstract val instanceSelectionViewModelProvider: () -> InstanceSelectionViewModel
}
