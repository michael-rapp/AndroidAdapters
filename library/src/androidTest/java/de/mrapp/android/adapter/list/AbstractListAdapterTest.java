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
package de.mrapp.android.adapter.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
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
import de.mrapp.android.adapter.ParcelableImplementation;
import de.mrapp.android.adapter.R;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.util.logging.LogLevel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests the functionality of the class {@link AbstractListAdapter}.
 *
 * @author Michael Rapp
 */
@RunWith(AndroidJUnit4.class)
public class AbstractListAdapterTest {

    /**
     * An implementation of the abstract class {@link AbstractListAdapter}, which is needed for test
     * purposes.
     */
    private class AbstractListAdapterImplementation
            extends AbstractListAdapter<Object, ListDecorator<Object>> {

        /**
         * The constant serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        protected final void applyDecorator(@NonNull final Context context,
                                            @NonNull final View view, final int index) {
            getDecorator()
                    .applyDecorator(context, this, view, getItem(index), index, true, 0, false);
        }

        @Override
        protected final void onSaveInstanceState(@NonNull final Bundle savedState) {

        }

        @Override
        protected final void onRestoreInstanceState(@NonNull final Bundle savedState) {

        }

        /**
         * Creates a new adapter, whose underlying data is managed as a list of arbitrary items.
         *
         * @param context
         *         The context, the adapter belongs to, as an instance of the class {@link Context}.
         *         The context may not be null
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
         *         underlying data has been modified, as an instance of the type {@link Set} or an
         *         empty set, if no listeners should be notified
         */
        protected AbstractListAdapterImplementation(final Context context,
                                                    final ListDecorator<Object> decorator,
                                                    final LogLevel logLevel,
                                                    final ArrayList<Item<Object>> items,
                                                    final boolean allowDuplicates,
                                                    final boolean notifyOnChange,
                                                    final Set<ListAdapterItemClickListener<Object>> itemClickListeners,
                                                    final Set<ListAdapterItemLongClickListener<Object>> itemLongClickListeners,
                                                    final Set<ListAdapterListener<Object>> adapterListeners) {
            super(context, decorator, logLevel, items, allowDuplicates, notifyOnChange,
                    itemClickListeners, itemLongClickListeners, adapterListeners);
        }

        @Override
        public boolean isEnabled(@NonNull final Object item) {
            return false;
        }

        @Override
        public int getFirstEnabledIndex() {
            return 0;
        }

        @Override
        public Object getFirstEnabledItem() {
            return null;
        }

        @Override
        public int getLastEnabledIndex() {
            return 0;
        }

        @Override
        public Object getLastEnabledItem() {
            return null;
        }

        @Override
        public int getFirstDisabledIndex() {
            return 0;
        }

        @Override
        public Object getFirstDisabledItem() {
            return null;
        }

        @Override
        public int getLastDisabledIndex() {
            return 0;
        }

        @Override
        public Object getLastDisabledItem() {
            return null;
        }

        @Override
        public List<Integer> getEnabledIndices() {
            return null;
        }

        @Override
        public List<Object> getEnabledItems() {
            return null;
        }

        @Override
        public List<Integer> getDisabledIndices() {
            return null;
        }

        @Override
        public List<Object> getDisabledItems() {
            return null;
        }

        @Override
        public int getEnabledItemCount() {
            return 0;
        }

        @Override
        public void setEnabled(final int index, final boolean enabled) {

        }

        @Override
        public void setEnabled(@NonNull final Object item, final boolean enabled) {

        }

        @Override
        public boolean triggerEnableState(final int index) {
            return false;
        }

        @Override
        public boolean triggerEnableState(@NonNull final Object item) {
            return false;
        }

        @Override
        public void setAllEnabled(final boolean enabled) {

        }

        @Override
        public void triggerAllEnableStates() {

        }

        @Override
        public void addEnableStateListener(
                @NonNull final ListEnableStateListener<Object> listener) {

        }

        @Override
        public void removeEnableStateListener(
                @NonNull final ListEnableStateListener<Object> listener) {

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
        public int setItemState(@NonNull final Object item, final int state) {
            return 0;
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
        public void addItemStateListener(@NonNull final ListItemStateListener<Object> listener) {

        }

        @Override
        public void removeItemStateListener(@NonNull final ListItemStateListener<Object> listener) {

        }

        @Override
        public boolean isItemStateTriggeredOnClick() {
            return false;
        }

        @Override
        public void triggerItemStateOnClick(final boolean triggerItemStateOnClick) {

        }

        @Override
        public void setNumberOfItemStates(final int numberOfItemStates) {

        }

        @Override
        public AbstractListAdapter<Object, ListDecorator<Object>> clone()
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
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override
        public boolean isEnabled(final int position) {
            return true;
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

        /**
         * True, if the method <code>onShowItem(...):void</code> has been invoked, false otherwise.
         */
        private boolean hasOnShowItemBeenInvoked;

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
            hasOnShowItemBeenInvoked = true;
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
        private AbstractListAdapterTest getOuterType() {
            return AbstractListAdapterTest.this;
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
     * Creates and returns an instance of the class {@link AbstractListAdapterImplementation}, which
     * can be used for test purposes.
     *
     * @return The instance, which has been created
     */
    private AbstractListAdapterImplementation createAdapter() {
        return createAdapter(false);
    }

    /**
     * Creates and returns an instance of the class {@link AbstractListAdapterImplementation}, which
     * can be used for test purposes.
     *
     * @param allowDuplicates
     *         True , if the adapter should allow duplicate items, false otherwise
     * @return The instance, which has been created
     */
    private AbstractListAdapterImplementation createAdapter(final boolean allowDuplicates) {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        return new AbstractListAdapterImplementation(context, new ListDecoratorImplementation(),
                LogLevel.ALL, new ArrayList<Item<Object>>(), allowDuplicates, true,
                new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                new LinkedHashSet<ListAdapterListener<Object>>());
    }

    @Test
    public final void testCloneItems() throws CloneNotSupportedException {
        Object item1 = new SerializableImplementation(1);
        Object item2 = new SerializableImplementation(2);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(item1);
        abstractListAdapter.addItem(item2);
        List<Item<Object>> clonedItems = abstractListAdapter.cloneItems();
        assertEquals(item1, clonedItems.get(0).getData());
        assertEquals(item2, clonedItems.get(1).getData());
    }

    @Test
    public final void testConstructor() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        ListDecorator<Object> decorator = new ListDecoratorImplementation();
        ArrayList<Item<Object>> items = new ArrayList<>();
        boolean allowDuplicates = true;
        boolean notifyOnChange = true;
        LogLevel logLevel = LogLevel.ERROR;
        Set<ListAdapterItemClickListener<Object>> itemClickListeners = new LinkedHashSet<>();
        Set<ListAdapterItemLongClickListener<Object>> itemLongClickListeners =
                new LinkedHashSet<>();
        Set<ListAdapterListener<Object>> adapterListeners = new LinkedHashSet<>();
        AbstractListAdapterImplementation abstractListAdapter =
                new AbstractListAdapterImplementation(context, decorator, logLevel, items,
                        allowDuplicates, notifyOnChange, itemClickListeners, itemLongClickListeners,
                        adapterListeners);
        assertEquals(context, abstractListAdapter.getContext());
        assertEquals(decorator, abstractListAdapter.getDecorator());
        assertEquals(items, abstractListAdapter.getItems());
        assertNull(abstractListAdapter.getParameters());
        assertEquals(allowDuplicates, abstractListAdapter.areDuplicatesAllowed());
        assertEquals(notifyOnChange, abstractListAdapter.isNotifiedOnChange());
        assertEquals(itemClickListeners, abstractListAdapter.getItemClickListeners());
        assertEquals(adapterListeners, abstractListAdapter.getAdapterListeners());
        assertEquals(logLevel, abstractListAdapter.getLogLevel());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionWhenContextIsNull() {
        new AbstractListAdapterImplementation(null, new ListDecoratorImplementation(), LogLevel.ALL,
                new ArrayList<Item<Object>>(), false, true,
                new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                new LinkedHashSet<ListAdapterListener<Object>>());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionWhenDecoratorIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        new AbstractListAdapterImplementation(context, null, LogLevel.ALL,
                new ArrayList<Item<Object>>(), false, true,
                new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                new LinkedHashSet<ListAdapterListener<Object>>());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionWhenItemsIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        new AbstractListAdapterImplementation(context, new ListDecoratorImplementation(),
                LogLevel.ALL, null, false, true,
                new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                new LinkedHashSet<ListAdapterListener<Object>>());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionWhenItemClickListenersIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        new AbstractListAdapterImplementation(context, new ListDecoratorImplementation(),
                LogLevel.ALL, new ArrayList<Item<Object>>(), false, true, null,
                new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                new LinkedHashSet<ListAdapterListener<Object>>());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionWhenItemLongClickListenersIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        new AbstractListAdapterImplementation(context, new ListDecoratorImplementation(),
                LogLevel.ALL, new ArrayList<Item<Object>>(), false, true,
                new LinkedHashSet<ListAdapterItemClickListener<Object>>(), null,
                new LinkedHashSet<ListAdapterListener<Object>>());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionWhenAdapterListenersIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        new AbstractListAdapterImplementation(context, new ListDecoratorImplementation(),
                LogLevel.ALL, new ArrayList<Item<Object>>(), false, true,
                new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionWhenLogLevelIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        new AbstractListAdapterImplementation(context, new ListDecoratorImplementation(), null,
                new ArrayList<Item<Object>>(), false, true,
                new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                new LinkedHashSet<ListAdapterListener<Object>>());
    }

    @Test
    public final void testSetLogLevel() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        LogLevel logLevel = LogLevel.ERROR;
        abstractListAdapter.setLogLevel(logLevel);
        assertEquals(logLevel, abstractListAdapter.getLogLevel());
    }

    @Test
    public final void testSetParameters() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        Bundle parameters = new Bundle();
        abstractListAdapter.setParameters(parameters);
        assertEquals(parameters, abstractListAdapter.getParameters());
    }

    @Test
    public final void testAllowDuplicates() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        boolean allowDuplicates = true;
        abstractListAdapter.allowDuplicates(allowDuplicates);
        assertEquals(allowDuplicates, abstractListAdapter.areDuplicatesAllowed());
    }

    @Test
    public final void testNotifyOnChange() {
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.notifyObserversOnDataSetChanged();
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
        dataSetObserver.reset();
        boolean notifyOnChange = false;
        abstractListAdapter.notifyOnChange(notifyOnChange);
        assertEquals(notifyOnChange, abstractListAdapter.isNotifiedOnChange());
        abstractListAdapter.notifyObserversOnDataSetChanged();
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAdapterListener() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        assertTrue(abstractListAdapter.getAdapterListeners().isEmpty());
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        assertEquals(1, abstractListAdapter.getAdapterListeners().size());
        assertTrue(abstractListAdapter.getAdapterListeners().contains(listAdapterListener));
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddAdapterListenerThrowsExceptionWhenListenerIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addAdapterListener(null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemoveAdapterListener() {
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.removeAdapterListener(listAdapterListener);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        assertFalse(abstractListAdapter.getAdapterListeners().isEmpty());
        abstractListAdapter.removeAdapterListener(listAdapterListener);
        assertTrue(abstractListAdapter.getAdapterListeners().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testRemoveAdapterListenerThrowsExceptionWhenListenerIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.removeAdapterListener(null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddItem() {
        Object item = new Object();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        int index = abstractListAdapter.addItem(item);
        assertEquals(0, index);
        assertTrue(abstractListAdapter.containsItem(item));
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddItemThrowsExceptionWhenItemIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
        abstractListAdapter.addItem(null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddItemWhenDuplicatesAreAllowed() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.addItem(item);
        dataSetObserver.reset();
        int index = abstractListAdapter.addItem(item);
        assertEquals(1, index);
        assertTrue(abstractListAdapter.containsItem(item));
        assertEquals(2, abstractListAdapter.getCount());
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item, 0);
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddItemWhenDuplicatesAreNotAllowed() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.addItem(item);
        dataSetObserver.reset();
        int index = abstractListAdapter.addItem(item);
        assertEquals(-1, index);
        assertTrue(abstractListAdapter.containsItem(item));
        assertEquals(1, abstractListAdapter.getCount());
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item, 0);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddItemAtSpecificIndex() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.addItem(item1);
        dataSetObserver.reset();
        boolean added = abstractListAdapter.addItem(0, item2);
        assertTrue(added);
        assertTrue(abstractListAdapter.containsItem(item2));
        assertEquals(2, abstractListAdapter.getCount());
        assertEquals(0, abstractListAdapter.indexOf(item2));
        assertEquals(1, abstractListAdapter.indexOf(item1));
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item1, 0);
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item2, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddItemAtSpecificIndexThrowsExceptionWhenItemIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(0, (Object) null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testAddItemAtSpecificIndexThrowsExceptionWhenIndexIsInvalid() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
        abstractListAdapter.addItem(-1, new Object());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddItemAtSpecificIndexWhenDuplicatesAreAllowed() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.addItem(item);
        dataSetObserver.reset();
        boolean added = abstractListAdapter.addItem(0, item);
        assertTrue(added);
        assertTrue(abstractListAdapter.containsItem(item));
        assertEquals(2, abstractListAdapter.getCount());
        assertEquals(0, abstractListAdapter.indexOf(item));
        verify(listAdapterListener, times(2)).onItemAdded(abstractListAdapter, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddItemAtSpecificIndexWhenDuplicatesAreNotAllowed() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.addItem(item);
        dataSetObserver.reset();
        boolean added = abstractListAdapter.addItem(0, item);
        assertFalse(added);
        assertTrue(abstractListAdapter.containsItem(item));
        assertEquals(1, abstractListAdapter.getCount());
        assertEquals(0, abstractListAdapter.indexOf(item));
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item, 0);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllItemsFromCollection() {
        Object item1 = new Object();
        Object item2 = new Object();
        Collection<Object> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        dataSetObserver.reset();
        boolean added = abstractListAdapter.addAllItems(items);
        assertTrue(added);
        assertTrue(abstractListAdapter.containsItem(item1));
        assertTrue(abstractListAdapter.containsItem(item2));
        assertEquals(2, abstractListAdapter.getCount());
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item1, 0);
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddAllItemsFromCollectionThrowsExceptionWhenCollectionIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        Collection<Object> items = null;
        abstractListAdapter.addAllItems(items);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllItemsFromCollectionContainsDuplicatesWhenDuplicatesAreAllowed() {
        Object item = new Object();
        Collection<Object> items = new ArrayList<>();
        items.add(item);
        items.add(item);
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        dataSetObserver.reset();
        boolean added = abstractListAdapter.addAllItems(items);
        assertTrue(added);
        assertTrue(abstractListAdapter.containsItem(item));
        assertEquals(2, abstractListAdapter.getCount());
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item, 0);
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllItemsFromCollectionContainsDuplicatesWhenDuplicatesAreNotAllowed() {
        Object item = new Object();
        Collection<Object> items = new ArrayList<>();
        items.add(item);
        items.add(item);
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        dataSetObserver.reset();
        boolean added = abstractListAdapter.addAllItems(items);
        assertFalse(added);
        assertTrue(abstractListAdapter.containsItem(item));
        assertEquals(1, abstractListAdapter.getCount());
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllItemsFromCollectionAtSpecificIndex() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        Collection<Object> items = new ArrayList<>();
        items.add(item2);
        items.add(item3);
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.addItem(item1);
        dataSetObserver.reset();
        boolean added = abstractListAdapter.addAllItems(0, items);
        assertTrue(added);
        assertTrue(abstractListAdapter.containsItem(item1));
        assertTrue(abstractListAdapter.containsItem(item2));
        assertTrue(abstractListAdapter.containsItem(item3));
        assertEquals(3, abstractListAdapter.getCount());
        assertEquals(0, abstractListAdapter.indexOf(item2));
        assertEquals(1, abstractListAdapter.indexOf(item3));
        assertEquals(2, abstractListAdapter.indexOf(item1));
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item1, 0);
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item2, 0);
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item3, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllItemsFromCollectionAtSpecificIndexWhenDuplicatesAreAllowed() {
        Object item1 = new Object();
        Object item2 = new Object();
        Collection<Object> items = new ArrayList<>();
        items.add(item2);
        items.add(item2);
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.addItem(item1);
        dataSetObserver.reset();
        boolean added = abstractListAdapter.addAllItems(0, items);
        assertTrue(added);
        assertTrue(abstractListAdapter.containsItem(item1));
        assertTrue(abstractListAdapter.containsItem(item2));
        assertEquals(3, abstractListAdapter.getCount());
        assertEquals(0, abstractListAdapter.indexOf(item2));
        assertEquals(2, abstractListAdapter.indexOf(item1));
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item1, 0);
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item2, 0);
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllItemsFromCollectionAtSpecificIndexWhenDuplicatesAreNotAllowed() {
        Object item1 = new Object();
        Object item2 = new Object();
        Collection<Object> items = new ArrayList<>();
        items.add(item2);
        items.add(item2);
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.addItem(item1);
        dataSetObserver.reset();
        boolean added = abstractListAdapter.addAllItems(0, items);
        assertFalse(added);
        assertTrue(abstractListAdapter.containsItem(item1));
        assertTrue(abstractListAdapter.containsItem(item2));
        assertEquals(2, abstractListAdapter.getCount());
        assertEquals(0, abstractListAdapter.indexOf(item2));
        assertEquals(1, abstractListAdapter.indexOf(item1));
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item1, 0);
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item2, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddAllItemsFromCollectionAtSpecificIndexThrowsExceptionWhenCollectionIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        Collection<Object> items = null;
        abstractListAdapter.addAllItems(0, items);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testAddAllItemsFromCollectionAtSpecificIndexThrowsExceptionWhenIndexIsInvalid() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
        Collection<Object> items = new ArrayList<>();
        items.add(new Object());
        abstractListAdapter.addAllItems(-1, items);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllItemsFromArray() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object[] items = new Object[2];
        items[0] = item1;
        items[1] = item2;
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        dataSetObserver.reset();
        boolean added = abstractListAdapter.addAllItems(items);
        assertTrue(added);
        assertTrue(abstractListAdapter.containsItem(item1));
        assertTrue(abstractListAdapter.containsItem(item2));
        assertEquals(2, abstractListAdapter.getCount());
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item1, 0);
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddAllItemsFromArrayThrowsExceptionWhenArrayIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        Object[] items = null;
        abstractListAdapter.addAllItems(items);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllItemsFromArrayContainsDuplicatesWhenDuplicatesAreAllowed() {
        Object item = new Object();
        Object[] items = new Object[2];
        items[0] = item;
        items[1] = item;
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        dataSetObserver.reset();
        boolean added = abstractListAdapter.addAllItems(items);
        assertTrue(added);
        assertTrue(abstractListAdapter.containsItem(item));
        assertEquals(2, abstractListAdapter.getCount());
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item, 0);
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllItemsFromArrayContainsDuplicatesWhenDuplicatesAreNotAllowed() {
        Object item = new Object();
        Object[] items = new Object[2];
        items[0] = item;
        items[1] = item;
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        dataSetObserver.reset();
        boolean added = abstractListAdapter.addAllItems(items);
        assertFalse(added);
        assertTrue(abstractListAdapter.containsItem(item));
        assertEquals(1, abstractListAdapter.getCount());
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllItemsFromArrayAtSpecificIndex() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        Object[] items = new Object[2];
        items[0] = item2;
        items[1] = item3;
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.addItem(item1);
        dataSetObserver.reset();
        boolean added = abstractListAdapter.addAllItems(0, items);
        assertTrue(added);
        assertTrue(abstractListAdapter.containsItem(item1));
        assertTrue(abstractListAdapter.containsItem(item2));
        assertTrue(abstractListAdapter.containsItem(item3));
        assertEquals(3, abstractListAdapter.getCount());
        assertEquals(0, abstractListAdapter.indexOf(item2));
        assertEquals(1, abstractListAdapter.indexOf(item3));
        assertEquals(2, abstractListAdapter.indexOf(item1));
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item1, 0);
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item2, 0);
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item3, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllItemsFromArrayAtSpecificIndexWhenDuplicatesAreAllowed() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object[] items = new Object[2];
        items[0] = item2;
        items[1] = item2;
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.addItem(item1);
        dataSetObserver.reset();
        boolean added = abstractListAdapter.addAllItems(0, items);
        assertTrue(added);
        assertTrue(abstractListAdapter.containsItem(item1));
        assertTrue(abstractListAdapter.containsItem(item2));
        assertEquals(3, abstractListAdapter.getCount());
        assertEquals(0, abstractListAdapter.indexOf(item2));
        assertEquals(2, abstractListAdapter.indexOf(item1));
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item1, 0);
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item2, 0);
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAllItemsFromArrayAtSpecificIndexWhenDuplicatesAreNotAllowed() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object[] items = new Object[2];
        items[0] = item2;
        items[1] = item2;
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.addItem(item1);
        dataSetObserver.reset();
        boolean added = abstractListAdapter.addAllItems(0, items);
        assertFalse(added);
        assertTrue(abstractListAdapter.containsItem(item1));
        assertTrue(abstractListAdapter.containsItem(item2));
        assertEquals(2, abstractListAdapter.getCount());
        assertEquals(0, abstractListAdapter.indexOf(item2));
        assertEquals(1, abstractListAdapter.indexOf(item1));
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item1, 0);
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item2, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddAllItemsFromArrayAtSpecificIndexThrowsExceptionWhenItemIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        Object[] items = null;
        abstractListAdapter.addAllItems(0, items);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testAddAllItemsFromArrayAtSpecificIndexThrowsExceptionWhenIndexIsInvalid() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
        Object[] items = new Object[1];
        items[0] = new Object();
        abstractListAdapter.addAllItems(-1, items);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testReplaceItem() {
        Object item1 = new Object();
        Object item2 = new Object();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addItem(item1);
        dataSetObserver.reset();
        Object replacedItem = abstractListAdapter.replaceItem(0, item2);
        assertEquals(item1, replacedItem);
        assertEquals(1, abstractListAdapter.getCount());
        assertTrue(abstractListAdapter.containsItem(item2));
        verify(listAdapterListener, times(1)).onItemAdded(abstractListAdapter, item2, 0);
        verify(listAdapterListener, times(1)).onItemRemoved(abstractListAdapter, item1, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testReplaceItemThrowsExceptionWhenItemIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.replaceItem(0, null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testReplaceItemThrowsExceptionWhenIndexIsInvalid() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.replaceItem(-1, new Object());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemoveItemAtSpecificIndex() {
        Object item = new Object();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addItem(item);
        dataSetObserver.reset();
        Object removedItem = abstractListAdapter.removeItem(0);
        assertEquals(item, removedItem);
        assertTrue(abstractListAdapter.isEmpty());
        verify(listAdapterListener, times(1)).onItemRemoved(abstractListAdapter, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testRemoveItemAtSpecificIndexThrowsExceptionWhenIndexIsInvalid() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.removeItem(-1);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemoveItemWhenAdapterDoesContainItem() {
        Object item = new Object();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addItem(item);
        dataSetObserver.reset();
        boolean removed = abstractListAdapter.removeItem(item);
        assertTrue(removed);
        assertTrue(abstractListAdapter.isEmpty());
        verify(listAdapterListener, times(1)).onItemRemoved(abstractListAdapter, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemoveItemWhenAdapterDoesNotContainItem() {
        Object item = new Object();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addItem(new Object());
        dataSetObserver.reset();
        boolean removed = abstractListAdapter.removeItem(item);
        assertFalse(removed);
        assertFalse(abstractListAdapter.isEmpty());
        verify(listAdapterListener, times(0)).onItemRemoved(abstractListAdapter, item, 0);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testRemoveItemThrowsExceptionWhenItemIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.removeItem(null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemoveAllItemsFromCollectionWhenAdapterDoesContainAllItems() {
        Object item1 = new Object();
        Object item2 = new Object();
        Collection<Object> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addAllItems(items);
        dataSetObserver.reset();
        boolean removed = abstractListAdapter.removeAllItems(items);
        assertTrue(removed);
        assertTrue(abstractListAdapter.isEmpty());
        verify(listAdapterListener, times(1)).onItemRemoved(abstractListAdapter, item1, 0);
        verify(listAdapterListener, times(1)).onItemRemoved(abstractListAdapter, item2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemoveAllItemsFromCollectionWhenAdapterDoesNotContainAllItems() {
        Object item1 = new Object();
        Object item2 = new Object();
        Collection<Object> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addItem(item1);
        dataSetObserver.reset();
        boolean removed = abstractListAdapter.removeAllItems(items);
        assertFalse(removed);
        assertTrue(abstractListAdapter.isEmpty());
        verify(listAdapterListener, times(1)).onItemRemoved(abstractListAdapter, item1, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testRemoveAllItemsFromCollectionThrowsExceptionWhenCollectionIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        Collection<Object> items = null;
        abstractListAdapter.removeAllItems(items);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemoveAllItemsFromArrayWhenAdapterDoesContainAllItems() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object[] items = new Object[2];
        items[0] = item1;
        items[1] = item2;
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addAllItems(items);
        dataSetObserver.reset();
        boolean removed = abstractListAdapter.removeAllItems(items);
        assertTrue(removed);
        assertTrue(abstractListAdapter.isEmpty());
        verify(listAdapterListener, times(1)).onItemRemoved(abstractListAdapter, item1, 0);
        verify(listAdapterListener, times(1)).onItemRemoved(abstractListAdapter, item2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemoveAllItemsFromArrayWhenAdapterDoesNotContainAllItems() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object[] items = new Object[2];
        items[0] = item1;
        items[1] = item2;
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addItem(item1);
        dataSetObserver.reset();
        boolean removed = abstractListAdapter.removeAllItems(items);
        assertFalse(removed);
        assertTrue(abstractListAdapter.isEmpty());
        verify(listAdapterListener, times(1)).onItemRemoved(abstractListAdapter, item1, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testRemoveAllItemsFromArrayThrowsExceptionWhenCollectionIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        Object[] items = null;
        abstractListAdapter.removeAllItems(items);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRetainAllItemsFromCollection() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        Collection<Object> items = new ArrayList<>();
        items.add(item2);
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addItem(item1);
        abstractListAdapter.addItem(item2);
        abstractListAdapter.addItem(item3);
        dataSetObserver.reset();
        abstractListAdapter.retainAllItems(items);
        assertEquals(1, abstractListAdapter.getCount());
        assertTrue(abstractListAdapter.containsItem(item2));
        verify(listAdapterListener, times(1)).onItemRemoved(abstractListAdapter, item1, 0);
        verify(listAdapterListener, times(1)).onItemRemoved(abstractListAdapter, item3, 2);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testRetainAllItemsFromCollectionThrowsExceptionWhenCollectionIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        Collection<Object> items = null;
        abstractListAdapter.retainAllItems(items);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRetainAllItemsFromArray() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        Object[] items = new Object[1];
        items[0] = item2;
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addItem(item1);
        abstractListAdapter.addItem(item2);
        abstractListAdapter.addItem(item3);
        dataSetObserver.reset();
        abstractListAdapter.retainAllItems(items);
        assertEquals(1, abstractListAdapter.getCount());
        assertTrue(abstractListAdapter.containsItem(item2));
        verify(listAdapterListener, times(1)).onItemRemoved(abstractListAdapter, item1, 0);
        verify(listAdapterListener, times(1)).onItemRemoved(abstractListAdapter, item3, 2);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testRetainAllItemsFromArrayThrowsExceptionWhenCollectionIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        Object[] items = null;
        abstractListAdapter.retainAllItems(items);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testClearItems() {
        Object item1 = new Object();
        Object item2 = new Object();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.addItem(item1);
        abstractListAdapter.addItem(item2);
        dataSetObserver.reset();
        abstractListAdapter.clearItems();
        assertTrue(abstractListAdapter.isEmpty());
        verify(listAdapterListener, times(1)).onItemRemoved(abstractListAdapter, item1, 0);
        verify(listAdapterListener, times(1)).onItemRemoved(abstractListAdapter, item2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test
    public final void testIterator() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(item1);
        abstractListAdapter.addItem(item2);
        Iterator<Object> iterator = abstractListAdapter.iterator();
        assertEquals(item1, iterator.next());
        assertEquals(item2, iterator.next());
    }

    @Test
    public final void testListIterator() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(item1);
        abstractListAdapter.addItem(item2);
        ListIterator<Object> listIterator = abstractListAdapter.listIterator();
        assertEquals(item1, listIterator.next());
        assertEquals(item2, listIterator.next());
    }

    @Test
    public final void testListIteratorWithSpecificStartIndex() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(item1);
        abstractListAdapter.addItem(item2);
        ListIterator<Object> listIterator = abstractListAdapter.listIterator(1);
        assertEquals(item2, listIterator.next());
    }

    @Test
    public final void testSubList() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        Object item4 = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(item1);
        abstractListAdapter.addItem(item2);
        abstractListAdapter.addItem(item3);
        abstractListAdapter.addItem(item4);
        Collection<Object> subList = abstractListAdapter.subList(1, 3);
        assertEquals(2, subList.size());
        Iterator<Object> iterator = subList.iterator();
        assertEquals(item2, iterator.next());
        assertEquals(item3, iterator.next());
    }

    @Test
    public final void testToArray() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(item1);
        abstractListAdapter.addItem(item2);
        Object[] items = abstractListAdapter.toArray();
        assertEquals(2, items.length);
        assertEquals(item1, items[0]);
        assertEquals(item2, items[1]);
    }

    @Test
    public final void testToArrayWithArrayParameter() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(item1);
        abstractListAdapter.addItem(item2);
        Object[] items = abstractListAdapter.toArray(new Object[2]);
        assertEquals(2, items.length);
        assertEquals(item1, items[0]);
        assertEquals(item2, items[1]);
    }

    @Test
    public final void testIndexOfWhenAdapterDoesContainItem() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(item1);
        abstractListAdapter.addItem(item2);
        assertEquals(1, abstractListAdapter.indexOf(item2));
    }

    @Test
    public final void testIndexOfWhenAdapterDoesNotContainItem() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(item1);
        assertEquals(-1, abstractListAdapter.indexOf(item2));
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testIndexOfThrowsExceptionWhenItemIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.indexOf(null);
    }

    @Test
    public final void testLastIndexOfWhenAdapterDoesContainItem() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
        abstractListAdapter.allowDuplicates(true);
        abstractListAdapter.addItem(item1);
        abstractListAdapter.addItem(item2);
        abstractListAdapter.addItem(item2);
        assertEquals(2, abstractListAdapter.lastIndexOf(item2));
    }

    @Test
    public final void testLastIndexOfWhenAdapterDoesNotContainItem() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(item1);
        assertEquals(-1, abstractListAdapter.lastIndexOf(item2));
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testLastIndexOfThrowsExceptionWhenItemIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.lastIndexOf(null);
    }

    @Test
    public final void testContainsItemWhenAdapterDoesContainItem() {
        Object item = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
        abstractListAdapter.addItem(item);
        assertTrue(abstractListAdapter.containsItem(item));
    }

    @Test
    public final void testContainsItemWhenAdapterDoesNotContainItem() {
        Object item = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
        assertFalse(abstractListAdapter.containsItem(item));
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testContainsItemThrowsExceptionWhenItemIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.containsItem(null);
    }

    @Test
    public final void testContainsAllItemsFromCollectionWhenAdapterDoesContainAllItems() {
        Object item1 = new Object();
        Object item2 = new Object();
        Collection<Object> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
        abstractListAdapter.addAllItems(items);
        assertTrue(abstractListAdapter.containsAllItems(items));
    }

    @Test
    public final void testContainsAllItemsFromCollectionWhenAdapterDoesNotContainAllItems() {
        Object item1 = new Object();
        Object item2 = new Object();
        Collection<Object> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
        abstractListAdapter.addItem(item1);
        assertFalse(abstractListAdapter.containsAllItems(items));
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testContainsAllItemsFromCollectionThrowsExceptionWhenCollectionIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        Collection<Object> items = null;
        abstractListAdapter.containsAllItems(items);
    }

    @Test
    public final void testContainsAllItemsFromArrayWhenAdapterDoesContainAllItems() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object[] items = new Object[2];
        items[0] = item1;
        items[1] = item2;
        AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
        abstractListAdapter.addAllItems(items);
        assertTrue(abstractListAdapter.containsAllItems(items));
    }

    @Test
    public final void testContainsAllItemsFromArrayWhenAdapterDoesNotContainAllItems() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object[] items = new Object[2];
        items[0] = item1;
        items[1] = item2;
        AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
        abstractListAdapter.addItem(item1);
        assertFalse(abstractListAdapter.containsAllItems(items));
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testContainsAllItemsFromArrayThrowsExceptionWhenArrayIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        Object[] items = null;
        abstractListAdapter.containsAllItems(items);
    }

    @Test
    public final void testGetItem() {
        Object item = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(item);
        assertEquals(item, abstractListAdapter.getItem(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testGetItemThrowsExceptionWhenIndexIsInvalid() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.getItem(-1);
    }

    @Test
    public final void testGetAllItems() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(item1);
        abstractListAdapter.addItem(item2);
        Collection<Object> items = abstractListAdapter.getAllItems();
        Iterator<Object> iterator = items.iterator();
        assertEquals(item1, iterator.next());
        assertEquals(item2, iterator.next());
    }

    @Test
    public final void testIsEmptyWhenAdapterIsEmpty() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        assertTrue(abstractListAdapter.isEmpty());
    }

    @Test
    public final void testIsEmptyWhenAdapterIsNotEmpty() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(new Object());
        assertFalse(abstractListAdapter.isEmpty());
    }

    @Test
    public final void testAttachToListView() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        ListView listView = new ListView(context);
        abstractListAdapter.attach(listView);
        assertEquals(listView, abstractListAdapter.getAdapterView());
        assertEquals(abstractListAdapter, listView.getAdapter());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAttachToListViewThrowsException() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        ListView listView = null;
        abstractListAdapter.attach(listView);
    }

    @Test
    public final void testAttachToGridView() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        GridView gridView = new GridView(context);
        abstractListAdapter.attach(gridView);
        assertEquals(gridView, abstractListAdapter.getAdapterView());
        assertEquals(abstractListAdapter, gridView.getAdapter());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAttachToGridViewThrowsException() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        GridView gridView = null;
        abstractListAdapter.attach(gridView);
    }

    @Test
    public final void testDetach() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        ListView adapterView = new ListView(context);
        abstractListAdapter.attach(adapterView);
        abstractListAdapter.detach();
        assertNull(abstractListAdapter.getAdapterView());
        assertNull(adapterView.getAdapter());
    }

    @Test
    public final void testDetachWhenAdapterHasNotBeenAttachedBefore() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        ListView adapterView = new ListView(context);
        abstractListAdapter.detach();
        assertNull(abstractListAdapter.getAdapterView());
        assertNull(adapterView.getAdapter());
    }

    @Test
    public final void testDetachWhenAdapterWhenViewAdapterChanged() {
        AbstractListAdapterImplementation abstractListAdapter1 = createAdapter();
        AbstractListAdapterImplementation abstractListAdapter2 = createAdapter();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        ListView adapterView = new ListView(context);
        abstractListAdapter1.attach(adapterView);
        abstractListAdapter2.attach(adapterView);
        abstractListAdapter1.detach();
        assertNull(abstractListAdapter1.getAdapterView());
        assertEquals(abstractListAdapter2, adapterView.getAdapter());
    }

    @Test
    public final void testGetCountWhenAdapterIsEmpty() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        assertEquals(0, abstractListAdapter.getCount());
    }

