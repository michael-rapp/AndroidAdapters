/*
 * Copyright 2014 - 2018 Michael Rapp
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
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import junit.framework.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests the functionality of the class {@link AbstractListAdapter}.
 *
 * @author Michael Rapp
 */
public class AbstractListAdapterTest extends AndroidTestCase {

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
        return new AbstractListAdapterImplementation(getContext(),
                new ListDecoratorImplementation(), LogLevel.ALL, new ArrayList<Item<Object>>(),
                allowDuplicates, true, new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                new LinkedHashSet<ListAdapterListener<Object>>());
    }

    /**
     * Tests the functionality of the method, which allows to create a deep copy of the list, which
     * contains the adapter's items.
     *
     * @throws CloneNotSupportedException
     *         The exception, which is thrown, if cloning is not supported by the adapter's
     *         underlying data
     */
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

    /**
     * Tests, if all properties are set correctly by the constructor.
     */
    public final void testConstructor() {
        Context context = getContext();
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the context, which is passed to
     * the constructor, is null.
     */
    public final void testConstructorThrowsExceptionWhenContextIsNull() {
        try {
            new AbstractListAdapterImplementation(null, new ListDecoratorImplementation(),
                    LogLevel.ALL, new ArrayList<Item<Object>>(), false, true,
                    new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                    new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                    new LinkedHashSet<ListAdapterListener<Object>>());
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the decorator, which is passed to
     * the constructor, is null.
     */
    public final void testConstructorThrowsExceptionWhenDecoratorIsNull() {
        try {
            new AbstractListAdapterImplementation(getContext(), null, LogLevel.ALL,
                    new ArrayList<Item<Object>>(), false, true,
                    new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                    new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                    new LinkedHashSet<ListAdapterListener<Object>>());
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the collection, which contains the
     * items, which is passed to the constructor, is null.
     */
    public final void testConstructorThrowsExceptionWhenItemsIsNull() {
        try {
            new AbstractListAdapterImplementation(getContext(), new ListDecoratorImplementation(),
                    LogLevel.ALL, null, false, true,
                    new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                    new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                    new LinkedHashSet<ListAdapterListener<Object>>());
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the set, which contains the item
     * click listeners, which is passed to the constructor, is null.
     */
    public final void testConstructorThrowsExceptionWhenItemClickListenersIsNull() {
        try {
            new AbstractListAdapterImplementation(getContext(), new ListDecoratorImplementation(),
                    LogLevel.ALL, new ArrayList<Item<Object>>(), false, true, null,
                    new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                    new LinkedHashSet<ListAdapterListener<Object>>());
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the set, which contains the item
     * long click listeners, which is passed to the constructor, is null.
     */
    public final void testConstructorThrowsExceptionWhenItemLongClickListenersIsNull() {
        try {
            new AbstractListAdapterImplementation(getContext(), new ListDecoratorImplementation(),
                    LogLevel.ALL, new ArrayList<Item<Object>>(), false, true,
                    new LinkedHashSet<ListAdapterItemClickListener<Object>>(), null,
                    new LinkedHashSet<ListAdapterListener<Object>>());
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the set, which contains the
     * adapter listeners, which is passed to the constructor, is null.
     */
    public final void testConstructorThrowsExceptionWhenAdapterListenersIsNull() {
        try {
            new AbstractListAdapterImplementation(getContext(), new ListDecoratorImplementation(),
                    LogLevel.ALL, new ArrayList<Item<Object>>(), false, true,
                    new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                    new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(), null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the log level, which is passed to
     * the constructor, is null.
     */
    public final void testConstructorThrowsExceptionWhenLogLevelIsNull() {
        try {
            new AbstractListAdapterImplementation(getContext(), new ListDecoratorImplementation(),
                    null, new ArrayList<Item<Object>>(), false, true,
                    new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                    new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                    new LinkedHashSet<ListAdapterListener<Object>>());
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to set the log level.
     */
    public final void testSetLogLevel() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        LogLevel logLevel = LogLevel.ERROR;
        abstractListAdapter.setLogLevel(logLevel);
        assertEquals(logLevel, abstractListAdapter.getLogLevel());
    }

    /**
     * Tests the functionality of the method, which allows to set a bundle, which contains the key
     * value pairs, which should be stored within the adapter.
     */
    public final void testSetParameters() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        Bundle parameters = new Bundle();
        abstractListAdapter.setParameters(parameters);
        assertEquals(parameters, abstractListAdapter.getParameters());
    }

    /**
     * Tests the functionality of the method, which allows to set, whether duplicate items should be
     * allowed, or not.
     */
    public final void testAllowDuplicates() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        boolean allowDuplicates = true;
        abstractListAdapter.allowDuplicates(allowDuplicates);
        assertEquals(allowDuplicates, abstractListAdapter.areDuplicatesAllowed());
    }

    /**
     * Tests the functionality of the method, which allows to set, whether the method
     * <code>notifyDataSetChanged():void</code> should be automatically called when the adapter's
     * underlying data has been changed, or not.
     */
    public final void testNotifyOnChange() {
        DataSetObserver dataSetObserver = new DataSetObserver();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.registerDataSetObserver(dataSetObserver);
        abstractListAdapter.notifyDataSetChanged();
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
        dataSetObserver.reset();
        boolean notifyOnChange = false;
        abstractListAdapter.notifyOnChange(notifyOnChange);
        assertEquals(notifyOnChange, abstractListAdapter.isNotifiedOnChange());
        abstractListAdapter.notifyDataSetChanged();
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the method, which allows to add a listener, which should be
     * notified, when the adapter's underlying data has been modified.
     */
    @SuppressWarnings("unchecked")
    public final void testAddAdapterListener() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        assertTrue(abstractListAdapter.getAdapterListeners().isEmpty());
        abstractListAdapter.addAdapterListener(listAdapterListener);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        assertEquals(1, abstractListAdapter.getAdapterListeners().size());
        assertTrue(abstractListAdapter.getAdapterListeners().contains(listAdapterListener));
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if a {@link ListAdapterListener},
     * which is null, should be added to the adapter.
     */
    public final void testAddAdapterListenerThrowsExceptionWhenListenerIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            abstractListAdapter.addAdapterListener(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to remove a listener, which should not be
     * notified, when the adapter's underlying data has been changed, anymore.
     */
    @SuppressWarnings("unchecked")
    public final void testRemoveAdapterListener() {
        ListAdapterListener<Object> listAdapterListener = mock(ListAdapterListener.class);
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.removeAdapterListener(listAdapterListener);
        abstractListAdapter.addAdapterListener(listAdapterListener);
        assertFalse(abstractListAdapter.getAdapterListeners().isEmpty());
        abstractListAdapter.removeAdapterListener(listAdapterListener);
        assertTrue(abstractListAdapter.getAdapterListeners().isEmpty());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if a {@link ListAdapterListener},
     * which is null, should be removed from the adapter.
     */
    public final void testRemoveAdapterListenerThrowsExceptionWhenListenerIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            abstractListAdapter.removeAdapterListener(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to add an item to the adapter.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if an item, which is null, should be
     * added to the adapter.
     */
    public final void testAddItemThrowsExceptionWhenItemIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
            abstractListAdapter.addItem(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to add an item to the adapter, if the
     * adapter does already contain the item and duplicates are allowed.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Tests the functionality of the method, which allows to add an item to the adapter, if the
     * adapter does already contain the item and duplicates are not allowed.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Tests the functionality of the method, which allows to add an item to the adapter at a
     * specific index, if the adapter does already contain the item and duplicates are allowed.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if an item, which is null, should be
     * added to the adapter at a specific index.
     */
    public final void testAddItemAtSpecificIndexThrowsExceptionWhenItemIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            abstractListAdapter.addItem(0, null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that an {@link IndexOutOfBoundsException} is thrown, if an item should be added to
     * the adapter at an invalid index.
     */
    public final void testAddItemAtSpecificIndexThrowsExceptionWhenIndexIsInvalid() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
            abstractListAdapter.addItem(-1, new Object());
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to add an item to the adapter at a
     * specific index, if the adapter does already contain the item and duplicates are allowed.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Tests the functionality of the method, which allows to add an item to the adapter at a
     * specific index, if the adapter does already contain the item and duplicates are allowed.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Tests the functionality of the method, which allows to add all items, which are contained by
     * a specific collection, to the adapter.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if all items of a collection, which
     * is null, should be added to the adapter.
     */
    public final void testAddAllItemsFromCollectionThrowsExceptionWhenCollectionIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            Collection<Object> items = null;
            abstractListAdapter.addAllItems(items);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to add all items, which are contained by
     * a specific collection, if the collection contains duplicates and duplicates are allowed.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Tests the functionality of the method, which allows to add all items, which are contained by
     * a specific collection, if the collection contains duplicates and duplicates are not allowed.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Tests the functionality of the method, which allows to add all items, which are contained by
     * a specific collection, to the adapter at a specific index.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Tests the functionality of the method, which allows to add all items, which are contained by
     * a specific collection, to the adapter at a specific index, if the collection contains
     * duplicates and duplicates are allowed.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Tests the functionality of the method, which allows to add all items, which are contained by
     * a specific collection, to the adapter at a specific index, if the collection contains
     * duplicates and duplicates are not allowed.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if all items of a collection, which
     * is null, should be added to the adapter at a specific index.
     */
    public final void testAddAllItemsFromCollectionAtSpecificIndexThrowsExceptionWhenCollectionIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            Collection<Object> items = null;
            abstractListAdapter.addAllItems(0, items);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that an {@link IndexOutOfBoundsException} is thrown, if all items, which are
     * contained by a specific collection, should be added to the adapter at an invalid index.
     */
    public final void testAddAllItemsFromCollectionAtSpecificIndexThrowsExceptionWhenIndexIsInvalid() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
            Collection<Object> items = new ArrayList<>();
            items.add(new Object());
            abstractListAdapter.addAllItems(-1, items);
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to add all items, which are contained by
     * a specific array, to the adapter.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if all items of an array, which is
     * null, should be added to the adapter.
     */
    public final void testAddAllItemsFromArrayThrowsExceptionWhenArrayIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            Object[] items = null;
            abstractListAdapter.addAllItems(items);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to add all items, which are contained by
     * a specific array, if the array contains duplicates and duplicates are allowed.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Tests the functionality of the method, which allows to add all items, which are contained by
     * a specific array, if the array contains duplicates and duplicates are not allowed.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Tests the functionality of the method, which allows to add all items, which are contained by
     * a specific array, to the adapter at a specific index.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Tests the functionality of the method, which allows to add all items, which are contained by
     * a specific array, to the adapter at a specific index, if the collection contains duplicates
     * and duplicates are allowed.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Tests the functionality of the method, which allows to add all items, which are contained by
     * a specific array, to the adapter at a specific index, if the array contains duplicates and
     * duplicates are not allowed.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if all items of an array, which is
     * null, should be added to the adapter at a specific index.
     */
    public final void testAddAllItemsFromArrayAtSpecificIndexThrowsExceptionWhenItemIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            Object[] items = null;
            abstractListAdapter.addAllItems(0, items);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that an {@link IndexOutOfBoundsException} is thrown, if all items, which are
     * contained by a specific array, should be added to the adapter at an invalid index.
     */
    public final void testAddAllItemsFromArrayAtSpecificIndexThrowsExceptionWhenIndexIsInvalid() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
            Object[] items = new Object[1];
            items[0] = new Object();
            abstractListAdapter.addAllItems(-1, items);
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to replace the item at a specific index.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the item at a specific index
     * should be replaced by an item, which is null.
     */
    public final void testReplaceItemThrowsExceptionWhenItemIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            abstractListAdapter.replaceItem(0, null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that an {@link IndexOutOfBoundsException} is thrown, if the item at an invalid index
     * should be replaced.
     */
    public final void testReplaceItemThrowsExceptionWhenIndexIsInvalid() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            abstractListAdapter.replaceItem(-1, new Object());
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to remove the item at a specific index
     * from the adapter.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Ensures, that an {@link IndexOutOfBoundsException} is thrown, if the item at an invalid index
     * should be removed from the adapter.
     */
    public final void testRemoveItemAtSpecificIndexThrowsExceptionWhenIndexIsInvalid() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            abstractListAdapter.removeItem(-1);
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to remove a specific item from the
     * adapter, if the adapter does actually contain the item.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Tests the functionality of the method, which allows to remove a specific item from the
     * adapter, if the adapter does not contain the item.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Ensures, that an {@link NullPointerException} is thrown, if an item, which is null, should be
     * removed from the adapter.
     */
    public final void testRemoveItemThrowsExceptionWhenItemIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            abstractListAdapter.removeItem(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to remove all items, which are contained
     * by a specific collection, from the adapter, if the adapter does actually contain all of these
     * items.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Tests the functionality of the method, which allows to remove all items, which are contained
     * by a specific collection, from the adapter, if the adapter does not contain all of these
     * items.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if all items, which are contained by
     * a collection, which is null, should be removed from the adapter.
     */
    public final void testRemoveAllItemsFromCollectionThrowsExceptionWhenCollectionIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            Collection<Object> items = null;
            abstractListAdapter.removeAllItems(items);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to remove all items, which are contained
     * by a specific array, from the adapter, if the adapter does actually contain all of these
     * items.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Tests the functionality of the method, which allows to remove all items, which are contained
     * by a specific array, from the adapter, if the adapter does not contain all of these items.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if all items, which are contained by
     * an array, which is null, should be removed from the adapter.
     */
    public final void testRemoveAllItemsFromArrayThrowsExceptionWhenCollectionIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            Object[] items = null;
            abstractListAdapter.removeAllItems(items);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to remove all items except of the ones,
     * which are contained by a specific collection, from the adapter.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if all items except of the ones,
     * which are contained by a collection, which is null, should be removed from the adapter.
     */
    public final void testRetainAllItemsFromCollectionThrowsExceptionWhenCollectionIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            Collection<Object> items = null;
            abstractListAdapter.retainAllItems(items);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to remove all items except of the ones,
     * which are contained by a specific array, from the adapter.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if all items except of the ones,
     * which are contained by an array, which is null, should be removed from the adapter.
     */
    public final void testRetainAllItemsFromArrayThrowsExceptionWhenCollectionIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            Object[] items = null;
            abstractListAdapter.retainAllItems(items);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to remove all items from the adapter.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Tests the functionality of the method, which allows to retrieve an {@link Iterator}, which
     * allows to iterate the adapter's items.
     */
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

    /**
     * Tests the functionality of the method, which allows to retrieve a {@link ListIterator}, which
     * allows to iterate the adapter's items.
     */
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

    /**
     * Tests the functionality of the method, which allows to retrieve a {@link ListIterator}, which
     * allows to iterate the adapter's items, beginning at a specific index.
     */
    public final void testListIteratorWithSpecificStartIndex() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(item1);
        abstractListAdapter.addItem(item2);
        ListIterator<Object> listIterator = abstractListAdapter.listIterator(1);
        assertEquals(item2, listIterator.next());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * the items between a specific start and end index.
     */
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

    /**
     * Tests the functionality of the method, which allows to retrieve an array, which contains the
     * adapter's items.
     */
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

    /**
     * Tests the functionality of the method, which allows to retrieve an array, which contains the
     * adapter's items and is created by using an already existing array.
     */
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

    /**
     * Tests the functionality of the method, which allows to retrieve the index of a specific item,
     * if the adapter does actually contain this item.
     */
    public final void testIndexOfWhenAdapterDoesContainItem() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(item1);
        abstractListAdapter.addItem(item2);
        assertEquals(1, abstractListAdapter.indexOf(item2));
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of a specific item,
     * if the adapter does not contain this item.
     */
    public final void testIndexOfWhenAdapterDoesNotContainItem() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(item1);
        assertEquals(-1, abstractListAdapter.indexOf(item2));
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the index of an item, which is
     * null, should be retrieved.
     */
    public final void testIndexOfThrowsExceptionWhenItemIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            abstractListAdapter.indexOf(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the last index of a specific
     * item, if the adapter does actually contain this item.
     */
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

    /**
     * Tests the functionality of the method, which allows to retrieve the last index of a specific
     * item, if the adapter does not contain this item.
     */
    public final void testLastIndexOfWhenAdapterDoesNotContainItem() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(item1);
        assertEquals(-1, abstractListAdapter.lastIndexOf(item2));
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the last index of an item, which
     * is null, should be retrieved.
     */
    public final void testLastIndexOfThrowsExceptionWhenItemIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            abstractListAdapter.lastIndexOf(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Test the functionality of the method, which allows to retrieve, whether the adapter contains
     * an item, if the adapter does actually contain the item.
     */
    public final void testContainsItemWhenAdapterDoesContainItem() {
        Object item = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
        abstractListAdapter.addItem(item);
        assertTrue(abstractListAdapter.containsItem(item));
    }

    /**
     * Test the functionality of the method, which allows to retrieve, whether the adapter contains
     * an item, if the adapter does not contain the item.
     */
    public final void testContainsItemWhenAdapterDoesNotContainItem() {
        Object item = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter(true);
        assertFalse(abstractListAdapter.containsItem(item));
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if it should be retrieved, whether
     * the adapter contains an item, which is null, or not.
     */
    public final void testContainsItemThrowsExceptionWhenItemIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            abstractListAdapter.containsItem(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Test the functionality of the method, which allows to retrieve, whether the adapter contains
     * all items, which are contained by a collection, if the adapter does actually contain all of
     * these items.
     */
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

    /**
     * Test the functionality of the method, which allows to retrieve, whether the adapter contains
     * all items, which are contained by a collection, if the adapter does not contain all of these
     * items.
     */
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if it should be retrieved, whether
     * all items, which are contained by a collection, which is null, are contained by the adapter,
     * or not.
     */
    public final void testContainsAllItemsFromCollectionThrowsExceptionWhenCollectionIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            Collection<Object> items = null;
            abstractListAdapter.containsAllItems(items);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Test the functionality of the method, which allows to retrieve, whether the adapter contains
     * all items, which are contained by an array, if the adapter does actually contain all of these
     * items.
     */
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

    /**
     * Test the functionality of the method, which allows to retrieve, whether the adapter contains
     * all items, which are contained by an array, if the adapter does not contain all of these
     * items.
     */
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if it should be retrieved, whether
     * all items, which are contained by an array, which is null, are contained by the adapter, or
     * not.
     */
    public final void testContainsAllItemsFromArrayThrowsExceptionWhenArrayIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            Object[] items = null;
            abstractListAdapter.containsAllItems(items);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the item, which belongs to a
     * specific index.
     */
    public final void testGetItem() {
        Object item = new Object();
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(item);
        assertEquals(item, abstractListAdapter.getItem(0));
    }

    /**
     * Ensures, that an {@link IndexOutOfBoundsException} is thrown, if an item, which belongs to an
     * invalid index, should be retrieved.
     */
    public final void testGetItemThrowsExceptionWhenIndexIsInvalid() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            abstractListAdapter.getItem(-1);
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * all items.
     */
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

    /**
     * Tests the functionality of the method, which allows to retrieve, whether the adapter is
     * empty, or not, if the adapter is actually empty.
     */
    public final void testIsEmptyWhenAdapterIsEmpty() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        assertTrue(abstractListAdapter.isEmpty());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve, whether the adapter is
     * empty, or not, if the adapter is not empty.
     */
    public final void testIsEmptyWhenAdapterIsNotEmpty() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(new Object());
        assertFalse(abstractListAdapter.isEmpty());
    }

    /**
     * Tests the functionality of the method, which allows to attach the adapter to a list view.
     */
    public final void testAttachToListView() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        ListView listView = new ListView(getContext());
        abstractListAdapter.attach(listView);
        assertEquals(listView, abstractListAdapter.getAdapterView());
        assertEquals(abstractListAdapter, listView.getAdapter());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to attach
     * the adapter to a list view, if the list view is null.
     */
    public final void testAttachToListViewThrowsException() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            ListView listView = null;
            abstractListAdapter.attach(listView);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to attach the adapter to a grid view.
     */
    public final void testAttachToGridView() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        GridView gridView = new GridView(getContext());
        abstractListAdapter.attach(gridView);
        assertEquals(gridView, abstractListAdapter.getAdapterView());
        assertEquals(abstractListAdapter, gridView.getAdapter());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to attach
     * the adapter to a grid view, if the grid view is null.
     */
    public final void testAttachToGridViewThrowsException() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            GridView gridView = null;
            abstractListAdapter.attach(gridView);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to detach the adapter from the view, it
     * is currently attached to.
     */
    public final void testDetach() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        ListView adapterView = new ListView(getContext());
        abstractListAdapter.attach(adapterView);
        abstractListAdapter.detach();
        assertNull(abstractListAdapter.getAdapterView());
        assertNull(adapterView.getAdapter());
    }

    /**
     * Tests the functionality of the method, which allows to detach the adapter from the view, it
     * is currently attached to, if the adapter has not been attached to a view before.
     */
    public final void testDetachWhenAdapterHasNotBeenAttachedBefore() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        ListView adapterView = new ListView(getContext());
        abstractListAdapter.detach();
        assertNull(abstractListAdapter.getAdapterView());
        assertNull(adapterView.getAdapter());
    }

    /**
     * Tests the functionality of the method, which allows to detach the adapter from the view, it
     * is currently attached to, if the adapter of the view changed in the meantime.
     */
    public final void testDetachWhenAdapterWhenViewAdapterChanged() {
        AbstractListAdapterImplementation abstractListAdapter1 = createAdapter();
        AbstractListAdapterImplementation abstractListAdapter2 = createAdapter();
        ListView adapterView = new ListView(getContext());
        abstractListAdapter1.attach(adapterView);
        abstractListAdapter2.attach(adapterView);
        abstractListAdapter1.detach();
        assertNull(abstractListAdapter1.getAdapterView());
        assertEquals(abstractListAdapter2, adapterView.getAdapter());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the count of items, if the
     * adapter is empty.
     */
    public final void testGetCountWhenAdapterIsEmpty() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        assertEquals(0, abstractListAdapter.getCount());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the count of items, if the
     * adapter is not empty.
     */
    public final void testGetCountWhenAdapterIsNotEmpty() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(new Object());
        assertEquals(1, abstractListAdapter.getCount());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the id of the item, which
     * belongs to a specific index.
     */
    public final void testGetItemId() {
        AbstractListAdapterImplementation abstractListAdapter = createAdapter();
        abstractListAdapter.addItem(new Object());
        assertEquals(0, abstractListAdapter.getItemId(0));
    }

    /**
     * Tests the functionality of the getView-method.
     */
    @SuppressWarnings("unchecked")
    public final void testGetView() {
        Context context = getContext();
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

    /**
     * Tests the functionality of the getView-method, when the passed view is not null.
     */
    public final void testGetViewWhenViewIsNotNull() {
        Context context = getContext();
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

    /**
     * Tests the functionality of the onSaveInstanceState-method, when the adapter's underlying data
     * implements the interface {@link Parcelable}.
     */
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

    /**
     * Tests the functionality of the onSaveInstanceState-method, when the adapter's underlying data
     * implements the interface {@link Serializable}.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Tests the functionality of the onSaveInstanceState-method, if the underlying data does
     * neither implement the interface {@link Parcelable}, nor the interface {@link Serializable}.
     */
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the onSaveInstanceState-method, if
     * the bundle is null.
     */
    public final void testOnSaveInstanceStateThrowsExceptionWhenBundleIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            abstractListAdapter.onSaveInstanceState(null, "key");
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the onSaveInstanceState-method, if
     * the key is null.
     */
    public final void testOnSaveInstanceStateThrowsExceptionWhenKeyIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            abstractListAdapter.onSaveInstanceState(new Bundle(), null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that a {@link IllegalArgumentException} is thrown by the onSaveInstanceState-method,
     * if the key is empty.
     */
    public final void testOnSaveInstanceStateThrowsExceptionWhenKeyIsEmpty() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            abstractListAdapter.onSaveInstanceState(new Bundle(), "");
            Assert.fail();
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Tests the functionality of the onRestoreInstanceState-method, when the adapter's underlying
     * data implements the interface {@link Parcelable}.
     */
    public final void testOnRestoreInstanceStateWhenDataIsParcelable() {
        String key = "adapterkey";
        Bundle savedInstanceState = new Bundle();
        ParcelableImplementation item1 = new ParcelableImplementation(0);
        ParcelableImplementation item2 = new ParcelableImplementation(1);
        ArrayList<Item<ParcelableImplementation>> savedItems =
                new ArrayList<Item<ParcelableImplementation>>();
        savedItems.add(new Item<ParcelableImplementation>(item1));
        savedItems.add(new Item<ParcelableImplementation>(item2));
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

    /**
     * Tests the functionality of the onRestoreInstanceState-method, when the adapter's underlying
     * data implements the interface {@link Serializable}.
     */
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the onRestoreInstanceState-method,
     * if the bundle is null.
     */
    public final void testOnRestoreInstanceStateThrowsExceptionWhenBundleIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            abstractListAdapter.onRestoreInstanceState(null, "key");
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the onRestoreInstanceState-method,
     * if the key is null.
     */
    public final void testOnRestoreInstanceStateThrowsExceptionWhenKeyIsNull() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            abstractListAdapter.onRestoreInstanceState(new Bundle(), null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the
     * onRestoreInstanceState-method, if the key is empty.
     */
    public final void testOnRestoreInstanceStateThrowsExceptionWhenKeyIsEmpty() {
        try {
            AbstractListAdapterImplementation abstractListAdapter = createAdapter();
            abstractListAdapter.onRestoreInstanceState(new Bundle(), "");
            Assert.fail();
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Tests the functionality of the hashCode-method.
     */
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

    /**
     * Tests the functionality of the equals-method.
     */
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