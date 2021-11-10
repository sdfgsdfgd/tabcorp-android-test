package kaan.tabcorp.bindings.recyclerview

import androidx.recyclerview.widget.DiffUtil

/**
 * The DiffUtil callback that treats all items as different.
 *
 * Created by Kaan Osmanagaoglu on 2019-07-26.
 *
 */
class NegativeDiffCallback<T> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = false

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = false
}