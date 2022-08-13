package com.yavin.yavinintentapi.ui.main.api.v4.print

import com.google.gson.annotations.SerializedName

data class PrintResponseV4(
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("message")
    var message: String? = null
)
