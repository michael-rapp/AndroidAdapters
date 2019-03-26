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
package de.mrapp.android.adapter.list.itemstate;

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
import de.mrapp.android.adapter.R;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.list.ListAdapter;
import de.mrapp.android.adapter.list.ListAdapterItemClickListener;
import de.mrapp.android.adapter.list.ListAdapterItemLongClickListener;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.util.logging.LogLevel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests the functionality of the class {@link AbstractItemStateListAdapter}.
 *
 * @author Michael Rapp
 */
@RunWith(AndroidJUnit4.class)
public class AbstractItemStateListAdapterTest {

    /**
     * An implementation of the abstract class {@link AbstractItemStateListAdapter}, which is needed
     * for test purposes.
     */
    private class AbstractItemStateListAdapterImplementation
            extends AbstractItemStateListAdapter<Object, ListDecorator<Object>> {

        /**
         * The constant serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        protected void applyDecorator(@NonNull final Context context, @NonNull final View view,
                                      final int index) {

        }

        /**
         * Creates a new adapter, whose underlying data is managed as a list of arbitrary items,
         * which may have multiple states.
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
         * @param numberOfItemStates
         *         The number of states, the adapter's items may have, as an {@link Integer} value.
         *         The value must be at least 1
         * @param triggerItemStateOnClick
         *         True, if the state of an item should be triggered, when it is clicked by the
         *         user, false otherwise
         * @param itemStateListeners
         *         A set, which contains the listeners, which should be notified, when the state of
         *         an item has been changed or an empty set, if no listeners should be notified
         */
        protected AbstractItemStateListAdapterImplementation(final Context context,
                                                             final ListDecorator<Object> decorator,
                                                             final LogLevel logLevel,
                                                             final ArrayList<Item<Object>> items,
                                                             final boolean allowDuplicates,
                                                             final boolean notifyOnChange,
                                                             final Set<ListAdapterItemClickListener<Object>> itemClickListeners,
                                                             final Set<ListAdapterItemLongClickListener<Object>> itemLongClickListeners,
                                                             final Set<ListAdapterListener<Object>> adapterListeners,
                                                             final Set<ListEnableStateListener<Object>> enableStateListeners,
                                                             final int numberOfItemStates,
                                                             final boolean triggerItemStateOnClick,
                                                             final Set<ListItemStateListener<Object>> itemStateListeners) {
            super(context, decorator, logLevel, items, allowDuplicates, notifyOnChange,
                    itemClickListeners, itemLongClickListeners, adapterListeners,
                    enableStateListeners, numberOfItemStates, triggerItemStateOnClick,
                    itemStateListeners);
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
        public AbstractItemStateListAdapter<Object, ListDecorator<Object>> clone()
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

        @NonNull
        @Override
        public View onInflateView(@NonNull final LayoutInflater inflater,
                                  @Nullable final ViewGroup parent, final int viewType) {
            return inflater.inflate(R.layout.view, parent, false);
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
        int numberOfItemStates = 2;
        boolean triggerItemStateOnClick = true;
        Set<ListItemStateListener<Object>> itemStateListeners = new LinkedHashSet<>();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), numberOfItemStates,
                        triggerItemStateOnClick, itemStateListeners);
        assertEquals(numberOfItemStates, abstractItemStateListAdapter.getNumberOfItemStates());
        assertEquals(triggerItemStateOnClick,
                abstractItemStateListAdapter.isItemStateTriggeredOnClick());
        assertEquals(itemStateListeners, abstractItemStateListAdapter.getItemStateListeners());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionWhenItemStateListenersIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        new AbstractItemStateListAdapterImplementation(context, new ListDecoratorImplementation(),
                LogLevel.ALL, new ArrayList<Item<Object>>(), false, true,
                new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                new LinkedHashSet<ListAdapterListener<Object>>(),
                new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionWhenNumberOfItemStatesIsLessThanOne() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        new AbstractItemStateListAdapterImplementation(context, new ListDecoratorImplementation(),
                LogLevel.ALL, new ArrayList<Item<Object>>(), false, true,
                new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                new LinkedHashSet<ListAdapterListener<Object>>(),
                new LinkedHashSet<ListEnableStateListener<Object>>(), 0, false,
                new LinkedHashSet<ListItemStateListener<Object>>());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetNumberOfItemStates() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractItemStateListAdapter.addItemStateListener(listItemStateListener);
        abstractItemStateListAdapter.addItem(item1);
        abstractItemStateListAdapter.addItem(item2);
        abstractItemStateListAdapter.setItemState(item1, 0);
        abstractItemStateListAdapter.setItemState(item2, 1);
        dataSetObserver.reset();
        abstractItemStateListAdapter.setNumberOfItemStates(1);
        assertEquals(1, abstractItemStateListAdapter.getNumberOfItemStates());
        assertEquals(0, abstractItemStateListAdapter.getItemState(item1));
        assertEquals(0, abstractItemStateListAdapter.getItemState(item2));
        verify(listItemStateListener, times(1))
                .onItemStateChanged(abstractItemStateListAdapter, item2, 1, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testSetNumberOfItemStatesThrowsExceptionWhenNumberOfStatesIsLessThanOne() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.setNumberOfItemStates(0);
    }

    @Test
    public final void testMinItemState() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        assertEquals(0, abstractItemStateListAdapter.minItemState());
    }

    @Test
    public final void testMaxItemState() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        assertEquals(0, abstractItemStateListAdapter.maxItemState());
        abstractItemStateListAdapter.setNumberOfItemStates(2);
        assertEquals(1, abstractItemStateListAdapter.maxItemState());
    }

