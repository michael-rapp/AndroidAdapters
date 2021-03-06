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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import de.mrapp.android.adapter.DataSetObserver;
import de.mrapp.android.adapter.ExpandableListDecorator;
import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.FilterQuery;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.Order;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.expandablelist.enablestate.ExpandableListEnableStateListener;
import de.mrapp.android.adapter.expandablelist.filterable.ExpandableListFilterListener;
import de.mrapp.android.adapter.expandablelist.itemstate.ExpandableListItemStateListener;
import de.mrapp.android.adapter.expandablelist.sortable.ExpandableListSortingListener;
import de.mrapp.android.adapter.list.selectable.MultipleChoiceListAdapterImplementation;
import de.mrapp.android.util.logging.LogLevel;
import de.mrapp.util.datastructure.ListenerList;

import static androidx.test.InstrumentationRegistry.getContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests the functionality of the class {@link AbstractExpandableListAdapter}.
 *
 * @author Michael Rapp
 */
@RunWith(AndroidJUnit4.class)
public class AbstractExpandableListAdapterTest {

    /**
     * An implementation of the abstract class {@link AbstractExpandableListAdapter}, which is
     * needed for test purposes.
     */
    private class AbstractExpandableListAdapterImplementation extends
            AbstractExpandableListAdapter<Object, Object, ExpandableListDecorator<Object, Object>> {

        /**
         * The constant serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        protected void onSaveInstanceState(@NonNull Bundle outState) {

        }

        @Override
        protected void onRestoreInstanceState(@NonNull Bundle savedState) {

        }

        @Override
        protected void applyDecoratorOnGroup(@NonNull Context context, @NonNull View view,
                                             int index) {

        }

        @Override
        protected void applyDecoratorOnChild(@NonNull Context context, @NonNull View view,
                                             int groupIndex, int childIndex) {

        }

        /**
         * Creates a new adapter, whose underlying data is managed as a list of arbitrary group and
         * child items.
         *
         * @param context
         *         The context, the adapter belongs to, as an instance of the class {@link Context}.
         *         The context may not be null
         * @param decorator
         *         The decorator, which should be used to customize the appearance of the views,
         *         which are used to visualize the group and child items of the adapter, as an
         *         instance of the generic type DecoratorType. The decorator may not be null
         * @param logLevel
         *         The log level, which should be used for logging, as a value of the enum LogLevel.
         *         The log level may not be null
         * @param groupAdapter
         *         The adapter, which should manage the adapter's group items, as an instance of the
         *         type {@link MultipleChoiceListAdapter}. The adapter may not be null
         * @param allowDuplicateChildren
         *         True, if duplicate group items, regardless from the group they belong to, should
         *         be allowed, false otherwise
         * @param notifyOnChange
         *         True, if the method <code>notifyDataSetChanged():void</code> should be
         *         automatically called when the adapter's underlying data has been changed, false
         *         otherwise
         * @param expandGroupOnClick
         *         True, if a group should be expanded, when it is clicked by the user, false
         *         otherwise
         * @param itemClickListeners
         *         A list, which contains the listeners, which should be notified, when an item of
         *         the adapter has been clicked by the user, as an instance of the class
         *         ListenerList or an empty list, if no listeners should be notified
         * @param itemLongClickListeners
         *         A list, which contains the listeners, which should be notified, when an item of
         *         the adapter has been long-clicked by the user, as an instance of the class
         *         ListenerList, or an empty list, if no listeners should be notified
         * @param adapterListeners
         *         A list, which contains the listeners, which should be notified, when the
         *         adapter's underlying data has been modified, as an instance of the class
         *         ListenerList or an empty list, if no listeners should be notified
         * @param expansionListeners
         *         A list, which contains the listeners, which should be notified, when a group item
         *         has been expanded or collapsed, as an instance of the class ListenerList or an
         *         empty list, if no listeners should be notified
         */
        protected AbstractExpandableListAdapterImplementation(final Context context,
                                                              final ExpandableListDecorator<Object, Object> decorator,
                                                              final LogLevel logLevel,
                                                              final MultipleChoiceListAdapter<Group<Object, Object>> groupAdapter,
                                                              final boolean allowDuplicateChildren,
                                                              final boolean notifyOnChange,
                                                              final boolean expandGroupOnClick,
                                                              final ListenerList<ExpandableListAdapterItemClickListener<Object, Object>> itemClickListeners,
                                                              final ListenerList<ExpandableListAdapterItemLongClickListener<Object, Object>> itemLongClickListeners,
                                                              final ListenerList<ExpandableListAdapterListener<Object, Object>> adapterListeners,
                                                              final ListenerList<ExpansionListener<Object, Object>> expansionListeners) {
            super(context, decorator, logLevel, groupAdapter, allowDuplicateChildren,
                    notifyOnChange, expandGroupOnClick, itemClickListeners, itemLongClickListeners,
                    adapterListeners, expansionListeners);
        }

        @Override
        public AbstractExpandableListAdapter<Object, Object, ExpandableListDecorator<Object, Object>> clone()
                throws CloneNotSupportedException {
            return null;
        }

        @Override
        public boolean isGroupEnabled(int groupIndex) {
            return false;
        }

        @Override
        public boolean isGroupEnabled(@NonNull Object group) {
            return false;
        }

        @Override
        public int getFirstEnabledGroupIndex() {
            return 0;
        }

        @Override
        public Object getFirstEnabledGroup() {
            return null;
        }

        @Override
        public int getLastEnabledGroupIndex() {
            return 0;
        }

        @Override
        public Object getLastEnabledGroup() {
            return null;
        }

        @Override
        public int getFirstDisabledGroupIndex() {
            return 0;
        }

        @Override
        public Object getFirstDisabledGroup() {
            return null;
        }

        @Override
        public int getLastDisabledGroupIndex() {
            return 0;
        }

        @Override
        public Object getLastDisabledGroup() {
            return null;
        }

        @Override
        public List<Integer> getEnabledGroupIndices() {
            return null;
        }

        @Override
        public List<Object> getEnabledGroups() {
            return null;
        }

        @Override
        public List<Integer> getDisabledGroupIndices() {
            return null;
        }

        @Override
        public List<Object> getDisabledGroups() {
            return null;
        }

        @Override
        public int getEnabledGroupCount() {
            return 0;
        }

        @Override
        public void setGroupEnabled(int groupIndex, boolean enabled) {

        }

        @Override
        public void setGroupEnabled(@NonNull Object group, boolean enabled) {

        }

        @Override
        public boolean triggerGroupEnableState(int groupIndex) {
            return false;
        }

        @Override
        public boolean triggerGroupEnableState(boolean triggerChildStates, int groupIndex) {
            return false;
        }

        @Override
        public boolean triggerGroupEnableState(@NonNull Object group) {
            return false;
        }

        @Override
        public boolean triggerGroupEnableState(boolean triggerChildStates, @NonNull Object group) {
            return false;
        }

        @Override
        public void setAllGroupsEnabled(boolean enabled) {

        }

        @Override
        public void triggerAllGroupEnableStates() {

        }

        @Override
        public void triggerAllGroupEnableStates(boolean triggerChildStates) {

        }

        @Override
        public boolean isChildEnabled(@NonNull Object group, int childIndex) {
            return false;
        }

        @Override
        public boolean isChildEnabled(@NonNull Object group, @NonNull Object child) {
            return false;
        }

        @Override
        public boolean isChildEnabled(int groupIndex, int childIndex) {
            return false;
        }

        @Override
        public boolean isChildEnabled(int groupIndex, @NonNull Object child) {
            return false;
        }

        @Override
        public int getFirstEnabledChildIndex(@NonNull Object group) {
            return 0;
        }

        @Override
        public Object getFirstEnabledChild(@NonNull Object group) {
            return null;
        }

        @Override
        public int getFirstEnabledChildIndex(int groupIndex) {
            return 0;
        }

        @Override
        public Object getFirstEnabledChild(int groupIndex) {
            return null;
        }

        @Override
        public int getLastEnabledChildIndex(@NonNull Object group) {
            return 0;
        }

        @Override
        public Object getLastEnabledChild(@NonNull Object group) {
            return null;
        }

        @Override
        public int getLastEnabledChildIndex(int groupIndex) {
            return 0;
        }

        @Override
        public Object getLastEnabledChild(int groupIndex) {
            return null;
        }

        @Override
        public int getFirstDisabledChildIndex(@NonNull Object group) {
            return 0;
        }

        @Override
        public Object getFirstDisabledChild(@NonNull Object group) {
            return null;
        }

        @Override
        public int getFirstDisabledChildIndex(int groupIndex) {
            return 0;
        }

        @Override
        public Object getFirstDisabledChild(int groupIndex) {
            return null;
        }

        @Override
        public int getLastDisabledChildIndex(@NonNull Object group) {
            return 0;
        }

        @Override
        public Object getLastDisabledChild(@NonNull Object group) {
            return null;
        }

        @Override
        public int getLastDisabledChildIndex(int groupIndex) {
            return 0;
        }

        @Override
        public Object getLastDisabledChild(int groupIndex) {
            return null;
        }

        @Override
        public List<Object> getEnabledChildren() {
            return null;
        }

        @Override
        public List<Integer> getEnabledChildIndices(@NonNull Object group) {
            return null;
        }

        @Override
        public List<Object> getEnabledChildren(@NonNull Object group) {
            return null;
        }

        @Override
        public List<Integer> getEnabledChildIndices(int groupIndex) {
            return null;
        }

        @Override
        public List<Object> getEnabledChildren(int groupIndex) {
            return null;
        }

        @Override
        public List<Object> getDisabledChildren() {
            return null;
        }

        @Override
        public List<Integer> getDisabledChildIndices(@NonNull Object group) {
            return null;
        }

        @Override
        public List<Object> getDisabledChildren(@NonNull Object group) {
            return null;
        }

        @Override
        public List<Integer> getDisabledChildIndices(int groupIndex) {
            return null;
        }

        @Override
        public List<Object> getDisabledChildren(int groupIndex) {
            return null;
        }

        @Override
        public int getEnabledChildCount() {
            return 0;
        }

        @Override
        public int getEnabledChildCount(@NonNull Object group) {
            return 0;
        }

        @Override
        public int getEnabledChildCount(int groupIndex) {
            return 0;
        }

        @Override
        public void setChildEnabled(@NonNull Object group, int childIndex, boolean enabled) {

        }

        @Override
        public void setChildEnabled(@NonNull Object group, @NonNull Object child, boolean enabled) {

        }

        @Override
        public void setChildEnabled(int groupIndex, int childIndex, boolean enabled) {

        }

        @Override
        public void setChildEnabled(int groupIndex, @NonNull Object child, boolean enabled) {

        }

        @Override
        public boolean triggerChildEnableState(@NonNull Object group, int childIndex) {
            return false;
        }

        @Override
        public boolean triggerChildEnableState(@NonNull Object group, @NonNull Object childItem) {
            return false;
        }

        @Override
        public boolean triggerChildEnableState(int groupIndex, int childIndex) {
            return false;
        }

        @Override
        public boolean triggerChildEnableState(int groupIndex, @NonNull Object childItem) {
            return false;
        }

        @Override
        public void setAllChildrenEnabled(boolean enabled) {

        }

        @Override
        public void setAllChildrenEnabled(@NonNull Object group, boolean enabled) {

        }

        @Override
        public void setAllChildrenEnabled(int groupIndex, boolean enabled) {

        }

        @Override
        public void triggerAllChildEnableStates() {

        }

        @Override
        public void triggerAllChildEnableStates(@NonNull Object group) {

        }

        @Override
        public void triggerAllChildEnableStates(int groupIndex) {

        }

        @Override
        public boolean areChildEnableStatesSetImplicitly() {
            return false;
        }

        @Override
        public void setChildEnableStatesImplicitly(boolean setChildEnableStatesImplicitly) {

        }

        @Override
        public void addEnableStateListener(
                @NonNull ExpandableListEnableStateListener<Object, Object> listener) {

        }

        @Override
        public void removeEnableStateListener(
                @NonNull ExpandableListEnableStateListener<Object, Object> listener) {

        }

        @Override
        public boolean isFiltered() {
            return false;
        }

        @Override
        public Set<? extends FilterQuery> getGroupFilterQueries() {
            return null;
        }

        @Nullable
        @Override
        public List<Object> applyGroupFilter(@NonNull String query, int flags) {
            return null;
        }

        @Nullable
        @Override
        public List<Object> applyGroupFilter(@NonNull String query, int flags,
                                             @NonNull Filter<Object> filter) {
            return null;
        }

        @Override
        public boolean resetGroupFilter(@NonNull String query, int flags) {
            return false;
        }

        @Override
        public void resetAllGroupFilters() {

        }

        @Override
        public boolean isGroupFilterApplied(@NonNull String query, int flags) {
            return false;
        }

        @Override
        public boolean areGroupsFiltered() {
            return false;
        }

        @Nullable
        @Override
        public List<Object> applyChildFilter(@NonNull String query, int flags) {
            return null;
        }

        @Nullable
        @Override
        public List<Object> applyChildFilter(boolean filterEmptyGroups, @NonNull String query,
                                             int flags) {
            return null;
        }

        @Nullable
        @Override
        public List<Object> applyChildFilter(@NonNull Object group, @NonNull String query,
                                             int flags) {
            return null;
        }

        @Nullable
        @Override
        public List<Object> applyChildFilter(boolean filterEmptyGroup, @NonNull Object group,
                                             @NonNull String query, int flags) {
            return null;
        }

        @Nullable
        @Override
        public List<Object> applyChildFilter(int groupIndex, @NonNull String query, int flags) {
            return null;
        }

        @Nullable
        @Override
        public List<Object> applyChildFilter(boolean filterEmptyGroup, int groupIndex,
                                             @NonNull String query, int flags) {
            return null;
        }

        @Nullable
        @Override
        public List<Object> applyChildFilter(@NonNull String query, int flags,
                                             @NonNull Filter<Object> filter) {
            return null;
        }

        @Nullable
        @Override
        public List<Object> applyChildFilter(boolean filterEmptyGroups, @NonNull String query,
                                             int flags, @NonNull Filter<Object> filter) {
            return null;
        }

        @Nullable
        @Override
        public List<Object> applyChildFilter(@NonNull Object group, @NonNull String query,
                                             int flags, @NonNull Filter<Object> filter) {
            return null;
        }

        @Nullable
        @Override
        public List<Object> applyChildFilter(boolean filterEmptyGroup, @NonNull Object group,
                                             @NonNull String query, int flags,
                                             @NonNull Filter<Object> filter) {
            return null;
        }

        @Nullable
        @Override
        public List<Object> applyChildFilter(int groupIndex, @NonNull String query, int flags,
                                             @NonNull Filter<Object> filter) {
            return null;
        }

        @Nullable
        @Override
        public List<Object> applyChildFilter(boolean filterEmptyGroup, int groupIndex,
                                             @NonNull String query, int flags,
                                             @NonNull Filter<Object> filter) {
            return null;
        }

        @Override
        public boolean resetChildFilter(@NonNull String query, int flags) {
            return false;
        }

        @Override
        public boolean resetChildFilter(@NonNull Object group, @NonNull String query, int flags) {
            return false;
        }

        @Override
        public boolean resetChildFilter(int groupIndex, @NonNull String query, int flags) {
            return false;
        }

        @Override
        public void resetAllChildFilters() {

        }

        @Override
        public void resetAllChildFilters(@NonNull Object group) {

        }

        @Override
        public void resetAllChildFilters(int groupIndex) {

        }

        @Override
        public boolean isChildFilterApplied(@NonNull String query, int flags) {
            return false;
        }

        @Override
        public boolean areChildrenFiltered() {
            return false;
        }

        @Override
        public Set<? extends FilterQuery> getChildFilterQueries() {
            return null;
        }

        @Override
        public Set<? extends FilterQuery> getChildFilterQueries(final int groupIndex) {
            return null;
        }

        @Override
        public Set<? extends FilterQuery> getChildFilterQueries(final Object group) {
            return null;
        }

        @Override
        public boolean isChildFilterApplied(@NonNull Object group, @NonNull String query,
                                            int flags) {
            return false;
        }

        @Override
        public boolean isChildFilterApplied(int groupIndex, @NonNull String query, int flags) {
            return false;
        }

        @Override
        public boolean areChildrenFiltered(@NonNull Object group) {
            return false;
        }

        @Override
        public boolean areChildrenFiltered(int groupIndex) {
            return false;
        }

        @Override
        public void addFilterListener(
                @NonNull ExpandableListFilterListener<Object, Object> listener) {

        }

        @Override
        public void removeFilterListener(
                @NonNull ExpandableListFilterListener<Object, Object> listener) {

        }

        @Override
        public int getNumberOfGroupStates() {
            return 0;
        }

        @Override
        public void setNumberOfGroupStates(int numberOfGroupStates) {

        }

        @Override
        public int minGroupState() {
            return 0;
        }

        @Override
        public int maxGroupState() {
            return 0;
        }

        @Override
        public int getGroupState(int groupIndex) {
            return 0;
        }

        @Override
        public int getGroupState(@NonNull Object group) {
            return 0;
        }

        @Override
        public int setGroupState(int groupIndex, int state) {
            return 0;
        }

        @Override
        public int setGroupState(@NonNull Object group, int state) {
            return 0;
        }

        @Override
        public boolean setAllGroupStates(int state) {
            return false;
        }

        @Override
        public int triggerGroupState(int groupIndex) {
            return 0;
        }

        @Override
        public int triggerGroupState(boolean triggerChildStates, int groupIndex) {
            return 0;
        }

        @Override
        public int triggerGroupState(@NonNull Object group) {
            return 0;
        }

        @Override
        public int triggerGroupState(boolean triggerChildStates, @NonNull Object group) {
            return 0;
        }

        @Override
        public boolean triggerAllGroupStates() {
            return false;
        }

        @Override
        public boolean triggerAllGroupStates(boolean triggerChildStates) {
            return false;
        }

        @Override
        public int getFirstGroupIndexWithSpecificState(int state) {
            return 0;
        }

        @Override
        public Object getFirstGroupWithSpecificState(int state) {
            return null;
        }

        @Override
        public int getLastGroupIndexWithSpecificState(int state) {
            return 0;
        }

        @Override
        public Object getLastGroupWithSpecificState(int state) {
            return null;
        }

        @Override
        public List<Integer> getGroupIndicesWithSpecificState(int state) {
            return null;
        }

        @Override
        public List<Object> getGroupsWithSpecificState(int state) {
            return null;
        }

        @Override
        public int getGroupStateCount(int state) {
            return 0;
        }

        @Override
        public boolean isGroupStateTriggeredOnClick() {
            return false;
        }

        @Override
        public void triggerGroupStateOnClick(boolean triggerGroupStateOnClick) {

        }

        @Override
        public int getNumberOfChildStates() {
            return 0;
        }

        @Override
        public void setNumberOfChildStates(int numberOfChildStates) {

        }

        @Override
        public int minChildState() {
            return 0;
        }

        @Override
        public int maxChildState() {
            return 0;
        }

        @Override
        public int getChildState(@NonNull Object group, int childIndex) {
            return 0;
        }

        @Override
        public int getChildState(@NonNull Object group, @NonNull Object child) {
            return 0;
        }

        @Override
        public int getChildState(int groupIndex, int childIndex) {
            return 0;
        }

        @Override
        public int getChildState(int groupIndex, @NonNull Object child) {
            return 0;
        }

        @Override
        public int setChildState(@NonNull Object group, int childIndex, int state) {
            return 0;
        }

        @Override
        public int setChildState(@NonNull Object group, @NonNull Object child, int state) {
            return 0;
        }

        @Override
        public int setChildState(int groupIndex, int childIndex, int state) {
            return 0;
        }

        @Override
        public int setChildState(int groupIndex, @NonNull Object child, int state) {
            return 0;
        }

        @Override
        public boolean setAllChildStates(int state) {
            return false;
        }

        @Override
        public boolean setAllChildStates(@NonNull Object group, int state) {
            return false;
        }

        @Override
        public boolean setAllChildStates(int groupIndex, int state) {
            return false;
        }

        @Override
        public int triggerChildState(@NonNull Object group, int childIndex) {
            return 0;
        }

        @Override
        public int triggerChildState(@NonNull Object group, @NonNull Object child) {
            return 0;
        }

        @Override
        public int triggerChildState(int groupIndex, int childIndex) {
            return 0;
        }

        @Override
        public int triggerChildState(int groupIndex, @NonNull Object child) {
            return 0;
        }

        @Override
        public boolean triggerAllChildStates() {
            return false;
        }

        @Override
        public boolean triggerAllChildStates(@NonNull Object group) {
            return false;
        }

        @Override
        public boolean triggerAllChildStates(int groupIndex) {
            return false;
        }

        @Override
        public int getFirstChildIndexWithSpecificState(@NonNull Object group, int state) {
            return 0;
        }

        @Override
        public int getFirstChildIndexWithSpecificState(int groupIndex, int state) {
            return 0;
        }

        @Override
        public Object getFirstChildWithSpecificState(@NonNull Object group, int state) {
            return null;
        }

        @Override
        public Object getFirstChildWithSpecificState(int groupIndex, int state) {
            return null;
        }

        @Override
        public int getLastChildIndexWithSpecificState(@NonNull Object group, int state) {
            return 0;
        }

        @Override
        public int getLastChildIndexWithSpecificState(int groupIndex, int state) {
            return 0;
        }

        @Override
        public Object getLastChildWithSpecificState(@NonNull Object group, int state) {
            return null;
        }

        @Override
        public Object getLastChildWithSpecificState(int groupIndex, int state) {
            return null;
        }

        @Override
        public List<Integer> getChildIndicesWithSpecificState(@NonNull Object group, int state) {
            return null;
        }

        @Override
        public List<Integer> getChildIndicesWithSpecificState(int groupIndex, int state) {
            return null;
        }

        @Override
        public List<Object> getChildrenWithSpecificState(int state) {
            return null;
        }

        @Override
        public List<Object> getChildrenWithSpecificState(@NonNull Object group, int state) {
            return null;
        }

        @Override
        public List<Object> getChildrenWithSpecificState(int groupIndex, int state) {
            return null;
        }

        @Override
        public int getChildStateCount(int state) {
            return 0;
        }

        @Override
        public int getChildStateCount(@NonNull Object group, int state) {
            return 0;
        }

        @Override
        public int getChildStateCount(int groupIndex, int state) {
            return 0;
        }

        @Override
        public boolean isChildStateTriggeredOnClick() {
            return false;
        }

        @Override
        public void triggerChildStateOnClick(boolean triggerChildStateOnClick) {

        }

        @Override
        public boolean areChildStatesSetImplicitly() {
            return false;
        }

        @Override
        public void setChildStatesImplicitly(boolean setChildStatesImplicitly) {

        }

        @Override
        public void addItemStateListener(
                @NonNull ExpandableListItemStateListener<Object, Object> listener) {

        }

        @Override
        public void removeItemStateListener(
                @NonNull ExpandableListItemStateListener<Object, Object> listener) {

        }

        @Override
        public void sortGroups() {

        }

        @Override
        public void sortGroups(@NonNull Order order) {

        }

        @Override
        public void sortGroups(@Nullable Comparator<Object> comparator) {

        }

        @Override
        public void sortGroups(@NonNull Order order, @Nullable Comparator<Object> comparator) {

        }

        @Override
        public int addGroupSorted(@NonNull Object group) {
            return 0;
        }

        @Override
        public int addGroupSorted(@NonNull Object group, @Nullable Comparator<Object> comparator) {
            return 0;
        }

        @Override
        public boolean addAllGroupsSorted(@NonNull Collection<?> groups) {
            return false;
        }

        @Override
        public boolean addAllGroupsSorted(@NonNull Collection<?> groups,
                                          @Nullable Comparator<Object> comparator) {
            return false;
        }

        @Override
        public boolean addAllGroupsSorted(@NonNull Object... groups) {
            return false;
        }

        @Override
        public boolean addAllGroupsSorted(@Nullable Comparator<Object> comparator,
                                          @NonNull Object... groups) {
            return false;
        }

        @Override
        public Order getGroupOrder() {
            return null;
        }

        @Override
        public void sortChildren() {

        }

        @Override
        public void sortChildren(@NonNull Order order) {

        }

        @Override
        public void sortChildren(@Nullable Comparator<Object> comparator) {

        }

        @Override
        public void sortChildren(@NonNull Order order, @Nullable Comparator<Object> comparator) {

        }

        @Override
        public void sortChildren(int groupIndex) {

        }

        @Override
        public void sortChildren(int groupIndex, @NonNull Order order) {

        }

        @Override
        public void sortChildren(int groupIndex, @Nullable Comparator<Object> comparator) {

        }

        @Override
        public void sortChildren(int groupIndex, @NonNull Order order,
                                 @Nullable Comparator<Object> comparator) {

        }

        @Override
        public void sortChildren(@NonNull Object group) {

        }

        @Override
        public void sortChildren(@NonNull Object group, @NonNull Order order) {

        }

        @Override
        public void sortChildren(@NonNull Object group, @Nullable Comparator<Object> comparator) {

        }

        @Override
        public void sortChildren(@NonNull Object group, @NonNull Order order,
                                 @Nullable Comparator<Object> comparator) {

        }

        @Override
        public int addChildSorted(@NonNull Object group, @NonNull Object child) {
            return 0;
        }

        @Override
        public int addChildSorted(@NonNull Object group, @NonNull Object child,
                                  @Nullable Comparator<Object> comparator) {
            return 0;
        }

        @Override
        public int addChildSorted(int groupIndex, @NonNull Object child) {
            return 0;
        }

        @Override
        public int addChildSorted(int groupIndex, @NonNull Object child,
                                  @Nullable Comparator<Object> comparator) {
            return 0;
        }

        @Override
        public boolean addAllChildrenSorted(@NonNull Object group,
                                            @NonNull Collection<?> children) {
            return false;
        }

        @Override
        public boolean addAllChildrenSorted(@NonNull Object group, @NonNull Collection<?> children,
                                            @Nullable Comparator<Object> comparator) {
            return false;
        }

        @Override
        public boolean addAllChildrenSorted(int groupIndex, @NonNull Collection<?> children) {
            return false;
        }

        @Override
        public boolean addAllChildrenSorted(int groupIndex, @NonNull Collection<?> children,
                                            @Nullable Comparator<Object> comparator) {
            return false;
        }

        @Override
        public boolean addAllChildrenSorted(@NonNull Object group, @NonNull Object... children) {
            return false;
        }

        @Override
        public boolean addAllChildrenSorted(@Nullable Comparator<Object> comparator,
                                            @NonNull Object group, @NonNull Object... children) {
            return false;
        }

        @Override
        public boolean addAllChildrenSorted(int groupIndex, @NonNull Object... children) {
            return false;
        }

        @Override
        public boolean addAllChildrenSorted(@Nullable Comparator<Object> comparator, int groupIndex,
                                            @NonNull Object... children) {
            return false;
        }

        @Override
        public Order getChildOrder() {
            return null;
        }

        @Override
        public Order getChildOrder(@NonNull Object group) {
            return null;
        }

        @Override
        public Order getChildOrder(int groupIndex) {
            return null;
        }

        @Override
        public void addSortingListener(
                @NonNull ExpandableListSortingListener<Object, Object> listener) {

        }

        @Override
        public void removeSortingListener(
                @NonNull ExpandableListSortingListener<Object, Object> listener) {

        }
    }

