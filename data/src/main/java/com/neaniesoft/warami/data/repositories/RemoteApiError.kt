package com.neaniesoft.warami.data.repositories

data class RemoteApiError(val httpCode: Int, val errorBody: String) :
    Exception("Remote API error. Http code: $httpCode")
