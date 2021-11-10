package kaan.tabcorp.bindings.recyclerview

import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableList
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

// /**
// * [RecyclerView] binding adapters.
// *
// * Created by Alex Chiviliov on 2019-07-31.
// */
//
// /**
// * Assigns [SingleDataBoundListAdapter] to [RecyclerView] from XML.
// *
// * ```
// * <androidx.recyclerview.widget.RecyclerView
// *     ...
// *     app:items="@{viewModel.accounts}"
// *     app:itemLayout="@{@layout/account_item}"
// *     app:itemDiff="@{viewModel.accountDiff}" />
// * ```
// *
// */
// @BindingAdapter("items", "itemLayout", "itemDiff")
// fun <T> setAdapter(
//        view: RecyclerView,
//        items: LiveData<List<T>>,
//        @LayoutRes itemLayout: Int,
//        itemDiff: DiffUtil.ItemCallback<T>
// ) {
//    if (view.adapter == null) {
//        view.adapter = SingleDataBoundListAdapter(items, itemLayout, itemDiff)
//    }
// }

/**
 * Assigns [MultiDataBoundListAdapter] to [RecyclerView] from XML.
 *
 * ```
 * <androidx.recyclerview.widget.RecyclerView
 *     ...
 *     app:items="@{viewModel.accounts}"
 *     app:itemLayoutProvider="@{viewModel.accountLayout}"
 *     app:itemDiff="@{viewModel.accountDiff}"
 *     app:onItemsUpdated="@{viewModel.onItemsUpdated}"/>
 * ```
 *
 */
@BindingAdapter("items", "itemLayoutProvider", "itemDiff", "onItemsUpdated")
fun <T> setAdapter(
    view: RecyclerView,
    items: LiveData<List<T>>,
    itemLayoutProvider: (T) -> Int,
    itemDiff: DiffUtil.ItemCallback<T>,
    onItemsUpdated: (() -> Unit)?
) {
    if (view.adapter == null) {
        view.adapter = MultiDataBoundListAdapter(items, itemLayoutProvider, itemDiff, onItemsUpdated)
    }
}

@BindingAdapter("items", "itemLayoutProvider", "itemDiff")
fun <T> setAdapter(
    view: RecyclerView,
    items: LiveData<List<T>>,
    itemLayoutProvider: (T) -> Int,
    itemDiff: DiffUtil.ItemCallback<T>
) {
    setAdapter(view, items, itemLayoutProvider, itemDiff, null)
}

/**
 * Assigns [SingleDataBoundObservableListAdapter] to [RecyclerView] from XML.
 *
 * ```
 * <androidx.recyclerview.widget.RecyclerView
 *     ...
 *     app:items="@{viewModel.accounts}"
 *     app:itemLayout="@{@layout/account_item}"
 *     app:itemIdProvider="@{viewModel.accountIdProvider}" />
 * ```
 *
 */
@BindingAdapter("items", "itemLayout", "itemIdProvider")
fun <T> setAdapter(
    view: RecyclerView,
    items: ObservableList<T>,
    @LayoutRes itemLayout: Int,
    itemIdProvider: ((T) -> Long)?
) {
    if (view.adapter == null) {
        view.adapter = SingleDataBoundObservableListAdapter(items, itemLayout, itemIdProvider)
    }
}

/**
 * Assigns [MultiDataBoundObservableListAdapter] to [RecyclerView] from XML.
 *
 * ```
 * <androidx.recyclerview.widget.RecyclerView
 *     ...
 *     app:items="@{viewModel.accounts}"
 *     app:itemLayoutProvider="@{viewModel.accountLayout}"
 *     app:itemIdProvider="@{viewModel.accountIdProvider}" />
 * ```
 *
 */
@BindingAdapter("items", "itemLayoutProvider", "itemIdProvider")
fun <T> setAdapter(
    view: RecyclerView,
    items: ObservableList<T>,
    itemLayoutProvider: (T) -> Int,
    itemIdProvider: ((T) -> Long)?
) {
    if (view.adapter == null) {
        view.adapter = MultiDataBoundObservableListAdapter(items, itemLayoutProvider, itemIdProvider)
    }
}

@BindingAdapter("items", "itemLayout")
fun <T> setAdapter(
    view: RecyclerView,
    items: ObservableList<T>,
    @LayoutRes itemLayout: Int
) {
    setAdapter(view, items, itemLayout, null)
}

@BindingAdapter("items", "itemLayoutProvider")
fun <T> setAdapter(
    view: RecyclerView,
    items: ObservableList<T>,
    itemLayoutProvider: (T) -> Int
) {
    setAdapter(view, items, itemLayoutProvider, null)
}
