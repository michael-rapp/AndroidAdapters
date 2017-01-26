/*
 * Copyright 2014 - 2017 Michael Rapp
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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.mrapp.android.adapter.expandablelist.ExpandableListAdapter;
import de.mrapp.android.adapter.MultipleChoiceExpandableListAdapter;
import de.mrapp.android.adapter.SingleChoiceExpandableListAdapter;

/**
 * An abstract base class for all decorators, which should allow to customize the appearance of the
 * view, which is used to visualize the items of a {@link ExpandableListAdapter}, {@link
 * SingleChoiceExpandableListAdapter} or {@link MultipleChoiceExpandableListAdapter}.
 *
 * @param <GroupType>
 *         The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the adapter's child items
 * @author Michael Rapp
 * @since 0.2.0
 */
public abstract class AbstractExpandableListDecorator<GroupType, ChildType>
        extends AbstractDecorator {

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
     * The method which is invoked by an adapter to inflate the view, which should be used to
     * visualize a specific group item.
     *
     * @param inflater
     *         The inflater, which should be used to inflate the view, as an instance of the class
     *         {@link LayoutInflater}. The inflater may not be null
     * @param parent
     *         The parent view of the view, which should be inflated, as an instance of the class
     *         {@link ViewGroup} or null, if no parent view is available
     * @param group
     *         The group item, which should be visualized, as an instance of the generic type
     *         GroupType. The item may not be null
     * @return The view, which has been inflated, as an instance of the class {@link View}. The view
     * may not be null
     */
    public final View inflateGroupView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
                                       @NonNull GroupType group) {
        return onInflateGroupView(inflater, parent, getGroupType(group));
    }

    /**
     * The method which is invoked by an adapter to inflate the view, which should be used to
     * visualize a specific child item.
     *
     * @param inflater
     *         The inflater, which should be used to inflate the view, as an instance of the class
     *         {@link LayoutInflater}. The inflater may not be null
     * @param parent
     *         The parent view of the view, which should be inflated, as an instance of the class
     *         {@link ViewGroup} or null, if no parent view is available
     * @param child
     *         The child item, which should be visualized, as an instance of the generic type
     *         ChildType. The item may not be null
     * @return The view, which has been inflated, as an instance of the class {@link View}. The view
     * may not be null
     */
    public final View inflateChildView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
                                       @NonNull ChildType child) {
        return onInflateChildView(inflater, parent, getChildType(child));
    }

    /**
     * The method which is invoked, when a view, which is used to visualize a group item, should be
     * inflated.
     *
     * @param inflater
     *         The inflater, which should be used to inflate the view, as an instance of the class
     *         {@link LayoutInflater}. The inflater may not be null
     * @param parent
     *         The parent view of the view, which should be inflated, as an instance of the class
     *         {@link ViewGroup} or null, if no parent view is available
     * @param groupType
     *         The view type of the group item, which should be visualized, as an {@link Integer}
     *         value
     * @return The view, which has been inflated, as an instance of the class {@link View}. The view
     * may not be null
     */
    @NonNull
    public abstract View onInflateGroupView(@NonNull LayoutInflater inflater,
                                            @Nullable ViewGroup parent, int groupType);

    /**
     * The method which is invoked, when a view, which is used to visualize a child item, should be
     * inflated.
     *
     * @param inflater
     *         The inflater, which should be used to inflate the view, as an instance of the class
     *         {@link LayoutInflater}. The inflater may not be null
     * @param parent
     *         The parent view of the view, which should be inflated, as an instance of the class
     *         {@link ViewGroup} or null, if no parent view is available
     * @param childType
     *         The view type of the child item, which should be visualized, as an {@link Integer}
     *         value
     * @return The view, which has been inflated, as an instance of the class {@link View}. The view
     * may not be null
     */
    @NonNull
    public abstract View onInflateChildView(@NonNull LayoutInflater inflater,
                                            @Nullable ViewGroup parent, int childType);

}