    @Test
    public final void testGetItemStateByIndex() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItem(item1);
        abstractItemStateListAdapter.addItem(item2);
        abstractItemStateListAdapter.setItemState(1, 1);
        assertEquals(0, abstractItemStateListAdapter.getItemState(0));
        assertEquals(1, abstractItemStateListAdapter.getItemState(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testGetItemStateByIndexThrowsExceptionWhenIndexIsInvalid() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.getItemState(-1);
    }

    @Test
    public final void testGetItemState() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItem(item1);
        abstractItemStateListAdapter.addItem(item2);
        abstractItemStateListAdapter.setItemState(item2, 1);
        assertEquals(0, abstractItemStateListAdapter.getItemState(item1));
        assertEquals(1, abstractItemStateListAdapter.getItemState(item2));
    }

    @Test(expected = NoSuchElementException.class)
    public final void testGetItemStateThrowsExceptionWhenAdapterDoesNotContainItem() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.getItemState(new Object());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testGetItemStateThrowsExceptionWhenItemIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.getItemState(null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetItemStateByIndex() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractItemStateListAdapter.addItemStateListener(listItemStateListener);
        abstractItemStateListAdapter.addItem(item);
        dataSetObserver.reset();
        int previousState = abstractItemStateListAdapter.setItemState(0, 1);
        assertEquals(0, previousState);
        assertEquals(1, abstractItemStateListAdapter.getItemState(0));
        verify(listItemStateListener, times(1))
                .onItemStateChanged(abstractItemStateListAdapter, item, 0, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetItemStateByIndexWhenItemIsDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractItemStateListAdapter.addItemStateListener(listItemStateListener);
        abstractItemStateListAdapter.addItem(item);
        abstractItemStateListAdapter.setEnabled(0, false);
        dataSetObserver.reset();
        int previousState = abstractItemStateListAdapter.setItemState(0, 1);
        assertEquals(-1, previousState);
        assertEquals(0, abstractItemStateListAdapter.getItemState(0));
        verify(listItemStateListener, times(0))
                .onItemStateChanged(abstractItemStateListAdapter, item, 0, 1);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetItemStateByIndexWhenStateIsAlreadySet() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractItemStateListAdapter.addItemStateListener(listItemStateListener);
        abstractItemStateListAdapter.addItem(item);
        abstractItemStateListAdapter.setItemState(0, 1);
        dataSetObserver.reset();
        int previousState = abstractItemStateListAdapter.setItemState(0, 1);
        assertEquals(1, previousState);
        assertEquals(1, abstractItemStateListAdapter.getItemState(0));
        verify(listItemStateListener, times(1))
                .onItemStateChanged(abstractItemStateListAdapter, item, 0, 1);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testSetItemStateByIndexThrowsExceptionWhenIndexIsInvalid() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.setItemState(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testSetItemStateByIndexThrowsExceptionWhenStateIsLessThanZero() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItem(new Object());
        abstractItemStateListAdapter.setItemState(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testSetItemStateByIndexThrowsExceptionWhenStateIsGreaterThanMaxState() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItem(new Object());
        abstractItemStateListAdapter.setItemState(0, 2);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetItemState() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractItemStateListAdapter.addItemStateListener(listItemStateListener);
        abstractItemStateListAdapter.addItem(item);
        dataSetObserver.reset();
        int previousState = abstractItemStateListAdapter.setItemState(item, 1);
        assertEquals(0, previousState);
        assertEquals(1, abstractItemStateListAdapter.getItemState(item));
        verify(listItemStateListener, times(1))
                .onItemStateChanged(abstractItemStateListAdapter, item, 0, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetItemStateWhenItemIsDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractItemStateListAdapter.addItemStateListener(listItemStateListener);
        abstractItemStateListAdapter.addItem(item);
        abstractItemStateListAdapter.setEnabled(item, false);
        dataSetObserver.reset();
        int previousState = abstractItemStateListAdapter.setItemState(item, 1);
        assertEquals(-1, previousState);
        assertEquals(0, abstractItemStateListAdapter.getItemState(item));
        verify(listItemStateListener, times(0))
                .onItemStateChanged(abstractItemStateListAdapter, item, 0, 1);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetItemStateWhenStateIsAlreadySet() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractItemStateListAdapter.addItemStateListener(listItemStateListener);
        abstractItemStateListAdapter.addItem(item);
        abstractItemStateListAdapter.setItemState(item, 1);
        dataSetObserver.reset();
        int previousState = abstractItemStateListAdapter.setItemState(item, 1);
        assertEquals(1, previousState);
        assertEquals(1, abstractItemStateListAdapter.getItemState(item));
        verify(listItemStateListener, times(1))
                .onItemStateChanged(abstractItemStateListAdapter, item, 0, 1);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testSetItemStateThrowsExceptionWhenItemIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.setItemState(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testSetItemStateThrowsExceptionWhenStateIsLessThanZero() {
        Object item = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItem(item);
        abstractItemStateListAdapter.setItemState(item, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testSetItemStateThrowsExceptionWhenStateIsGreaterThanMaxState() {
        Object item = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItem(item);
        abstractItemStateListAdapter.setItemState(item, 2);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetAllItemStates() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractItemStateListAdapter.addItemStateListener(listItemStateListener);
        abstractItemStateListAdapter.addItem(item1);
        abstractItemStateListAdapter.addItem(item2);
        dataSetObserver.reset();
        boolean result = abstractItemStateListAdapter.setAllItemStates(1);
        assertTrue(result);
        assertEquals(1, abstractItemStateListAdapter.getItemState(item1));
        assertEquals(1, abstractItemStateListAdapter.getItemState(item2));
        verify(listItemStateListener, times(1))
                .onItemStateChanged(abstractItemStateListAdapter, item1, 0, 1);
        verify(listItemStateListener, times(1))
                .onItemStateChanged(abstractItemStateListAdapter, item2, 1, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetAllItemStatesWhenNotAllStatesAreChanged() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractItemStateListAdapter.addItemStateListener(listItemStateListener);
        abstractItemStateListAdapter.addItem(item1);
        abstractItemStateListAdapter.addItem(item2);
        abstractItemStateListAdapter.setEnabled(1, false);
        dataSetObserver.reset();
        boolean result = abstractItemStateListAdapter.setAllItemStates(1);
        assertFalse(result);
        assertEquals(1, abstractItemStateListAdapter.getItemState(item1));
        assertEquals(0, abstractItemStateListAdapter.getItemState(item2));
        verify(listItemStateListener, times(1))
                .onItemStateChanged(abstractItemStateListAdapter, item1, 0, 1);
        verify(listItemStateListener, times(0))
                .onItemStateChanged(abstractItemStateListAdapter, item2, 1, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testSetAllItemStatesThrowsExceptionWhenStateIsLessThanZero() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.setAllItemStates(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testSetAllItemStatesThrowsExceptionWhenStateIsGreaterThanMaxState() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.setAllItemStates(2);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerItemStateByIndexWhenStateIsNotMaxState() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractItemStateListAdapter.addItemStateListener(listItemStateListener);
        abstractItemStateListAdapter.addItem(item);
        dataSetObserver.reset();
        int previousState = abstractItemStateListAdapter.triggerItemState(0);
        assertEquals(0, previousState);
        assertEquals(1, abstractItemStateListAdapter.getItemState(0));
        verify(listItemStateListener, times(1))
                .onItemStateChanged(abstractItemStateListAdapter, item, 0, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerItemStateByIndexWhenStateIsMaxState() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractItemStateListAdapter.addItemStateListener(listItemStateListener);
        abstractItemStateListAdapter.addItem(item);
        abstractItemStateListAdapter.setItemState(0, abstractItemStateListAdapter.maxItemState());
        dataSetObserver.reset();
        int previousState = abstractItemStateListAdapter.triggerItemState(0);
        assertEquals(1, previousState);
        assertEquals(0, abstractItemStateListAdapter.getItemState(0));
        verify(listItemStateListener, times(1))
                .onItemStateChanged(abstractItemStateListAdapter, item, 0, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerItemStateByIndexWhenItemIsDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractItemStateListAdapter.addItemStateListener(listItemStateListener);
        abstractItemStateListAdapter.addItem(item);
        abstractItemStateListAdapter.setEnabled(0, false);
        dataSetObserver.reset();
        int previousState = abstractItemStateListAdapter.triggerItemState(0);
        assertEquals(-1, previousState);
        assertEquals(0, abstractItemStateListAdapter.getItemState(0));
        verify(listItemStateListener, times(0))
                .onItemStateChanged(abstractItemStateListAdapter, item, 0, 0);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testTriggerItemStateByIndexThrowsExceptionWhenIndexIsInvalid() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.triggerItemState(-1);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerItemStateWhenStateIsNotMaxState() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractItemStateListAdapter.addItemStateListener(listItemStateListener);
        abstractItemStateListAdapter.addItem(item);
        dataSetObserver.reset();
        int previousState = abstractItemStateListAdapter.triggerItemState(item);
        assertEquals(0, previousState);
        assertEquals(1, abstractItemStateListAdapter.getItemState(item));
        verify(listItemStateListener, times(1))
                .onItemStateChanged(abstractItemStateListAdapter, item, 0, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerItemStateWhenStateIsMaxState() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractItemStateListAdapter.addItemStateListener(listItemStateListener);
        abstractItemStateListAdapter.addItem(item);
        abstractItemStateListAdapter
                .setItemState(item, abstractItemStateListAdapter.maxItemState());
        dataSetObserver.reset();
        int previousState = abstractItemStateListAdapter.triggerItemState(item);
        assertEquals(1, previousState);
        assertEquals(0, abstractItemStateListAdapter.getItemState(item));
        verify(listItemStateListener, times(1))
                .onItemStateChanged(abstractItemStateListAdapter, item, 0, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerItemStateWhenItemIsDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractItemStateListAdapter.addItemStateListener(listItemStateListener);
        abstractItemStateListAdapter.addItem(item);
        abstractItemStateListAdapter.setEnabled(0, false);
        dataSetObserver.reset();
        int previousState = abstractItemStateListAdapter.triggerItemState(item);
        assertEquals(-1, previousState);
        assertEquals(0, abstractItemStateListAdapter.getItemState(item));
        verify(listItemStateListener, times(0))
                .onItemStateChanged(abstractItemStateListAdapter, item, 0, 0);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testTriggerItemStateThrowsExceptionWhenItemIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.triggerItemState(null);
    }

    @Test(expected = NoSuchElementException.class)
    public final void testTriggerItemStateThrowsExceptionWhenAdapterDoesNotContainItem() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.triggerItemState(new Object());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerAllItemStates() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractItemStateListAdapter.addItemStateListener(listItemStateListener);
        abstractItemStateListAdapter.addItem(item1);
        abstractItemStateListAdapter.addItem(item2);
        abstractItemStateListAdapter
                .setItemState(item2, abstractItemStateListAdapter.maxItemState());
        dataSetObserver.reset();
        boolean result = abstractItemStateListAdapter.triggerAllItemStates();
        assertTrue(result);
        assertEquals(1, abstractItemStateListAdapter.getItemState(item1));
        assertEquals(0, abstractItemStateListAdapter.getItemState(item2));
        verify(listItemStateListener, times(1))
                .onItemStateChanged(abstractItemStateListAdapter, item1, 0, 1);
        verify(listItemStateListener, times(1))
                .onItemStateChanged(abstractItemStateListAdapter, item2, 1, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerAllItemStatesWhenNotAllStatesAreChanged() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractItemStateListAdapter.addItemStateListener(listItemStateListener);
        abstractItemStateListAdapter.addItem(item1);
        abstractItemStateListAdapter.addItem(item2);
        abstractItemStateListAdapter.setEnabled(1, false);
        dataSetObserver.reset();
        boolean result = abstractItemStateListAdapter.triggerAllItemStates();
        assertFalse(result);
        assertEquals(1, abstractItemStateListAdapter.getItemState(item1));
        assertEquals(0, abstractItemStateListAdapter.getItemState(item2));
        verify(listItemStateListener, times(1))
                .onItemStateChanged(abstractItemStateListAdapter, item1, 0, 1);
        verify(listItemStateListener, times(0))
                .onItemStateChanged(abstractItemStateListAdapter, item2, 1, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test
    public final void testGetFirstIndexWithSpecificState() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItem(item1);
        abstractItemStateListAdapter.addItem(item2);
        assertEquals(0, abstractItemStateListAdapter.getFirstIndexWithSpecificState(0));
    }

    @Test
    public final void testGetFirstIndexWithSpecificStateWhenNoItemHasState() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItem(item1);
        abstractItemStateListAdapter.addItem(item2);
        assertEquals(-1, abstractItemStateListAdapter.getFirstIndexWithSpecificState(1));
    }

    @Test
    public final void testGetFirstIndexWithSpecificStateWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        assertEquals(-1, abstractItemStateListAdapter.getFirstIndexWithSpecificState(0));
    }

    @Test
    public final void testGetFirstItemWithSpecificState() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItem(item1);
        abstractItemStateListAdapter.addItem(item2);
        assertEquals(item1, abstractItemStateListAdapter.getFirstItemWithSpecificState(0));
    }

    @Test
    public final void testGetFirstItemWithSpecificStateWhenNoItemHasState() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItem(item1);
        abstractItemStateListAdapter.addItem(item2);
        assertNull(abstractItemStateListAdapter.getFirstItemWithSpecificState(1));
    }

    @Test
    public final void testGetFirstItemWithSpecificStateWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        assertNull(abstractItemStateListAdapter.getFirstItemWithSpecificState(0));
    }

    @Test
    public final void testGetLastIndexWithSpecificState() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItem(item1);
        abstractItemStateListAdapter.addItem(item2);
        assertEquals(1, abstractItemStateListAdapter.getLastIndexWithSpecificState(0));
    }

    @Test
    public final void testGetLastIndexWithSpecificStateWhenNoItemHasState() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItem(item1);
        abstractItemStateListAdapter.addItem(item2);
        assertEquals(-1, abstractItemStateListAdapter.getLastIndexWithSpecificState(1));
    }

    @Test
    public final void testGetLastIndexWithSpecificStateWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        assertEquals(-1, abstractItemStateListAdapter.getLastIndexWithSpecificState(0));
    }

    @Test
    public final void testGetLastItemWithSpecificState() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItem(item1);
        abstractItemStateListAdapter.addItem(item2);
        assertEquals(item2, abstractItemStateListAdapter.getLastItemWithSpecificState(0));
    }

    @Test
    public final void testGetLastItemWithSpecificStateWhenNoItemHasState() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItem(item1);
        abstractItemStateListAdapter.addItem(item2);
        assertNull(abstractItemStateListAdapter.getLastItemWithSpecificState(1));
    }

    @Test
    public final void testGetLastItemWithSpecificStateWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        assertNull(abstractItemStateListAdapter.getLastItemWithSpecificState(0));
    }

    @Test
    public final void testGetIndicesWithSpecificState() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItem(item1);
        abstractItemStateListAdapter.addItem(item2);
        abstractItemStateListAdapter.addItem(item3);
        abstractItemStateListAdapter.setItemState(item2, 1);
        Collection<Integer> indices = abstractItemStateListAdapter.getIndicesWithSpecificState(0);
        assertEquals(2, indices.size());
        Iterator<Integer> iterator = indices.iterator();
        assertEquals(0, iterator.next().intValue());
        assertEquals(2, iterator.next().intValue());
    }

    @Test
    public final void testGetIndicesWithSpecificStateWhenNoItemHasState() {
        Object item = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItem(item);
        Collection<Integer> indices = abstractItemStateListAdapter.getIndicesWithSpecificState(1);
        assertTrue(indices.isEmpty());
    }

    @Test
    public final void testGetIndicesWithSpecificStateWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        Collection<Integer> indices = abstractItemStateListAdapter.getIndicesWithSpecificState(0);
        assertTrue(indices.isEmpty());
    }

    @Test
    public final void testGetItemsWithSpecificState() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItem(item1);
        abstractItemStateListAdapter.addItem(item2);
        abstractItemStateListAdapter.addItem(item3);
        abstractItemStateListAdapter.setItemState(item2, 1);
        Collection<Object> items = abstractItemStateListAdapter.getItemsWithSpecificState(0);
        assertEquals(2, items.size());
        Iterator<Object> iterator = items.iterator();
        assertEquals(item1, iterator.next());
        assertEquals(item3, iterator.next());
    }

    @Test
    public final void testGetItemsWithSpecificStateWhenNoItemHasState() {
        Object item = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItem(item);
        Collection<Object> items = abstractItemStateListAdapter.getItemsWithSpecificState(1);
        assertTrue(items.isEmpty());
    }

    @Test
    public final void testGetItemsWithSpecificStateWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        Collection<Object> items = abstractItemStateListAdapter.getItemsWithSpecificState(0);
        assertTrue(items.isEmpty());
    }

    @Test
    public final void testGetItemStateCountWithSpecificState() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItem(item1);
        abstractItemStateListAdapter.addItem(item2);
        abstractItemStateListAdapter.addItem(item3);
        abstractItemStateListAdapter.setItemState(item2, 1);
        assertEquals(2, abstractItemStateListAdapter.getItemStateCount(0));
    }

