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
 * Tests the functionality of the class {@link ExpandableListDecorator}.
 *
 * @author Michael Rapp
 */
public class ExpandableListDecoratorTest extends AndroidTestCase {

    /**
     * An implementation of the abstract class {@link ExpandableListDecorator}, which is needed for
     * test purposes.
     */
    private class ExpandableListDecoratorImplementation
            extends ExpandableListDecorator<Object, Object> {

        /**
         * The context, which is passed to the decorator.
         */
        private Context context;

        /**
         * The adapter, which is passed to the decorator.
         */
        private ExpandableListAdapter<Object, Object> adapter;

        /**
         * The view, which is passed to the decorator.
         */
        private View view;

        /**
         * The group item, which is passed to the decorator.
         */
        private Object group;

        /**
         * The group view type, which is passed to the decorator.
         */
        private int groupViewType;

        /**
         * The child item, which is passed to the decorator.
         */
        private Object child;

        /**
         * The child view type, which is passed to the decorator.
         */
        private int childViewType;

        /**
         * The group index, which is passed to the decorator.
         */
        private int groupIndex;

        /**
         * The child index, which is passed to the decorator.
         */
        private int childIndex;

        /**
         * The expansion state, which is passed to the decorator.
         */
        private boolean expanded;

        /**
         * The enable state, which is passed to the decorator.
         */
        private boolean enabled;

        /**
         * The state, which is passed to the decorator.
         */
        private int state;

        /**
         * The filter state, which is passed to the decorator.
         */
        private boolean filtered;

        @SuppressWarnings("ConstantConditions")
        @Override
        @NonNull
        public View onInflateGroupView(@NonNull final LayoutInflater inflater,
                                       @Nullable final ViewGroup parent, final int groupType) {
            return null;
        }

        @Override
        public void onShowGroup(@NonNull final Context context,
                                @NonNull final ExpandableListAdapter<Object, Object> adapter,
                                @NonNull final View view, @NonNull final Object group,
                                final int viewType, final int index, final boolean expanded,
                                final boolean enabled, final int state, final boolean filtered) {
            this.context = context;
            this.adapter = adapter;
            this.view = view;
            this.group = group;
            this.groupViewType = viewType;
            this.childViewType = -1;
            this.groupIndex = index;
            this.expanded = expanded;
            this.enabled = enabled;
            this.state = state;
            this.filtered = filtered;
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        @NonNull
        public View onInflateChildView(@NonNull final LayoutInflater inflater,
                                       @Nullable final ViewGroup parent, final int childType) {
            return null;
        }

        @Override
        public void onShowChild(@NonNull final Context context,
                                @NonNull final ExpandableListAdapter<Object, Object> adapter,
                                @NonNull final View view, @NonNull final Object child,
                                final int viewType, final int childIndex,
                                @NonNull final Object group, final int groupIndex,
                                final boolean enabled, final int state, final boolean filtered) {
            this.context = context;
            this.adapter = adapter;
            this.view = view;
            this.child = child;
            this.childViewType = viewType;
            this.groupViewType = -1;
            this.childIndex = childIndex;
            this.group = group;
            this.groupIndex = groupIndex;
            this.enabled = enabled;
            this.state = state;
            this.filtered = filtered;
        }

    }

    ;

    /**
     * Tests the functionality of the applyDecoratorOnGroup-method.
     */
    @SuppressWarnings("unchecked")
    public final void testApplyDecoratorOnGroup() {
        Context context = getContext();
        ExpandableListAdapter<Object, Object> adapter = mock(ExpandableListAdapter.class);
        View view = new View(context);
        Object group = new Object();
        int index = 1;
        boolean expanded = true;
        boolean enabled = true;
        int state = 1;
        boolean filtered = true;
        ExpandableListDecoratorImplementation expandableListDecorator =
                new ExpandableListDecoratorImplementation();
        expandableListDecorator
                .applyDecoratorOnGroup(context, adapter, view, group, index, expanded, enabled,
                        state, filtered);
        assertEquals(context, expandableListDecorator.context);
        assertEquals(adapter, expandableListDecorator.adapter);
        assertEquals(view, expandableListDecorator.view);
        assertEquals(group, expandableListDecorator.group);
        assertEquals(0, expandableListDecorator.groupViewType);
        assertEquals(index, expandableListDecorator.groupIndex);
        assertEquals(expanded, expandableListDecorator.expanded);
        assertEquals(enabled, expandableListDecorator.enabled);
        assertEquals(state, expandableListDecorator.state);
        assertEquals(filtered, expandableListDecorator.filtered);
    }

    /**
     * Tests the functionality of the applyDecoratorOnChild-method.
     */
    @SuppressWarnings("unchecked")
    public final void testApplyDecoratorOnChild() {
        Context context = getContext();
        ExpandableListAdapter<Object, Object> adapter = mock(ExpandableListAdapter.class);
        View view = new View(context);
        Object group = new Object();
        Object child = new Object();
        int groupIndex = 1;
        int childIndex = 1;
        boolean enabled = true;
        int state = 1;
        boolean filtered = true;
        ExpandableListDecoratorImplementation expandableListDecorator =
                new ExpandableListDecoratorImplementation();
        expandableListDecorator
                .applyDecoratorOnChild(context, adapter, view, child, childIndex, group, groupIndex,
                        enabled, state, filtered);
        assertEquals(context, expandableListDecorator.context);
        assertEquals(adapter, expandableListDecorator.adapter);
        assertEquals(view, expandableListDecorator.view);
        assertEquals(group, expandableListDecorator.group);
        assertEquals(child, expandableListDecorator.child);
        assertEquals(0, expandableListDecorator.childViewType);
        assertEquals(groupIndex, expandableListDecorator.groupIndex);
        assertEquals(childIndex, expandableListDecorator.childIndex);
        assertEquals(enabled, expandableListDecorator.enabled);
        assertEquals(state, expandableListDecorator.state);
        assertEquals(filtered, expandableListDecorator.filtered);
    }

    /**
     * Tests the functionality of the getGroupViewType-method.
     */
    public final void testGetGroupViewType() {
        Object group = new Object();
        ExpandableListDecoratorImplementation expandableListDecorator =
                new ExpandableListDecoratorImplementation();
        int viewType = expandableListDecorator.getGroupType(group);
        assertEquals(0, viewType);
    }

    /**
     * Tests the functionality of the getChildViewType-method.
     */
    public final void testGetChildViewType() {
        Object child = new Object();
        ExpandableListDecoratorImplementation expandableListDecorator =
                new ExpandableListDecoratorImplementation();
        int viewType = expandableListDecorator.getChildType(child);
        assertEquals(0, viewType);
    }

}