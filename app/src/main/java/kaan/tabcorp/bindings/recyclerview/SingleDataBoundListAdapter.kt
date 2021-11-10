package kaan.tabcorp.bindings.recyclerview

import androidx.annotation.LayoutRes
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil

class SingleDataBoundListAdapter<T>(
    items: LiveData<List<T>>,
    @LayoutRes private val itemLayout: Int,
    itemDiff: DiffUtil.ItemCallback<T>
) : DataBoundListAdapter<T>(items, itemDiff, null) {

    @LayoutRes
    override fun getItemLayoutId(position: Int): Int = itemLayout
}
