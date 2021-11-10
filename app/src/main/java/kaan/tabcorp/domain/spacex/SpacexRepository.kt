package kaan.tabcorp.domain.spacex

import kaan.tabcorp.data.BFFApi
import kaan.tabcorp.ui.launch.RocketItem
import kaan.tabcorp.ui.spacex.LaunchItem
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
    private val _launchesAll = MutableStateFlow<List<LaunchItem>>(listOf())
    private val _launches = MutableStateFlow<List<LaunchItem>>(listOf())
    val launches = _launches.asStateFlow()

    fun toggleSuccessfulLaunches() {
        _launches.value =
            if (_launches.value.any { it.success != true })
                _launches.value.filterNot { it.success != true }
            else
                _launchesAll.value
    }

    suspend fun getLaunches(onLaunchClick: (input: LaunchItem) -> Unit) = withContext(Dispatchers.IO) {
        try {
            _launchesAll.value = bffApi.getLaunches().mapToLaunchItems(sf, onLaunchClick).sortedBy { it.date }
            _launches.value = _launchesAll.value.toMutableList()

            _launchesAll.value.forEach {
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    suspend fun getLaunchDetails(launchId: String) {
        bffApi.getLaunchDetails(launchId)
    }

    suspend fun getRocketDetails(rocketId: String): RocketItem? {
        return try {
            bffApi.getRocketDetails(rocketId).mapToRocketItem()
        } catch (e: Exception) {
            e.printStackTrace()

            null
        }
    }

    companion object {
        private val sf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }
}
