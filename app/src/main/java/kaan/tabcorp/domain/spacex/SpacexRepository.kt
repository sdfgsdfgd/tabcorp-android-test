package kaan.tabcorp.domain.spacex

import android.util.Log
import kaan.tabcorp.data.SpaceXAPI
import kaan.tabcorp.data.models.LaunchDetails
import kaan.tabcorp.ui.launch.RocketItem
import kaan.tabcorp.ui.spacex.HeaderItem
import kaan.tabcorp.ui.spacex.LaunchItem
import kaan.tabcorp.utilities.DateTimeUtils.toYear
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpacexRepository @Inject constructor(private val spaceXAPI: SpaceXAPI) {
    private val _launchesAll = MutableStateFlow<List<LaunchItem>>(listOf())
    private val _launches = MutableStateFlow<List<LaunchItem>>(listOf())
    val launches = _launches.asStateFlow()

    private val _isSortByDate = MutableStateFlow(true)
    private val _isFilterSuccess = MutableStateFlow(false)

    suspend fun toggleSuccessfulLaunches() {
        withContext(Dispatchers.Default) {
            _launches.value =
                (if (!_isFilterSuccess.value)
                    _launches.value.filterNot { it.success != true }
                else
                    _launchesAll.value)
            _isFilterSuccess.value = !_isFilterSuccess.value
        }
        check()
    }

    suspend fun toggleSortingByDateOrMission() {
        withContext(Dispatchers.Default) {  // Offload filtering / sorting operations to Default background thread.
            if (_isSortByDate.value)
                _launches.value = _launches.value
                    .sortedBy { it.missionName } //.groupBy { }         // Group by first Letter
                    .also {
                        it.groupBy { it.missionName[0] }.map { (missionNameLetter, launch) ->

                            listOf(HeaderItem(missionNameLetter.toString())) + launch

//                            Log.d("XXXXXXXX", "Mission Name Letter: $missionNameLetter ${launch.map { Pair(it.missionName, it.date?.toYear()) }}")
                        }.also {
                            it.forEach { it.forEach {
                                Log.d("YYYYYY", "-yyyyy--> ${(it as? LaunchItem)?.missionName ?: (it as? HeaderItem)?.text}")
                            } }
                        }
                    }
            else
                _launches.value = _launches.value
                    .sortedBy { it.date }
                    .also {
                        it.groupBy { it.date?.toYear() ?: 0 }.map { (year, launch) ->
                            listOf(HeaderItem(year.toString())) + launch
                        }
                            .also {
                                it.forEach {
                                    it.forEach {
                                        Log.d("YYYYYY", "-xxxxxxxxxxxxxxxxxxxxxxxx--> ${(it as? LaunchItem)?.missionName ?: (it as? HeaderItem)?.text}")
                                    }
                                }
                            }
                    }

            _isSortByDate.value = !_isSortByDate.value
        }
        check() //XXX todo REMOVE
    }

    private fun check() {
        Log.d("XXXXXX", "launchesAll contains fails: ${_launchesAll.value.any { it.success == false }}")
        Log.d("XXXXXX", "launchesAll[0..3].success: ")
        _launchesAll.value.subList(0, 3).forEach {
            Log.d("XXXXXX", "------> ${it.id} - ${it.success}")
        }
        Log.d("XXXXXX", "launches[0..3].success: ")
        _launches.value.subList(0, 3).forEach {
            Log.d("XXXXXX", "------> ${it.id} - ${it.success}")
        }
        Log.d("XXXXXX", "launches contains fails: ${_launches.value.any { it.success == false }}")
    }

    suspend fun getLaunches(onLaunchClick: (input: LaunchItem) -> Unit) = withContext(Dispatchers.IO) {
        try {
            _launchesAll.value = spaceXAPI.getLaunches().mapToLaunchItems(sf, onLaunchClick).sortedBy { it.date }
            _launches.value = _launchesAll.value.toMutableList()

            _launchesAll.value.forEach {
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getLaunchDetails(launchId: String): LaunchDetails? {
        return try {
            spaceXAPI.getLaunchDetails(launchId)
        } catch (e: Exception) {
            e.printStackTrace()

            null
        }
    }

    suspend fun getRocketDetails(rocketId: String): RocketItem? {
        return try {
            spaceXAPI.getRocketDetails(rocketId).mapToRocketItem()
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
