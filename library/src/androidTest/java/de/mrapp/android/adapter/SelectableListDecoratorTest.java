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
package de.mrapp.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import de.mrapp.android.adapter.list.selectable.SelectableListAdapter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Tests the functionality of the class {@link SelectableListDecorator}.
 *
 * @author Michael Rapp
 */
@RunWith(AndroidJUnit4.class)
public class SelectableListDecoratorTest {

    /**
     * An implementation of the abstract {@link ListDecorator}, which is needed for test purposes.
     */
    private class SelectableListDecoratorImplementation extends SelectableListDecorator<Object> {

        /**
         * The context, which is passed to the decorator.
         */
        private Context context;

        /**
         * The adapter, which is passed to the decorator.
         */
        private SelectableListAdapter<Object> adapter;

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

        /**
         * The selection state, which is passed to the decorator.
         */
        private boolean selected;

        @SuppressWarnings("ConstantConditions")
        @NonNull
        @Override
        public View onInflateView(@NonNull final LayoutInflater inflater,
                                  @Nullable final ViewGroup parent, final int viewType) {
            return null;
        }

        @Override
        public void onShowItem(@NonNull final Context context,
                               @NonNull final SelectableListAdapter<Object> adapter,
                               @NonNull final View view, @NonNull final Object item,
                               final int viewType, final int index, final boolean enabled,
                               final int state, final boolean filtered, final boolean selected) {
            this.context = context;
            this.adapter = adapter;
            this.view = view;
            this.item = item;
            this.viewType = viewType;
            this.index = index;
            this.enabled = enabled;
            this.state = state;
            this.filtered = filtered;
            this.selected = selected;
        }

    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testApplyDecorator() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SelectableListAdapter<Object> adapter = mock(SelectableListAdapter.class);
        View view = new View(context);
        Object item = new Object();
        int index = 1;
        boolean enabled = true;
        int state = 1;
        boolean filtered = true;
        boolean selected = true;
        SelectableListDecoratorImplementation selectableListDecorator =
                new SelectableListDecoratorImplementation();
        selectableListDecorator
                .applyDecorator(context, adapter, view, item, index, enabled, state, filtered,
                        selected);
        assertEquals(context, selectableListDecorator.context);
        assertEquals(adapter, selectableListDecorator.adapter);
        assertEquals(view, selectableListDecorator.view);
        assertEquals(item, selectableListDecorator.item);
        assertEquals(0, selectableListDecorator.viewType);
        assertEquals(index, selectableListDecorator.index);
        assertEquals(enabled, selectableListDecorator.enabled);
        assertEquals(state, selectableListDecorator.state);
        assertEquals(filtered, selectableListDecorator.filtered);
        assertEquals(selected, selectableListDecorator.selected);
    }

    @Test
    public final void testGetViewType() {
        Object item = new Object();
        SelectableListDecoratorImplementation selectableListDecorator =
                new SelectableListDecoratorImplementation();
        int viewType = selectableListDecorator.getViewType(item);
        assertEquals(0, viewType);
    }

}