package com.example.giftcard.api

import java.time.Instant

data class FindCardSummariesQuery(val offset: Int, val limit: Int)
data class FindCardSummariesResponse(val data: List<CardSummary>)

class CountCardSummariesQuery { override fun toString() : String = "CountCardSummariesQuery" }
data class CountCardSummariesResponse(val count: Int)

data class CardSummary(
        var id: String? = null,
        var initialValue: Int? = null,
        var issuedAt: Instant? = null,
        var remainingValue: Int? = null
        )
