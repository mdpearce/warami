package com.neaniesoft.warami.domain.usecases

import com.neaniesoft.warami.common.models.SortType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BuildFullListOfSortTypesUseCase @Inject constructor() {
    operator fun invoke(): List<SortType> = listOf(
        SortType.ACTIVE,
        SortType.HOT,
        SortType.NEW,
        SortType.OLD,
        SortType.MOST_COMMENTS,
        SortType.NEW_COMMENTS,
        SortType.TOP_HOUR,
        SortType.TOP_SIX_HOUR,
        SortType.TOP_TWELVE_HOUR,
        SortType.TOP_DAY,
        SortType.TOP_WEEK,
        SortType.TOP_MONTH,
        SortType.TOP_THREE_MONTHS,
        SortType.TOP_SIX_MONTHS,
        SortType.TOP_NINE_MONTHS,
        SortType.TOP_YEAR,
        SortType.TOP_ALL,
    )
}
