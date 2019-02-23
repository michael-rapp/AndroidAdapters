/*
 * Copyright 2014 - 2019 Michael Rapp
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
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.datastructure.UnmodifiableList;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.datastructure.group.GroupIterator;
import de.mrapp.android.adapter.datastructure.group.GroupListIterator;
import de.mrapp.android.adapter.datastructure.group.UnmodifiableGroupList;
import de.mrapp.android.adapter.decorator.AbstractExpandableListDecorator;
import de.mrapp.android.adapter.list.selectable.MultipleChoiceListAdapterImplementation;
import de.mrapp.android.adapter.util.AdapterViewUtil;
import de.mrapp.android.util.logging.LogLevel;
import de.mrapp.android.util.logging.Logger;
import de.mrapp.android.util.view.ExpandableGridView;
import de.mrapp.android.util.view.ViewHolder;
import de.mrapp.util.Condition;
import de.mrapp.util.datastructure.ListenerList;

/**
 * An abstract base class for all adapters, whose underlying data is managed as a list of arbitrary
 * group and child items. Such an adapter's purpose is to provide the underlying data for
 * visualization using a {@link ExpandableListView} widget.
 *
 * @param <GroupType>
 *         The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *         The type of the underlying data of the adapter's child items
 * @param <DecoratorType>
 *         The type of the decorator, which allows to customize the appearance of the views, which
 *         are used to visualize the group and child items of the adapter
 * @author Michael Rapp
 * @since 0.1.0
 */
