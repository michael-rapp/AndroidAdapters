/*
 * AndroidAdapters Copyright 2014 - 2015 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package de.mrapp.android.adapter.decorator;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * A view holder, which provides references to previously referenced views in order to reuse them.
 *
 * @author Michael Rapp
 * @since 0.1.0
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    /**
     * The view, the view holder is associated with.
     */
    private final View parentView;

    /**
     * A sparse array, which maps view types to sparse arrays, which map the resource IDs of already
     * referenced views to the views themselves.
     */
    private SparseArray<SparseArray<View>> views;

    /**
     * Creates a new view holder.
     *
     * @param parentView
     *         The view, the view holder is associated with, as an instance of the class {@link
     *         View}. The view may not be null
     */
    public ViewHolder(final View parentView) {
        super(parentView);
        this.parentView = parentView;
        this.views = new SparseArray<>();
    }

    /**
     * Returns the view, the view holder is associated with.
     *
     * @return The view, the view holder is associated with, as an instance of the class {@link
     * View}. The view may not be null
     */
    public final View getParentView() {
        return parentView;
    }

    /**
     * Returns the view, which belongs to a specific resource ID. If the view has already been
     * referenced, the reference, which is stored in the view holder, will be reused. Otherwise the
     * method <code>findViewById(int):View</code> of the given parent view is used to reference the
     * view.
     *
     * @param viewId
     *         The resource ID of the view, which should be returned, as an {@link Integer} value.
     *         The ID must be a valid resource ID of a view, which belongs to the given parent view
     * @param viewType
     *         The view type of the view, which should be returned, as an {@link Integer} value
     * @return The view, which belongs to the given resource ID, as an instance of the class {@link
     * View} or null, if no view with the given ID is available
     */
    public final View getView(@IdRes final int viewId, final int viewType) {
        SparseArray<View> mapping = views.get(viewType);

        if (mapping == null) {
            mapping = new SparseArray<>();
            views.put(viewType, mapping);
        }

        View view = mapping.get(viewId);

        if (view == null) {
            view = parentView.findViewById(viewId);
            mapping.put(viewId, view);
        }

        return view;
    }

}