    @Test
    public final void testGetItemStateCountWhenNotItemHasState() {
        Object item1 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItem(item1);
        assertEquals(0, abstractItemStateListAdapter.getItemStateCount(1));
    }

    @Test
    public final void testGetItemStateCountWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        assertEquals(0, abstractItemStateListAdapter.getItemStateCount(0));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddItemStateListener() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        assertTrue(abstractItemStateListAdapter.getItemStateListeners().isEmpty());
        abstractItemStateListAdapter.addItemStateListener(listItemStateListener);
        abstractItemStateListAdapter.addItemStateListener(listItemStateListener);
        assertEquals(1, abstractItemStateListAdapter.getItemStateListeners().size());
        assertTrue(abstractItemStateListAdapter.getItemStateListeners()
                .contains(listItemStateListener));
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddItemStateListenerThrowsExceptionWhenListenerIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.addItemStateListener(null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemoveItemStateListener() {
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.removeItemStateListener(listItemStateListener);
        abstractItemStateListAdapter.addItemStateListener(listItemStateListener);
        assertFalse(abstractItemStateListAdapter.getItemStateListeners().isEmpty());
        abstractItemStateListAdapter.removeItemStateListener(listItemStateListener);
        assertTrue(abstractItemStateListAdapter.getItemStateListeners().isEmpty());
    }

    @Test
    public final void testOnSaveInstanceState() {
        Bundle outState = new Bundle();
        int numberOfItemStates = 2;
        boolean triggerItemStateOnClick = true;
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.setNumberOfItemStates(numberOfItemStates);
        abstractItemStateListAdapter.triggerItemStateOnClick(triggerItemStateOnClick);
        abstractItemStateListAdapter.onSaveInstanceState(outState);
        int savedNumberOfItemStates =
                outState.getInt(AbstractItemStateListAdapter.NUMBER_OF_ITEM_STATES_BUNDLE_KEY);
        assertEquals(numberOfItemStates, savedNumberOfItemStates);
        boolean savedTriggerItemStateOnClick = outState.getBoolean(
                AbstractItemStateListAdapter.TRIGGER_ITEM_STATE_ON_CLICK_BUNDLE_KEY);
        assertEquals(triggerItemStateOnClick, savedTriggerItemStateOnClick);
    }

    @Test
    public final void testOnRestoreInstanceState() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        Bundle savedInstanceState = new Bundle();
        abstractItemStateListAdapter.onSaveInstanceState(savedInstanceState);
        int numberOfItemStates = 2;
        savedInstanceState.putInt(AbstractItemStateListAdapter.NUMBER_OF_ITEM_STATES_BUNDLE_KEY,
                numberOfItemStates);
        boolean triggerItemStateOnClick = true;
        savedInstanceState
                .putBoolean(AbstractItemStateListAdapter.TRIGGER_ITEM_STATE_ON_CLICK_BUNDLE_KEY,
                        triggerItemStateOnClick);
        abstractItemStateListAdapter.onRestoreInstanceState(savedInstanceState);
        assertEquals(numberOfItemStates, abstractItemStateListAdapter.getNumberOfItemStates());
        assertEquals(triggerItemStateOnClick,
                abstractItemStateListAdapter.isItemStateTriggeredOnClick());
    }