public abstract class AbstractExpandableListAdapter<GroupType, ChildType, DecoratorType extends AbstractExpandableListDecorator<GroupType, ChildType>>
        extends RecyclerView.Adapter<ViewHolder>
        implements ExpandableListAdapter<GroupType, ChildType> {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The key, which is used to store the state of the view, the adapter has been attached to,
     * within a bundle.
     */
    @VisibleForTesting
    protected static final String ADAPTER_VIEW_STATE_BUNDLE_KEY =
            AbstractExpandableListAdapter.class.getSimpleName() + "::AdapterViewState";

    /**
     * The key, which is used to store the adapter, which manages the adapter's group items, within
     * a bundle.
     */
    @VisibleForTesting
    protected static final String GROUP_ADAPTER_BUNDLE_KEY =
            AbstractExpandableListAdapter.class.getSimpleName() + "::GroupAdapterState";

    /**
     * The key, which is used to store the adapters, which manage the adapter's child items, within
     * a bundle.
     */
    @VisibleForTesting
    protected static final String CHILD_ADAPTER_BUNDLE_KEY =
            AbstractExpandableListAdapter.class.getSimpleName() + "::ChildAdapterState_%d";

    /**
     * The key, which is used to store, whether duplicate child items should be allowed, or not,
     * within a bundle.
     */
    @VisibleForTesting
    protected static final String ALLOW_DUPLICATE_CHILDREN_BUNDLE_KEY =
            AbstractExpandableListAdapter.class.getSimpleName() + "::AllowDuplicateChildren";

    /**
     * The key, which is used to store, whether a group's expansion should be triggered, when it is
     * clicked by the user, or not, within a bundle.
     */
    @VisibleForTesting
    protected static final String TRIGGER_GROUP_EXPANSION_ON_CLICK_BUNDLE_KEY =
            AbstractExpandableListAdapter.class.getSimpleName() + "::TriggerGroupExpansionOnClick";

    /**
     * The key, which is used to store the log level, which is used for logging, within a bundle.
     */
    @VisibleForTesting
    protected static final String LOG_LEVEL_BUNDLE_KEY =
            AbstractExpandableListAdapter.class.getSimpleName() + "::LogLevel";

    /**
     * The flag, which is used to identify view types, which correspond to group items.
     */
    private static final int FLAG_VIEW_TYPE_GROUP = 0x10;

    /**
     * The flag, which is used to identify view types, which correspond to child items.
     */
    private static final int FLAG_VIEW_TYPE_CHILD = 0x01;

    /**
     * The context, the adapter belongs to.
     */
    private final transient Context context;

    /**
     * The decorator, which allows to customize the appearance of the views, which are used to
     * visualize the group and child items of the adapter.
     */
    private transient DecoratorType decorator;

    /**
     * The logger, which is used for logging.
     */
    private final transient Logger logger;

    /**
     * The data set observable, which is notified, when the underlying data of the adapter has been
     * changed.
     */
    private final transient DataSetObservable dataSetObservable;

    /**
     * A set, which contains the listeners, which should be notified, when an item of the adapter
     * has been clicked by the user.
     */
    private transient ListenerList<ExpandableListAdapterItemClickListener<GroupType, ChildType>>
            itemClickListeners;

    /**
     * A set, which contains the listeners, which should be notified, when an item of the adapter
     * has been long-clicked by the user.
     */
    private transient ListenerList<ExpandableListAdapterItemLongClickListener<GroupType, ChildType>>
            itemLongClickListeners;

    /**
     * A set, which contains the listeners, which should be notified, when the adapter's underlying
     * data has been modified.
     */
    private transient ListenerList<ExpandableListAdapterListener<GroupType, ChildType>>
            adapterListeners;

    /**
     * A set, which contains the listeners, which should be notified, when a group item has been
     * expanded or collapsed.
     */
    private transient ListenerList<ExpansionListener<GroupType, ChildType>> expansionListeners;

    /**
     * The view, the adapter is currently attached to.
     */
    private transient ExpandableListView adapterView;

    /**
     * The expandable grid view, the adapter is currently attached to.
     */
    private transient ExpandableGridView expandableGridView;

    /**
     * The expandable recycler view, the adapter is currently attached to.
     */
    private transient RecyclerView expandableRecyclerView;

    /**
     * True, if duplicate children, regardless from the group they belong to, are allowed, false
     * otherwise.
     */
    private boolean allowDuplicateChildren;

    /**
     * True, if the a group's expansion should be triggered, when it is clicked by the user, false
     * otherwise.
     */
    private boolean triggerGroupExpansionOnClick;

    /**
     * True, if the method <code>notifyDataSetChanged():void</code> is automatically called when the
     * adapter's underlying data has been changed, false otherwise.
     */
    private boolean notifyOnChange;

    /**
     * An adapter, which manages the adapter's group items.
     */
    private MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter;

    /**
     * True, if the adapter view has been marked to be tainted, false otherwise. If the adapter is
     * marked as tainted, it will be synchronized with the adapter's underlying data, when calling
     * the method <code>notifyDataSetChanged</code> the next time
     */
    private boolean adapterViewTainted;

    /**
     * Notifies all listeners, which have been registered to be notified, when an item of the
     * adapter has been clicked by the user, about a group, which has been clicked.
     *
     * @param group
     *         The group, which has been clicked by the user, as an instance of the generic type
     *         GroupType. The item may not be null
     * @param index
     *         The index of the group, which has been clicked by the user, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1
     */
    private void notifyOnGroupClicked(@NonNull final GroupType group, final int index) {
        for (ExpandableListAdapterItemClickListener<GroupType, ChildType> listener : itemClickListeners) {
            listener.onGroupClicked(this, group, index);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when an item of the
     * adapter has been clicked by the user, about a header, which has been clicked.
     *
     * @param view
     *         The header view, which has been clicked by the user, as an instance of the class
     *         {@link View}. The view may not be null
     * @param index
     *         The index of the header, which has been clicked by the user, as an {@link Integer}
     *         value
     */
    private void notifyOnHeaderClicked(@NonNull final View view, final int index) {
        for (ExpandableListAdapterItemClickListener<GroupType, ChildType> listener : itemClickListeners) {
            listener.onHeaderClicked(this, view, index);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when an item of the
     * adapter has been clicked by the user, about a footer, which has been clicked.
     *
     * @param view
     *         The footer view, which has been clicked by the user, as an instance of the class
     *         {@link View}. The view may not be null
     * @param index
     *         The index of the footer, which has been clicked by the user, as an {@link Integer}
     *         value
     */
    private void notifyOnFooterClicked(@NonNull final View view, final int index) {
        for (ExpandableListAdapterItemClickListener<GroupType, ChildType> listener : itemClickListeners) {
            listener.onFooterClicked(this, view, index);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when an item of the
     * adapter has been clicked by the user, about a child, which has been clicked.
     *
     * @param child
     *         The child, which has been clicked by the user, as an instance of the generic type
     *         ChildType. The item may not be null
     * @param childIndex
     *         The index of the child, which has been clicked by the user, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getChildCount(groupIndex):int</code> - 1
     * @param group
     *         The group, the child, which has been clicked by the user, belongs to, as an instance
     *         of the generic type GroupType. The item may not be null
     * @param groupIndex
     *         The index of the group, the child, which has been clicked by the user, belongs to, as
     *         an {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1
     */
    private void notifyOnChildClicked(@NonNull final ChildType child, final int childIndex,
                                      @NonNull final GroupType group, final int groupIndex) {
        for (ExpandableListAdapterItemClickListener<GroupType, ChildType> listener : itemClickListeners) {
            listener.onChildClicked(this, child, childIndex, group, groupIndex);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when an item of the
     * adapter has been long-clicked by the user, about a group, which has been long-clicked.
     *
     * @param group
     *         The group, which has been long-clicked by the user, as an instance of the generic
     *         type GroupType. The item may not be null
     * @param index
     *         The index of the group, which has been long-clicked by the user, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1
     * @return True, if a listener has consumed the long-click, false otherwise
     */
    private boolean notifyOnGroupLongClicked(@NonNull final GroupType group, final int index) {
        for (ExpandableListAdapterItemLongClickListener<GroupType, ChildType> listener : itemLongClickListeners) {
            if (listener.onGroupLongClicked(this, group, index)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when an item of the
     * adapter has been long-clicked by the user, about a header, which has been long-clicked.
     *
     * @param view
     *         The header view, which has been long-clicked by the user, as an instance of the class
     *         {@link View}. The view may not be null
     * @param index
     *         The index of the header, which has been long-clicked by the user, as an {@link
     *         Integer} value
     * @return True, if a listener has consumed the long-click-false otherwise
     */
    private boolean notifyOnHeaderLongClicked(@NonNull final View view, final int index) {
        for (ExpandableListAdapterItemLongClickListener<GroupType, ChildType> listener : itemLongClickListeners) {
            if (listener.onHeaderLongClicked(this, view, index)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when an item of the
     * adapter has been long-clicked by the user, about a footer, which has been long-clicked.
     *
     * @param view
     *         The footer view, which has been long-clicked by the user, as an instance of the class
     *         {@link View}. The view may not be null
     * @param index
     *         The index of the footer, which has been long-clicked by the user, as an {@link
     *         Integer} value
     * @return True, if a listener has consumed the long-click-false otherwise
     */
    private boolean notifyOnFooterLongClicked(@NonNull final View view, final int index) {
        for (ExpandableListAdapterItemLongClickListener<GroupType, ChildType> listener : itemLongClickListeners) {
            if (listener.onFooterLongClicked(this, view, index)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when an item of the
     * adapter has been long-clicked by the user, about a child, which has been long-clicked.
     *
     * @param child
     *         The child, which has been long-clicked by the user, as an instance of the generic
     *         type ChildType. The item may not be null
     * @param childIndex
     *         The index of the child, which has been long-clicked by the user, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getChildCount(groupIndex):int</code> - 1
     * @param group
     *         The group, the child, which has been long-clicked by the user, belongs to, as an
     *         instance of the generic type GroupType. The item may not be null
     * @param groupIndex
     *         The index of the group, the child, which has been long-clicked by the user, belongs
     *         to, as an {@link Integer} value. The index must be between 0 and the value of the
     *         method <code>getGroupCount():int</code> - 1
     * @return True, if a listener has consumed the long-click, false otherwise
     */
    private boolean notifyOnChildLongClicked(@NonNull final ChildType child, final int childIndex,
                                             @NonNull final GroupType group, final int groupIndex) {
        for (ExpandableListAdapterItemLongClickListener<GroupType, ChildType> listener : itemLongClickListeners) {
            if (listener.onChildLongClicked(this, child, childIndex, group, groupIndex)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when the adapter's
     * underlying data has been modified, about a group, which has been added to the adapter.
     *
     * @param group
     *         The group, which has been added to the adapter, as an instance of the generic type
     *         GroupType. The group may not be null
     * @param index
     *         The index of the group, which has been added to the adapter, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1
     */
    private void notifyOnGroupAdded(@NonNull final GroupType group, final int index) {
        for (ExpandableListAdapterListener<GroupType, ChildType> listener : adapterListeners) {
            listener.onGroupAdded(this, group, index);
        }
    }

    /**
     * Notifies all listeners, which have been register to be notified, when the adapter's
     * underlying data has been modified, about a group, which has been removed from the adapter.
     *
     * @param group
     *         The group, which has been removed from the adapter, as an instance of the generic
     *         type GroupType. The group may not be null
     * @param index
     *         The index of the group, which has been removed from the adapter, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1
     */
    private void notifyOnGroupRemoved(@NonNull final GroupType group, final int index) {
        for (ExpandableListAdapterListener<GroupType, ChildType> listener : adapterListeners) {
            listener.onGroupRemoved(this, group, index);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when the adapter's
     * underlying data has been modified, about a child, which has been added to the adapter.
     *
     * @param child
     *         The child, which has been added to the adapter, as an instance of the generic type
     *         ChildType. The child may not be null
     * @param childIndex
     *         The index of the child, which has been added to the adapter, as an {@link Integer}
     *         value. The index must be between 0 and the value of the method
     *         <code>getChildCount(groupIndex):int</code> - 1
     * @param group
     *         The group, the child, which has been added to the adapter, belongs to, as an instance
     *         of the generic type GroupType. The group may not be null
     * @param groupIndex
     *         The index of the group, the child, which has been added to the adapter, belongs to,
     *         as an {@link Integer} value. The index must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1
     */
    private void notifyOnChildAdded(@NonNull final ChildType child, final int childIndex,
                                    @NonNull final GroupType group, final int groupIndex) {
        for (ExpandableListAdapterListener<GroupType, ChildType> listener : adapterListeners) {
            listener.onChildAdded(this, child, childIndex, group, groupIndex);
        }
    }

    /**
     * Notifies all listeners, which have been registered to be notified, when the adapter's
     * underlying data has been modified, about a child, which has been removed from the adapter.
     *
     * @param child
     *         The child, which has been removed from the adapter, as an instance of the generic
     *         type ChildType. The child may not be null
     * @param childIndex
     *         The index of the child, which has been removed from the adapter, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getChildCount(groupIndex):int</code> - 1
     * @param group
     *         The group, the child, which has been removed from the adapter, belongs to, as an
     *         instance of the generic type GroupType. The group may not be null
     * @param groupIndex
     *         The index of the group, the child, which has been removed from the adapter, belongs
     *         to, as an {@link Integer} value. The index must be between 0 and the value of the
     *         method <code>getGroupCount():int</code> - 1
     */
    private void notifyOnChildRemoved(@NonNull final ChildType child, final int childIndex,
                                      @NonNull final GroupType group, final int groupIndex) {
        for (ExpandableListAdapterListener<GroupType, ChildType> listener : adapterListeners) {
            listener.onChildRemoved(this, child, childIndex, group, groupIndex);
        }
    }

    /**
     * Notifies all listener, which have been registered to be notified, when a group item has been
     * expanded or collapsed, that a group has been expanded.
     *
     * @param group
     *         The group, which has been expanded, as an instance of the generic type GroupType. The
     *         group may not be null
     * @param groupIndex
     *         The index of the group, which has been expanded, as an {@link Integer} value
     */
    private void notifyOnGroupExpanded(@NonNull final GroupType group, final int groupIndex) {
        for (ExpansionListener<GroupType, ChildType> listener : expansionListeners) {
            listener.onGroupExpanded(this, group, groupIndex);
        }
    }

    /**
     * Notifies all listener, which have been registered to be notified, when a group item has been
     * expanded or collapsed, that a group has been collapsed.
     *
     * @param group
     *         The group, which has been collapsed, as an instance of the generic type GroupType.
     *         The group may not be null
     * @param groupIndex
     *         The index of the group, which has been collapsed, as an {@link Integer} value
     */
    private void notifyOnGroupCollapsed(@NonNull final GroupType group, final int groupIndex) {
        for (ExpansionListener<GroupType, ChildType> listener : expansionListeners) {
            listener.onGroupCollapsed(this, group, groupIndex);
        }
    }

    /**
     * Creates and returns an adapter, which may be used to manage the adapter's child items.
     *
     * @return The adapter, which has been created, as an instance of the type {@link
     * MultipleChoiceListAdapter}. The adapter may not be null
     */
    @CallSuper
    private MultipleChoiceListAdapter<ChildType> createChildAdapter() {
        MultipleChoiceListAdapter<ChildType> childAdapter =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new NullObjectDecorator<ChildType>());
        childAdapter.setLogLevel(LogLevel.OFF);
        return childAdapter;
    }

    /**
     * Creates and returns a listener, which allows to trigger the expansion state of a group, when
     * it is clicked by the user.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * ExpandableListAdapterItemClickListener}
     */
    private ExpandableListAdapterItemClickListener<GroupType, ChildType> createGroupClickListener() {
        return new ExpandableListAdapterItemClickListener<GroupType, ChildType>() {

            @Override
            public void onGroupClicked(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final GroupType group, final int index) {
                if (isGroupExpansionTriggeredOnClick()) {
                    triggerGroupExpansion(index);
                    getLogger().logVerbose(getClass(), "Triggering group expansion on click...");
                }
            }

            @Override
            public void onChildClicked(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final ChildType child, final int childIndex,
                    @NonNull final GroupType group, final int groupIndex) {

            }

            @Override
            public void onHeaderClicked(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final View view, final int index) {

            }

            @Override
            public void onFooterClicked(
                    @NonNull final ExpandableListAdapter<GroupType, ChildType> adapter,
                    @NonNull final View view, final int index) {

            }

        };
    }

    /**
     * Creates and returns an {@link ExpandableListView.OnGroupClickListener}, which is invoked,
     * when a group item of the adapter has been clicked.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * ExpandableListView.OnGroupClickListener}
     */
    private ExpandableListView.OnGroupClickListener createAdapterViewOnGroupClickListener() {
        return new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(final ExpandableListView parent, final View v,
                                        final int groupPosition, final long id) {
                notifyOnGroupClicked(getGroup(groupPosition), groupPosition);
                return true;
            }

        };
    }

    /**
     * Creates and returns an {@link ExpandableListView.OnChildClickListener}, which is invoked,
     * when a child item of the adapter has been clicked.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * ExpandableListView.OnChildClickListener}
     */
    private ExpandableListView.OnChildClickListener createAdapterViewOnChildClickListener() {
        return new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(final ExpandableListView parent, final View v,
                                        final int groupPosition, final int childPosition,
                                        final long id) {
                notifyOnChildClicked(getChild(groupPosition, childPosition), childPosition,
                        getGroup(groupPosition), groupPosition);
                return true;
            }

        };
    }

    /**
     * Creates and returns an {@link AdapterView.OnItemClickListener}, which is invoked, when an
     * item of the adapter has been clicked.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * AdapterView.OnItemClickListener}
     */
    private AdapterView.OnItemClickListener createAdapterViewOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, final View view,
                                    final int position, final long id) {
                int itemType = ExpandableListView.getPackedPositionType(id);

                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    int groupIndex = ExpandableListView.getPackedPositionGroup(id);

                    if (groupIndex == Integer.MAX_VALUE) {
                        if (position < adapterView.getHeaderViewsCount()) {
                            notifyOnHeaderClicked(view, position);
                        } else {
                            int headerCount = expandableGridView.getHeaderViewsCount();
                            int itemCount = getGroupCount() - getChildCount();
                            notifyOnFooterClicked(view, position - headerCount - itemCount);
                        }
                    }
                }
            }

        };
    }

    /**
     * Creates and returns an {@link AdapterView.OnItemLongClickListener}, which is invoked, when an
     * item of the adapter has been long-clicked.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * AdapterView.OnItemLongClickListener}
     */
    private AdapterView.OnItemLongClickListener createAdapterViewOnItemLongClickListener() {
        return new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view,
                                           final int position, final long id) {
                int itemType = ExpandableListView.getPackedPositionType(id);

                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    int groupIndex = ExpandableListView.getPackedPositionGroup(id);

                    if (groupIndex == Integer.MAX_VALUE) {
                        if (position < adapterView.getHeaderViewsCount()) {
                            return notifyOnHeaderLongClicked(view, position);
                        } else {
                            int headerCount = adapterView.getHeaderViewsCount();
                            int itemCount = getGroupCount() + getChildCount();
                            return notifyOnFooterLongClicked(view,
                                    position - headerCount - itemCount);
                        }
                    } else {
                        int childIndex = ExpandableListView.getPackedPositionChild(id);
                        return notifyOnChildLongClicked(getChild(groupIndex, childIndex),
                                childIndex, getGroup(groupIndex), groupIndex);
                    }
                } else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    int groupIndex = ExpandableListView.getPackedPositionGroup(id);
                    return notifyOnGroupLongClicked(getGroup(groupIndex), groupIndex);
                }

                return false;
            }

        };
    }

    /**
     * Creates and returns an {@link ExpandableGridView.OnGroupClickListener}, which is invoked,
     * when a group item of the adapter has been clicked.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * ExpandableGridView.OnGroupClickListener}
     */
    private ExpandableGridView.OnGroupClickListener createExpandableGridViewOnGroupClickListener() {
        return new ExpandableGridView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(@NonNull final ExpandableGridView parent,
                                        @NonNull final View v, final int groupPosition,
                                        final long id) {
                notifyOnGroupClicked(getGroup(groupPosition), groupPosition);
                return true;
            }

        };
    }

    /**
     * Creates and returns an {@link ExpandableGridView.OnChildClickListener}, which is invoked,
     * when a child item of the adapter has been clicked.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * ExpandableGridView.OnChildClickListener}
     */
    private ExpandableGridView.OnChildClickListener createExpandableGridViewOnChildClickListener() {
        return new ExpandableGridView.OnChildClickListener() {

            @Override
            public boolean onChildClick(@NonNull final ExpandableGridView parent,
                                        @NonNull final View v, final int groupPosition,
                                        final int childPosition, final long id) {
                notifyOnChildClicked(getChild(groupPosition, childPosition), childPosition,
                        getGroup(groupPosition), groupPosition);
                return true;
            }

        };
    }

    /**
     * Creates and returns an {@link AdapterView.OnItemClickListener}, which is invoked, when an
     * item of the adapter has been clicked.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * AdapterView.OnItemClickListener}
     */
    private AdapterView.OnItemClickListener createExpandableGridViewOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, final View view,
                                    final int position, final long id) {
                int itemType = ExpandableGridView.getPackedPositionType(id);

                if (itemType == ExpandableGridView.PACKED_POSITION_TYPE_CHILD) {
                    int groupIndex = ExpandableGridView.getPackedPositionGroup(id);

                    if (groupIndex == Integer.MAX_VALUE) {
                        if (position < expandableGridView.getHeaderViewsCount()) {
                            notifyOnHeaderClicked(view, position);
                        } else {
                            int headerCount = expandableGridView.getHeaderViewsCount();
                            int itemCount = getGroupCount() - getChildCount();
                            notifyOnFooterClicked(view, position - headerCount - itemCount);
                        }
                    }
                }
            }

        };
    }

    /**
     * Creates and returns an {@link AdapterView.OnItemLongClickListener}, which is invoked, when an
     * item of the adapter has been long-clicked.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * AdapterView.OnItemLongClickListener}
     */
    private AdapterView.OnItemLongClickListener createExpandableGridViewOnItemLongClickListener() {
        return new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view,
                                           final int position, final long id) {
                int itemType = ExpandableGridView.getPackedPositionType(id);

                if (itemType == ExpandableGridView.PACKED_POSITION_TYPE_CHILD) {
                    int groupIndex = ExpandableGridView.getPackedPositionGroup(id);

                    if (groupIndex == Integer.MAX_VALUE) {
                        if (position < expandableGridView.getHeaderViewsCount()) {
                            return notifyOnHeaderLongClicked(view, position);
                        } else {
                            int headerCount = adapterView.getHeaderViewsCount();
                            int itemCount = getGroupCount() + getChildCount();
                            return notifyOnFooterLongClicked(view,
                                    position - headerCount - itemCount);
                        }
                    } else {
                        int childIndex = ExpandableListView.getPackedPositionChild(id);
                        return notifyOnChildLongClicked(getChild(groupIndex, childIndex),
                                childIndex, getGroup(groupIndex), groupIndex);
                    }
                } else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    int groupIndex = ExpandableListView.getPackedPositionGroup(id);
                    return notifyOnGroupLongClicked(getGroup(groupIndex), groupIndex);
                }

                return false;
            }

        };
    }

    /**
     * Creates and returns an {@link OnClickListener}, which is invoked, when a group item of the
     * adapter has been clicked.
     *
     * @param groupIndex
     *         The index of the group item, which has been clicked, as an {@link Integer} value
     * @return The listener, which has been created, as an instance of the type {@link
     * OnClickListener}
     */
    private OnClickListener createRecyclerViewOnGroupClickListener(final int groupIndex) {
        return new OnClickListener() {

            @Override
            public void onClick(final View v) {
                GroupType group = getGroup(groupIndex);
                notifyOnGroupClicked(group, groupIndex);
            }

        };
    }

    /**
     * Creates and returns an {@link OnClickListener}, which is invoked, when a group item of the
     * adapter has been clicked.
     *
     * @param groupIndex
     *         The index of the group item, which has been clicked, as an {@link Integer} value
     * @return The listener, which has been created, as an instance of the type {@link
     * OnClickListener}
     */
    private OnClickListener createRecyclerViewOnChildClickListener(final int groupIndex,
                                                                   final int childIndex) {
        return new OnClickListener() {

            @Override
            public void onClick(final View v) {
                GroupType group = getGroup(groupIndex);
                ChildType child = getChild(groupIndex, childIndex);
                notifyOnChildClicked(child, childIndex, group, groupIndex);
            }

        };
    }

    /**
     * Creates and returns an {@link OnLongClickListener}, which is invoked, when a group item of
     * the adapter has been long-clicked.
     *
     * @param groupIndex
     *         The index of the group item, which has been long-clicked, as an {@link Integer}
     *         value
     * @return The listener, which has been created, as an instance of the type {@link
     * OnLongClickListener}
     */
    private OnLongClickListener createRecyclerViewOnGroupLongClickListener(final int groupIndex) {
        return new OnLongClickListener() {

            @Override
            public boolean onLongClick(final View v) {
                GroupType group = getGroup(groupIndex);
                return notifyOnGroupLongClicked(group, groupIndex);
            }

        };
    }

    /**
     * Creates and returns an {@link OnLongClickListener}, which is invoked, when a child item of
     * the adapter has been long-clicked.
     *
     * @param groupIndex
     *         The index of the group, the child item, which has been long-clicked, belongs to, as
     *         an {@link Integer} value
     * @param childIndex
     *         The index of the child item which has been long-clicked, as an {@link Integer} value
     * @return The listener, which has been created, as an instance of the type {@link
     * OnLongClickListener}
     */
    private OnLongClickListener createRecyclerViewOnChildLongClickListener(final int groupIndex,
                                                                           final int childIndex) {
        return new OnLongClickListener() {

            @Override
            public boolean onLongClick(final View v) {
                GroupType group = getGroup(groupIndex);
                ChildType child = getChild(groupIndex, childIndex);
                return notifyOnChildLongClicked(child, childIndex, group, groupIndex);
            }

        };
    }

    /**
     * Synchronizes the adapter view, the adapter is currently attached to, with the adapter's
     * underlying data, e.g. by collapsing or expanding its groups depending on their current
     * expansion states.
     */
    private void syncAdapterView() {
        if (adapterView != null || expandableGridView != null || expandableRecyclerView == null) {
            if (adapterView != null || expandableGridView != null) {
                for (int i = 0; i < getGroupCount(); i++) {
                    if (isGroupExpanded(i)) {
                        if (adapterView != null) {
                            adapterView.expandGroup(i);
                        } else {
                            expandableGridView.expandGroup(i);
                        }
                    } else {
                        if (adapterView != null) {
                            adapterView.collapseGroup(i);
                        } else {
                            expandableGridView.collapseGroup(i);
                        }
                    }
                }
            }

            adapterViewTainted = false;
        }
    }

    /**
     * Creates an observer, which allows to notify the adapter's data set observable, when the
     * underlying data of the adapter has been changed.
     *
     * @return The observer, which has been created, as an instance of the class {@link
     * RecyclerView.AdapterDataObserver}
     */

    private RecyclerView.AdapterDataObserver createAdapterDataSetObserver() {
        return new RecyclerView.AdapterDataObserver() {

            /**
             * Notifies the adapter's data set observable, that the underlying data of the adapter has been changed.
             */
            private void notifyChanged() {
                dataSetObservable.notifyChanged();

                if (adapterViewTainted) {
                    syncAdapterView();
                }
            }

            @Override
            public void onChanged() {
                notifyChanged();
            }

            @Override
            public void onItemRangeChanged(final int positionStart, final int itemCount) {
                notifyChanged();
            }

            @Override
            public void onItemRangeChanged(final int positionStart, final int itemCount,
                                           final Object payload) {
                notifyChanged();
            }

            @Override
            public void onItemRangeInserted(final int positionStart, final int itemCount) {
                notifyChanged();
            }

            @Override
            public void onItemRangeRemoved(final int positionStart, final int itemCount) {
                notifyChanged();
            }

            @Override
            public void onItemRangeMoved(final int fromPosition, final int toPosition,
                                         final int itemCount) {
                notifyChanged();
            }

        };
    }

    /**
     * Returns a pair, which contains the group and child index of the item, which corresponds to a
     * specific packed position.
     *
     * @param packedPosition
     *         The packed position of the item, whose group and child index should be returned, as
     *         an {@link Integer} value
     * @return A pair, which contains the group and child index of the item, which corresponds to
     * the given packed position, as an instance of the class {@link Pair}
     */
    @NonNull
    private Pair<Integer, Integer> getPackedPositionGroupAndChild(final int packedPosition) {
        int currentPosition = packedPosition;
        int groupIndex = -1;
        int childIndex = -1;

        for (int i = 0; i < getGroupCount(); i++) {
            if (currentPosition == 0) {
                groupIndex = i;
                break;
            } else {
                currentPosition--;

                if (isGroupExpanded(i)) {
                    int childCount = getChildCount(i);

                    if (currentPosition < childCount) {
                        groupIndex = i;
                        childIndex = currentPosition;
                        break;
                    } else {
                        currentPosition -= childCount;
                    }
                }
            }
        }

        return new Pair<>(groupIndex, childIndex);
    }

    /**
     * Returns, the context, the adapter belongs to.
     *
     * @return The context, the adapter belongs to, as an instance of the class {@link Context}. The
     * context may not be null
     */
    protected final Context getContext() {
        return context;
    }

    /**
     * Returns the logger, which is used for logging.
     *
     * @return The logger, which is used for logging, as an instance of the class Logger. The logger
     * may not be null
     */
    protected final Logger getLogger() {
        return logger;
    }

    /**
     * Returns the adapter, which manages the adapter's group items.
     *
     * @return The adapter, which manages the adapter's group items, as an instance of the type
     * {@link MultipleChoiceListAdapter}. The adapter may not be null
     */
    protected final MultipleChoiceListAdapter<Group<GroupType, ChildType>> getGroupAdapter() {
        return groupAdapter;
    }

    /**
     * Creates and returns a deep copy of the adapter, which manages the adapter's group items.
     *
     * @return A deep copy of the adapter, which manages the adapter's group items, as an instance
     * of the type {@link MultipleChoiceListAdapter}. The adapter may not be null
     * @throws CloneNotSupportedException
     *         The exception, which is thrown, if cloning is not supported by the adapter's
     *         underlying data
     */
    protected final MultipleChoiceListAdapter<Group<GroupType, ChildType>> cloneGroupAdapter()
            throws CloneNotSupportedException {
        return groupAdapter.clone();
    }

    /**
     * Returns a list, which contains the listeners, which should be notified, when an item of the
     * adapter has been clicked by the user.
     *
     * @return A list, which contains the listeners, which should be notified, when an item of the
     * adapter has been clicked by the user, as an instance of the class ListenerList or an empty
     * list, if no listeners should be notified
     */
    protected final ListenerList<ExpandableListAdapterItemClickListener<GroupType, ChildType>> getItemClickListeners() {
        return itemClickListeners;
    }

    /**
     * Returns a list, which contains the listeners, which should be notified, when an item of the
     * adapter has been long-clicked by the user.
     *
     * @return A list, which contains the listeners, which should be notified, when an item of the
     * adapter has been long-clicked by the user, as an instance of the class ListenerList or an
     * empty list, if no listeners should be notified
     */
    protected final ListenerList<ExpandableListAdapterItemLongClickListener<GroupType, ChildType>> getItemLongClickListeners() {
        return itemLongClickListeners;
    }

    /**
     * Returns a list, which contains the listeners, which should be notified, when the adapter's
     * underlying data has been modified.
     *
     * @return A list, which contains the listeners, which should be notified, when the adapter's
     * underlying data has been modified, as an instance of the class ListenerList or an empty list,
     * if no listeners should be notified
     */
    protected final ListenerList<ExpandableListAdapterListener<GroupType, ChildType>> getAdapterListeners() {
        return adapterListeners;
    }

    /**
     * Returns a list, which contains the listeners, which should be notified, when a group item has
     * been expanded or collapsed.
     *
     * @return A list, which contains the listeners which should be notified, when a group item has
     * been expanded or collapsed, as an instance of the class ListenerList or an empty list, if no
     * listeners should be notified
     */
    protected final ListenerList<ExpansionListener<GroupType, ChildType>> getExpansionListeners() {
        return expansionListeners;
    }

    /**
     * Notifies all observers, that the adapter's underlying data has been changed, if automatically
     * notifying such events is currently enabled.
     */
    protected final void notifyObserversOnDataSetChanged() {
        if (isNotifiedOnChange()) {
            notifyDataSetChanged();
        }
    }

    /**
     * Notifies all observers, that a group has been added, if notifying such events is currently
     * enabled.
     *
     * @param groupIndex
     *         The index of the group, which has been added, as an {@link Integer} value
     */
    protected final void notifyObserversOnGroupInserted(final int groupIndex) {
        if (isNotifiedOnChange()) {
            if (expandableRecyclerView != null) {
                notifyGroupInserted(groupIndex);
            } else {
                notifyDataSetChanged();
            }
        }
    }

    /**
     * Notifies all observers, that a group has been removed, if notifying such events is currently
     * enabled.
     *
     * @param groupIndex
     *         The index of the group, which has been removed, as an {@link Integer} value
     */
    protected final void notifyObserversOnGroupRemoved(final int groupIndex) {
        if (isNotifiedOnChange()) {
            if (expandableRecyclerView != null) {
                notifyGroupRemoved(groupIndex);
            } else {
                notifyDataSetChanged();
            }
        }
    }

    /**
     * Notifies all observers, that a group has been changed, if notifying such events is currently
     * enabled.
     *
     * @param groupIndex
     *         The index of the group, which has been changed, as an {@link Integer} value
     */
    protected final void notifyObserversOnGroupChanged(final int groupIndex) {
        if (isNotifiedOnChange()) {
            if (expandableRecyclerView != null) {
                notifyGroupChanged(groupIndex);
            } else {
                notifyDataSetChanged();
            }
        }
    }

    /**
     * Notifies all observers, that a child has been added, if notifying such events is currently
     * enabled.
     *
     * @param groupIndex
     *         The index of the group, the child, which has been added, belongs to, as an {@link
     *         Integer} value
     * @param childIndex
     *         The index of the child, which has been added, as an {@link Integer} value
     */
    protected final void notifyObserversOnChildInserted(final int groupIndex,
                                                        final int childIndex) {
        if (isNotifiedOnChange()) {
            if (expandableRecyclerView != null) {
                notifyChildInserted(groupIndex, childIndex);
            } else {
                notifyDataSetChanged();
            }
        }
    }

    /**
     * Notifies all observers, that multiple children have been added, if notifying such events is
     * currently enabled.
     *
     * @param groupIndex
     *         The index of the group, the children, which have been added, belong to, as an {@link
     *         Integer} value
     * @param startIndex
     *         The index of the first child, which has been added, as an {@link Integer} value
     * @param childCount
     *         The number of children, which have been added, as an {@link Integer} value
     */
    protected final void notifyObserversOnChildRangeInserted(final int groupIndex,
                                                             final int startIndex,
                                                             final int childCount) {
        if (isNotifiedOnChange() && childCount > 0) {
            if (expandableRecyclerView != null) {
                notifyChildRangeInserted(groupIndex, startIndex, childCount);
            } else {
                notifyDataSetChanged();
            }
        }
    }

    /**
     * Notifies all observers, that a child has been removed, if notifying such events is currently
     * enabled.
     *
     * @param groupIndex
     *         The index of the group, the child, which has been removed, belongs to, as an {@link
     *         Integer} value
     * @param childIndex
     *         The index of the child, which has been removed, as an {@link Integer} value
     */
    protected final void notifyObserversOnChildRemoved(final int groupIndex, final int childIndex) {
        if (isNotifiedOnChange()) {
            if (expandableRecyclerView != null) {
                notifyChildRemoved(groupIndex, childIndex);
            } else {
                notifyDataSetChanged();
            }
        }
    }

    /**
     * Notifies all observers, that multiple children have been removed, if notifying such events is
     * currently enabled.
     *
     * @param groupIndex
     *         The index of the group, the children, which have been removed, belong to, as an
     *         {@link Integer} value
     * @param startIndex
     *         The index of the first child, which has been removed, as an {@link Integer} value
     * @param childCount
     *         The number of children, which have been removed, as an {@link Integer} value
     */
    protected final void notifyObserversOnChildRangeRemoved(final int groupIndex,
                                                            final int startIndex,
                                                            final int childCount) {
        if (isNotifiedOnChange() && childCount > 0) {
            if (expandableRecyclerView != null) {
                notifyChildRangeRemoved(groupIndex, startIndex, childCount);
            } else {
                notifyDataSetChanged();
            }
        }
    }

    /**
     * Notifies all observers, that a child has been changed, if notifying such events is currently
     * enabled.
     *
     * @param groupIndex
     *         The index of the group, the child, which has been changed, belongs to, as an {@link
     *         Integer} value
     * @param childIndex
     *         The index of the child, which has been changed, as an {@link Integer} value
     */
    protected final void notifyObserversOnChildChanged(final int groupIndex, final int childIndex) {
        if (isNotifiedOnChange()) {
            if (expandableRecyclerView != null) {
                notifyChildChanged(groupIndex, childIndex);
            } else {
                notifyDataSetChanged();
            }
        }
    }

    /**
     * Notifies all observers, that a group has been expanded or collapsed, if notifying such events
     * is currently enabled.
     *
     * @param index
     *         The index of the group, which has been expanded or collapsed, as an {@link Integer}
     *         value
     * @param expanded
     *         True, if the group has been expanded, false, if it has been collapsed
     */
    protected final void notifyObserversOnExpansion(final int index, final boolean expanded) {
        if (index > 0) {
            if (isGroupExpanded(index - 1)) {
                notifyObserversOnChildChanged(index - 1, getChildCount(index - 1) - 1);
            } else {
                notifyObserversOnGroupChanged(index - 1);
            }
        }

        notifyObserversOnGroupChanged(index);

        if (expanded) {
            notifyObserversOnChildRangeInserted(index, 0, getChildCount(index));
        } else {
            notifyObserversOnChildRangeRemoved(index, 0, getChildCount(index));
        }

        if (index < getGroupCount() - 1) {
            notifyObserversOnGroupChanged(index + 1);
        }
    }

    /**
     * Returns the index of a specific group item or throws a {@link NoSuchElementException} if the
     * adapter does not contain the group item.
     *
     * @param group
     *         The group item, whose index should be returned, as an instance of the generic type
     *         GroupType. The group item may not be null
     * @return The index of the the given group item, as an {@link Integer} value
     */
    protected final int indexOfGroupOrThrowException(@NonNull final GroupType group) {
        int groupIndex = indexOfGroup(group);

        if (groupIndex != -1) {
            return groupIndex;
        } else {
            throw new NoSuchElementException("Adapter does not contain group \"" + group + "\"");
        }
    }

    /**
     * Returns the index of a specific child item within the group, which belongs to a specific
     * index, or throws a {@link NoSuchElementException} if the group does not contain the child
     * item.
     *
     * @param groupIndex
     *         The index of the group, the child item, whose index should be returned, belongs to,
     *         as an {@link Integer} value. The value must be between 0 and the value of the method
     *         <code>getGroupCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException}
     *         will be thrown
     * @param child
     *         The child item, whose index should be returned, as an instance of the generic type
     *         ChildType. The child item may not be null
     * @return The index of the given child item, as an {@link Integer} value
     */
    protected final int indexOfChildOrThrowException(final int groupIndex,
                                                     @NonNull final ChildType child) {
        int childIndex = indexOfChild(groupIndex, child);

        if (childIndex != -1) {
            return childIndex;
        } else {
            throw new NoSuchElementException(
                    "Group \"" + getGroup(groupIndex) + "\" at index " + groupIndex +
                            " does not contain child \"" + child + "\"");
        }
    }

    /**
     * Creates and returns a group. This method may be overridden by subclasses in order to modify
     * the group.
     *
     * @param groupIndex
     *         The index of the group, which should be created, as an {@link Integer} value
     * @param group
     *         The data of the group, which should be created, as an instance of the generic type
     *         GroupType. The data may not be null
     * @return The group, which has been created, as an instance of the class {@link Group}. The
     * group may not be null
     */
    @CallSuper
    protected Group<GroupType, ChildType> createGroup(final int groupIndex,
                                                      @NonNull final GroupType group) {
        return new Group<>(group, createChildAdapter());
    }

    /**
     * Marks the adapter view to be tainted. This causes it to be synchronized with the adapter's
     * underlying data, when calling the method <code>notifyDataSetChanged</code> the next time.
     */
    protected final void taintAdapterView() {
        if (adapterView != null || expandableGridView != null || expandableRecyclerView != null) {
            this.adapterViewTainted = true;
        }
    }

    /**
     * This method is invoked when the state of the adapter is about to be stored within a bundle.
     *
     * @param outState
     *         The bundle, which is used to store the state of the adapter within a bundle, as an
     *         instance of the class {@link Bundle}. The bundle may not be null
     */
    protected abstract void onSaveInstanceState(@NonNull final Bundle outState);

    /**
     * This method is invoked when the state of the adapter, which has previously been stored within
     * a bundle, is about to be restored.
     *
     * @param savedState
     *         The bundle, which has been previously used to store the state of the adapter, as an
     *         instance of the class {@link Bundle}. The bundle may not be null
     */
    protected abstract void onRestoreInstanceState(@NonNull final Bundle savedState);

    /**
     * This method is invoked to apply the decorator, which allows to customize the view, which is
     * used to visualize a specific group item.
     *
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param view
     *         The view, which is used to visualize the group item, as an instance of the class
     *         {@link View}. The view may not be null
     * @param index
     *         The index of the group item, which should be visualized, as an {@link Integer} value
     */
    protected abstract void applyDecoratorOnGroup(@NonNull final Context context,
                                                  @NonNull final View view, final int index);

    /**
     * This method is invoked to apply the decorator, which allows to customize the view, which is
     * used to visualize a specific child item.
     *
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param view
     *         The view, which is used to visualize the child item, as an instance of the class
     *         {@link View}. The view may not be null
     * @param groupIndex
     *         The index of the group, the child item, which should be visualized, belongs to, as an
     *         {@link Integer} value
     * @param childIndex
     *         The index of the child item, which should be visualized, as an {@link Integer} value
     */
    protected abstract void applyDecoratorOnChild(@NonNull final Context context,
                                                  @NonNull final View view, final int groupIndex,
                                                  final int childIndex);

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
     *         A list, which contains the listeners, which should be notified, when an item of the
     *         adapter has been clicked by the user, as an instance of the class ListenerList, or an
     *         empty list, if no listeners should be notified
     * @param itemLongClickListeners
     *         A list, which contains the listeners, which should be notified, when an item of the
     *         adapter has been long-clicked by the user, as an instance of the class ListenerList,
     *         or an empty list, if no listeners should be notified
     * @param adapterListeners
     *         A list, which contains the listeners, which should be notified, when the adapter's
     *         underlying data has been modified, as an instance of the class ListenerList, or an
     *         empty list, if no listeners should be notified
     * @param expansionListeners
     *         A list, which contains the listeners, which should be notified, when a group item has
     *         been expanded or collapsed, as an instance of the class ListenerList, or an empty
     *         list, if no listeners should be notified
     */
    protected AbstractExpandableListAdapter(@NonNull final Context context,
                                            @NonNull final DecoratorType decorator,
                                            @NonNull final LogLevel logLevel,
                                            @NonNull final MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter,
                                            final boolean allowDuplicateChildren,
                                            final boolean notifyOnChange,
                                            final boolean triggerGroupExpansionOnClick,
                                            @NonNull final ListenerList<ExpandableListAdapterItemClickListener<GroupType, ChildType>> itemClickListeners,
                                            @NonNull final ListenerList<ExpandableListAdapterItemLongClickListener<GroupType, ChildType>> itemLongClickListeners,
                                            @NonNull final ListenerList<ExpandableListAdapterListener<GroupType, ChildType>> adapterListeners,
                                            @NonNull final ListenerList<ExpansionListener<GroupType, ChildType>> expansionListeners) {
        Condition.INSTANCE.ensureNotNull(context, "The context may not be null");
        Condition.INSTANCE.ensureNotNull(decorator, "The decorator may not be null");
        Condition.INSTANCE
                .ensureNotNull(itemClickListeners, "The item click listeners may not be null");
        Condition.INSTANCE.ensureNotNull(itemLongClickListeners,
                "The item long click listeners may not be null");
        Condition.INSTANCE.ensureNotNull(adapterListeners, "The adapter listeners may not be null");
        Condition.INSTANCE
                .ensureNotNull(expansionListeners, "The expansion listeners may not be null");
        this.context = context;
        this.decorator = decorator;
        this.logger = new Logger(logLevel);
        this.dataSetObservable = new DataSetObservable();
        this.groupAdapter = groupAdapter;
        this.groupAdapter.setLogLevel(LogLevel.OFF);
        this.groupAdapter.notifyOnChange(false);
        this.allowDuplicateChildren = allowDuplicateChildren;
        this.notifyOnChange = notifyOnChange;
        this.triggerGroupExpansionOnClick = triggerGroupExpansionOnClick;
        this.itemClickListeners = itemClickListeners;
        this.itemLongClickListeners = itemLongClickListeners;
        this.adapterListeners = adapterListeners;
        this.expansionListeners = expansionListeners;
        setHasStableIds(true);
        addItemClickListener(createGroupClickListener());
        registerAdapterDataObserver(createAdapterDataSetObserver());
    }

    /**
     * Returns the decorator, which allows to customize the appearance of the views, which are used
     * to visualize the group and child items of the adapter.
     *
     * @return The decorator, which allows to customize the appearance of the views, which are used
     * to visualize the group and child items of the adapter, as an instance of the generic type
     * DecoratorType. The decorator may not be null
     */
    public final DecoratorType getDecorator() {
        return decorator;
    }

    /**
     * Sets the decorator, which allows to customize the appearance of the views, which are used to
     * visualize the group and child items of the adapter.
     *
     * @param decorator
     *         The decorator, which should be set, as an instance of the generic type DecoratorType.
     *         The decorator may not be null
     */
    public final void setDecorator(@NonNull final DecoratorType decorator) {
        Condition.INSTANCE.ensureNotNull(decorator, "The decorator may not be null");
        this.decorator = decorator;
        notifyObserversOnDataSetChanged();
    }

    /**
     * @see DataSetObservable#notifyInvalidated()
     */
    public void notifyDataSetInvalidated() {
        dataSetObservable.notifyInvalidated();
    }

    @Override
    public void registerDataSetObserver(final DataSetObserver observer) {
        dataSetObservable.registerObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(final DataSetObserver observer) {
        dataSetObservable.unregisterObserver(observer);
    }

    @Override
    public final void notifyGroupChanged(final int groupIndex) {
        notifyGroupChanged(groupIndex, false);
    }

    @Override
    public final void notifyGroupChanged(final int groupIndex, @Nullable final Object payload) {
        notifyGroupRangeChanged(groupIndex, 1, payload);
    }

    @Override
    public final void notifyGroupRangeChanged(final int startIndex, final int groupCount) {
        notifyGroupRangeChanged(startIndex, groupCount, false);
    }

    @Override
    public final void notifyGroupRangeChanged(final int startIndex, final int groupCount,
                                              @Nullable final Object payload) {
        Condition.INSTANCE.ensureAtLeast(groupCount, 1, "The group count must be at least 1");

        for (int i = 0; i < groupCount; i++) {
            int groupIndex = startIndex + i;
            int packedPosition = getPackedPositionForGroup(groupIndex);
            notifyItemChanged(packedPosition, payload);
        }
    }

    @Override
    public final void notifyGroupInserted(final int groupIndex) {
        notifyGroupRangeInserted(groupIndex, 1);
    }

    @Override
    public final void notifyGroupRangeInserted(final int startIndex, final int groupCount) {
        Condition.INSTANCE.ensureAtLeast(groupCount, 1, "The group count must be at least 1");

        for (int i = 0; i < groupCount; i++) {
            int groupIndex = startIndex + i;
            int packedPosition = getPackedPositionForGroup(groupIndex);
            notifyItemInserted(packedPosition);
        }
    }

    @Override
    public final void notifyGroupRemoved(final int groupIndex) {
        int packedPosition = getPackedPositionForGroup(groupIndex);
        notifyItemRemoved(packedPosition);
    }

    @Override
    public final void notifyChildChanged(final int groupIndex, final int childIndex) {
        notifyChildChanged(groupIndex, childIndex, null);
    }

    @Override
    public final void notifyChildChanged(final int groupIndex, final int childIndex,
                                         final Object payload) {
        notifyChildRangeChanged(groupIndex, childIndex, 1);
    }

    @Override
    public final void notifyChildRangeChanged(final int groupIndex, final int startIndex,
                                              final int childCount) {
        notifyChildRangeChanged(groupIndex, startIndex, childCount, null);
    }

    @Override
    public final void notifyChildRangeChanged(final int groupIndex, final int startIndex,
                                              final int childCount, final Object payload) {
        Condition.INSTANCE.ensureAtLeast(childCount, 1, "The child count must be at least 1");
        int packedPosition = getPackedPositionForChild(groupIndex, startIndex);
        notifyItemRangeChanged(packedPosition, childCount, payload);
    }

    @Override
    public final void notifyChildInserted(final int groupIndex, final int childIndex) {
        notifyChildRangeInserted(groupIndex, childIndex, 1);
    }

    @Override
    public final void notifyChildRangeInserted(final int groupIndex, final int startIndex,
                                               final int childCount) {
        Condition.INSTANCE.ensureAtLeast(childCount, 1, "The child count must be at least 1");
        int packedPosition = getPackedPositionForChild(groupIndex, startIndex);
        notifyItemRangeInserted(packedPosition, childCount);
    }

    @Override
    public final void notifyChildRemoved(final int groupIndex, final int childIndex) {
        notifyChildRangeRemoved(groupIndex, childIndex, 1);
    }

    @Override
    public final void notifyChildRangeRemoved(final int groupIndex, final int startIndex,
                                              final int childCount) {
        Condition.INSTANCE.ensureAtLeast(childCount, 1, "The child count must be at least 1");
        int packedPosition = getPackedPositionForChild(groupIndex, startIndex);
        notifyItemRangeRemoved(packedPosition, childCount);
    }

    @Override
    public final void onGroupExpanded(final int groupPosition) {

    }

    @Override
    public final void onGroupCollapsed(final int groupPosition) {

    }

    @Deprecated
    @Override
    public final int getPackedPositionType(final int packedPosition) {
        Pair<Integer, Integer> pair = getPackedPositionGroupAndChild(packedPosition);
        int groupIndex = pair.first;
        int childIndex = pair.second;
        return childIndex != -1 ? PACKED_POSITION_TYPE_CHILD :
                groupIndex != -1 ? PACKED_POSITION_TYPE_GROUP : PACKED_POSITION_TYPE_NULL;
    }

    @Deprecated
    @Override
    public final int getPackedPositionGroup(final int packedPosition) {
        Pair<Integer, Integer> pair = getPackedPositionGroupAndChild(packedPosition);
        return pair.first;
    }

    @Deprecated
    @Override
    public final int getPackedPositionChild(final int packedPosition) {
        Pair<Integer, Integer> pair = getPackedPositionGroupAndChild(packedPosition);
        int groupIndex = pair.first;
        int childIndex = pair.second;
        return groupIndex != -1 && childIndex != -1 ? childIndex : -1;
    }

    @Deprecated
    @Override
    public final int getPackedPositionForGroup(final int groupIndex) {
        Condition.INSTANCE.ensureAtLeast(groupIndex, 0, "The group index must be at least 0");
        int packedPosition = 0;

        for (int i = 0; i < groupIndex; i++) {
            packedPosition++;

            if (i < getGroupCount() && isGroupExpanded(i)) {
                packedPosition += getChildCount(i);
            }
        }

        return packedPosition;
    }

    @Deprecated
    @Override
    public final int getPackedPositionForChild(final int groupIndex, final int childIndex) {
        Condition.INSTANCE.ensureAtLeast(childIndex, 0, "The child index must be at least 0",
                IndexOutOfBoundsException.class);
        int packedPosition = getPackedPositionForGroup(groupIndex);
        return packedPosition + childIndex + 1;
    }

    @Override
    public final long getCombinedChildId(final long groupId, final long childId) {
        return 0x8000000000000000L | ((groupId & 0x7FFFFFFF) << 32) | (childId & 0xFFFFFFFF);
    }

    @Override
    public final long getCombinedGroupId(final long groupId) {
        return (groupId & 0x7FFFFFFF) << 32;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public final boolean isEmpty() {
        return getGroupCount() == 0;
    }

    @Override
    public final int getItemCount() {
        int count = 0;

        for (int i = 0; i < getGroupCount(); i++) {
            count++;

            if (isGroupExpanded(i)) {
                count += getChildCount(i);
            }
        }

        return count;
    }

    @Override
    public final long getItemId(final int index) {
        Pair<Integer, Integer> pair = getPackedPositionGroupAndChild(index);
        int groupIndex = pair.first;
        int childIndex = pair.second;
        Object item = childIndex == -1 ? getGroup(groupIndex) : getChild(groupIndex, childIndex);
        return System.identityHashCode(item);
    }

    @Override
    public final LogLevel getLogLevel() {
        return getLogger().getLogLevel();
    }

    @Override
    public final void setLogLevel(@NonNull final LogLevel logLevel) {
        getLogger().setLogLevel(logLevel);
    }

    @Override
    public final Bundle getParameters() {
        return groupAdapter.getParameters();
    }

    @Override
    public final void setParameters(@Nullable final Bundle parameters) {
        groupAdapter.setParameters(parameters);
        String message = "Set parameters to \"" + parameters + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void addAdapterListener(
            @NonNull final ExpandableListAdapterListener<GroupType, ChildType> listener) {
        Condition.INSTANCE.ensureNotNull(listener, "The listener may not be null");
        adapterListeners.add(listener);
        String message = "Added adapter listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void removeAdapterListener(
            @NonNull final ExpandableListAdapterListener<GroupType, ChildType> listener) {
        Condition.INSTANCE.ensureNotNull(listener, "The listener may not be null");
        adapterListeners.remove(listener);
        String message = "Removed adapter listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void addExpansionListener(
            @NonNull final ExpansionListener<GroupType, ChildType> listener) {
        Condition.INSTANCE.ensureNotNull(listener, "The listener may not be null");
        expansionListeners.add(listener);
        String message = "Added expansion listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void removeExpansionListener(
            @NonNull final ExpansionListener<GroupType, ChildType> listener) {
        Condition.INSTANCE.ensureNotNull(listener, "The listener may not be null");
        expansionListeners.remove(listener);
        String message = "Removed expansion listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void addItemClickListener(
            @NonNull final ExpandableListAdapterItemClickListener<GroupType, ChildType> listener) {
        Condition.INSTANCE.ensureNotNull(listener, "The listener may not be null");
        itemClickListeners.add(listener);
    }

    @Override
    public final void removeItemClickListener(
            @NonNull final ExpandableListAdapterItemClickListener<GroupType, ChildType> listener) {
        Condition.INSTANCE.ensureNotNull(listener, "The listener may not be null");
        itemClickListeners.remove(listener);
    }

    @Override
    public final void addItemLongClickListener(
            @NonNull final ExpandableListAdapterItemLongClickListener<GroupType, ChildType> listener) {
        Condition.INSTANCE.ensureNotNull(listener, "The listener may not be null");
        itemLongClickListeners.add(listener);
    }

    @Override
    public final void removeItemLongClickListener(
            @NonNull final ExpandableListAdapterItemLongClickListener<GroupType, ChildType> listener) {
        Condition.INSTANCE.ensureNotNull(listener, "The listener may not be null");
        itemLongClickListeners.remove(listener);
    }

    @Override
    public final boolean areDuplicateGroupsAllowed() {
        return groupAdapter.areDuplicatesAllowed();
    }

    @Override
    public final void allowDuplicateGroups(final boolean allowDuplicateGroups) {
        groupAdapter.allowDuplicates(allowDuplicateGroups);
        String message =
                "Duplicate groups are now " + (allowDuplicateGroups ? "allowed" : "disallowed");
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final boolean isNotifiedOnChange() {
        return notifyOnChange;
    }

    @Override
    public final void notifyOnChange(final boolean notifyOnChange) {
        this.notifyOnChange = notifyOnChange;
        String message = "Changes of the adapter's underlying data are now " +
                (notifyOnChange ? "" : "not ") + "automatically notified";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final int addGroup(@NonNull final GroupType group) {
        int index = getGroupCount();
        boolean added = addGroup(index, group);
        return added ? index : -1;
    }

    @Override
    public final boolean addGroup(final int index, @NonNull final GroupType group) {
        boolean added = groupAdapter.addItem(index, createGroup(index, group));

        if (added) {
            notifyOnGroupAdded(group, index);
            notifyObserversOnGroupInserted(index);
            String message = "Group \"" + group + "\" added at index " + index;
            getLogger().logInfo(getClass(), message);
            return true;
        } else {
            String message = "Group \"" + group + "\" at index " + index +
                    " not added, because adapter already contains group";
            getLogger().logDebug(getClass(), message);
            return false;
        }
    }

    @Override
    public final boolean addAllGroups(@NonNull final Collection<? extends GroupType> groups) {
        return addAllGroups(getGroupCount(), groups);
    }

    @Override
    public final boolean addAllGroups(final int index,
                                      @NonNull final Collection<? extends GroupType> groups) {
        Condition.INSTANCE.ensureNotNull(groups, "The collection may not be null");
        boolean result = true;
        int currentIndex = index;

        for (GroupType group : groups) {
            boolean added = addGroup(currentIndex, group);
            result &= added;

            if (added) {
                currentIndex++;
            }
        }

        return result;
    }

    @SafeVarargs
    @Override
    public final boolean addAllGroups(@NonNull final GroupType... groups) {
        return addAllGroups(getGroupCount(), groups);
    }

    @SafeVarargs
    @Override
    public final boolean addAllGroups(final int index, @NonNull final GroupType... groups) {
        Condition.INSTANCE.ensureNotNull(groups, "The array may not be null");
        return addAllGroups(index, Arrays.asList(groups));
    }

    @Override
    public final GroupType replaceGroup(final int index, @NonNull final GroupType group) {
        Condition.INSTANCE.ensureNotNull(group, "The group may not be null");
        GroupType replacedGroup =
                groupAdapter.replaceItem(index, new Group<>(group, createChildAdapter())).getData();
        notifyOnGroupRemoved(replacedGroup, index);
        notifyOnGroupAdded(group, index);
        notifyObserversOnGroupChanged(index);
        String message =
                "Replaced group \"" + replacedGroup + "\" at index " + index + " with item \"" +
                        group + "\"";
        getLogger().logInfo(getClass(), message);
        return replacedGroup;
    }

    @Override
    public final GroupType removeGroup(final int index) {
        GroupType removedGroup = groupAdapter.removeItem(index).getData();
        notifyOnGroupRemoved(removedGroup, index);
        notifyObserversOnGroupRemoved(index);
        String message = "Removed group \"" + removedGroup + "\" from index " + index;
        getLogger().logInfo(getClass(), message);
        return removedGroup;
    }

    @Override
    public final boolean removeGroup(@NonNull final GroupType group) {
        int index = indexOfGroup(group);

        if (index != -1) {
            groupAdapter.removeItem(index);
            notifyOnGroupRemoved(group, index);
            notifyObserversOnGroupRemoved(index);
            String message = "Removed group \"" + group + "\" from index " + index;
            getLogger().logInfo(getClass(), message);
            return true;
        }

        String message =
                "Group \"" + group + "\" not removed, because adapter does not contain group";
        getLogger().logDebug(getClass(), message);
        return false;
    }

    @Override
    public final boolean removeAllGroups(@NonNull final Collection<? extends GroupType> groups) {
        Condition.INSTANCE.ensureNotNull(groups, "The collection may not be null");
        int numberOfRemovedGroups = 0;

        for (int i = getGroupCount() - 1; i >= 0; i--) {
            if (groups.contains(getGroup(i))) {
                removeGroup(i);
                numberOfRemovedGroups++;
            }
        }

        return numberOfRemovedGroups == groups.size();
    }

    @SafeVarargs
    @Override
    public final boolean removeAllGroups(@NonNull final GroupType... groups) {
        return removeAllGroups(Arrays.asList(groups));
    }

    @Override
    public final void retainAllGroups(@NonNull final Collection<? extends GroupType> groups) {
        Condition.INSTANCE.ensureNotNull(groups, "The collection may not be null");

        for (int i = getGroupCount() - 1; i >= 0; i--) {
            if (!groups.contains(getGroup(i))) {
                removeGroup(i);
            }
        }
    }

    @SafeVarargs
    @Override
    public final void retainAllGroups(@NonNull final GroupType... groups) {
        Condition.INSTANCE.ensureNotNull(groups, "The array may not be null");
        retainAllGroups(Arrays.asList(groups));
    }

    @Override
    public final void clearGroups() {
        for (int i = getGroupCount() - 1; i >= 0; i--) {
            removeGroup(i);
        }
    }

    @Override
    public final Iterator<GroupType> groupIterator() {
        return new GroupIterator<>(groupAdapter.iterator());
    }

    @Override
    public final ListIterator<GroupType> groupListIterator() {
        return new GroupListIterator<>(groupAdapter.listIterator(), context);
    }

    @Override
    public final ListIterator<GroupType> groupListIterator(final int index) {
        return new GroupListIterator<>(groupAdapter.listIterator(index), context);
    }

    @Override
    public final List<GroupType> subListGroups(final int start, final int end) {
        return new UnmodifiableGroupList<>(getGroupAdapter().subList(start, end));
    }

    @Override
    public final Object[] groupsToArray() {
        return getAllGroups().toArray();
    }

    @Override
    public final <T> T[] groupsToArray(@NonNull final T[] array) {
        return getAllGroups().toArray(array);
    }

    @Override
    public final int indexOfGroup(@NonNull final GroupType group) {
        Condition.INSTANCE.ensureNotNull(group, "The group may not be null");

        for (int i = 0; i < getGroupCount(); i++) {
            if (getGroup(i).equals(group)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final boolean containsGroup(@NonNull final GroupType group) {
        return indexOfGroup(group) != -1;
    }

    @Override
    public final boolean containsAllGroups(@NonNull final Collection<? extends GroupType> groups) {
        Condition.INSTANCE.ensureNotNull(groups, "The collection may not be null");
        boolean result = true;

        for (GroupType group : groups) {
            result &= containsGroup(group);
        }

        return result;
    }

    @SafeVarargs
    @Override
    public final boolean containsAllGroups(@NonNull final GroupType... groups) {
        return containsAllGroups(Arrays.asList(groups));
    }

    @Override
    public final GroupType getGroup(final int groupIndex) {
        return groupAdapter.getItem(groupIndex).getData();
    }

    @Override
    public final List<GroupType> getAllGroups() {
        return new UnmodifiableGroupList<>(groupAdapter.getAllItems());
    }

    @Override
    public final boolean isGroupEmpty(final int groupIndex) {
        return groupAdapter.getItem(groupIndex).getChildAdapter().isEmpty();
    }

    @Override
    public final boolean isGroupEmpty(@NonNull final GroupType group) {
        return isGroupEmpty(indexOfGroupOrThrowException(group));
    }

    @Override
    public final boolean areDuplicateChildrenAllowed() {
        return allowDuplicateChildren;
    }

    @Override
    public final void allowDuplicateChildren(final boolean allowDuplicateChildren) {
        this.allowDuplicateChildren = true;
        Iterator<Group<GroupType, ChildType>> iterator = groupAdapter.iterator();

        while (iterator.hasNext()) {
            Group<GroupType, ChildType> group = iterator.next();
            MultipleChoiceListAdapter<ChildType> childAdapter = group.getChildAdapter();
            childAdapter.allowDuplicates(allowDuplicateChildren);
        }

        String message =
                "Duplicate children are now " + (allowDuplicateChildren ? "allowed" : "disallowed");
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final boolean areDuplicateChildrenAllowed(final int groupIndex) {
        return groupAdapter.getItem(groupIndex).getChildAdapter().areDuplicatesAllowed();
    }

    @Override
    public final void allowDuplicateChildren(final int groupIndex,
                                             final boolean allowDuplicateChildren) {
        Group<GroupType, ChildType> group = groupAdapter.getItem(groupIndex);
        group.getChildAdapter().allowDuplicates(allowDuplicateChildren);
        String message = "Duplicate children are now " +
                (allowDuplicateChildren ? "allowed" : "disallowed") + " for group \"" +
                group.getData() + "\" at index " + groupIndex;
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final boolean areDuplicateChildrenAllowed(@NonNull final GroupType group) {
        return areDuplicateChildrenAllowed(indexOfGroupOrThrowException(group));
    }

    @Override
    public final void allowDuplicateChildren(@NonNull final GroupType group,
                                             final boolean allowDuplicateChildren) {
        allowDuplicateChildren(indexOfGroupOrThrowException(group), allowDuplicateChildren);
    }

    @Override
    public final int addChild(final int groupIndex, @NonNull final ChildType child) {
        int index = getChildCount(groupIndex);
        boolean added = addChild(groupIndex, getChildCount(groupIndex), child);
        return added ? index : -1;
    }

    @Override
    public final int addChild(@NonNull final GroupType group, @NonNull final ChildType child) {
        int groupIndex = indexOfGroupOrThrowException(group);
        int childIndex = getChildCount(groupIndex);
        boolean added = addChild(groupIndex, childIndex, child);
        return added ? childIndex : -1;
    }

    @Override
    public final boolean addChild(final int groupIndex, final int index,
                                  @NonNull final ChildType child) {
        Group<GroupType, ChildType> group = groupAdapter.getItem(groupIndex);

        if (areDuplicateChildrenAllowed() || !containsChild(child)) {
            boolean added = group.getChildAdapter().addItem(index, child);

            if (added) {
                notifyOnChildAdded(child, index, group.getData(), groupIndex);
                notifyObserversOnChildInserted(groupIndex, index);
                String message =
                        "Child \"" + child + "\" added at index " + index + " to group \"" +
                                group.getData() + "\" at index " + groupIndex;
                getLogger().logInfo(getClass(), message);
                return true;
            } else {
                String message =
                        "Child \"" + child + "\" at index " + index + " not added to group \"" +
                                group.getData() + "\" at index " + groupIndex +
                                ", because group already contains child";
                getLogger().logDebug(getClass(), message);
                return false;
            }
        } else {
            String message =
                    "Child \"" + child + "\" at index " + index + " not added to group \"" +
                            group.getData() + "\" at index " + groupIndex +
                            ", because adapter already contains child";
            getLogger().logDebug(getClass(), message);
            return false;
        }
    }

    @Override
    public final boolean addChild(@NonNull final GroupType group, final int index,
                                  @NonNull final ChildType child) {
        return addChild(indexOfGroupOrThrowException(group), index, child);
    }

    @Override
    public final boolean addAllChildren(final int groupIndex,
                                        @NonNull final Collection<? extends ChildType> children) {
        return addAllChildren(groupIndex, getChildCount(groupIndex), children);
    }

    @Override
    public final boolean addAllChildren(@NonNull final GroupType group,
                                        @NonNull final Collection<? extends ChildType> children) {
        return addAllChildren(indexOfGroupOrThrowException(group), children);
    }

    @Override
    public final boolean addAllChildren(final int groupIndex, final int index,
                                        @NonNull final Collection<? extends ChildType> children) {
        Condition.INSTANCE.ensureNotNull(children, "The collection may not be null");
        boolean result = true;
        int currentIndex = index;

        for (ChildType child : children) {
            boolean added = addChild(groupIndex, currentIndex, child);
            result &= added;

            if (added) {
                currentIndex++;
            }
        }

        return result;
    }

    @Override
    public final boolean addAllChildren(@NonNull final GroupType group, final int childIndex,
                                        @NonNull final Collection<? extends ChildType> children) {
        return addAllChildren(indexOfGroupOrThrowException(group), childIndex, children);
    }

    @SafeVarargs
    @Override
    public final boolean addAllChildren(final int groupIndex,
                                        @NonNull final ChildType... children) {
        Condition.INSTANCE.ensureNotNull(children, "The array may not be null");
        return addAllChildren(groupIndex, Arrays.asList(children));
    }

    @SafeVarargs
    @Override
    public final boolean addAllChildren(@NonNull final GroupType group,
                                        @NonNull final ChildType... children) {
        Condition.INSTANCE.ensureNotNull(children, "The array may not be null");
        return addAllChildren(group, Arrays.asList(children));
    }

    @SafeVarargs
    @Override
    public final boolean addAllChildren(final int groupIndex, final int index,
                                        @NonNull final ChildType... children) {
        Condition.INSTANCE.ensureNotNull(children, "The array may not be null");
        return addAllChildren(groupIndex, index, Arrays.asList(children));
    }

    @SafeVarargs
    @Override
    public final boolean addAllChildren(@NonNull final GroupType group, final int index,
                                        @NonNull final ChildType... children) {
        Condition.INSTANCE.ensureNotNull(children, "The array may not be null");
        return addAllChildren(group, index, Arrays.asList(children));
    }

    @Override
    public final ChildType replaceChild(final int groupIndex, final int index,
                                        @NonNull final ChildType child) {
        Condition.INSTANCE.ensureNotNull(child, "The child may not be null");
        Group<GroupType, ChildType> group = groupAdapter.getItem(groupIndex);
        ChildType replacedChild = group.getChildAdapter().replaceItem(index, child);
        notifyOnChildRemoved(replacedChild, index, group.getData(), groupIndex);
        notifyOnChildAdded(replacedChild, index, group.getData(), groupIndex);
        notifyObserversOnChildChanged(groupIndex, index);
        String message =
                "Replaced child \"" + replacedChild + "\" at index " + index + " of group \"" +
                        group.getData() + "\" at index " + groupIndex + " with child \"" + child +
                        "\"";
        getLogger().logInfo(getClass(), message);
        return replacedChild;
    }

    @Override
    public final ChildType replaceChild(@NonNull final GroupType group, final int index,
                                        @NonNull final ChildType child) {
        return replaceChild(indexOfGroupOrThrowException(group), index, child);
    }

    @Override
    public final ChildType removeChild(final int groupIndex, final int index) {
        return removeChild(false, groupIndex, index);
    }

    @Override
    public final ChildType removeChild(final boolean removeEmptyGroup, final int groupIndex,
                                       final int index) {
        Group<GroupType, ChildType> group = groupAdapter.getItem(groupIndex);
        ChildType removedChild = group.getChildAdapter().removeItem(index);
        notifyOnChildRemoved(removedChild, index, group.getData(), groupIndex);
        String message =
                "Removed child \"" + removedChild + "\" from index " + index + " of group \"" +
                        group.getData() + "\" at index " + groupIndex;
        getLogger().logInfo(getClass(), message);

        if (removeEmptyGroup && group.getChildAdapter().isEmpty()) {
            removeGroup(groupIndex);
        }

        notifyObserversOnChildRemoved(groupIndex, index);
        return removedChild;
    }

    @Override
    public final ChildType removeChild(@NonNull final GroupType group, final int index) {
        return removeChild(false, group, index);
    }

    @Override
    public final ChildType removeChild(final boolean removeEmptyGroup,
                                       @NonNull final GroupType group, final int index) {
        return removeChild(removeEmptyGroup, indexOfGroupOrThrowException(group), index);
    }

    @Override
    public final boolean removeChild(final int groupIndex, @NonNull final ChildType child) {
        return removeChild(false, groupIndex, child);
    }

    @Override
    public final boolean removeChild(final boolean removeEmptyGroup, final int groupIndex,
                                     @NonNull final ChildType child) {
        Condition.INSTANCE.ensureNotNull(child, "The child may not be null");
        Group<GroupType, ChildType> group = groupAdapter.getItem(groupIndex);
        int index = group.getChildAdapter().indexOf(child);

        if (index != -1) {
            removeChild(removeEmptyGroup, groupIndex, index);
        }

        return false;
    }

    @Override
    public final boolean removeChild(@NonNull final GroupType group,
                                     @NonNull final ChildType child) {
        return removeChild(false, group, child);
    }

    @Override
    public final boolean removeChild(final boolean removeEmptyGroup, @NonNull final GroupType group,
                                     @NonNull final ChildType child) {
        return removeChild(removeEmptyGroup, indexOfGroupOrThrowException(group), child);
    }

    @Override
    public final boolean removeAllChildren(
            @NonNull final Collection<? extends ChildType> children) {
        return removeAllChildren(false, children);
    }

    @Override
    public final boolean removeAllChildren(final boolean removeEmptyGroups,
                                           @NonNull final Collection<? extends ChildType> children) {
        boolean result = true;

        for (int i = groupAdapter.getCount() - 1; i >= 0; i--) {
            result &= removeAllChildren(removeEmptyGroups, i, children);
        }

        return result;
    }

    @Override
    public final boolean removeAllChildren(final int groupIndex,
                                           @NonNull final Collection<? extends ChildType> children) {
        return removeAllChildren(false, groupIndex, children);
    }

    @Override
    public final boolean removeAllChildren(final boolean removeEmptyGroup, final int groupIndex,
                                           @NonNull final Collection<? extends ChildType> children) {
        Condition.INSTANCE.ensureNotNull(children, "The collection may not be null");
        boolean result = true;
        Group<GroupType, ChildType> group = groupAdapter.getItem(groupIndex);

        for (int i = group.getChildAdapter().getCount() - 1; i >= 0; i--) {
            if (children.contains(getChild(groupIndex, i))) {
                result &= removeChild(removeEmptyGroup, groupIndex, i) != null;
            }
        }

        return result;
    }

    @Override
    public final boolean removeAllChildren(@NonNull final GroupType group,
                                           @NonNull final Collection<? extends ChildType> children) {
        return removeAllChildren(false, group, children);
    }

    @Override
    public final boolean removeAllChildren(final boolean removeEmptyGroup,
                                           @NonNull final GroupType group,
                                           @NonNull final Collection<? extends ChildType> children) {
        return removeAllChildren(removeEmptyGroup, indexOfGroupOrThrowException(group), children);
    }

    @SafeVarargs
    @Override
    public final boolean removeAllChildren(@NonNull final ChildType... children) {
        return removeAllChildren(false, children);
    }

    @SafeVarargs
    @Override
    public final boolean removeAllChildren(final boolean removeEmptyGroups,
                                           @NonNull final ChildType... children) {
        Condition.INSTANCE.ensureNotNull(children, "The array may not be null");
        return removeAllChildren(removeEmptyGroups, Arrays.asList(children));
    }

    @SafeVarargs
    @Override
    public final boolean removeAllChildren(final int groupIndex,
                                           @NonNull final ChildType... children) {
        return removeAllChildren(false, groupIndex, children);
    }

    @SafeVarargs
    @Override
    public final boolean removeAllChildren(final boolean removeEmptyGroup, final int groupIndex,
                                           @NonNull final ChildType... children) {
        Condition.INSTANCE.ensureNotNull(children, "The array may not be null");
        return removeAllChildren(removeEmptyGroup, groupIndex, Arrays.asList(children));
    }

    @SafeVarargs
    @Override
    public final boolean removeAllChildren(@NonNull final GroupType group,
                                           @NonNull final ChildType... children) {
        return removeAllChildren(false, group, children);
    }

    @SafeVarargs
    @Override
    public final boolean removeAllChildren(final boolean removeEmptyGroup,
                                           @NonNull final GroupType group,
                                           @NonNull final ChildType... children) {
        Condition.INSTANCE.ensureNotNull(children, "The array may not be null");
        return removeAllChildren(removeEmptyGroup, group, Arrays.asList(children));
    }

    @Override
    public final void retainAllChildren(@NonNull final Collection<? extends ChildType> children) {
        retainAllChildren(false, children);
    }

    @Override
    public final void retainAllChildren(final boolean removeEmptyGroups,
                                        @NonNull final Collection<? extends ChildType> children) {
        for (int i = groupAdapter.getCount() - 1; i >= 0; i--) {
            retainAllChildren(removeEmptyGroups, i, children);
        }
    }

    @Override
    public final void retainAllChildren(final int groupIndex,
                                        @NonNull final Collection<? extends ChildType> children) {
        retainAllChildren(false, groupIndex, children);
    }

    @Override
    public final void retainAllChildren(final boolean removeEmptyGroup, final int groupIndex,
                                        @NonNull final Collection<? extends ChildType> children) {
        Condition.INSTANCE.ensureNotNull(children, "The collection may not be null");
        Group<GroupType, ChildType> group = groupAdapter.getItem(groupIndex);

        for (int i = group.getChildAdapter().getCount() - 1; i >= 0; i--) {
            if (!children.contains(group.getChildAdapter().getItem(i))) {
                removeChild(removeEmptyGroup, groupIndex, i);
            }
        }
    }

    @Override
    public final void retainAllChildren(@NonNull final GroupType group,
                                        @NonNull final Collection<? extends ChildType> children) {
        retainAllChildren(false, group, children);
    }

    @Override
    public final void retainAllChildren(final boolean removeEmptyGroup,
                                        @NonNull final GroupType group,
                                        @NonNull final Collection<? extends ChildType> children) {
        retainAllChildren(removeEmptyGroup, indexOfGroupOrThrowException(group), children);
    }

    @SafeVarargs
    @Override
    public final void retainAllChildren(@NonNull final ChildType... children) {
        retainAllChildren(false, children);
    }

    @SafeVarargs
    @Override
    public final void retainAllChildren(final boolean removeEmptyGroups,
                                        @NonNull final ChildType... children) {
        Condition.INSTANCE.ensureNotNull(children, "The array may not be null");
        retainAllChildren(removeEmptyGroups, Arrays.asList(children));
    }

    @SafeVarargs
    @Override
    public final void retainAllChildren(final int groupIndex,
                                        @NonNull final ChildType... children) {
        retainAllChildren(false, groupIndex, children);
    }

    @SafeVarargs
    @Override
    public final void retainAllChildren(final boolean removeEmptyGroup, final int groupIndex,
                                        @NonNull final ChildType... children) {
        Condition.INSTANCE.ensureNotNull(children, "The array may not be null");
        retainAllChildren(removeEmptyGroup, groupIndex, Arrays.asList(children));
    }

    @SafeVarargs
    @Override
    public final void retainAllChildren(@NonNull final GroupType group,
                                        @NonNull final ChildType... children) {
        retainAllChildren(false, group, children);
    }

    @SafeVarargs
    @Override
    public final void retainAllChildren(final boolean removeEmptyGroup,
                                        @NonNull final GroupType group,
                                        @NonNull final ChildType... children) {
        Condition.INSTANCE.ensureNotNull(children, "The array may not be null");
        retainAllChildren(removeEmptyGroup, group, Arrays.asList(children));
    }

    @Override
    public final void clearChildren() {
        clearChildren(false);
    }

    @Override
    public final void clearChildren(final boolean removeEmptyGroups) {
        for (int i = groupAdapter.getCount() - 1; i >= 0; i--) {
            clearChildren(removeEmptyGroups, i);
        }
    }

    @Override
    public final void clearChildren(final int groupIndex) {
        clearChildren(false);
    }

    @Override
    public final void clearChildren(final boolean removeEmptyGroup, final int groupIndex) {
        Group<GroupType, ChildType> group = groupAdapter.getItem(groupIndex);

        for (int i = group.getChildAdapter().getCount() - 1; i >= 0; i--) {
            removeChild(removeEmptyGroup, groupIndex, i);
        }
    }

    @Override
    public final void clearChildren(@NonNull final GroupType group) {
        clearChildren(false, group);
    }

    @Override
    public final void clearChildren(final boolean removeEmptyGroup,
                                    @NonNull final GroupType group) {
        clearChildren(removeEmptyGroup, indexOfGroupOrThrowException(group));
    }

    @Override
    public final Iterator<ChildType> childIterator() {
        return getAllChildren().iterator();
    }

    @Override
    public final Iterator<ChildType> childIterator(final int groupIndex) {
        return groupAdapter.getItem(groupIndex).getChildAdapter().iterator();
    }

    @Override
    public final Iterator<ChildType> childIterator(@NonNull final GroupType group) {
        return childIterator(indexOfGroupOrThrowException(group));
    }

    @Override
    public final ListIterator<ChildType> childListIterator(final int groupIndex) {
        return groupAdapter.getItem(groupIndex).getChildAdapter().listIterator();
    }

    @Override
    public final ListIterator<ChildType> childListIterator(@NonNull final GroupType group) {
        return childListIterator(indexOfGroupOrThrowException(group));
    }

    @Override
    public final ListIterator<ChildType> childListIterator(final int groupIndex,
                                                           final int childIndex) {
        return groupAdapter.getItem(groupIndex).getChildAdapter().listIterator(childIndex);
    }

    @Override
    public final ListIterator<ChildType> childListIterator(@NonNull final GroupType group,
                                                           final int childIndex) {
        return childListIterator(indexOfGroupOrThrowException(group), childIndex);
    }

    @Override
    public final List<ChildType> subListChildren(final int groupIndex, final int start,
                                                 final int end) {
        return groupAdapter.getItem(groupIndex).getChildAdapter().subList(start, end);
    }

    @Override
    public final List<ChildType> subListChildren(@NonNull final GroupType group, final int start,
                                                 final int end) {
        return subListChildren(indexOfGroupOrThrowException(group), start, end);
    }

    @Override
    public final Object[] childrenToArray() {
        return getAllChildren().toArray();
    }

    @Override
    public final Object[] childrenToArray(final int groupIndex) {
        return groupAdapter.getItem(groupIndex).getChildAdapter().toArray();
    }

    @Override
    public final Object[] childrenToArray(@NonNull final GroupType group) {
        return childrenToArray(indexOfGroupOrThrowException(group));
    }

    @Override
    public final <T> T[] childrenToArray(@NonNull final T[] array) {
        return getAllChildren().toArray(array);
    }

    @Override
    public final <T> T[] childrenToArray(final int groupIndex, @NonNull final T[] array) {
        return groupAdapter.getItem(groupIndex).getChildAdapter().toArray(array);
    }

    @Override
    public final <T> T[] childrenToArray(@NonNull final GroupType group, @NonNull final T[] array) {
        return childrenToArray(indexOfGroupOrThrowException(group), array);
    }

    @Override
    public final int indexOfChild(@NonNull final ChildType child) {
        Condition.INSTANCE.ensureNotNull(child, "The child may not be null");

        for (int i = 0; i < groupAdapter.getCount(); i++) {
            if (groupAdapter.getItem(i).getChildAdapter().containsItem(child)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final int indexOfChild(final int groupIndex, @NonNull final ChildType child) {
        Condition.INSTANCE.ensureNotNull(child, "The child may not be null");
        return groupAdapter.getItem(groupIndex).getChildAdapter().indexOf(child);
    }

    @Override
    public final int indexOfChild(@NonNull final GroupType group, @NonNull final ChildType child) {
        return indexOfChild(indexOfGroupOrThrowException(group), child);
    }

    @Override
    public final int lastIndexOfChild(@NonNull final ChildType child) {
        Condition.INSTANCE.ensureNotNull(child, "The child may not be null");

        for (int i = groupAdapter.getCount() - 1; i >= 0; i--) {
            if (groupAdapter.getItem(i).getChildAdapter().containsItem(child)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final int lastIndexOfChild(final int groupIndex, @NonNull final ChildType child) {
        Condition.INSTANCE.ensureNotNull(child, "The child may not be null");
        return groupAdapter.getItem(groupIndex).getChildAdapter().lastIndexOf(child);
    }

    @Override
    public final int lastIndexOfChild(@NonNull final GroupType group,
                                      @NonNull final ChildType child) {
        return lastIndexOfChild(indexOfGroupOrThrowException(group), child);
    }

    @Override
    public final boolean containsChild(@NonNull final ChildType child) {
        Condition.INSTANCE.ensureNotNull(child, "The child may not be null");

        for (Group<GroupType, ChildType> group : groupAdapter.getAllItems()) {
            if (group.getChildAdapter().containsItem(child)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public final boolean containsChild(final int groupIndex, @NonNull final ChildType child) {
        Condition.INSTANCE.ensureNotNull(child, "The child may not be null");
        return groupAdapter.getItem(groupIndex).getChildAdapter().containsItem(child);
    }

    @Override
    public final boolean containsChild(@NonNull final GroupType group,
                                       @NonNull final ChildType child) {
        return containsChild(indexOfGroupOrThrowException(group), child);
    }

    @Override
    public final boolean containsAllChildren(
            @NonNull final Collection<? extends ChildType> children) {
        return getAllChildren().containsAll(children);
    }

    @Override
    public final boolean containsAllChildren(final int groupIndex,
                                             @NonNull final Collection<? extends ChildType> children) {
        Condition.INSTANCE.ensureNotNull(children, "The collection may not be null");
        return groupAdapter.getItem(groupIndex).getChildAdapter().containsAllItems(children);
    }

    @Override
    public final boolean containsAllChildren(@NonNull final GroupType group,
                                             @NonNull final Collection<? extends ChildType> children) {
        return containsAllChildren(indexOfGroupOrThrowException(group), children);
    }

    @SafeVarargs
    @Override
    public final boolean containsAllChildren(@NonNull final ChildType... children) {
        Condition.INSTANCE.ensureNotNull(children, "The array may not be null");
        return containsAllChildren(Arrays.asList(children));
    }

    @Override
    public final int getChildCount() {
        return getAllChildren().size();
    }

    @Override
    public final int getChildCount(final int groupIndex) {
        return groupAdapter.getItem(groupIndex).getChildAdapter().getCount();
    }

    @Override
    public final int getChildCount(@NonNull final GroupType group) {
        return getChildCount(indexOfGroupOrThrowException(group));
    }

    @Override
    public final ChildType getChild(@NonNull final GroupType group, final int childIndex) {
        return getChild(indexOfGroupOrThrowException(group), childIndex);
    }

    @Override
    public final ChildType getChild(final int groupIndex, final int childIndex) {
        return groupAdapter.getItem(groupIndex).getChildAdapter().getItem(childIndex);
    }

    @Override
    public final List<ChildType> getAllChildren() {
        List<ChildType> result = new ArrayList<>();

        for (Group<GroupType, ChildType> group : groupAdapter.getAllItems()) {
            result.addAll(group.getChildAdapter().getAllItems());
        }

        return new UnmodifiableList<>(result);
    }

    @Override
    public final List<ChildType> getAllChildren(final int groupIndex) {
        return groupAdapter.getItem(groupIndex).getChildAdapter().getAllItems();
    }

    @Override
    public final List<ChildType> getAllChildren(@NonNull final GroupType group) {
        return getAllChildren(indexOfGroupOrThrowException(group));
    }

    @SafeVarargs
    @Override
    public final boolean containsAllChildren(final int groupIndex,
                                             @NonNull final ChildType... children) {
        Condition.INSTANCE.ensureNotNull(children, "The children may not be null");
        return groupAdapter.getItem(groupIndex).getChildAdapter().containsAllItems(children);
    }

    @SafeVarargs
    @Override
    public final boolean containsAllChildren(@NonNull final GroupType group,
                                             @NonNull final ChildType... children) {
        return containsAllChildren(indexOfGroupOrThrowException(group), children);
    }

    @Override
    public final boolean isGroupExpanded(final int index) {
        return getGroupAdapter().getItem(index).isExpanded();
    }

    @Override
    public final boolean isGroupExpanded(@NonNull final GroupType group) {
        return isGroupExpanded(indexOfGroup(group));
    }

    @Override
    public final GroupType getFirstExpandedGroup() {
        int index = getFirstExpandedGroupIndex();

        if (index != -1) {
            return getGroup(index);
        }

        return null;
    }

    @Override
    public final int getFirstExpandedGroupIndex() {
        for (int i = 0; i < getGroupCount(); i++) {
            if (isGroupExpanded(i)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final GroupType getLastExpandedGroup() {
        int index = getLastExpandedGroupIndex();

        if (index != -1) {
            return getGroup(index);
        }

        return null;
    }

    @Override
    public final int getLastExpandedGroupIndex() {
        for (int i = getGroupCount() - 1; i >= 0; i--) {
            if (isGroupExpanded(i)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final GroupType getFirstCollapsedGroup() {
        int index = getFirstCollapsedGroupIndex();

        if (index != -1) {
            return getGroup(index);
        }

        return null;
    }

    @Override
    public final int getFirstCollapsedGroupIndex() {
        for (int i = 0; i < getGroupCount(); i++) {
            if (!isGroupExpanded(i)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final GroupType getLastCollapsedGroup() {
        int index = getLastCollapsedGroupIndex();

        if (index != -1) {
            return getGroup(index);
        }

        return null;
    }

    @Override
    public final int getLastCollapsedGroupIndex() {
        for (int i = getGroupCount() - 1; i >= 0; i--) {
            if (!isGroupExpanded(i)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final List<GroupType> getExpandedGroups() {
        List<GroupType> expandedGroups = new ArrayList<>();

        for (int i = 0; i < getGroupCount(); i++) {
            if (isGroupExpanded(i)) {
                expandedGroups.add(getGroup(i));
            }
        }

        return new UnmodifiableList<>(expandedGroups);
    }

    @Override
    public final List<Integer> getExpandedGroupIndices() {
        List<Integer> expandedGroupIndices = new ArrayList<>();

        for (int i = 0; i < getGroupCount(); i++) {
            if (isGroupExpanded(i)) {
                expandedGroupIndices.add(i);
            }
        }

        return new UnmodifiableList<>(expandedGroupIndices);
    }

    @Override
    public final List<GroupType> getCollapsedGroups() {
        List<GroupType> collapsedGroups = new ArrayList<>();

        for (int i = 0; i < getGroupCount(); i++) {
            if (!isGroupExpanded(i)) {
                collapsedGroups.add(getGroup(i));
            }
        }

        return new UnmodifiableList<>(collapsedGroups);
    }

    @Override
    public final List<Integer> getCollapsedGroupIndices() {
        List<Integer> collapsedGroupIndices = new ArrayList<>();

        for (int i = 0; i < getGroupCount(); i++) {
            if (isGroupExpanded(i)) {
                collapsedGroupIndices.add(i);
            }
        }

        return new UnmodifiableList<>(collapsedGroupIndices);
    }

    @Override
    public final int getExpandedGroupCount() {
        return getExpandedGroups().size();
    }

    @Override
    public final void setGroupExpanded(@NonNull final GroupType group, final boolean expanded) {
        setGroupExpanded(indexOfGroupOrThrowException(group), expanded);
    }

    @Override
    public final void setGroupExpanded(final int index, final boolean expanded) {
        Group<GroupType, ChildType> group = getGroupAdapter().getItem(index);

        if (group.isExpanded() != expanded) {
            group.setExpanded(expanded);

            if (adapterView != null || expandableGridView != null ||
                    expandableRecyclerView != null) {
                if (isNotifiedOnChange()) {
                    if (expanded) {
                        if (adapterView != null) {
                            adapterView.expandGroup(index);
                        } else if (expandableGridView != null) {
                            expandableGridView.expandGroup(index);
                        } else {
                            notifyObserversOnExpansion(index, expanded);
                        }
                    } else {
                        if (adapterView != null) {
                            adapterView.collapseGroup(index);
                        } else if (expandableGridView != null) {
                            expandableGridView.collapseGroup(index);
                        } else {
                            notifyObserversOnExpansion(index, expanded);
                        }
                    }
                } else {
                    taintAdapterView();
                }
            }

            if (expanded) {
                notifyOnGroupExpanded(group.getData(), index);
                getLogger().logInfo(getClass(),
                        "Group \"" + group.getData() + "\" at index " + index + "expanded");
            } else {
                notifyOnGroupCollapsed(group.getData(), index);
                getLogger().logInfo(getClass(),
                        "Group \"" + group.getData() + "\" at index " + index + "collapsed");
            }
        } else {
            String message = "Group \"" + group.getData() + "\" at index " + index + " not " +
                    (expanded ? "expanded" : "collapsed") + ", because group is already " +
                    (expanded ? "expanded" : "collapsed");
            getLogger().logDebug(getClass(), message);
        }
    }

    @Override
    public final boolean triggerGroupExpansion(@NonNull final GroupType group) {
        return triggerGroupExpansion(indexOfGroupOrThrowException(group));
    }

    @Override
    public final boolean triggerGroupExpansion(final int index) {
        if (isGroupExpanded(index)) {
            setGroupExpanded(index, false);
            return false;
        } else {
            setGroupExpanded(index, true);
            return true;
        }
    }

    @Override
    public final void setAllGroupsExpanded(final boolean expanded) {
        for (int i = 0; i < getGroupCount(); i++) {
            setGroupExpanded(i, expanded);
        }
    }

    @Override
    public final void triggerAllGroupExpansions() {
        for (int i = 0; i < getGroupCount(); i++) {
            triggerGroupExpansion(i);
        }
    }

    @Override
    public final boolean isGroupExpansionTriggeredOnClick() {
        return triggerGroupExpansionOnClick;
    }

    @Override
    public final void triggerGroupExpansionOnClick(final boolean triggerGroupExpansionOnClick) {
        this.triggerGroupExpansionOnClick = triggerGroupExpansionOnClick;
        String message = "Groups are now " + (triggerGroupExpansionOnClick ? "" : "not ") +
                "expanded on click";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void attach(@NonNull final ExpandableListView adapterView) {
        Condition.INSTANCE.ensureNotNull(adapterView, "The adapter view may not be null");
        detach();
        this.adapterView = adapterView;
        this.adapterView.setAdapter(this);
        this.adapterView.setOnGroupClickListener(createAdapterViewOnGroupClickListener());
        this.adapterView.setOnChildClickListener(createAdapterViewOnChildClickListener());
        this.adapterView.setOnItemClickListener(createAdapterViewOnItemClickListener());
        this.adapterView.setOnItemLongClickListener(createAdapterViewOnItemLongClickListener());
        syncAdapterView();
        String message = "Attached adapter to view \"" + adapterView + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void attach(@NonNull final ExpandableGridView adapterView) {
        Condition.INSTANCE.ensureNotNull(adapterView, "The adapter view may not be null");
        detach();
        this.expandableGridView = adapterView;
        this.expandableGridView.setAdapter(this);
        this.expandableGridView
                .setOnGroupClickListener(createExpandableGridViewOnGroupClickListener());
        this.expandableGridView
                .setOnChildClickListener(createExpandableGridViewOnChildClickListener());
        this.expandableGridView
                .setOnItemClickListener(createExpandableGridViewOnItemClickListener());
        this.expandableGridView
                .setOnItemLongClickListener(createExpandableGridViewOnItemLongClickListener());
        syncAdapterView();
        String message = "Attached adapter to view \"" + adapterView + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void attach(@NonNull final RecyclerView adapterView) {
        Condition.INSTANCE.ensureNotNull(adapterView, "The adapter view may not be null");
        detach();
        this.expandableRecyclerView = adapterView;
        this.expandableRecyclerView.setAdapter(this);
        syncAdapterView();
        String message = "Attached adapter to view \"" + adapterView + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void detach() {
        if (adapterView != null) {
            if (adapterView.getAdapter() == this) {
                adapterView.setAdapter((android.widget.ExpandableListAdapter) null);
                String message = "Detached adapter from view \"" + adapterView + "\"";
                getLogger().logDebug(getClass(), message);
            } else {
                String message = "Adapter has not been detached, because the adapter of the " +
                        "corresponding view has been changed " + "in the meantime";
                getLogger().logVerbose(getClass(), message);
            }

            adapterView = null;
        } else if (expandableGridView != null) {
            if (expandableGridView.getExpandableListAdapter() == this) {
                expandableGridView.setAdapter((ListAdapter) null);
                String message = "Detached adapter from view \"" + expandableGridView + "\"";
                getLogger().logDebug(getClass(), message);
            } else {
                String message = "Adapter has not been detached, because the adapter of the " +
                        "corresponding view has been changed in the meantime";
                getLogger().logVerbose(getClass(), message);
            }

            expandableGridView = null;
        } else if (expandableRecyclerView != null) {
            if (expandableRecyclerView.getAdapter() == this) {
                expandableRecyclerView.setAdapter(null);
                String message = "Detached adapter from view \"" + expandableRecyclerView + "\"";
                getLogger().logDebug(getClass(), message);
            } else {
                String message = "Adapter has not been detached, because the adapter of the " +
                        " corresponding view has been changed in the meantime";
                getLogger().logVerbose(getClass(), message);
            }

            expandableRecyclerView = null;
        } else {
            String message = "Adapter has not been detached, because it has not " +
                    "been attached to a view yet";
            getLogger().logVerbose(getClass(), message);
        }
    }

    @Override
    public final boolean isAttached() {
        return getAdapterView() != null;
    }

    @Nullable
    @Override
    public final View getAdapterView() {
        if (adapterView != null) {
            return adapterView;
        } else if (expandableGridView != null) {
            return expandableGridView;
        } else if (expandableRecyclerView != null) {
            return expandableRecyclerView;
        }

        return null;
    }

    @Override
    public final boolean isChildSelectable(final int groupIndex, final int childIndex) {
        return isChildEnabled(groupIndex, childIndex);
    }

    @Override
    public final long getChildId(final int groupIndex, final int childIndex) {
        return childIndex;
    }

    @Override
    public final int getChildrenCount(final int groupIndex) {
        return groupAdapter.getItem(groupIndex).getChildAdapter().getCount();
    }

    @Override
    public final int getGroupCount() {
        return groupAdapter.getCount();
    }

    @Override
    public final long getGroupId(final int groupIndex) {
        return groupIndex;
    }

    @Override
    public final int getChildType(final int groupIndex, final int childIndex) {
        return getDecorator().getChildType(getChild(groupIndex, childIndex));
    }

    @Override
    public final int getChildTypeCount() {
        return getDecorator().getChildTypeCount();
    }

    @Override
    public final int getGroupType(final int groupIndex) {
        return getDecorator().getGroupType(getGroup(groupIndex));
    }

    @Override
    public final int getGroupTypeCount() {
        return getDecorator().getGroupTypeCount();
    }

    @Override
    public final int getItemViewType(final int position) {
        Pair<Integer, Integer> pair = getPackedPositionGroupAndChild(position);

        if (pair.second != -1) {
            int viewType = getChildType(pair.first, pair.second);
            return viewType * 256 | FLAG_VIEW_TYPE_CHILD;
        }

        int viewType = getGroupType(pair.first);
        return viewType * 256 | FLAG_VIEW_TYPE_GROUP;
    }

    @Override
    public final View getGroupView(final int groupIndex, final boolean isExpanded,
                                   @Nullable final View convertView,
                                   @Nullable final ViewGroup parent) {
        if (adapterView == null && expandableGridView == null) {
            throw new IllegalStateException(
                    "Adapter must be attached to an ExpandableListView or ExpandableGridView using its attach-method");
        }

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = getDecorator().inflateGroupView(inflater, parent, getGroup(groupIndex));
            String message = "Inflated view to visualize the group at index " + groupIndex;
            getLogger().logVerbose(getClass(), message);
        }

        applyDecoratorOnGroup(getContext(), view, groupIndex);
        return view;
    }

    @Override
    public final View getChildView(final int groupIndex, final int childIndex,
                                   final boolean isLastChild, @Nullable final View convertView,
                                   @Nullable final ViewGroup parent) {
        if (adapterView == null && expandableGridView == null) {
            throw new IllegalStateException(
                    "Adapter must be attached to an ExpandableListView or ExpandableGridView using its attach-method");
        }

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = getDecorator()
                    .inflateChildView(inflater, parent, getChild(groupIndex, childIndex));
            String message = "Inflated view to visualize the child at index " + childIndex +
                    " of the group at index " + groupIndex;
            getLogger().logVerbose(getClass(), message);
        }

        applyDecoratorOnChild(getContext(), view, groupIndex, childIndex);
        return view;
    }

    @Override
    public final ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                               final int viewType) {
        if (expandableRecyclerView == null) {
            throw new IllegalStateException(
                    "Adapter must be attached to a RecyclerView using its attach-method");
        }

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view;

        if ((viewType & FLAG_VIEW_TYPE_GROUP) == FLAG_VIEW_TYPE_GROUP) {
            view = getDecorator().onInflateGroupView(inflater, parent, viewType / 256);
            getLogger().logVerbose(getClass(), "Inflated view to visualize the group item");
        } else {
            view = getDecorator().onInflateChildView(inflater, parent, viewType / 256);
            getLogger().logVerbose(getClass(), "Inflated view to visualize the child item");
        }

        return new ExpandableListItemViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        Pair<Integer, Integer> pair = getPackedPositionGroupAndChild(position);
        int groupIndex = pair.first;
        int childIndex = pair.second;
        ExpandableListItemViewHolder expandableListItemViewHolder =
                (ExpandableListItemViewHolder) viewHolder;
        expandableListItemViewHolder.setGroupIndex(groupIndex);
        expandableListItemViewHolder.setChildIndex(childIndex);

        if (childIndex != -1) {
            applyDecoratorOnChild(getContext(), expandableListItemViewHolder.getParentView(),
                    groupIndex, childIndex);
            expandableListItemViewHolder.getParentView().setOnClickListener(
                    createRecyclerViewOnChildClickListener(groupIndex, childIndex));
            expandableListItemViewHolder.getParentView().setOnLongClickListener(
                    createRecyclerViewOnChildLongClickListener(groupIndex, childIndex));
        } else {
            applyDecoratorOnGroup(getContext(), expandableListItemViewHolder.getParentView(),
                    groupIndex);
            expandableListItemViewHolder.getParentView()
                    .setOnClickListener(createRecyclerViewOnGroupClickListener(groupIndex));
            expandableListItemViewHolder.getParentView()
                    .setOnLongClickListener(createRecyclerViewOnGroupLongClickListener(groupIndex));
        }
    }

    @Override
    public final void onSaveInstanceState(@NonNull final Bundle outState,
                                          @NonNull final String key) {
        Bundle savedState = new Bundle();
        groupAdapter.onSaveInstanceState(savedState, GROUP_ADAPTER_BUNDLE_KEY);

        if (savedState.containsKey(GROUP_ADAPTER_BUNDLE_KEY)) {
            for (int i = 0; i < groupAdapter.getCount(); i++) {
                MultipleChoiceListAdapter<ChildType> childAdapter =
                        groupAdapter.getItem(i).getChildAdapter();

                if (childAdapter != null) {
                    String childAdapterKey = String.format(CHILD_ADAPTER_BUNDLE_KEY, i);
                    childAdapter.onSaveInstanceState(savedState, childAdapterKey);
                }
            }
        }

        if (adapterView != null) {
            AdapterViewUtil
                    .onSaveInstanceState(adapterView, savedState, ADAPTER_VIEW_STATE_BUNDLE_KEY);
        } else if (expandableGridView != null) {
            AdapterViewUtil.onSaveInstanceState(expandableGridView, savedState,
                    ADAPTER_VIEW_STATE_BUNDLE_KEY);
        } else if (expandableRecyclerView != null) {
            RecyclerView.LayoutManager layoutManager = expandableRecyclerView.getLayoutManager();

            if (layoutManager != null) {
                savedState.putParcelable(ADAPTER_VIEW_STATE_BUNDLE_KEY,
                        layoutManager.onSaveInstanceState());
            }
        } else {
            String message = "The state of the adapter view can not be stored, because the " +
                    "adapter has not been attached to a view";
            getLogger().logWarn(getClass(), message);
        }

        savedState.putBoolean(ALLOW_DUPLICATE_CHILDREN_BUNDLE_KEY, areDuplicateChildrenAllowed());
        savedState.putBoolean(TRIGGER_GROUP_EXPANSION_ON_CLICK_BUNDLE_KEY,
                isGroupExpansionTriggeredOnClick());
        savedState.putInt(LOG_LEVEL_BUNDLE_KEY, getLogLevel().getRank());
        onSaveInstanceState(savedState);
        outState.putBundle(key, savedState);
        getLogger().logDebug(getClass(), "Saved instance state");
    }

    @Override
    public final void onRestoreInstanceState(@NonNull final Bundle savedInstanceState,
                                             @NonNull final String key) {
        Bundle savedState = savedInstanceState.getBundle(key);

        if (savedState != null) {
            if (savedState.containsKey(GROUP_ADAPTER_BUNDLE_KEY)) {
                groupAdapter.onRestoreInstanceState(savedState, GROUP_ADAPTER_BUNDLE_KEY);

                for (int i = 0; i < groupAdapter.getCount(); i++) {
                    Group<GroupType, ChildType> group = groupAdapter.getItem(i);
                    String childAdapterKey = String.format(CHILD_ADAPTER_BUNDLE_KEY, i);
                    MultipleChoiceListAdapter<ChildType> childAdapter = createChildAdapter();

                    if (savedState.containsKey(childAdapterKey)) {
                        childAdapter.onRestoreInstanceState(savedState, childAdapterKey);
                    }

                    group.setChildAdapter(childAdapter);
                }
            }

            if (savedState.containsKey(ADAPTER_VIEW_STATE_BUNDLE_KEY)) {
                if (adapterView != null) {
                    AdapterViewUtil.onRestoreInstanceState(adapterView, savedState,
                            ADAPTER_VIEW_STATE_BUNDLE_KEY);
                } else if (expandableGridView != null) {
                    AdapterViewUtil.onRestoreInstanceState(expandableGridView, savedState,
                            ADAPTER_VIEW_STATE_BUNDLE_KEY);
                } else if (expandableRecyclerView != null) {
                    expandableRecyclerView.getLayoutManager().onRestoreInstanceState(
                            savedState.getParcelable(ADAPTER_VIEW_STATE_BUNDLE_KEY));
                }
            }

            allowDuplicateChildren(savedState.getBoolean(ALLOW_DUPLICATE_CHILDREN_BUNDLE_KEY));
            triggerGroupExpansionOnClick(
                    savedState.getBoolean(TRIGGER_GROUP_EXPANSION_ON_CLICK_BUNDLE_KEY));
            setLogLevel(LogLevel.fromRank(savedState.getInt(LOG_LEVEL_BUNDLE_KEY)));
            onRestoreInstanceState(savedState);
            notifyDataSetChanged();
            getLogger().logDebug(getClass(), "Restored instance state");
        } else {
            getLogger().logWarn(getClass(),
                    "Saved instance state does not contain bundle with key \"" + key + "\"");
        }
    }

    @CallSuper
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (allowDuplicateChildren ? 1231 : 1237);
        result = prime * result + (triggerGroupExpansionOnClick ? 1231 : 1237);
        result = prime * result + groupAdapter.hashCode();
        result = prime * result + getLogLevel().getRank();
        return result;
    }

    @CallSuper
    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractExpandableListAdapter<?, ?, ?> other = (AbstractExpandableListAdapter<?, ?, ?>) obj;
        if (allowDuplicateChildren != other.allowDuplicateChildren)
            return false;
        if (triggerGroupExpansionOnClick != other.triggerGroupExpansionOnClick)
            return false;
        if (!groupAdapter.equals(other.groupAdapter))
            return false;
        if (!getLogLevel().equals(other.getLogLevel()))
            return false;
        return true;
    }

    @Override
    public abstract AbstractExpandableListAdapter<GroupType, ChildType, DecoratorType> clone()
            throws CloneNotSupportedException;

}