package com.yavin.yavinintentapi.ui.main.api.v4.share

import com.google.gson.annotations.SerializedName
import com.yavin.yavinintentapi.ui.main.model.Customer
import com.yavin.yavinintentapi.ui.main.model.ReceiptTicket

data class ShareRequestV4(
    @SerializedName("receiptTicket")
    var receiptTicket: ReceiptTicket?,
    @SerializedName("transactionId")
    var transactionId: String?,
    @SerializedName("medium")
    var medium: String?,
    @SerializedName("customer")
    var customer: Customer?
)
