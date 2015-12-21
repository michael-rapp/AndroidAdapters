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
package de.mrapp.android.adapter.list.selectable;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.test.AndroidTestCase;
import android.view.View;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import de.mrapp.android.adapter.DataSetObserver;
import de.mrapp.android.adapter.Filterable;
import de.mrapp.android.adapter.R;
import de.mrapp.android.adapter.SelectableListDecorator;
import de.mrapp.android.adapter.datastructure.AppliedFilter;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.inflater.InflaterFactory;
import de.mrapp.android.adapter.list.ListAdapterItemClickListener;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.adapter.logging.LogLevel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests the functionality of the class {@link SingleChoiceListAdapterImplementation}.
 *
 * @author Michael Rapp
 */
public class SingleChoiceListAdapterImplementationTest extends AndroidTestCase {

    /**
     * An implementation of the abstract class {@link SelectableListDecorator}, which is needed for
     * test purposes.
     */
    private class SelectableListDecoratorImplementation extends SelectableListDecorator<Object> {

        @Override
        protected void onShowItem(@NonNull final Context context,
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
        Inflater inflater = mock(Inflater.class);
        SelectableListDecorator<Object> decorator = new SelectableListDecoratorImplementation();
        ArrayList<Item<Object>> items = new ArrayList<>();
        boolean allowDuplicates = true;
        boolean notifyOnChange = true;
        Set<ListAdapterItemClickListener<Object>> itemClickListeners = new LinkedHashSet<>();
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
                new SingleChoiceListAdapterImplementation<>(context, inflater, decorator,
                        LogLevel.ALL, items, allowDuplicates, notifyOnChange, itemClickListeners,
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

    /**
     * Tests, if all properties are set correctly by the public constructor.
     */
    public final void testPublicConstructor() {
        Context context = getContext();
        Inflater inflater = mock(Inflater.class);
        SelectableListDecorator<Object> decorator = new SelectableListDecoratorImplementation();
        new SingleChoiceListAdapterImplementation<>(context, inflater, decorator);
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the currently
     * selected item.
     */
    public final void testGetSelectedIndex() {
        Object item1 = new Object();
        Object item2 = new Object();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.select(item2);
        assertEquals(1, singleChoiceListAdapterImplementation.getSelectedIndex());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the currently
     * selected item, if the adapter is empty.
     */
    public final void testGetSelectedIndexWhenAdapterIsEmpty() {
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        assertEquals(-1, singleChoiceListAdapterImplementation.getSelectedIndex());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of the currently
     * selected item, if all items are disabled.
     */
    public final void testGetSelectedIndexWhenAllItemsAreDisabled() {
        Object item = new Object();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.addItem(item);
        singleChoiceListAdapterImplementation.setEnabled(item, false);
        assertEquals(-1, singleChoiceListAdapterImplementation.getSelectedIndex());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the currently selected item.
     */
    public final void testGetSelectedItem() {
        Object item1 = new Object();
        Object item2 = new Object();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.select(item2);
        assertEquals(item2, singleChoiceListAdapterImplementation.getSelectedItem());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the currently selected item,
     * if the adapter is empty.
     */
    public final void testGetSelectedItemWhenAdapterIsEmpty() {
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        assertNull(singleChoiceListAdapterImplementation.getSelectedItem());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the currently selected item,
     * if no item is enabled.
     */
    public final void testGetSelectedWhenNoItemIsEnabled() {
        Object item = new Object();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.addItem(item);
        singleChoiceListAdapterImplementation.setEnabled(item, false);
        assertNull(singleChoiceListAdapterImplementation.getSelectedItem());
    }

    /**
     * Tests the functionality of the method, which allows to select the item, which belongs to a
     * specific index.
     */
    @SuppressWarnings("unchecked")
    public final void testSelectByIndex() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        dataSetObserver.reset();
        boolean selected = singleChoiceListAdapterImplementation.select(1);
        assertTrue(selected);
        assertEquals(1, singleChoiceListAdapterImplementation.getSelectedIndex());
        verify(listSelectionListener, times(1))
                .onItemUnselected(singleChoiceListAdapterImplementation, item1, 0);
        verify(listSelectionListener, times(1))
                .onItemSelected(singleChoiceListAdapterImplementation, item2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the method, which allows to select the item, which belongs to a
     * specific index, if the item is already selected.
     */
    @SuppressWarnings("unchecked")
    public final void testSelectByIndexWhenItemIsAlreadySelected() {
        Object item1 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item1);
        dataSetObserver.reset();
        boolean selected = singleChoiceListAdapterImplementation.select(0);
        assertFalse(selected);
        assertEquals(0, singleChoiceListAdapterImplementation.getSelectedIndex());
        verify(listSelectionListener, times(1))
                .onItemSelected(singleChoiceListAdapterImplementation, item1, 0);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the method, which allows to select the item, which belongs to a
     * specific index, if the item is disabled.
     */
    @SuppressWarnings("unchecked")
    public final void testSelectByIndexWhenItemIsDisabled() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.setEnabled(item2, false);
        dataSetObserver.reset();
        boolean selected = singleChoiceListAdapterImplementation.select(1);
        assertFalse(selected);
        assertEquals(0, singleChoiceListAdapterImplementation.getSelectedIndex());
        verify(listSelectionListener, times(0))
                .onItemUnselected(singleChoiceListAdapterImplementation, item1, 0);
        verify(listSelectionListener, times(0))
                .onItemSelected(singleChoiceListAdapterImplementation, item2, 1);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Ensures, that an {@link IndexOutOfBoundsException} is thrown by the method, which allows to
     * select the item, which belongs to a specific index, if the index is invalid.
     */
    public final void testSelectByIndexThrowsExceptionWhenIndexIsInvalid() {
        try {
            SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                    new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                            new SelectableListDecoratorImplementation());
            singleChoiceListAdapterImplementation.select(-1);
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to select a specific item.
     */
    @SuppressWarnings("unchecked")
    public final void testSelect() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        dataSetObserver.reset();
        boolean selected = singleChoiceListAdapterImplementation.select(item2);
        assertTrue(selected);
        assertEquals(item2, singleChoiceListAdapterImplementation.getSelectedItem());
        verify(listSelectionListener, times(1))
                .onItemUnselected(singleChoiceListAdapterImplementation, item1, 0);
        verify(listSelectionListener, times(1))
                .onItemSelected(singleChoiceListAdapterImplementation, item2, 1);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the method, which allows to select a specific item, if the item is
     * already selected.
     */
    @SuppressWarnings("unchecked")
    public final void testSelectWhenItemIsAlreadySelected() {
        Object item1 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item1);
        dataSetObserver.reset();
        boolean selected = singleChoiceListAdapterImplementation.select(item1);
        assertFalse(selected);
        assertEquals(0, singleChoiceListAdapterImplementation.getSelectedIndex());
        verify(listSelectionListener, times(1))
                .onItemSelected(singleChoiceListAdapterImplementation, item1, 0);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Tests the functionality of the method, which allows to select a specific item, if the item is
     * disabled.
     */
    @SuppressWarnings("unchecked")
    public final void testSelectWhenItemIsDisabled() {
        Object item1 = new Object();
        Object item2 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.setEnabled(item2, false);
        dataSetObserver.reset();
        boolean selected = singleChoiceListAdapterImplementation.select(item2);
        assertFalse(selected);
        assertEquals(item1, singleChoiceListAdapterImplementation.getSelectedItem());
        verify(listSelectionListener, times(0))
                .onItemUnselected(singleChoiceListAdapterImplementation, item1, 0);
        verify(listSelectionListener, times(0))
                .onItemSelected(singleChoiceListAdapterImplementation, item2, 1);
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the method, which allows to select
     * a specific item, if the item is null.
     */
    public final void testSelectThrowsExceptionWhenItemIsNull() {
        try {
            SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                    new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                            new SelectableListDecoratorImplementation());
            singleChoiceListAdapterImplementation.select(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that a {@link NoSuchElementException} is thrown by the method, which allows to
     * select a specific item, if the item does not belong to the adapter.
     */
    public final void testSelectThrowsExceptionWhenAdapterDoesNotContainItem() {
        try {
            SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                    new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                            new SelectableListDecoratorImplementation());
            singleChoiceListAdapterImplementation.select(new Object());
            Assert.fail();
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to set, whether the adapter's selection
     * should be adapted automatically, or not.
     */
    public final void testAdaptSelectionAutomatically() {
        boolean adaptSelectionAutomatically = false;
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation
                .adaptSelectionAutomatically(adaptSelectionAutomatically);
        assertEquals(adaptSelectionAutomatically,
                singleChoiceListAdapterImplementation.isSelectionAdaptedAutomatically());
    }

    /**
     * Ensures, that the selection is adapted, when an item is added to an empty adapter.
     */
    @SuppressWarnings("unchecked")
    public final void testSelectionIsAdaptedWhenItemIsAddedToEmptyAdapter() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item);
        assertEquals(item, singleChoiceListAdapterImplementation.getSelectedItem());
        verify(listSelectionListener, times(1))
                .onItemSelected(singleChoiceListAdapterImplementation, item, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Ensures, that the selection is adapted correctly by ascending index, when the currently
     * selected item is removed from the adapter.
     */
    @SuppressWarnings("unchecked")
    public final void testSelectionIsAdaptedByAscendingIndexWhenSelectedItemIsRemoved() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
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

    /**
     * Ensures, that the selection is adapted correctly by descending index, when the currently
     * selected item is removed from the adapter.
     */
    @SuppressWarnings("unchecked")
    public final void testSelectionIsAdaptedByDescendingIndexWhenSelectedItemIsRemoved() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.addItem(item3);
        singleChoiceListAdapterImplementation.setEnabled(item2, false);
        singleChoiceListAdapterImplementation.select(item3);
        dataSetObserver.reset();
        singleChoiceListAdapterImplementation.removeItem(item3);
        assertEquals(item1, singleChoiceListAdapterImplementation.getSelectedItem());
        verify(listSelectionListener, times(2))
                .onItemSelected(singleChoiceListAdapterImplementation, item1, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Ensures, that the selection is adapted correctly, when the currently selected item is removed
     * from the adapter and the adapter does not contain an other item.
     */
    public final void testSelectionIsAdaptedWhenSelectedItemIsRemovedAndNoOtherItemExists() {
        Object item = new Object();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.addItem(item);
        singleChoiceListAdapterImplementation.removeItem(item);
        assertNull(singleChoiceListAdapterImplementation.getSelectedItem());
    }

    /**
     * Ensures, that the selection is adapted correctly, when the currently selected item is removed
     * from the adapter and the adapter does not contain an other item, which is enabled.
     */
    public final void testSelectionIsAdaptedWhenSelectedItemIsRemovedAndAllOtherItemsAreDisabled() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.addItem(item3);
        singleChoiceListAdapterImplementation.setEnabled(item1, false);
        singleChoiceListAdapterImplementation.setEnabled(item3, false);
        singleChoiceListAdapterImplementation.removeItem(item2);
        assertNull(singleChoiceListAdapterImplementation.getSelectedItem());
    }

    /**
     * Ensures, that the selection is adapted, when the only item, the adapter contains, becomes
     * enabled.
     */
    @SuppressWarnings("unchecked")
    public final void testSelectionIsAdaptedWhenOnlyItemBecomesEnabled() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
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

    /**
     * Ensures, that the selection is adapted correctly by ascending index, when the currently
     * selected item becomes disabled.
     */
    @SuppressWarnings("unchecked")
    public final void testSelectionIsAdaptedByAscendingIndexWhenSelectedItemBecomesDisabled() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
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

    /**
     * Ensures, that the selection is adapted correctly by descending index, when the currently
     * selected item becomes disabled.
     */
    @SuppressWarnings("unchecked")
    public final void testSelectionIsAdaptedByDescendingIndexWhenSelectedItemBecomesDisabled() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.addItem(item3);
        singleChoiceListAdapterImplementation.setEnabled(item2, false);
        singleChoiceListAdapterImplementation.select(item3);
        dataSetObserver.reset();
        singleChoiceListAdapterImplementation.setEnabled(item3, false);
        assertEquals(item1, singleChoiceListAdapterImplementation.getSelectedItem());
        verify(listSelectionListener, times(1))
                .onItemUnselected(singleChoiceListAdapterImplementation, item3, 2);
        verify(listSelectionListener, times(2))
                .onItemSelected(singleChoiceListAdapterImplementation, item1, 0);
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
    }

    /**
     * Ensures, that the selection is adapted correctly, when the currently selected item becomes
     * disabled and the adapter does not contain an other item.
     */
    @SuppressWarnings("unchecked")
    public final void testSelectionIsAdaptedWhenSelectedItemBecomesDisabledAndNoOtherItemExists() {
        Object item = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
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

    /**
     * Ensures, that the selection is adapted correctly, when the currently selected item becomes
     * disabled and the adapter does not contain an other item, which is enabled.
     */
    @SuppressWarnings("unchecked")
    public final void testSelectionIsAdaptedWhenSelectedItemBecomesDisabledAndAllOtherItemsAreDisabled() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
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

    /**
     * Ensures, that the selections of the unfiltered items are also adapted, when a filtered item
     * is selected.
     */
    public final void testAdaptUnfilteredItemsWhenItemIsSelected() {
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        FilterableImplementation item1 = new FilterableImplementation("cdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcquerystringdef");
        FilterableImplementation item3 = new FilterableImplementation("xsbiquerystringdjwi");
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.addItem(item3);
        singleChoiceListAdapterImplementation.applyFilter("querystring", 0);
        singleChoiceListAdapterImplementation.select(item3);
        assertEquals(item3, singleChoiceListAdapterImplementation.getSelectedItem());
        singleChoiceListAdapterImplementation.resetAllFilters();
        assertEquals(item3, singleChoiceListAdapterImplementation.getSelectedItem());
    }

    /**
     * Ensures, that the selections of the unfiltered items are also adapted, when a duplicate
     * filtered item is selected.
     */
    public final void testAdaptUnfilteredItemsWhenDuplicateItemIsSelected() {
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.allowDuplicates(true);
        FilterableImplementation item1 = new FilterableImplementation("cdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcquerystringdef");
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.applyFilter("querystring", 0);
        singleChoiceListAdapterImplementation.select(1);
        assertEquals(1, singleChoiceListAdapterImplementation.getSelectedIndex());
        singleChoiceListAdapterImplementation.resetAllFilters();
        assertFalse(singleChoiceListAdapterImplementation.isSelected(0));
        assertFalse(singleChoiceListAdapterImplementation.isSelected(1));
        assertTrue(singleChoiceListAdapterImplementation.isSelected(2));
    }

    /**
     * Ensures, that the selection is adapted, when the currently selected item is filtered.
     */
    public final void testAdaptSelectionWhenSelectedItemIsFiltered() {
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
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

    /**
     * Ensures, that the selection is adapted, when all items are filtered.
     */
    public final void testAdaptSelectionWhenAllItemsAreFiltered() {
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.allowDuplicates(true);
        FilterableImplementation item1 = new FilterableImplementation("cdefghij");
        FilterableImplementation item2 = new FilterableImplementation("bcdef");
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        singleChoiceListAdapterImplementation.select(item2);
        singleChoiceListAdapterImplementation.applyFilter("querystring", 0);
        assertNull(singleChoiceListAdapterImplementation.getSelectedItem());
        singleChoiceListAdapterImplementation.resetAllFilters();
        assertTrue(singleChoiceListAdapterImplementation.isSelected(0));
        assertFalse(singleChoiceListAdapterImplementation.isSelected(1));
    }

    /**
     * Tests the functionality of the method, which allows to set, whether an item should be
     * selected, when it is clicked by the user, or not.
     */
    @SuppressWarnings("unchecked")
    public final void testSelectItemOnClick() {
        Object item1 = new Object();
        Object item2 = new Object();
        Context context = getContext();
        Inflater inflater = InflaterFactory.createInflater(R.layout.view);
        DataSetObserver dataSetObserver = new DataSetObserver();
        ListSelectionListener<Object> listSelectionListener = mock(ListSelectionListener.class);
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(context, inflater,
                        new SelectableListDecoratorImplementation());
        singleChoiceListAdapterImplementation.selectItemOnClick(false);
        singleChoiceListAdapterImplementation.registerDataSetObserver(dataSetObserver);
        singleChoiceListAdapterImplementation.addSelectionListener(listSelectionListener);
        singleChoiceListAdapterImplementation.addItem(item1);
        singleChoiceListAdapterImplementation.addItem(item2);
        View view = singleChoiceListAdapterImplementation.getView(1, null, null);
        dataSetObserver.reset();
        view.performClick();
        assertTrue(singleChoiceListAdapterImplementation.isSelected(0));
        assertFalse(singleChoiceListAdapterImplementation.isSelected(1));
        assertFalse(dataSetObserver.hasOnChangedBeenCalled());
        verify(listSelectionListener, times(0))
                .onItemSelected(singleChoiceListAdapterImplementation, item2, 1);
        singleChoiceListAdapterImplementation.selectItemOnClick(true);
        assertTrue(singleChoiceListAdapterImplementation.isItemSelectedOnClick());
        view.performClick();
        assertFalse(singleChoiceListAdapterImplementation.isSelected(0));
        assertTrue(singleChoiceListAdapterImplementation.isSelected(1));
        assertTrue(dataSetObserver.hasOnChangedBeenCalled());
        verify(listSelectionListener, times(1))
                .onItemSelected(singleChoiceListAdapterImplementation, item2, 1);
    }

    /**
     * Tests the functionality of the toString-method.
     */
    public final void testToString() {
        ArrayList<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        LogLevel logLevel = LogLevel.ERROR;
        boolean allowDuplicates = true;
        boolean notifyOnchange = true;
        Set<ListAdapterItemClickListener<Object>> itemClickListeners = new LinkedHashSet<>();
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
        SingleChoiceListAdapterImplementation<Object> singleChoiceListAdapterImplementation =
                new SingleChoiceListAdapterImplementation<>(getContext(), mock(Inflater.class),
                        new SelectableListDecoratorImplementation(), logLevel, items,
                        allowDuplicates, notifyOnchange, itemClickListeners, adapterListeners,
                        enableStateListeners, numberOfItemStates, triggerItemStateOnClick,
                        itemStateListeners, sortingListeners, filterListeners, appliedFilters,
                        selectItemOnClick, selectionListeners, adaptSelectionAutomatically);
        singleChoiceListAdapterImplementation.setParameters(parameters);
        assertEquals("SingleChoiceListAdapter (" + items.size() + " items) [logLevel=" + logLevel +
                        ", parameters=" + parameters + ", notifyOnChange=" + notifyOnchange +
                        ", allowDuplicates=" + allowDuplicates + ", numberOfItemStates=" +
                        numberOfItemStates + ", triggerItemStateOnClick=" +
                        triggerItemStateOnClick + ", filtered=" + false + ", selectItemOnClick=" +
                        selectItemOnClick + ", adaptSelectionAutomatically=" +
                        adaptSelectionAutomatically + "]",
                singleChoiceListAdapterImplementation.toString());
    }

    /**
     * Tests the functionality of the clone-method.
     *
     * @throws CloneNotSupportedException
     *         The exception, which is thrown, if cloning is not supported
     */
    public final void testClone() throws CloneNotSupportedException {
        Context context = getContext();
        Inflater inflater = mock(Inflater.class);
        SelectableListDecorator<Object> decorator = new SelectableListDecoratorImplementation();
        ArrayList<Item<Object>> items = new ArrayList<>();
        boolean allowDuplicates = true;
        boolean notifyOnChange = true;
        Set<ListAdapterItemClickListener<Object>> itemClickListeners = new LinkedHashSet<>();
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
                new SingleChoiceListAdapterImplementation<>(context, inflater, decorator,
                        LogLevel.ALL, items, allowDuplicates, notifyOnChange, itemClickListeners,
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