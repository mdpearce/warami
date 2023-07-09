package com.neaniesoft.warami.signin

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import me.tatarka.inject.annotations.Inject

typealias SignInScreen = @Composable () -> Unit

@Composable
@RootNavGraph(start = true)
@Destination
@Inject
fun SignInScreen() {

}
