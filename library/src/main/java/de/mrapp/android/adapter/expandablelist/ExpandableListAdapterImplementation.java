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
package de.mrapp.android.adapter.expandablelist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import de.mrapp.android.adapter.ExpandableListDecorator;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.NoChoiceExpandableListAdapter;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.expandablelist.enablestate.ExpandableListEnableStateListener;
import de.mrapp.android.adapter.expandablelist.filterable.AbstractFilterableExpandableListAdapter;
import de.mrapp.android.adapter.expandablelist.filterable.ExpandableListFilterListener;
import de.mrapp.android.adapter.expandablelist.itemstate.ExpandableListItemStateListener;
import de.mrapp.android.adapter.expandablelist.sortable.ExpandableListSortingListener;
import de.mrapp.android.adapter.list.selectable.MultipleChoiceListAdapterImplementation;
import de.mrapp.android.util.logging.LogLevel;

/**
 * An adapter, whose underlying data is managed as a list of arbitrary group and child items. Such
 * an adapter's purpose is to provide the underlying data for visualization using a {@link
 * ExpandableListView} widget.
 *
 * @param <GroupType>
 *         The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the adapter's child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public class ExpandableListAdapterImplementation<GroupType, ChildType> extends
        AbstractFilterableExpandableListAdapter<GroupType, ChildType, ExpandableListDecorator<GroupType, ChildType>>
        implements NoChoiceExpandableListAdapter<GroupType, ChildType> {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new adapter, whose underlying data is managed as a list of arbitrary group and
     * child items.
     *
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param decorator
     *         The decorator, which should be used to customize the appearance of the views, which
     *         are used to visualize the group and child items of the adapter, as an instance of the
     *         generic type DecoratorType. The decorator may not be null
     * @param logLevel
     *         The log level, which should be used for logging, as a value of the enum LogLevel. The
     *         log level may not be null
     * @param groupAdapter
     *         The adapter, which should manage the adapter's group items, as an instance of the
     *         type {@link MultipleChoiceListAdapter}. The adapter may not be null
     * @param allowDuplicateChildren
     *         True, if duplicate child items, regardless from the group they belong to, should be
     *         allowed, false otherwise
     * @param notifyOnChange
     *         True, if the method <code>notifyDataSetChanged():void</code> should be automatically
     *         called when the adapter's underlying data has been changed, false otherwise
     * @param triggerGroupExpansionOnClick
     *         True, if a group's expansion should be triggered, when it is clicked by the user,
     *         false otherwise
     * @param itemClickListeners
     *         A set, which contains the listeners, which should be notified, when an item of the
     *         adapter has been clicked by the user, as an instance of the type {@link Set}, or an
     *         empty set, if no listeners should be notified
     * @param itemLongClickListeners
     *         A set, which contains the listeners, which should be notified, when an item of the
     *         adapter has been long-clicked by the user, as an instance of the type {@link Set}, or
     *         an empty set, if no listeners should be notified
     * @param adapterListeners
     *         A set, which contains the listeners, which should be notified, when the adapter's
     *         underlying data has been modified, as an instance of the type {@link Set}, or an
     *         empty set, if no listeners should be notified
     * @param expansionListeners
     *         A set, which contains the listeners, which should be notified, when a group item has
     *         been expanded or collapsed, as an instance of the type {@link Set}, or an empty set,
     *         if no listeners should be notified
     * @param setChildEnableStatesImplicitly
     *         True, if the enable states of children should be also set, when the enable state of
     *         the group, they belong to, is set
     * @param enableStateListeners
     *         A set, which contains the listeners, which should be notified, when an item has been
     *         disabled or enabled, as an instance of the type {@link Set}, or an empty set, if no
     *         listeners should be notified
     * @param numberOfGroupStates
     *         The number of states, the adapter's group items may have, as an {@link Integer}
     *         value. The value must be at least 1
     * @param numberOfChildStates
     *         The number of states, the adapter's child items may have, as an {@link Integer}
     *         value. The value must be at least 1
     * @param triggerGroupStateOnClick
     *         True, if the state of a group item should be triggered, when it is clicked by the
     *         user, false otherwise
     * @param triggerChildStateOnClick
     *         True, if the state of a child item should be triggered, when it is clicked by the
     *         user, false otherwise
     * @param setChildStatesImplicitly
     *         True, if the states of children should be also set, when the state of the group, they
     *         belong to, is set, false otherwise
     * @param itemStateListeners
     *         A set, which contains the listeners, which should be notified, when the state of an
     *         item has been changed, or an empty set, if no listeners should be notified
     * @param sortingListeners
     *         A set, which contains the listeners, which should be notified, when the adapter's
     *         underlying data has been sorted, or an empty set, if no listeners should be notified
     * @param filterListeners
     *         A set, which contains the listeners, which should be notified, when the adapter's
     *         underlying data has been filtered, or an empty set, if no listeners should be
     *         notified
     */
    protected ExpandableListAdapterImplementation(@NonNull final Context context,
                                                  @NonNull final ExpandableListDecorator<GroupType, ChildType> decorator,
                                                  @NonNull final LogLevel logLevel,
                                                  @NonNull final MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter,
                                                  final boolean allowDuplicateChildren,
                                                  final boolean notifyOnChange,
                                                  final boolean triggerGroupExpansionOnClick,
                                                  @NonNull final Set<ExpandableListAdapterItemClickListener<GroupType, ChildType>> itemClickListeners,
                                                  @NonNull final Set<ExpandableListAdapterItemLongClickListener<GroupType, ChildType>> itemLongClickListeners,
                                                  @NonNull final Set<ExpandableListAdapterListener<GroupType, ChildType>> adapterListeners,
                                                  @NonNull final Set<ExpansionListener<GroupType, ChildType>> expansionListeners,
                                                  final boolean setChildEnableStatesImplicitly,
                                                  @NonNull final Set<ExpandableListEnableStateListener<GroupType, ChildType>> enableStateListeners,
                                                  final int numberOfGroupStates,
                                                  final int numberOfChildStates,
                                                  final boolean triggerGroupStateOnClick,
                                                  final boolean triggerChildStateOnClick,
                                                  final boolean setChildStatesImplicitly,
                                                  @NonNull final Set<ExpandableListItemStateListener<GroupType, ChildType>> itemStateListeners,
                                                  @NonNull final Set<ExpandableListSortingListener<GroupType, ChildType>> sortingListeners,
                                                  @NonNull final Set<ExpandableListFilterListener<GroupType, ChildType>> filterListeners) {
        super(context, decorator, logLevel, groupAdapter, allowDuplicateChildren, notifyOnChange,
                triggerGroupExpansionOnClick, itemClickListeners, itemLongClickListeners,
                adapterListeners, expansionListeners, setChildEnableStatesImplicitly,
                enableStateListeners, numberOfGroupStates, numberOfChildStates,
                triggerGroupStateOnClick, triggerChildStateOnClick, setChildStatesImplicitly,
                itemStateListeners, sortingListeners, filterListeners);
    }

    /**
     * Creates a new adapter, whose underlying data is managed as a list of arbitrary group and
     * child items.
     *
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param decorator
     *         The decorator, which should be used to customize the appearance of the views, which
     *         are used to visualize the group and child items of the adapter, as an instance of the
     *         generic type DecoratorType. The decorator may not be null
     */
    public ExpandableListAdapterImplementation(@NonNull final Context context,
                                               @NonNull final ExpandableListDecorator<GroupType, ChildType> decorator) {
        this(context, decorator, LogLevel.INFO,
                new MultipleChoiceListAdapterImplementation<>(context,
                        new NullObjectDecorator<Group<GroupType, ChildType>>()), false, true, true,
                new CopyOnWriteArraySet<ExpandableListAdapterItemClickListener<GroupType, ChildType>>(),
                new CopyOnWriteArraySet<ExpandableListAdapterItemLongClickListener<GroupType, ChildType>>(),
                new CopyOnWriteArraySet<ExpandableListAdapterListener<GroupType, ChildType>>(),
                new CopyOnWriteArraySet<ExpansionListener<GroupType, ChildType>>(), true,
                new CopyOnWriteArraySet<ExpandableListEnableStateListener<GroupType, ChildType>>(),
                1, 1, false, false, false,
                new CopyOnWriteArraySet<ExpandableListItemStateListener<GroupType, ChildType>>(),
                new CopyOnWriteArraySet<ExpandableListSortingListener<GroupType, ChildType>>(),
                new CopyOnWriteArraySet<ExpandableListFilterListener<GroupType, ChildType>>());
    }

    @Override
    protected final void applyDecoratorOnGroup(@NonNull final Context context,
                                               @NonNull final View view, final int index) {
        GroupType group = getGroup(index);
        boolean expanded = isGroupExpanded(index);
        boolean enabled = isGroupEnabled(index);
        int state = getGroupState(index);
        boolean filtered = areGroupsFiltered();
        getDecorator()
                .applyDecoratorOnGroup(context, this, view, group, index, expanded, enabled, state,
                        filtered);
    }

    @Override
    protected final void applyDecoratorOnChild(@NonNull final Context context,
                                               @NonNull final View view, final int groupIndex,
                                               final int childIndex) {
        GroupType group = getGroup(groupIndex);
        ChildType child = getChild(groupIndex, childIndex);
        boolean enabled = isChildEnabled(groupIndex, childIndex);
        int state = getChildState(groupIndex, childIndex);
        boolean filtered = areChildrenFiltered();
        getDecorator()
                .applyDecoratorOnChild(context, this, view, child, childIndex, group, groupIndex,
                        enabled, state, filtered);
    }

    @Override
    public final String toString() {
        return "NoChoiceExpandableListAdapter (" + getGroupCount() + " groups, " + getChildCount() +
                " children) [logLevel=" + getLogLevel() + ", parameters=" + getParameters() +
                ", notifyOnChange=" + isNotifiedOnChange() + ", allowDuplicateGroups=" +
                areDuplicateGroupsAllowed() + ", allowDuplicateChildren=" +
                areDuplicateChildrenAllowed() + ", numberOfGroupStates=" +
                getNumberOfGroupStates() + ", numberOfChildStates=" + getNumberOfChildStates() +
                ", triggerGroupStateOnClick=" + isGroupStateTriggeredOnClick() +
                ", triggerChildStateOnClick=" + isChildStateTriggeredOnClick() + ", filtered=" +
                isFiltered() + "]";
    }

    @Override
    public final ExpandableListAdapterImplementation<GroupType, ChildType> clone()
            throws CloneNotSupportedException {
        return new ExpandableListAdapterImplementation<>(getContext(), getDecorator(),
                getLogLevel(), cloneGroupAdapter(), areDuplicateChildrenAllowed(),
                isNotifiedOnChange(), isGroupExpansionTriggeredOnClick(), getItemClickListeners(),
                getItemLongClickListeners(), getAdapterListeners(), getExpansionListeners(),
                areChildEnableStatesSetImplicitly(), getEnableStateListeners(),
                getNumberOfGroupStates(), getNumberOfChildStates(), isGroupStateTriggeredOnClick(),
                isChildStateTriggeredOnClick(), areChildStatesSetImplicitly(),
                getItemStateListeners(), getSortingListeners(), getFilterListeners());
    }

}