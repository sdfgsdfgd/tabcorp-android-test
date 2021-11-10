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
    private val _launchesAll = MutableStateFlow<List<LaunchItem>>(listOf())
    private val _launches = MutableStateFlow<List<LaunchItem>>(listOf())
    val launches = _launches.asStateFlow()

    fun toggleSuccessfulLaunches() {
        _launches.value =
            if (_launches.value.any { it.success != true })
                _launches.value.filterNot { it.success != true }
            else
                _launchesAll.value


        Log.d("XXX", "_launchesAll contain fails ? [ ${_launchesAll.value.any { it.success == false}} ]")
        Log.d("XXX", "_launches contain fails ? [ ${_launches.value.any { it.success == false}} ]")
        Log.d("XXX", "launches contain fails ? [ ${launches.value.any { it.success == false}} ]")
    }

    suspend fun getLaunches() = withContext(Dispatchers.IO) {
        try {
            _launchesAll.value = bffApi.getLaunches().mapToLaunchItems(sf).sortedBy { it.date }
            _launches.value = _launchesAll.value.toMutableList()
        } catch (e: Exception) {
            Log.d("XXX", "Error retrieving launches: $e")
        }

        _launches.value.forEach {
            Log.d("XXX", "----> [${it.id}]")
        }
    }

    companion object {
        private val sf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }
}
