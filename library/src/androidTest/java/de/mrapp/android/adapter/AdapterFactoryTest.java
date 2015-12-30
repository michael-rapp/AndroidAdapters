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
package de.mrapp.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.mrapp.android.adapter.expandablelist.selectable.SelectableExpandableListAdapter;
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
     * ListAdapter}.
     */
    public final void testCreateListAdapter() {
        ListAdapter<Object> listAdapter =
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
     * ExpandableListAdapter}.
     */
    public final void testCreateExpandableListAdapter() {
        ExpandableListAdapter<Object, Object> expandableListAdapter = AdapterFactory
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