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
package de.mrapp.android.adapter.list.itemstate;

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
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import de.mrapp.android.adapter.DataSetObserver;
import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.FilterQuery;
import de.mrapp.android.adapter.ListAdapter;
import de.mrapp.android.adapter.ListDecorator;
import de.mrapp.android.adapter.Order;
import de.mrapp.android.adapter.R;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.inflater.InflaterFactory;
import de.mrapp.android.adapter.list.ListAdapterItemClickListener;
import de.mrapp.android.adapter.list.ListAdapterItemLongClickListener;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.adapter.logging.LogLevel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests the functionality of the class {@link AbstractItemStateListAdapter}.
 *
 * @author Michael Rapp
 */
public class AbstractItemStateListAdapterTest extends AndroidTestCase {

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
         * @param inflater
         *         The inflater, which should be used to inflate the views, which are used to
         *         visualize the adapter's items, as an instance of the type {@link Inflater}. The
         *         inflater may not be null
         * @param decorator
         *         The decorator, which should be used to customize the appearance of the views,
         *         which are used to visualize the items of the adapter, as an instance of the
         *         generic type DecoratorType. The decorator may not be null
         * @param logLevel
         *         The log level, which should be used for logging, as a value of the enum {@link
         *         LogLevel}. The log level may not be null
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
                                                             final Inflater inflater,
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
            super(context, inflater, decorator, logLevel, items, allowDuplicates, notifyOnChange,
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

        @Override
        public boolean applyFilter(@NonNull final String query, final int flags) {
            return false;
        }

        @Override
        public boolean applyFilter(@NonNull final String query, final int flags,
                                   @NonNull final Filter<Object> filter) {
            return false;
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
        public int getItemViewType(final int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
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

    /**
     * Tests, if all properties are set correctly by the constructor.
     */
    public final void testConstructor() {
        int numberOfItemStates = 2;
        boolean triggerItemStateOnClick = true;
        Set<ListItemStateListener<Object>> itemStateListeners = new LinkedHashSet<>();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the set, which contains the item
     * state listeners, which is passed to the constructor, is null.
     */
    public final void testConstructorThrowsExceptionWhenItemStateListenersIsNull() {
        try {
            new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
                    new ListDecoratorImplementation(), LogLevel.ALL, new ArrayList<Item<Object>>(),
                    false, true, new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                    new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                    new LinkedHashSet<ListAdapterListener<Object>>(),
                    new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false, null);
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that a {@link IllegalArgumentException} is thrown, if the number of item states,
     * which is passed to the constructor, is less than 1.
     */
    public final void testConstructorThrowsExceptionWhenNumberOfItemStatesIsLessThanOne() {
        try {
            new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
                    new ListDecoratorImplementation(), LogLevel.ALL, new ArrayList<Item<Object>>(),
                    false, true, new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                    new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                    new LinkedHashSet<ListAdapterListener<Object>>(),
                    new LinkedHashSet<ListEnableStateListener<Object>>(), 0, false,
                    new LinkedHashSet<ListItemStateListener<Object>>());
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to set the number of item states.
     */
    @SuppressWarnings("unchecked")
    public final void testSetNumberOfItemStates() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the method, which allows to
     * set the number of item states, if the number of states is less than 1.
     */
    public final void testSetNumberOfItemStatesThrowsExceptionWhenNumberOfStatesIsLessThanOne() {
        try {
            AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                    new AbstractItemStateListAdapterImplementation(getContext(),
                            mock(Inflater.class), new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                            new LinkedHashSet<ListItemStateListener<Object>>());
            abstractItemStateListAdapter.setNumberOfItemStates(0);
            Assert.fail();
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the minimum item state.
     */
    public final void testMinItemState() {
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        assertEquals(0, abstractItemStateListAdapter.minItemState());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the maximum item state.
     */
    public final void testMaxItemState() {
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the state of the item, which
     * belongs to a specific index.
     */
    public final void testGetItemStateByIndex() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Ensures, that an {@link IndexOutOfBoundsException} is thrown by the method, which allows to
     * retrieve the state of the item, which belongs to a specific index, if the index is invalid.
     */
    public final void testGetItemStateByIndexThrowsExceptionWhenIndexIsInvalid() {
        try {
            AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                    new AbstractItemStateListAdapterImplementation(getContext(),
                            mock(Inflater.class), new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                            new LinkedHashSet<ListItemStateListener<Object>>());
            abstractItemStateListAdapter.getItemState(-1);
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the state of a specific
     * item.
     */
    public final void testGetItemState() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Ensures, that a {@link NoSuchElementException} is thrown by the method, which allows to
     * retrieve the state of a specific item, if the item does not belong to the adapter.
     */
    public final void testGetItemStateThrowsExceptionWhenAdapterDoesNotContainItem() {
        try {
            AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                    new AbstractItemStateListAdapterImplementation(getContext(),
                            mock(Inflater.class), new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                            new LinkedHashSet<ListItemStateListener<Object>>());
            abstractItemStateListAdapter.getItemState(new Object());
            Assert.fail();
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to
     * retrieve the state of a specific item, if the item is null.
     */
    public final void testGetItemStateThrowsExceptionWhenItemIsNull() {
        try {
            AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                    new AbstractItemStateListAdapterImplementation(getContext(),
                            mock(Inflater.class), new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                            new LinkedHashSet<ListItemStateListener<Object>>());
            abstractItemStateListAdapter.getItemState(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to set the state of the item, which
     * belongs to a specific index.
     */
    @SuppressWarnings("unchecked")
    public final void testSetItemStateByIndex() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to set the state of the item, which
     * belongs to a specific index, if the item is disabled.
     */
    @SuppressWarnings("unchecked")
    public final void testSetItemStateByIndexWhenItemIsDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to set the state of the item, which
     * belongs to a specific index, if the state is already set.
     */
    @SuppressWarnings("unchecked")
    public final void testSetItemStateByIndexWhenStateIsAlreadySet() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Ensures, that an {@link IndexOutOfBoundsException} is thrown by the method, which allows to
     * set the state of the item, which belongs to a specific index, if the index is invalid.
     */
    public final void testSetItemStateByIndexThrowsExceptionWhenIndexIsInvalid() {
        try {
            AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                    new AbstractItemStateListAdapterImplementation(getContext(),
                            mock(Inflater.class), new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                            new LinkedHashSet<ListItemStateListener<Object>>());
            abstractItemStateListAdapter.setItemState(-1, 0);
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the method, which allows to
     * set the state of the item, which belongs to a specific index, if the state is less than 0.
     */
    public final void testSetItemStateByIndexThrowsExceptionWhenStateIsLessThanZero() {
        try {
            AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                    new AbstractItemStateListAdapterImplementation(getContext(),
                            mock(Inflater.class), new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                            new LinkedHashSet<ListItemStateListener<Object>>());
            abstractItemStateListAdapter.addItem(new Object());
            abstractItemStateListAdapter.setItemState(0, -1);
            Assert.fail();
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the method, which allows to
     * set the state of the item, which belongs to a specific index, if the state is greater than
     * the maximum state.
     */
    public final void testSetItemStateByIndexThrowsExceptionWhenStateIsGreaterThanMaxState() {
        try {
            AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                    new AbstractItemStateListAdapterImplementation(getContext(),
                            mock(Inflater.class), new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                            new LinkedHashSet<ListItemStateListener<Object>>());
            abstractItemStateListAdapter.addItem(new Object());
            abstractItemStateListAdapter.setItemState(0, 2);
            Assert.fail();
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to set the state of a specific item.
     */
    @SuppressWarnings("unchecked")
    public final void testSetItemState() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to set the state of a specific item, if
     * the item is disabled.
     */
    @SuppressWarnings("unchecked")
    public final void testSetItemStateWhenItemIsDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to set the state of a specific item, if
     * the state is already set.
     */
    @SuppressWarnings("unchecked")
    public final void testSetItemStateWhenStateIsAlreadySet() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Ensures, that an {@link NullPointerException} is thrown by the method, which allows to set
     * the state of a specific item, if the item is null.
     */
    public final void testSetItemStateThrowsExceptionWhenItemIsNull() {
        try {
            AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                    new AbstractItemStateListAdapterImplementation(getContext(),
                            mock(Inflater.class), new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                            new LinkedHashSet<ListItemStateListener<Object>>());
            abstractItemStateListAdapter.setItemState(null, 0);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the method, which allows to
     * set the state of a specific item, if the state is less than 0.
     */
    public final void testSetItemStateThrowsExceptionWhenStateIsLessThanZero() {
        try {
            Object item = new Object();
            AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                    new AbstractItemStateListAdapterImplementation(getContext(),
                            mock(Inflater.class), new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                            new LinkedHashSet<ListItemStateListener<Object>>());
            abstractItemStateListAdapter.addItem(item);
            abstractItemStateListAdapter.setItemState(item, -1);
            Assert.fail();
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the method, which allows to
     * set the state of a specific item, if the state is greater than the maximum state.
     */
    public final void testSetItemStateThrowsExceptionWhenStateIsGreaterThanMaxState() {
        try {
            Object item = new Object();
            AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                    new AbstractItemStateListAdapterImplementation(getContext(),
                            mock(Inflater.class), new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                            new LinkedHashSet<ListItemStateListener<Object>>());
            abstractItemStateListAdapter.addItem(item);
            abstractItemStateListAdapter.setItemState(item, 2);
            Assert.fail();
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to set the states of all items to a
     * specific state.
     */
    @SuppressWarnings("unchecked")
    public final void testSetAllItemStates() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to set the states of all items to a
     * specific state, if not all states are changed.
     */
    @SuppressWarnings("unchecked")
    public final void testSetAllItemStatesWhenNotAllStatesAreChanged() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the method, which allows to
     * set the states of all items to a specific state, if the state is less than 0.
     */
    public final void testSetAllItemStatesThrowsExceptionWhenStateIsLessThanZero() {
        try {
            AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                    new AbstractItemStateListAdapterImplementation(getContext(),
                            mock(Inflater.class), new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                            new LinkedHashSet<ListItemStateListener<Object>>());
            abstractItemStateListAdapter.setAllItemStates(-1);
            Assert.fail();
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the method, which allows to
     * set the states of all items to a specific state, if the state is greater than the maximum
     * state.
     */
    public final void testSetAllItemStatesThrowsExceptionWhenStateIsGreaterThanMaxState() {
        try {
            AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                    new AbstractItemStateListAdapterImplementation(getContext(),
                            mock(Inflater.class), new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                            new LinkedHashSet<ListItemStateListener<Object>>());
            abstractItemStateListAdapter.setAllItemStates(2);
            Assert.fail();
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to trigger the state of the item, which
     * belongs to a specific index, if the item's state is not already the maximum state.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerItemStateByIndexWhenStateIsNotMaxState() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to trigger the state of the item, which
     * belongs to a specific index, if the item's state is already the maximum state.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerItemStateByIndexWhenStateIsMaxState() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to trigger the state of the item, which
     * belongs to a specific index, if the item is disabled.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerItemStateByIndexWhenItemIsDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Ensures, that an {@link IndexOutOfBoundsException} is thrown by the method, which allows to
     * trigger the state of the item, which belongs to a specific index, if the index is invalid.
     */
    public final void testTriggerItemStateByIndexThrowsExceptionWhenIndexIsInvalid() {
        try {
            AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                    new AbstractItemStateListAdapterImplementation(getContext(),
                            mock(Inflater.class), new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                            new LinkedHashSet<ListItemStateListener<Object>>());
            abstractItemStateListAdapter.triggerItemState(-1);
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to trigger the state of a specific item,
     * if the item's state is not already the maximum state.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerItemStateWhenStateIsNotMaxState() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to trigger the state of a specific item,
     * if the item's state is already the maximum state.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerItemStateWhenStateIsMaxState() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to trigger the state of a specific item,
     * if the item is disabled.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerItemStateWhenItemIsDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to trigger
     * the state of a specific item, if the item is null.
     */
    public final void testTriggerItemStateThrowsExceptionWhenItemIsNull() {
        try {
            AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                    new AbstractItemStateListAdapterImplementation(getContext(),
                            mock(Inflater.class), new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                            new LinkedHashSet<ListItemStateListener<Object>>());
            abstractItemStateListAdapter.triggerItemState(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that a {@link NoSuchElementException} is thrown by the method, which allows to
     * trigger the state of a specific item, if the item does not belong to the adapter.
     */
    public final void testTriggerItemStateThrowsExceptionWhenAdapterDoesNotContainItem() {
        try {
            AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                    new AbstractItemStateListAdapterImplementation(getContext(),
                            mock(Inflater.class), new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                            new LinkedHashSet<ListItemStateListener<Object>>());
            abstractItemStateListAdapter.triggerItemState(new Object());
            Assert.fail();
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to trigger the states of all items.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerAllItemStates() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to trigger the states of all items, if
     * not all states are changed.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerAllItemStatesWhenNotAllStatesAreChanged() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the first item,
     * which has a specific state.
     */
    public final void testGetFirstIndexWithSpecificState() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the first item,
     * which has a specific state, if no item has the state.
     */
    public final void testGetFirstIndexWithSpecificStateWhenNoItemHasState() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the first item,
     * which has a specific state, if the adapter is empty.
     */
    public final void testGetFirstIndexWithSpecificStateWhenAdapterIsEmpty() {
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        assertEquals(-1, abstractItemStateListAdapter.getFirstIndexWithSpecificState(0));
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the first item, which has a
     * specific state.
     */
    public final void testGetFirstItemWithSpecificState() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the first item, which has a
     * specific state, if no item has the state.
     */
    public final void testGetFirstItemWithSpecificStateWhenNoItemHasState() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the first item, which has a
     * specific state, if the adapter is empty.
     */
    public final void testGetFirstItemWithSpecificStateWhenAdapterIsEmpty() {
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        assertNull(abstractItemStateListAdapter.getFirstItemWithSpecificState(0));
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the last item,
     * which has a specific state.
     */
    public final void testGetLastIndexWithSpecificState() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the last item,
     * which has a specific state, if no item has the state.
     */
    public final void testGetLastIndexWithSpecificStateWhenNoItemHasState() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the last item,
     * which has a specific state, if the adapter is empty.
     */
    public final void testGetLastIndexWithSpecificStateWhenAdapterIsEmpty() {
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        assertEquals(-1, abstractItemStateListAdapter.getLastIndexWithSpecificState(0));
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the last item, which has a
     * specific state.
     */
    public final void testGetLastItemWithSpecificState() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the last item, which has a
     * specific state, if no item has the state.
     */
    public final void testGetLastItemWithSpecificStateWhenNoItemHasState() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the last item, which has a
     * specific state, if the adapter is empty.
     */
    public final void testGetLastItemWithSpecificStateWhenAdapterIsEmpty() {
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        assertNull(abstractItemStateListAdapter.getLastItemWithSpecificState(0));
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * the indices of all items, which have a specific state.
     */
    public final void testGetIndicesWithSpecificState() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * the indices of all items, which have a specific state, if no item has the state.
     */
    public final void testGetIndicesWithSpecificStateWhenNoItemHasState() {
        Object item = new Object();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * the indices of all items, which have a specific state, if the adapter is empty.
     */
    public final void testGetIndicesWithSpecificStateWhenAdapterIsEmpty() {
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * all items, which have a specific state.
     */
    public final void testGetItemsWithSpecificState() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * all items, which have a specific state, if no item has the state.
     */
    public final void testGetItemsWithSpecificStateWhenNoItemHasState() {
        Object item = new Object();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * all items, which have a specific state, if the adapter is empty.
     */
    public final void testGetItemsWithSpecificStateWhenAdapterIsEmpty() {
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the number of items, which
     * have a specific state.
     */
    public final void testGetItemStateCountWithSpecificState() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the number of items, which
     * have a specific state, if no item has the state.
     */
    public final void testGetItemStateCountWhenNotItemHasState() {
        Object item1 = new Object();
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the number of items, which
     * have a specific state, if the adapter is empty.
     */
    public final void testGetItemStateCountWhenAdapterIsEmpty() {
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        assertEquals(0, abstractItemStateListAdapter.getItemStateCount(0));
    }

    /**
     * Tests the functionality of the method, which allows to set, whether the state of an item
     * should be triggered, when it is clicked by the user, or not.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerItemStateOnClick() {
        Object item = new Object();
        Inflater inflater = InflaterFactory.createInflater(R.layout.view);
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListItemStateListener<Object> itemStateListener = mock(ListItemStateListener.class);
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), inflater,
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        abstractItemStateListAdapter.registerDataSetObserver(dataSetObserver);
        abstractItemStateListAdapter.addItemStateListener(itemStateListener);
        abstractItemStateListAdapter.addItem(item);
        View view = abstractItemStateListAdapter.getView(0, null, null);
        dataSetObserver.reset();
        view.performClick();
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
        verify(itemStateListener, times(0))
                .onItemStateChanged(abstractItemStateListAdapter, item, 0, 1);
        abstractItemStateListAdapter.triggerItemStateOnClick(true);
        assertTrue(abstractItemStateListAdapter.isItemStateTriggeredOnClick());
        view.performClick();
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
        verify(itemStateListener, times(1))
                .onItemStateChanged(abstractItemStateListAdapter, item, 0, 1);
    }

    /**
     * Tests the functionality of the method, which allows to add a listener, which should be
     * notified, when the state of an item has been changed.
     */
    @SuppressWarnings("unchecked")
    public final void testAddItemStateListener() {
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if a {@link ListItemStateListener},
     * which is null, should be added to the adapter.
     */
    public final void testAddItemStateListenerThrowsExceptionWhenListenerIsNull() {
        try {
            AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                    new AbstractItemStateListAdapterImplementation(getContext(),
                            mock(Inflater.class), new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                            new LinkedHashSet<ListItemStateListener<Object>>());
            abstractItemStateListAdapter.addItemStateListener(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to remove a listener, which should not be
     * notified, when the state of an item has been changed, anymore.
     */
    @SuppressWarnings("unchecked")
    public final void testRemoveItemStateListener() {
        ListItemStateListener<Object> listItemStateListener = mock(ListItemStateListener.class);
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the onSaveInstanceState-method.
     */
    public final void testOnSaveInstanceState() {
        Bundle outState = new Bundle();
        int numberOfItemStates = 2;
        boolean triggerItemStateOnClick = true;
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the onRestoreInstanceState-method.
     */
    public final void testOnRestoreInstanceState() {
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the hashCode-method.
     */
    public final void testHashCode() {
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter1 =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter2 =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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

    /**
     * Tests the functionality of the equals-method.
     */
    public final void testEquals() {
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter1 =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>());
        AbstractItemStateListAdapterImplementation abstractItemStateListAdapter2 =
                new AbstractItemStateListAdapterImplementation(getContext(), mock(Inflater.class),
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