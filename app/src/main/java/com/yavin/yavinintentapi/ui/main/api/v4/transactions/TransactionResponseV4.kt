package com.yavin.yavinintentapi.ui.main.api.v4.transactions

import com.google.gson.annotations.SerializedName
import com.yavin.yavinintentapi.ui.main.model.Customer

data class TransactionResponseV4(
    @SerializedName("appVersion")
    var appVersion: String? = null,
    @SerializedName("createdAt")
    var createdAt: String? = null,
    @SerializedName("amount")
    var amount: Int? = null,
    @SerializedName("giftAmount")
    var giftAmount: Int? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("transactionId")
    var transactionId: String? = null,
    @SerializedName("scheme")
    var scheme: String? = null,
    @SerializedName("issuer")
    var issuer: String? = null,
    @SerializedName("cartId")
    var cartId: String? = null,
    @SerializedName("transactionType")
    var transactionType: String? = null,
    @SerializedName("reference")
    var reference: String? = null,
    @SerializedName("customer")
    var customer: Customer? = null,
    @SerializedName("currencyCode")
    var currencyCode: String? = null
)