package kaan.tabcorp.bindings.recyclerview;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

/**
 * An implementation of {@link BaseDataBoundAdapter} that uses LiveData List as the item storage.
 *
 * A reference implementation for an adapter that wants to use data binding "the right way". It
 * works with {@link DataBoundViewHolder}.
 * <p>
 * This class uses layout id as the item type.
 * <p>
 * It can be used for both single type lists and multiple type lists.
 *
 * Created by Alex Chiviliov on 2019-07-26.
 */

public abstract class DataBoundListAdapter<T>
        extends BaseDataBoundAdapter<T> {

    private final AsyncListDiffer<T> mDiffer;
    private final AdapterListUpdateCallback updateCallback;

    protected DataBoundListAdapter(LiveData<List<T>> items, DiffUtil.ItemCallback<T> itemDiff, OnItemsUpdatedCalback onItemsUpdated) {
        updateCallback = new AdapterListUpdateCallback(this);
        mDiffer = new AsyncListDiffer<>(updateCallback,
                new AsyncDifferConfig.Builder<>(itemDiff).build());

        items.observe(this, new Observer<List<T>>() {
            @Override
            public void onChanged(List<T> list) {
                submitList(list, new Runnable() {
                    @Override
                    public void run() {
                        if (onItemsUpdated != null) {
                            onItemsUpdated.onItemsUpdated();
                        }
                    }
                });
            }
        });
    }

    /**
     * Submits a new list to be diffed, and displayed.
     * <p>
     * If a list is already being displayed, a diff will be computed on a background thread, which
     * will dispatch Adapter.notifyItem events on the main thread.
     *
     * @param list The new list to be displayed.
     * @param commitCallback
     */
    private void submitList(@Nullable List<T> list, @Nullable Runnable commitCallback) {
        mDiffer.submitList(list, commitCallback);
    }

    protected final T getItem(int position) {
        return mDiffer.getCurrentList().get(position);
    }

    @Override
    public final int getItemCount() {
        return mDiffer.getCurrentList().size();
    }

    interface OnItemsUpdatedCalback {
        void onItemsUpdated();
    }
}
