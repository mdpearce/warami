package com.neaniesoft.warami.featurefeed

import com.neaniesoft.warami.common.models.SortType
import com.neaniesoft.warami.domain.usecases.BuildFullListOfSortTypesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SortTypeSelectableImpl(
    private val buildFullListOfSortTypes: BuildFullListOfSortTypesUseCase,
) : SortTypeSelectable {
    private val scope = CoroutineScope(Dispatchers.Default)

    private val _sortType: MutableStateFlow<SortType> = MutableStateFlow(SortType.ACTIVE)
    override val sortType: StateFlow<SortType> = _sortType.asStateFlow()
    private val _sortTypeMenuItems: MutableStateFlow<List<SortType>> = MutableStateFlow(emptyList())
    override val sortTypeMenuItems: StateFlow<List<SortType>> = _sortTypeMenuItems.asStateFlow()

    override fun onSortTypeChanged(sortType: SortType) {
        scope.launch {
            _sortTypeMenuItems.emit(emptyList())
            _sortType.emit(sortType)
        }
    }

    override fun onSortTypeButtonClicked() {
        scope.launch {
            _sortTypeMenuItems.emit(
                buildFullListOfSortTypes(),
            )
        }
    }

    override fun onSortTypeMenuDismissed() {
        scope.launch {
            _sortTypeMenuItems.emit(emptyList())
        }
    }
}
