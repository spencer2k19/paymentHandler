package com.yavin.yavinintentapi.ui.main.api.v4.print

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PrintRequestV4(
    @SerializedName("data")
    var `data`: String? = null,
    @SerializedName("format")
    var format: String? = null
) : Parcelable