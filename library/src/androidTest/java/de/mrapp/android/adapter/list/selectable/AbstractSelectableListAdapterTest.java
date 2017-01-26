/*
 * Copyright 2014 - 2017 Michael Rapp
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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;

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
import de.mrapp.android.adapter.logging.LogLevel;

import static org.mockito.Mockito.mock;

/**
 * Tests the functionality of the class {@link AbstractSelectableListAdapter}.
 *
 * @author Michael Rapp
 */
public class AbstractSelectableListAdapterTest extends AndroidTestCase {

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

    /**
     * Tests, if all properties are set correctly by the constructor.
     */
    public final void testConstructor() {
        boolean selectItemOnClick = true;
        Set<ListSelectionListener<Object>> selectionListeners =
                new LinkedHashSet<ListSelectionListener<Object>>();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(getContext(),
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the set, which contains the
     * selection listeners, which is passed to the constructor, is null.
     */
    public final void testConstructorThrowsExceptionWhenSelectionListenersIsNull() {
        try {
            new AbstractSelectableListAdapterImplementation(getContext(),
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
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to add a listener, which should be
     * notified, when the selection of an item has been changed.
     */
    @SuppressWarnings("unchecked")
    public final void testAddSelectionListener() {
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(getContext(),
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if a {@link ListSelectionListener},
     * which is null, should be added to the adapter.
     */
    public final void testAddSelectionListenerThrowsExceptionWhenListenerIsNull() {
        try {
            AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                    new AbstractSelectableListAdapterImplementation(getContext(),
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
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to remove a listener, which should not be
     * notified, when an item's selection has been changed, anymore.
     */
    @SuppressWarnings("unchecked")
    public final void testRemoveSelectionListener() {
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(getContext(),
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if a {@link ListSelectionListener},
     * which is null, should be removed from the adapter.
     */
    public final void testRemoveAdapterListenerThrowsExceptionWhenListenerIsNull() {
        try {
            AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                    new AbstractSelectableListAdapterImplementation(getContext(),
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
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to retrieve, whether the item, which
     * belongs to a specific index, is selected, or not, if the item is actually selected.
     */
    public final void testIsSelectedByIndexWhenItemIsSelected() {
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve, whether the item, which
     * belongs to a specific index, is selected, or not, if the item is not selected.
     */
    public final void testIsSelectedByIndexWhenItemIsNotSelected() {
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(getContext(),
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

    /**
     * Ensures, that an {@link IndexOutOfBoundsException} is thrown, by the method, which allows to
     * retrieve, whether the item, which belongs to a specific index, is selected, or not, if the
     * index is invalid.
     */
    public final void testIsSelectedByIndexThrowsExceptionWhenIndexIsInvalid() {
        try {
            AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                    new AbstractSelectableListAdapterImplementation(getContext(),
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
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to retrieve, whether a specific item is
     * selected, or not, if the item is actually selected.
     */
    public final void testIsSelectedWhenItemIsSelected() {
        Object item = new Object();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve, whether a specific item is
     * selected, or not, if the item is not selected.
     */
    public final void testIsSelectedWhenItemIsNotSelected() {
        Object item = new Object();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(getContext(),
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

    /**
     * Ensures, that a {@link NoSuchElementException} is thrown by the method, which allows to
     * retrieve, whether a specific item is selected, or not, if the item does not belong to the
     * adapter.
     */
    public final void testIsSelectedThrowsExceptionWhenAdapterDoesNotContainItem() {
        try {
            AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                    new AbstractSelectableListAdapterImplementation(getContext(),
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
            Assert.fail();
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to
     * retrieve, whether a specific item is selected, or not, if the item is null.
     */
    public final void testIsSelectedThrowsExceptionWhenItemIsNull() {
        try {
            AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                    new AbstractSelectableListAdapterImplementation(getContext(),
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
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the number of selected
     * items.
     */
    public final void testGetNumberOfSelectedItems() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the number of selected
     * items.
     */
    public final void testGetNumberOfSelectedItemsWhenAdapterIsEmpty() {
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the method, which allows to retrieve the number of selected
     * items.
     */
    public final void testGetNumberOfSelectedItemsWhenNoItemIsSelected() {
        Object item1 = new Object();
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the onSaveInstanceState-method.
     */
    public final void testOnSaveInstanceState() {
        Bundle outState = new Bundle();
        boolean selectItemOnClick = true;
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the onRestoreInstanceState-method.
     */
    public final void testOnRestoreInstanceState() {
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter =
                new AbstractSelectableListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the applyDecorator-method.
     */
    public final void testApplyDecorator() {
        Object item = new Object();
        Context context = getContext();
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

    /**
     * Tests the functionality of the hashCode-method.
     */
    public final void testHashCode() {
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter1 =
                new AbstractSelectableListAdapterImplementation(getContext(),
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
                new AbstractSelectableListAdapterImplementation(getContext(),
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

    /**
     * Tests the functionality of the equals-method.
     */
    public final void testEquals() {
        AbstractSelectableListAdapterImplementation abstractSelectableListAdapter1 =
                new AbstractSelectableListAdapterImplementation(getContext(),
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
                new AbstractSelectableListAdapterImplementation(getContext(),
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