    /**
     * An implementation of the abstract class {@link ExpandableListDecorator}, which is needed for
     * test purposes.
     */
    private class ExpandableListDecoratorImplementation
            extends ExpandableListDecorator<Object, Object> {

        @SuppressWarnings("ConstantConditions")
        @Override
        @NonNull
        public View onInflateGroupView(@NonNull final LayoutInflater inflater,
                                       @Nullable final ViewGroup parent, final int groupType) {
            return null;
        }

        @Override
        public void onShowGroup(@NonNull final Context context,
                                @NonNull final ExpandableListAdapter<Object, Object> adapter,
                                @NonNull final View view, @NonNull final Object group,
                                final int viewType, final int index, final boolean expanded,
                                final boolean enabled, final int state, final boolean filtered) {

        }

        @SuppressWarnings("ConstantConditions")
        @Override
        @NonNull
        public View onInflateChildView(@NonNull final LayoutInflater inflater,
                                       @Nullable final ViewGroup parent, final int childType) {
            return null;
        }

        @Override
        public void onShowChild(@NonNull final Context context,
                                @NonNull final ExpandableListAdapter<Object, Object> adapter,
                                @NonNull final View view, @NonNull final Object child,
                                final int viewType, final int childIndex,
                                @NonNull final Object group, final int groupIndex,
                                final boolean enabled, final int state, final boolean filtered) {

        }

    }

