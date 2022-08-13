package com.yavin.yavinintentapi.ui.main.api.v4.payment

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.yavin.yavinintentapi.ui.main.model.Customer
import com.yavin.yavinintentapi.ui.main.model.ReceiptTicket
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentResponseV4(
    @SerializedName("appVersion")
    var appVersion: String? = null,
    @SerializedName("amount")
    var amount: Int = 0,
    @SerializedName("cardToken")
    var cardToken: String? = null,
    @SerializedName("cartId")
    var cartId: String? = null,
    @SerializedName("clientCardTicket")
    var clientCardTicket: String? = null,
    @SerializedName("merchantCardTicket")
    var merchantCardTicket: String? = null,
    @SerializedName("currencyCode")
    var currencyCode: String? = null,
    @SerializedName("customer")
    var customer: Customer? = null,
    @SerializedName("giftAmount")
    var giftAmount: Int? = 0,
    @SerializedName("scheme")
    var scheme: String? = null,
    @SerializedName("issuer")
    var issuer: String? = null,
    @SerializedName("receiptTicket")
    var receiptTicket: ReceiptTicket? = null,
    @SerializedName("reference")
    var reference: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("transactionId")
    var transactionId: String? = null,
    @SerializedName("transactionType")
    var transactionType: String? = null
) : Parcelable