package kaan.tabcorp.domain.spacex

import kaan.tabcorp.ui.spacex.LaunchItem
import kaan.tabcorp.data.models.LaunchResponse
import kaan.tabcorp.data.models.RocketResponse
import kaan.tabcorp.ui.launch.RocketItem
import java.text.SimpleDateFormat

// SpaceX -   LaunchesResponse -> LaunchItems   mapper
fun List<LaunchResponse>.mapToLaunchItems(
    formatting: SimpleDateFormat,
    onLaunchClick: (input: LaunchItem) -> Unit
) = this.map { response ->
    LaunchItem(
        response.id ?: kotlin.math.abs(Math.random().hashCode()).toString(),
        response.rocket ?: "",
        response.name.orEmpty(),
        response.flight_number,
        response.details ?: "",
        response.date_utc?.let { formatting.parse(it) },
        response.success,
        response.links.patch.small,
        onLaunchClick
    )
}

// SpaceX -   RocketResponse -> RocketItem   mapper
fun RocketResponse.mapToRocketItem() = RocketItem(id, name, type, stages, active, boosters, costPerLaunch, successRatePct, country, company, description)