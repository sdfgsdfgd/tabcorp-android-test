package kaan.tabcorp.ui.spacex

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kaan.tabcorp.R
import kaan.tabcorp.domain.spacex.SpacexRepository
import kaan.tabcorp.utilities.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModel @Inject constructor(private val spacexRepository: SpacexRepository) : ViewModel() {

    private var portrait = false

    private val _filterSuccessfulLaunches = SingleLiveEvent<Boolean>()
    val filterSuccessfulLaunches: SingleLiveEvent<Boolean> = _filterSuccessfulLaunches

    private val _sortClicked = SingleLiveEvent<Unit>()
    val sortClicked: SingleLiveEvent<Unit> = _sortClicked

    private val _navigate = SingleLiveEvent<LaunchItem>()
    val navigate: LiveData<LaunchItem> = _navigate

    //TODO: Switch with Result object for error handling and loader indicators
    //      RecyclerView data
    val assets = spacexRepository.launches.asLiveData()
    val assetsDiff: DiffUtil.ItemCallback<LaunchItem> = LaunchDiffUtil()
    val assetsLayoutProvider: (LaunchItem) -> Int = { if (portrait) R.layout.asset_item_portrait else R.layout.asset_item_landscape }

    init {
        fetchLaunches()
    }

    fun onFilterClicked() {
        Log.d("XXXXXX", "=== FILTER SUCCESS CLICKED ===")

        viewModelScope.launch {
            spacexRepository.toggleSuccessfulLaunches()

            _filterSuccessfulLaunches.value = !(filterSuccessfulLaunches.value ?: false)
        }
    }

    fun onSortToggleClicked() {
        viewModelScope.launch {
            spacexRepository.toggleSortingByDateOrMission()

            _sortClicked.value = Unit
        }
    }

    fun setPortraitOrientation(isPortrait: Boolean) {
        this.portrait = isPortrait
    }

    private fun fetchLaunches() {
        viewModelScope.launch {
            spacexRepository.getLaunches(::onLaunchClick)
        }
    }

    private fun onLaunchClick(launchItem: LaunchItem) {
        _navigate.value = launchItem
    }
}

class LaunchDiffUtil : DiffUtil.ItemCallback<LaunchItem>() {
    override fun areItemsTheSame(oldItem: LaunchItem, newItem: LaunchItem) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: LaunchItem, newItem: LaunchItem) = false
}