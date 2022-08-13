package com.example.paymenthandler.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReceiptTicket(
    @SerializedName("data")
    var `data`: String? = null,
    @SerializedName("format")
    var format: String? = null
) : Parcelable