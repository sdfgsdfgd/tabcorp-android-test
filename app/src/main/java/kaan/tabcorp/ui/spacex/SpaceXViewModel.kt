package kaan.tabcorp.ui.spacex

import androidx.lifecycle.*
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

    private val _navigate = SingleLiveEvent<LaunchItem>()
    val navigate: LiveData<LaunchItem> = _navigate


    //TODO: Switch with Result object for error handling and loader indicators

    // RecyclerView data
    val assets = spacexRepository.launches.asLiveData().map { launches ->
        launches.sortedBy { it.date }
    }
    val assetsDiff: DiffUtil.ItemCallback<LaunchItem> = LaunchDiffUtil()
    val assetsLayoutProvider: (LaunchItem) -> Int = { if (portrait) R.layout.asset_item_portrait else R.layout.asset_item_landscape }

    init {
        fetchLaunches()
    }

    fun onFilterClicked() {
        _filterSuccessfulLaunches.value = !(filterSuccessfulLaunches.value ?: false)

        spacexRepository.toggleSuccessfulLaunches()
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
    override fun areContentsTheSame(oldItem: LaunchItem, newItem: LaunchItem) = oldItem.id == newItem.id
}