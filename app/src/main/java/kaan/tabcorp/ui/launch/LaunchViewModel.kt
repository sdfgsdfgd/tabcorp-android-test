package kaan.tabcorp.ui.launch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kaan.tabcorp.domain.spacex.SpacexRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(private val spacexRepository: SpacexRepository) : ViewModel() {

    init {
//        queryRocketDetails("5e9d0d95eda69974db09d1ed")
//        queryLaunchDetails()
    }



    fun queryRocketDetails(rocketId: String) {
        viewModelScope.launch {
            val rocket = spacexRepository.getRocketDetails(rocketId)



            Log.d("XXXXX", "!!! ROCKET !!!")
            Log.d("XXXXX", "${rocket?.name}")
            Log.d("XXXXX", "${rocket?.type}")
            Log.d("XXXXX", "${rocket?.active}")
            Log.d("XXXXX", "${rocket?.boosters}")
            Log.d("XXXXX", "${rocket?.country}")
            Log.d("XXXXX", "${rocket?.description}")
        }
    }


    //      =============== todo =====================
    fun queryLaunchDetails(launchId: String) {
//        viewModelScope.launch {
//            spacexRepository.getLaunchDetails(rocketId)
//        }
    }
}