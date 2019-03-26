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
 * Tests the functionality of the class {@link SingleChoiceListAdapterImplementation}.
 *
 * @author Michael Rapp
 */
@RunWith(AndroidJUnit4.class)
public class SingleChoiceListAdapterImplementationTest {

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
        boolean adaptSelectionAutomatically = false;
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context, decorator, LogLevel.ALL, items,
                        allowDuplicates, notifyOnChange, itemClickListeners, itemLongClickListeners,
                        adapterListeners, enableStateListeners, numberOfItemStates,
                        triggerItemStateOnClick, itemStateListeners, sortingListeners,
                        filterListeners, appliedFilters, selectItemOnClick, selectionListeners,
                        adaptSelectionAutomatically);
        assertEquals(allowDuplicates, singleChoiceListAdapterImplementation.areDuplicatesAllowed());
        assertEquals(notifyOnChange, singleChoiceListAdapterImplementation.isNotifiedOnChange());
        assertEquals(numberOfItemStates,
                singleChoiceListAdapterImplementation.getNumberOfItemStates());
        assertEquals(triggerItemStateOnClick,
                singleChoiceListAdapterImplementation.isItemStateTriggeredOnClick());
        assertEquals(selectItemOnClick,
                singleChoiceListAdapterImplementation.isItemSelectedOnClick());
        assertEquals(selectionListeners,
                singleChoiceListAdapterImplementation.getSelectionListeners());
        assertEquals(adaptSelectionAutomatically,
                singleChoiceListAdapterImplementation.isSelectionAdaptedAutomatically());
    }

    @Test
    public final void testPublicConstructor() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SelectableListDecorator<Object> decorator = new SelectableListDecoratorImplementation();
        new SingleChoiceListAdapterImplementation<>(context, decorator);
    }

    @Test
    public final void testGetSelectedIndex() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Object item1 = new Object();
        Object item2 = new Object();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.triggerSelection(item2);
        assertEquals(1, singleChoiceListAdapterImplementation.getSelectedIndex());
    }

    @Test
    public final void testGetSelectedIndexWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        assertEquals(-1, singleChoiceListAdapterImplementation.getSelectedIndex());
    }

    @Test
    public final void testGetSelectedIndexWhenAllItemsAreDisabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Object item = new Object();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.addItem(item);
        singleChoiceListAdapterImplementation.setEnabled(item, false);
        assertEquals(-1, singleChoiceListAdapterImplementation.getSelectedIndex());
    }

    @Test
    public final void testGetSelectedItem() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Object item1 = new Object();
        Object item2 = new Object();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.triggerSelection(item2);
        assertEquals(item2, singleChoiceListAdapterImplementation.getSelectedItem());
    }

    @Test
    public final void testGetSelectedItemWhenAdapterIsEmpty() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        assertNull(singleChoiceListAdapterImplementation.getSelectedItem());
    }

    @Test
    public final void testGetSelectedWhenNoItemIsEnabled() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Object item = new Object();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.addItem(item);
        singleChoiceListAdapterImplementation.setEnabled(item, false);
        assertNull(singleChoiceListAdapterImplementation.getSelectedItem());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSelectByIndex() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        dataSetObserver.reset();
        boolean selected = singleChoiceListAdapterImplementation.triggerSelection(1);
        assertTrue(selected);
        assertEquals(1, singleChoiceListAdapterImplementation.getSelectedIndex());
        verify(listSelectionListener, times(1))
                .onItemUnselected(singleChoiceListAdapterImplementation, item1, 0);
        verify(listSelectionListener, times(1))
                .onItemSelected(singleChoiceListAdapterImplementation, item2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSelectByIndexWhenItemIsAlreadySelected() {
        Object item1 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item1);
        dataSetObserver.reset();
        boolean result = singleChoiceListAdapterImplementation.triggerSelection(0);
        assertTrue(result);
        assertEquals(-1, singleChoiceListAdapterImplementation.getSelectedIndex());
        verify(listSelectionListener, times(1))
                .onItemUnselected(singleChoiceListAdapterImplementation, item1, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSelectByIndexWhenItemIsDisabled() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.setEnabled(item2, false);
        dataSetObserver.reset();
        boolean selected = singleChoiceListAdapterImplementation.triggerSelection(1);
        assertFalse(selected);
        assertEquals(0, singleChoiceListAdapterImplementation.getSelectedIndex());
        verify(listSelectionListener, times(0))
                .onItemUnselected(singleChoiceListAdapterImplementation, item1, 0);
        verify(listSelectionListener, times(0))
                .onItemSelected(singleChoiceListAdapterImplementation, item2, 1);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testSelectByIndexThrowsExceptionWhenIndexIsInvalid() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.triggerSelection(-1);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSelect() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        dataSetObserver.reset();
        boolean selected = singleChoiceListAdapterImplementation.triggerSelection(item2);
        assertTrue(selected);
        assertEquals(item2, singleChoiceListAdapterImplementation.getSelectedItem());
        verify(listSelectionListener, times(1))
                .onItemUnselected(singleChoiceListAdapterImplementation, item1, 0);
        verify(listSelectionListener, times(1))
                .onItemSelected(singleChoiceListAdapterImplementation, item2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSelectWhenItemIsAlreadySelected() {
        Object item1 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item1);
        dataSetObserver.reset();
        boolean result = singleChoiceListAdapterImplementation.triggerSelection(item1);
        assertTrue(result);
        assertEquals(-1, singleChoiceListAdapterImplementation.getSelectedIndex());
        verify(listSelectionListener, times(1))
                .onItemUnselected(singleChoiceListAdapterImplementation, item1, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSelectWhenItemIsDisabled() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.setEnabled(item2, false);
        dataSetObserver.reset();
        boolean selected = singleChoiceListAdapterImplementation.triggerSelection(item2);
        assertFalse(selected);
        assertEquals(item1, singleChoiceListAdapterImplementation.getSelectedItem());
        verify(listSelectionListener, times(0))
                .onItemUnselected(singleChoiceListAdapterImplementation, item1, 0);
        verify(listSelectionListener, times(0))
                .onItemSelected(singleChoiceListAdapterImplementation, item2, 1);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testSelectThrowsExceptionWhenItemIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.triggerSelection(null);
    }

    @Test(expected = NoSuchElementException.class)
    public final void testSelectThrowsExceptionWhenAdapterDoesNotContainItem() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.triggerSelection(new Object());
    }

    @Test
    public final void testAdaptSelectionAutomatically() {
        boolean adaptSelectionAutomatically = false;
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation
                .adaptSelectionAutomatically(adaptSelectionAutomatically);
        assertEquals(adaptSelectionAutomatically,
                singleChoiceListAdapterImplementation.isSelectionAdaptedAutomatically());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSelectionIsAdaptedWhenItemIsAddedToEmptyAdapter() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item);
        assertEquals(item, singleChoiceListAdapterImplementation.getSelectedItem());
        verify(listSelectionListener, times(1))
                .onItemSelected(singleChoiceListAdapterImplementation, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSelectionIsAdaptedByAscendingIndexWhenSelectedItemIsRemoved() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.addItem(item3);
        singleChoiceListAdapterImplementation.setEnabled(item2, false);
        dataSetObserver.reset();
        singleChoiceListAdapterImplementation.removeItem(item1);
        assertEquals(item3, singleChoiceListAdapterImplementation.getSelectedItem());
        verify(listSelectionListener, times(1))
                .onItemSelected(singleChoiceListAdapterImplementation, item3, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSelectionIsAdaptedByDescendingIndexWhenSelectedItemIsRemoved() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.addItem(item3);
        singleChoiceListAdapterImplementation.setEnabled(item2, false);
        singleChoiceListAdapterImplementation.triggerSelection(item3);
        dataSetObserver.reset();
        singleChoiceListAdapterImplementation.removeItem(item3);
        assertEquals(item1, singleChoiceListAdapterImplementation.getSelectedItem());
        verify(listSelectionListener, times(2))
                .onItemSelected(singleChoiceListAdapterImplementation, item1, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test
    public final void testSelectionIsAdaptedWhenSelectedItemIsRemovedAndNoOtherItemExists() {
        Object item = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.addItem(item);
        singleChoiceListAdapterImplementation.removeItem(item);
        assertNull(singleChoiceListAdapterImplementation.getSelectedItem());
    }

    @Test
    public final void testSelectionIsAdaptedWhenSelectedItemIsRemovedAndAllOtherItemsAreDisabled() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.addItem(item3);
        singleChoiceListAdapterImplementation.setEnabled(item1, false);
        singleChoiceListAdapterImplementation.setEnabled(item3, false);
        singleChoiceListAdapterImplementation.removeItem(item2);
        assertNull(singleChoiceListAdapterImplementation.getSelectedItem());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSelectionIsAdaptedWhenOnlyItemBecomesEnabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item);
        singleChoiceListAdapterImplementation.setEnabled(item, false);
        assertNull(singleChoiceListAdapterImplementation.getSelectedItem());
        dataSetObserver.reset();
        singleChoiceListAdapterImplementation.setEnabled(item, true);
        assertEquals(item, singleChoiceListAdapterImplementation.getSelectedItem());
        verify(listSelectionListener, times(2))
                .onItemSelected(singleChoiceListAdapterImplementation, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSelectionIsAdaptedByAscendingIndexWhenSelectedItemBecomesDisabled() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.addItem(item3);
        singleChoiceListAdapterImplementation.setEnabled(item2, false);
        dataSetObserver.reset();
        singleChoiceListAdapterImplementation.setEnabled(item1, false);
        assertEquals(item3, singleChoiceListAdapterImplementation.getSelectedItem());
        verify(listSelectionListener, times(1))
                .onItemUnselected(singleChoiceListAdapterImplementation, item1, 0);
        verify(listSelectionListener, times(1))
                .onItemSelected(singleChoiceListAdapterImplementation, item3, 2);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSelectionIsAdaptedByDescendingIndexWhenSelectedItemBecomesDisabled() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.addItem(item3);
        singleChoiceListAdapterImplementation.setEnabled(item2, false);
        singleChoiceListAdapterImplementation.triggerSelection(item3);
        dataSetObserver.reset();
        singleChoiceListAdapterImplementation.setEnabled(item3, false);
        assertEquals(item1, singleChoiceListAdapterImplementation.getSelectedItem());
        verify(listSelectionListener, times(1))
                .onItemUnselected(singleChoiceListAdapterImplementation, item3, 2);
        verify(listSelectionListener, times(2))
                .onItemSelected(singleChoiceListAdapterImplementation, item1, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSelectionIsAdaptedWhenSelectedItemBecomesDisabledAndNoOtherItemExists() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item);
        dataSetObserver.reset();
        singleChoiceListAdapterImplementation.setEnabled(item, false);
        assertNull(singleChoiceListAdapterImplementation.getSelectedItem());
        verify(listSelectionListener, times(1))
                .onItemUnselected(singleChoiceListAdapterImplementation, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSelectionIsAdaptedWhenSelectedItemBecomesDisabledAndAllOtherItemsAreDisabled() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.addItem(item3);
        singleChoiceListAdapterImplementation.setEnabled(item1, false);
        singleChoiceListAdapterImplementation.setEnabled(item3, false);
        singleChoiceListAdapterImplementation.setEnabled(item2, false);
        assertNull(singleChoiceListAdapterImplementation.getSelectedItem());
        verify(listSelectionListener, times(1))
                .onItemUnselected(singleChoiceListAdapterImplementation, item2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    @Test
    public final void testAdaptUnfilteredItemsWhenItemIsSelected() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        FilterableImplementation item1 = new FilterableImplementation("cdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcquerystringdef");
        FilterableImplementation item3 = new FilterableImplementation("xsbiquerystringdjwi");
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.addItem(item3);
        singleChoiceListAdapterImplementation.applyFilter("querystring", 0);
        singleChoiceListAdapterImplementation.triggerSelection(item3);
        assertEquals(item3, singleChoiceListAdapterImplementation.getSelectedItem());
        singleChoiceListAdapterImplementation.resetAllFilters();
        assertEquals(item3, singleChoiceListAdapterImplementation.getSelectedItem());
    }

    @Test
    public final void testAdaptUnfilteredItemsWhenDuplicateItemIsSelected() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.allowDuplicates(true);
        FilterableImplementation item1 = new FilterableImplementation("cdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcquerystringdef");
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.applyFilter("querystring", 0);
        singleChoiceListAdapterImplementation.triggerSelection(1);
        assertEquals(1, singleChoiceListAdapterImplementation.getSelectedIndex());
        singleChoiceListAdapterImplementation.resetAllFilters();
        assertFalse(singleChoiceListAdapterImplementation.isSelected(0));
        assertFalse(singleChoiceListAdapterImplementation.isSelected(1));
        assertTrue(singleChoiceListAdapterImplementation.isSelected(2));
    }

    @Test
    public final void testAdaptSelectionWhenSelectedItemIsFiltered() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.allowDuplicates(true);
        FilterableImplementation item1 = new FilterableImplementation("cdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcquerystringdef");
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.applyFilter("querystring", 0);
        assertEquals(item2, singleChoiceListAdapterImplementation.getSelectedItem());
        singleChoiceListAdapterImplementation.resetAllFilters();
        assertFalse(singleChoiceListAdapterImplementation.isSelected(0));
        assertTrue(singleChoiceListAdapterImplementation.isSelected(1));
    }

    @Test
    public final void testAdaptSelectionWhenAllItemsAreFiltered() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.allowDuplicates(true);
        FilterableImplementation item1 = new FilterableImplementation("cdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcdef");
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.triggerSelection(item2);
        singleChoiceListAdapterImplementation.applyFilter("querystring", 0);
        assertNull(singleChoiceListAdapterImplementation.getSelectedItem());
        singleChoiceListAdapterImplementation.resetAllFilters();
        assertTrue(singleChoiceListAdapterImplementation.isSelected(0));
        assertFalse(singleChoiceListAdapterImplementation.isSelected(1));
    }

    @Test
    public final void testToString() {
        ArrayList<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        LogLevel logLevel = LogLevel.ERROR;
        boolean allowDuplicates = true;
        boolean notifyOnchange = true;
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
        boolean adaptSelectionAutomatically = false;
        Bundle parameters = new Bundle();
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context,
                        new SelectableListDecoratorImplementation(), logLevel, items,
                        allowDuplicates, notifyOnchange, itemClickListeners, itemLongClickListeners,
                        adapterListeners, enableStateListeners, numberOfItemStates,
                        triggerItemStateOnClick, itemStateListeners, sortingListeners,
                        filterListeners, appliedFilters, selectItemOnClick, selectionListeners,
                        adaptSelectionAutomatically);
        singleChoiceListAdapterImplementation.setParameters(parameters);
        assertEquals("SingleChoiceListAdapter (" + items.size() + " items) [logLevel=" + logLevel +
                        ", parameters=" + parameters + ", notifyOnChange=" + notifyOnchange +
                        ", allowDuplicates=" + allowDuplicates + ", numberOfItemStates=" +
                        numberOfItemStates + ", triggerItemStateOnClick=" + triggerItemStateOnClick +
                        ", filtered=" + false + ", selectItemOnClick=" + selectItemOnClick +
                        ", adaptSelectionAutomatically=" + adaptSelectionAutomatically + "]",
                singleChoiceListAdapterImplementation.toString());
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
        boolean selectItemOnClick = true;
        Set<ListSelectionListener<Object>> selectionListeners = new LinkedHashSet<>();
        boolean adaptSelectionAutomatically = false;
        Set<ListFilterListener<Object>> filterListeners = new LinkedHashSet<>();
        LinkedHashSet<AppliedFilter<Object>> appliedFilters = new LinkedHashSet<>();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context, decorator, LogLevel.ALL, items,
                        allowDuplicates, notifyOnChange, itemClickListeners, itemLongClickListeners,
                        adapterListeners, enableStateListeners, numberOfItemStates,
                        triggerItemStateOnClick, itemStateListeners, sortingListeners,
                        filterListeners, appliedFilters, selectItemOnClick, selectionListeners,
                        adaptSelectionAutomatically);
        SingleChoiceListAdapterImplementation<Object> clonedSingleChoiceListAdapterImplementation =
                singleChoiceListAdapterImplementation.clone();
        assertEquals(allowDuplicates,
                clonedSingleChoiceListAdapterImplementation.areDuplicatesAllowed());
        assertEquals(notifyOnChange,
                clonedSingleChoiceListAdapterImplementation.isNotifiedOnChange());
        assertEquals(numberOfItemStates,
                clonedSingleChoiceListAdapterImplementation.getNumberOfItemStates());
        assertEquals(triggerItemStateOnClick,
                clonedSingleChoiceListAdapterImplementation.isItemStateTriggeredOnClick());
        assertEquals(selectItemOnClick,
                clonedSingleChoiceListAdapterImplementation.isItemSelectedOnClick());
        assertEquals(selectionListeners,
                clonedSingleChoiceListAdapterImplementation.getSelectionListeners());
        assertEquals(adaptSelectionAutomatically,
                clonedSingleChoiceListAdapterImplementation.isSelectionAdaptedAutomatically());
    }

}