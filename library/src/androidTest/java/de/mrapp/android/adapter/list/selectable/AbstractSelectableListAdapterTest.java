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
package de.mrapp.android.adapter.list.selectable;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import de.mrapp.android.adapter.R;
import de.mrapp.android.adapter.SelectableListDecorator;
import de.mrapp.android.adapter.datastructure.AppliedFilter;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.list.ListAdapterItemClickListener;
import de.mrapp.android.adapter.list.ListAdapterItemLongClickListener;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.util.logging.LogLevel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Tests the functionality of the class {@link AbstractSelectableListAdapter}.
 *
 * @author Michael Rapp
 */
@RunWith(AndroidJUnit4.class)
public class AbstractSelectableListAdapterTest {

    /**
     * An implementation of the abstract class {@link AbstractSelectableListAdapter}, which is
     * needed for test purposes.
     */
    private class AbstractSelectableListAdapterImplementation
            extends AbstractSelectableListAdapter<Object> {

        /**
         * The constant serial version UID.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Creates a new adapter, whose underlying data is managed as a list of arbitrary items, of
         * which one or multiple items can be selected.
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
         * @param selectItemOnClick
         *         True, if an item should be selected, when it is clicked by the user, false
         *         otherwise
         * @param selectionListeners
         *         A set, which contains the listeners, which should be notified, when an item's
         *         selection has been changed or an empty set, if no listeners should be notified
         */
        protected AbstractSelectableListAdapterImplementation(final Context context,
                                                              final SelectableListDecorator<Object> decorator,
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
                                                              final Set<ListItemStateListener<Object>> itemStateListeners,
                                                              final Set<ListSortingListener<Object>> sortingListeners,
                                                              final Set<ListFilterListener<Object>> filterListeners,
                                                              final LinkedHashSet<AppliedFilter<Object>> appliedFilters,
                                                              final boolean selectItemOnClick,
                                                              final Set<ListSelectionListener<Object>> selectionListeners) {
            super(context, decorator, logLevel, items, allowDuplicates, notifyOnChange,
                    itemClickListeners, itemLongClickListeners, adapterListeners,
                    enableStateListeners, numberOfItemStates, triggerItemStateOnClick,
                    itemStateListeners, sortingListeners, filterListeners, appliedFilters,
                    selectItemOnClick, selectionListeners);
        }

        /**
         * Sets the selection of the item, which belongs to a specific index.
         *
         * @param index
         *         The index if the item, whose selection should be set, as an {@link Integer}
         *         value
         * @param selected
         *         True, if the item, which belongs to the given index, should be selected, false
         *         otherwise
         */
        public void select(final int index, final boolean selected) {
            getItems().get(index).setSelected(true);
            notifyOnItemSelected(getItem(index), index);
            notifyDataSetChanged();
        }

        /**
         * Sets the selection of a specific item.
         *
         * @param item
         *         The item, whose selection should be set, as an instance of the class {@link
         *         Object}. The item may not be null
         * @param selected
         *         True, if the given item should be selected, false otherwise
         */
        public void setSelected(final Object item, final boolean selected) {
            select(indexOf(item), selected);
        }

        @Override
        public AbstractSelectableListAdapter<Object> clone() throws CloneNotSupportedException {
            return null;
        }

    }

