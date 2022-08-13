package com.example.paymenthandler.api.v4.payment

import android.os.Parcelable
import com.example.paymenthandler.model.Customer
import com.example.paymenthandler.model.ReceiptTicket
import com.example.paymenthandler.model.Vendor
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentRequestV4(
    @SerializedName("amount")
    var amount: Int = 0,
    @SerializedName("cartId")
    var cartId: String? = null,
    @SerializedName("currencyCode")
    var currencyCode: String? = null,
    @SerializedName("customer")
    var customer: Customer? = null,
    @SerializedName("giftAmount")
    var giftAmount: Int? = 0,
    @SerializedName("medium")
    var medium: String? = null,
    @SerializedName("prepayScreen")
    var prepayScreen: String? = null,
    @SerializedName("receiptTicket")
    var receiptTicket: ReceiptTicket? = null,
    @SerializedName("receiptTicketJson")
    var receiptTicketJson: String? = null,
    @SerializedName("reference")
    var reference: String? = null,
    @SerializedName("transactionType")
    var transactionType: String? = null,
    @SerializedName("vendor")
    var vendor: Vendor? = null
) : Parcelable