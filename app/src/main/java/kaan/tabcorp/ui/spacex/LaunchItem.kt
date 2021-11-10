package kaan.tabcorp.ui.spacex

import java.io.Serializable
import java.util.*

/**
 * UI object for populating the SpaceX launches list view.
 */
class LaunchItem(
    val id: String,
    val rocketId: String,
    val flightNumber: Int?,
    val title: String,
    val date: Date?,
    val success: Boolean?,
    val thumbnailImgUrl: String?, // For displaying the thumbnail image of spacex launch badges with Picasso
    val onClickCallback: (item: LaunchItem) -> Unit
) : Serializable
