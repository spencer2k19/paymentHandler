package com.yavin.yavinintentapi.ui.main.model

enum class ApiVersion(val version: String) {
    V1("v1"),
    V4("v4"),
    UNKNOWN("unknown");

    companion object {
        fun fromCode(code: String?): ApiVersion {
            for (apiVersion in values()) {
                if (apiVersion.version.equals(code, true)) {
                    return apiVersion
                }
            }

            return UNKNOWN
        }
    }
}