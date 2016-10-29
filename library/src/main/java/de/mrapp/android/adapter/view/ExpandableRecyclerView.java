package de.mrapp.android.adapter.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import de.mrapp.android.adapter.expandablelist.ExpandableListAdapter;

/**
 * @author Michael Rapp
 */
public class ExpandableRecyclerView extends RecyclerView {
    public ExpandableRecyclerView(Context context) {
        super(context);
    }

    public ExpandableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Sets the adapter that provides data to this view.
     *
     * @param adapter
     *         The adapter, which should be set, as an instance of the type {@link
     *         ExpandableListAdapter} or null, if no adapter should be set
     */
    public final void setAdapter(@Nullable final ExpandableListAdapter adapter) {

    }

    /**
     * Returns the adapter that provides data to this view.
     *
     * @return The adapter that provides data to this view as an instance of the type {@link
     * ExpandableListAdapter} or null, if no adapter has been set.
     */
    public ExpandableListAdapter getExpandableListAdapter() {
        return null;
    }

    public void expandGroup(int i) {

    }

    public void collapseGroup(int i) {

    }

}