    /**
     * An implementation of the abstract class {@link SelectableListDecorator}, which is needed for
     * test purposes.
     */
    private class SelectableListDecoratorImplementation extends SelectableListDecorator<Object> {

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
                               @NonNull final SelectableListAdapter<Object> adapter,
                               @NonNull final View view, @NonNull final Object item,
                               final int viewType, final int index, final boolean enabled,
                               final int state, final boolean filtered, final boolean selected) {
            hasOnShowItemBeenInvoked = true;
        }

    }

    @Test
    public final void testConstructor() {
        boolean selectItemOnClick = true;
        Set<ListSelectionListener<Object>> selectionListeners = new LinkedHashSet<>();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), selectItemOnClick,
                        selectionListeners);
        assertEquals(selectItemOnClick, abstractSelectableListAdapter.isItemSelectedOnClick());
        assertEquals(selectionListeners, abstractSelectableListAdapter.getSelectionListeners());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionWhenSelectionListenersIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        new AbstractSelectableListAdapterImplementation(context,
                new SelectableListDecoratorImplementation(), LogLevel.ALL,
                new ArrayList<Item<Object>>(), false, true,
                new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                new LinkedHashSet<ListAdapterListener<Object>>(),
                new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                new LinkedHashSet<ListItemStateListener<Object>>(),
                new LinkedHashSet<ListSortingListener<Object>>(),
                new LinkedHashSet<ListFilterListener<Object>>(),
                new LinkedHashSet<AppliedFilter<Object>>(), false, null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddSelectionListener() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        assertEquals(1, abstractSelectableListAdapter.getSelectionListeners().size());
        abstractSelectableListAdapter.addSelectionListener(listSelectionListener);
        abstractSelectableListAdapter.addSelectionListener(listSelectionListener);
        assertEquals(2, abstractSelectableListAdapter.getSelectionListeners().size());
        assertTrue(abstractSelectableListAdapter.getSelectionListeners()
                .contains(listSelectionListener));
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddSelectionListenerThrowsExceptionWhenListenerIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        abstractSelectableListAdapter.addSelectionListener(null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemoveSelectionListener() {
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        abstractSelectableListAdapter.removeSelectionListener(listSelectionListener);
        abstractSelectableListAdapter.addSelectionListener(listSelectionListener);
        assertEquals(2, abstractSelectableListAdapter.getSelectionListeners().size());
        abstractSelectableListAdapter.removeSelectionListener(listSelectionListener);
        assertEquals(1, abstractSelectableListAdapter.getSelectionListeners().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testRemoveAdapterListenerThrowsExceptionWhenListenerIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        abstractSelectableListAdapter.removeSelectionListener(null);
    }

    @Test
    public final void testIsSelectedByIndexWhenItemIsSelected() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        abstractSelectableListAdapter.addItem(new Object());
        abstractSelectableListAdapter.select(0, true);
        assertTrue(abstractSelectableListAdapter.isSelected(0));
    }

    @Test
    public final void testIsSelectedByIndexWhenItemIsNotSelected() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        abstractSelectableListAdapter.addItem(new Object());
        assertFalse(abstractSelectableListAdapter.isSelected(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testIsSelectedByIndexThrowsExceptionWhenIndexIsInvalid() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        assertTrue(abstractSelectableListAdapter.isSelected(-1));
    }

    @Test
    public final void testIsSelectedWhenItemIsSelected() {
        Object item = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        abstractSelectableListAdapter.addItem(item);
        abstractSelectableListAdapter.setSelected(item, true);
        assertTrue(abstractSelectableListAdapter.isSelected(item));
    }

    @Test
    public final void testIsSelectedWhenItemIsNotSelected() {
        Object item = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        abstractSelectableListAdapter.addItem(item);
        assertFalse(abstractSelectableListAdapter.isSelected(item));
    }

    @Test(expected = NoSuchElementException.class)
    public final void testIsSelectedThrowsExceptionWhenAdapterDoesNotContainItem() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        abstractSelectableListAdapter.isSelected(new Object());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testIsSelectedThrowsExceptionWhenItemIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        abstractSelectableListAdapter.isSelected(null);
    }

    @Test
    public final void testGetNumberOfSelectedItems() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        abstractSelectableListAdapter.addItem(item1);
        abstractSelectableListAdapter.addItem(item2);
        abstractSelectableListAdapter.addItem(item3);
        abstractSelectableListAdapter.setSelected(item1, true);
        abstractSelectableListAdapter.setSelected(item3, true);
        assertEquals(2, abstractSelectableListAdapter.getSelectedItemCount());
    }

    @Test
    public final void testGetNumberOfSelectedItemsWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        assertEquals(0, abstractSelectableListAdapter.getSelectedItemCount());
    }

    @Test
    public final void testGetNumberOfSelectedItemsWhenNoItemIsSelected() {
        Object item1 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        abstractSelectableListAdapter.addItem(item1);
        assertEquals(0, abstractSelectableListAdapter.getSelectedItemCount());
    }

    @Test
    public final void testOnSaveInstanceState() {
        Bundle outState = new Bundle();
        boolean selectItemOnClick = true;
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        abstractSelectableListAdapter.selectItemOnClick(selectItemOnClick);
        abstractSelectableListAdapter.onSaveInstanceState(outState);
        boolean savedSelectItemOnClick =
                outState.getBoolean(AbstractSelectableListAdapter.SELECT_ITEM_ON_CLICK_BUNDLE_KEY);
        assertEquals(selectItemOnClick, savedSelectItemOnClick);
    }

    @Test
    public final void testOnRestoreInstanceState() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        Bundle savedInstanceState = new Bundle();
        abstractSelectableListAdapter.onSaveInstanceState(savedInstanceState);
        boolean selectItemOnClick = true;
        savedInstanceState.putBoolean(AbstractSelectableListAdapter.SELECT_ITEM_ON_CLICK_BUNDLE_KEY,
                selectItemOnClick);
        abstractSelectableListAdapter.onRestoreInstanceState(savedInstanceState);
        assertEquals(selectItemOnClick, abstractSelectableListAdapter.isItemSelectedOnClick());
    }

    @Test
    public final void testApplyDecorator() {
        Object item = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SelectableListDecoratorImplementation decorator =
                new SelectableListDecoratorImplementation();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(context, decorator, LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 2, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        abstractSelectableListAdapter.addItem(item);
        abstractSelectableListAdapter.setEnabled(item, false);
        abstractSelectableListAdapter.setItemState(item, 1);
        abstractSelectableListAdapter.setSelected(item, true);
        abstractSelectableListAdapter.attach(new ListView(context));
        View view = abstractSelectableListAdapter.getView(0, null, null);
        view.performClick();
        assertTrue(decorator.hasOnShowItemBeenInvoked);
    }

    @Test
    public final void testHashCode() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter1 =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter2 =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        assertEquals(abstractSelectableListAdapter1.hashCode(),
                abstractSelectableListAdapter1.hashCode());
        assertEquals(abstractSelectableListAdapter1.hashCode(),
                abstractSelectableListAdapter2.hashCode());
        abstractSelectableListAdapter1.allowDuplicates(true);
        assertNotSame(abstractSelectableListAdapter1.hashCode(),
                abstractSelectableListAdapter2.hashCode());
        abstractSelectableListAdapter2
                .allowDuplicates(abstractSelectableListAdapter1.areDuplicatesAllowed());
        abstractSelectableListAdapter1.selectItemOnClick(true);
        assertNotSame(abstractSelectableListAdapter1.hashCode(),
                abstractSelectableListAdapter2.hashCode());
        abstractSelectableListAdapter2
                .selectItemOnClick(abstractSelectableListAdapter1.isItemSelectedOnClick());
    }

    @Test
    public final void testEquals() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter1 =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter2 =
                new AbstractSelectableListAdapterImplementation(context,
                        new SelectableListDecoratorImplementation(), LogLevel.ALL,
                        new ArrayList<Item<Object>>(), false, true,
                        new LinkedHashSet<ListAdapterItemClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterItemLongClickListener<Object>>(),
                        new LinkedHashSet<ListAdapterListener<Object>>(),
                        new LinkedHashSet<ListEnableStateListener<Object>>(), 1, false,
                        new LinkedHashSet<ListItemStateListener<Object>>(),
                        new LinkedHashSet<ListSortingListener<Object>>(),
                        new LinkedHashSet<ListFilterListener<Object>>(),
                        new LinkedHashSet<AppliedFilter<Object>>(), false,
                        new LinkedHashSet<ListSelectionListener<Object>>());
        assertTrue(abstractSelectableListAdapter1.equals(abstractSelectableListAdapter1));
        assertTrue(abstractSelectableListAdapter1.equals(abstractSelectableListAdapter2));
        assertFalse(abstractSelectableListAdapter1.equals(null));
        assertFalse(abstractSelectableListAdapter2.equals(new Object()));
        abstractSelectableListAdapter1.allowDuplicates(true);
        assertFalse(abstractSelectableListAdapter1.equals(abstractSelectableListAdapter2));
        abstractSelectableListAdapter2
                .allowDuplicates(abstractSelectableListAdapter1.areDuplicatesAllowed());
        abstractSelectableListAdapter1.selectItemOnClick(true);
        assertFalse(abstractSelectableListAdapter1.equals(abstractSelectableListAdapter2));
        abstractSelectableListAdapter2
                .selectItemOnClick(abstractSelectableListAdapter1.isItemStateTriggeredOnClick());
    }

}