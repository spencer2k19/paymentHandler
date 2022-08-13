package com.example.paymenthandler.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Vendor(
    @SerializedName("softwareName")
    var softwareName: String? = null,
    @SerializedName("softwareVersion")
    var softwareVersion: String? = null
) : Parcelable