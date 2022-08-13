package com.yavin.yavinintentapi.ui.main.api.v1.payment


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class PaymentResponseV1(
    @SerializedName("amount")
    var amount: Int? = null,
    @SerializedName("cardToken")
    var cardToken: String? = null,
    @SerializedName("clientTicket")
    var clientTicket: String? = null,
    @SerializedName("giftAmount")
    var giftAmount: Int? = null,
    @SerializedName("reference")
    var reference: String? = null,
    @SerializedName("signatureRequired")
    var signatureRequired: Boolean? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("totalAmount")
    var totalAmount: Int? = null,
    @SerializedName("transactionId")
    var transactionId: String? = null,
) : Parcelable