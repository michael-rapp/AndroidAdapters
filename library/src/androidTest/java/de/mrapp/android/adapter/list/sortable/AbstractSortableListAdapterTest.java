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
package de.mrapp.android.adapter.list.sortable;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import de.mrapp.android.adapter.DataSetObserver;
import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.FilterQuery;
import de.mrapp.android.adapter.list.ListAdapter;
import de.mrapp.android.adapter.ListDecorator;
import de.mrapp.android.adapter.Order;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.list.ListAdapterItemClickListener;
import de.mrapp.android.adapter.list.ListAdapterItemLongClickListener;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.logging.LogLevel;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests the functionality of the class {@link AbstractSortableListAdapter}.
 *
 * @author Michael Rapp
 */
public class AbstractSortableListAdapterTest extends AndroidTestCase {

    /**
     * An implementation of the abstract class {@link AbstractSortableListAdapter}, which is needed
     * for test purposes.
     */
    private class AbstractSortableListAdapterImplementation extends
            AbstractSortableListAdapter<ComparableImplementation, ListDecorator<ComparableImplementation>> {

        /**
         * The constant serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        protected final void applyDecorator(@NonNull final Context context,
                                            @NonNull final View view, final int index) {

        }

        /**
         * Creates a new adapter, whose underlying data is managed as a sortable list of arbitrary
         * items.
         *
         * @param context
         *         The context, the adapter should belong to, as an instance of the class {@link
         *         Context}. The context may not be null
         * @param decorator
         *         The decorator, which should be used to customize the appearance of the views,
         *         which are used to visualize the items of the adapter, as an instance of the
         *         generic type DecoratorType. The decorator may not be null
         * @param logLevel
         *         The log level, which should be used for logging, as a value of the enum {@link
         *         LogLevel}. The log level may not be null
         * @param items
         *         A list, which contains the the adapter's items, or an empty list, if the adapter
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
         *         A set, which contains the listeners, which should be notified when the adapter's
         *         underlying data has been modified or an empty set, if no listeners should be
         *         notified
         * @param enableStateListeners
         *         A set, which contains the listeners, which should be notified when an item has
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
         * @param sortingListeners
         *         A set, which contains the listeners, which should be notified, when the adapter's
         *         underlying data has been sorted or an empty set, if no listeners should be
         *         notified
         */
        protected AbstractSortableListAdapterImplementation(final Context context,
                                                            final ListDecorator<ComparableImplementation> decorator,
                                                            final LogLevel logLevel,
                                                            final ArrayList<Item<ComparableImplementation>> items,
                                                            final boolean allowDuplicates,
                                                            final boolean notifyOnChange,
                                                            final Set<ListAdapterItemClickListener<ComparableImplementation>> itemClickListeners,
                                                            final Set<ListAdapterItemLongClickListener<ComparableImplementation>> itemLongClickListeners,
                                                            final Set<ListAdapterListener<ComparableImplementation>> adapterListeners,
                                                            final Set<ListEnableStateListener<ComparableImplementation>> enableStateListeners,
                                                            final int numberOfItemStates,
                                                            final boolean triggerItemStateOnClick,
                                                            final Set<ListItemStateListener<ComparableImplementation>> itemStateListeners,
                                                            final Set<ListSortingListener<ComparableImplementation>> sortingListeners) {
            super(context, decorator, logLevel, items, allowDuplicates, notifyOnChange,
                    itemClickListeners, itemLongClickListeners, adapterListeners,
                    enableStateListeners, numberOfItemStates, triggerItemStateOnClick,
                    itemStateListeners, sortingListeners);
        }

        @Override
        public AbstractSortableListAdapter<ComparableImplementation, ListDecorator<ComparableImplementation>> clone()
                throws CloneNotSupportedException {
            throw new CloneNotSupportedException();
        }

        @Nullable
        @Override
        public List<ComparableImplementation> applyFilter(@NonNull final String query,
                                                          final int flags) {
            return null;
        }

        @Nullable
        @Override
        public List<ComparableImplementation> applyFilter(@NonNull final String query,
                                                          final int flags,
                                                          @NonNull final Filter<ComparableImplementation> filter) {
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
        public void addFilterListener(
                @NonNull final ListFilterListener<ComparableImplementation> listener) {

        }

        @Override
        public void removeFilterListener(
                @NonNull final ListFilterListener<ComparableImplementation> listener) {

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
    private class ListDecoratorImplementation extends ListDecorator<ComparableImplementation> {

        @SuppressWarnings("ConstantConditions")
        @NonNull
        @Override
        public View onInflateView(@NonNull final LayoutInflater inflater,
                                  @Nullable final ViewGroup parent, final int viewType) {
            return null;
        }

        @Override
        public void onShowItem(@NonNull final Context context,
                               @NonNull final ListAdapter<ComparableImplementation> adapter,
                               @NonNull final View view,
                               @NonNull final ComparableImplementation item, final int viewType,
                               final int index, final boolean enabled, final int state,
                               final boolean filtered) {

        }

    }

    /**
     * An implementation of the interface {@link Comparable}, which is needed for test purposes.
     */
    private class ComparableImplementation implements Comparable<ComparableImplementation> {

        /**
         * A value, which is needed for test purposes.
         */
        private int value;

        /**
         * Creates a new comparable implementation.
         *
         * @param value
         *         The value of the comparable implementation
         */
        public ComparableImplementation(final int value) {
            this.value = value;
        }

        @Override
        public int compareTo(@NonNull final ComparableImplementation another) {
            if (value < another.value) {
                return -1;
            } else if (value == another.value) {
                return 0;
            } else {
                return 1;
            }
        }

    }

    /**
     * An implementation of the interface {@link Comparator}, which is needed for test purposes.
     */
    private class ComparatorImplementation implements Comparator<ComparableImplementation> {

        @Override
        public int compare(final ComparableImplementation lhs, final ComparableImplementation rhs) {
            return lhs.compareTo(rhs);
        }

    }

    /**
     * Tests, if all properties are set correctly by the constructor.
     */
    public final void testConstructor() {
        Set<ListSortingListener<ComparableImplementation>> sortingListeners = new LinkedHashSet<>();
        AbstractSortableListAdapterImplementation abstractSortableListAdapter =
                new AbstractSortableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<ComparableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                        sortingListeners);
        assertEquals(sortingListeners, abstractSortableListAdapter.getSortingListeners());
        assertNull(abstractSortableListAdapter.getOrder());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the set, which contains the
     * sorting listeners, which is passed to the constructor, is null.
     */
    public final void testConstructorThrowsExceptionWhenSortingListenersIsNull() {
        try {
            new AbstractSortableListAdapterImplementation(getContext(),
                    new ListDecoratorImplementation(), LogLevel.ALL,
                    new ArrayList<Item<ComparableImplementation>>(), false, true,
                    new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                    new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                    new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                    new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(), 1,
                    false, new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                    null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to add a listener, which should be
     * notified, when the adapter's underlying data has been sorted, to the adapter.
     */
    @SuppressWarnings("unchecked")
    public final void testAddSortingListener() {
        AbstractSortableListAdapterImplementation abstractSortableListAdapter =
                new AbstractSortableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<ComparableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
        ListSortingListener<ComparableImplementation> listSortingListener =
                mock(ListSortingListener.class);
        abstractSortableListAdapter.addSortingListener(listSortingListener);
        assertEquals(1, abstractSortableListAdapter.getSortingListeners().size());
        assertTrue(abstractSortableListAdapter.getSortingListeners().contains(listSortingListener));
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if a {@link ListSortingListener},
     * which is null, should be added to the adapter.
     */
    public final void testAddSortingListenerThrowsExceptionWhenListenerIsNull() {
        try {
            AbstractSortableListAdapterImplementation abstractSortableListAdapter =
                    new AbstractSortableListAdapterImplementation(getContext(),
                            new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<ComparableImplementation>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(),
                            1, false,
                            new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
            abstractSortableListAdapter.addSortingListener(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to remove a listener, which should not be
     * notified, when the adapter's underlying data has been sorted, anymore, from the adapter.
     */
    @SuppressWarnings("unchecked")
    public final void testRemoveSortingListener() {
        AbstractSortableListAdapterImplementation abstractSortableListAdapter =
                new AbstractSortableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<ComparableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
        ListSortingListener<ComparableImplementation> listSortingListener =
                mock(ListSortingListener.class);
        abstractSortableListAdapter.removeSortingListener(listSortingListener);
        abstractSortableListAdapter.addSortingListener(listSortingListener);
        assertFalse(abstractSortableListAdapter.getSortingListeners().isEmpty());
        abstractSortableListAdapter.removeSortingListener(listSortingListener);
        assertTrue(abstractSortableListAdapter.getSortingListeners().isEmpty());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if a {@link ListSortingListener},
     * which is null, should be removed from the adapter.
     */
    public final void testRemoveSortingListenerThrowsExceptionWhenListenerIsNull() {
        try {
            AbstractSortableListAdapterImplementation abstractSortableListAdapter =
                    new AbstractSortableListAdapterImplementation(getContext(),
                            new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<ComparableImplementation>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(),
                            1, false,
                            new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
            abstractSortableListAdapter.removeSortingListener(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to sort the adapter's items in the
     * default order.
     */
    @SuppressWarnings("unchecked")
    public final void testSortByDefaultOrder() {
        ComparableImplementation item1 = new ComparableImplementation(2);
        ComparableImplementation item2 = new ComparableImplementation(1);
        ListSortingListener<ComparableImplementation> listSortingListener =
                mock(ListSortingListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractSortableListAdapterImplementation abstractSortableListAdapter =
                new AbstractSortableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<ComparableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
        abstractSortableListAdapter.addSortingListener(listSortingListener);
        abstractSortableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractSortableListAdapter.addItem(item1);
        abstractSortableListAdapter.addItem(item2);
        dataSetObserver.reset();
        abstractSortableListAdapter.sort();
        assertEquals(0, abstractSortableListAdapter.indexOf(item2));
        assertEquals(1, abstractSortableListAdapter.indexOf(item1));
        verify(listSortingListener, times(1))
                .onSorted(eq(abstractSortableListAdapter), any(Collection.class),
                        eq(Order.ASCENDING), (Comparator<ComparableImplementation>) isNull());
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
        assertEquals(Order.ASCENDING, abstractSortableListAdapter.getOrder());
    }

    /**
     * Tests the functionality of the method, which allows to sort the adapter's items in ascending
     * order.
     */
    @SuppressWarnings("unchecked")
    public final void testSortByAscendingOrder() {
        ComparableImplementation item1 = new ComparableImplementation(2);
        ComparableImplementation item2 = new ComparableImplementation(1);
        Order order = Order.ASCENDING;
        ListSortingListener<ComparableImplementation> listSortingListener =
                mock(ListSortingListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractSortableListAdapterImplementation abstractSortableListAdapter =
                new AbstractSortableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<ComparableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
        abstractSortableListAdapter.addSortingListener(listSortingListener);
        abstractSortableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractSortableListAdapter.addItem(item1);
        abstractSortableListAdapter.addItem(item2);
        dataSetObserver.reset();
        abstractSortableListAdapter.sort(order);
        assertEquals(0, abstractSortableListAdapter.indexOf(item2));
        assertEquals(1, abstractSortableListAdapter.indexOf(item1));
        verify(listSortingListener, times(1))
                .onSorted(eq(abstractSortableListAdapter), any(Collection.class), eq(order),
                        (Comparator<ComparableImplementation>) isNull());
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
        assertEquals(order, abstractSortableListAdapter.getOrder());
    }

    /**
     * Tests the functionality of the method, which allows to sort the adapter's items in descending
     * order.
     */
    @SuppressWarnings("unchecked")
    public final void testSortByDescendingOrder() {
        ComparableImplementation item1 = new ComparableImplementation(1);
        ComparableImplementation item2 = new ComparableImplementation(2);
        Order order = Order.DESCENDING;
        ListSortingListener<ComparableImplementation> listSortingListener =
                mock(ListSortingListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractSortableListAdapterImplementation abstractSortableListAdapter =
                new AbstractSortableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<ComparableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
        abstractSortableListAdapter.addSortingListener(listSortingListener);
        abstractSortableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractSortableListAdapter.addItem(item1);
        abstractSortableListAdapter.addItem(item2);
        dataSetObserver.reset();
        abstractSortableListAdapter.sort(order);
        assertEquals(0, abstractSortableListAdapter.indexOf(item2));
        assertEquals(1, abstractSortableListAdapter.indexOf(item1));
        verify(listSortingListener, times(1))
                .onSorted(eq(abstractSortableListAdapter), any(Collection.class), eq(order),
                        (Comparator<ComparableImplementation>) isNull());
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
        assertEquals(order, abstractSortableListAdapter.getOrder());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the adapter's items should be
     * sorted by an order, which is null.
     */
    public final void testSortThrowsExceptionWhenOrderIsNull() {
        try {
            AbstractSortableListAdapterImplementation abstractSortableListAdapter =
                    new AbstractSortableListAdapterImplementation(getContext(),
                            new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<ComparableImplementation>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(),
                            1, false,
                            new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
            Order order = null;
            abstractSortableListAdapter.sort(order);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to sort the adapter's items using a
     * specific comparator in the default order.
     */
    @SuppressWarnings("unchecked")
    public final void testSortUsingComparatorByDefaultOrder() {
        ComparableImplementation item1 = new ComparableImplementation(2);
        ComparableImplementation item2 = new ComparableImplementation(1);
        ListSortingListener<ComparableImplementation> listSortingListener =
                mock(ListSortingListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractSortableListAdapterImplementation abstractSortableListAdapter =
                new AbstractSortableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<ComparableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
        abstractSortableListAdapter.addSortingListener(listSortingListener);
        abstractSortableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractSortableListAdapter.addItem(item1);
        abstractSortableListAdapter.addItem(item2);
        dataSetObserver.reset();
        Comparator<ComparableImplementation> comparator = new ComparatorImplementation();
        abstractSortableListAdapter.sort(comparator);
        assertEquals(0, abstractSortableListAdapter.indexOf(item2));
        assertEquals(1, abstractSortableListAdapter.indexOf(item1));
        verify(listSortingListener, times(1))
                .onSorted(eq(abstractSortableListAdapter), any(Collection.class),
                        eq(Order.ASCENDING), eq(comparator));
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
        assertEquals(Order.ASCENDING, abstractSortableListAdapter.getOrder());
    }

    /**
     * Tests the functionality of the method, which allows to sort the adapter's items using a
     * specific comparator in ascending order.
     */
    @SuppressWarnings("unchecked")
    public final void testSortUsingComparatorByAscendingOrder() {
        ComparableImplementation item1 = new ComparableImplementation(2);
        ComparableImplementation item2 = new ComparableImplementation(1);
        Order order = Order.ASCENDING;
        ListSortingListener<ComparableImplementation> listSortingListener =
                mock(ListSortingListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractSortableListAdapterImplementation abstractSortableListAdapter =
                new AbstractSortableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<ComparableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
        abstractSortableListAdapter.addSortingListener(listSortingListener);
        abstractSortableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractSortableListAdapter.addItem(item1);
        abstractSortableListAdapter.addItem(item2);
        dataSetObserver.reset();
        Comparator<ComparableImplementation> comparator = new ComparatorImplementation();
        abstractSortableListAdapter.sort(order, comparator);
        assertEquals(0, abstractSortableListAdapter.indexOf(item2));
        assertEquals(1, abstractSortableListAdapter.indexOf(item1));
        verify(listSortingListener, times(1))
                .onSorted(eq(abstractSortableListAdapter), any(Collection.class), eq(order),
                        eq(comparator));
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
        assertEquals(order, abstractSortableListAdapter.getOrder());
    }

    /**
     * Tests the functionality of the method, which allows to sort the adapter's items using a
     * specific comparator in descending order.
     */
    @SuppressWarnings("unchecked")
    public final void testSortUsingComparatorByDescendingOrder() {
        ComparableImplementation item1 = new ComparableImplementation(1);
        ComparableImplementation item2 = new ComparableImplementation(2);
        Order order = Order.DESCENDING;
        ListSortingListener<ComparableImplementation> listSortingListener =
                mock(ListSortingListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractSortableListAdapterImplementation abstractSortableListAdapter =
                new AbstractSortableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<ComparableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
        abstractSortableListAdapter.addSortingListener(listSortingListener);
        abstractSortableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractSortableListAdapter.addItem(item1);
        abstractSortableListAdapter.addItem(item2);
        dataSetObserver.reset();
        Comparator<ComparableImplementation> comparator = new ComparatorImplementation();
        abstractSortableListAdapter.sort(order, comparator);
        assertEquals(0, abstractSortableListAdapter.indexOf(item2));
        assertEquals(1, abstractSortableListAdapter.indexOf(item1));
        verify(listSortingListener, times(1))
                .onSorted(eq(abstractSortableListAdapter), any(Collection.class), eq(order),
                        eq(comparator));
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
        assertEquals(order, abstractSortableListAdapter.getOrder());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the adapter's items should be
     * sorted by an order, which is null, using a specific comparator.
     */
    public final void testSortUsingComparatorThrowsExceptionWhenOrderIsNull() {
        try {
            AbstractSortableListAdapterImplementation abstractSortableListAdapter =
                    new AbstractSortableListAdapterImplementation(getContext(),
                            new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<ComparableImplementation>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(),
                            1, false,
                            new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
            abstractSortableListAdapter.sort(null, new ComparatorImplementation());
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the adapter's items should be
     * sorted in the default order, using a specific comparator, which is null.
     */
    public final void testSortUsingComparatorByDefaultOrderThrowsExceptionWhenComparatorIsNull() {
        try {
            AbstractSortableListAdapterImplementation abstractSortableListAdapter =
                    new AbstractSortableListAdapterImplementation(getContext(),
                            new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<ComparableImplementation>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(),
                            1, false,
                            new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
            Comparator<ComparableImplementation> comparator = null;
            abstractSortableListAdapter.sort(comparator);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the adapter's items should be
     * sorted in a specific order, using a specific comparator, which is null.
     */
    public final void testSortUsingComparatorBySpecificOrderThrowsExceptionWhenComparatorIsNull() {
        try {
            AbstractSortableListAdapterImplementation abstractSortableListAdapter =
                    new AbstractSortableListAdapterImplementation(getContext(),
                            new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<ComparableImplementation>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(),
                            1, false,
                            new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                            new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
            Comparator<ComparableImplementation> comparator = null;
            abstractSortableListAdapter.sort(Order.DESCENDING, comparator);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that the order of the adapter's items is invalidated, when an item is added to the
     * adapter.
     */
    public final void testAddItemInvalidatesOrder() {
        AbstractSortableListAdapterImplementation abstractSortableListAdapter =
                new AbstractSortableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<ComparableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
        ComparableImplementation item1 = new ComparableImplementation(2);
        ComparableImplementation item2 = new ComparableImplementation(1);
        abstractSortableListAdapter.addItem(item1);
        abstractSortableListAdapter.addItem(item2);
        abstractSortableListAdapter.sort();
        assertEquals(Order.ASCENDING, abstractSortableListAdapter.getOrder());
        abstractSortableListAdapter.addItem(new ComparableImplementation(0));
        assertNull(abstractSortableListAdapter.getOrder());
    }

    /**
     * Ensures, that the order of the adapter's items is invalidated, when an item is removed from
     * the adapter.
     */
    public final void testRemoveItemInvalidatesOrder() {
        AbstractSortableListAdapterImplementation abstractSortableListAdapter =
                new AbstractSortableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<ComparableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
        ComparableImplementation item1 = new ComparableImplementation(2);
        ComparableImplementation item2 = new ComparableImplementation(1);
        abstractSortableListAdapter.addItem(item1);
        abstractSortableListAdapter.addItem(item2);
        abstractSortableListAdapter.sort();
        assertEquals(Order.ASCENDING, abstractSortableListAdapter.getOrder());
        abstractSortableListAdapter.removeItem(0);
        assertNull(abstractSortableListAdapter.getOrder());
    }

    /**
     * Tests the functionality of the hashCode-method.
     */
    public final void testHashCode() {
        AbstractSortableListAdapterImplementation abstractSortableListAdapter1 =
                new AbstractSortableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<ComparableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
        AbstractSortableListAdapterImplementation abstractSortableListAdapter2 =
                new AbstractSortableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<ComparableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
        assertEquals(abstractSortableListAdapter1.hashCode(),
                abstractSortableListAdapter1.hashCode());
        assertEquals(abstractSortableListAdapter1.hashCode(),
                abstractSortableListAdapter2.hashCode());
        abstractSortableListAdapter1.sort();
        assertNotSame(abstractSortableListAdapter1.hashCode(),
                abstractSortableListAdapter2.hashCode());
    }

    /**
     * Tests the functionality of the equals-method.
     */
    public final void testEquals() {
        AbstractSortableListAdapterImplementation abstractSortableListAdapter1 =
                new AbstractSortableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<ComparableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
        AbstractSortableListAdapterImplementation abstractSortableListAdapter2 =
                new AbstractSortableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<ComparableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
        assertTrue(abstractSortableListAdapter1.equals(abstractSortableListAdapter1));
        assertTrue(abstractSortableListAdapter1.equals(abstractSortableListAdapter2));
        assertFalse(abstractSortableListAdapter1.equals(null));
        assertFalse(abstractSortableListAdapter1.equals(new Object()));
        abstractSortableListAdapter1.sort();
        assertFalse(abstractSortableListAdapter1.equals(abstractSortableListAdapter2));
    }

    /**
     * Tests the functionality of the onSaveInstanceState-method.
     */
    public final void testOnSaveInstanceState() {
        Bundle outState = new Bundle();
        Order order = Order.DESCENDING;
        AbstractSortableListAdapterImplementation abstractSortableListAdapter =
                new AbstractSortableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<ComparableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
        abstractSortableListAdapter.sort(order);
        abstractSortableListAdapter.onSaveInstanceState(outState);
        Order savedOrder =
                (Order) outState.getSerializable(AbstractSortableListAdapter.ORDER_BUNDLE_KEY);
        assertEquals(savedOrder, order);
    }

    /**
     * Tests the functionality of the onRestoreInstanceState-method.
     */
    public final void testOnRestoreInstanceState() {
        String key = "keyadapter";
        Order order = Order.DESCENDING;
        AbstractSortableListAdapterImplementation abstractSortableListAdapter =
                new AbstractSortableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<ComparableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<ComparableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<ComparableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<ComparableImplementation>>());
        Bundle savedInstanceState = new Bundle();
        abstractSortableListAdapter.onSaveInstanceState(savedInstanceState, key);
        Bundle savedState = savedInstanceState.getBundle(key);
        savedState.putSerializable(AbstractSortableListAdapter.ORDER_BUNDLE_KEY, order);
        abstractSortableListAdapter.onRestoreInstanceState(savedInstanceState, key);
        assertEquals(order, abstractSortableListAdapter.getOrder());
    }

}