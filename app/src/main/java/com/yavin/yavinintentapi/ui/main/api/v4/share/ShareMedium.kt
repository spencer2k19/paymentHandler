package com.yavin.yavinintentapi.ui.main.api.v4.share

enum class ShareMedium(val code: String) {
    PRINT("print"),
    SMS("sms"),
    EMAIL("email"),
    UNDEFINED("undefined");

    companion object {
        fun fromCode(code: String?): ShareMedium {
            if (code == null) {
                return UNDEFINED
            }

            for (type in values()) {
                if (type.code.equals(code, ignoreCase = true)) {
                    return type
                }
            }

            return UNDEFINED
        }
    }
}