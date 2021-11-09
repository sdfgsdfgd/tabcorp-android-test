package kaan.tabcorp.bindings.recyclerview;

import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.OnRebindCallback;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.recyclerview.widget.RecyclerView;

import kaan.tabcorp.BR;

import java.util.List;

public abstract class BaseDataBoundAdapter<T>
        extends RecyclerView.Adapter<DataBoundViewHolder>
        implements LifecycleOwner {

    private static final Object DB_PAYLOAD = new Object();

    private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @Nullable
    private RecyclerView mRecyclerView;

    protected BaseDataBoundAdapter() {
        mLifecycleRegistry.setCurrentState(Lifecycle.State.INITIALIZED);
    }

    /**
     * This is used to block items from updating themselves. RecyclerView wants to know when an
     * item is invalidated and it prefers to refresh it via onRebind. It also helps with performance
     * since data binding will not update views that are not changed.
     */
    private final OnRebindCallback mOnRebindCallback = new OnRebindCallback() {
        @Override
        public boolean onPreBind(ViewDataBinding binding) {
            if (mRecyclerView == null || mRecyclerView.isComputingLayout()) {
                return true;
            }
            int childAdapterPosition = mRecyclerView.getChildAdapterPosition(binding.getRoot());
            if (childAdapterPosition == RecyclerView.NO_POSITION) {
                return true;
            }
            notifyItemChanged(childAdapterPosition, DB_PAYLOAD);
            return false;
        }
    };

    @Override
    @CallSuper
    @NonNull
    public DataBoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DataBoundViewHolder vh = DataBoundViewHolder.create(parent, viewType);
        vh.binding.addOnRebindCallback(mOnRebindCallback);
        return vh;
    }

    @Override
    public final void onBindViewHolder(@NonNull DataBoundViewHolder holder, int position,
                                       List<Object> payloads) {
        // when a VH is rebound to the same item, we don't have to call the setters
        if (payloads.isEmpty() || hasNonDataBindingInvalidate(payloads)) {
            bindItem(holder, position, payloads);
        }
        if (holder.binding.getLifecycleOwner() == null) {
            holder.binding.setLifecycleOwner(this);
        }
        holder.binding.executePendingBindings();
    }

    @Override
    public final void onBindViewHolder(@NonNull DataBoundViewHolder holder, int position) {
        throw new IllegalArgumentException("just overridden to make final.");
    }

    /**
     * Override this method to handle binding your items into views
     *
     * @param holder The ViewHolder that has the binding instance
     * @param position The position of the item in the adapter
     * @param payloads The payloads that were passed into the onBind method
     */
    @CallSuper
    protected void bindItem(DataBoundViewHolder holder, int position,
                            List<Object> payloads) {
        holder.binding.setVariable(BR.item, getItem(position));
    }

    private boolean hasNonDataBindingInvalidate(List<Object> payloads) {
        for (Object payload : payloads) {
            if (payload != DB_PAYLOAD) {
                return true;
            }
        }
        return false;
    }

    @Override
    @CallSuper
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mLifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
    }

    @Override
    @CallSuper
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        mRecyclerView = null;
        mLifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
    }

    @Override
    public final int getItemViewType(int position) {
        return getItemLayoutId(position);
    }

    protected abstract T getItem(int position);

    @LayoutRes
    protected abstract int getItemLayoutId(int position);

    @Override
    @NonNull
    public final Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }
}
