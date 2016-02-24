/*
 * Copyright 2014 - 2016 Michael Rapp
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