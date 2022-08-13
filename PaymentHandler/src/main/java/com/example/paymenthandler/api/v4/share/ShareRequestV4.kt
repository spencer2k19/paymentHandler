package com.example.paymenthandler.api.v4.share

import com.example.paymenthandler.model.Customer
import com.example.paymenthandler.model.ReceiptTicket
import com.google.gson.annotations.SerializedName

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
