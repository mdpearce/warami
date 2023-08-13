package com.neaniesoft.warami.featurefeed

import com.neaniesoft.warami.common.models.SortType
import kotlinx.coroutines.flow.StateFlow

interface SortTypeSelectable {
    val sortType: StateFlow<SortType>
    val sortTypeMenuItems: StateFlow<List<SortType>>
    fun onSortTypeChanged(sortType: SortType)
    fun onSortTypeButtonClicked()
    fun onSortTypeMenuDismissed()
}
