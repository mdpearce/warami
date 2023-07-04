package com.neaniesoft.warami.featurefeed.models

sealed class RefreshingIndicator
object NotRefreshing : RefreshingIndicator()
object Refreshing : RefreshingIndicator()
data class RefreshingWithPercentage(val percentComplete: Int) : RefreshingIndicator()
