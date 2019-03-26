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

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests the functionality of the class {@link MultipleChoiceListAdapterImplementation}.
 *
 * @author Michael Rapp
 */
@RunWith(AndroidJUnit4.class)
public class MultipleChoiceListAdapterImplementationTest {

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

    @Test
    public final void testProtectedConstructor() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
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

    @Test
    public final void testPublicConstructor() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SelectableListDecorator<Object> decorator = new SelectableListDecoratorImplementation();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context, decorator);
        assertFalse(multipleChoiceListAdapterImplementation.areDuplicatesAllowed());
        assertTrue(multipleChoiceListAdapterImplementation.isNotifiedOnChange());
    }

    @Test
    public final void testGetFirstSelectedIndex() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.setSelected(0, true);
        multipleChoiceListAdapterImplementation.setSelected(1, true);
        assertEquals(0, multipleChoiceListAdapterImplementation.getFirstSelectedIndex());
    }

    @Test
    public final void testAdaptUnfilteredItemsWhenItemIsSelected() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @Test
    public final void testAdaptUnfilteredItemsWhenItemIsUnselected() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @Test
    public final void testGetFirstSelectedIndexWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        assertEquals(-1, multipleChoiceListAdapterImplementation.getFirstSelectedIndex());
    }

    @Test
    public final void testGetFirstSelectedIndexWhenNoItemIsSelected() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        assertEquals(-1, multipleChoiceListAdapterImplementation.getFirstSelectedIndex());
    }

    @Test
    public final void testGetFirstSelectedItem() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        multipleChoiceListAdapterImplementation.setSelected(item1, true);
        multipleChoiceListAdapterImplementation.setSelected(item2, true);
        assertEquals(item1, multipleChoiceListAdapterImplementation.getFirstSelectedItem());
    }

    @Test
    public final void testGetFirstSelectedItemWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        assertNull(multipleChoiceListAdapterImplementation.getFirstSelectedItem());
    }

    @Test
    public final void testGetFirstSelectedItemWhenNoItemIsSelected() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        assertNull(multipleChoiceListAdapterImplementation.getFirstSelectedItem());
    }

    @Test
    public final void testGetLastSelectedIndex() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.setSelected(0, true);
        multipleChoiceListAdapterImplementation.setSelected(1, true);
        assertEquals(1, multipleChoiceListAdapterImplementation.getLastSelectedIndex());
    }

    @Test
    public final void testGetLastSelectedIndexWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        assertEquals(-1, multipleChoiceListAdapterImplementation.getLastSelectedIndex());
    }

    @Test
    public final void testGetLastSelectedIndexWhenNoItemIsSelected() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        assertEquals(-1, multipleChoiceListAdapterImplementation.getLastSelectedIndex());
    }

    @Test
    public final void testGetLastSelectedItem() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        multipleChoiceListAdapterImplementation.setSelected(item1, true);
        multipleChoiceListAdapterImplementation.setSelected(item2, true);
        assertEquals(item2, multipleChoiceListAdapterImplementation.getLastSelectedItem());
    }

    @Test
    public final void testGetLastSelectedItemWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        assertNull(multipleChoiceListAdapterImplementation.getLastSelectedItem());
    }

    @Test
    public final void testGetLastSelectedItemWhenNoItemIsSelected() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        assertNull(multipleChoiceListAdapterImplementation.getLastSelectedItem());
    }

    @Test
    public final void testGetFirstUnselectedIndex() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        assertEquals(0, multipleChoiceListAdapterImplementation.getFirstUnselectedIndex());
    }

    @Test
    public final void testGetFirstUnselectedIndexWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        assertEquals(-1, multipleChoiceListAdapterImplementation.getFirstUnselectedIndex());
    }

    @Test
    public final void testGetFirstUnselectedIndexWhenNoItemIsUnselected() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.setSelected(0, true);
        multipleChoiceListAdapterImplementation.setSelected(1, true);
        assertEquals(-1, multipleChoiceListAdapterImplementation.getFirstUnselectedIndex());
    }

    @Test
    public final void testGetFirstUnselectedItem() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        assertEquals(item1, multipleChoiceListAdapterImplementation.getFirstUnselectedItem());
    }

    @Test
    public final void testGetFirstUnselectedItemWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        assertNull(multipleChoiceListAdapterImplementation.getFirstUnselectedItem());
    }

    @Test
    public final void testGetFirstUnselectedItemWhenNoItemIsUnselected() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        multipleChoiceListAdapterImplementation.setSelected(item1, true);
        multipleChoiceListAdapterImplementation.setSelected(item2, true);
        assertNull(multipleChoiceListAdapterImplementation.getFirstUnselectedItem());
    }

    @Test
    public final void testGetLastUnselectedIndex() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        assertEquals(1, multipleChoiceListAdapterImplementation.getLastUnselectedIndex());
    }

    @Test
    public final void testGetLastUnselectedIndexWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        assertEquals(-1, multipleChoiceListAdapterImplementation.getLastUnselectedIndex());
    }

    @Test
    public final void testGetLastUnselectedIndexWhenNoItemIsUnselected() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        multipleChoiceListAdapterImplementation.setSelected(0, true);
        multipleChoiceListAdapterImplementation.setSelected(1, true);
        assertEquals(-1, multipleChoiceListAdapterImplementation.getLastUnselectedIndex());
    }

    @Test
    public final void testGetLastUnselectedItem() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        assertEquals(item2, multipleChoiceListAdapterImplementation.getLastUnselectedItem());
    }

    @Test
    public final void testGetLastUnselectedItemWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        assertNull(multipleChoiceListAdapterImplementation.getLastUnselectedItem());
    }

    @Test
    public final void testGetLastUnselectedItemWhenNoItemIsUnselected() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item1);
        multipleChoiceListAdapterImplementation.addItem(item2);
        multipleChoiceListAdapterImplementation.setSelected(item1, true);
        multipleChoiceListAdapterImplementation.setSelected(item2, true);
        assertNull(multipleChoiceListAdapterImplementation.getLastUnselectedItem());
    }

    @Test
    public final void testGetSelectedIndices() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @Test
    public final void testGetSelectedIndicesWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        Collection<Integer> indices = multipleChoiceListAdapterImplementation.getSelectedIndices();
        assertTrue(indices.isEmpty());
    }

    @Test
    public final void testGetSelectedIndicesWhenNoItemIsSelected() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        Collection<Integer> indices = multipleChoiceListAdapterImplementation.getSelectedIndices();
        assertTrue(indices.isEmpty());
    }

    @Test
    public final void testGetSelectedItems() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @Test
    public final void testGetSelectedItemsWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        Collection<Object> items = multipleChoiceListAdapterImplementation.getSelectedItems();
        assertTrue(items.isEmpty());
    }

    @Test
    public final void testGetSelectedItemsWhenNoItemIsSelected() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(new Object());
        Collection<Object> items = multipleChoiceListAdapterImplementation.getSelectedItems();
        assertTrue(items.isEmpty());
    }

    @Test
    public final void testGetUnselectedIndices() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @Test
    public final void testGetUnselectedIndicesWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        Collection<Integer> indices =
                multipleChoiceListAdapterImplementation.getUnselectedIndices();
        assertTrue(indices.isEmpty());
    }

    @Test
    public final void testGetUnselectedIndicesWhenNoItemIsUnselected() {
        Object item = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item);
        multipleChoiceListAdapterImplementation.setSelected(item, true);
        Collection<Integer> indices =
                multipleChoiceListAdapterImplementation.getUnselectedIndices();
        assertTrue(indices.isEmpty());
    }

    @Test
    public final void testGetUnselectedItems() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @Test
    public final void testGetUnselectedItemsWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        Collection<Object> items = multipleChoiceListAdapterImplementation.getUnselectedItems();
        assertTrue(items.isEmpty());
    }

    @Test
    public final void testGetUnselectedItemsWhenNoItemIsUnselected() {
        Object item = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.addItem(item);
        multipleChoiceListAdapterImplementation.setSelected(item, true);
        Collection<Object> items = multipleChoiceListAdapterImplementation.getUnselectedItems();
        assertTrue(items.isEmpty());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetSelectedByIndex() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetSelectedByIndexWhenItemIsDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @Test
    public final void testSetSelectedByIndexWhenItemIsAlreadySelected() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        multipleChoiceListAdapterImplementation.addItem(item);
        multipleChoiceListAdapterImplementation.setSelected(0, true);
        dataSetObserver.reset();
        boolean changed = multipleChoiceListAdapterImplementation.setSelected(0, true);
        assertFalse(changed);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testSetSelectedByIndexThrowsExceptionWhenIndexIsInvalid() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.setSelected(-1, true);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetSelected() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetSelectedWhenItemIsDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @Test
    public final void testSetSelectedWhenItemIsAlreadySelected() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        multipleChoiceListAdapterImplementation.addItem(item);
        multipleChoiceListAdapterImplementation.setSelected(item, true);
        dataSetObserver.reset();
        boolean changed = multipleChoiceListAdapterImplementation.setSelected(item, true);
        assertFalse(changed);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testSetSelectedThrowsExceptionWhenItemIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.setSelected(null, true);
    }

    @Test(expected = NoSuchElementException.class)
    public final void testSetSelectedThrowsExceptionWhenAdapterDoesNotContainItem() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.setSelected(new Object(), true);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerSelectionByIndexWhenItemIsNotSelected() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerSelectionByIndexWhenItemIsSelected() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerSelectionByIndexWhenItemIsDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testTriggerSelectionByIndexThrowsExceptionWhenIndexIsInvalid() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.triggerSelection(-1);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerSelectionWhenItemIsNotSelected() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerSelectionWhenItemIsSelected() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerSelectionWhenItemIsDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @Test(expected = IllegalArgumentException.class)
    public final void testTriggerSelectionThrowsExceptionWhenItemIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.triggerSelection(null);
    }

    @Test(expected = NoSuchElementException.class)
    public final void testTriggerSelectionThrowsExceptionWhenAdapterDoesNotContainItem() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        multipleChoiceListAdapterImplementation.triggerSelection(new Object());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetAllSelected() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetAllSelectedWhenItemIsAlreadySelected() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetAllSelectedWhenItemIsDisabled() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerAllSelections() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @SuppressWarnings("unchecked")
    @Test
    public final void testTriggerAllSelectionsWhenItemIsDisabled() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @SuppressWarnings("unchecked")
    @Test
    public final void testSelectionIsAdaptedWhenItemBecomesDisabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @Test
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
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapterImplementation<Object> multipleChoiceListAdapterImplementation =
                new MultipleChoiceListAdapterImplementation<>(context,
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

    @Test
    public final void testClone() throws CloneNotSupportedException {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
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