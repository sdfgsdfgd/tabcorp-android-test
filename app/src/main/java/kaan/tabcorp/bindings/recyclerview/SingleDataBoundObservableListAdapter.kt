package kaan.tabcorp.bindings.recyclerview

import androidx.annotation.LayoutRes
import androidx.databinding.ObservableList

class SingleDataBoundObservableListAdapter<T>(
        items: ObservableList<T>,
        @LayoutRes private val layoutId: Int,
        itemIdProvider: ((T) -> Long)? = null
) : DataBoundObservableListAdapter<T>(items, itemIdProvider) {

    @LayoutRes
    override fun getItemLayoutId(position: Int): Int = layoutId
}