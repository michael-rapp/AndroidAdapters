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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import de.mrapp.android.adapter.ListDecorator;
import de.mrapp.android.adapter.R;
import de.mrapp.android.adapter.datastructure.AppliedFilter;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.filterable.ListFilterListener;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.util.logging.LogLevel;

/**
 * Tests the functionality of the class {@link NoChoiceListAdapterImplementation}.
 *
 * @author Michael Rapp
 */
public class NoChoiceListAdapterImplementationTest extends AndroidTestCase {

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
        NoChoiceListAdapterImplementation<Object> noChoiceListAdapterImplementation =
                new NoChoiceListAdapterImplementation<>(context, decorator, LogLevel.ALL, items,
                        allowDuplicates, notifyOnChange, itemClickListeners, itemLongClickListeners,
                        adapterListeners, enableStateListeners, numberOfItemStates,
                        triggerItemStateOnClick, itemStateListeners, sortingListeners,
                        filterListeners, appliedFilters);
        assertEquals(context, noChoiceListAdapterImplementation.getContext());
        assertEquals(decorator, noChoiceListAdapterImplementation.getDecorator());
        assertEquals(items, noChoiceListAdapterImplementation.getItems());
        assertEquals(allowDuplicates, noChoiceListAdapterImplementation.areDuplicatesAllowed());
        assertEquals(notifyOnChange, noChoiceListAdapterImplementation.isNotifiedOnChange());
        assertEquals(itemClickListeners, noChoiceListAdapterImplementation.getItemClickListeners());
        assertEquals(itemLongClickListeners,
                noChoiceListAdapterImplementation.getItemLongClickListeners());
        assertEquals(adapterListeners, noChoiceListAdapterImplementation.getAdapterListeners());
        assertEquals(numberOfItemStates, noChoiceListAdapterImplementation.getNumberOfItemStates());
        assertEquals(triggerItemStateOnClick,
                noChoiceListAdapterImplementation.isItemStateTriggeredOnClick());
    }

    /**
     * Tests, if all properties are set correctly by the public constructor.
     */
    public final void testPublicConstructor() {
        Context context = getContext();
        ListDecorator<Object> decorator = new ListDecoratorImplementation();
        NoChoiceListAdapterImplementation<Object> noChoiceListAdapterImplementation =
                new NoChoiceListAdapterImplementation<>(context, decorator);
        assertEquals(context, noChoiceListAdapterImplementation.getContext());
        assertEquals(decorator, noChoiceListAdapterImplementation.getDecorator());
        assertEquals(false, noChoiceListAdapterImplementation.areDuplicatesAllowed());
        assertEquals(true, noChoiceListAdapterImplementation.isNotifiedOnChange());
    }

    /**
     * Tests the functionality of the applyDecorator-method.
     */
    public final void testApplyDecorator() {
        Object item = new Object();
        Context context = getContext();
        ListDecoratorImplementation decorator = new ListDecoratorImplementation();
        NoChoiceListAdapterImplementation<Object> noChoiceListAdapterImplementation =
                new NoChoiceListAdapterImplementation<>(context, decorator);
        noChoiceListAdapterImplementation.setNumberOfItemStates(2);
        noChoiceListAdapterImplementation.addItem(item);
        noChoiceListAdapterImplementation.setEnabled(item, false);
        noChoiceListAdapterImplementation.setItemState(item, 1);
        noChoiceListAdapterImplementation.attach(new ListView(context));
        View view = noChoiceListAdapterImplementation.getView(0, null, null);
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
        NoChoiceListAdapterImplementation<Object> noChoiceListAdapterImplementation =
                new NoChoiceListAdapterImplementation<>(getContext(),
                        new ListDecoratorImplementation(), logLevel, items, allowDuplicates,
                        notifyOnChange, itemClickListeners, itemLongClickListeners,
                        adapterListeners, enableStateListeners, numberOfItemStates,
                        triggerItemStateOnClick, itemStateListeners, sortingListeners,
                        filterListeners, appliedFilters);
        noChoiceListAdapterImplementation.setParameters(parameters);
        assertEquals(
                "ListAdapter (" + items.size() + " items) [logLevel=" + logLevel + ", parameters=" +
                        parameters + ", notifyOnChange=" + notifyOnChange + ", allowDuplicates=" +
                        allowDuplicates + ", numberOfItemStates=" + numberOfItemStates +
                        ", triggerItemStateOnClick=" + triggerItemStateOnClick + ", filtered=" +
                        false + "]", noChoiceListAdapterImplementation.toString());
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
        NoChoiceListAdapterImplementation<Object> noChoiceListAdapterImplementation =
                new NoChoiceListAdapterImplementation<>(context, decorator, LogLevel.ALL, items,
                        allowDuplicates, notifyOnChange, itemClickListeners, itemLongClickListeners,
                        adapterListeners, enableStateListeners, numberOfItemStates,
                        triggerItemStateOnClick, itemStateListeners, sortingListeners,
                        filterListeners, appliedFilters);
        NoChoiceListAdapterImplementation<Object> clonedNoChoiceListAdapterImplementation =
                noChoiceListAdapterImplementation.clone();
        assertEquals(context, clonedNoChoiceListAdapterImplementation.getContext());
        assertEquals(decorator, clonedNoChoiceListAdapterImplementation.getDecorator());
        assertEquals(items, clonedNoChoiceListAdapterImplementation.getItems());
        assertEquals(allowDuplicates,
                clonedNoChoiceListAdapterImplementation.areDuplicatesAllowed());
        assertEquals(notifyOnChange, clonedNoChoiceListAdapterImplementation.isNotifiedOnChange());
        assertEquals(itemClickListeners,
                clonedNoChoiceListAdapterImplementation.getItemClickListeners());
        assertEquals(itemLongClickListeners,
                clonedNoChoiceListAdapterImplementation.getItemLongClickListeners());
        assertEquals(adapterListeners,
                clonedNoChoiceListAdapterImplementation.getAdapterListeners());
        assertEquals(numberOfItemStates,
                clonedNoChoiceListAdapterImplementation.getNumberOfItemStates());
        assertEquals(triggerItemStateOnClick,
                clonedNoChoiceListAdapterImplementation.isItemStateTriggeredOnClick());
    }

}