    /**
     * An implementation of the interface {@link Serializable}, which is needed for test purposes.
     */
    private class SerializableImplementation implements Serializable {

        /**
         * The constant serial version UID.
         */
        private static final long serialVersionUID = 1L;

        /**
         * A value, which is needed for test purposes.
         */
        private int value;

        /**
         * Returns the outer type of this inner class.
         *
         * @return The outer type
         */
        private AbstractExpandableListAdapterTest getOuterType() {
            return AbstractExpandableListAdapterTest.this;
        }

        /**
         * Creates a new serializable implementation.
         *
         * @param value
         *         A value, which is needed for test purposes
         */
        public SerializableImplementation(final int value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + value;
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            SerializableImplementation other = (SerializableImplementation) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (value != other.value)
                return false;
            return true;
        }

        @Override
        public final Serializable clone() {
            return new SerializableImplementation(value);
        }

    }

    /**
     * Creates and returns an instance of the class {@link AbstractExpandableListAdapterImplementation},
     * which can be used for test purposes.
     *
     * @return The instance, which has been created
     */
    private AbstractExpandableListAdapterImplementation createAdapter() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapter<Group<Object, Object>> groupAdapter =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new NullObjectDecorator<Group<Object, Object>>());
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter =
                new AbstractExpandableListAdapterImplementation(context,
                        new ExpandableListDecoratorImplementation(), LogLevel.ALL, groupAdapter,
                        false, true, true,
                        new ListenerList<ExpandableListAdapterItemClickListener<Object, Object>>(),
                        new ListenerList<ExpandableListAdapterItemLongClickListener<Object, Object>>(),
                        new ListenerList<ExpandableListAdapterListener<Object, Object>>(),
                        new ListenerList<ExpansionListener<Object, Object>>());
        return abstractExpandableListAdapter;
    }

    @Test
    public final void testCloneGroupAdapter() throws CloneNotSupportedException {
        Object group1 = new SerializableImplementation(1);
        Object group2 = new SerializableImplementation(2);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(group1);
        abstractExpandableListAdapter.addGroup(group2);
        MultipleChoiceListAdapter<Group<Object, Object>> clonedGroupAdapter =
                abstractExpandableListAdapter.cloneGroupAdapter();
        assertEquals(group1, clonedGroupAdapter.getItem(0).getData());
        assertEquals(group2, clonedGroupAdapter.getItem(1).getData());
    }

    @Test
    public final void testConstructor() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        ExpandableListDecorator<Object, Object> decorator =
                new ExpandableListDecoratorImplementation();
        LogLevel logLevel = LogLevel.ERROR;
        MultipleChoiceListAdapter<Group<Object, Object>> groupAdapter =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new NullObjectDecorator<Group<Object, Object>>());
        boolean allowDuplicateChildren = false;
        boolean notifyOnChange = true;
        boolean triggerGroupExpansionOnClick = true;
        ListenerList<ExpandableListAdapterItemClickListener<Object, Object>> itemClickListeners =
                new ListenerList<>();
        ListenerList<ExpandableListAdapterItemLongClickListener<Object, Object>>
                itemLongClickListeners = new ListenerList<>();
        ListenerList<ExpandableListAdapterListener<Object, Object>> adapterListeners =
                new ListenerList<>();
        ListenerList<ExpansionListener<Object, Object>> expansionListeners = new ListenerList<>();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter =
                new AbstractExpandableListAdapterImplementation(context, decorator, logLevel,
                        groupAdapter, allowDuplicateChildren, notifyOnChange,
                        triggerGroupExpansionOnClick, itemClickListeners, itemLongClickListeners,
                        adapterListeners, expansionListeners);
        assertEquals(context, abstractExpandableListAdapter.getContext());
        assertEquals(decorator, abstractExpandableListAdapter.getDecorator());
        assertEquals(logLevel, abstractExpandableListAdapter.getLogLevel());
        assertEquals(groupAdapter, abstractExpandableListAdapter.getGroupAdapter());
        assertEquals(allowDuplicateChildren,
                abstractExpandableListAdapter.areDuplicateChildrenAllowed());
        assertEquals(notifyOnChange, abstractExpandableListAdapter.isNotifiedOnChange());
        assertEquals(triggerGroupExpansionOnClick,
                abstractExpandableListAdapter.isGroupExpansionTriggeredOnClick());
        assertEquals(itemClickListeners, abstractExpandableListAdapter.getItemClickListeners());
        assertEquals(itemLongClickListeners,
                abstractExpandableListAdapter.getItemLongClickListeners());
        assertEquals(adapterListeners, abstractExpandableListAdapter.getAdapterListeners());
        assertEquals(expansionListeners, abstractExpandableListAdapter.getExpansionListeners());
        assertNull(abstractExpandableListAdapter.getParameters());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionWhenContextIsNull() {
        MultipleChoiceListAdapter<Group<Object, Object>> groupAdapter =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new NullObjectDecorator<Group<Object, Object>>());
        new AbstractExpandableListAdapterImplementation(null,
                new ExpandableListDecoratorImplementation(), LogLevel.ALL, groupAdapter, false,
                true, true,
                new ListenerList<ExpandableListAdapterItemClickListener<Object, Object>>(),
                new ListenerList<ExpandableListAdapterItemLongClickListener<Object, Object>>(),
                new ListenerList<ExpandableListAdapterListener<Object, Object>>(),
                new ListenerList<ExpansionListener<Object, Object>>());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionWhenDecoratorIsNull() {
        MultipleChoiceListAdapter<Group<Object, Object>> groupAdapter =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new NullObjectDecorator<Group<Object, Object>>());
        new AbstractExpandableListAdapterImplementation(getContext(), null, LogLevel.ALL,
                groupAdapter, false, true, true,
                new ListenerList<ExpandableListAdapterItemClickListener<Object, Object>>(),
                new ListenerList<ExpandableListAdapterItemLongClickListener<Object, Object>>(),
                new ListenerList<ExpandableListAdapterListener<Object, Object>>(),
                new ListenerList<ExpansionListener<Object, Object>>());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionWhenLogLevelIsNull() {
        MultipleChoiceListAdapter<Group<Object, Object>> groupAdapter =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new NullObjectDecorator<Group<Object, Object>>());
        new AbstractExpandableListAdapterImplementation(getContext(),
                new ExpandableListDecoratorImplementation(), null, groupAdapter, false, true, true,
                new ListenerList<ExpandableListAdapterItemClickListener<Object, Object>>(),
                new ListenerList<ExpandableListAdapterItemLongClickListener<Object, Object>>(),
                new ListenerList<ExpandableListAdapterListener<Object, Object>>(),
                new ListenerList<ExpansionListener<Object, Object>>());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionWhenGroupAdapterIsNull() {
        new AbstractExpandableListAdapterImplementation(getContext(),
                new ExpandableListDecoratorImplementation(), LogLevel.ALL, null, false, true, true,
                new ListenerList<ExpandableListAdapterItemClickListener<Object, Object>>(),
                new ListenerList<ExpandableListAdapterItemLongClickListener<Object, Object>>(),
                new ListenerList<ExpandableListAdapterListener<Object, Object>>(),
                new ListenerList<ExpansionListener<Object, Object>>());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionWhenItemClickListenersIsNull() {
        MultipleChoiceListAdapter<Group<Object, Object>> groupAdapter =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new NullObjectDecorator<Group<Object, Object>>());
        new AbstractExpandableListAdapterImplementation(getContext(),
                new ExpandableListDecoratorImplementation(), null, groupAdapter, false, true, true,
                null,
                new ListenerList<ExpandableListAdapterItemLongClickListener<Object, Object>>(),
                new ListenerList<ExpandableListAdapterListener<Object, Object>>(),
                new ListenerList<ExpansionListener<Object, Object>>());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionWhenItemLongClickListenersIsNull() {
        MultipleChoiceListAdapter<Group<Object, Object>> groupAdapter =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new NullObjectDecorator<Group<Object, Object>>());
        new AbstractExpandableListAdapterImplementation(getContext(),
                new ExpandableListDecoratorImplementation(), null, groupAdapter, false, true, true,
                new ListenerList<ExpandableListAdapterItemClickListener<Object, Object>>(), null,
                new ListenerList<ExpandableListAdapterListener<Object, Object>>(),
                new ListenerList<ExpansionListener<Object, Object>>());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionWhenAdapterListenersIsNull() {
        MultipleChoiceListAdapter<Group<Object, Object>> groupAdapter =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new NullObjectDecorator<Group<Object, Object>>());
        new AbstractExpandableListAdapterImplementation(getContext(),
                new ExpandableListDecoratorImplementation(), null, groupAdapter, false, true, true,
                new ListenerList<ExpandableListAdapterItemClickListener<Object, Object>>(),
                new ListenerList<ExpandableListAdapterItemLongClickListener<Object, Object>>(),
                null, new ListenerList<ExpansionListener<Object, Object>>());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionWhenExpansionListenersIsNull() {
        MultipleChoiceListAdapter<Group<Object, Object>> groupAdapter =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new NullObjectDecorator<Group<Object, Object>>());
        new AbstractExpandableListAdapterImplementation(getContext(),
                new ExpandableListDecoratorImplementation(), null, groupAdapter, false, true, true,
                new ListenerList<ExpandableListAdapterItemClickListener<Object, Object>>(),
                new ListenerList<ExpandableListAdapterItemLongClickListener<Object, Object>>(),
                new ListenerList<ExpandableListAdapterListener<Object, Object>>(), null);
    }

    @Test
    public final void testSetLogLevel() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        LogLevel logLevel = LogLevel.ERROR;
        abstractExpandableListAdapter.setLogLevel(logLevel);
        assertEquals(logLevel, abstractExpandableListAdapter.getLogLevel());
    }

    @Test
    public final void testSetParameters() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        Bundle parameters = new Bundle();
        abstractExpandableListAdapter.setParameters(parameters);
        assertEquals(parameters, abstractExpandableListAdapter.getParameters());
    }

    @Test
    public final void testAllowDuplicateGroups() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        boolean allowDuplicateGroups = true;
        abstractExpandableListAdapter.allowDuplicateGroups(allowDuplicateGroups);
        assertEquals(allowDuplicateGroups,
                abstractExpandableListAdapter.areDuplicateGroupsAllowed());
    }

    @Test
    public final void testAllowDuplicateChildren() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        boolean allowDuplicateChildren = true;
        abstractExpandableListAdapter.allowDuplicateChildren(allowDuplicateChildren);
        assertEquals(allowDuplicateChildren,
                abstractExpandableListAdapter.areDuplicateChildrenAllowed());
    }

    @Test
    public final void testNotifyOnChange() {
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.notifyObserversOnDataSetChanged();
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
        dataSetObserver.reset();
        boolean notifyOnChange = false;
        abstractExpandableListAdapter.notifyOnChange(notifyOnChange);
        assertEquals(notifyOnChange, abstractExpandableListAdapter.isNotifiedOnChange());
        abstractExpandableListAdapter.notifyObserversOnDataSetChanged();
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testRemoveItemClickListenerThrowsExceptionWhenListenerIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.removeItemClickListener(null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAdapterListener() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        assertTrue(abstractExpandableListAdapter.getAdapterListeners().isEmpty());
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        assertEquals(1, abstractExpandableListAdapter.getAdapterListeners().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddAdapterListenerThrowsExceptionWhenListenerIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addAdapterListener(null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemoveAdapterListener() {
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.removeAdapterListener(adapterListener);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        assertFalse(abstractExpandableListAdapter.getAdapterListeners().isEmpty());
        abstractExpandableListAdapter.removeAdapterListener(adapterListener);
        assertTrue(abstractExpandableListAdapter.getAdapterListeners().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testRemoveAdapterListenerThrowsExceptionIfListenerIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.removeAdapterListener(null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddExpansionListener() {
        ExpansionListener<Object, Object> expansionListener = mock(ExpansionListener.class);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        assertTrue(abstractExpandableListAdapter.getExpansionListeners().isEmpty());
        abstractExpandableListAdapter.addExpansionListener(expansionListener);
        abstractExpandableListAdapter.addExpansionListener(expansionListener);
        assertEquals(1, abstractExpandableListAdapter.getExpansionListeners().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddExpansionListenerThrowsExceptionWhenListenerIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addExpansionListener(null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemoveExpansionListener() {
        ExpansionListener<Object, Object> expansionListener = mock(ExpansionListener.class);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.removeExpansionListener(expansionListener);
        abstractExpandableListAdapter.addExpansionListener(expansionListener);
        assertFalse(abstractExpandableListAdapter.getExpansionListeners().isEmpty());
        abstractExpandableListAdapter.removeExpansionListener(expansionListener);
        assertTrue(abstractExpandableListAdapter.getExpansionListeners().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testRemoveExpansionListenerThrowsExceptionWhenListenerIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.removeExpansionListener(null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddGroup() {
        Object group = new Object();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        int index = abstractExpandableListAdapter.addGroup(group);
        assertEquals(0, index);
        assertTrue(abstractExpandableListAdapter.containsGroup(group));
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddGroupThrowsExceptionWhenGroupIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddDuplicateGroup() {
        Object group = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group);
        dataSetObserver.reset();
        int index = abstractExpandableListAdapter.addGroup(group);
        assertEquals(-1, index);
        assertTrue(abstractExpandableListAdapter.containsGroup(group));
        assertEquals(1, abstractExpandableListAdapter.getGroupCount());
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group, 0);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddGroupAtSpecificIndex() {
        Object group1 = new Object();
        Object group2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group1);
        dataSetObserver.reset();
        boolean added = abstractExpandableListAdapter.addGroup(0, group2);
        assertTrue(added);
        assertTrue(abstractExpandableListAdapter.containsGroup(group2));
        assertEquals(2, abstractExpandableListAdapter.getGroupCount());
        assertEquals(0, abstractExpandableListAdapter.indexOfGroup(group2));
        assertEquals(1, abstractExpandableListAdapter.indexOfGroup(group1));
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group1, 0);
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group2, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddGroupAtSpecificIndexThrowsExceptionWhenGroupIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(0, null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testAddGroupAtSpecificIndexThrowsExceptionWhenIndexIsInvalid() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(-1, new Object());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddDuplicateGroupAtSpecificIndex() {
        Object group = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group);
        dataSetObserver.reset();
        boolean added = abstractExpandableListAdapter.addGroup(0, group);
        assertFalse(added);
        assertTrue(abstractExpandableListAdapter.containsGroup(group));
        assertEquals(1, abstractExpandableListAdapter.getGroupCount());
        assertEquals(0, abstractExpandableListAdapter.indexOfGroup(group));
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group, 0);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllGroupsFromCollection() {
        Object group1 = new Object();
        Object group2 = new Object();
        Collection<Object> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);
        DataSetObserver dataSetObserver = new DataSetObserver();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        dataSetObserver.reset();
        boolean added = abstractExpandableListAdapter.addAllGroups(groups);
        assertTrue(added);
        assertTrue(abstractExpandableListAdapter.containsGroup(group1));
        assertTrue(abstractExpandableListAdapter.containsGroup(group2));
        assertEquals(2, abstractExpandableListAdapter.getGroupCount());
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group1, 0);
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddAllGroupsFromCollectionThrowsExceptionWhenCollectionIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        Collection<Object> groups = null;
        abstractExpandableListAdapter.addAllGroups(groups);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllGroupsFromCollectionContainingDuplicates() {
        Object group = new Object();
        Collection<Object> groups = new ArrayList<>();
        groups.add(group);
        groups.add(group);
        DataSetObserver dataSetObserver = new DataSetObserver();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        dataSetObserver.reset();
        boolean added = abstractExpandableListAdapter.addAllGroups(groups);
        assertFalse(added);
        assertTrue(abstractExpandableListAdapter.containsGroup(group));
        assertEquals(1, abstractExpandableListAdapter.getGroupCount());
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllGroupsFromCollectionAtSpecificIndex() {
        Object group1 = new Object();
        Object group2 = new Object();
        Object group3 = new Object();
        Collection<Object> groups = new ArrayList<>();
        groups.add(group2);
        groups.add(group3);
        DataSetObserver dataSetObserver = new DataSetObserver();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group1);
        dataSetObserver.reset();
        boolean added = abstractExpandableListAdapter.addAllGroups(0, groups);
        assertTrue(added);
        assertTrue(abstractExpandableListAdapter.containsGroup(group1));
        assertTrue(abstractExpandableListAdapter.containsGroup(group2));
        assertTrue(abstractExpandableListAdapter.containsGroup(group3));
        assertEquals(3, abstractExpandableListAdapter.getGroupCount());
        assertEquals(0, abstractExpandableListAdapter.indexOfGroup(group2));
        assertEquals(1, abstractExpandableListAdapter.indexOfGroup(group3));
        assertEquals(2, abstractExpandableListAdapter.indexOfGroup(group1));
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group1, 0);
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group2, 0);
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group3, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllGroupsFromCollectionContainingDuplicatesAtSpecificIndex() {
        Object group1 = new Object();
        Object group2 = new Object();
        Collection<Object> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);
        DataSetObserver dataSetObserver = new DataSetObserver();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group1);
        dataSetObserver.reset();
        boolean added = abstractExpandableListAdapter.addAllGroups(0, groups);
        assertFalse(added);
        assertTrue(abstractExpandableListAdapter.containsGroup(group1));
        assertTrue(abstractExpandableListAdapter.containsGroup(group2));
        assertEquals(2, abstractExpandableListAdapter.getGroupCount());
        assertEquals(0, abstractExpandableListAdapter.indexOfGroup(group2));
        assertEquals(1, abstractExpandableListAdapter.indexOfGroup(group1));
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group1, 0);
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group2, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddAllGroupsFromCollectionAtSpecificIndexThrowsExceptionWhenCollectionIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        Collection<Object> groups = null;
        abstractExpandableListAdapter.addAllGroups(0, groups);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testAddAllGroupsFromCollectionAtSpecificIndexThrowsExceptionWhenIndexIsInvalid() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        Collection<Object> groups = new ArrayList<>();
        groups.add(new Object());
        abstractExpandableListAdapter.addAllGroups(-1, groups);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllGroupsFromArray() {
        Object group1 = new Object();
        Object group2 = new Object();
        Object[] groups = new Object[2];
        groups[0] = group1;
        groups[1] = group2;
        DataSetObserver dataSetObserver = new DataSetObserver();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        dataSetObserver.reset();
        boolean added = abstractExpandableListAdapter.addAllGroups(groups);
        assertTrue(added);
        assertTrue(abstractExpandableListAdapter.containsGroup(group1));
        assertTrue(abstractExpandableListAdapter.containsGroup(group2));
        assertEquals(2, abstractExpandableListAdapter.getGroupCount());
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group1, 0);
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddAllGroupsFromArrayThrowsExceptionWhenArrayIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        Object[] groups = null;
        abstractExpandableListAdapter.addAllGroups(groups);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllGroupsFromArrayContainingDuplicates() {
        Object group = new Object();
        Object[] groups = new Object[2];
        groups[0] = group;
        groups[1] = group;
        DataSetObserver dataSetObserver = new DataSetObserver();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        dataSetObserver.reset();
        boolean added = abstractExpandableListAdapter.addAllGroups(groups);
        assertFalse(added);
        assertTrue(abstractExpandableListAdapter.containsGroup(group));
        assertEquals(1, abstractExpandableListAdapter.getGroupCount());
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllGroupsFromArrayAtSpecificIndex() {
        Object group1 = new Object();
        Object group2 = new Object();
        Object group3 = new Object();
        Object[] groups = new Object[2];
        groups[0] = group2;
        groups[1] = group3;
        DataSetObserver dataSetObserver = new DataSetObserver();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group1);
        dataSetObserver.reset();
        boolean added = abstractExpandableListAdapter.addAllGroups(0, groups);
        assertTrue(added);
        assertTrue(abstractExpandableListAdapter.containsGroup(group1));
        assertTrue(abstractExpandableListAdapter.containsGroup(group2));
        assertTrue(abstractExpandableListAdapter.containsGroup(group3));
        assertEquals(3, abstractExpandableListAdapter.getGroupCount());
        assertEquals(0, abstractExpandableListAdapter.indexOfGroup(group2));
        assertEquals(1, abstractExpandableListAdapter.indexOfGroup(group3));
        assertEquals(2, abstractExpandableListAdapter.indexOfGroup(group1));
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group1, 0);
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group2, 0);
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group3, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllGroupsFromArrayContainingDuplicatesAtSpecificIndex() {
        Object group1 = new Object();
        Object group2 = new Object();
        Object[] groups = new Object[2];
        groups[0] = group1;
        groups[1] = group2;
        DataSetObserver dataSetObserver = new DataSetObserver();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group1);
        dataSetObserver.reset();
        boolean added = abstractExpandableListAdapter.addAllGroups(0, groups);
        assertFalse(added);
        assertTrue(abstractExpandableListAdapter.containsGroup(group1));
        assertTrue(abstractExpandableListAdapter.containsGroup(group2));
        assertEquals(2, abstractExpandableListAdapter.getGroupCount());
        assertEquals(0, abstractExpandableListAdapter.indexOfGroup(group2));
        assertEquals(1, abstractExpandableListAdapter.indexOfGroup(group1));
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group1, 0);
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group2, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddAllGroupsFromArrayAtSpecificIndexThrowsExceptionWhenCollectionIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        Object[] groups = null;
        abstractExpandableListAdapter.addAllGroups(0, groups);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testAddAllGroupsFromArrayAtSpecificIndexThrowsExceptionWhenIndexIsInvalid() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        Object[] groups = new Object[1];
        groups[0] = new Object();
        abstractExpandableListAdapter.addAllGroups(-1, groups);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testReplaceGroup() {
        Object group1 = new Object();
        Object group2 = new Object();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addGroup(group1);
        dataSetObserver.reset();
        Object replacedGroup = abstractExpandableListAdapter.replaceGroup(0, group2);
        assertEquals(group1, replacedGroup);
        assertEquals(1, abstractExpandableListAdapter.getGroupCount());
        assertTrue(abstractExpandableListAdapter.containsGroup(group2));
        verify(adapterListener, times(1)).onGroupAdded(abstractExpandableListAdapter, group2, 0);
        verify(adapterListener, times(1)).onGroupRemoved(abstractExpandableListAdapter, group1, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testReplaceGroupThrowsExceptionWhenGroupIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.replaceGroup(0, null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testReplaceGroupThrowsExceptionWhenIndexIsInvalid() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.replaceGroup(-1, new Object());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemoveGroupAtSpecificIndex() {
        Object group = new Object();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addGroup(group);
        Object removedGroup = abstractExpandableListAdapter.removeGroup(0);
        assertEquals(group, removedGroup);
        assertTrue(abstractExpandableListAdapter.isEmpty());
        verify(adapterListener, times(1)).onGroupRemoved(abstractExpandableListAdapter, group, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testRemoveGroupAtSpecificIndexThrowsExceptionWhenIndexIsInvalid() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.removeGroup(-1);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemoveGroupWhenAdapterDoesContainGroup() {
        Object group = new Object();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addGroup(group);
        dataSetObserver.reset();
        boolean removed = abstractExpandableListAdapter.removeGroup(group);
        assertTrue(removed);
        assertTrue(abstractExpandableListAdapter.isEmpty());
        verify(adapterListener, times(1)).onGroupRemoved(abstractExpandableListAdapter, group, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemoveGroupWhenAdapterDoesNotContainGroup() {
        Object group = new Object();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addGroup(new Object());
        dataSetObserver.reset();
        boolean removed = abstractExpandableListAdapter.removeGroup(group);
        assertFalse(removed);
        assertFalse(abstractExpandableListAdapter.isEmpty());
        verify(adapterListener, times(0)).onGroupRemoved(abstractExpandableListAdapter, group, 0);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testRemoveGroupThrowsExceptionWhenItemIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.removeGroup(null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemoveAllGroupsFromCollectionWhenAdapterDoesContainAllGroups() {
        Object group1 = new Object();
        Object group2 = new Object();
        Collection<Object> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAllGroups(groups);
        dataSetObserver.reset();
        boolean removed = abstractExpandableListAdapter.removeAllGroups(groups);
        assertTrue(removed);
        assertTrue(abstractExpandableListAdapter.isEmpty());
        verify(adapterListener, times(1)).onGroupRemoved(abstractExpandableListAdapter, group1, 0);
        verify(adapterListener, times(1)).onGroupRemoved(abstractExpandableListAdapter, group2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemoveAllGroupsFromCollectionWhenAdapterDoesNotContainAllGroups() {
        Object group1 = new Object();
        Object group2 = new Object();
        Collection<Object> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addGroup(group1);
        dataSetObserver.reset();
        boolean removed = abstractExpandableListAdapter.removeAllGroups(groups);
        assertFalse(removed);
        assertTrue(abstractExpandableListAdapter.isEmpty());
        verify(adapterListener, times(1)).onGroupRemoved(abstractExpandableListAdapter, group1, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testRemoveAllGroupsFromCollectionThrowsExceptionWhenCollectionIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        Collection<Object> groups = null;
        abstractExpandableListAdapter.removeAllGroups(groups);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemoveAllGroupsFromArrayWhenAdapterDoesContainAllGroups() {
        Object group1 = new Object();
        Object group2 = new Object();
        Object[] groups = new Object[2];
        groups[0] = group1;
        groups[1] = group2;
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAllGroups(groups);
        dataSetObserver.reset();
        boolean removed = abstractExpandableListAdapter.removeAllGroups(groups);
        assertTrue(removed);
        assertTrue(abstractExpandableListAdapter.isEmpty());
        verify(adapterListener, times(1)).onGroupRemoved(abstractExpandableListAdapter, group1, 0);
        verify(adapterListener, times(1)).onGroupRemoved(abstractExpandableListAdapter, group2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemoveAllGroupsFromArrayWhenAdapterDoesNotContainAllGroups() {
        Object group1 = new Object();
        Object group2 = new Object();
        Object[] groups = new Object[2];
        groups[0] = group1;
        groups[1] = group2;
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addGroup(group1);
        dataSetObserver.reset();
        boolean removed = abstractExpandableListAdapter.removeAllGroups(groups);
        assertFalse(removed);
        assertTrue(abstractExpandableListAdapter.isEmpty());
        verify(adapterListener, times(1)).onGroupRemoved(abstractExpandableListAdapter, group1, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testRemoveAllGroupsFromArrayThrowsExceptionWhenArrayIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        Object[] groups = null;
        abstractExpandableListAdapter.removeAllGroups(groups);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRetainAllGroupsFromCollection() {
        Object group1 = new Object();
        Object group2 = new Object();
        Object group3 = new Object();
        Collection<Object> groups = new ArrayList<>();
        groups.add(group2);
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addGroup(group1);
        abstractExpandableListAdapter.addGroup(group2);
        abstractExpandableListAdapter.addGroup(group3);
        dataSetObserver.reset();
        abstractExpandableListAdapter.retainAllGroups(groups);
        assertEquals(1, abstractExpandableListAdapter.getGroupCount());
        assertTrue(abstractExpandableListAdapter.containsGroup(group2));
        verify(adapterListener, times(1)).onGroupRemoved(abstractExpandableListAdapter, group1, 0);
        verify(adapterListener, times(1)).onGroupRemoved(abstractExpandableListAdapter, group3, 2);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testRetainAllGroupsFromCollectionThrowsExceptionWhenCollectionIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        Collection<Object> groups = null;
        abstractExpandableListAdapter.retainAllGroups(groups);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRetainAllGroupsFromArray() {
        Object group1 = new Object();
        Object group2 = new Object();
        Object group3 = new Object();
        Object[] groups = new Object[1];
        groups[0] = group2;
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addGroup(group1);
        abstractExpandableListAdapter.addGroup(group2);
        abstractExpandableListAdapter.addGroup(group3);
        dataSetObserver.reset();
        abstractExpandableListAdapter.retainAllGroups(groups);
        assertEquals(1, abstractExpandableListAdapter.getGroupCount());
        assertTrue(abstractExpandableListAdapter.containsGroup(group2));
        verify(adapterListener, times(1)).onGroupRemoved(abstractExpandableListAdapter, group1, 0);
        verify(adapterListener, times(1)).onGroupRemoved(abstractExpandableListAdapter, group3, 2);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testRetainAllGroupsFromArrayThrowsExceptionWhenArrayIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        Object[] groups = null;
        abstractExpandableListAdapter.retainAllGroups(groups);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testClearGroups() {
        Object group1 = new Object();
        Object group2 = new Object();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addGroup(group1);
        abstractExpandableListAdapter.addGroup(group2);
        dataSetObserver.reset();
        abstractExpandableListAdapter.clearGroups();
        assertTrue(abstractExpandableListAdapter.isEmpty());
        verify(adapterListener, times(1)).onGroupRemoved(abstractExpandableListAdapter, group1, 0);
        verify(adapterListener, times(1)).onGroupRemoved(abstractExpandableListAdapter, group2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test
    public final void testGroupIterator() {
        Object group1 = new Object();
        Object group2 = new Object();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(group1);
        abstractExpandableListAdapter.addGroup(group2);
        Iterator<Object> iterator = abstractExpandableListAdapter.groupIterator();
        assertEquals(group1, iterator.next());
        assertEquals(group2, iterator.next());
    }

    @Test
    public final void testGroupListIterator() {
        Object group1 = new Object();
        Object group2 = new Object();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(group1);
        abstractExpandableListAdapter.addGroup(group2);
        ListIterator<Object> listIterator = abstractExpandableListAdapter.groupListIterator();
        assertEquals(group1, listIterator.next());
        assertEquals(group2, listIterator.next());
    }

    @Test
    public final void testListIteratorWithSpecificStartIndex() {
        Object group1 = new Object();
        Object group2 = new Object();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(group1);
        abstractExpandableListAdapter.addGroup(group2);
        ListIterator<Object> listIterator = abstractExpandableListAdapter.groupListIterator(1);
        assertEquals(group2, listIterator.next());
    }

    @Test
    public final void testSubListGroups() {
        Object group1 = new Object();
        Object group2 = new Object();
        Object group3 = new Object();
        Object group4 = new Object();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(group1);
        abstractExpandableListAdapter.addGroup(group2);
        abstractExpandableListAdapter.addGroup(group3);
        abstractExpandableListAdapter.addGroup(group4);
        Collection<Object> subList = abstractExpandableListAdapter.subListGroups(1, 3);
        assertEquals(2, subList.size());
        Iterator<Object> iterator = subList.iterator();
        assertEquals(group2, iterator.next());
        assertEquals(group3, iterator.next());
    }

    @Test
    public final void testGroupsToArray() {
        Object group1 = new Object();
        Object group2 = new Object();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(group1);
        abstractExpandableListAdapter.addGroup(group2);
        Object[] groups = abstractExpandableListAdapter.groupsToArray();
        assertEquals(2, groups.length);
        assertEquals(group1, groups[0]);
        assertEquals(group2, groups[1]);
    }

    @Test
    public final void testGroupsToArrayWithArrayParameter() {
        Object group1 = new Object();
        Object group2 = new Object();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(group1);
        abstractExpandableListAdapter.addGroup(group2);
        Object[] groups = abstractExpandableListAdapter.groupsToArray(new Object[2]);
        assertEquals(2, groups.length);
        assertEquals(group1, groups[0]);
        assertEquals(group2, groups[1]);
    }

    @Test
    public final void testIndexOfGroupWhenAdapterDoesContainGroup() {
        Object group1 = new Object();
        Object group2 = new Object();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(group1);
        abstractExpandableListAdapter.addGroup(group2);
        assertEquals(1, abstractExpandableListAdapter.indexOfGroup(group2));
    }

    @Test
    public final void testIndexOfGroupWhenAdapterDoesNotContainGroup() {
        Object group1 = new Object();
        Object group2 = new Object();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(group1);
        assertEquals(-1, abstractExpandableListAdapter.indexOfGroup(group2));
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testIndexOfGroupThrowsExceptionWhenGroupIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.indexOfGroup(null);
    }

    @Test
    public final void testContainsGroupWhenAdapterDoesContainGroup() {
        Object group = new Object();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(group);
        assertTrue(abstractExpandableListAdapter.containsGroup(group));
    }

    @Test
    public final void testContainsGroupWhenAdapterDoesNotContainGroup() {
        Object group = new Object();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        assertFalse(abstractExpandableListAdapter.containsGroup(group));
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testContainsGroupThrowsExceptionWhenGroupIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.containsGroup(null);
    }

    @Test
    public final void testContainsAllGroupsFromCollectionWhenAdapterDoesContainAllGroups() {
        Object group1 = new Object();
        Object group2 = new Object();
        Collection<Object> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addAllGroups(groups);
        assertTrue(abstractExpandableListAdapter.containsAllGroups(groups));
    }

    @Test
    public final void testContainsAllGroupsFromCollectionWhenAdapterDoesNotContainAllGroups() {
        Object group1 = new Object();
        Object group2 = new Object();
        Collection<Object> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(group1);
        assertFalse(abstractExpandableListAdapter.containsAllGroups(groups));
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testContainsAllItemsFromCollectionThrowsExceptionWhenCollectionIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        Collection<Object> groups = null;
        abstractExpandableListAdapter.containsAllGroups(groups);
    }

    @Test
    public final void testContainsAllGroupsFromArrayWhenAdapterDoesContainAllGroups() {
        Object group1 = new Object();
        Object group2 = new Object();
        Object[] groups = new Object[2];
        groups[0] = group1;
        groups[1] = group2;
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addAllGroups(groups);
        assertTrue(abstractExpandableListAdapter.containsAllGroups(groups));
    }

    @Test
    public final void testContainsAllGroupsFromArrayWhenAdapterDoesNotContainAllGroups() {
        Object group1 = new Object();
        Object group2 = new Object();
        Object[] groups = new Object[2];
        groups[0] = group1;
        groups[1] = group2;
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(group1);
        assertFalse(abstractExpandableListAdapter.containsAllGroups(groups));
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testContainsAllItemsFromArrayThrowsExceptionWhenArrayIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        Object[] groups = null;
        abstractExpandableListAdapter.containsAllGroups(groups);
    }

    @Test
    public final void testGetGroupCountWhenAdapterIsEmpty() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        assertEquals(0, abstractExpandableListAdapter.getGroupCount());
    }

    @Test
    public final void testGetGroupCountWhenAdapterIsNotEmpty() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(new Object());
        assertEquals(1, abstractExpandableListAdapter.getGroupCount());
    }

    @Test
    public final void testGetGroup() {
        Object group = new Object();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(group);
        assertEquals(group, abstractExpandableListAdapter.getGroup(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testGetGroupThrowsExceptionWhenIndexIsInvalid() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.getGroup(-1);
    }

    @Test
    public final void testGetAllGroups() {
        Object group1 = new Object();
        Object group2 = new Object();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(group1);
        abstractExpandableListAdapter.addGroup(group2);
        Collection<Object> groups = abstractExpandableListAdapter.getAllGroups();
        Iterator<Object> iterator = groups.iterator();
        assertEquals(group1, iterator.next());
        assertEquals(group2, iterator.next());
    }

    @Test
    public final void testIsEmptyWhenAdapterIsEmpty() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        assertTrue(abstractExpandableListAdapter.isEmpty());
    }

    @Test
    public final void testIsEmptyWhenAdapterIsNotEmpty() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(new Object());
        assertFalse(abstractExpandableListAdapter.isEmpty());
    }

    @Test
    public final void testIsGroupEmptyWhenGroupIsEmpty() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        Object group = new Object();
        abstractExpandableListAdapter.addGroup(group);
        assertTrue(abstractExpandableListAdapter.isGroupEmpty(group));
    }

    @Test
    public final void testIsGroupEmptyWhenGroupIsNotEmpty() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        Object group = new Object();
        abstractExpandableListAdapter.addGroup(group);
        abstractExpandableListAdapter.addChild(0, new Object());
        assertFalse(abstractExpandableListAdapter.isGroupEmpty(group));
    }

    @Test(expected = NoSuchElementException.class)
    public final void testIsGroupEmptyThrowsException() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.isGroupEmpty(new Object());
    }

    @Test
    public final void testIsGroupEmptyWithIndexParameterWhenGroupIsEmpty() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(new Object());
        assertTrue(abstractExpandableListAdapter.isGroupEmpty(0));
    }

    @Test
    public final void testIsGroupEmptyWithIndexParameterWhenGroupIsNotEmpty() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(new Object());
        abstractExpandableListAdapter.addChild(0, new Object());
        assertFalse(abstractExpandableListAdapter.isGroupEmpty(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testIsGroupEmptyWithIndexParameterThrowsException() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.isGroupEmpty(-1);
    }

    @Test
    public final void testAllowDuplicateChildrenWithGroupParameter() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        Object group = new Object();
        boolean allowDuplicateChildren = true;
        abstractExpandableListAdapter.addGroup(group);
        abstractExpandableListAdapter.allowDuplicateChildren(group, allowDuplicateChildren);
        assertEquals(allowDuplicateChildren,
                abstractExpandableListAdapter.areDuplicateChildrenAllowed(group));
    }

    @Test(expected = NoSuchElementException.class)
    public final void testAllowDuplicateChildrenWithGroupParameterThrowsException() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.allowDuplicateChildren(new Object(), true);
    }

    @Test
    public final void testAllowDuplicateChildrenWithIndexParameter() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(new Object());
        boolean allowDuplicateChildren = true;
        abstractExpandableListAdapter.allowDuplicateChildren(0, allowDuplicateChildren);
        assertEquals(allowDuplicateChildren,
                abstractExpandableListAdapter.areDuplicateChildrenAllowed(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testAllowDuplicateChildrenWithIndexParameterThrowsException() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.allowDuplicateChildren(-1, true);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddChildWithGroupParameter() {
        Object group = new Object();
        Object child = new Object();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group);
        dataSetObserver.reset();
        int index = abstractExpandableListAdapter.addChild(group, child);
        assertEquals(0, index);
        assertTrue(abstractExpandableListAdapter.containsChild(group, child));
        verify(adapterListener, times(1))
                .onChildAdded(abstractExpandableListAdapter, child, 0, group, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = NoSuchElementException.class)
    public final void testAddChildWithGroupParameterThrowsExceptionIfGroupDoesNotBelongToAdapter() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addChild(new Object(), new Object());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddChildWithGroupParameterThrowsExceptionIfChildIsNull() {
        Object group = new Object();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(group);
        abstractExpandableListAdapter.addChild(group, null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddDuplicateChildWithGroupParameter() {
        Object group = new Object();
        Object child = new Object();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group);
        dataSetObserver.reset();
        abstractExpandableListAdapter.addChild(group, child);
        int index = abstractExpandableListAdapter.addChild(group, child);
        assertEquals(-1, index);
        assertEquals(1, abstractExpandableListAdapter.getChildCount(group));
        assertTrue(abstractExpandableListAdapter.containsChild(group, child));
        verify(adapterListener, times(1))
                .onChildAdded(abstractExpandableListAdapter, child, 0, group, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddGlobalDuplicateChildWithGroupParameter() {
        Object group1 = new Object();
        Object group2 = new Object();
        Object child = new Object();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group1);
        abstractExpandableListAdapter.addGroup(group2);
        dataSetObserver.reset();
        abstractExpandableListAdapter.addChild(group1, child);
        int index = abstractExpandableListAdapter.addChild(group2, child);
        assertEquals(-1, index);
        assertEquals(1, abstractExpandableListAdapter.getChildCount(group1));
        assertEquals(0, abstractExpandableListAdapter.getChildCount(group2));
        assertTrue(abstractExpandableListAdapter.containsChild(child));
        assertTrue(abstractExpandableListAdapter.containsChild(group1, child));
        assertFalse(abstractExpandableListAdapter.containsChild(group2, child));
        verify(adapterListener, times(1))
                .onChildAdded(abstractExpandableListAdapter, child, 0, group1, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddChildWithGroupIndexParameter() {
        Object group = new Object();
        Object child = new Object();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group);
        dataSetObserver.reset();
        int index = abstractExpandableListAdapter.addChild(0, child);
        assertEquals(0, index);
        assertTrue(abstractExpandableListAdapter.containsChild(0, child));
        verify(adapterListener, times(1))
                .onChildAdded(abstractExpandableListAdapter, child, 0, group, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddChildWithGroupIndexParameterThrowsExceptionWhenChildIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(new Object());
        abstractExpandableListAdapter.addChild(0, null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testAddChildWithGroupIndexParameterThrowsExceptionWhenIndexIsInvalid() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addChild(0, new Object());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddDuplicateChildWithGroupIndexParameter() {
        Object group = new Object();
        Object child = new Object();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group);
        dataSetObserver.reset();
        abstractExpandableListAdapter.addChild(0, child);
        int index = abstractExpandableListAdapter.addChild(0, child);
        assertEquals(-1, index);
        assertEquals(1, abstractExpandableListAdapter.getChildCount(0));
        assertTrue(abstractExpandableListAdapter.containsChild(0, child));
        verify(adapterListener, times(1))
                .onChildAdded(abstractExpandableListAdapter, child, 0, group, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddGlobalDuplicateChildWithGroupIndexParameter() {
        Object group1 = new Object();
        Object group2 = new Object();
        Object child = new Object();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group1);
        abstractExpandableListAdapter.addGroup(group2);
        dataSetObserver.reset();
        abstractExpandableListAdapter.addChild(0, child);
        int index = abstractExpandableListAdapter.addChild(1, child);
        assertEquals(-1, index);
        assertEquals(1, abstractExpandableListAdapter.getChildCount(0));
        assertEquals(0, abstractExpandableListAdapter.getChildCount(1));
        assertTrue(abstractExpandableListAdapter.containsChild(child));
        assertTrue(abstractExpandableListAdapter.containsChild(0, child));
        assertFalse(abstractExpandableListAdapter.containsChild(1, child));
        verify(adapterListener, times(1))
                .onChildAdded(abstractExpandableListAdapter, child, 0, group1, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddChildWithGroupParameterAtSpecificIndex() {
        Object group = new Object();
        Object child1 = new Object();
        Object child2 = new Object();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group);
        abstractExpandableListAdapter.addChild(group, child1);
        dataSetObserver.reset();
        boolean added = abstractExpandableListAdapter.addChild(group, 0, child2);
        assertTrue(added);
        assertTrue(abstractExpandableListAdapter.containsChild(group, child1));
        assertTrue(abstractExpandableListAdapter.containsChild(group, child2));
        assertEquals(1, abstractExpandableListAdapter.indexOfChild(group, child1));
        assertEquals(0, abstractExpandableListAdapter.indexOfChild(group, child2));
        verify(adapterListener, times(1))
                .onChildAdded(abstractExpandableListAdapter, child2, 0, group, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddChildWithGroupParameterAtSpecificIndexThrowsExceptionIfChildIsNull() {
        Object group = new Object();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(group);
        abstractExpandableListAdapter.addChild(group, 0, null);
    }

    @Test(expected = NoSuchElementException.class)
    public final void testAddChildWithGroupParameterAtSpecificIndexThrowsExceptionIfGroupDoesNotBelongToAdapter() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addChild(new Object(), 0, new Object());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testAddChildWithGroupParameterAtSpecificIndexThrowsExceptionIfIndexIsInvalid() {
        Object group = new Object();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(group);
        abstractExpandableListAdapter.addChild(group, -1, new Object());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddDuplicateChildWithGroupParameterAtSpecificIndex() {
        Object group = new Object();
        Object child1 = new Object();
        Object child2 = new Object();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group);
        abstractExpandableListAdapter.addChild(group, child1);
        dataSetObserver.reset();
        abstractExpandableListAdapter.addChild(group, 0, child2);
        boolean added = abstractExpandableListAdapter.addChild(group, 0, child2);
        assertFalse(added);
        assertEquals(2, abstractExpandableListAdapter.getChildCount(group));
        assertTrue(abstractExpandableListAdapter.containsChild(group, child1));
        assertTrue(abstractExpandableListAdapter.containsChild(group, child2));
        assertEquals(1, abstractExpandableListAdapter.indexOfChild(group, child1));
        assertEquals(0, abstractExpandableListAdapter.indexOfChild(group, child2));
        verify(adapterListener, times(1))
                .onChildAdded(abstractExpandableListAdapter, child2, 0, group, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddGlobalDuplicateChildWithGroupParameterAtSpecificIndex() {
        Object group1 = new Object();
        Object group2 = new Object();
        Object child1 = new Object();
        Object child2 = new Object();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group1);
        abstractExpandableListAdapter.addGroup(group2);
        abstractExpandableListAdapter.addChild(group1, child1);
        dataSetObserver.reset();
        abstractExpandableListAdapter.addChild(group1, 0, child2);
        boolean added = abstractExpandableListAdapter.addChild(group2, 0, child2);
        assertFalse(added);
        assertEquals(2, abstractExpandableListAdapter.getChildCount(group1));
        assertEquals(0, abstractExpandableListAdapter.getChildCount(group2));
        assertTrue(abstractExpandableListAdapter.containsChild(group1, child1));
        assertTrue(abstractExpandableListAdapter.containsChild(group1, child2));
        assertEquals(1, abstractExpandableListAdapter.indexOfChild(group1, child1));
        assertEquals(0, abstractExpandableListAdapter.indexOfChild(group1, child2));
        verify(adapterListener, times(1))
                .onChildAdded(abstractExpandableListAdapter, child2, 0, group1, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddChildWithGroupIndexParameterAtSpecificIndex() {
        Object group = new Object();
        Object child1 = new Object();
        Object child2 = new Object();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group);
        abstractExpandableListAdapter.addChild(group, child1);
        dataSetObserver.reset();
        boolean added = abstractExpandableListAdapter.addChild(0, 0, child2);
        assertTrue(added);
        assertTrue(abstractExpandableListAdapter.containsChild(0, child1));
        assertTrue(abstractExpandableListAdapter.containsChild(0, child2));
        assertEquals(1, abstractExpandableListAdapter.indexOfChild(0, child1));
        assertEquals(0, abstractExpandableListAdapter.indexOfChild(0, child2));
        verify(adapterListener, times(1))
                .onChildAdded(abstractExpandableListAdapter, child2, 0, group, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddChildWithGroupIndexParameterAtSpecificIndexThrowsExceptionIfChildIsNull() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(new Object());
        abstractExpandableListAdapter.addChild(0, 0, null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testAddChildWithGroupIndexParameterAtSpecificIndexThrowsExceptionIfGroupIndexIsInvalid() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(new Object());
        abstractExpandableListAdapter.addChild(-1, 0, new Object());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testAddChildWithGroupIndexParameterAtSpecificIndexThrowsExceptionIfIndexIsInvalid() {
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(new Object());
        abstractExpandableListAdapter.addChild(0, -1, new Object());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddDuplicateChildWithGroupIndexParameterAtSpecificIndex() {
        Object group = new Object();
        Object child1 = new Object();
        Object child2 = new Object();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group);
        abstractExpandableListAdapter.addChild(group, child1);
        dataSetObserver.reset();
        abstractExpandableListAdapter.addChild(0, 0, child2);
        boolean added = abstractExpandableListAdapter.addChild(0, 0, child2);
        assertFalse(added);
        assertTrue(abstractExpandableListAdapter.containsChild(0, child1));
        assertTrue(abstractExpandableListAdapter.containsChild(0, child2));
        assertEquals(1, abstractExpandableListAdapter.indexOfChild(0, child1));
        assertEquals(0, abstractExpandableListAdapter.indexOfChild(0, child2));
        verify(adapterListener, times(1))
                .onChildAdded(abstractExpandableListAdapter, child2, 0, group, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddGlobalDuplicateChildWithGroupIndexParameterAtSpecificIndex() {
        Object group1 = new Object();
        Object group2 = new Object();
        Object child1 = new Object();
        Object child2 = new Object();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group1);
        abstractExpandableListAdapter.addGroup(group2);
        abstractExpandableListAdapter.addChild(group1, child1);
        dataSetObserver.reset();
        abstractExpandableListAdapter.addChild(0, 0, child2);
        boolean added = abstractExpandableListAdapter.addChild(1, 0, child2);
        assertFalse(added);
        assertTrue(abstractExpandableListAdapter.containsChild(0, child1));
        assertTrue(abstractExpandableListAdapter.containsChild(0, child2));
        assertEquals(1, abstractExpandableListAdapter.indexOfChild(0, child1));
        assertEquals(0, abstractExpandableListAdapter.indexOfChild(0, child2));
        verify(adapterListener, times(1))
                .onChildAdded(abstractExpandableListAdapter, child2, 0, group1, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllChildrenFromCollectionWithGroupParameter() {
        Object group = new Object();
        Object child1 = new Object();
        Object child2 = new Object();
        Collection<Object> children = new ArrayList<>();
        children.add(child1);
        children.add(child2);
        DataSetObserver dataSetObserver = new DataSetObserver();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group);
        dataSetObserver.reset();
        boolean added = abstractExpandableListAdapter.addAllChildren(group, children);
        assertTrue(added);
        assertTrue(abstractExpandableListAdapter.containsChild(group, child1));
        assertTrue(abstractExpandableListAdapter.containsChild(group, child2));
        assertEquals(2, abstractExpandableListAdapter.getChildCount(group));
        verify(adapterListener, times(1))
                .onChildAdded(abstractExpandableListAdapter, child1, 0, group, 0);
        verify(adapterListener, times(1))
                .onChildAdded(abstractExpandableListAdapter, child2, 1, group, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddAllChildrenFromCollectionWithGroupParameterThrowsExceptionIfCollectionIsNull() {
        Object group = new Object();
        Collection<Object> children = null;
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(group);
        abstractExpandableListAdapter.addAllChildren(group, children);
    }

    @Test(expected = NoSuchElementException.class)
    public final void testAddAllChildrenFromCollectionWithGroupParameterThrowsExceptionIfGroupDoesNotBelongToAdapter() {
        Collection<Object> children = new ArrayList<>();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addAllChildren(new Object(), children);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllChildrenFromCollectionContainingDuplicatesWithGroupParameter() {
        Object group = new Object();
        Object child = new Object();
        Collection<Object> children = new ArrayList<>();
        children.add(child);
        children.add(child);
        DataSetObserver dataSetObserver = new DataSetObserver();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group);
        dataSetObserver.reset();
        boolean added = abstractExpandableListAdapter.addAllChildren(group, children);
        assertFalse(added);
        assertTrue(abstractExpandableListAdapter.containsChild(group, child));
        assertEquals(1, abstractExpandableListAdapter.getChildCount(group));
        verify(adapterListener, times(1))
                .onChildAdded(abstractExpandableListAdapter, child, 0, group, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllChildrenFromCollectionWithGroupIndexParameter() {
        Object group = new Object();
        Object child1 = new Object();
        Object child2 = new Object();
        Collection<Object> children = new ArrayList<>();
        children.add(child1);
        children.add(child2);
        DataSetObserver dataSetObserver = new DataSetObserver();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group);
        dataSetObserver.reset();
        boolean added = abstractExpandableListAdapter.addAllChildren(0, children);
        assertTrue(added);
        assertTrue(abstractExpandableListAdapter.containsChild(0, child1));
        assertTrue(abstractExpandableListAdapter.containsChild(0, child2));
        assertEquals(2, abstractExpandableListAdapter.getChildCount(0));
        verify(adapterListener, times(1))
                .onChildAdded(abstractExpandableListAdapter, child1, 0, group, 0);
        verify(adapterListener, times(1))
                .onChildAdded(abstractExpandableListAdapter, child2, 1, group, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddAllChildrenFromCollectionWithGroupIndexParameterThrowsExceptionIfCollectionIsNull() {
        Collection<Object> children = null;
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(new Object());
        abstractExpandableListAdapter.addAllChildren(0, children);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testAddAllChildrenFromCollectionWithGroupIndexParameterThrowsExceptionIfIndexIsInvalid() {
        Collection<Object> children = new ArrayList<>();
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.addGroup(new Object());
        abstractExpandableListAdapter.addAllChildren(-1, children);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllChildrenFromCollectionContainingDuplicatesWithGroupIndexParameter() {
        Object group = new Object();
        Object child = new Object();
        Collection<Object> children = new ArrayList<>();
        children.add(child);
        children.add(child);
        DataSetObserver dataSetObserver = new DataSetObserver();
        ExpandableListAdapterListener<Object, Object> adapterListener =
                mock(ExpandableListAdapterListener.class);
        AbstractExpandableListAdapterImplementation abstractExpandableListAdapter = createAdapter();
        abstractExpandableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractExpandableListAdapter.addAdapterListener(adapterListener);
        abstractExpandableListAdapter.addGroup(group);
        dataSetObserver.reset();
        boolean added = abstractExpandableListAdapter.addAllChildren(0, children);
        assertFalse(added);
        assertTrue(abstractExpandableListAdapter.containsChild(0, child));
        assertEquals(1, abstractExpandableListAdapter.getChildCount(0));
        verify(adapterListener, times(1))
                .onChildAdded(abstractExpandableListAdapter, child, 0, group, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

}