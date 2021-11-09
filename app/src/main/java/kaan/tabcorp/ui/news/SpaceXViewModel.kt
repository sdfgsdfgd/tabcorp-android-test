package kaan.tabcorp.ui.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kaan.tabcorp.domain.flights.SpacexRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModel @Inject constructor(private val spacexRepository: SpacexRepository) : ViewModel() {

    private var portrait = false

//    val launches = MutableLiveData<List<Launch>>>()

//    val assets = MutableLiveData<List<Any>>()
//    val assetsDiff: DiffUtil.ItemCallback<Asset> = NegativeDiffCallback()
//    val assetsLayoutProvider: (Asset) -> Int = { if (portrait) R.layout.asset_item_portrait else R.layout.asset_item_landscape }


    init {
        Log.d("XXX", "====== - ======")

        getLaunches()
    }

    fun setPortraitOrientation(isPortrait: Boolean) {
        this.portrait = isPortrait
    }


    private fun getLaunches() {
        viewModelScope.launch {
            spacexRepository.getLaunches()
        }
    }
}