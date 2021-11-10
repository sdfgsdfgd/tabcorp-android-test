package kaan.tabcorp.data.models

import kaan.tabcorp.data.models.Payload

data class SecondStage(
    val block: Int,
    val payloads: List<Payload>
)