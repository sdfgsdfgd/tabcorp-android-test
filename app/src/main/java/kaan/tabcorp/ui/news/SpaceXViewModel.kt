package kaan.tabcorp.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kaan.tabcorp.domain.FlightsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpaceXViewModel @Inject constructor(private val flightsRepository: FlightsRepository) : ViewModel() {

    private var portrait = false

//    val news = MutableLiveData<News>()

//    val assets = MutableLiveData<List<Any>>()
//    val assetsDiff: DiffUtil.ItemCallback<Asset> = NegativeDiffCallback()
//    val assetsLayoutProvider: (Asset) -> Int = { if (portrait) R.layout.asset_item_portrait else R.layout.asset_item_landscape }

    private val _navigateToWebview = MutableLiveData<String>()
    val navigateToWebview: LiveData<String> get() = _navigateToWebview

    init {
        getNews()

        viewModelScope.launch {
            flightsRepository.getFlights()
        }

        Log.d("XXX", "====== Alright let's begin ... ")
    }

    fun setPortraitOrientation(isPortrait: Boolean) {
        this.portrait = isPortrait
    }


    private fun getNews() {
//        loading.value = true      // Todo: Loading animations, before the network call.
//        disposable.add(
//            flightsRepository.getNews()
//            !!.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(object : DisposableSingleObserver<News?>() {
//                    override fun onSuccess(news: News) {
//                        assets.value = news.assets.onEach { it.onClickHandler = ::onAssetClicked }
//                    }
//
//                    override fun onError(e: Throwable) {
//                        // TODO: Error handling.
//                    }
//                })
//        )
    }

    private fun onAssetClicked(url: String) {
        _navigateToWebview.value = url
    }
}