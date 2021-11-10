package kaan.tabcorp.ui.spacex

import android.util.Log
import androidx.lifecycle.*
import androidx.recyclerview.widget.DiffUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kaan.tabcorp.R
import kaan.tabcorp.data.models.LaunchItem
import kaan.tabcorp.domain.spacex.SpacexRepository
import kaan.tabcorp.utilities.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModel @Inject constructor(private val spacexRepository: SpacexRepository) : ViewModel() {

    private var portrait = false

    private val _filterSuccessfulLaunches = MutableLiveData<Boolean>(false)
    val filterSuccessfulLaunches: LiveData<Boolean> = _filterSuccessfulLaunches

    private val _navigate = SingleLiveEvent<Unit>()
    val navigate: LiveData<Unit> = _navigate

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
        _filterSuccessfulLaunches.value = !filterSuccessfulLaunches.value!!

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
        Log.d("XXX", "Launch Clicked Description: ${launchItem.title}")

        _navigate.value = Unit
    }
}

class LaunchDiffUtil : DiffUtil.ItemCallback<LaunchItem>() {
    override fun areItemsTheSame(oldItem: LaunchItem, newItem: LaunchItem) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: LaunchItem, newItem: LaunchItem) = oldItem.id == newItem.id
}