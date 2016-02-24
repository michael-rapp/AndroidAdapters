/*
 * Copyright 2014 - 2016 Michael Rapp
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
package de.mrapp.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import de.mrapp.android.adapter.decorator.AbstractDecorator;
import de.mrapp.android.adapter.decorator.AbstractExpandableListDecorator;

/**
 * An abstract base class for all decorators, which should allow to customize the appearance of the
 * views, which are used to visualize the group and child items of an {@link
 * ExpandableListAdapter}.
 *
 * @param <GroupType>
 *         The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the adapter's child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public abstract class ExpandableListDecorator<GroupType, ChildType>
        extends AbstractExpandableListDecorator<GroupType, ChildType> {

    /**
     * The method, which is invoked by an adapter to apply the decorator on a group item. It
     * initializes the view holder pattern, which is provided by the decorator and then delegates
     * the method call to the decorator's custom implementation of the method
     * <code>onShowGroup(...):void</code>.
     *
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param adapter
     *         The adapter, whose items are visualized by the decorator, as an instance of the type
     *         {@link ExpandableListAdapter}. The adapter may not be null
     * @param view
     *         The view, which is used to visualize the group item, as an instance of the class
     *         {@link View}. The view may not be null
     * @param group
     *         The group item, which should be visualized, as an instance of the generic type
     *         GroupType. The group item may not be null
     * @param index
     *         The index of the group item, which should be visualized, as an {@link Integer} value
     * @param expanded
     *         True, if the group item, which should be visualized, is currently expanded, false
     *         otherwise
     * @param enabled
     *         True, if the group item, which should be visualized, is currently enabled, false
     *         otherwise
     * @param state
     *         The current state of the group item, which should be visualized, as an {@link
     *         Integer} value
     * @param filtered
     *         True, if at least one filter is currently applied on the adapter's group items, false
     *         otherwise
     */
    public final void applyDecoratorOnGroup(@NonNull final Context context,
                                            @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                                            @NonNull final View view,
                                            @NonNull final GroupType group, final int index,
                                            final boolean expanded, final boolean enabled,
                                            final int state, final boolean filtered) {
        setCurrentParentView(view);
        int viewType = getGroupType(group);
        setCurrentViewType(viewType);
        adaptViewState(view, enabled, false);
        onShowGroup(context, adapter, view, group, viewType, index, expanded, enabled, state,
                filtered);
    }

    /**
     * The method, which is invoked by an adapter to apply the decorator on a child item. It
     * initializes the view holder pattern, which is provided by the decorator and then delegates
     * the method call to the decorator's custom implementation of the method
     * <code>onShowChild(...):void</code>.
     *
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param adapter
     *         The adapter, whose items are visualized by the decorator, as an instance of the type
     *         {@link ExpandableListAdapter}. The adapter may not be null
     * @param view
     *         The view, which is used to visualize the child item, as an instance of the class
     *         {@link View}. The view may not be null
     * @param child
     *         The child item, which should be visualized, as an instance of the generic type
     *         ChildType. The child item may not be null
     * @param childIndex
     *         The index of the child item, which should be visualized, as an {@link Integer} value
     * @param group
     *         The group item, the child, which should be visualized, belongs to, as an instance of
     *         the generic type GroupType. The group item may not be null
     * @param groupIndex
     *         The index of the group item, the child, which should be visualized, belongs to, as an
     *         {@link Integer} value
     * @param enabled
     *         True, if the child item, which should be visualized, is currently enabled, false
     *         otherwise
     * @param state
     *         The current state of the child item, which should be visualized, as an {@link
     *         Integer} value
     * @param filtered
     *         True, if at least one filter is currently applied on the adapter's child items, false
     *         otherwise
     */
    public final void applyDecoratorOnChild(@NonNull final Context context,
                                            @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                                            @NonNull final View view,
                                            @NonNull final ChildType child, final int childIndex,
                                            @NonNull final GroupType group, final int groupIndex,
                                            final boolean enabled, final int state,
                                            final boolean filtered) {
        setCurrentParentView(view);
        int viewType = getChildType(child);
        setCurrentViewType(viewType);
        adaptViewState(view, enabled, false);
        onShowChild(context, adapter, view, child, viewType, childIndex, group, groupIndex, enabled,
                state, filtered);
    }

    /**
     * The method which is invoked, when the view, which is used to visualize a group item, should
     * be shown, respectively when it should be refreshed. The purpose of this method is to
     * customize the appearance of the view, which is used to visualize the appropriate group item,
     * depending on its state and whether it is currently enabled or not.
     *
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param adapter
     *         The adapter, whose items are visualized by the decorator, as an instance of the type
     *         {@link ExpandableListAdapter}. The adapter may not be null
     * @param view
     *         The view, which is used to visualize the group item, as an instance of the class
     *         {@link View}. The view may not be null
     * @param group
     *         The group item, which should be visualized, as an instance of the generic type
     *         GroupType. The group item may not be null
     * @param viewType
     *         The view type of the group item, which should be visualized, as an {@link Integer}
     *         value
     * @param index
     *         The index of the group item, which should be visualized, as an {@link Integer} value
     * @param expanded
     *         True, if the group item, which should be visualized, is currently expanded, false
     *         otherwise
     * @param enabled
     *         True, if the group item, which should be visualized, is currently enabled, false
     *         otherwise
     * @param state
     *         The current state of the group item, which should be visualized, as an {@link
     *         Integer} value
     * @param filtered
     *         True, if at least one filter is currently applied on the adapter's group items, false
     *         otherwise
     */
    public abstract void onShowGroup(@NonNull Context context,
                                     @NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                                     @NonNull View view, @NonNull GroupType group, int viewType,
                                     int index, boolean expanded, boolean enabled, int state,
                                     boolean filtered);

    /**
     * The method which is invoked, when the view, which is used to visualize a child item, should
     * be shown, respectively when it should be refreshed. The purpose of this method is to
     * customize the appearance of the view, which is used to visualize the appropriate child item,
     * depending on its state and whether it is currently enabled or not.
     *
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param adapter
     *         The adapter, whose items are visualized by the decorator, as an instance of the type
     *         {@link ExpandableListAdapter}. The adapter may not be null
     * @param view
     *         The view, which is used to visualize the child item, as an instance of the class
     *         {@link View}. The view may not be null
     * @param child
     *         The child item, which should be visualized, as an instance of the generic type
     *         ChildType. The child item may not be null
     * @param viewType
     *         The view type of the child item, which should be visualized, as an {@link Integer}
     *         value
     * @param childIndex
     *         The index of the child item, which should be visualized, as an {@link Integer} value
     * @param group
     *         The group item, the child, which should be visualized, belongs to, as an instance of
     *         the generic type GroupType. The group item may not be null
     * @param groupIndex
     *         The index of the group item, the child, which should be visualized, belongs to, as an
     *         {@link Integer} value
     * @param enabled
     *         True, if the child item, which should be visualized, is currently enabled, false
     *         otherwise
     * @param state
     *         The current state of the child item, which should be visualized, as an {@link
     *         Integer} value
     * @param filtered
     *         True, if at least one filter is currently applied on the adapter's child items, false
     *         otherwise
     */
    public abstract void onShowChild(@NonNull Context context,
                                     @NonNull ExpandableListAdapter<GroupType, ChildType> adapter,
                                     @NonNull View view, @NonNull ChildType child, int viewType,
                                     int childIndex, @NonNull GroupType group, int groupIndex,
                                     boolean enabled, int state, boolean filtered);

}