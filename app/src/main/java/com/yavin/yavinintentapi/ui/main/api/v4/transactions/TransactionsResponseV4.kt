package com.yavin.yavinintentapi.ui.main.api.v4.transactions

import com.google.gson.annotations.SerializedName
import com.yavin.yavinintentapi.ui.main.api.v4.transactions.TransactionResponseV4

data class TransactionsResponseV4(
    @SerializedName("total")
    var total: Int? = null,
    @SerializedName("count")
    var count: Int? = null,
    @SerializedName("limit")
    var limit: Int? = null,
    @SerializedName("offset")
    var offset: Int? = null,
    @SerializedName("transactions")
    var transactions: List<TransactionResponseV4>? = null
)