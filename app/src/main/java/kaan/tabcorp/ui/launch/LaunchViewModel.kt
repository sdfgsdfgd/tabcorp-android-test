package kaan.tabcorp.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kaan.tabcorp.data.models.LaunchDetails
import kaan.tabcorp.data.models.LaunchResponse
import kaan.tabcorp.domain.spacex.SpacexRepository
import kaan.tabcorp.ui.spacex.LaunchItem
import kaan.tabcorp.utilities.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(private val spacexRepository: SpacexRepository) : ViewModel() {

    private val _rocketDetails = SingleLiveEvent<RocketItem>()
    val rocketDetails: LiveData<RocketItem> = _rocketDetails

    private val _launchDetails = SingleLiveEvent<LaunchDetails?>()
    val launchDetails: LiveData<LaunchDetails?> = _launchDetails

    fun queryRocketDetails(rocketId: String) {
        viewModelScope.launch {
            _rocketDetails.postValue(spacexRepository.getRocketDetails(rocketId))
        }
    }

    fun queryLaunchDetails(launchId: String) {
        viewModelScope.launch {
            _launchDetails.postValue(spacexRepository.getLaunchDetails(launchId))
        }
    }
}