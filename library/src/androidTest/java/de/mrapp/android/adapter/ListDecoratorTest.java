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
package de.mrapp.android.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.mrapp.android.adapter.list.ListAdapter;

import static org.mockito.Mockito.mock;

/**
 * Tests the functionality of the class {@link ListDecorator}.
 *
 * @author Michael Rapp
 */
public class ListDecoratorTest extends AndroidTestCase {

    /**
     * An implementation of the abstract {@link ListDecorator}, which is needed for test purposes.
     */
    private class ListDecoratorImplementation extends ListDecorator<Object> {

        /**
         * The context, which is passed to the decorator.
         */
        private Context context;

        /**
         * The adapter, which is passed to the decorator.
         */
        private ListAdapter<Object> adapter;

        /**
         * The view, which is passed to the decorator.
         */
        private View view;

        /**
         * The item, which is passed to the decorator.
         */
        private Object item;

        /**
         * The view type, which is passed to the decorator.
         */
        private int viewType;

        /**
         * The index, which is passed to the decorator.
         */
        private int index;

        /**
         * The enable state, which is passed to the decorator.
         */
        private boolean enabled;

        /**
         * The item state, which is passed to the decorator.
         */
        private int state;

        /**
         * The filter state, which is passed to the decorator.
         */
        private boolean filtered;

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
            this.context = context;
            this.adapter = adapter;
            this.view = view;
            this.item = item;
            this.viewType = viewType;
            this.index = index;
            this.enabled = enabled;
            this.state = state;
            this.filtered = filtered;
        }

    }

    /**
     * Tests the functionality of the applyDecorator-method.
     */
    @SuppressWarnings("unchecked")
    public final void testApplyDecorator() {
        Context context = getContext();
        ListAdapter<Object> adapter = mock(ListAdapter.class);
        View view = new View(context);
        Object item = new Object();
        int index = 1;
        boolean enabled = true;
        int state = 1;
        boolean filtered = true;
        ListDecoratorImplementation listDecorator = new ListDecoratorImplementation();
        listDecorator.applyDecorator(context, adapter, view, item, index, enabled, state, filtered);
        assertEquals(context, listDecorator.context);
        assertEquals(adapter, listDecorator.adapter);
        assertEquals(view, listDecorator.view);
        assertEquals(item, listDecorator.item);
        assertEquals(0, listDecorator.viewType);
        assertEquals(index, listDecorator.index);
        assertEquals(enabled, listDecorator.enabled);
        assertEquals(state, listDecorator.state);
        assertEquals(filtered, listDecorator.filtered);
    }

    /**
     * Tests the functionality of the getViewType-method.
     */
    public final void testGetViewType() {
        Object item = new Object();
        ListDecoratorImplementation listDecorator = new ListDecoratorImplementation();
        int viewType = listDecorator.getViewType(item);
        assertEquals(0, viewType);
    }

}