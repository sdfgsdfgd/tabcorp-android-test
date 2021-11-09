package kaan.tabcorp.bindings.recyclerview

import androidx.annotation.LayoutRes
import androidx.databinding.ObservableList

/**
 * A RecyclerView [Adapter] for lists with items where different items can have different layouts.
 *
 * Usage:
 *
 * 1. Define a ViewModel containing a list of items
 * ```
 * class AccountListViewModel : ViewModel() {
 *     class AccountItem(val id: Int, val balanceText: String)
 *
 *     val accounts = ObservableArrayList<AccountItem>()
 * }
 * ```
 *
 * 2. Provide a function that maps items to item layouts (for example,
 * by exposing it as the ViewModel property).
 * ```
 * val accountLayout: (AccountItem) -> Int = { account -> if (account.isPremium R.layout.account_item_premium else R.layout.account_item }
 * ```
 *
 * 3. Optionally, provide a function for extracting item's stable ID (for example,
 * by exposing it as the ViewModel property).
 * ```
 * val accountIdProvider = { account: AccountItem -> account.id }
 * ```
 *
 * 4. Design the item layout. The layout should use the databinding <variable> called `item`.
 * ```
 * <data>
 *     <variable name="item" type="...AccountItem"
 *     ...
 *     <TextView
 *         android:text="@{item.id}"
 *         ...
 *     <TextView
 *         android:text="@{item.balanceText}"
 *         ...
 * </data>
 * ```
 *
 * 5. Associate an instance of [MultiDataBoundObservableListAdapter] with [RecyclerView]. This can be done either in code:
 * ```
 * recyclerView.adapter = MultiDataBoundObservableListAdapter(viewModel.accounts, viewModel.accountLayout, viewModel.accountIdProvider)
 * ```
 *
 * or in XML:
 * ```
 * <androidx.recyclerview.widget.RecyclerView
 *     ...
 *     app:items="@{viewModel.accounts}"
 *     app:itemLayoutProvider="@{viewModel.accountLayout}"
 *     app:itemIdProvider="@{viewModel.accountIdProvider}" />
 * ```
 *
 * Created by Alex Chiviliov on 2019-08-08.
 *
 */
class MultiDataBoundObservableListAdapter<T>(
        items: ObservableList<T>,
        private val itemLayoutProvider: (T) -> Int,
        itemIdProvider: ((T) -> Long)? = null
) : DataBoundObservableListAdapter<T>(items, itemIdProvider) {

    @LayoutRes
    override fun getItemLayoutId(position: Int): Int = itemLayoutProvider(getItem(position))
}