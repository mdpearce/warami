package com.neaniesoft.warami.domain.di

import com.neaniesoft.warami.data.di.DatabaseComponent
import com.neaniesoft.warami.domain.usecases.BuildPostSearchParametersUseCase
import com.neaniesoft.warami.domain.usecases.GetPostsForSearchParamsUseCase
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Scope

@Component
@DomainScope
abstract class DomainComponent(
    @Component val dataComponent: DatabaseComponent,
) {
    abstract val buildPostSearchParametersUseCase: BuildPostSearchParametersUseCase
    abstract val getPostsForSearchParamsUseCase: GetPostsForSearchParamsUseCase
}

@Scope
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
annotation class DomainScope
