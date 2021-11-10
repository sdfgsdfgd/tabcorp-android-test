package kaan.tabcorp.ui.launch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kaan.tabcorp.domain.spacex.SpacexRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(private val spacexRepository: SpacexRepository) : ViewModel() {

    init {
        queryLaunchDetails()
        queryRocketDetails()
    }

    private fun getLaunches() {
        viewModelScope.launch {
//            spacexRepository.getLaunches()
        }
    }
}