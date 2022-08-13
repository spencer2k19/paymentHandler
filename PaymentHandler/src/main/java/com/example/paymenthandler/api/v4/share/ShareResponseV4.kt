package com.example.paymenthandler.api.v4.share

import com.google.gson.annotations.SerializedName

data class ShareResponseV4(
    @SerializedName("status")
    var status: String?,
    @SerializedName("message")
    var message: String? = null
)