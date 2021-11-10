package kaan.tabcorp.data

import kaan.tabcorp.data.models.LaunchesResponse
import retrofit2.http.GET

interface BFFApi {

    // region In app messaging
    @GET(ENDPOINT_LAUNCHES)
    suspend fun getLaunches(): List<LaunchesResponse>
    // endregion

    companion object {
        private const val ENDPOINT_LAUNCHES: String = "v5/launches"
        private const val ENDPOINT_LAUNCH_NEXT: String = "launches/next"


    }
}
