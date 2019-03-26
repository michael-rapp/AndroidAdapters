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
package de.mrapp.android.adapter.list.enablestate;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import de.mrapp.android.adapter.DataSetObserver;
import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.FilterQuery;
import de.mrapp.android.adapter.ListDecorator;
import de.mrapp.android.adapter.Order;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.list.ListAdapter;
import de.mrapp.android.adapter.list.ListAdapterItemClickListener;
import de.mrapp.android.adapter.list.ListAdapterItemLongClickListener;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.util.logging.LogLevel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests the functionality of the class {@link AbstractEnableStateListAdapter}.
 *
 * @author Michael Rapp
 */
@RunWith(AndroidJUnit4.class)
public class AbstractEnableStateListAdapterTest {

    /**
     * An implementation of the abstract class {@link AbstractEnableStateListAdapter}, which is
     * needed for test purposes.
     */
    private class AbstractEnableStateListAdapterImplementation
            extends AbstractEnableStateListAdapter<Object, ListDecorator<Object>> {

        /**
         * The constant serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        protected void applyDecorator(@NonNull final Context context, @NonNull final View view,
                                      final int index) {

        }

        @Override
        protected final void onSaveInstanceState(@NonNull final Bundle savedState) {

        }

        @Override
        protected final void onRestoreInstanceState(@NonNull final Bundle savedState) {

        }

        /**
         * Creates a new adapter, whose underlying data is managed as a list of arbitrary items,
         * which may be disabled or enabled.
         *
         * @param context
         *         The context, the adapter should belong to, as an instance of the class {@link
         *         Context}. The context may not be null
         * @param decorator
         *         The decorator, which should be used to customize the appearance of the views,
         *         which are used to visualize the items of the adapter, as an instance of the
         *         generic type DecoratorType. The decorator may not be null
         * @param logLevel
         *         The log level, which should be used for logging, as a value of the enum LogLevel.
         *         The log level may not be null
         * @param items
         *         A list, which contains the adapter's items, or an empty list, if the adapter
         *         should not contain any items
         * @param allowDuplicates
         *         True, if duplicate items should be allowed, false otherwise
         * @param notifyOnChange
         *         True, if the method <code>notifyDataSetChanged():void</code> should be
         *         automatically called when the adapter's underlying data has been changed, false
         *         otherwise
         * @param itemClickListeners
         *         A set, which contains the listeners, which should be notified, when an item of
         *         the adapter has been clicked by the user, as an instance of the type {@link Set}
         *         or an empty set, if no listeners should be notified
         * @param itemLongClickListeners
         *         A set, which contains the listeners, which should be notified, when an item of
         *         the adapter has been long-clicked by the user, as an instance of the type {@link
         *         Set} or an empty set, if no listeners should be notified
         * @param adapterListeners
         *         A set, which contains the listeners, which should be notified, when the adapter's
         *         underlying data has been modified or an empty set, if no listeners should be
         *         notified
         * @param enableStateListeners
         *         A set, which contains the listeners, which should be notified, when an item has
         *         been disabled or enabled or an empty set, if no listeners should be notified
         */
        protected AbstractEnableStateListAdapterImplementation(final Context context,
                                                               final ListDecorator<Object> decorator,
                                                               final LogLevel logLevel,
                                                               final ArrayList<Item<Object>> items,
                                                               final boolean allowDuplicates,
                                                               final boolean notifyOnChange,
                                                               final Set<ListAdapterItemClickListener<Object>> itemClickListeners,
                                                               final Set<ListAdapterItemLongClickListener<Object>> itemLongClickListeners,
                                                               final Set<ListAdapterListener<Object>> adapterListeners,
                                                               final Set<ListEnableStateListener<Object>> enableStateListeners) {
            super(context, decorator, logLevel, items, allowDuplicates, notifyOnChange,
                    itemClickListeners, itemLongClickListeners, adapterListeners,
                    enableStateListeners);
        }

        @Override
        public void sort() {

        }

        @Override
        public void sort(@NonNull final Order order) {

        }

        @Override
        public void sort(@NonNull final Comparator<Object> comparator) {

        }

        @Override
        public void sort(@NonNull final Order order, @NonNull final Comparator<Object> comparator) {

        }

        @Override
        public int addItemSorted(@NonNull final Object item) {
            return 0;
        }

        @Override
        public int addItemSorted(@NonNull final Object item,
                                 @Nullable final Comparator<Object> comparator) {
            return 0;
        }

        @Override
        public boolean addAllItemsSorted(@NonNull final Collection<?> items) {
            return false;
        }

        @Override
        public boolean addAllItemsSorted(@NonNull final Collection<?> items,
                                         @Nullable final Comparator<Object> comparator) {
            return false;
        }

        @Override
        public boolean addAllItemsSorted(@NonNull final Object... items) {
            return false;
        }

        @Override
        public boolean addAllItemsSorted(@Nullable final Comparator<Object> comparator,
                                         @NonNull final Object... items) {
            return false;
        }

        @Override
        public Order getOrder() {
            return null;
        }

        @Override
        public void addSortingListener(@NonNull final ListSortingListener<Object> listener) {

        }

        @Override
        public void removeSortingListener(@NonNull final ListSortingListener<Object> listener) {

        }

        @Override
        public int getNumberOfItemStates() {
            return 0;
        }

        @Override
        public void setNumberOfItemStates(final int numberOfItemStates) {

        }

        @Override
        public int minItemState() {
            return 0;
        }

        @Override
        public int maxItemState() {
            return 0;
        }

        @Override
        public int getItemState(final int index) {
            return 0;
        }

        @Override
        public int getItemState(@NonNull final Object item) {
            return 0;
        }

        @Override
        public int setItemState(final int index, final int state) {
            return 0;
        }

        @Override
        public int setItemState(@NonNull final Object item, final int state) {
            return 0;
        }

        @Override
        public boolean setAllItemStates(final int state) {
            return false;
        }

        @Override
        public int triggerItemState(final int index) {
            return 0;
        }

        @Override
        public int triggerItemState(@NonNull final Object item) {
            return 0;
        }

        @Override
        public boolean triggerAllItemStates() {
            return false;
        }

        @Override
        public int getFirstIndexWithSpecificState(final int state) {
            return 0;
        }

        @Override
        public Object getFirstItemWithSpecificState(final int state) {
            return null;
        }

        @Override
        public int getLastIndexWithSpecificState(final int state) {
            return 0;
        }

        @Override
        public Object getLastItemWithSpecificState(final int state) {
            return null;
        }

        @Override
        public List<Integer> getIndicesWithSpecificState(final int state) {
            return null;
        }

        @Override
        public List<Object> getItemsWithSpecificState(final int state) {
            return null;
        }

        @Override
        public int getItemStateCount(final int state) {
            return 0;
        }

        @Override
        public boolean isItemStateTriggeredOnClick() {
            return false;
        }

        @Override
        public void triggerItemStateOnClick(final boolean triggerItemStateOnClick) {

        }

        @Override
        public void addItemStateListener(@NonNull final ListItemStateListener<Object> listener) {

        }

        @Override
        public void removeItemStateListener(@NonNull final ListItemStateListener<Object> listener) {

        }

        @Override
        public AbstractEnableStateListAdapter<Object, ListDecorator<Object>> clone()
                throws CloneNotSupportedException {
            return null;
        }

        @Nullable
        @Override
        public List<Object> applyFilter(@NonNull final String query, final int flags) {
            return null;
        }

        @Nullable
        @Override
        public List<Object> applyFilter(@NonNull final String query, final int flags,
                                        @NonNull final Filter<Object> filter) {
            return null;
        }

        @Override
        public boolean resetFilter(@NonNull final String query, final int flags) {
            return false;
        }

        @Override
        public void resetAllFilters() {

        }

        @Override
        public boolean isFiltered() {
            return false;
        }

        @Override
        public Set<? extends FilterQuery> getFilterQueries() {
            return null;
        }

        @Override
        public void addFilterListener(@NonNull final ListFilterListener<Object> listener) {

        }

        @Override
        public void removeFilterListener(@NonNull final ListFilterListener<Object> listener) {

        }

        @Override
        public boolean isFilterApplied(@NonNull final String query, final int flags) {
            return false;
        }

    }

