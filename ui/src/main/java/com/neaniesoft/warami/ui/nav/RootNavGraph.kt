package com.neaniesoft.warami.ui.nav

import com.neaniesoft.warami.featurefeed.FeaturefeedNavGraph
import com.neaniesoft.warami.signin.SigninNavGraph
import com.neaniesoft.warami.ui.UiNavGraph
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route

object RootNavGraph : NavGraphSpec {
    override val destinationsByRoute: Map<String, DestinationSpec<*>> = emptyMap()
    override val route: String = "root"
    override val startRoute: Route = UiNavGraph
    override val nestedNavGraphs: List<NavGraphSpec> = listOf(
        UiNavGraph,
        FeaturefeedNavGraph,
        SigninNavGraph,
    )
}