    @Test
    public final void testGetCountWhenAdapterIsNotEmpty() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(new Object());
        assertEquals(1, abstractListAdapter.getCount());
    }

    @Test
    public final void testGetItemId() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(new Object());
        assertNotSame(0, abstractListAdapter.getItemId(0));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testGetView() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        ListDecoratorImplementation decorator = new ListDecoratorImplementation();
        Object item = new Object();
        AbstractListAdapterImplementation abstractListAdapter =
                new AbstractListAdapterImplementation(context, decorator, LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>());
        abstractListAdapter.addItem(item);
        abstractListAdapter.attach(new ListView(context));
        View view = abstractListAdapter.getView(0, null, null);
        assertNotNull(view);
        view.performClick();
        assertTrue(decorator.hasOnShowItemBeenInvoked);
    }

    @Test
    public final void testGetViewWhenViewIsNotNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        ListDecoratorImplementation decorator = new ListDecoratorImplementation();
        Object item = new Object();
        AbstractListAdapterImplementation abstractListAdapter =
                new AbstractListAdapterImplementation(context, decorator, LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>());
        abstractListAdapter.addItem(item);
        abstractListAdapter.attach(new ListView(context));
        View view1 = View.inflate(context, R.layout.view, null);
        View view2 = abstractListAdapter.getView(0, view1, null);
        assertEquals(view1, view2);
        assertTrue(decorator.hasOnShowItemBeenInvoked);
    }

    @Test
    public final void testOnSaveInstanceStateWhenDataIsParcelable() {
        String key = "adapterkey";
        Bundle outState = new Bundle();
        Object item1 = new ParcelableImplementation(0);
        Object item2 = new ParcelableImplementation(1);
        Bundle parameters = new Bundle();
        parameters.putInt("key", 1);
        boolean allowDuplicates = true;
        boolean notifyOnChange = false;
        LogLevel logLevel = LogLevel.ERROR;
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(item1);
        abstractListAdapter.addItem(item2);
        abstractListAdapter.setParameters(parameters);
        abstractListAdapter.allowDuplicates(allowDuplicates);
        abstractListAdapter.notifyOnChange(notifyOnChange);
        abstractListAdapter.setLogLevel(logLevel);
        abstractListAdapter.onSaveInstanceState(outState, key);
        Bundle savedState = outState.getBundle(key);
        ArrayList<Item<ParcelableImplementation>> savedItems =
                savedState.getParcelableArrayList(AbstractListAdapter.PARCELABLE_ITEMS_BUNDLE_KEY);
        Bundle savedParameters = savedState.getBundle(AbstractListAdapter.PARAMETERS_BUNDLE_KEY);
        boolean savedAllowDuplicates =
                savedState.getBoolean(AbstractListAdapter.ALLOW_DUPLICATES_BUNDLE_KEY);
        boolean savedNotifyOnChange =
                savedState.getBoolean(AbstractListAdapter.NOTIFY_ON_CHANGE_BUNDLE_KEY);
        Iterator<Item<ParcelableImplementation>> itemsIterator = savedItems.iterator();
        assertEquals(item1, itemsIterator.next().getData());
        assertEquals(item2, itemsIterator.next().getData());
        assertEquals(1, savedParameters.getInt("key"));
        assertEquals(allowDuplicates, savedAllowDuplicates);
        assertEquals(notifyOnChange, savedNotifyOnChange);
        assertEquals(logLevel, abstractListAdapter.getLogLevel());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testOnSaveInstanceStateWhenDataIsSerializable() {
        String key = "adapterkey";
        Bundle outState = new Bundle();
        Object item1 = new SerializableImplementation(0);
        Object item2 = new SerializableImplementation(1);
        Bundle parameters = new Bundle();
        parameters.putInt("key", 1);
        boolean allowDuplicates = true;
        boolean notifyOnChange = false;
        LogLevel logLevel = LogLevel.ERROR;
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(item1);
        abstractListAdapter.addItem(item2);
        abstractListAdapter.setParameters(parameters);
        abstractListAdapter.allowDuplicates(allowDuplicates);
        abstractListAdapter.notifyOnChange(notifyOnChange);
        abstractListAdapter.setLogLevel(logLevel);
        abstractListAdapter.onSaveInstanceState(outState, key);
        Bundle savedState = outState.getBundle(key);
        ArrayList<Item<Object>> savedItems = (ArrayList<Item<Object>>) savedState
                .getSerializable(AbstractListAdapter.SERIALIZABLE_ITEMS_BUNDLE_KEY);
        Bundle savedParameters = savedState.getBundle(AbstractListAdapter.PARAMETERS_BUNDLE_KEY);
        boolean savedAllowDuplicates =
                savedState.getBoolean(AbstractListAdapter.ALLOW_DUPLICATES_BUNDLE_KEY);
        boolean savedNotifyOnChange =
                savedState.getBoolean(AbstractListAdapter.NOTIFY_ON_CHANGE_BUNDLE_KEY);
        Iterator<Item<Object>> itemsIterator = savedItems.iterator();
        assertEquals(item1, itemsIterator.next().getData());
        assertEquals(item2, itemsIterator.next().getData());
        assertEquals(1, savedParameters.getInt("key"));
        assertEquals(allowDuplicates, savedAllowDuplicates);
        assertEquals(notifyOnChange, savedNotifyOnChange);
        assertEquals(logLevel, abstractListAdapter.getLogLevel());
    }

    @Test
    public final void testOnSaveInstanceStateWhenDataIsNotParcelableOrSerializable() {
        String key = "adapterkey";
        Bundle outState = new Bundle();
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(item1);
        abstractListAdapter.addItem(item2);
        abstractListAdapter.onSaveInstanceState(outState, key);
        assertNull(outState.getSerializable(AbstractListAdapter.SERIALIZABLE_ITEMS_BUNDLE_KEY));
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testOnSaveInstanceStateThrowsExceptionWhenBundleIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.onSaveInstanceState(null, "key");
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testOnSaveInstanceStateThrowsExceptionWhenKeyIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.onSaveInstanceState(new Bundle(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testOnSaveInstanceStateThrowsExceptionWhenKeyIsEmpty() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.onSaveInstanceState(new Bundle(), "");
    }

    @Test
    public final void testOnRestoreInstanceStateWhenDataIsParcelable() {
        String key = "adapterkey";
        Bundle savedInstanceState = new Bundle();
        ParcelableImplementation item1 = new ParcelableImplementation(0);
        ParcelableImplementation item2 = new ParcelableImplementation(1);
        ArrayList<Item<ParcelableImplementation>> savedItems = new ArrayList<>();
        savedItems.add(new Item<>(item1));
        savedItems.add(new Item<>(item2));
        Bundle parameters = new Bundle();
        parameters.putInt("key", 1);
        boolean allowDuplicates = true;
        boolean notifyOnChange = false;
        LogLevel logLevel = LogLevel.ERROR;
        Bundle savedState = new Bundle();
        savedState.putParcelableArrayList(AbstractListAdapter.PARCELABLE_ITEMS_BUNDLE_KEY,
                savedItems);
        savedState.putBundle(AbstractListAdapter.PARAMETERS_BUNDLE_KEY, parameters);
        savedState.putBoolean(AbstractListAdapter.ALLOW_DUPLICATES_BUNDLE_KEY, allowDuplicates);
        savedState.putBoolean(AbstractListAdapter.NOTIFY_ON_CHANGE_BUNDLE_KEY, notifyOnChange);
        savedState.putInt(AbstractListAdapter.LOG_LEVEL_BUNDLE_KEY, logLevel.getRank());
        savedInstanceState.putBundle(key, savedState);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        DataSetObserver dataSetObserver = new DataSetObserver();
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.onRestoreInstanceState(savedInstanceState, key);
        assertEquals(item1, abstractListAdapter.getItem(0));
        assertEquals(item2, abstractListAdapter.getItem(1));
        assertEquals(1, abstractListAdapter.getParameters().getInt("key"));
        assertEquals(allowDuplicates, abstractListAdapter.areDuplicatesAllowed());
        assertEquals(notifyOnChange, abstractListAdapter.isNotifiedOnChange());
        assertEquals(logLevel, abstractListAdapter.getLogLevel());
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test
    public final void testOnRestoreInstanceStateWhenDataIsSerializable() {
        String key = "adapterkey";
        Bundle savedInstanceState = new Bundle();
        SerializableImplementation item1 = new SerializableImplementation(0);
        SerializableImplementation item2 = new SerializableImplementation(1);
        ArrayList<Item<SerializableImplementation>> savedItems = new ArrayList<>();
        savedItems.add(new Item<>(item1));
        savedItems.add(new Item<>(item2));
        Bundle parameters = new Bundle();
        parameters.putInt("key", 1);
        boolean allowDuplicates = true;
        boolean notifyOnChange = false;
        LogLevel logLevel = LogLevel.ERROR;
        Bundle savedState = new Bundle();
        savedState.putSerializable(AbstractListAdapter.SERIALIZABLE_ITEMS_BUNDLE_KEY, savedItems);
        savedState.putBundle(AbstractListAdapter.PARAMETERS_BUNDLE_KEY, parameters);
        savedState.putBoolean(AbstractListAdapter.ALLOW_DUPLICATES_BUNDLE_KEY, allowDuplicates);
        savedState.putBoolean(AbstractListAdapter.NOTIFY_ON_CHANGE_BUNDLE_KEY, notifyOnChange);
        savedState.putInt(AbstractListAdapter.LOG_LEVEL_BUNDLE_KEY, logLevel.getRank());
        savedInstanceState.putBundle(key, savedState);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        DataSetObserver dataSetObserver = new DataSetObserver();
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.onRestoreInstanceState(savedInstanceState, key);
        assertEquals(item1, abstractListAdapter.getItem(0));
        assertEquals(item2, abstractListAdapter.getItem(1));
        assertEquals(1, abstractListAdapter.getParameters().getInt("key"));
        assertEquals(allowDuplicates, abstractListAdapter.areDuplicatesAllowed());
        assertEquals(notifyOnChange, abstractListAdapter.isNotifiedOnChange());
        assertEquals(logLevel, abstractListAdapter.getLogLevel());
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testOnRestoreInstanceStateThrowsExceptionWhenBundleIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.onRestoreInstanceState(null, "key");
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testOnRestoreInstanceStateThrowsExceptionWhenKeyIsNull() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.onRestoreInstanceState(new Bundle(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testOnRestoreInstanceStateThrowsExceptionWhenKeyIsEmpty() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.onRestoreInstanceState(new Bundle(), "");
    }

    @Test
    public final void testHashCode() {
        AbstractListAdapterImplementation abstractListAdapter1 = createAdapter();
        AbstractListAdapterImplementation abstractListAdapter2 = createAdapter();
        assertEquals(abstractListAdapter1.hashCode(), abstractListAdapter1.hashCode());
        assertEquals(abstractListAdapter1.hashCode(), abstractListAdapter2.hashCode());
        abstractListAdapter1.addItem(new Object());
        assertNotSame(abstractListAdapter1.hashCode(), abstractListAdapter2.hashCode());
        abstractListAdapter2.addItem(abstractListAdapter1.getItem(0));
        abstractListAdapter1.allowDuplicates(true);
        assertNotSame(abstractListAdapter1.hashCode(), abstractListAdapter2.hashCode());
        abstractListAdapter2.allowDuplicates(abstractListAdapter1.areDuplicatesAllowed());
        abstractListAdapter1.notifyOnChange(false);
        assertNotSame(abstractListAdapter1.hashCode(), abstractListAdapter2.hashCode());
        abstractListAdapter2.notifyOnChange(abstractListAdapter1.isNotifiedOnChange());
        abstractListAdapter1.setLogLevel(LogLevel.ERROR);
        assertNotSame(abstractListAdapter1.hashCode(), abstractListAdapter2.hashCode());
        abstractListAdapter2.setLogLevel(abstractListAdapter1.getLogLevel());
        Bundle parameters = new Bundle();
        parameters.putInt("key", 0);
        abstractListAdapter1.setParameters(parameters);
        assertNotSame(abstractListAdapter1.hashCode(), abstractListAdapter2.hashCode());
        abstractListAdapter2.setParameters(new Bundle());
        assertNotSame(abstractListAdapter1.hashCode(), abstractListAdapter2.hashCode());
    }

    @Test
    public final void testEquals() {
        AbstractListAdapterImplementation abstractListAdapter1 = createAdapter();
        AbstractListAdapterImplementation abstractListAdapter2 = createAdapter();
        assertTrue(abstractListAdapter1.equals(abstractListAdapter1));
        assertTrue(abstractListAdapter1.equals(abstractListAdapter2));
        assertFalse(abstractListAdapter1.equals(null));
        assertFalse(abstractListAdapter1.equals(new Object()));
        abstractListAdapter1.addItem(new Object());
        assertFalse(abstractListAdapter1.equals(abstractListAdapter2));
        abstractListAdapter2.addItem(abstractListAdapter1.getItem(0));
        abstractListAdapter1.allowDuplicates(true);
        assertFalse(abstractListAdapter1.equals(abstractListAdapter2));
        abstractListAdapter2.allowDuplicates(abstractListAdapter1.areDuplicatesAllowed());
        abstractListAdapter1.notifyOnChange(false);
        assertFalse(abstractListAdapter1.equals(abstractListAdapter2));
        abstractListAdapter2.notifyOnChange(abstractListAdapter1.isNotifiedOnChange());
        abstractListAdapter1.setLogLevel(LogLevel.ERROR);
        assertFalse(abstractListAdapter1.equals(abstractListAdapter2));
        abstractListAdapter2.setLogLevel(abstractListAdapter1.getLogLevel());
        Bundle parameters = new Bundle();
        parameters.putInt("key", 0);
        abstractListAdapter1.setParameters(parameters);
        assertFalse(abstractListAdapter1.equals(abstractListAdapter2));
        abstractListAdapter2.setParameters(new Bundle());
        assertFalse(abstractListAdapter1.equals(abstractListAdapter2));
    }

}