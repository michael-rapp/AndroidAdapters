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
package de.mrapp.android.adapter.list.enablestate;

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
import java.util.NoSuchElementException;
import java.util.Set;

import de.mrapp.android.adapter.DataSetObserver;
import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.FilterQuery;
import de.mrapp.android.adapter.ListAdapter;
import de.mrapp.android.adapter.ListDecorator;
import de.mrapp.android.adapter.Order;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.list.ListAdapterItemClickListener;
import de.mrapp.android.adapter.list.ListAdapterItemLongClickListener;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.adapter.logging.LogLevel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests the functionality of the class {@link AbstractEnableStateListAdapter}.
 *
 * @author Michael Rapp
 */
public class AbstractEnableStateListAdapterTest extends AndroidTestCase {

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

    /**
     * Tests, if all properties are set correctly by the constructor.
     */
    public final void testConstructor() {
        Set<ListEnableStateListener<Object>> enableStateListeners = new LinkedHashSet<>();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(), enableStateListeners);
        assertEquals(enableStateListeners,
                abstractEnableStateListAdapter.getEnableStateListeners());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the set, which contains the enable
     * state listeners, which is passed to the constructor, is null.
     */
    public final void testConstructorThrowsExceptionWhenEnableStateListenersIsNull() {
        try {
            new AbstractEnableStateListAdapterImplementation(getContext(),
                    new ListDecoratorImplementation(), LogLevel.ALL, new ArrayList<Item<Object>>(),
                    false, true, new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                    new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                    new LinkedHashSet<ListAdapterListener<Object>>(), null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to retrieve, whether all items are
     * enabled, or not, when all items are actually enabled.
     */
    public final void testAreAllItemsEnabledWhenAllItemsAreEnabled() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve, whether all items are
     * enabled, or not, when not all items are enabled.
     */
    public final void testAreAllItemsEnabledWhenNotAllItemsAreEnabled() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve, whether the item, which
     * belongs to a specific index, is enabled or not, when the item is actually enabled.
     */
    public final void testIsEnabledByIndexWhenItemIsEnabled() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        assertTrue(abstractEnableStateListAdapter.isEnabled(0));
    }

    /**
     * Tests the functionality of the method, which allows to retrieve, whether the item, which
     * belongs to a specific index, is enabled or not, when the item is not enabled.
     */
    public final void testIsEnabledByIndexWhenItemIsNotEnabled() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Ensures, that an {@link IndexOutOfBoundsException} is thrown, if it should be retrieved,
     * whether the item, which belongs to a specific index, which is invalid, is enabled or not.
     */
    public final void testIsEnabledByIndexThrowsExceptionWhenIndexIsInvalid() {
        try {
            AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                    new AbstractEnableStateListAdapterImplementation(getContext(),
                            new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>());
            abstractEnableStateListAdapter.isEnabled(-1);
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to retrieve, whether a specific item is
     * enabled or not, when the item is actually enabled.
     */
    public final void testIsEnabledWhenItemIsEnabled() {
        Object item = new Object();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(item);
        assertTrue(abstractEnableStateListAdapter.isEnabled(item));
    }

    /**
     * Tests the functionality of the method, which allows to retrieve, whether a specific item is
     * enabled or not, when the item is not enabled.
     */
    public final void testIsEnabledWhenItemIsNotEnabled() {
        Object item = new Object();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if it should be retrieved, whether a
     * specific item, which is null, is enabled or not.
     */
    public final void testIsEnabledThrowsExceptionWhenItemIsNull() {
        try {
            AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                    new AbstractEnableStateListAdapterImplementation(getContext(),
                            new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>());
            abstractEnableStateListAdapter.isEnabled(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the first
     * enabled item.
     */
    public final void testGetFirstEnabledIndex() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the first
     * enabled item, when the adapter is empty.
     */
    public final void testGetFirstEnabledIndexWhenAdapterIsEmpty() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        assertEquals(-1, abstractEnableStateListAdapter.getFirstEnabledIndex());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the first
     * enabled item, when no item is enabled.
     */
    public final void testGetFirstEnabledIndexWhenNoItemIsEnabled() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the first enabled item.
     */
    public final void testGetFirstEnabledItem() {
        Object item = new Object();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the first enabled item, when
     * the adapter is empty.
     */
    public final void testGetFirstEnabledItemWhenAdapterIsEmpty() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        assertNull(abstractEnableStateListAdapter.getFirstEnabledItem());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the first enabled item, when
     * no item is enabled.
     */
    public final void testGetFirstEnabledItemWhenNoItemIsEnabled() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the last enabled
     * item.
     */
    public final void testGetLastEnabledIndex() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the last enabled
     * item, when the adapter is empty.
     */
    public final void testGetLastEnabledIndexWhenAdapterIsEmpty() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        assertEquals(-1, abstractEnableStateListAdapter.getLastEnabledIndex());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the last enabled
     * item, when no item is enabled.
     */
    public final void testGetLastEnabledIndexWhenNoItemIsEnabled() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the last enabled item.
     */
    public final void testGetLastEnabledItem() {
        Object item = new Object();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the last enabled item, when
     * the adapter is empty.
     */
    public final void testGetLastEnabledItemWhenAdapterIsEmpty() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        assertNull(abstractEnableStateListAdapter.getLastEnabledItem());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the last enabled item, when
     * no item is enabled.
     */
    public final void testGetLastEnabledItemWhenNoItemIsEnabled() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the first
     * disabled item.
     */
    public final void testGetFirstDisabledIndex() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the first
     * disabled item, when the adapter is empty.
     */
    public final void testGetFirstDisabledIndexWhenAdapterIsEmpty() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        assertEquals(-1, abstractEnableStateListAdapter.getFirstDisabledIndex());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the first
     * disabled item, when no item is disabled.
     */
    public final void testGetFirstDisabledIndexWhenNoItemIsDisabled() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        assertEquals(-1, abstractEnableStateListAdapter.getFirstDisabledIndex());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the first disabled item.
     */
    public final void testGetFirstDisabledItem() {
        Object item = new Object();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the first disabled item, when
     * the adapter is empty.
     */
    public final void testGetFirstDisabledItemWhenAdapterIsEmpty() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        assertNull(abstractEnableStateListAdapter.getFirstDisabledItem());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the first disabled item, when
     * no item is disabled.
     */
    public final void testGetFirstDisabledItemWhenNoItemIsDisabled() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        assertNull(abstractEnableStateListAdapter.getFirstDisabledItem());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the last
     * disabled item.
     */
    public final void testGetLastDisabledIndex() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the last
     * disabled item, when the adapter is empty.
     */
    public final void testGetLastDisabledIndexWhenAdapterIsEmpty() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        assertEquals(-1, abstractEnableStateListAdapter.getLastDisabledIndex());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the last
     * disabled item, when no item is disabled.
     */
    public final void testGetLastDisabledIndexWhenNoItemIsDisabled() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        assertEquals(-1, abstractEnableStateListAdapter.getLastDisabledIndex());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the last disabled item.
     */
    public final void testGetLastDisabledItem() {
        Object item = new Object();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the last disabled item, when
     * the adapter is empty.
     */
    public final void testGetLastDisabledItemWhenAdapterIsEmpty() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        assertNull(abstractEnableStateListAdapter.getLastDisabledItem());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the last disabled item, when
     * no item is disabled.
     */
    public final void testGetLastDisabledItemWhenNoItemIsDisabled() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        abstractEnableStateListAdapter.addItem(new Object());
        assertNull(abstractEnableStateListAdapter.getLastDisabledItem());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * the indices of all enabled items.
     */
    public final void testGetEnabledIndices() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * the indices of all enabled items, when the adapter is empty.
     */
    public final void testGetEnabledIndicesWhenAdapterIsEmpty() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        Collection<Integer> enabledIndices = abstractEnableStateListAdapter.getEnabledIndices();
        assertTrue(enabledIndices.isEmpty());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * the indices of all enabled items, when the adapter is empty.
     */
    public final void testGetEnabledIndicesWhenNoItemIsEnabled() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * all enabled items.
     */
    public final void testGetEnabledItems() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * all enabled items, when the adapter is empty.
     */
    public final void testGetEnabledItemsWhenAdapterIsEmpty() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        Collection<Object> enabledItems = abstractEnableStateListAdapter.getEnabledItems();
        assertTrue(enabledItems.isEmpty());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * all enabled items, when no item is enabled.
     */
    public final void testGetEnabledItemsWhenNoItemIsEnabled() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * the indices of all disabled items.
     */
    public final void testGetDisabledIndices() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * the indices of all disabled items, when the adapter is empty.
     */
    public final void testGetDisabledIndicesWhenAdapterIsEmpty() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        Collection<Integer> disabledIndices = abstractEnableStateListAdapter.getDisabledIndices();
        assertTrue(disabledIndices.isEmpty());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * the indices of all disabled items, when no item is disabled.
     */
    public final void testGetDisabledIndicesWhenNoItemIsDisabled() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * all disabled items.
     */
    public final void testGetDisabledItems() {
        Object item1 = new Object();
        Object item2 = new Object();
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * all disabled items, when the adapter is empty.
     */
    public final void testGetDisabledItemsWhenAdapterIsEmpty() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        Collection<Object> disabledItems = abstractEnableStateListAdapter.getDisabledItems();
        assertTrue(disabledItems.isEmpty());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * all disabled items, when no item is disabled.
     */
    public final void testGetDisabledItemsWhenNoItemIsDisabled() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the number of enabled items.
     */
    public final void testGetEnabledItemCount() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the number of enabled items,
     * when the adapter is empty.
     */
    public final void testGetEnabledItemCountWhenAdapterIsEmpty() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
                        new ListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>());
        assertEquals(0, abstractEnableStateListAdapter.getEnabledItemCount());
    }

    /**
     * Tests the functionality of the method, which allows to set the enable state of the item,
     * which belongs to a specific index.
     */
    @SuppressWarnings("unchecked")
    public final void testSetEnabledByIndex() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to set the enable state of the item,
     * which belongs to a specific index, if the item is already enabled.
     */
    @SuppressWarnings("unchecked")
    public final void testSetEnabledByIndexWhenItemIsAlreadyEnabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Ensures, that an {@link IndexOutOfBoundsException} is thrown by the method, which allows to
     * set the enable state of the item, which belongs to a specific index, if the index is
     * invalid.
     */
    public final void testSetEnabledByIndexThrowsExceptionWhenIndexIsInvalid() {
        try {
            AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                    new AbstractEnableStateListAdapterImplementation(getContext(),
                            new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>());
            abstractEnableStateListAdapter.setEnabled(-1, true);
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to set the enable state of a specific
     * item.
     */
    @SuppressWarnings("unchecked")
    public final void testSetEnabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to set the enable state of a specific
     * item, if the item is already enabled.
     */
    @SuppressWarnings("unchecked")
    public final void testSetEnabledWhenItemIsAlreadyEnabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Ensures, that a {@link NoSuchElementException} is thrown by the method, which allows to set
     * the enable state of a specific item, if the item does not belong to the adapter.
     */
    public final void testSetEnabledThrowsExceptionWhenAdapterDoesNotContainItem() {
        try {
            AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                    new AbstractEnableStateListAdapterImplementation(getContext(),
                            new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>());
            abstractEnableStateListAdapter.setEnabled(new Object(), true);
            Assert.fail();
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to set the
     * enable state of an item, if the item is null.
     */
    public final void testSetEnabledThrowsExceptionWhenItemIsNull() {
        try {
            AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                    new AbstractEnableStateListAdapterImplementation(getContext(),
                            new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>());
            abstractEnableStateListAdapter.setEnabled(null, true);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to trigger the enable state of the item,
     * which belongs to a specific index, when the item is enabled.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerEnableStateByIndexWhenItemIsEnabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to trigger the enable state of the item,
     * which belongs to a specific index, when the item is disabled.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerEnableStateByIndexWhenItemIsDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Ensures, that an {@link IndexOutOfBoundsException} is thrown, if the enable state of the
     * item, which belongs to a specific index, which is invalid, should be triggered.
     */
    public final void testTriggerEnableStateByIndexThrowsExceptionWhenIndexIsInvalid() {
        try {
            AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                    new AbstractEnableStateListAdapterImplementation(getContext(),
                            new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>());
            abstractEnableStateListAdapter.triggerEnableState(-1);
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to trigger the enable state of a specific
     * item, when the item is enabled.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerEnableStateWhenItemIsEnabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to trigger the enable state of a specific
     * item, when the item is disabled.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerEnableStateWhenItemIsDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Ensures, that a {@link NoSuchElementException} is thrown, if the enable state of a specific
     * item, which does not belong to the adapter, should be triggered.
     */
    public final void testTriggerEnableStateThrowsExceptionWhenAdapterDoesNotContainItem() {
        try {
            AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                    new AbstractEnableStateListAdapterImplementation(getContext(),
                            new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>());
            abstractEnableStateListAdapter.triggerEnableState(new Object());
            Assert.fail();
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the enable state of a specific
     * item, which is null, should be triggered.
     */
    public final void testTriggerEnableStateThrowsExceptionWhenItemIsNull() {
        try {
            AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                    new AbstractEnableStateListAdapterImplementation(getContext(),
                            new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>());
            abstractEnableStateListAdapter.triggerEnableState(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to set the enable states of all items.
     */
    @SuppressWarnings("unchecked")
    public final void testSetAllEnabled() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to trigger the enable states of all
     * items.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerAllEnableStates() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListEnableStateListener<Object> listEnableStateListener =
                mock(ListEnableStateListener.class);
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to add a listener, which should be
     * notified, when the enable state of an item has been changed.
     */
    @SuppressWarnings("unchecked")
    public final void testAddEnableStateListener() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if a {@link ListEnableStateListener},
     * which is null, should be added to the adapter.
     */
    public final void testAddEnableStateListenerThrowsExceptionWhenListenerIsNull() {
        try {
            AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                    new AbstractEnableStateListAdapterImplementation(getContext(),
                            new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>());
            abstractEnableStateListAdapter.addEnableStateListener(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to remove a listener, which should not be
     * notified, when the enable state of an item has been changed, anymore.
     */
    @SuppressWarnings("unchecked")
    public final void testRemoveEnableStateListener() {
        AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                new AbstractEnableStateListAdapterImplementation(getContext(),
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

    /**
     * Ensures that a {@link NullPointerException} is thrown, if a {@link ListEnableStateListener},
     * which is null, should be removed from the adapter.
     */
    public final void testRemoveEnableStateListenerThrowsExceptionWhenListenerIsNull() {
        try {
            AbstractEnableStateListAdapterImplementation abstractEnableStateListAdapter =
                    new AbstractEnableStateListAdapterImplementation(getContext(),
                            new ListDecoratorImplementation(), LogLevel.ALL,
                            new ArrayList<Item<Object>>(), false, true,
                            new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                            new LinkedHashSet<ListAdapterListener<Object>>(),
                            new LinkedHashSet<ListEnableStateListener<Object>>());
            abstractEnableStateListAdapter.removeEnableStateListener(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

}