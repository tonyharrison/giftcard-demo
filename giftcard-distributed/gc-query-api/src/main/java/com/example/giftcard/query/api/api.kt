package com.example.giftcard.query.api

import java.time.Instant

data class FindCardSummariesQuery(val offset: Int, val limit: Int)
class CountCardSummariesQuery { override fun toString() : String = "CountCardSummariesQuery" }

data class CardSummary(
        var id: String? = null,
        var initialValue: Int? = null,
        var issuedAt: Instant? = null,
        var remainingValue: Int? = null
        )
