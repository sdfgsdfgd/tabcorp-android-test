package kaan.tabcorp.ui.spacex

import android.util.Log
import androidx.lifecycle.*
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kaan.tabcorp.R
import kaan.tabcorp.bindings.recyclerview.NegativeDiffCallback
import kaan.tabcorp.data.models.LaunchItem
import kaan.tabcorp.domain.spacex.SpacexRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModel @Inject constructor(private val spacexRepository: SpacexRepository) : ViewModel() {

    private var portrait = false

    val launches  = spacexRepository.launches.asLiveData()

    // RecyclerView data
    val assets = launches.map { launches ->
        launches.sortedBy { it.date }
    }
    val assetsDiff: DiffUtil.ItemCallback<LaunchItem> = NegativeDiffCallback()
    val assetsLayoutProvider: (LaunchItem) -> Int = { if (portrait) R.layout.asset_item_portrait else R.layout.asset_item_landscape }

    init {
        getLaunches()
    }

    fun onFilterClicked() {
        Log.d("XXX", "============ filter clicked ============")
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