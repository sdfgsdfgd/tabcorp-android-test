package kaan.tabcorp.domain.spacex

import kaan.tabcorp.data.models.LaunchItem
import kaan.tabcorp.data.models.LaunchesResponse
import java.text.SimpleDateFormat

// SpaceX - LaunchItems mapper
fun List<LaunchesResponse>.mapToLaunchItems(formatting: SimpleDateFormat) = this.map { response ->
    LaunchItem(
        response.id ?: Math.random().hashCode().toString(),
        response.details ?: "",
        response.date_utc?.let { formatting.parse(it) },
        response.success,
        response.links.patch.small
    )
}

