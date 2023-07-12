package com.neaniesoft.warami.signin.di

import com.neaniesoft.warami.signin.SignInScope
import com.neaniesoft.warami.signin.SignInScreen
import com.neaniesoft.warami.signin.SignInViewModel
import me.tatarka.inject.annotations.Component

@Component
@SignInScope
abstract class SignInComponent {
    abstract val signInScreen: SignInScreen

    @get:SignInScope
    abstract val signInViewModelProvider: () -> SignInViewModel
}
