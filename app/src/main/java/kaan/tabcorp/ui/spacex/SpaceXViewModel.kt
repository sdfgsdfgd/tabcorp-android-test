package kaan.tabcorp.ui.spacex

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
    private var portrait = false    //todo: Possible future portrait design implementations...

    private val _filterSuccessfulLaunches = SingleLiveEvent<Boolean>()
    val filterSuccessfulLaunches: SingleLiveEvent<Boolean> = _filterSuccessfulLaunches

    private val _sortClicked = SingleLiveEvent<Unit>()
    val sortClicked: SingleLiveEvent<Unit> = _sortClicked

    private val _navigate = SingleLiveEvent<LaunchItem>()
    val navigate: LiveData<LaunchItem> = _navigate

    /**
     * 3 adapter bindings for the launches list
     */
    val assets = spacexRepository.launches.asLiveData()
    val assetsDiff: DiffUtil.ItemCallback<ListItem> = LaunchDiffUtil()
    val assetsLayoutProvider: (ListItem) -> Int = { item ->
        when (item) {
            is HeaderItem -> R.layout.view_group_header
            is LaunchItem -> if (portrait) R.layout.asset_item_portrait else R.layout.asset_item_landscape
        }
    }

    init {
        fetchLaunches()
    }

    fun onFilterClicked() {
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

/**
 * DiffUtil feature enables list diff'ing and other processing on recyclerview, animating both the item and content changes;
 * also keeping the remaining UI between list updates.
 */
class LaunchDiffUtil : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem) =
        oldItem is HeaderItem && newItem is HeaderItem && oldItem.text == newItem.text ||
                oldItem is LaunchItem && newItem is LaunchItem && oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem) = false
}