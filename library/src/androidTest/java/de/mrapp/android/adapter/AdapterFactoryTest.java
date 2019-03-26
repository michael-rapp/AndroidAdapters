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
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapter;
import de.mrapp.android.adapter.expandablelist.selectable.SelectableExpandableListAdapter;
import de.mrapp.android.adapter.list.ListAdapter;
import de.mrapp.android.adapter.list.selectable.SelectableListAdapter;

import static org.junit.Assert.assertNotNull;

/**
 * Tests the functionality of the class {@link AdapterFactory}.
 *
 * @author Michael Rapp
 */
@RunWith(AndroidJUnit4.class)
public class AdapterFactoryTest {

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

    @Test
    public final void testCreateListAdapter() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        NoChoiceListAdapter<Object> listAdapter =
                AdapterFactory.createListAdapter(context, new ListDecoratorImplementation());
        assertNotNull(listAdapter);
    }

    @Test
    public final void testCreateSingleChoiceListAdapter() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceListAdapter<Object> singleChoiceListAdapter = AdapterFactory
                .createSingleChoiceListAdapter(context,
                        new SelectableListDecoratorImplementation());
        assertNotNull(singleChoiceListAdapter);
    }

    @Test
    public final void testCreateMultipleChoiceListAdapter() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceListAdapter<Object> multipleChoiceListAdapter = AdapterFactory
                .createMultipleChoiceListAdapter(context,
                        new SelectableListDecoratorImplementation());
        assertNotNull(multipleChoiceListAdapter);
    }

    @Test
    public final void testCreateExpandableListAdapter() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        NoChoiceExpandableListAdapter<Object, Object> expandableListAdapter = AdapterFactory
                .createExpandableListAdapter(context, new ExpandableListDecoratorImplementation());
        assertNotNull(expandableListAdapter);
    }

    @Test
    public final void testCreateSingleChoiceExpandableListAdapter() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        SingleChoiceExpandableListAdapter<Object, Object> singleChoiceExpandableListAdapter =
                AdapterFactory.createSingleChoiceExpandableListAdapter(context,
                        new SelectableExpandableListDecoratorImplementation(),
                        ChoiceMode.GROUPS_AND_CHILDREN);
        assertNotNull(singleChoiceExpandableListAdapter);
    }

    @Test
    public final void testCreateMultipleChoiceExpandableListAdapter() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        MultipleChoiceExpandableListAdapter<Object, Object> multipleChoiceExpandableListAdapter =
                AdapterFactory.createMultipleChoiceExpandableListAdapter(context,
                        new SelectableExpandableListDecoratorImplementation(),
                        ChoiceMode.GROUPS_AND_CHILDREN);
        assertNotNull(multipleChoiceExpandableListAdapter);
    }

}