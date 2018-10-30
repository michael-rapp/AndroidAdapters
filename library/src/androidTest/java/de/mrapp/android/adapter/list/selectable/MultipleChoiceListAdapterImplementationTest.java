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
package de.mrapp.android.adapter.list.selectable;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import de.mrapp.android.adapter.DataSetObserver;
import de.mrapp.android.adapter.Filterable;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests the functionality of the class {@link MultipleChoiceListAdapterImplementation}.
 *
 * @author Michael Rapp
 */
public class MultipleChoiceListAdapterImplementationTest extends AndroidTestCase {

    /**
     * An implementation of the abstract class {@link SelectableListDecorator}, which is needed for
     * test purposes.
     */
    private class SelectableListDecoratorImplementation extends SelectableListDecorator<Object> {

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
     * Tests, if all properties are set correctly by the protected constructor.
     */
    public final void testProtectedConstructor() {
        Context context = getContext();
        SelectableListDecorator<Object> decorator = new SelectableListDecoratorImplementation();
        ArrayList<Item<Object>> items = new ArrayList<>();
        boolean allowDuplicates = true;
        boolean notifyOnChange = true;
        Set<ListAdapterItemClickListener<Object>> itemClickListeners = new LinkedHashSet<>();
        Set<ListAdapterItemLongClickListener<Object>> itemLongClickListeners =
                new LinkedHashSet<>();
        Set<ListAdapterListener<Object>> adapterListeners = new LinkedHashSet<>();
        Set<ListEnableStateListener<Object>> enableStateListeners = new LinkedHashSet<>();
        int numberOfItemStates = 2;
        boolean triggerItemStateOnClick = true;
        Set<ListItemStateListener<Object>> itemStateListeners = new LinkedHashSet<>();
        Set<ListSortingListener<Object>> sortingListeners = new LinkedHashSet<>();
        Set<ListFilterListener<Object>> filterListeners = new LinkedHashSet<>();
        LinkedHashSet<AppliedFilter<Object>> appliedFilters = new LinkedHashSet<>();
        boolean selectItemOnClick = true;
        Set<ListSelectionListener<Object>> selectionListeners = new LinkedHashSet<>();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context, decorator, LogLevel.ALL,
                        items, allowDuplicates, notifyOnChange, itemClickListeners,
                        itemLongClickListeners, adapterListeners, enableStateListeners,
                        numberOfItemStates, triggerItemStateOnClick, itemStateListeners,
                        sortingListeners, filterListeners, appliedFilters, selectItemOnClick,
                        selectionListeners);
        assertEquals(allowDuplicates,
                multipleChoiceListAdapterImplementation.areDuplicatesAllowed());
        assertEquals(notifyOnChange, multipleChoiceListAdapterImplementation.isNotifiedOnChange());
        assertEquals(numberOfItemStates,
                multipleChoiceListAdapterImplementation.getNumberOfItemStates());
        assertEquals(triggerItemStateOnClick,
                multipleChoiceListAdapterImplementation.isItemStateTriggeredOnClick());
        assertEquals(selectItemOnClick,
                multipleChoiceListAdapterImplementation.isItemSelectedOnClick());
        assertEquals(selectionListeners,
                multipleChoiceListAdapterImplementation.getSelectionListeners());
    }

