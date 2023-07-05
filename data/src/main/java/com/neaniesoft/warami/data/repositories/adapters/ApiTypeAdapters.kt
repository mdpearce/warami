package com.neaniesoft.warami.data.repositories.adapters

import com.neaniesoft.warami.api.models.ListingType
import com.neaniesoft.warami.api.models.SortType
import com.neaniesoft.warami.data.repositories.ApiSortType
import com.neaniesoft.warami.data.repositories.DomainSortType

typealias ApiListingType = ListingType
typealias DomainListingType = com.neaniesoft.warami.common.models.ListingType

fun ApiListingType.toDomain(): DomainListingType {
    return when (this) {
        ListingType.subscribed -> DomainListingType.SUBSCRIBED
        ListingType.all -> DomainListingType.ALL
        ListingType.local -> DomainListingType.LOCAL
    }
}

fun DomainListingType.toApi(): ApiListingType = when (this) {
    com.neaniesoft.warami.common.models.ListingType.SUBSCRIBED -> ApiListingType.subscribed
    com.neaniesoft.warami.common.models.ListingType.ALL -> ApiListingType.all
    com.neaniesoft.warami.common.models.ListingType.LOCAL -> ApiListingType.local
}

fun DomainSortType.toApi(): ApiSortType = when (this) {
    com.neaniesoft.warami.common.models.SortType.HOT -> SortType.hot
    com.neaniesoft.warami.common.models.SortType.NEW -> SortType.new
    com.neaniesoft.warami.common.models.SortType.OLD -> SortType.old
    com.neaniesoft.warami.common.models.SortType.ACTIVE -> SortType.active
    com.neaniesoft.warami.common.models.SortType.TOP_DAY -> SortType.topDay
    com.neaniesoft.warami.common.models.SortType.TOP_WEEK -> SortType.topWeek
    com.neaniesoft.warami.common.models.SortType.TOP_MONTH -> SortType.topMonth
    com.neaniesoft.warami.common.models.SortType.TOP_YEAR -> SortType.topYear
    com.neaniesoft.warami.common.models.SortType.TOP_ALL -> SortType.topAll
    com.neaniesoft.warami.common.models.SortType.MOST_COMMENTS -> SortType.mostComments
    com.neaniesoft.warami.common.models.SortType.NEW_COMMENTS -> SortType.newComments
    com.neaniesoft.warami.common.models.SortType.TOP_HOUR -> SortType.topHour
    com.neaniesoft.warami.common.models.SortType.TOP_SIX_HOUR -> SortType.topSixHour
    com.neaniesoft.warami.common.models.SortType.TOP_TWELVE_HOUR -> SortType.topTwelveHour
    com.neaniesoft.warami.common.models.SortType.TOP_THREE_MONTHS -> SortType.topThreeMonths
    com.neaniesoft.warami.common.models.SortType.TOP_SIX_MONTHS -> SortType.topSixMonths
    com.neaniesoft.warami.common.models.SortType.TOP_NINE_MONTHS -> SortType.topNineMonths
}
