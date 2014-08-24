/*
 * AndroidAdapters Copyright 2014 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>. 
 */
package de.mrapp.android.adapter.decorator;

import android.util.SparseArray;
import android.view.View;

/**
 * A view holder, which provides references to previously referenced views in
 * order to reuse them.
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public class ViewHolder {

	/**
	 * A sparse array, which maps the resource IDs of already referenced views
	 * to the views themselves.
	 */
	private SparseArray<View> views;

	/**
	 * Creates a new view holder.
	 */
	public ViewHolder() {
		this.views = new SparseArray<View>();
	}

	/**
	 * Returns the view, which belongs to a specific resource ID. If the view
	 * has already been referenced, the reference, which is stored in the view
	 * holder, will be reused. Otherwise the method
	 * <code>findViewById(int):View</code> of the given parent view is used to
	 * reference the view.
	 * 
	 * @param parentView
	 *            The parent view, the view, which should be returned, belongs
	 *            to as an instance of the class {@link View}. The view may not
	 *            be null
	 * @param viewId
	 *            The resource ID of the view, which should be returned, as an
	 *            {@link Integer} value. The ID must be a valid resource ID of a
	 *            view, which belongs to the given parent view
	 * @return The view, which belongs to the given resource ID, as an instance
	 *         of the class {@link View}. The view may not be null
	 */
	public final View getView(final View parentView, final int viewId) {
		View view = views.get(viewId);

		if (view == null) {
			view = parentView.findViewById(viewId);
			views.put(viewId, view);
			parentView.setTag(this);
		}

		return view;
	}

}