/*
 * AndroidAdapters Copyright 2014 - 2016 Michael Rapp
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
package de.mrapp.android.adapter.expandablelist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;

import de.mrapp.android.adapter.ExpandableListAdapter;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.datastructure.UnmodifiableList;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.datastructure.group.GroupIterator;
import de.mrapp.android.adapter.datastructure.group.GroupListIterator;
import de.mrapp.android.adapter.datastructure.group.UnmodifiableGroupList;
import de.mrapp.android.adapter.decorator.AbstractExpandableListDecorator;
import de.mrapp.android.adapter.list.selectable.MultipleChoiceListAdapterImplementation;
import de.mrapp.android.adapter.logging.LogLevel;
import de.mrapp.android.adapter.logging.Logger;
import de.mrapp.android.adapter.util.AdapterViewUtil;

import static de.mrapp.android.util.Condition.ensureNotNull;

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
        extends BaseExpandableListAdapter implements ExpandableListAdapter<GroupType, ChildType> {

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
     * The key, which is used to store, whether a group should be expanded, when it is clicked by
     * the user, or not, within a bundle.
     */
    @VisibleForTesting
    protected static final String EXPAND_GROUP_ON_CLICK_BUNDLE_KEY =
            AbstractExpandableListAdapter.class.getSimpleName() + "::ExpandGroupOnClick";

    /**
     * The key, which is used to store the log level, which is used for logging, within a bundle.
     */
    @VisibleForTesting
    protected static final String LOG_LEVEL_BUNDLE_KEY =
            AbstractExpandableListAdapter.class.getSimpleName() + "::LogLevel";

    /**
     * The context, the adapter belongs to.
     */
    private final transient Context context;

    /**
     * The decorator, which allows to customize the appearance of the views, which are used to
     * visualize the group and child items of the adapter.
     */
    private final transient DecoratorType decorator;

    /**
     * The logger, which is used for logging.
     */
    private final transient Logger logger;

    /**
     * A set, which contains the listeners, which should be notified, when an item of the adapter
     * has been clicked by the user.
     */
    private transient Set<ExpandableListAdapterItemClickListener<GroupType, ChildType>>
            itemClickListeners;

    /**
     * A set, which contains the listeners, which should be notified, when an item of the adapter
     * has been long-clicked by the user.
     */
    private transient Set<ExpandableListAdapterItemLongClickListener<GroupType, ChildType>>
            itemLongClickListeners;

    /**
     * A set, which contains the listeners, which should be notified, when the adapter's underlying
     * data has been modified.
     */
    private transient Set<ExpandableListAdapterListener<GroupType, ChildType>> adapterListeners;

    /**
     * A set, which contains the listeners, which should be notified, when a group item has been
     * expanded or collapsed.
     */
    private transient Set<ExpansionListener<GroupType, ChildType>> expansionListeners;

    /**
     * The view, the adapter is currently attached to.
     */
    private transient ExpandableListView adapterView;

    /**
     * True, if duplicate children, regardless from the group they belong to, are allowed, false
     * otherwise.
     */
    private boolean allowDuplicateChildren;

    /**
     * True, if the a group should be expanded, when it is clicked by the user, false otherwise.
     */
    private boolean expandGroupOnClick;

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
                if (isGroupExpandedOnClick()) {
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
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                notifyOnChildClicked(getChild(groupPosition, childPosition), childPosition,
                        getGroup(groupPosition), groupPosition);
                return false;
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
                    int childIndex = ExpandableListView.getPackedPositionChild(id);
                    return notifyOnChildLongClicked(getChild(groupIndex, childIndex), childIndex,
                            getGroup(groupIndex), groupIndex);
                } else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    int groupIndex = ExpandableListView.getPackedPositionGroup(id);
                    return notifyOnGroupLongClicked(getGroup(groupIndex), groupIndex);
                } else {
                    return false;
                }
            }

        };
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
     * Returns the decorator, which allows to customize the appearance of the views, which are used
     * to visualize the group and child items of the adapter.
     *
     * @return The decorator, which allows to customize the appearance of the views, which are used
     * to visualize the group and child items of the adapter, as an instance of the generic type
     * DecoratorType. The decorator may not be null
     */
    protected final DecoratorType getDecorator() {
        return decorator;
    }

    /**
     * Returns the logger, which is used for logging.
     *
     * @return The logger, which is used for logging, as an instance of the class {@link Logger}.
     * The logger may not be null
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
     * Returns a set, which contains the listeners, which should be notified, when an item of the
     * adapter has been clicked by the user.
     *
     * @return A set, which contains the listeners, which should be notified, when an item of the
     * adapter has been clicked by the user, as an instance of the type {@link Set} or an empty set,
     * if no listeners should be notified
     */
    protected final Set<ExpandableListAdapterItemClickListener<GroupType, ChildType>> getItemClickListeners() {
        return itemClickListeners;
    }

    /**
     * Returns a set, which contains the listeners, which should be notified, when an item of the
     * adapter has been long-clicked by the user.
     *
     * @return A set, which contains the listeners, which should be notified, when an item of the
     * adapter has been long-clicked by the user, as an instance of the type {@link Set} or an empty
     * set, if no listeners should be notified
     */
    protected final Set<ExpandableListAdapterItemLongClickListener<GroupType, ChildType>> getItemLongClickListeners() {
        return itemLongClickListeners;
    }

    /**
     * Returns a set, which contains the listeners, which should be notified, when the adapter's
     * underlying data has been modified.
     *
     * @return A set, which contains the listeners, which should be notified, when the adapter's
     * underlying data has been modified, as an instance of the type {@link Set} or an empty set, if
     * no listeners should be notified
     */
    protected final Set<ExpandableListAdapterListener<GroupType, ChildType>> getAdapterListeners() {
        return adapterListeners;
    }

    /**
     * Returns a set, which contains the listeners, which should be notified, when a group item has
     * been expanded or collapsed.
     *
     * @return A set, which contains the listeners which should be notified, when a group item has
     * been expanded or collapsed, as an instance of the type {@link Set} or an empty set, if no
     * listeners should be notified
     */
    protected final Set<ExpansionListener<GroupType, ChildType>> getExpansionListeners() {
        return expansionListeners;
    }

    /**
     * Returns the view, the adapter is currently attached to.
     *
     * @return The view, the adapter is currently attached to, as an instance of the class {@link
     * ExpandableListView}, or null, if the adapter is currently not attached to a view
     */
    protected final ExpandableListView getAdapterView() {
        return adapterView;
    }

    /**
     * Notifies, that the adapter's underlying data has been changed, if automatically notifying
     * such events is currently enabled.
     */
    protected final void notifyOnDataSetChanged() {
        if (isNotifiedOnChange()) {
            notifyDataSetChanged();
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
    protected Group<GroupType, ChildType> createGroup(final int groupIndex,
                                                      @NonNull final GroupType group) {
        return new Group<>(group, createChildAdapter());
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
     *         The log level, which should be used for logging, as a value of the enum {@link
     *         LogLevel}. The log level may not be null
     * @param groupAdapter
     *         The adapter, which should manage the adapter's group items, as an instance of the
     *         type {@link MultipleChoiceListAdapter}. The adapter may not be null
     * @param allowDuplicateChildren
     *         True, if duplicate child items, regardless from the group they belong to, should be
     *         allowed, false otherwise
     * @param notifyOnChange
     *         True, if the method <code>notifyDataSetChanged():void</code> should be automatically
     *         called when the adapter's underlying data has been changed, false otherwise
     * @param expandGroupOnClick
     *         True, if a group should be expanded, when it is clicked by the user, false otherwise
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
     */
    protected AbstractExpandableListAdapter(@NonNull final Context context,
                                            @NonNull final DecoratorType decorator,
                                            @NonNull final LogLevel logLevel,
                                            @NonNull final MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter,
                                            final boolean allowDuplicateChildren,
                                            final boolean notifyOnChange,
                                            final boolean expandGroupOnClick,
                                            @NonNull final Set<ExpandableListAdapterItemClickListener<GroupType, ChildType>> itemClickListeners,
                                            @NonNull final Set<ExpandableListAdapterItemLongClickListener<GroupType, ChildType>> itemLongClickListeners,
                                            @NonNull final Set<ExpandableListAdapterListener<GroupType, ChildType>> adapterListeners,
                                            @NonNull final Set<ExpansionListener<GroupType, ChildType>> expansionListeners) {
        ensureNotNull(context, "The context may not be null");
        ensureNotNull(decorator, "The decorator may not be null");
        ensureNotNull(itemClickListeners, "The item click listeners may not be null");
        ensureNotNull(itemLongClickListeners, "The item long click listeners may not be null");
        ensureNotNull(adapterListeners, "The adapter listeners may not be null");
        ensureNotNull(expansionListeners, "The expansion listeners may not be null");
        this.context = context;
        this.decorator = decorator;
        this.logger = new Logger(logLevel);
        this.groupAdapter = groupAdapter;
        this.groupAdapter.setLogLevel(LogLevel.OFF);
        this.groupAdapter.notifyOnChange(false);
        this.allowDuplicateChildren = allowDuplicateChildren;
        this.notifyOnChange = notifyOnChange;
        this.expandGroupOnClick = expandGroupOnClick;
        this.itemClickListeners = itemClickListeners;
        this.itemLongClickListeners = itemLongClickListeners;
        this.adapterListeners = adapterListeners;
        this.expansionListeners = expansionListeners;
        addItemClickListener(createGroupClickListener());
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
        ensureNotNull(listener, "The listener may not be null");
        adapterListeners.add(listener);
        String message = "Added adapter listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void removeAdapterListener(
            @NonNull final ExpandableListAdapterListener<GroupType, ChildType> listener) {
        ensureNotNull(listener, "The listener may not be null");
        adapterListeners.remove(listener);
        String message = "Removed adapter listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void addExpansionListener(
            @NonNull final ExpansionListener<GroupType, ChildType> listener) {
        ensureNotNull(listener, "The listener may not be null");
        expansionListeners.add(listener);
        String message = "Added expansion listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void removeExpansionListener(
            @NonNull final ExpansionListener<GroupType, ChildType> listener) {
        ensureNotNull(listener, "The listener may not be null");
        expansionListeners.remove(listener);
        String message = "Removed expansion listener \"" + listener + "\"";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void addItemClickListener(
            @NonNull final ExpandableListAdapterItemClickListener<GroupType, ChildType> listener) {
        ensureNotNull(listener, "The listener may not be null");
        itemClickListeners.add(listener);
    }

    @Override
    public final void removeItemClickListener(
            @NonNull final ExpandableListAdapterItemClickListener<GroupType, ChildType> listener) {
        ensureNotNull(listener, "The listener may not be null");
        itemClickListeners.remove(listener);
    }

    @Override
    public final void addItemLongClickListener(
            @NonNull final ExpandableListAdapterItemLongClickListener<GroupType, ChildType> listener) {
        ensureNotNull(listener, "The listener may not be null");
        itemLongClickListeners.add(listener);
    }

    @Override
    public final void removeItemLongClickListener(
            @NonNull final ExpandableListAdapterItemLongClickListener<GroupType, ChildType> listener) {
        ensureNotNull(listener, "The listener may not be null");
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
            notifyOnDataSetChanged();
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
        ensureNotNull(groups, "The collection may not be null");
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
        ensureNotNull(groups, "The array may not be null");
        return addAllGroups(index, Arrays.asList(groups));
    }

    @Override
    public final GroupType replaceGroup(final int index, @NonNull final GroupType group) {
        ensureNotNull(group, "The group may not be null");
        GroupType replacedGroup =
                groupAdapter.replaceItem(index, new Group<>(group, createChildAdapter())).getData();
        notifyOnGroupRemoved(replacedGroup, index);
        notifyOnGroupAdded(group, index);
        notifyOnDataSetChanged();
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
        notifyOnDataSetChanged();
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
            notifyOnDataSetChanged();
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
        ensureNotNull(groups, "The collection may not be null");
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
        ensureNotNull(groups, "The collection may not be null");

        for (int i = getGroupCount() - 1; i >= 0; i--) {
            if (!groups.contains(getGroup(i))) {
                removeGroup(i);
            }
        }
    }

    @SafeVarargs
    @Override
    public final void retainAllGroups(@NonNull final GroupType... groups) {
        ensureNotNull(groups, "The array may not be null");
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
        ensureNotNull(group, "The group may not be null");

        for (int i = 0; i < getGroupCount(); i++) {
            if (getGroup(i) == group) {
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
        ensureNotNull(groups, "The collection may not be null");
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
            group.getChildAdapter().allowDuplicates(allowDuplicateChildren);
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
                notifyOnDataSetChanged();
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
        ensureNotNull(children, "The collection may not be null");
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
        ensureNotNull(children, "The array may not be null");
        return addAllChildren(groupIndex, Arrays.asList(children));
    }

    @SafeVarargs
    @Override
    public final boolean addAllChildren(@NonNull final GroupType group,
                                        @NonNull final ChildType... children) {
        ensureNotNull(children, "The array may not be null");
        return addAllChildren(group, Arrays.asList(children));
    }

    @SafeVarargs
    @Override
    public final boolean addAllChildren(final int groupIndex, final int index,
                                        @NonNull final ChildType... children) {
        ensureNotNull(children, "The array may not be null");
        return addAllChildren(groupIndex, index, Arrays.asList(children));
    }

    @SafeVarargs
    @Override
    public final boolean addAllChildren(@NonNull final GroupType group, final int index,
                                        @NonNull final ChildType... children) {
        ensureNotNull(children, "The array may not be null");
        return addAllChildren(group, index, Arrays.asList(children));
    }

    @Override
    public final ChildType replaceChild(final int groupIndex, final int index,
                                        @NonNull final ChildType child) {
        ensureNotNull(child, "The child may not be null");
        Group<GroupType, ChildType> group = groupAdapter.getItem(groupIndex);
        ChildType replacedChild = group.getChildAdapter().replaceItem(index, child);
        notifyOnChildRemoved(replacedChild, index, group.getData(), groupIndex);
        notifyOnChildAdded(replacedChild, index, group.getData(), groupIndex);
        notifyOnDataSetChanged();
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

        notifyOnDataSetChanged();
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
        ensureNotNull(child, "The child may not be null");
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
        ensureNotNull(children, "The collection may not be null");
        boolean result = true;
        Group<GroupType, ChildType> group = groupAdapter.getItem(groupIndex);

        for (int i = group.getChildAdapter().getCount() - 1; i >= 0; i--) {
            result &= removeChild(removeEmptyGroup, groupIndex, i) != null;
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
        ensureNotNull(children, "The array may not be null");
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
        ensureNotNull(children, "The array may not be null");
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
        ensureNotNull(children, "The array may not be null");
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
        ensureNotNull(children, "The collection may not be null");
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
        ensureNotNull(children, "The array may not be null");
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
        ensureNotNull(children, "The array may not be null");
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
        ensureNotNull(children, "The array may not be null");
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
        ensureNotNull(child, "The child may not be null");

        for (int i = 0; i < groupAdapter.getCount(); i++) {
            if (groupAdapter.getItem(i).getChildAdapter().containsItem(child)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final int indexOfChild(final int groupIndex, @NonNull final ChildType child) {
        ensureNotNull(child, "The child may not be null");
        return groupAdapter.getItem(groupIndex).getChildAdapter().indexOf(child);
    }

    @Override
    public final int indexOfChild(@NonNull final GroupType group, @NonNull final ChildType child) {
        return indexOfChild(indexOfGroupOrThrowException(group), child);
    }

    @Override
    public final int lastIndexOfChild(@NonNull final ChildType child) {
        ensureNotNull(child, "The child may not be null");

        for (int i = groupAdapter.getCount() - 1; i >= 0; i--) {
            if (groupAdapter.getItem(i).getChildAdapter().containsItem(child)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final int lastIndexOfChild(final int groupIndex, @NonNull final ChildType child) {
        ensureNotNull(child, "The child may not be null");
        return groupAdapter.getItem(groupIndex).getChildAdapter().lastIndexOf(child);
    }

    @Override
    public final int lastIndexOfChild(@NonNull final GroupType group,
                                      @NonNull final ChildType child) {
        return lastIndexOfChild(indexOfGroupOrThrowException(group), child);
    }

    @Override
    public final boolean containsChild(@NonNull final ChildType child) {
        ensureNotNull(child, "The child may not be null");

        for (Group<GroupType, ChildType> group : groupAdapter.getAllItems()) {
            if (group.getChildAdapter().containsItem(child)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public final boolean containsChild(final int groupIndex, @NonNull final ChildType child) {
        ensureNotNull(child, "The child may not be null");
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
        ensureNotNull(children, "The collection may not be null");
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
        ensureNotNull(children, "The array may not be null");
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
        ensureNotNull(children, "The children may not be null");
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
        return getAdapterView() != null && getAdapterView().isGroupExpanded(index);
    }

    @Override
    public final boolean isGroupExpanded(@NonNull final GroupType group) {
        return isGroupExpanded(indexOfGroup(group));
    }

    @Override
    public final boolean isGroupCollapsed(final int index) {
        return !isGroupExpanded(index);
    }

    @Override
    public final boolean isGroupCollapsed(@NonNull final GroupType group) {
        return !isGroupExpanded(group);
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
        if (getAdapterView() != null) {
            for (int i = 0; i < getGroupCount(); i++) {
                if (getAdapterView().isGroupExpanded(i)) {
                    return i;
                }
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
        if (getAdapterView() != null) {
            for (int i = getGroupCount() - 1; i >= 0; i--) {
                if (getAdapterView().isGroupExpanded(i)) {
                    return i;
                }
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
        if (getAdapterView() != null) {
            for (int i = 0; i < getGroupCount(); i++) {
                if (!getAdapterView().isGroupExpanded(i)) {
                    return i;
                }
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
        if (getAdapterView() != null) {
            for (int i = getGroupCount() - 1; i >= 0; i--) {
                if (!getAdapterView().isGroupExpanded(i)) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public final List<GroupType> getExpandedGroups() {
        List<GroupType> expandedGroups = new ArrayList<>();

        if (getAdapterView() != null) {
            for (int i = 0; i < getGroupCount(); i++) {
                if (getAdapterView().isGroupExpanded(i)) {
                    expandedGroups.add(getGroup(i));
                }
            }
        }

        return new UnmodifiableList<>(expandedGroups);
    }

    @Override
    public final List<Integer> getExpandedGroupIndices() {
        List<Integer> expandedGroupIndices = new ArrayList<>();

        if (getAdapterView() != null) {
            for (int i = 0; i < getGroupCount(); i++) {
                if (getAdapterView().isGroupExpanded(i)) {
                    expandedGroupIndices.add(i);
                }
            }
        }

        return new UnmodifiableList<>(expandedGroupIndices);
    }

    @Override
    public final List<GroupType> getCollapsedGroups() {
        List<GroupType> collapedGroups = new ArrayList<>();

        if (getAdapterView() != null) {
            for (int i = 0; i < getGroupCount(); i++) {
                if (!getAdapterView().isGroupExpanded(i)) {
                    collapedGroups.add(getGroup(i));
                }
            }
        }

        return new UnmodifiableList<>(collapedGroups);
    }

    @Override
    public final List<Integer> getCollapsedGroupIndices() {
        List<Integer> collapsedGroupIndices = new ArrayList<>();

        if (getAdapterView() != null) {
            for (int i = 0; i < getGroupCount(); i++) {
                if (!getAdapterView().isGroupExpanded(i)) {
                    collapsedGroupIndices.add(i);
                }
            }
        }

        return new UnmodifiableList<>(collapsedGroupIndices);
    }

    @Override
    public final int getExpandedGroupCount() {
        return getExpandedGroups().size();
    }

    @Override
    public final int getCollapsedGroupCount() {
        return getCollapsedGroups().size();
    }

    @Override
    public final void expandGroup(@NonNull final GroupType group) {
        if (getAdapterView() != null) {
            getAdapterView().expandGroup(indexOfGroupOrThrowException(group));
        } else {
            String message = "The group \"" + group + "\" has not been expanded, because the " +
                    "adapter is not attached to a view";
            getLogger().logWarn(getClass(), message);
        }
    }

    @Override
    public final void expandGroup(final int index) {
        if (getAdapterView() != null) {
            getAdapterView().expandGroup(index);
        } else {
            String message =
                    "The group at index " + index + " has not been expanded, because the " +
                            "adapter is not attached to a view";
            getLogger().logWarn(getClass(), message);
        }
    }

    @Override
    public final void collapseGroup(@NonNull final GroupType group) {
        if (getAdapterView() != null) {
            getAdapterView().collapseGroup(indexOfGroupOrThrowException(group));
        } else {
            String message = "The group \"" + group + "\" has not been collapsed, because the " +
                    "adapter is not attached to a view";
            getLogger().logWarn(getClass(), message);
        }
    }

    @Override
    public final void collapseGroup(final int index) {
        if (getAdapterView() != null) {
            getAdapterView().collapseGroup(index);
        } else {
            String message =
                    "The group at index " + index + "has not been collapsed, because the " +
                            "adapter is not attached to a view";
            getLogger().logWarn(getClass(), message);
        }
    }

    @Override
    public final boolean triggerGroupExpansion(@NonNull final GroupType group) {
        if (getAdapterView() != null) {
            int index = indexOfGroupOrThrowException(group);

            if (getAdapterView().isGroupExpanded(index)) {
                getAdapterView().collapseGroup(index);
                return false;
            } else {
                getAdapterView().expandGroup(index);
                return true;
            }
        } else {
            String message = "The expansion of the group \"" + group +
                    "\" has not been triggered, because the " + "adapter is not attached to a view";
            getLogger().logWarn(getClass(), message);
            return false;
        }
    }

    @Override
    public final boolean triggerGroupExpansion(final int index) {
        if (getAdapterView() != null) {
            if (getAdapterView().isGroupExpanded(index)) {
                getAdapterView().collapseGroup(index);
                return false;
            } else {
                getAdapterView().expandGroup(index);
                return true;
            }
        } else {
            String message = "The expansion of the group at index " + index +
                    " has not been triggered, because the " + "adapter is not attached to a view";
            getLogger().logWarn(getClass(), message);
            return false;
        }
    }

    @Override
    public final void expandAllGroups() {
        if (getAdapterView() != null) {
            for (int i = 0; i < getGroupCount(); i++) {
                getAdapterView().expandGroup(i);
            }
        } else {
            String message = "All groups have not been expanded, because the " +
                    "adapter is not attached to a view";
            getLogger().logWarn(getClass(), message);
        }
    }

    @Override
    public final void collapseAllGroups() {
        if (getAdapterView() != null) {
            for (int i = 0; i < getGroupCount(); i++) {
                getAdapterView().expandGroup(i);
            }
        } else {
            String message = "All groups have not been collapsed, because the " +
                    "adapter is not attached to a view";
            getLogger().logWarn(getClass(), message);
        }
    }

    @Override
    public final void triggerAllGroupExpansions() {
        if (getAdapterView() != null) {
            for (int i = 0; i < getGroupCount(); i++) {
                if (getAdapterView().isGroupExpanded(i)) {
                    getAdapterView().collapseGroup(i);
                } else {
                    getAdapterView().expandGroup(i);
                }
            }
        } else {
            String message = "The expansion states of all groups have not been " +
                    "triggered, because the adapter is not attached to a view";
            getLogger().logWarn(getClass(), message);
        }
    }

    @Override
    public final boolean isGroupExpandedOnClick() {
        return expandGroupOnClick;
    }

    @Override
    public final void expandGroupOnClick(final boolean expandGroupOnClick) {
        this.expandGroupOnClick = expandGroupOnClick;
        String message =
                "Groups are now " + (expandGroupOnClick ? "" : "not ") + "expanded on click";
        getLogger().logDebug(getClass(), message);
    }

    @Override
    public final void attach(@NonNull final ExpandableListView adapterView) {
        ensureNotNull(adapterView, "The adapter view may not be null");
        detach();
        this.adapterView = adapterView;
        this.adapterView.setAdapter(this);
        this.adapterView.setOnGroupClickListener(createAdapterViewOnGroupClickListener());
        this.adapterView.setOnChildClickListener(createAdapterViewOnChildClickListener());
        this.adapterView.setOnItemLongClickListener(createAdapterViewOnItemLongClickListener());
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
                String message = "Adapter has not been detached, because the " +
                        "adapter of the corresponding view has been changed " + "in the meantime";
                getLogger().logVerbose(getClass(), message);
            }

            adapterView = null;
        } else {
            String message = "Adapter has not been detached, because it has not " +
                    "been attached to a view yet";
            getLogger().logVerbose(getClass(), message);
        }
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
    public final boolean hasStableIds() {
        return true;
    }

    @Override
    public final View getGroupView(final int groupIndex, final boolean isExpanded,
                                   @Nullable final View convertView,
                                   @Nullable final ViewGroup parent) {
        if (adapterView == null) {
            throw new IllegalStateException(
                    "Adapter must be attached to an ExpandableListView using its attach-method");
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
        if (adapterView == null) {
            throw new IllegalStateException(
                    "Adapter must be attached to an ExpandableListView using its attach-method");
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
        } else {
            String message = "The state of the adapter view can not be stored, because the " +
                    "adapter has not been attached to a view";
            getLogger().logWarn(getClass(), message);
        }

        savedState.putBoolean(ALLOW_DUPLICATE_CHILDREN_BUNDLE_KEY, areDuplicateChildrenAllowed());
        savedState.putBoolean(EXPAND_GROUP_ON_CLICK_BUNDLE_KEY, isGroupExpandedOnClick());
        savedState.putInt(LOG_LEVEL_BUNDLE_KEY, getLogLevel().getRank());
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
                    String childAdapterKey = String.format(CHILD_ADAPTER_BUNDLE_KEY, i);

                    if (savedState.containsKey(childAdapterKey)) {
                        MultipleChoiceListAdapter<ChildType> childAdapter = createChildAdapter();
                        childAdapter.onRestoreInstanceState(savedState, childAdapterKey);
                        groupAdapter.getItem(i).setChildAdapter(childAdapter);
                    }
                }
            }

            if (savedState.containsKey(ADAPTER_VIEW_STATE_BUNDLE_KEY) && adapterView != null) {
                AdapterViewUtil.onRestoreInstanceState(adapterView, savedState,
                        ADAPTER_VIEW_STATE_BUNDLE_KEY);
            }

            allowDuplicateChildren(savedState.getBoolean(ALLOW_DUPLICATE_CHILDREN_BUNDLE_KEY));
            expandGroupOnClick(savedState.getBoolean(EXPAND_GROUP_ON_CLICK_BUNDLE_KEY));
            setLogLevel(LogLevel.fromRank(savedState.getInt(LOG_LEVEL_BUNDLE_KEY)));
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
        result = prime * result + (expandGroupOnClick ? 1231 : 1237);
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
        if (expandGroupOnClick != other.expandGroupOnClick)
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