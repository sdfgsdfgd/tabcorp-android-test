package kaan.tabcorp.domain.spacex

import android.util.Log
import kaan.tabcorp.data.BFFApi
import kaan.tabcorp.data.models.LaunchItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpacexRepository @Inject constructor(private val bffApi: BFFApi) {

    private val _launches = MutableStateFlow<List<LaunchItem>>(listOf())
    val launches = _launches.asStateFlow()

    suspend fun getLaunches() = withContext(Dispatchers.IO) {
        try {
            _launches.value = bffApi.getLaunches().mapToLaunchItems(sf)
        } catch (e: Exception) {
            Log.d("XXX", "Error retrieving launches: $e")
        }
    }

    companion object {
        private val sf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }
}
