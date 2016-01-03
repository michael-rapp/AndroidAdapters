/*
 * AndroidAdapters Copyright 2014 - 2016 Michael Rapp
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
package de.mrapp.android.adapter.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import de.mrapp.android.adapter.ListAdapter;
import de.mrapp.android.adapter.ListDecorator;
import de.mrapp.android.adapter.R;
import de.mrapp.android.adapter.datastructure.AppliedFilter;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.adapter.logging.LogLevel;

/**
 * Tests the functionality of the class {@link ListAdapterImplementation}.
 *
 * @author Michael Rapp
 */
public class ListAdapterImplementationTest extends AndroidTestCase {

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
     * Tests, if all properties are set correctly by the protected constructor.
     */
    public final void testProtectedConstructor() {
        Context context = getContext();
        ListDecorator<Object> decorator = new ListDecoratorImplementation();
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
        ListAdapterImplementation<Object> listAdapterImplementation =
                new ListAdapterImplementation<>(context, decorator, LogLevel.ALL, items,
                        allowDuplicates, notifyOnChange, itemClickListeners, itemLongClickListeners,
                        adapterListeners, enableStateListeners, numberOfItemStates,
                        triggerItemStateOnClick, itemStateListeners, sortingListeners,
                        filterListeners, appliedFilters);
        assertEquals(context, listAdapterImplementation.getContext());
        assertEquals(decorator, listAdapterImplementation.getDecorator());
        assertEquals(items, listAdapterImplementation.getItems());
        assertEquals(allowDuplicates, listAdapterImplementation.areDuplicatesAllowed());
        assertEquals(notifyOnChange, listAdapterImplementation.isNotifiedOnChange());
        assertEquals(itemClickListeners, listAdapterImplementation.getItemClickListeners());
        assertEquals(itemLongClickListeners, listAdapterImplementation.getItemLongClickListeners());
        assertEquals(adapterListeners, listAdapterImplementation.getAdapterListeners());
        assertEquals(numberOfItemStates, listAdapterImplementation.getNumberOfItemStates());
        assertEquals(triggerItemStateOnClick,
                listAdapterImplementation.isItemStateTriggeredOnClick());
    }

    /**
     * Tests, if all properties are set correctly by the public constructor.
     */
    public final void testPublicConstructor() {
        Context context = getContext();
        ListDecorator<Object> decorator = new ListDecoratorImplementation();
        ListAdapterImplementation<Object> listAdapterImplementation =
                new ListAdapterImplementation<>(context, decorator);
        assertEquals(context, listAdapterImplementation.getContext());
        assertEquals(decorator, listAdapterImplementation.getDecorator());
        assertEquals(false, listAdapterImplementation.areDuplicatesAllowed());
        assertEquals(true, listAdapterImplementation.isNotifiedOnChange());
    }

    /**
     * Tests the functionality of the applyDecorator-method.
     */
    public final void testApplyDecorator() {
        Object item = new Object();
        Context context = getContext();
        ListDecoratorImplementation decorator = new ListDecoratorImplementation();
        ListAdapterImplementation<Object> listAdapterImplementation =
                new ListAdapterImplementation<>(context, decorator);
        listAdapterImplementation.setNumberOfItemStates(2);
        listAdapterImplementation.addItem(item);
        listAdapterImplementation.setEnabled(item, false);
        listAdapterImplementation.setItemState(item, 1);
        View view = listAdapterImplementation.getView(0, null, null);
        view.performClick();
        assertTrue(decorator.hasOnShowItemBeenInvoked);
    }

    /**
     * Tests the functionality of the toString-method.
     */
    public final void testToString() {
        ArrayList<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
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
        Bundle parameters = new Bundle();
        ListAdapterImplementation<Object> listAdapterImplementation =
                new ListAdapterImplementation<>(getContext(), new ListDecoratorImplementation(),
                        logLevel, items, allowDuplicates, notifyOnChange, itemClickListeners,
                        itemLongClickListeners, adapterListeners, enableStateListeners,
                        numberOfItemStates, triggerItemStateOnClick, itemStateListeners,
                        sortingListeners, filterListeners, appliedFilters);
        listAdapterImplementation.setParameters(parameters);
        assertEquals(
                "ListAdapter (" + items.size() + " items) [logLevel=" + logLevel + ", parameters=" +
                        parameters + ", notifyOnChange=" + notifyOnChange + ", allowDuplicates=" +
                        allowDuplicates + ", numberOfItemStates=" + numberOfItemStates +
                        ", triggerItemStateOnClick=" + triggerItemStateOnClick + ", filtered=" +
                        false + "]", listAdapterImplementation.toString());
    }

    /**
     * Tests the functionality of the clone-method.
     *
     * @throws CloneNotSupportedException
     *         The exception, which is thrown, if cloning is not supported
     */
    public final void testClone() throws CloneNotSupportedException {
        Context context = getContext();
        ListDecorator<Object> decorator = new ListDecoratorImplementation();
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
        ListAdapterImplementation<Object> listAdapterImplementation =
                new ListAdapterImplementation<>(context, decorator, LogLevel.ALL, items,
                        allowDuplicates, notifyOnChange, itemClickListeners, itemLongClickListeners,
                        adapterListeners, enableStateListeners, numberOfItemStates,
                        triggerItemStateOnClick, itemStateListeners, sortingListeners,
                        filterListeners, appliedFilters);
        ListAdapterImplementation<Object> clonedListAdapterImplementation =
                listAdapterImplementation.clone();
        assertEquals(context, clonedListAdapterImplementation.getContext());
        assertEquals(decorator, clonedListAdapterImplementation.getDecorator());
        assertEquals(items, clonedListAdapterImplementation.getItems());
        assertEquals(allowDuplicates, clonedListAdapterImplementation.areDuplicatesAllowed());
        assertEquals(notifyOnChange, clonedListAdapterImplementation.isNotifiedOnChange());
        assertEquals(itemClickListeners, clonedListAdapterImplementation.getItemClickListeners());
        assertEquals(itemLongClickListeners,
                clonedListAdapterImplementation.getItemLongClickListeners());
        assertEquals(adapterListeners, clonedListAdapterImplementation.getAdapterListeners());
        assertEquals(numberOfItemStates, clonedListAdapterImplementation.getNumberOfItemStates());
        assertEquals(triggerItemStateOnClick,
                clonedListAdapterImplementation.isItemStateTriggeredOnClick());
    }

}