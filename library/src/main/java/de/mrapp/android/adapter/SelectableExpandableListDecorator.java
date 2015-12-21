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
package de.mrapp.android.adapter;

import de.mrapp.android.adapter.decorator.AbstractDecorator;
import de.mrapp.android.adapter.expandablelist.selectable.SelectableExpandableListAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * An abstract base class for all decorators, which should allow to customize the appearance of the
 * views, which are used to visualize the group and child items of an {@link
 * SelectableExpandableListAdapter}.
 *
 * @param <GroupType>
 *         The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the adapter's child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public abstract class SelectableExpandableListDecorator<GroupType, ChildType>
        extends AbstractDecorator {

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
     *         {@link SelectableExpandableListAdapter}. The adapter may not be null
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
     * @param selected
     *         True, if the group item, which should be visualized, is currently selected, false
     *         otherwise
     */
    public final void applyDecoratorOnGroup(@NonNull final Context context,
                                            @NonNull final SelectableExpandableListAdapter<GroupType, ChildType> adapter,
                                            @NonNull final View view,
                                            @NonNull final GroupType group, final int index,
                                            final boolean expanded, final boolean enabled,
                                            final int state, final boolean filtered,
                                            final boolean selected) {
        setCurrentParentView(view);
        int viewType = getGroupType(group);
        setCurrentViewType(viewType);
        adaptViewState(view, enabled, false);
        onShowGroup(context, adapter, view, group, viewType, index, expanded, enabled, state,
                filtered, selected);
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
     *         {@link SelectableExpandableListAdapter}. The adapter may not be null
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
     * @param selected
     *         True, if the child item, which should be visualized, is currently selected, false
     *         otherwise
     */
    public final void applyDecoratorOnChild(@NonNull final Context context,
                                            @NonNull final SelectableExpandableListAdapter<GroupType, ChildType> adapter,
                                            @NonNull final View view,
                                            @NonNull final ChildType child, final int childIndex,
                                            @NonNull final GroupType group, final int groupIndex,
                                            final boolean enabled, final int state,
                                            final boolean filtered, final boolean selected) {
        setCurrentParentView(view);
        int viewType = getChildType(child);
        setCurrentViewType(viewType);
        adaptViewState(view, enabled, false);
        onShowChild(context, adapter, view, child, viewType, childIndex, group, groupIndex, enabled,
                state, filtered, selected);
    }

    /**
     * The method which is invoked in order to retrieve the view type of a specific group item,
     * which is about to be visualized. This method has to be overridden by custom decorators which
     * should be able to visualize some group items optically divergent from others, returning a
     * different integer constant for each type.
     *
     * @param group
     *         The group item, which should be visualized, as an instance of the generic type
     *         GroupType. The group item may not be null
     * @return The view type of the group item, which is about to be visualized, as an {@link
     * Integer} value
     */
    public int getGroupType(@NonNull final GroupType group) {
        return 0;
    }

    /**
     * Returns the number of view types, which are used by the decorator in order to visualize child
     * items. This method has to be overridden by custom decorators in order to return a value,
     * which is consistent with the implementation of the <code>getChildViewType</code>-method.
     *
     * @return The number of view types, which are used by the decorator in order to visualize child
     * items, as an {@link Integer} value
     */
    public int getGroupTypeCount() {
        return 1;
    }

    /**
     * The method which is invoked in order to retrieve the view type of a specific child item,
     * which is about to be visualized. This method has to be overridden by custom decorators which
     * should be able to visualize some child items optically divergent from others, returning a
     * different integer constant for each type.
     *
     * @param child
     *         The child item, which should be visualized, as an instance of the generic type
     *         ChildType. The child item may not be null
     * @return The view type of the child item, which is about to be visualized, as an {@link
     * Integer} value
     */
    public int getChildType(@NonNull final ChildType child) {
        return 0;
    }

    /**
     * Returns the number of view types, which are used by the decorator in order to visualize child
     * items. This method has to be overridden by custom decorators in order to return a value,
     * which is consistent with the implementation of the <code>getChildViewType</code>-method.
     *
     * @return The number of view types, which are used by the decorator in order to visualize child
     * items, as an {@link Integer} value
     */
    public int getChildTypeCount() {
        return 1;
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
     *         {@link SelectableExpandableListAdapter}. The adapter may not be null
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
     * @param selected
     *         True, if the group item, which should be visualized, is currently selected, false
     *         otherwise
     */
    protected abstract void onShowGroup(@NonNull Context context,
                                        @NonNull SelectableExpandableListAdapter<GroupType, ChildType> adapter,
                                        @NonNull View view, @NonNull GroupType group, int viewType,
                                        int index, boolean expanded, boolean enabled, int state,
                                        boolean filtered, boolean selected);

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
     *         {@link SelectableExpandableListAdapter}. The adapter may not be null
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
     * @param selected
     *         True, if the child item, which should be visualized, is currently selected, false
     *         otherwise
     */
    protected abstract void onShowChild(@NonNull Context context,
                                        @NonNull SelectableExpandableListAdapter<GroupType, ChildType> adapter,
                                        @NonNull View view, @NonNull ChildType child, int viewType,
                                        int childIndex, @NonNull GroupType group, int groupIndex,
                                        boolean enabled, int state, boolean filtered,
                                        boolean selected);

}