    /**
     * An implementation of the abstract class {@link ListDecorator}, which is needed for test
     * purposes.
     */
    private class ListDecoratorImplementation extends ListDecorator<Object> {

        @SuppressWarnings("ConstantConditions")
        @NonNull
        @Override
        public View onInflateView(@NonNull final LayoutInflater inflater,
                                  @Nullable final ViewGroup parent, final int viewType) {
            return null;
        }

        @Override
        public void onShowItem(@NonNull final Context context,
                               @NonNull final ListAdapter<Object> adapter, @NonNull final View view,
                               @NonNull final Object item, final int viewType, final int index,
                               final boolean enabled, final int state, final boolean filtered) {

        }

    }

    @Test
    public final void testConstructor() {
        Set<ListEnableStateListener<Object>> enableStateListeners = new LinkedHashSet<>();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(), enableStateListeners);
        assertEquals(enableStateListeners,
                abstractEnableStateListAdapter.getEnableStateListeners());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionWhenEnableStateListenersIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        new AbstractEnableStateListAdapterImplementation(context, new ListDecoratorImplementation(),
                LogLevel.ALL, new ArrayList<Item<Object>>(), false, true,
                new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                new LinkedHashSet<ListAdapterListener<Object>>(), null);
    }

    @Test
    public final void testAreAllItemsEnabledWhenAllItemsAreEnabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.addItem(new Object());
        assertTrue(abstractEnableStateListAdapter.areAllItemsEnabled());
    }

    @Test
    public final void testAreAllItemsEnabledWhenNotAllItemsAreEnabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.setEnabled(0, false);
        assertFalse(abstractEnableStateListAdapter.areAllItemsEnabled());
    }

    @Test
    public final void testIsEnabledByIndexWhenItemIsEnabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        assertTrue(abstractEnableStateListAdapter.isEnabled(0));
    }

    @Test
    public final void testIsEnabledByIndexWhenItemIsNotEnabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.setEnabled(0, false);
        assertFalse(abstractEnableStateListAdapter.isEnabled(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testIsEnabledByIndexThrowsExceptionWhenIndexIsInvalid() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.isEnabled(-1);
    }

    @Test
    public final void testIsEnabledWhenItemIsEnabled() {
        Object item = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(item);
        assertTrue(abstractEnableStateListAdapter.isEnabled(item));
    }

    @Test
    public final void testIsEnabledWhenItemIsNotEnabled() {
        Object item = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(item);
        abstractEnableStateListAdapter.setEnabled(0, false);
        assertFalse(abstractEnableStateListAdapter.isEnabled(item));
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testIsEnabledThrowsExceptionWhenItemIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.isEnabled(null);
    }

    @Test
    public final void testGetFirstEnabledIndex() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.addItem(new Object());
        assertEquals(0, abstractEnableStateListAdapter.getFirstEnabledIndex());
    }

    @Test
    public final void testGetFirstEnabledIndexWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        assertEquals(-1, abstractEnableStateListAdapter.getFirstEnabledIndex());
    }

    @Test
    public final void testGetFirstEnabledIndexWhenNoItemIsEnabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.setEnabled(0, false);
        assertEquals(-1, abstractEnableStateListAdapter.getFirstEnabledIndex());
    }

    @Test
    public final void testGetFirstEnabledItem() {
        Object item = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(item);
        abstractEnableStateListAdapter.addItem(new Object());
        assertEquals(item, abstractEnableStateListAdapter.getFirstEnabledItem());
    }

    @Test
    public final void testGetFirstEnabledItemWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        assertNull(abstractEnableStateListAdapter.getFirstEnabledItem());
    }

    @Test
    public final void testGetFirstEnabledItemWhenNoItemIsEnabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.setEnabled(0, false);
        assertNull(abstractEnableStateListAdapter.getFirstEnabledItem());
    }

    @Test
    public final void testGetLastEnabledIndex() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.addItem(new Object());
        assertEquals(1, abstractEnableStateListAdapter.getLastEnabledIndex());
    }

    @Test
    public final void testGetLastEnabledIndexWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        assertEquals(-1, abstractEnableStateListAdapter.getLastEnabledIndex());
    }

    @Test
    public final void testGetLastEnabledIndexWhenNoItemIsEnabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.setEnabled(0, false);
        assertEquals(-1, abstractEnableStateListAdapter.getLastEnabledIndex());
    }

    @Test
    public final void testGetLastEnabledItem() {
        Object item = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.addItem(item);
        assertEquals(item, abstractEnableStateListAdapter.getLastEnabledItem());
    }

    @Test
    public final void testGetLastEnabledItemWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        assertNull(abstractEnableStateListAdapter.getLastEnabledItem());
    }

    @Test
    public final void testGetLastEnabledItemWhenNoItemIsEnabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.setEnabled(0, false);
        assertNull(abstractEnableStateListAdapter.getLastEnabledItem());
    }

    @Test
    public final void testGetFirstDisabledIndex() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.setEnabled(0, false);
        abstractEnableStateListAdapter.setEnabled(1, false);
        assertEquals(0, abstractEnableStateListAdapter.getFirstDisabledIndex());
    }

    @Test
    public final void testGetFirstDisabledIndexWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        assertEquals(-1, abstractEnableStateListAdapter.getFirstDisabledIndex());
    }

    @Test
    public final void testGetFirstDisabledIndexWhenNoItemIsDisabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        assertEquals(-1, abstractEnableStateListAdapter.getFirstDisabledIndex());
    }

    @Test
    public final void testGetFirstDisabledItem() {
        Object item = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(item);
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.setEnabled(0, false);
        abstractEnableStateListAdapter.setEnabled(1, false);
        assertEquals(item, abstractEnableStateListAdapter.getFirstDisabledItem());
    }

    @Test
    public final void testGetFirstDisabledItemWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        assertNull(abstractEnableStateListAdapter.getFirstDisabledItem());
    }

    @Test
    public final void testGetFirstDisabledItemWhenNoItemIsDisabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        assertNull(abstractEnableStateListAdapter.getFirstDisabledItem());
    }

    @Test
    public final void testGetLastDisabledIndex() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.setEnabled(0, false);
        abstractEnableStateListAdapter.setEnabled(1, false);
        assertEquals(1, abstractEnableStateListAdapter.getLastDisabledIndex());
    }

    @Test
    public final void testGetLastDisabledIndexWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        assertEquals(-1, abstractEnableStateListAdapter.getLastDisabledIndex());
    }

    @Test
    public final void testGetLastDisabledIndexWhenNoItemIsDisabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        assertEquals(-1, abstractEnableStateListAdapter.getLastDisabledIndex());
    }

    @Test
    public final void testGetLastDisabledItem() {
        Object item = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.addItem(item);
        abstractEnableStateListAdapter.setEnabled(0, false);
        abstractEnableStateListAdapter.setEnabled(1, false);
        assertEquals(item, abstractEnableStateListAdapter.getLastDisabledItem());
    }

    @Test
    public final void testGetLastDisabledItemWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        assertNull(abstractEnableStateListAdapter.getLastDisabledItem());
    }

    @Test
    public final void testGetLastDisabledItemWhenNoItemIsDisabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        assertNull(abstractEnableStateListAdapter.getLastDisabledItem());
    }

    @Test
    public final void testGetEnabledIndices() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.addItem(new Object());
        Collection<Integer> enabledIndices = abstractEnableStateListAdapter.getEnabledIndices();
        Iterator<Integer> iterator = enabledIndices.iterator();
        assertEquals(0, iterator.next().intValue());
        assertEquals(1, iterator.next().intValue());
    }

    @Test
    public final void testGetEnabledIndicesWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        Collection<Integer> enabledIndices = abstractEnableStateListAdapter.getEnabledIndices();
        assertTrue(enabledIndices.isEmpty());
    }

    @Test
    public final void testGetEnabledIndicesWhenNoItemIsEnabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.setEnabled(0, false);
        Collection<Integer> enabledIndices = abstractEnableStateListAdapter.getEnabledIndices();
        assertTrue(enabledIndices.isEmpty());
    }

    @Test
    public final void testGetEnabledItems() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(item1);
        abstractEnableStateListAdapter.addItem(item2);
        Collection<Object> enabledItems = abstractEnableStateListAdapter.getEnabledItems();
        Iterator<Object> iterator = enabledItems.iterator();
        assertEquals(item1, iterator.next());
        assertEquals(item2, iterator.next());
    }

    @Test
    public final void testGetEnabledItemsWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        Collection<Object> enabledItems = abstractEnableStateListAdapter.getEnabledItems();
        assertTrue(enabledItems.isEmpty());
    }

    @Test
    public final void testGetEnabledItemsWhenNoItemIsEnabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.setEnabled(0, false);
        Collection<Object> enabledItems = abstractEnableStateListAdapter.getEnabledItems();
        assertTrue(enabledItems.isEmpty());
    }

    @Test
    public final void testGetDisabledIndices() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.setEnabled(0, false);
        abstractEnableStateListAdapter.setEnabled(1, false);
        Collection<Integer> disabledIndices = abstractEnableStateListAdapter.getDisabledIndices();
        Iterator<Integer> iterator = disabledIndices.iterator();
        assertEquals(0, iterator.next().intValue());
        assertEquals(1, iterator.next().intValue());
    }

    @Test
    public final void testGetDisabledIndicesWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        Collection<Integer> disabledIndices = abstractEnableStateListAdapter.getDisabledIndices();
        assertTrue(disabledIndices.isEmpty());
    }

    @Test
    public final void testGetDisabledIndicesWhenNoItemIsDisabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        Collection<Integer> disabledIndices = abstractEnableStateListAdapter.getDisabledIndices();
        assertTrue(disabledIndices.isEmpty());
    }

    @Test
    public final void testGetDisabledItems() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(item1);
        abstractEnableStateListAdapter.addItem(item2);
        abstractEnableStateListAdapter.setEnabled(0, false);
        abstractEnableStateListAdapter.setEnabled(1, false);
        Collection<Object> disabledItems = abstractEnableStateListAdapter.getDisabledItems();
        Iterator<Object> iterator = disabledItems.iterator();
        assertEquals(item1, iterator.next());
        assertEquals(item2, iterator.next());
    }

    @Test
    public final void testGetDisabledItemsWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        Collection<Object> disabledItems = abstractEnableStateListAdapter.getDisabledItems();
        assertTrue(disabledItems.isEmpty());
    }

    @Test
    public final void testGetDisabledItemsWhenNoItemIsDisabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        Collection<Object> disabledItems = abstractEnableStateListAdapter.getDisabledItems();
        assertTrue(disabledItems.isEmpty());
    }

    @Test
    public final void testGetEnabledItemCount() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.addItem(new Object());
        abstractEnableStateListAdapter.setEnabled(0, false);
        assertEquals(1, abstractEnableStateListAdapter.getEnabledItemCount());
    }

    @Test
    public final void testGetEnabledItemCountWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        assertEquals(0, abstractEnableStateListAdapter.getEnabledItemCount());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetEnabledByIndex() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractEnableStateListAdapter.addEnableStateListener(listEnableStateListener);
        abstractEnableStateListAdapter.addItem(item);
        abstractEnableStateListAdapter.setEnabled(0, false);
        assertFalse(abstractEnableStateListAdapter.isEnabled(0));
        verify(listEnableStateListener, times(1))
                .onItemDisabled(abstractEnableStateListAdapter, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
        dataSetObserver.reset();
        abstractEnableStateListAdapter.setEnabled(0, true);
        assertTrue(abstractEnableStateListAdapter.isEnabled(0));
        verify(listEnableStateListener, times(1))
                .onItemEnabled(abstractEnableStateListAdapter, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetEnabledByIndexWhenItemIsAlreadyEnabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractEnableStateListAdapter.addEnableStateListener(listEnableStateListener);
        abstractEnableStateListAdapter.addItem(item);
        dataSetObserver.reset();
        abstractEnableStateListAdapter.setEnabled(0, true);
        assertTrue(abstractEnableStateListAdapter.isEnabled(0));
        verify(listEnableStateListener, times(0))
                .onItemDisabled(abstractEnableStateListAdapter, item, 0);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testSetEnabledByIndexThrowsExceptionWhenIndexIsInvalid() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.setEnabled(-1, true);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetEnabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractEnableStateListAdapter.addEnableStateListener(listEnableStateListener);
        abstractEnableStateListAdapter.addItem(item);
        abstractEnableStateListAdapter.setEnabled(item, false);
        assertFalse(abstractEnableStateListAdapter.isEnabled(item));
        verify(listEnableStateListener, times(1))
                .onItemDisabled(abstractEnableStateListAdapter, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
        dataSetObserver.reset();
        abstractEnableStateListAdapter.setEnabled(item, true);
        assertTrue(abstractEnableStateListAdapter.isEnabled(item));
        verify(listEnableStateListener, times(1))
                .onItemEnabled(abstractEnableStateListAdapter, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetEnabledWhenItemIsAlreadyEnabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractEnableStateListAdapter.addEnableStateListener(listEnableStateListener);
        abstractEnableStateListAdapter.addItem(item);
        dataSetObserver.reset();
        abstractEnableStateListAdapter.setEnabled(item, true);
        assertTrue(abstractEnableStateListAdapter.isEnabled(item));
        verify(listEnableStateListener, times(0))
                .onItemDisabled(abstractEnableStateListAdapter, item, 0);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = NoSuchElementException.class)
    public final void testSetEnabledThrowsExceptionWhenAdapterDoesNotContainItem() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.setEnabled(new Object(), true);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testSetEnabledThrowsExceptionWhenItemIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.setEnabled(null, true);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerEnableStateByIndexWhenItemIsEnabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractEnableStateListAdapter.addEnableStateListener(listEnableStateListener);
        abstractEnableStateListAdapter.addItem(item);
        dataSetObserver.reset();
        boolean enabled = abstractEnableStateListAdapter.triggerEnableState(0);
        assertFalse(enabled);
        assertFalse(abstractEnableStateListAdapter.isEnabled(0));
        verify(listEnableStateListener, times(1))
                .onItemDisabled(abstractEnableStateListAdapter, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerEnableStateByIndexWhenItemIsDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractEnableStateListAdapter.addEnableStateListener(listEnableStateListener);
        abstractEnableStateListAdapter.addItem(item);
        abstractEnableStateListAdapter.setEnabled(0, false);
        dataSetObserver.reset();
        boolean enabled = abstractEnableStateListAdapter.triggerEnableState(0);
        assertTrue(enabled);
        assertTrue(abstractEnableStateListAdapter.isEnabled(0));
        verify(listEnableStateListener, times(1))
                .onItemEnabled(abstractEnableStateListAdapter, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testTriggerEnableStateByIndexThrowsExceptionWhenIndexIsInvalid() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.triggerEnableState(-1);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerEnableStateWhenItemIsEnabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractEnableStateListAdapter.addEnableStateListener(listEnableStateListener);
        abstractEnableStateListAdapter.addItem(item);
        dataSetObserver.reset();
        boolean enabled = abstractEnableStateListAdapter.triggerEnableState(item);
        assertFalse(enabled);
        assertFalse(abstractEnableStateListAdapter.isEnabled(item));
        verify(listEnableStateListener, times(1))
                .onItemDisabled(abstractEnableStateListAdapter, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerEnableStateWhenItemIsDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractEnableStateListAdapter.addEnableStateListener(listEnableStateListener);
        abstractEnableStateListAdapter.addItem(item);
        abstractEnableStateListAdapter.setEnabled(item, false);
        dataSetObserver.reset();
        boolean enabled = abstractEnableStateListAdapter.triggerEnableState(item);
        assertTrue(enabled);
        assertTrue(abstractEnableStateListAdapter.isEnabled(item));
        verify(listEnableStateListener, times(1))
                .onItemEnabled(abstractEnableStateListAdapter, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = NoSuchElementException.class)
    public final void testTriggerEnableStateThrowsExceptionWhenAdapterDoesNotContainItem() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.triggerEnableState(new Object());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testTriggerEnableStateThrowsExceptionWhenItemIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.triggerEnableState(null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetAllEnabled() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractEnableStateListAdapter.addEnableStateListener(listEnableStateListener);
        abstractEnableStateListAdapter.addItem(item1);
        abstractEnableStateListAdapter.addItem(item2);
        abstractEnableStateListAdapter.setEnabled(0, false);
        abstractEnableStateListAdapter.setEnabled(1, false);
        dataSetObserver.reset();
        abstractEnableStateListAdapter.setAllEnabled(true);
        assertTrue(abstractEnableStateListAdapter.isEnabled(0));
        assertTrue(abstractEnableStateListAdapter.isEnabled(1));
        verify(listEnableStateListener, times(1))
                .onItemEnabled(abstractEnableStateListAdapter, item1, 0);
        verify(listEnableStateListener, times(1))
                .onItemEnabled(abstractEnableStateListAdapter, item2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
        dataSetObserver.reset();
        abstractEnableStateListAdapter.setAllEnabled(false);
        assertFalse(abstractEnableStateListAdapter.isEnabled(0));
        assertFalse(abstractEnableStateListAdapter.isEnabled(1));
        verify(listEnableStateListener, times(2))
                .onItemDisabled(abstractEnableStateListAdapter, item1, 0);
        verify(listEnableStateListener, times(2))
                .onItemDisabled(abstractEnableStateListAdapter, item2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerAllEnableStates() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractEnableStateListAdapter.addEnableStateListener(listEnableStateListener);
        abstractEnableStateListAdapter.addItem(item1);
        abstractEnableStateListAdapter.addItem(item2);
        abstractEnableStateListAdapter.setEnabled(item2, false);
        dataSetObserver.reset();
        abstractEnableStateListAdapter.triggerAllEnableStates();
        assertFalse(abstractEnableStateListAdapter.isEnabled(item1));
        assertTrue(abstractEnableStateListAdapter.isEnabled(item2));
        verify(listEnableStateListener, times(1))
                .onItemDisabled(abstractEnableStateListAdapter, item1, 0);
        verify(listEnableStateListener, times(1))
                .onItemEnabled(abstractEnableStateListAdapter, item2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddEnableStateListener() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        assertTrue(abstractEnableStateListAdapter.getEnableStateListeners().isEmpty());
        abstractEnableStateListAdapter.addEnableStateListener(listEnableStateListener);
        abstractEnableStateListAdapter.addEnableStateListener(listEnableStateListener);
        assertEquals(1, abstractEnableStateListAdapter.getEnableStateListeners().size());
        assertTrue(abstractEnableStateListAdapter.getEnableStateListeners()
                .contains(listEnableStateListener));
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddEnableStateListenerThrowsExceptionWhenListenerIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addEnableStateListener(null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemoveEnableStateListener() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        assertTrue(abstractEnableStateListAdapter.getEnableStateListeners().isEmpty());
        abstractEnableStateListAdapter.removeEnableStateListener(listEnableStateListener);
        abstractEnableStateListAdapter.addEnableStateListener(listEnableStateListener);
        assertFalse(abstractEnableStateListAdapter.getEnableStateListeners().isEmpty());
        abstractEnableStateListAdapter.removeEnableStateListener(listEnableStateListener);
        assertTrue(abstractEnableStateListAdapter.getEnableStateListeners().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testRemoveEnableStateListenerThrowsExceptionWhenListenerIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.removeEnableStateListener(null);
    }

}