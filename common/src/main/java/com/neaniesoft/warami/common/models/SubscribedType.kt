package com.neaniesoft.warami.common.models

enum class SubscribedType(val value: String) {
    SUBSCRIBED("Subscribed"),
    NOT_SUBSCRIBED("NotSubscribed"),
    PENDING("Pending");

    companion object {
        fun parse(input: String): SubscribedType {
            return when (input.lowercase()) {
                "subscribed" -> SubscribedType.SUBSCRIBED
                "notsubscribed", "not_subscribed" -> SubscribedType.NOT_SUBSCRIBED
                "pending" -> SubscribedType.PENDING
                else -> throw IllegalArgumentException("Unexpected subscribed type value: $input")
            }
        }
    }
}
