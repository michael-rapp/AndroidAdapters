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

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/**
 * An abstract base class for all decorators, which should allow to customize the appearance of the
 * view, which is used to visualize the items of an adapter. It provides methods, which allows to
 * use the view holder pattern to reference views in order to gain a better performance.
 *
 * @author Michael Rapp
 * @since 0.1.0
 */
public abstract class AbstractDecorator {

    /**
     * The parent view of the item, whose appearance should currently be customized by the
     * decorator. This parent view is used to reference the views, which are used to visualize the
     * appropriate item, if the view holder does not already provide such references.
     */
    private View currentParentView;

    /**
     * The view type of the item, whose appearance should currently be customized by the decorator.
     */
    private int currentViewType;

    /**
     * Adapts the children of a view group, which is used to visualize an item.
     *
     * @param parent
     *         The view group, whose children's states should be adapted, as an instance of the
     *         class {@link ViewGroup}. The view group may not be null
     * @param enabled
     *         True, if the item, which is visualized by the view group, is currently enabled, false
     *         otherwise
     * @param selected
     *         True, if the item, which is visualized by the view group, is currently selected,
     *         false otherwise
     */
    private void adaptChildrenViewStates(final ViewGroup parent, final boolean enabled,
                                         final boolean selected) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            adaptViewState(child, enabled, selected);
        }
    }

    /**
     * Adapts the state of a view, which is used to visualize an item. This method should never be
     * called or overridden by any custom decorator implementation.
     *
     * @param view
     *         The view, whose state should be adapted, as an instance of the class {@link View}.
     *         The view may not be null
     * @param enabled
     *         True, if the item, which is visualized by the view, is currently enabled, false
     *         otherwise
     * @param selected
     *         True , if the item, which is visualized by the view, is currently selected, false
     *         otherwise
     */
    @SuppressLint("NewApi")
    protected final void adaptViewState(final View view, final boolean enabled,
                                        final boolean selected) {
        if (isViewStateAdapted()) {
            view.setEnabled(enabled);
            view.setSelected(selected);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                view.setActivated(selected);
            }

            if (areChildrenViewStatesAdapted() && view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                adaptChildrenViewStates(viewGroup, enabled, selected);
            }
        }
    }

    /**
     * Sets the index of the item, whose appearance should currently be customized by the decorator.
     * This method should never be called or overridden by any custom decorator implementation.
     *
     * @param currentParentView
     *         The parent view, which should be set, as an instance of the class {@link View}. The
     *         parent view may not be null
     */
    protected final void setCurrentParentView(final View currentParentView) {
        ensureNotNull(currentParentView, "The parent view may not be null");
        this.currentParentView = currentParentView;
    }

    /**
     * Sets the view type of the item, whose appearance should currently be customized by the
     * decorator. This method should never be called or overridden by any custom decorator
     * implementation.
     *
     * @param currentViewType
     *         The view type, which should be set, as an {@link Integer} value
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
     * Returns the view, which belongs to a specific resource ID by using the view holder pattern in
     * order gain a better performance. The view will be implicitly casted to the type of the
     * attribute it should be assigned to.
     *
     * @param <ViewType>
     *         The type, the view is implicitly casted to. It must inherited from the class {@link
     *         View}
     * @param viewId
     *         The resource ID of the view, which should be returned, as an {@link Integer} value.
     *         The ID must be a valid resource ID of a view, which belongs to the view, whose
     *         appearance is currently customized by the decorator
     * @return The view, which belongs to the given resource ID, as an instance of the class {@link
     * View}. The view may not be null
     */
    @SuppressWarnings("unchecked")
    protected final <ViewType extends View> ViewType getView(final int viewId) {
        ViewHolder viewHolder = (ViewHolder) currentParentView.getTag();

        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            currentParentView.setTag(viewHolder);
        }

        return (ViewType) viewHolder.getView(currentParentView, viewId, currentViewType);
    }

    /**
     * Returns, whether the state of a view, which is used to visualize an item of the adapter,
     * should be adapted to the item's state, or not. For example, if the item is disabled, the
     * according view will also be disabled by the decorator by automatically calling its
     * <code>setEnabled(boolean):void</code> method. This method may overridden by a custom
     * decorator implementation to adapt the behavior of the decorator.
     *
     * @return True, if the state of a view, which is used to visualized an item of the adapter,
     * should be adapted to the item's state, false otherwise
     */
    protected boolean isViewStateAdapted() {
        return true;
    }

    /**
     * Returns, whether the state of the children of a view, which is used to visualize an item of
     * the adapter, should be adapted to the item's state, or not. For example, if the item is
     * disabled, the according view will also be disabled by the decorator by automatically calling
     * its <code>setEnabled(boolean):void</code> method. The states of children are only adapted, if
     * the method <code>isViewStateAdapter</code> returns true as well. This method may overridden
     * by a custom decorator implementation to adapt the behavior of the decorator.
     *
     * @return True, if the state of the children of a view, which is used to visualize an item of
     * the adapter, should be adapter to the item's state, false otherwise
     */
    protected boolean areChildrenViewStatesAdapted() {
        return false;
    }

}