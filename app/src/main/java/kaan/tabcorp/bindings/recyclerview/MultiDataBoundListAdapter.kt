package kaan.tabcorp.bindings.recyclerview

import androidx.annotation.LayoutRes
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil

/**
 * A RecyclerView [ListAdapter] for lists with items where different items can have different layouts.
 *
 * Usage:
 *
 * 1. Define a ViewModel containing a list of items
 * ```
 * class AccountListViewModel : ViewModel() {
 *     class AccountItem(val id: Int, val balanceText: String)
 *
 *     val accounts = MutableLiveData<List<AccountItem>>()
 * }
 * ```
 *
 * 2. Provide a function that maps items to item layouts (for example,
 * by exposing it as the ViewModel property).
 * ```
 * val accountLayout: (AccountItem) -> Int = { account -> if (account.isPremium R.layout.account_item_premium else R.layout.account_item }
 * ```
 *
 * 3. Provide a [DiffUtil.ItemCallback] for comparing list items (for example,
 * by exposing it as the ViewModel property).
 * ```
 * val accountDiff = object: DiffUtil.ItemCallback<AccountItem>() {
 *     override fun areItemsTheSame(oldItem: AccountItem, newItem: AccountItem) = oldItem.id == newItem.id
 *     override fun areContentsTheSame(oldItem: AccountItem, newItem: AccountItem) = oldItem.balanceText == newItem.balanceText
 * }
 * ```
 *
 * 4. Design the item layouts. The layouts should use the databinding <variable> called `item`.
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
 * 5. Associate an instance of [MultiDataBoundListAdapter] with [RecyclerView]. This can be done either in code:
 * ```
 * recyclerView.adapter = MultiDataBoundListAdapter(viewModel.accounts, viewModel.accountLayout, viewModel.accountDiff)
 * ```
 *
 * or in XML:
 * ```
 * <androidx.recyclerview.widget.RecyclerView
 *     ...
 *     app:items="@{viewModel.accounts}"
 *     app:itemLayoutProvider="@{viewModel.accountLayout}"
 *     app:itemDiff="@{viewModel.accountDiff}" />
 * ```
 *
 * Created by Alex Chiviliov on 2019-08-08.
 *
 */
class MultiDataBoundListAdapter<T>(
    items: LiveData<List<T>>,
    private val itemLayoutProvider: (T) -> Int,
    itemDiff: DiffUtil.ItemCallback<T>,
    onItemsUpdated: (() -> Unit)?
) : DataBoundListAdapter<T>(items, itemDiff, onItemsUpdated) {

    @LayoutRes
    override fun getItemLayoutId(position: Int): Int = itemLayoutProvider(getItem(position))
}
