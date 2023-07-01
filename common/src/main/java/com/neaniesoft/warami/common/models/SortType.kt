package com.neaniesoft.warami.common.models

enum class SortType(val value: String) {
    HOT("Hot"),
    NEW("New"),
    OLD("Old"),
    ACTIVE("Active"),
    TOP_DAY("TopDay"),
    TOP_WEEK("TopWeek"),
    TOP_MONTH("TopMonth"),
    TOP_YEAR("TopYear"),
    TOP_ALL("TopAll"),
    MOST_COMMENTS("MostComments"),
    NEW_COMMENTS("NewComments"),
    TOP_HOUR("TopHour"),
    TOP_SIX_HOUR("TopSixHour"),
    TOP_TWELVE_HOUR("TopTwelveHour"),
    TOP_THREE_MONTHS("TopThreeMonths"),
    TOP_SIX_MONTHS("TopSixMonths"),
    TOP_NINE_MONTHS("TopNineMonths"),
}
