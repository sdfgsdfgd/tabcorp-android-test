package kaan.tabcorp.data.models

import java.util.*

/**
 * UI object for populating the list content views.
 */
class LaunchItem(
    val title: String,
    val date: Date?,
    val success: Boolean?,
    val thumbnailImgUrl: String? // For displaying the thumbnail image of spacex launch badges with Picasso
)
