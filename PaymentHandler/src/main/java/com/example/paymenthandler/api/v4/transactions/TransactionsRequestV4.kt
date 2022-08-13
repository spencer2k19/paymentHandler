package com.example.paymenthandler.api.v4.transactions

import com.google.gson.annotations.SerializedName

data class TransactionsRequestV4(
    @SerializedName("startDate")
    var startDate: String? = null,

    @SerializedName("endDate")
    var endDate: String? = null,

    @SerializedName("limit")
    var limit: Int? = 20,

    @SerializedName("offset")
    var offset: Int? = 0,
)