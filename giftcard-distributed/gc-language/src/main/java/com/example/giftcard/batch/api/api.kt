package com.example.giftcard.batch.api

import org.axonframework.commandhandling.TargetAggregateIdentifier
import java.util.*

data class StartBatchCmd(@TargetAggregateIdentifier val id: UUID, val number: Int)
data class FindBatchSummariesQuery(val offset: Int, val limit: Int)
class CountBatchSummariesQuery { override fun toString() : String = "CountBatchSummariesQuery" }

data class BatchSummary(
    var id: UUID? = null,
    var nRequested: Int? = null,
    var nSucceeded: Int? = null,
    var nFailed: Int? = null
)
