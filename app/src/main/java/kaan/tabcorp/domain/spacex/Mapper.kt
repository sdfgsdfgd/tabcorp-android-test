package kaan.tabcorp.domain.spacex

import kaan.tabcorp.data.models.LaunchItem
import kaan.tabcorp.data.models.LaunchesResponse
import java.lang.Math.abs
import java.text.SimpleDateFormat

// SpaceX - LaunchItems mapper
fun List<LaunchesResponse>.mapToLaunchItems(
    formatting: SimpleDateFormat,
    onLaunchClick: (input: LaunchItem) -> Unit
) = this.map { response ->
    LaunchItem(
        response.id ?: kotlin.math.abs(Math.random().hashCode()).toString(),
        response.details ?: "",
        response.date_utc?.let { formatting.parse(it) },
        response.success,
        response.links.patch.small,
        onLaunchClick
    )
}

