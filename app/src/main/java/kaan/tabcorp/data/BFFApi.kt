package kaan.tabcorp.data

import kaan.tabcorp.data.models.LaunchesResponse
import kaan.tabcorp.data.models.RocketResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BFFApi {

    // region Launches
    @GET(ENDPOINT_LAUNCHES)
    suspend fun getLaunches(): List<LaunchesResponse>
    // endregion

    // region Launch Details
    @GET(ENDPOINT_LAUNCH_DETAILS)
    suspend fun getLaunchDetails(
        @Path("launchId") launchId: String,
//        @Body pumpRequest: LaunchDetailsRequest,
    ): Any

    @GET(ENDPOINT_ROCKET_DETAILS)
    suspend fun getRocketDetails(
        @Path("rocketId") rocketId: String,
//        @Body pumpRequest: RocketDetailsRequest,
    ): RocketResponse
    // endregion

    companion object {
        private const val ENDPOINT_LAUNCHES: String = "v5/launches"
        private const val ENDPOINT_LAUNCH_DETAILS = "v4/launch"
        private const val ENDPOINT_ROCKET_DETAILS = "v4/rockets/{rocketId}"


        private const val ENDPOINT_LAUNCH_NEXT: String = "launches/next"
    }
}