    @Test
    public final void testHashCode() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter1 =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter2 =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        assertEquals(abstractItemStateListAdapter1.hashCode(),
                abstractItemStateListAdapter1.hashCode());
        assertEquals(abstractItemStateListAdapter1.hashCode(),
                abstractItemStateListAdapter2.hashCode());
        abstractItemStateListAdapter1.allowDuplicates(true);
        assertNotSame(abstractItemStateListAdapter1.hashCode(),
                abstractItemStateListAdapter2.hashCode());
        abstractItemStateListAdapter2
                .allowDuplicates(abstractItemStateListAdapter1.areDuplicatesAllowed());
        abstractItemStateListAdapter1.setNumberOfItemStates(2);
        assertNotSame(abstractItemStateListAdapter1.hashCode(),
                abstractItemStateListAdapter2.hashCode());
        abstractItemStateListAdapter2
                .setNumberOfItemStates(abstractItemStateListAdapter1.getNumberOfItemStates());
        abstractItemStateListAdapter1.triggerItemStateOnClick(true);
        assertNotSame(abstractItemStateListAdapter1.hashCode(),
                abstractItemStateListAdapter2.hashCode());
        abstractItemStateListAdapter2.triggerItemStateOnClick(
                abstractItemStateListAdapter1.isItemStateTriggeredOnClick());
    }

    @Test
    public final void testEquals() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter1 =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter2 =
                new AbstractItemStateListAdapterImplementation(context,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        assertTrue(abstractItemStateListAdapter1.equals(abstractItemStateListAdapter1));
        assertTrue(abstractItemStateListAdapter1.equals(abstractItemStateListAdapter2));
        assertFalse(abstractItemStateListAdapter1.equals(null));
        assertFalse(abstractItemStateListAdapter1.equals(new Object()));
        abstractItemStateListAdapter1.allowDuplicates(true);
        assertFalse(abstractItemStateListAdapter1.equals(abstractItemStateListAdapter2));
        abstractItemStateListAdapter2
                .allowDuplicates(abstractItemStateListAdapter1.areDuplicatesAllowed());
        abstractItemStateListAdapter1.setNumberOfItemStates(2);
        assertFalse(abstractItemStateListAdapter1.equals(abstractItemStateListAdapter2));
        abstractItemStateListAdapter2
                .setNumberOfItemStates(abstractItemStateListAdapter1.getNumberOfItemStates());
        abstractItemStateListAdapter1.triggerItemStateOnClick(true);
        assertFalse(abstractItemStateListAdapter1.equals(abstractItemStateListAdapter2));
        abstractItemStateListAdapter2.triggerItemStateOnClick(
                abstractItemStateListAdapter1.isItemStateTriggeredOnClick());
    }

}