    /**
     * Tests, if all properties are set correctly by the public constructor.
     */
    public final void testPublicConstructor() {
        Context context = getContext();
        SelectableListDecorator<Object> decorator = new SelectableListDecoratorImplementation();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context, decorator);
        assertEquals(false, multipleChoiceListAdapterImplementation.areDuplicatesAllowed());
        assertEquals(true, multipleChoiceListAdapterImplementation.isNotifiedOnChange());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the first
     * selected item.
     */
    public final void testGetFirstSelectedIndex() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.setSelected(0, true);
        multipleChoiceListAdapterImplementation.setSelected(1, true);
        assertEquals(0, multipleChoiceListAdapterImplementation.getFirstSelectedIndex());
    }

    /**
     * Ensures, that the unfiltered items are adapted, when an item of a filtered adapter is
     * selected.
     */
    public final void testAdaptUnfilteredItemsWhenItemIsSelected() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        FilterableImplementation item1 = new FilterableImplementation("cdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcquerystringdef");
        FilterableImplementation item3 = new FilterableImplementation("xsbiquerystringdjwi");
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        multipleChoiceListAdapterImplementation.addItem(item3);
        multipleChoiceListAdapterImplementation.applyFilter("querystring", 0);
        assertTrue(multipleChoiceListAdapterImplementation.getSelectedIndices().isEmpty());
        multipleChoiceListAdapterImplementation.setSelected(0, true);
        multipleChoiceListAdapterImplementation.resetAllFilters();
        Collection<Integer> selectedIndices =
                multipleChoiceListAdapterImplementation.getSelectedIndices();
        assertEquals(1, selectedIndices.size());
        assertEquals(1, selectedIndices.iterator().next().intValue());
    }

    /**
     * Ensures, that the unfiltered items are adapted, when an item of a filtered adapter is
     * unselected.
     */
    public final void testAdaptUnfilteredItemsWhenItemIsUnselected() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        FilterableImplementation item1 = new FilterableImplementation("cdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcquerystringdef");
        FilterableImplementation item3 = new FilterableImplementation("xsbiquerystringdjwi");
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        multipleChoiceListAdapterImplementation.addItem(item3);
        multipleChoiceListAdapterImplementation.setSelected(1, true);
        multipleChoiceListAdapterImplementation.applyFilter("querystring", 0);
        assertEquals(1, multipleChoiceListAdapterImplementation.getSelectedIndices().size());
        multipleChoiceListAdapterImplementation.setSelected(0, false);
        multipleChoiceListAdapterImplementation.resetAllFilters();
        assertTrue(multipleChoiceListAdapterImplementation.getSelectedIndices().isEmpty());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the first
     * selected item, if the adapter is empty.
     */
    public final void testGetFirstSelectedIndexWhenAdapterIsEmpty() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        assertEquals(-1, multipleChoiceListAdapterImplementation.getFirstSelectedIndex());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the first
     * selected item, if no item is selected.
     */
    public final void testGetFirstSelectedIndexWhenNoItemIsSelected() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        assertEquals(-1, multipleChoiceListAdapterImplementation.getFirstSelectedIndex());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the first selected item.
     */
    public final void testGetFirstSelectedItem() {
        Object item1 = new Object();
        Object item2 = new Object();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        multipleChoiceListAdapterImplementation.setSelected(item1, true);
        multipleChoiceListAdapterImplementation.setSelected(item2, true);
        assertEquals(item1, multipleChoiceListAdapterImplementation.getFirstSelectedItem());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the first selected item, if
     * the adapter is empty.
     */
    public final void testGetFirstSelectedItemWhenAdapterIsEmpty() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        assertNull(multipleChoiceListAdapterImplementation.getFirstSelectedItem());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the first selected item, if
     * no item is selected.
     */
    public final void testGetFirstSelectedItemWhenNoItemIsSelected() {
        Object item1 = new Object();
        Object item2 = new Object();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        assertNull(multipleChoiceListAdapterImplementation.getFirstSelectedItem());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the last
     * selected item.
     */
    public final void testGetLastSelectedIndex() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.setSelected(0, true);
        multipleChoiceListAdapterImplementation.setSelected(1, true);
        assertEquals(1, multipleChoiceListAdapterImplementation.getLastSelectedIndex());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the last
     * selected item, if the adapter is empty.
     */
    public final void testGetLastSelectedIndexWhenAdapterIsEmpty() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        assertEquals(-1, multipleChoiceListAdapterImplementation.getLastSelectedIndex());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the last
     * selected item, if no item is selected.
     */
    public final void testGetLastSelectedIndexWhenNoItemIsSelected() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        assertEquals(-1, multipleChoiceListAdapterImplementation.getLastSelectedIndex());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the last selected item.
     */
    public final void testGetLastSelectedItem() {
        Object item1 = new Object();
        Object item2 = new Object();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        multipleChoiceListAdapterImplementation.setSelected(item1, true);
        multipleChoiceListAdapterImplementation.setSelected(item2, true);
        assertEquals(item2, multipleChoiceListAdapterImplementation.getLastSelectedItem());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the last selected item, if
     * the adapter is empty.
     */
    public final void testGetLastSelectedItemWhenAdapterIsEmpty() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        assertNull(multipleChoiceListAdapterImplementation.getLastSelectedItem());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the last selected item, if no
     * item is selected.
     */
    public final void testGetLastSelectedItemWhenNoItemIsSelected() {
        Object item1 = new Object();
        Object item2 = new Object();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        assertNull(multipleChoiceListAdapterImplementation.getLastSelectedItem());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the first
     * unselected item.
     */
    public final void testGetFirstUnselectedIndex() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        assertEquals(0, multipleChoiceListAdapterImplementation.getFirstUnselectedIndex());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the first
     * unselected item, if the adapter is empty.
     */
    public final void testGetFirstUnselectedIndexWhenAdapterIsEmpty() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        assertEquals(-1, multipleChoiceListAdapterImplementation.getFirstUnselectedIndex());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the first
     * unselected item, if no item is unselected.
     */
    public final void testGetFirstUnselectedIndexWhenNoItemIsUnselected() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.setSelected(0, true);
        multipleChoiceListAdapterImplementation.setSelected(1, true);
        assertEquals(-1, multipleChoiceListAdapterImplementation.getFirstUnselectedIndex());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the first unselected item.
     */
    public final void testGetFirstUnselectedItem() {
        Object item1 = new Object();
        Object item2 = new Object();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        assertEquals(item1, multipleChoiceListAdapterImplementation.getFirstUnselectedItem());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the first unselected item, if
     * the adapter is empty.
     */
    public final void testGetFirstUnselectedItemWhenAdapterIsEmpty() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        assertNull(multipleChoiceListAdapterImplementation.getFirstUnselectedItem());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the first unselected item, if
     * no item is unselected.
     */
    public final void testGetFirstUnselectedItemWhenNoItemIsUnselected() {
        Object item1 = new Object();
        Object item2 = new Object();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        multipleChoiceListAdapterImplementation.setSelected(item1, true);
        multipleChoiceListAdapterImplementation.setSelected(item2, true);
        assertNull(multipleChoiceListAdapterImplementation.getFirstUnselectedItem());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the last
     * unselected item.
     */
    public final void testGetLastUnselectedIndex() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        assertEquals(1, multipleChoiceListAdapterImplementation.getLastUnselectedIndex());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the last
     * unselected item, if the adapter is empty.
     */
    public final void testGetLastUnselectedIndexWhenAdapterIsEmpty() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        assertEquals(-1, multipleChoiceListAdapterImplementation.getLastUnselectedIndex());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the last
     * unselected item, if no item is unselected.
     */
    public final void testGetLastUnselectedIndexWhenNoItemIsUnselected() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.setSelected(0, true);
        multipleChoiceListAdapterImplementation.setSelected(1, true);
        assertEquals(-1, multipleChoiceListAdapterImplementation.getLastUnselectedIndex());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the last unselected item.
     */
    public final void testGetLastUnselectedItem() {
        Object item1 = new Object();
        Object item2 = new Object();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        assertEquals(item2, multipleChoiceListAdapterImplementation.getLastUnselectedItem());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the last unselected item, if
     * the adapter is empty.
     */
    public final void testGetLastUnselectedItemWhenAdapterIsEmpty() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        assertNull(multipleChoiceListAdapterImplementation.getLastUnselectedItem());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the last unselected item, if
     * no item is unselected.
     */
    public final void testGetLastUnselectedItemWhenNoItemIsUnselected() {
        Object item1 = new Object();
        Object item2 = new Object();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        multipleChoiceListAdapterImplementation.setSelected(item1, true);
        multipleChoiceListAdapterImplementation.setSelected(item2, true);
        assertNull(multipleChoiceListAdapterImplementation.getLastUnselectedItem());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * the indices of all selected items.
     */
    public final void testGetSelectedIndices() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        multipleChoiceListAdapterImplementation.addItem(item3);
        multipleChoiceListAdapterImplementation.setSelected(item1, true);
        multipleChoiceListAdapterImplementation.setSelected(item3, true);
        Collection<Integer> indices = multipleChoiceListAdapterImplementation.getSelectedIndices();
        assertEquals(2, indices.size());
        Iterator<Integer> iterator = indices.iterator();
        assertEquals(0, iterator.next().intValue());
        assertEquals(2, iterator.next().intValue());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * the indices of all selected items, if the adapter is empty.
     */
    public final void testGetSelectedIndicesWhenAdapterIsEmpty() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        Collection<Integer> indices = multipleChoiceListAdapterImplementation.getSelectedIndices();
        assertTrue(indices.isEmpty());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * the indices of all selected items, if no item is selected.
     */
    public final void testGetSelectedIndicesWhenNoItemIsSelected() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        Collection<Integer> indices = multipleChoiceListAdapterImplementation.getSelectedIndices();
        assertTrue(indices.isEmpty());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * all selected items.
     */
    public final void testGetSelectedItems() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        multipleChoiceListAdapterImplementation.addItem(item3);
        multipleChoiceListAdapterImplementation.setSelected(item1, true);
        multipleChoiceListAdapterImplementation.setSelected(item3, true);
        Collection<Object> items = multipleChoiceListAdapterImplementation.getSelectedItems();
        assertEquals(2, items.size());
        Iterator<Object> iterator = items.iterator();
        assertEquals(item1, iterator.next());
        assertEquals(item3, iterator.next());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * all selected items, if the adapter is empty.
     */
    public final void testGetSelectedItemsWhenAdapterIsEmpty() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        Collection<Object> items = multipleChoiceListAdapterImplementation.getSelectedItems();
        assertTrue(items.isEmpty());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * all selected items, if no item is selected.
     */
    public final void testGetSelectedItemsWhenNoItemIsSelected() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        Collection<Object> items = multipleChoiceListAdapterImplementation.getSelectedItems();
        assertTrue(items.isEmpty());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * the indices of all unselected items.
     */
    public final void testGetUnselectedIndices() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        multipleChoiceListAdapterImplementation.addItem(item3);
        multipleChoiceListAdapterImplementation.setSelected(item2, true);
        Collection<Integer> indices =
                multipleChoiceListAdapterImplementation.getUnselectedIndices();
        assertEquals(2, indices.size());
        Iterator<Integer> iterator = indices.iterator();
        assertEquals(0, iterator.next().intValue());
        assertEquals(2, iterator.next().intValue());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * the indices of all unselected items, if the adapter is empty.
     */
    public final void testGetUnselectedIndicesWhenAdapterIsEmpty() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        Collection<Integer> indices =
                multipleChoiceListAdapterImplementation.getUnselectedIndices();
        assertTrue(indices.isEmpty());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * the indices of all unselected items.
     */
    public final void testGetUnselectedIndicesWhenNoItemIsUnselected() {
        Object item = new Object();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item);
        multipleChoiceListAdapterImplementation.setSelected(item, true);
        Collection<Integer> indices =
                multipleChoiceListAdapterImplementation.getUnselectedIndices();
        assertTrue(indices.isEmpty());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * all unselected items.
     */
    public final void testGetUnselectedItems() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        multipleChoiceListAdapterImplementation.addItem(item3);
        multipleChoiceListAdapterImplementation.setSelected(item2, true);
        Collection<Object> items = multipleChoiceListAdapterImplementation.getUnselectedItems();
        assertEquals(2, items.size());
        Iterator<Object> iterator = items.iterator();
        assertEquals(item1, iterator.next());
        assertEquals(item3, iterator.next());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * all unselected items, if the adapter is empty.
     */
    public final void testGetUnselectedItemsWhenAdapterIsEmpty() {
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        Collection<Object> items = multipleChoiceListAdapterImplementation.getUnselectedItems();
        assertTrue(items.isEmpty());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a collection, which contains
     * all unselected items.
     */
    public final void testGetUnselectedItemsWhenNoItemIsUnselected() {
        Object item = new Object();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item);
        multipleChoiceListAdapterImplementation.setSelected(item, true);
        Collection<Object> items = multipleChoiceListAdapterImplementation.getUnselectedItems();
        assertTrue(items.isEmpty());
    }

    /**
     * Tests the functionality of the method, which allows to set the selection of the item, which
     * belongs to a specific index.
     */
    @SuppressWarnings("unchecked")
    public final void testSetSelectedByIndex() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        multipleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        multipleChoiceListAdapterImplementation.addItem(item);
        dataSetObserver.reset();
        boolean changed = multipleChoiceListAdapterImplementation.setSelected(0, true);
        assertTrue(changed);
        assertTrue(multipleChoiceListAdapterImplementation.isSelected(0));
        verify(listSelectionListener, times(1))
                .onItemSelected(multipleChoiceListAdapterImplementation, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
        dataSetObserver.reset();
        changed = multipleChoiceListAdapterImplementation.setSelected(0, false);
        assertTrue(changed);
        assertFalse(multipleChoiceListAdapterImplementation.isSelected(0));
        verify(listSelectionListener, times(1))
                .onItemUnselected(multipleChoiceListAdapterImplementation, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the method, which allows to set the selection of the item, which
     * belongs to a specific index, if the item is disabled.
     */
    @SuppressWarnings("unchecked")
    public final void testSetSelectedByIndexWhenItemIsDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        multipleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        multipleChoiceListAdapterImplementation.addItem(item);
        multipleChoiceListAdapterImplementation.setEnabled(0, false);
        dataSetObserver.reset();
        boolean changed = multipleChoiceListAdapterImplementation.setSelected(0, true);
        assertFalse(changed);
        assertFalse(multipleChoiceListAdapterImplementation.isSelected(0));
        verify(listSelectionListener, times(0))
                .onItemSelected(multipleChoiceListAdapterImplementation, item, 0);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the method, which allows to set the selection of the item, which
     * belongs to a specific index, if the item is already selected.
     */
    public final void testSetSelectedByIndexWhenItemIsAlreadySelected() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        multipleChoiceListAdapterImplementation.addItem(item);
        multipleChoiceListAdapterImplementation.setSelected(0, true);
        dataSetObserver.reset();
        boolean changed = multipleChoiceListAdapterImplementation.setSelected(0, true);
        assertFalse(changed);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Ensures, that an {@link IndexOutOfBoundsException} is thrown by the method, which allows to
     * set the selection of the item, which belongs to a specific index, if the index is invalid.
     */
    public final void testSetSelectedByIndexThrowsExceptionWhenIndexIsInvalid() {
        try {
            MultipleChoiceListAdapterImplementation<Object>
                    multipleChoiceListAdapterImplementation =
                    new MultipleChoiceListAdapterImplementation<>(getContext(),
                            new SelectableListDecoratorImplementation());
            multipleChoiceListAdapterImplementation.setSelected(-1, true);
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to set the selection of a specific item.
     */
    @SuppressWarnings("unchecked")
    public final void testSetSelected() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        multipleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        multipleChoiceListAdapterImplementation.addItem(item);
        dataSetObserver.reset();
        boolean changed = multipleChoiceListAdapterImplementation.setSelected(item, true);
        assertTrue(changed);
        assertTrue(multipleChoiceListAdapterImplementation.isSelected(item));
        verify(listSelectionListener, times(1))
                .onItemSelected(multipleChoiceListAdapterImplementation, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
        dataSetObserver.reset();
        changed = multipleChoiceListAdapterImplementation.setSelected(item, false);
        assertTrue(changed);
        assertFalse(multipleChoiceListAdapterImplementation.isSelected(item));
        verify(listSelectionListener, times(1))
                .onItemUnselected(multipleChoiceListAdapterImplementation, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the method, which allows to set the selection of a specific item,
     * if the item is disabled.
     */
    @SuppressWarnings("unchecked")
    public final void testSetSelectedWhenItemIsDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        multipleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        multipleChoiceListAdapterImplementation.addItem(item);
        multipleChoiceListAdapterImplementation.setEnabled(item, false);
        dataSetObserver.reset();
        boolean changed = multipleChoiceListAdapterImplementation.setSelected(item, true);
        assertFalse(changed);
        assertFalse(multipleChoiceListAdapterImplementation.isSelected(item));
        verify(listSelectionListener, times(0))
                .onItemSelected(multipleChoiceListAdapterImplementation, item, 0);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the method, which allows to set the selection of a specific item,
     * if the item is already selected.
     */
    public final void testSetSelectedWhenItemIsAlreadySelected() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        multipleChoiceListAdapterImplementation.addItem(item);
        multipleChoiceListAdapterImplementation.setSelected(item, true);
        dataSetObserver.reset();
        boolean changed = multipleChoiceListAdapterImplementation.setSelected(item, true);
        assertFalse(changed);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Ensures, that an {@link NullPointerException} is thrown by the method, which allows to set
     * the selection a specific item, if the item is null.
     */
    public final void testSetSelectedThrowsExceptionWhenItemIsNull() {
        try {
            MultipleChoiceListAdapterImplementation<Object>
                    multipleChoiceListAdapterImplementation =
                    new MultipleChoiceListAdapterImplementation<>(getContext(),
                            new SelectableListDecoratorImplementation());
            multipleChoiceListAdapterImplementation.setSelected(null, true);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that an {@link NoSuchElementException} is thrown by the method, which allows to set
     * the selection of a specific item, if the item does not belong to the adapter.
     */
    public final void testSetSelectedThrowsExceptionWhenAdapterDoesNotContainItem() {
        try {
            MultipleChoiceListAdapterImplementation<Object>
                    multipleChoiceListAdapterImplementation =
                    new MultipleChoiceListAdapterImplementation<>(getContext(),
                            new SelectableListDecoratorImplementation());
            multipleChoiceListAdapterImplementation.setSelected(new Object(), true);
            Assert.fail();
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to trigger the selection of the item,
     * which belongs to a specific item, if the item is not selected.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerSelectionByIndexWhenItemIsNotSelected() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        multipleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        multipleChoiceListAdapterImplementation.addItem(item);
        dataSetObserver.reset();
        boolean changed = multipleChoiceListAdapterImplementation.triggerSelection(0);
        assertTrue(changed);
        assertTrue(multipleChoiceListAdapterImplementation.isSelected(0));
        verify(listSelectionListener, times(1))
                .onItemSelected(multipleChoiceListAdapterImplementation, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the method, which allows to trigger the selection of the item,
     * which belongs to a specific item, if the item is selected.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerSelectionByIndexWhenItemIsSelected() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        multipleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        multipleChoiceListAdapterImplementation.addItem(item);
        multipleChoiceListAdapterImplementation.setSelected(0, true);
        dataSetObserver.reset();
        boolean changed = multipleChoiceListAdapterImplementation.triggerSelection(0);
        assertTrue(changed);
        assertFalse(multipleChoiceListAdapterImplementation.isSelected(0));
        verify(listSelectionListener, times(1))
                .onItemUnselected(multipleChoiceListAdapterImplementation, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the method, which allows to trigger the selection of the item,
     * which belongs to a specific item, if the item is disabled.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerSelectionByIndexWhenItemIsDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        multipleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        multipleChoiceListAdapterImplementation.addItem(item);
        multipleChoiceListAdapterImplementation.setEnabled(0, false);
        dataSetObserver.reset();
        boolean changed = multipleChoiceListAdapterImplementation.triggerSelection(0);
        assertFalse(changed);
        assertFalse(multipleChoiceListAdapterImplementation.isSelected(0));
        verify(listSelectionListener, times(1))
                .onItemUnselected(multipleChoiceListAdapterImplementation, item, 0);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Ensures, that an {@link IndexOutOfBoundsException} is thrown by the method, which allows to
     * trigger the selection of the item, which belongs to a specific item, if the index is
     * invalid.
     */
    public final void testTriggerSelectionByIndexThrowsExceptionWhenIndexIsInvalid() {
        try {
            MultipleChoiceListAdapterImplementation<Object>
                    multipleChoiceListAdapterImplementation =
                    new MultipleChoiceListAdapterImplementation<>(getContext(),
                            new SelectableListDecoratorImplementation());
            multipleChoiceListAdapterImplementation.triggerSelection(-1);
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to trigger the selection of a specific
     * item, if the item is not selected.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerSelectionWhenItemIsNotSelected() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<Object>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        multipleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        multipleChoiceListAdapterImplementation.addItem(item);
        dataSetObserver.reset();
        boolean changed = multipleChoiceListAdapterImplementation.triggerSelection(item);
        assertTrue(changed);
        assertTrue(multipleChoiceListAdapterImplementation.isSelected(item));
        verify(listSelectionListener, times(1))
                .onItemSelected(multipleChoiceListAdapterImplementation, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the method, which allows to trigger the selection of a specific
     * item, if the item is selected.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerSelectionWhenItemIsSelected() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        multipleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        multipleChoiceListAdapterImplementation.addItem(item);
        multipleChoiceListAdapterImplementation.setSelected(item, true);
        dataSetObserver.reset();
        boolean changed = multipleChoiceListAdapterImplementation.triggerSelection(item);
        assertTrue(changed);
        assertFalse(multipleChoiceListAdapterImplementation.isSelected(item));
        verify(listSelectionListener, times(1))
                .onItemUnselected(multipleChoiceListAdapterImplementation, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the method, which allows to trigger the selection of a specific
     * item, if the item is disabled.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerSelectionWhenItemIsDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        multipleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        multipleChoiceListAdapterImplementation.addItem(item);
        multipleChoiceListAdapterImplementation.setEnabled(item, false);
        dataSetObserver.reset();
        boolean changed = multipleChoiceListAdapterImplementation.triggerSelection(item);
        assertFalse(changed);
        assertFalse(multipleChoiceListAdapterImplementation.isSelected(item));
        verify(listSelectionListener, times(0))
                .onItemSelected(multipleChoiceListAdapterImplementation, item, 0);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to trigger
     * the selection of a specific item, if the item is null.
     */
    public final void testTriggerSelectionThrowsExceptionWhenItemIsNull() {
        try {
            MultipleChoiceListAdapterImplementation<Object>
                    multipleChoiceListAdapterImplementation =
                    new MultipleChoiceListAdapterImplementation<>(getContext(),
                            new SelectableListDecoratorImplementation());
            multipleChoiceListAdapterImplementation.triggerSelection(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that a {@link NoSuchElementException} is thrown by the method, which allows to
     * trigger the selection of a specific item, if the item does not belong to the adapter.
     */
    public final void testTriggerSelectionThrowsExceptionWhenAdapterDoesNotContainItem() {
        try {
            MultipleChoiceListAdapterImplementation<Object>
                    multipleChoiceListAdapterImplementation =
                    new MultipleChoiceListAdapterImplementation<>(getContext(),
                            new SelectableListDecoratorImplementation());
            multipleChoiceListAdapterImplementation.triggerSelection(new Object());
            Assert.fail();
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to set the selections of all items.
     */
    @SuppressWarnings("unchecked")
    public final void testSetAllSelected() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        multipleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        dataSetObserver.reset();
        boolean changedAll = multipleChoiceListAdapterImplementation.setAllSelected(true);
        assertTrue(changedAll);
        verify(listSelectionListener, times(1))
                .onItemSelected(multipleChoiceListAdapterImplementation, item1, 0);
        verify(listSelectionListener, times(1))
                .onItemSelected(multipleChoiceListAdapterImplementation, item2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the method, which allows to set the selection of all items, if at
     * least one item is already selected.
     */
    @SuppressWarnings("unchecked")
    public final void testSetAllSelectedWhenItemIsAlreadySelected() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        multipleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        multipleChoiceListAdapterImplementation.setSelected(item2, true);
        dataSetObserver.reset();
        boolean changedAll = multipleChoiceListAdapterImplementation.setAllSelected(true);
        assertFalse(changedAll);
        verify(listSelectionListener, times(1))
                .onItemSelected(multipleChoiceListAdapterImplementation, item1, 0);
        verify(listSelectionListener, times(1))
                .onItemSelected(multipleChoiceListAdapterImplementation, item2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the method, which allows to set the selection of all items, if at
     * least one item is disabled.
     */
    @SuppressWarnings("unchecked")
    public final void testSetAllSelectedWhenItemIsDisabled() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        multipleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        multipleChoiceListAdapterImplementation.setEnabled(item2, false);
        dataSetObserver.reset();
        boolean changedAll = multipleChoiceListAdapterImplementation.setAllSelected(true);
        assertFalse(changedAll);
        verify(listSelectionListener, times(1))
                .onItemSelected(multipleChoiceListAdapterImplementation, item1, 0);
        verify(listSelectionListener, times(0))
                .onItemSelected(multipleChoiceListAdapterImplementation, item2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the method, which allows to trigger the selections of all items.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerAllSelections() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        multipleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        multipleChoiceListAdapterImplementation.setSelected(item2, true);
        dataSetObserver.reset();
        boolean changedAll = multipleChoiceListAdapterImplementation.triggerAllSelections();
        assertTrue(changedAll);
        verify(listSelectionListener, times(1))
                .onItemSelected(multipleChoiceListAdapterImplementation, item1, 0);
        verify(listSelectionListener, times(1))
                .onItemUnselected(multipleChoiceListAdapterImplementation, item2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the method, which allows to trigger the selections of all items,
     * if at least one item is disabled.
     */
    @SuppressWarnings("unchecked")
    public final void testTriggerAllSelectionsWhenItemIsDisabled() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        multipleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        multipleChoiceListAdapterImplementation.setEnabled(item2, false);
        dataSetObserver.reset();
        boolean changedAll = multipleChoiceListAdapterImplementation.triggerAllSelections();
        assertFalse(changedAll);
        verify(listSelectionListener, times(1))
                .onItemSelected(multipleChoiceListAdapterImplementation, item1, 0);
        verify(listSelectionListener, times(0))
                .onItemSelected(multipleChoiceListAdapterImplementation, item2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Ensures, that the selection is adapted, when an item becomes disabled.
     */
    @SuppressWarnings("unchecked")
    public final void testSelectionIsAdaptedWhenItemBecomesDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        multipleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        multipleChoiceListAdapterImplementation.addItem(item);
        multipleChoiceListAdapterImplementation.setSelected(item, true);
        dataSetObserver.reset();
        multipleChoiceListAdapterImplementation.setEnabled(item, false);
        assertFalse(multipleChoiceListAdapterImplementation.isSelected(item));
        verify(listSelectionListener, times(1))
                .onItemUnselected(multipleChoiceListAdapterImplementation, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the toString-method.
     */
    public final void testToString() {
        ArrayList<Item<Object>> items = new ArrayList<>();
        items.add(new Item<Object>(new Object()));
        LogLevel logLevel = LogLevel.ERROR;
        boolean allowDuplicates = true;
        boolean notifyOnChange = true;
        Set<ListAdapterItemClickListener<Object>> itemClickListeners = new LinkedHashSet<>();
        Set<ListAdapterItemLongClickListener<Object>> itemLongClickListeners =
                new LinkedHashSet<>();
        Set<ListAdapterListener<Object>> adapterListeners = new LinkedHashSet<>();
        Set<ListEnableStateListener<Object>> enableStateListeners = new LinkedHashSet<>();
        int numberOfItemStates = 2;
        boolean triggerItemStateOnClick = true;
        Set<ListItemStateListener<Object>> itemStateListeners = new LinkedHashSet<>();
        Set<ListSortingListener<Object>> sortingListeners = new LinkedHashSet<>();
        Set<ListFilterListener<Object>> filterListeners = new LinkedHashSet<>();
        LinkedHashSet<AppliedFilter<Object>> appliedFilters = new LinkedHashSet<>();
        boolean selectItemOnClick = true;
        Set<ListSelectionListener<Object>> selectionListeners = new LinkedHashSet<>();
        Bundle parameters = new Bundle();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(getContext(),
                        new SelectableListDecoratorImplementation(), logLevel, items,
                        allowDuplicates, notifyOnChange, itemClickListeners, itemLongClickListeners,
                        adapterListeners, enableStateListeners, numberOfItemStates,
                        triggerItemStateOnClick, itemStateListeners, sortingListeners,
                        filterListeners, appliedFilters, selectItemOnClick, selectionListeners);
        multipleChoiceListAdapterImplementation.setParameters(parameters);
        assertEquals(
                "MultipleChoiceListAdapter (" + items.size() + " items) [logLevel=" + logLevel +
                        ", parameters=" + parameters + ", notifyOnChange=" + notifyOnChange +
                        ", allowDuplicates=" + allowDuplicates + ", numberOfItemStates=" +
                        numberOfItemStates + ", triggerItemStateOnClick=" +
                        triggerItemStateOnClick + ", filtered=" + false + ", selectItemOnClick=" +
                        selectItemOnClick + "]",
                multipleChoiceListAdapterImplementation.toString());
    }

    /**
     * Tests the functionality of the clone-method.
     *
     * @throws CloneNotSupportedException
     *         The exception, which is thrown, if cloning is not supported
     */
    public final void testClone() throws CloneNotSupportedException {
        Context context = getContext();
        SelectableListDecorator<Object> decorator = new SelectableListDecoratorImplementation();
        ArrayList<Item<Object>> items = new ArrayList<>();
        boolean allowDuplicates = true;
        boolean notifyOnChange = true;
        Set<ListAdapterItemClickListener<Object>> itemClickListeners = new LinkedHashSet<>();
        Set<ListAdapterItemLongClickListener<Object>> itemLongClickListeners =
                new LinkedHashSet<>();
        Set<ListAdapterListener<Object>> adapterListeners = new LinkedHashSet<>();
        Set<ListEnableStateListener<Object>> enableStateListeners = new LinkedHashSet<>();
        int numberOfItemStates = 2;
        boolean triggerItemStateOnClick = true;
        Set<ListItemStateListener<Object>> itemStateListeners = new LinkedHashSet<>();
        Set<ListSortingListener<Object>> sortingListeners = new LinkedHashSet<>();
        Set<ListFilterListener<Object>> filterListeners = new LinkedHashSet<>();
        LinkedHashSet<AppliedFilter<Object>> appliedFilters = new LinkedHashSet<>();
        boolean selectItemOnClick = true;
        Set<ListSelectionListener<Object>> selectionListeners = new LinkedHashSet<>();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context, decorator, LogLevel.ALL,
                        items, allowDuplicates, notifyOnChange, itemClickListeners,
                        itemLongClickListeners, adapterListeners, enableStateListeners,
                        numberOfItemStates, triggerItemStateOnClick, itemStateListeners,
                        sortingListeners, filterListeners, appliedFilters, selectItemOnClick,
                        selectionListeners);
        MultipleChoiceListAdapterImplementation<Object>
                clonedMultipleChoiceListAdapterImplementation =
                multipleChoiceListAdapterImplementation.clone();
        assertEquals(allowDuplicates,
                clonedMultipleChoiceListAdapterImplementation.areDuplicatesAllowed());
        assertEquals(notifyOnChange,
                clonedMultipleChoiceListAdapterImplementation.isNotifiedOnChange());
        assertEquals(numberOfItemStates,
                clonedMultipleChoiceListAdapterImplementation.getNumberOfItemStates());
        assertEquals(triggerItemStateOnClick,
                clonedMultipleChoiceListAdapterImplementation.isItemStateTriggeredOnClick());
        assertEquals(selectItemOnClick,
                clonedMultipleChoiceListAdapterImplementation.isItemSelectedOnClick());
        assertEquals(selectionListeners,
                clonedMultipleChoiceListAdapterImplementation.getSelectionListeners());
    }

}