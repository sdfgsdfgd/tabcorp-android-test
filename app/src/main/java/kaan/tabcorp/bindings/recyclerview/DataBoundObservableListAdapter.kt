package kaan.tabcorp.bindings.recyclerview

import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView

/**
 * An implementation of [BaseDataBoundAdapter] that uses [ObservableList] as the item storage.
 *
 * A reference implementation for an adapter that wants to use data binding "the right way". It
 * works with [DataBoundViewHolder].
 *
 * This class uses layout id as the item type.
 *
 * It can be used for both single type lists and multiple type lists.
 *
 * Created by Kaan Osmanagaoglu on 2019-08-05.
 *
 */
abstract class DataBoundObservableListAdapter<T>(
    private val items: ObservableList<T>,
    private val itemIdProvider: ((T) -> Long)? = null
) : BaseDataBoundAdapter<T>() {

    private val onListChangedCallback = object: ObservableList.OnListChangedCallback<ObservableList<T>>() {

        override fun onChanged(sender: ObservableList<T>) {
            notifyDataSetChanged()
        }

        override fun onItemRangeRemoved(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
            notifyItemRangeRemoved(positionStart, itemCount)
        }

        override fun onItemRangeMoved(sender: ObservableList<T>, fromPosition: Int, toPosition: Int, itemCount: Int) {
            for (i in 0 until itemCount) {
                notifyItemMoved(fromPosition + i, toPosition + i)
            }
        }

        override fun onItemRangeInserted(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
            notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeChanged(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
            notifyItemRangeChanged(positionStart, itemCount)
        }
    }

    init {
        if (itemIdProvider != null) setHasStableIds(true)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        items.addOnListChangedCallback(onListChangedCallback)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        items.removeOnListChangedCallback(onListChangedCallback)
    }

    final override fun getItemCount(): Int = items.size

    final override fun getItem(position: Int): T = items[position]

    override fun getItemId(position: Int): Long =
        itemIdProvider?.invoke(getItem(position)) ?: super.getItemId(position)
}
