package kaan.tabcorp.domain.spacex

import kaan.tabcorp.data.SpaceXAPI
import kaan.tabcorp.data.models.LaunchDetails
import kaan.tabcorp.ui.launch.RocketItem
import kaan.tabcorp.ui.spacex.HeaderItem
import kaan.tabcorp.ui.spacex.LaunchItem
import kaan.tabcorp.ui.spacex.ListItem
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
    private val _launchesAll = MutableStateFlow<List<ListItem>>(listOf())       // Cache/stateflow of original items
    private val _launches = MutableStateFlow<List<ListItem>>(listOf())          // Cache/stateflow of sorted/filtered items
    val launches = _launches.asStateFlow()

    private val _isSortByDate = MutableStateFlow(true)          // Current active sorting:      date / mission first letter
    private val _isFilterSuccess = MutableStateFlow(false)      // Current active filtering     show all / show only success


    // Sort to current active sort setting
    private fun List<ListItem>.sort() = if (_isSortByDate.value) dateSortYearGroup() else missionSortFirstLetterGroup()

    // Sort by Mission  &  Group by Mission First Letter
    private fun List<ListItem>.missionSortFirstLetterGroup() =
        asSequence()
            .filterIsInstance<LaunchItem>()
            .sortedBy { it.missionName } //
            .groupBy { it.missionName[0] }
            .map { (missionNameLetter, launch) ->
                mutableListOf(HeaderItem(missionNameLetter.toString())) + launch
            }.flatten()

    // Sort by Date  &  Group by Year
    private fun List<ListItem>.dateSortYearGroup() =
        asSequence()
            .filterIsInstance<LaunchItem>()
            .sortedBy { it.date }
            .groupBy { it.date?.toYear() ?: 0 }
            .map { (year, launch) ->
                mutableListOf(HeaderItem(year.toString())) + launch
            }.flatten()

    /**
     * Toggles displaying all launches or only the successful launches.
     */
    suspend fun toggleSuccessfulLaunches() {
        withContext(Dispatchers.IO) {
            _launches.value =
                if (!_isFilterSuccess.value)
                    _launches.value.filter { it is LaunchItem && it.success == true }.sort()
                else
                    _launchesAll.value.sort()

            _isFilterSuccess.value = !_isFilterSuccess.value
        }
    }

    /**
     * Toggles sorting by date or mission.
     */
    suspend fun toggleSortingByDateOrMission() {
        withContext(Dispatchers.IO) {  // Offload filtering / sorting operations to Default background thread.
            if (_isSortByDate.value) {  // Group everything by mission name letter
                _launches.value = _launches.value.missionSortFirstLetterGroup()
            } else                      // Group everything by year
                _launches.value = _launches.value.dateSortYearGroup()
            _isSortByDate.value = !_isSortByDate.value
        }
    }

    // 1- The initial get all launches call - @SpaceXViewModel
    suspend fun getLaunches(onLaunchClick: (input: LaunchItem) -> Unit) = withContext(Dispatchers.IO) {
        try {
            _launchesAll.value = spaceXAPI.getLaunches().mapToLaunchItems(sf, onLaunchClick).sort()
            _launches.value = _launchesAll.value.toMutableList()

            _launchesAll.value.forEach {
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 2- Get launch details call - @LaunchViewModel
    suspend fun getLaunchDetails(launchId: String): LaunchDetails? {
        return try {
            spaceXAPI.getLaunchDetails(launchId)
        } catch (e: Exception) {
            e.printStackTrace()

            null
        }
    }

    // 3- Get rocket details call - @LaunchViewModel
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
