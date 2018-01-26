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
package de.mrapp.android.adapter.list.filterable;

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
import java.util.List;
import java.util.Set;

import de.mrapp.android.adapter.DataSetObserver;
import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.Filterable;
import de.mrapp.android.adapter.ListDecorator;
import de.mrapp.android.adapter.Order;
import de.mrapp.android.adapter.datastructure.AppliedFilter;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.datastructure.item.UnmodifiableItemList;
import de.mrapp.android.adapter.list.ListAdapter;
import de.mrapp.android.adapter.list.ListAdapterItemClickListener;
import de.mrapp.android.adapter.list.ListAdapterItemLongClickListener;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.list.sortable.AbstractSortableListAdapter;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.util.logging.LogLevel;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests the functionality of the class {@link AbstractFilterableListAdapter}.
 *
 * @author Michael Rapp
 */
public class AbstractFilterableListAdapterTest extends AndroidTestCase {

    /**
     * An implementation of the abstract class {@link AbstractFilterableListAdapter}, which is
     * needed for test purposes.
     */
    private class AbstractFilterableListAdapterImplementation extends
            AbstractFilterableListAdapter<FilterableImplementation, ListDecorator<FilterableImplementation>> {

        /**
         * The constant serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        protected void applyDecorator(@NonNull final Context context, @NonNull final View view,
                                      final int index) {

        }

        /**
         * Creates a new adapter, whose underlying data is managed as a filterable list of arbitrary
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
         *         The log level, which should be used for logging, as a value of the enum LogLevel.
         *         The log level may not be null
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
         * @param filterListeners
         *         A set, which contains the listeners, which should be notified, when the adapter's
         *         underlying data has been filtered or an empty set, if no listeners should be
         *         notified
         * @param appliedFilters
         *         A set, which contains the filters, which should be used to filter the adapter's
         *         underlying data or an empty set, if the adapter's underlying data should not be
         *         filtered
         */
        protected AbstractFilterableListAdapterImplementation(final Context context,
                                                              final ListDecorator<FilterableImplementation> decorator,
                                                              final LogLevel logLevel,
                                                              final ArrayList<Item<FilterableImplementation>> items,
                                                              final boolean allowDuplicates,
                                                              final boolean notifyOnChange,
                                                              final Set<ListAdapterItemClickListener<FilterableImplementation>> itemClickListeners,
                                                              final Set<ListAdapterItemLongClickListener<FilterableImplementation>> itemLongClickListeners,
                                                              final Set<ListAdapterListener<FilterableImplementation>> adapterListeners,
                                                              final Set<ListEnableStateListener<FilterableImplementation>> enableStateListeners,
                                                              final int numberOfItemStates,
                                                              final boolean triggerItemStateOnClick,
                                                              final Set<ListItemStateListener<FilterableImplementation>> itemStateListeners,
                                                              final Set<ListSortingListener<FilterableImplementation>> sortingListeners,
                                                              final Set<ListFilterListener<FilterableImplementation>> filterListeners,
                                                              final LinkedHashSet<AppliedFilter<FilterableImplementation>> appliedFilters) {
            super(context, decorator, logLevel, items, allowDuplicates, notifyOnChange,
                    itemClickListeners, itemLongClickListeners, adapterListeners,
                    enableStateListeners, numberOfItemStates, triggerItemStateOnClick,
                    itemStateListeners, sortingListeners, filterListeners, appliedFilters);
        }

        @Override
        public AbstractSortableListAdapter<FilterableImplementation, ListDecorator<FilterableImplementation>> clone()
                throws CloneNotSupportedException {
            return null;
        }

    }

    /**
     * An implementation of the abstract class {@link ListDecorator}, which is needed for test
     * purposes.
     */
    public class ListDecoratorImplementation extends ListDecorator<FilterableImplementation> {

        @SuppressWarnings("ConstantConditions")
        @NonNull
        @Override
        public View onInflateView(@NonNull final LayoutInflater inflater,
                                  @Nullable final ViewGroup parent, final int viewType) {
            return null;
        }

        @Override
        public void onShowItem(@NonNull final Context context,
                               @NonNull final ListAdapter<FilterableImplementation> adapter,
                               @NonNull final View view,
                               @NonNull final FilterableImplementation item, final int viewType,
                               final int index, final boolean enabled, final int state,
                               final boolean filtered) {

        }

    }

    /**
     * An implementation of the interface {@link Filterable}, which is needed for test purposes.
     */
    private class FilterableImplementation
            implements Filterable, Comparable<FilterableImplementation> {

        /**
         * The constant serial version UID.
         */
        private static final long serialVersionUID = 1L;

        /**
         * A value, which is needed for test purposes.
         */
        private String value;

        /**
         * Creates a new implementation of the interface {@link Filterable}.
         *
         * @param value
         *         A value, which is needed for test purposes
         */
        public FilterableImplementation(final String value) {
            this.value = value;
        }

        @Override
        public boolean match(@NonNull final String query, final int flags) {
            return value.contains(query);
        }

        @Override
        public int compareTo(@NonNull final FilterableImplementation another) {
            return value.compareTo(another.value);
        }

    }

