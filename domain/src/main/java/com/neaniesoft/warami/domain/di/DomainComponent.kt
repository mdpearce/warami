package com.neaniesoft.warami.domain.di

import android.content.Context
import com.neaniesoft.warami.data.di.DataModule
import com.neaniesoft.warami.domain.usecases.BuildPostSearchParametersUseCase
import com.neaniesoft.warami.domain.usecases.GetInstancesUseCase
import com.neaniesoft.warami.domain.usecases.IsLoggedInUseCase
import com.neaniesoft.warami.domain.usecases.LoginUseCase
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope

@Component
@DomainScope
abstract class DomainComponent(
    @Component val dataComponent: DataModule,
) {
    @Provides
    @DomainScope
    fun provideContext(): Context = dataComponent.provideContext()

    abstract val buildPostSearchParametersUseCase: BuildPostSearchParametersUseCase
    abstract val getInstancesUseCase: GetInstancesUseCase
    abstract val loginUseCase: LoginUseCase
    abstract val isLoggedInUseCase: IsLoggedInUseCase
}

@Scope
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class DomainScope
