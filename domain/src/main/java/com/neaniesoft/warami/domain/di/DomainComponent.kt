package com.neaniesoft.warami.domain.di

import android.content.Context
import com.neaniesoft.warami.data.di.DatabaseComponent
import com.neaniesoft.warami.domain.usecases.BuildPostSearchParametersUseCase
import com.neaniesoft.warami.domain.usecases.GetInstancesDownloadUrlUseCase
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope

@Component
@DomainScope
abstract class DomainComponent(
    @Component val dataComponent: DatabaseComponent,
) {
    @Provides
    @DomainScope
    fun provideContext(): Context = dataComponent.provideContext()

    abstract val buildPostSearchParametersUseCase: BuildPostSearchParametersUseCase
    abstract val getInstancesDownloadUrlUseCase: GetInstancesDownloadUrlUseCase
}

@Scope
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class DomainScope
