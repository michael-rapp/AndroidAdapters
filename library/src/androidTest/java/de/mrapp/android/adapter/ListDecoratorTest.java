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
package de.mrapp.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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