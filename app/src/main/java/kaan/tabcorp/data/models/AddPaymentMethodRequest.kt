package kaan.tabcorp.data.models

import com.squareup.moshi.Json

data class SomeItem(
    @Json(name = "token")
    val token: String = "",

    @Json(name = "defaultPaymentMethodId")
    val defaultPaymentMethodId: String? = null
)

data class SetDefaultPaymentMethodRequest(
    @Json(name = "defaultPaymentMethodId")
    val defaultPaymentMethodId: String
)

data class DeletePaymentMethodRequest(
    @Json(name = "paymentMethodId")
    val paymentMethodId: String
)