    /**
     * An implementation of the interface {@link Filter}, which is needed for test purposes.
     */
    private class FilterImplementation implements Filter<FilterableImplementation> {

        /**
         * The constant serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public boolean match(@NonNull final FilterableImplementation data,
                             @NonNull final String query, final int flags) {
            return data.match(query, flags);
        }

    }

    /**
     * An implementation of the interface {@link Comparator}, which is needed for test purposes.
     */
    private class ComparatorImplementation implements Comparator<FilterableImplementation> {

        @Override
        public int compare(final FilterableImplementation lhs, final FilterableImplementation rhs) {
            return lhs.compareTo(rhs);
        }

    }

    /**
     * Tests, if all properties are set correctly by the constructor.
     */
    public final void testConstructor() {
        Set<ListFilterListener<FilterableImplementation>> filterListeners = new LinkedHashSet<>();
        LinkedHashSet<AppliedFilter<FilterableImplementation>> appliedFilters =
                new LinkedHashSet<>();
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        filterListeners, appliedFilters);
        assertEquals(filterListeners, abstractFilterableListAdapter.getFilterListeners());
        assertEquals(appliedFilters, abstractFilterableListAdapter.getAppliedFilters());
    }

    /**
     * Tests the functionality of the method, which allows to create a deep copy of the filters,
     * which are applied on the adapter.
     */
    @SuppressWarnings("unchecked")
    public final void testCloneAppliedFilters() {
        LinkedHashSet<AppliedFilter<FilterableImplementation>> appliedFilters =
                new LinkedHashSet<>();
        String query = "query";
        Filter<FilterableImplementation> filter = mock(Filter.class);
        AppliedFilter<FilterableImplementation> appliedFilter =
                new AppliedFilter<>(query, 0, filter);
        appliedFilters.add(appliedFilter);
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        appliedFilters);
        Set<AppliedFilter<FilterableImplementation>> clonedAppliedFilters =
                abstractFilterableListAdapter.cloneAppliedFilters();
        assertEquals(appliedFilters.size(), clonedAppliedFilters.size());
        AppliedFilter<FilterableImplementation> clonedAppliedFilter =
                appliedFilters.iterator().next();
        assertEquals(query, clonedAppliedFilter.getQuery());
        assertEquals(filter, clonedAppliedFilter.getFilter());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the unfiltered index, which
     * corresponds to a specific filtered index.
     */
    public final void testGetUnfilteredIndex() {
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        FilterableImplementation item1 = new FilterableImplementation("cdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcquerystringdef");
        FilterableImplementation item3 = new FilterableImplementation("xsbiquerystringdjwi");
        abstractFilterableListAdapter.addItem(item1);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.addItem(item3);
        abstractFilterableListAdapter.applyFilter("querystring", 0);
        assertEquals(1, abstractFilterableListAdapter.getUnfilteredIndex(0));
        assertEquals(2, abstractFilterableListAdapter.getUnfilteredIndex(1));
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the unfiltered index, which
     * corresponds to a specific filtered index, if the adapter is not filtered.
     */
    public final void testGetUnfilteredIndexWhenAdapterIsNotFiltered() {
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        FilterableImplementation item1 = new FilterableImplementation("cdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcquerystringdef");
        abstractFilterableListAdapter.addItem(item1);
        abstractFilterableListAdapter.addItem(item2);
        assertEquals(0, abstractFilterableListAdapter.getUnfilteredIndex(0));
        assertEquals(1, abstractFilterableListAdapter.getUnfilteredIndex(1));
    }

    /**
     * Ensures, that an {@link IndexOutOfBoundsException} is thrown by the method, which allows to
     * retrieve the unfiltered index, which corresponds to a specific filtered index, if the index
     * is less than 0.
     */
    public final void testGetUnfilteredIndexThrowsExceptionWhenIndexIsLessThanZero() {
        try {
            AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                    new AbstractFilterableListAdapterImplementation(getContext(),
                            new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<FilterableImplementation>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                            new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                            new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(),
                            1, false,
                            new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                            new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                            new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                            new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
            abstractFilterableListAdapter.getUnfilteredIndex(-1);
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Ensures, that an {@link IndexOutOfBoundsException} is thrown by the method, which allows to
     * retrieve the unfiltered index, which corresponds to a specific filtered index, if the index
     * is greater than the number of items - 1.
     */
    public final void testGetUnfilteredIndexThrowsExceptionWhenIndexIsTooGreat() {
        try {
            AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                    new AbstractFilterableListAdapterImplementation(getContext(),
                            new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<FilterableImplementation>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                            new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                            new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(),
                            1, false,
                            new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                            new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                            new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                            new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
            abstractFilterableListAdapter.getUnfilteredIndex(0);
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to retrieve, whether a specific filter is
     * currently applied, or not.
     */
    public final void testIsFilterApplied() {
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        FilterableImplementation item = new FilterableImplementation("abcquerystringdef");
        abstractFilterableListAdapter.addItem(item);
        String query = "querystring";
        int flags = 1;
        assertFalse(abstractFilterableListAdapter.isFilterApplied(query, flags));
        abstractFilterableListAdapter.applyFilter(query, flags);
        assertTrue(abstractFilterableListAdapter.isFilterApplied(query, flags));
    }

    /**
     * Tests the functionality of the method, which allows to apply a filter on the adapter.
     */
    @SuppressWarnings("unchecked")
    public final void testApplyFilter() {
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListFilterListener<FilterableImplementation> filterListener =
                mock(ListFilterListener.class);
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        abstractFilterableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractFilterableListAdapter.addFilterListener(filterListener);
        FilterableImplementation item1 = new FilterableImplementation("abcdefghij");
        FilterableImplementation item2 = new FilterableImplementation("abcquerystringdef");
        FilterableImplementation item3 =
                new FilterableImplementation("abcquerystringdefquerystring2ghi");
        abstractFilterableListAdapter.addItem(item1);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.addItem(item3);
        dataSetObserver.reset();
        String query = "querystring";
        int flags = 1;
        List<FilterableImplementation> result =
                abstractFilterableListAdapter.applyFilter(query, flags);
        assertNotNull(result);
        List<FilterableImplementation> unfilteredItems =
                abstractFilterableListAdapter.getAllItems();
        assertEquals(2, abstractFilterableListAdapter.getCount());
        Iterator<FilterableImplementation> iterator = unfilteredItems.iterator();
        assertEquals(item2, iterator.next());
        assertEquals(item3, iterator.next());
        verify(filterListener, times(1))
                .onApplyFilter(eq(abstractFilterableListAdapter), eq(query), eq(flags),
                        isNull(Filter.class), any(UnmodifiableItemList.class),
                        any(UnmodifiableItemList.class));
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
        result = abstractFilterableListAdapter.applyFilter("querystring2", 1);
        assertNotNull(result);
        assertEquals(1, abstractFilterableListAdapter.getCount());
        iterator = abstractFilterableListAdapter.iterator();
        assertEquals(item3, iterator.next());
    }

    /**
     * Tests the functionality of the method, which allows to apply a filter on the adapter, if a
     * filter, which uses the same regular expression, is already applied on the adapter.
     */
    @SuppressWarnings("unchecked")
    public final void testApplyFilterWhenFilterIsAlreadyApplied() {
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListFilterListener<FilterableImplementation> filterListener =
                mock(ListFilterListener.class);
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        abstractFilterableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractFilterableListAdapter.addFilterListener(filterListener);
        FilterableImplementation item1 = new FilterableImplementation("abcdefghij");
        FilterableImplementation item2 = new FilterableImplementation("abcquerystringdef");
        FilterableImplementation item3 =
                new FilterableImplementation("abcquerystringdefquerystring2ghi");
        abstractFilterableListAdapter.addItem(item1);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.addItem(item3);
        dataSetObserver.reset();
        String query = "querystring";
        int flags = 1;
        abstractFilterableListAdapter.applyFilter(query, flags);
        dataSetObserver.reset();
        List<FilterableImplementation> filteredItems =
                abstractFilterableListAdapter.applyFilter(query, flags);
        assertNull(filteredItems);
        assertEquals(2, abstractFilterableListAdapter.getCount());
        verify(filterListener, times(1))
                .onApplyFilter(eq(abstractFilterableListAdapter), eq(query), eq(flags),
                        isNull(Filter.class), any(UnmodifiableItemList.class),
                        any(UnmodifiableItemList.class));
    }

    /**
     * Tests the functionality of the method, which allows to apply a filter, which uses a specific
     * instance of the type {@link Filter}, on the adapter.
     */
    @SuppressWarnings("unchecked")
    public final void testApplyFilterWithFilterParameter() {
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListFilterListener<FilterableImplementation> filterListener =
                mock(ListFilterListener.class);
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        abstractFilterableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractFilterableListAdapter.addFilterListener(filterListener);
        FilterableImplementation item1 = new FilterableImplementation("abcdefghij");
        FilterableImplementation item2 = new FilterableImplementation("abcquerystringdef");
        FilterableImplementation item3 =
                new FilterableImplementation("abcquerystringdefquerystring2ghi");
        abstractFilterableListAdapter.addItem(item1);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.addItem(item3);
        dataSetObserver.reset();
        String query = "querystring";
        int flags = 1;
        Filter<FilterableImplementation> filter = new FilterImplementation();
        List<FilterableImplementation> result =
                abstractFilterableListAdapter.applyFilter(query, 1, filter);
        assertNotNull(result);
        assertEquals(2, abstractFilterableListAdapter.getCount());
        List<FilterableImplementation> unfilteredItems =
                abstractFilterableListAdapter.getAllItems();
        Iterator<FilterableImplementation> iterator = unfilteredItems.iterator();
        assertEquals(item2, iterator.next());
        assertEquals(item3, iterator.next());
        verify(filterListener, times(1))
                .onApplyFilter(eq(abstractFilterableListAdapter), eq(query), eq(flags), eq(filter),
                        any(UnmodifiableItemList.class), any(UnmodifiableItemList.class));
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
        result = abstractFilterableListAdapter.applyFilter("querystring2", 1, filter);
        assertNotNull(result);
        assertEquals(1, abstractFilterableListAdapter.getCount());
        iterator = abstractFilterableListAdapter.iterator();
        assertEquals(item3, iterator.next());
    }

    /**
     * Tests the functionality of the method, which allows to apply a filter, which uses a specific
     * instance of the type {@link Filter}, on the adapter, if a filter, which uses the same regular
     * expression is already applied on the adapter.
     */
    @SuppressWarnings("unchecked")
    public final void testApplyFilterWithFilterParameterWhenFilterIsAlreadyApplied() {
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListFilterListener<FilterableImplementation> filterListener =
                mock(ListFilterListener.class);
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        abstractFilterableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractFilterableListAdapter.addFilterListener(filterListener);
        FilterableImplementation item1 = new FilterableImplementation("abcdefghij");
        FilterableImplementation item2 = new FilterableImplementation("abcquerystringdef");
        FilterableImplementation item3 =
                new FilterableImplementation("abcquerystringdefquerystring2ghi");
        abstractFilterableListAdapter.addItem(item1);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.addItem(item3);
        String query = "querystring";
        int flags = 1;
        Filter<FilterableImplementation> filter = new FilterImplementation();
        abstractFilterableListAdapter.applyFilter(query, flags, filter);
        dataSetObserver.reset();
        List<FilterableImplementation> result =
                abstractFilterableListAdapter.applyFilter(query, flags, filter);
        assertNull(result);
        assertEquals(2, abstractFilterableListAdapter.getCount());
        verify(filterListener, times(1))
                .onApplyFilter(eq(abstractFilterableListAdapter), eq(query), eq(flags), eq(filter),
                        any(UnmodifiableItemList.class), any(UnmodifiableItemList.class));
    }

    /**
     * Tests the functionality of the method, which allows to reset a filter.
     */
    @SuppressWarnings("unchecked")
    public final void testResetFilter() {
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListFilterListener<FilterableImplementation> filterListener =
                mock(ListFilterListener.class);
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        abstractFilterableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractFilterableListAdapter.addFilterListener(filterListener);
        FilterableImplementation item1 = new FilterableImplementation("abcdefghij");
        FilterableImplementation item2 = new FilterableImplementation("abcquerystringdef");
        FilterableImplementation item3 =
                new FilterableImplementation("abcquerystringdefquerystring2ghi");
        abstractFilterableListAdapter.addItem(item1);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.addItem(item3);
        String query = "querystring2";
        int flags = 1;
        abstractFilterableListAdapter.applyFilter(query, flags);
        abstractFilterableListAdapter.applyFilter("querystring", flags);
        dataSetObserver.reset();
        boolean reseted = abstractFilterableListAdapter.resetFilter(query, flags);
        assertTrue(reseted);
        List<FilterableImplementation> filteredItems = abstractFilterableListAdapter.getAllItems();
        assertEquals(2, filteredItems.size());
        Iterator<FilterableImplementation> iterator = filteredItems.iterator();
        assertEquals(item2, iterator.next());
        assertEquals(item3, iterator.next());
        verify(filterListener, times(1))
                .onResetFilter(eq(abstractFilterableListAdapter), eq(query), eq(flags),
                        any(UnmodifiableItemList.class));
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the method, which allows to reset a filter, if no filter, which
     * uses the given regular expression, is applied on the adapter.
     */
    @SuppressWarnings("unchecked")
    public final void testResetFilterWhenNoSuchFilterIsApplied() {
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListFilterListener<FilterableImplementation> filterListener =
                mock(ListFilterListener.class);
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        abstractFilterableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractFilterableListAdapter.addFilterListener(filterListener);
        String query = "";
        int flags = 0;
        boolean reseted = abstractFilterableListAdapter.resetFilter(query, flags);
        assertFalse(reseted);
        verify(filterListener, times(0))
                .onResetFilter(eq(abstractFilterableListAdapter), eq(query), eq(flags),
                        any(List.class));
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the method, which allows to reset all filters.
     */
    @SuppressWarnings("unchecked")
    public final void testResetAllFilters() {
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListFilterListener<FilterableImplementation> filterListener =
                mock(ListFilterListener.class);
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        abstractFilterableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractFilterableListAdapter.addFilterListener(filterListener);
        FilterableImplementation item1 = new FilterableImplementation("abcdefghij");
        FilterableImplementation item2 = new FilterableImplementation("abcquerystringdef");
        FilterableImplementation item3 =
                new FilterableImplementation("abcquerystringdefquerystring2ghi");
        abstractFilterableListAdapter.addItem(item1);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.addItem(item3);
        String query1 = "querystring2";
        String query2 = "querystring";
        int flags = 1;
        abstractFilterableListAdapter.applyFilter(query1, flags);
        abstractFilterableListAdapter.applyFilter(query2, flags);
        dataSetObserver.reset();
        abstractFilterableListAdapter.resetAllFilters();
        Collection<FilterableImplementation> filteredItems =
                abstractFilterableListAdapter.getAllItems();
        assertEquals(3, filteredItems.size());
        Iterator<FilterableImplementation> iterator = filteredItems.iterator();
        assertEquals(item1, iterator.next());
        assertEquals(item2, iterator.next());
        assertEquals(item3, iterator.next());
        verify(filterListener, times(1))
                .onResetFilter(eq(abstractFilterableListAdapter), eq(query1), eq(flags),
                        any(List.class));
        verify(filterListener, times(1))
                .onResetFilter(eq(abstractFilterableListAdapter), eq(query2), eq(flags),
                        any(List.class));
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve, whether at least one filter
     * is applied on the adapter, or not.
     */
    public final void testIsFiltered() {
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        assertFalse(abstractFilterableListAdapter.isFiltered());
        abstractFilterableListAdapter.applyFilter("", 0);
        assertTrue(abstractFilterableListAdapter.isFiltered());
    }

    /**
     * Tests the functionality of the method, which allows to add a listener, which should be
     * notified, when the adapter's underlying data has been filtered.
     */
    @SuppressWarnings("unchecked")
    public final void testAddFilterListener() {
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        ListFilterListener<FilterableImplementation> filterListener =
                mock(ListFilterListener.class);
        assertTrue(abstractFilterableListAdapter.getFilterListeners().isEmpty());
        abstractFilterableListAdapter.addFilterListener(filterListener);
        abstractFilterableListAdapter.addFilterListener(filterListener);
        assertEquals(1, abstractFilterableListAdapter.getFilterListeners().size());
        assertTrue(abstractFilterableListAdapter.getFilterListeners().contains(filterListener));
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if a {@link ListFilterListener},
     * which is null, should be added to the adapter.
     */
    public final void testAddFilterListenerThrowsExceptionWhenListenerIsNull() {
        try {
            AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                    new AbstractFilterableListAdapterImplementation(getContext(),
                            new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<FilterableImplementation>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                            new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                            new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(),
                            1, false,
                            new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                            new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                            new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                            new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
            abstractFilterableListAdapter.addFilterListener(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to add a listener, which should be
     * notified, when the adapter's underlying data has been filtered.
     */
    @SuppressWarnings("unchecked")
    public final void testRemoveFilterListener() {
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        ListFilterListener<FilterableImplementation> filterListener =
                mock(ListFilterListener.class);
        abstractFilterableListAdapter.removeFilterListener(filterListener);
        abstractFilterableListAdapter.addFilterListener(filterListener);
        assertFalse(abstractFilterableListAdapter.getFilterListeners().isEmpty());
        abstractFilterableListAdapter.removeFilterListener(filterListener);
        assertTrue(abstractFilterableListAdapter.getFilterListeners().isEmpty());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if a {@link ListFilterListener},
     * which is null, should be removed from the adapter.
     */
    public final void testRemoveFilterListenerThrowsExceptionWhenListenerIsNull() {
        try {
            AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                    new AbstractFilterableListAdapterImplementation(getContext(),
                            new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<FilterableImplementation>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                            new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                            new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(),
                            1, false,
                            new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                            new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                            new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                            new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
            abstractFilterableListAdapter.removeFilterListener(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that the unfiltered items are also sorted, when sorting a filtered adapter in
     * ascending order.
     */
    public final void testAdaptUnfilteredItemsWhenSortingInAscendingOrder() {
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        FilterableImplementation item1 = new FilterableImplementation("cdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcquerystringdef");
        FilterableImplementation item3 =
                new FilterableImplementation("abcquerystringdefquerystring2ghi");
        abstractFilterableListAdapter.addItem(item1);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.addItem(item3);
        abstractFilterableListAdapter.applyFilter("querystring", 0);
        abstractFilterableListAdapter.sort();
        abstractFilterableListAdapter.resetAllFilters();
        Iterator<FilterableImplementation> iterator = abstractFilterableListAdapter.iterator();
        assertEquals(item3, iterator.next());
        assertEquals(item2, iterator.next());
        assertEquals(item1, iterator.next());
    }

    /**
     * Ensures, that the unfiltered items are also sorted, when sorting a filtered adapter in
     * ascending order.
     */
    public final void testAdaptUnfilteredItemsWhenSortingInDescendingOrder() {
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        FilterableImplementation item1 = new FilterableImplementation("abcdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcquerystringdef");
        FilterableImplementation item3 =
                new FilterableImplementation("cquerystringdefquerystring2ghi");
        abstractFilterableListAdapter.addItem(item1);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.addItem(item3);
        abstractFilterableListAdapter.applyFilter("querystring", 0);
        abstractFilterableListAdapter.sort(Order.DESCENDING);
        abstractFilterableListAdapter.resetAllFilters();
        Iterator<FilterableImplementation> iterator = abstractFilterableListAdapter.iterator();
        assertEquals(item3, iterator.next());
        assertEquals(item2, iterator.next());
        assertEquals(item1, iterator.next());
    }

    /**
     * Ensures, that the unfiltered items are also sorted, when sorting a filtered adapter by using
     * a comparator in ascending order.
     */
    public final void testAdaptUnfilteredItemsWhenSortingByComparatorInAscendingOrder() {
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        FilterableImplementation item1 = new FilterableImplementation("cdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcquerystringdef");
        FilterableImplementation item3 =
                new FilterableImplementation("abcquerystringdefquerystring2ghi");
        abstractFilterableListAdapter.addItem(item1);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.addItem(item3);
        abstractFilterableListAdapter.applyFilter("querystring", 0);
        abstractFilterableListAdapter.sort(new ComparatorImplementation());
        abstractFilterableListAdapter.resetAllFilters();
        Iterator<FilterableImplementation> iterator = abstractFilterableListAdapter.iterator();
        assertEquals(item3, iterator.next());
        assertEquals(item2, iterator.next());
        assertEquals(item1, iterator.next());
    }

    /**
     * Ensures, that the unfiltered items are also sorted, when sorting a filtered adapter by using
     * a comparator in ascending order.
     */
    public final void testAdaptUnfilteredItemsWhenSortingByComparatorInDescendingOrder() {
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        FilterableImplementation item1 = new FilterableImplementation("abcdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcquerystringdef");
        FilterableImplementation item3 =
                new FilterableImplementation("cquerystringdefquerystring2ghi");
        abstractFilterableListAdapter.addItem(item1);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.addItem(item3);
        abstractFilterableListAdapter.applyFilter("querystring", 0);
        abstractFilterableListAdapter.sort(Order.DESCENDING, new ComparatorImplementation());
        abstractFilterableListAdapter.resetAllFilters();
        Iterator<FilterableImplementation> iterator = abstractFilterableListAdapter.iterator();
        assertEquals(item3, iterator.next());
        assertEquals(item2, iterator.next());
        assertEquals(item1, iterator.next());
    }

    /**
     * Ensures, that an item, which is added to a filtered adapter, is also added to the unfiltered
     * items, if the item matches the applied filters.
     */
    public final void testAdaptUnfilteredItemsWhenMatchingItemIsAdded() {
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        FilterableImplementation item1 = new FilterableImplementation("cdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcquerystringdef");
        FilterableImplementation item3 =
                new FilterableImplementation("abcquerystringdefquerystring2ghi");
        abstractFilterableListAdapter.addItem(item1);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.applyFilter("querystring", 0);
        abstractFilterableListAdapter.addItem(item3);
        assertTrue(abstractFilterableListAdapter.containsItem(item3));
        abstractFilterableListAdapter.resetAllFilters();
        Iterator<FilterableImplementation> iterator = abstractFilterableListAdapter.iterator();
        assertEquals(item1, iterator.next());
        assertEquals(item3, iterator.next());
        assertEquals(item2, iterator.next());
    }

    /**
     * Ensures, that an item, which is added to a filtered adapter, is also added to the unfiltered
     * items, but removed from the filtered items, if the item does not match the applied filters.
     */
    public final void testAdaptUnfilteredItemsWhenNotMatchingItemIsAdded() {
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        FilterableImplementation item1 = new FilterableImplementation("cdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcquerystringdef");
        FilterableImplementation item3 = new FilterableImplementation("abcdefghijklmnopqrstuvwxyz");
        abstractFilterableListAdapter.addItem(item1);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.applyFilter("querystring", 0);
        abstractFilterableListAdapter.addItem(item3);
        assertFalse(abstractFilterableListAdapter.containsItem(item3));
        abstractFilterableListAdapter.resetAllFilters();
        Iterator<FilterableImplementation> iterator = abstractFilterableListAdapter.iterator();
        assertEquals(item1, iterator.next());
        assertEquals(item3, iterator.next());
        assertEquals(item2, iterator.next());
    }

    /**
     * Ensures, that an item, which is removed from a filtered adapter, is also removed from the
     * unfiltered items.
     */
    public final void testAdaptUnfilteredItemsWhenItemIsRemoved() {
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        FilterableImplementation item1 = new FilterableImplementation("cdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcquerystringdef");
        FilterableImplementation item3 = new FilterableImplementation("xsbiquerystringdjwi");
        abstractFilterableListAdapter.addItem(item1);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.addItem(item3);
        abstractFilterableListAdapter.applyFilter("querystring", 0);
        abstractFilterableListAdapter.removeItem(item2);
        assertFalse(abstractFilterableListAdapter.containsItem(item2));
        abstractFilterableListAdapter.resetAllFilters();
        assertFalse(abstractFilterableListAdapter.containsItem(item2));
        Iterator<FilterableImplementation> iterator = abstractFilterableListAdapter.iterator();
        assertEquals(item1, iterator.next());
        assertEquals(item3, iterator.next());
    }

    /**
     * Ensures, that an item, which is removed from a filtered adapter, is also removed from the
     * unfiltered items, if the adapter contains duplicates.
     */
    public final void testAdaptUnfilteredItemsWhenDuplicateItemIsRemoved() {
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), true, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        FilterableImplementation item1 = new FilterableImplementation("cdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcquerystringdef");
        abstractFilterableListAdapter.addItem(item1);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.setEnabled(2, false);
        abstractFilterableListAdapter.applyFilter("querystring", 0);
        abstractFilterableListAdapter.removeItem(0);
        assertEquals(1, abstractFilterableListAdapter.getCount());
        abstractFilterableListAdapter.resetAllFilters();
        assertEquals(2, abstractFilterableListAdapter.getCount());
        Iterator<FilterableImplementation> iterator = abstractFilterableListAdapter.iterator();
        assertEquals(item1, iterator.next());
        assertEquals(item2, iterator.next());
        assertFalse(abstractFilterableListAdapter.isEnabled(1));
    }

    /**
     * Ensures, that an item, which is enabled in a filtered adapter, is also enabled in the
     * unfiltered items.
     */
    public final void testAdaptUnfilteredItemWhenItemIsEnabled() {
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), true, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        FilterableImplementation item1 = new FilterableImplementation("cdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcquerystringdef");
        abstractFilterableListAdapter.addItem(item1);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.setEnabled(1, false);
        abstractFilterableListAdapter.applyFilter("querystring", 0);
        assertFalse(abstractFilterableListAdapter.isEnabled(0));
        abstractFilterableListAdapter.resetAllFilters();
        abstractFilterableListAdapter.setEnabled(0, true);
        abstractFilterableListAdapter.resetAllFilters();
        assertTrue(abstractFilterableListAdapter.isEnabled(0));
    }

    /**
     * Ensures, that an item, which is disabled in a filtered adapter, is also disabled in the
     * unfiltered items.
     */
    public final void testAdaptUnfilteredItemWhenItemIsDisabled() {
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), true, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        FilterableImplementation item1 = new FilterableImplementation("cdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcquerystringdef");
        abstractFilterableListAdapter.addItem(item1);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.applyFilter("querystring", 0);
        assertTrue(abstractFilterableListAdapter.isEnabled(0));
        abstractFilterableListAdapter.resetAllFilters();
        abstractFilterableListAdapter.setEnabled(0, false);
        abstractFilterableListAdapter.resetAllFilters();
        assertFalse(abstractFilterableListAdapter.isEnabled(0));
    }

    /**
     * Ensures, that the state of an item, whose state is changed in a filtered adapter, is also
     * changed in the unfiltered items.
     */
    public final void testAdaptUnfilteredItemWhenItemStateChanged() {
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), true, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 2,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        FilterableImplementation item1 = new FilterableImplementation("cdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcquerystringdef");
        abstractFilterableListAdapter.addItem(item1);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.addItem(item2);
        abstractFilterableListAdapter.setItemState(1, 1);
        abstractFilterableListAdapter.applyFilter("querystring", 0);
        assertEquals(1, abstractFilterableListAdapter.getItemState(0));
        abstractFilterableListAdapter.resetAllFilters();
        abstractFilterableListAdapter.setItemState(0, 0);
        abstractFilterableListAdapter.resetAllFilters();
        assertEquals(0, abstractFilterableListAdapter.getItemState(0));
    }

    /**
     * Tests the functionality of the onSaveInstanceState-method.
     */
    @SuppressWarnings("unchecked")
    public final void testOnSaveInstanceState() {
        Bundle outState = new Bundle();
        String query = "querystring";
        int flags = 1;
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        abstractFilterableListAdapter.applyFilter(query, flags);
        abstractFilterableListAdapter.onSaveInstanceState(outState);
        LinkedHashSet<AppliedFilter<FilterableImplementation>> savedAppliedFilters =
                (LinkedHashSet<AppliedFilter<FilterableImplementation>>) outState
                        .getSerializable(AbstractFilterableListAdapter.APPLIED_FILTERS_BUNDLE_KEY);
        Iterator<AppliedFilter<FilterableImplementation>> appliedFiltersIterator =
                savedAppliedFilters.iterator();
        assertEquals(new AppliedFilter<FilterableImplementation>(query, flags),
                appliedFiltersIterator.next());
    }

    /**
     * Tests the functionality of the onRestoreInstanceState-method.
     */
    public final void testOnRestoreInstanceState() {
        String key = "keyadapter";
        FilterableImplementation item1 = new FilterableImplementation("abcdef");
        FilterableImplementation item2 = new FilterableImplementation("abcquerystringdef");
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        abstractFilterableListAdapter.addItem(item1);
        abstractFilterableListAdapter.addItem(item2);
        Bundle savedInstanceState = new Bundle();
        abstractFilterableListAdapter.onSaveInstanceState(savedInstanceState, key);
        AppliedFilter<FilterableImplementation> appliedFilter =
                new AppliedFilter<>("querystring", 0);
        LinkedHashSet<AppliedFilter<FilterableImplementation>> appliedFilters =
                new LinkedHashSet<>();
        appliedFilters.add(appliedFilter);
        Bundle savedState = savedInstanceState.getBundle(key);
        savedState.putSerializable(AbstractFilterableListAdapter.APPLIED_FILTERS_BUNDLE_KEY,
                appliedFilters);
        DataSetObserver dataSetObserver = new DataSetObserver();
        abstractFilterableListAdapter =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        abstractFilterableListAdapter.registerDataSetObserver(dataSetObserver);
        abstractFilterableListAdapter.onRestoreInstanceState(savedInstanceState, key);
        Iterator<AppliedFilter<FilterableImplementation>> appliedFiltersIterator =
                abstractFilterableListAdapter.getAppliedFilters().iterator();
        assertEquals(appliedFilter, appliedFiltersIterator.next());
        assertEquals(1, abstractFilterableListAdapter.getCount());
        assertEquals(item2, abstractFilterableListAdapter.iterator().next());
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the hashCode-method.
     */
    public final void testHashCode() {
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter1 =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter2 =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        assertEquals(abstractFilterableListAdapter1.hashCode(),
                abstractFilterableListAdapter1.hashCode());
        assertEquals(abstractFilterableListAdapter1.hashCode(),
                abstractFilterableListAdapter2.hashCode());
        abstractFilterableListAdapter1.allowDuplicates(true);
        assertNotSame(abstractFilterableListAdapter1.hashCode(),
                abstractFilterableListAdapter2.hashCode());
        abstractFilterableListAdapter2
                .allowDuplicates(abstractFilterableListAdapter1.areDuplicatesAllowed());
        abstractFilterableListAdapter1.applyFilter("", 0);
        assertNotSame(abstractFilterableListAdapter1.hashCode(),
                abstractFilterableListAdapter2.hashCode());
        abstractFilterableListAdapter2.applyFilter("", 0);
    }

    /**
     * Tests the functionality of the equals-method.
     */
    public final void testEquals() {
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter1 =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        AbstractFilterableListAdapterImplementation abstractFilterableListAdapter2 =
                new AbstractFilterableListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<FilterableImplementation>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListAdapterListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListEnableStateListener<FilterableImplementation>>(), 1,
                        false, new LinkedHashSet<ListItemStateListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListSortingListener<FilterableImplementation>>(),
                        new LinkedHashSet<ListFilterListener<FilterableImplementation>>(),
                        new LinkedHashSet<AppliedFilter<FilterableImplementation>>());
        assertTrue(abstractFilterableListAdapter1.equals(abstractFilterableListAdapter1));
        assertTrue(abstractFilterableListAdapter1.equals(abstractFilterableListAdapter2));
        assertFalse(abstractFilterableListAdapter1.equals(null));
        assertFalse(abstractFilterableListAdapter2.equals(new Object()));
        abstractFilterableListAdapter1.allowDuplicates(true);
        assertFalse(abstractFilterableListAdapter1.equals(abstractFilterableListAdapter2));
        abstractFilterableListAdapter2
                .allowDuplicates(abstractFilterableListAdapter1.areDuplicatesAllowed());
        abstractFilterableListAdapter1.applyFilter("", 0);
        assertFalse(abstractFilterableListAdapter1.equals(abstractFilterableListAdapter2));
        abstractFilterableListAdapter2.applyFilter("", 0);
    }

}