package com.yavin.yavinintentapi.ui.main.api.v1.payment

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentRequestV1(
    @SerializedName("amount")
    var amount: String? = null,
    @SerializedName("cartId")
    var cartId: String? = null,
    @SerializedName("client")
    var client: String? = null,
    @SerializedName("currency")
    var currency: String? = null,
    @SerializedName("forcePayment")
    var forcePayment: Boolean? = null,
    @SerializedName("medium")
    var medium: String? = null,
    @SerializedName("receiptTicket")
    var receiptTicket: List<String>? = null,
    @SerializedName("reference")
    var reference: String? = null,
    @SerializedName("showPrepay")
    var showPrepay: Boolean? = null,
    @SerializedName("showPostpay")
    var showPostpay: Boolean? = null,
    @SerializedName("showOnlyLastPostpay")
    var showOnlyLastPostpay: Boolean? = null,
    @SerializedName("transactionType")
    var transactionType: String? = null,
    @SerializedName("vendorToken")
    var vendorToken: String? = null
) : Parcelable