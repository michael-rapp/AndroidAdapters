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

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;
import android.view.View;

/**
 * An abstract base class for all decorators, which should allow to customize
 * the appearance of the view, which is used to visualize the items of an
 * adapter. It provides methods, which allows to use the view holder pattern to
 * reference views in order to gain a better performance.
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class AbstractDecorator {

	/**
	 * The parent view of the item, whose appearance should currently be
	 * customized by the decorator. This parent view is used to reference the
	 * views, which are used to visualize the appropriate item, if the view
	 * holder does not already provide such references.
	 */
	private View currentParentView;

	/**
	 * The view type of the item, whose appearance should currently be
	 * customized by the decorator.
	 */
	private int currentViewType;

	/**
	 * Sets the index of the item, whose appearance should currently be
	 * customized by the decorator. This method should never be called or
	 * overridden by any custom decorator implementation.
	 * 
	 * @param currentParentView
	 *            The parent view, which should be set, as an instance of the
	 *            class {@link View}. The parent view may not be null
	 */
	protected final void setCurrentParentView(final View currentParentView) {
		ensureNotNull(currentParentView, "The parent view may not be null");
		this.currentParentView = currentParentView;
	}

	/**
	 * Sets the view type of the item, whose appearance should currently be
	 * customized by the decorator. This method should never be called or
	 * overridden by any custom decorator implementation.
	 * 
	 * @param currentViewType
	 *            The view type, which should be set, as an {@link Integer}
	 *            value
	 */
	protected final void setCurrentViewType(final int currentViewType) {
		this.currentViewType = currentViewType;
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

	/**
	 * Returns the view, which belongs to a specific resource ID by using the
	 * view holder pattern in order gain a better performance. The view will be
	 * implicitly casted to the type of the attribute it should be assigned to.
	 * 
	 * @param <ViewType>
	 *            The type, the view is implicitly casted to. It must inherited
	 *            from the class {@link View}
	 * @param viewId
	 *            The resource ID of the view, which should be returned, as an
	 *            {@link Integer} value. The ID must be a valid resource ID of a
	 *            view, which belongs to the view, whose appearance is currently
	 *            customized by the decorator
	 * @return The view, which belongs to the given resource ID, as an instance
	 *         of the class {@link View}. The view may not be null
	 */
	@SuppressWarnings("unchecked")
	protected final <ViewType extends View> ViewType getView(final int viewId) {
		ViewHolder viewHolder = (ViewHolder) currentParentView.getTag();

		if (viewHolder == null) {
			viewHolder = new ViewHolder();
			currentParentView.setTag(viewHolder);
		}

		return (ViewType) viewHolder.getView(currentParentView, viewId,
				currentViewType);
	}

}