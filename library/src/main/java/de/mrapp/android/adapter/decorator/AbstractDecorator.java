/*
 * Copyright 2014 - 2018 Michael Rapp
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package de.mrapp.android.adapter.decorator;

import android.annotation.SuppressLint;
import android.os.Build;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import de.mrapp.android.util.view.AbstractViewHolderAdapter;

/**
 * An abstract base class for all decorators, which should allow to customize the appearance of the
 * view, which is used to visualize the items of an adapter. It provides methods, which allows to
 * use the view holder pattern to reference views in order to gain a better performance.
 *
 * @author Michael Rapp
 * @since 0.1.0
 */
public abstract class AbstractDecorator extends AbstractViewHolderAdapter {

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
    private void adaptChildrenViewStates(@NonNull final ViewGroup parent, final boolean enabled,
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
    protected final void adaptViewState(@NonNull final View view, final boolean enabled,
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
     * View} or null, if no view with the given ID is available
     * @deprecated Has been replaced by method <code>findViewById</code>
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    protected final <ViewType extends View> ViewType getView(@IdRes final int viewId) {
        return findViewById(viewId);
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