/*
 * Copyright 2014 - 2017 Michael Rapp
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

import de.mrapp.android.adapter.expandablelist.ExpandableListAdapter;
import de.mrapp.android.adapter.expandablelist.selectable.SelectableExpandableListAdapter;
import de.mrapp.android.adapter.list.ListAdapter;
import de.mrapp.android.adapter.list.selectable.SelectableListAdapter;

/**
 * Tests the functionality of the class {@link AdapterFactory}.
 *
 * @author Michael Rapp
 */
public class AdapterFactoryTest extends AndroidTestCase {

    /**
     * An implementation of the abstract class {@link ListDecorator}, which is needed for test
     * purposes.
     */
    private class ListDecoratorImplementation extends ListDecorator<Object> {

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

        }

    }

    /**
     * An implementation of the abstract class {@link SelectableListDecorator}, which is needed for
     * test purposes.
     */
    private class SelectableListDecoratorImplementation extends SelectableListDecorator<Object> {

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

        }

    }

    /**
     * An implementation of the abstract class {@link ExpandableListDecorator}, which is needed for
     * test purposes.
     */
    private class ExpandableListDecoratorImplementation
            extends ExpandableListDecorator<Object, Object> {

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

        }

    }

    /**
     * An implementation of the abstract class {@link SelectableExpandableListDecorator}, which is
     * needed for test purposes.
     */
    private class SelectableExpandableListDecoratorImplementation
            extends SelectableExpandableListDecorator<Object, Object> {

        @SuppressWarnings("ConstantConditions")
        @Override
        @NonNull
        public View onInflateGroupView(@NonNull final LayoutInflater inflater,
                                       @Nullable final ViewGroup parent, final int groupType) {
            return null;
        }

        @Override
        public void onShowGroup(@NonNull final Context context,
                                @NonNull final SelectableExpandableListAdapter<Object, Object> adapter,
                                @NonNull final View view, @NonNull final Object group,
                                final int viewType, final int index, final boolean expanded,
                                final boolean enabled, final int state, final boolean filtered,
                                final boolean selected) {

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
                                @NonNull final SelectableExpandableListAdapter<Object, Object> adapter,
                                @NonNull final View view, @NonNull final Object child,
                                final int viewType, final int childIndex,
                                @NonNull final Object group, final int groupIndex,
                                final boolean enabled, final int state, final boolean filtered,
                                final boolean selected) {

        }

    }

    /**
     * Tests the functionality of the method, which allows to create an instance of the type {@link
     * NoChoiceListAdapter}.
     */
    public final void testCreateListAdapter() {
        NoChoiceListAdapter<Object> listAdapter =
                AdapterFactory.createListAdapter(getContext(), new ListDecoratorImplementation());
        assertNotNull(listAdapter);
    }

    /**
     * Tests the functionality of the method, which allows to create an instance of the type {@link
     * SingleChoiceListAdapter}.
     */
    public final void testCreateSingleChoiceListAdapter() {
        SingleChoiceListAdapter<Object> singleChoiceListAdapter = AdapterFactory
                .createSingleChoiceListAdapter(getContext(),
                        new SelectableListDecoratorImplementation());
        assertNotNull(singleChoiceListAdapter);
    }

    /**
     * Tests the functionality of the method, which allows to create an instance of the type {@link
     * MultipleChoiceListAdapter}.
     */
    public final void testCreateMultipleChoiceListAdapter() {
        MultipleChoiceListAdapter<Object> multipleChoiceListAdapter = AdapterFactory
                .createMultipleChoiceListAdapter(getContext(),
                        new SelectableListDecoratorImplementation());
        assertNotNull(multipleChoiceListAdapter);
    }

    /**
     * Tests the functionality of the method, which allows to create an instance of the type {@link
     * NoChoiceExpandableListAdapter}.
     */
    public final void testCreateExpandableListAdapter() {
        NoChoiceExpandableListAdapter<Object, Object> expandableListAdapter = AdapterFactory
                .createExpandableListAdapter(getContext(),
                        new ExpandableListDecoratorImplementation());
        assertNotNull(expandableListAdapter);
    }

    /**
     * Tests the functionality of the method, which allows to create an instance of the type {@link
     * SingleChoiceExpandableListAdapter}.
     */
    public final void testCreateSingleChoiceExpandableListAdapter() {
        SingleChoiceExpandableListAdapter<Object, Object> singleChoiceExpandableListAdapter =
                AdapterFactory.createSingleChoiceExpandableListAdapter(getContext(),
                        new SelectableExpandableListDecoratorImplementation(),
                        ChoiceMode.GROUPS_AND_CHILDREN);
        assertNotNull(singleChoiceExpandableListAdapter);
    }

    /**
     * Tests the functionality of the method, which allows to create an instance of the type {@link
     * MultipleChoiceExpandableListAdapter}.
     */
    public final void testCreateMultipleChoiceExpandableListAdapter() {
        MultipleChoiceExpandableListAdapter<Object, Object> multipleChoiceExpandableListAdapter =
                AdapterFactory.createMultipleChoiceExpandableListAdapter(getContext(),
                        new SelectableExpandableListDecoratorImplementation(),
                        ChoiceMode.GROUPS_AND_CHILDREN);
        assertNotNull(multipleChoiceExpandableListAdapter);
    }

}