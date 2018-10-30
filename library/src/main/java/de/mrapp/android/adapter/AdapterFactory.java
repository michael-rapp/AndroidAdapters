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

import de.mrapp.android.adapter.expandablelist.ExpandableListAdapter;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterImplementation;
import de.mrapp.android.adapter.expandablelist.selectable.MultipleChoiceExpandableListAdapterImplementation;
import de.mrapp.android.adapter.expandablelist.selectable.SingleChoiceExpandableListAdapterImplementation;
import de.mrapp.android.adapter.list.NoChoiceListAdapterImplementation;
import de.mrapp.android.adapter.list.selectable.MultipleChoiceListAdapterImplementation;
import de.mrapp.android.adapter.list.selectable.SingleChoiceListAdapterImplementation;

/**
 * An utility class, which offers factory methods, which allow to initialize instances of the types
 * {@link NoChoiceListAdapter}, {@link SingleChoiceListAdapter}, {@link MultipleChoiceListAdapter},
 * {@link NoChoiceExpandableListAdapter}, {@link SingleChoiceExpandableListAdapter} or {@link
 * MultipleChoiceExpandableListAdapter}.
 *
 * @author Michael Rapp
 * @since 0.1.0
 */
public final class AdapterFactory {

    /**
     * Creates a new utility class, which offers factory methods, which allow to initialize
     * instances of the  types {@link NoChoiceListAdapter}, {@link SingleChoiceListAdapter}, {@link
     * MultipleChoiceListAdapter}, {@link NoChoiceExpandableListAdapter}, {@link
     * SingleChoiceExpandableListAdapter} or {@link MultipleChoiceExpandableListAdapter}.
     */
    private AdapterFactory() {

    }

    /**
     * Creates and returns an adapter, whose underlying data is managed as a list of arbitrary
     * items.
     *
     * @param <DataType>
     *         The type of the adapter's underlying data
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param decorator
     *         The decorator, which should be used to customize the appearance of the views, which
     *         are used to visualize the items of the adapter, as an instance of the type {@link
     *         ListDecorator}. The decorator may not be null
     * @return The adapter, which has been created, as an instance of the type {@link
     * NoChoiceListAdapter}
     */
    public static <DataType> NoChoiceListAdapter<DataType> createListAdapter(
            @NonNull final Context context, @NonNull final ListDecorator<DataType> decorator) {
        return new NoChoiceListAdapterImplementation<>(context, decorator);
    }

    /**
     * Creates and returns an adapter, whose underlying data is managed as a list of arbitrary
     * items, of which only one single item can be selected at once.
     *
     * @param <DataType>
     *         The type of the adapter's underlying data
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param decorator
     *         The decorator, which should be used to customize the appearance of the views, which
     *         are used to visualize the items of the adapter, as an instance of the type {@link
     *         ListDecorator}. The decorator may not be null
     * @return The adapter, which has been created, as an instance of the type {@link
     * SingleChoiceListAdapter}
     */
    public static <DataType> SingleChoiceListAdapter<DataType> createSingleChoiceListAdapter(
            @NonNull final Context context,
            @NonNull final SelectableListDecorator<DataType> decorator) {
        return new SingleChoiceListAdapterImplementation<>(context, decorator);
    }

    /**
     * Creates and returns an adapter, whose underlying data is managed as a list of arbitrary
     * items, of which multiple items can be selected at once.
     *
     * @param <DataType>
     *         The type of the adapter's underlying data
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param decorator
     *         The decorator, which should be used to customize the appearance of the views, which
     *         are used to visualize the items of the adapter, as an instance of the type {@link
     *         ListDecorator}. The decorator may not be null
     * @return The adapter, which has been created, as an instance of the type {@link
     * MultipleChoiceListAdapter}
     */
    public static <DataType> MultipleChoiceListAdapter<DataType> createMultipleChoiceListAdapter(
            @NonNull final Context context,
            @NonNull final SelectableListDecorator<DataType> decorator) {
        return new MultipleChoiceListAdapterImplementation<>(context, decorator);
    }

    /**
     * Creates and returns an adapter, whose underlying data is managed as a list of arbitrary group
     * and child items.
     *
     * @param <GroupType>
     *         The type of the underlying data of the adapter's group items
     * @param <ChildType>
     *         The type of the underlying data of the adapter's child items
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param decorator
     *         The decorator, which should be used to customize the appearance of the views, which
     *         are used to visualize the group and child items of the adapter, as an instance of the
     *         type {@link ExpandableListDecorator}. The decorator may not be null
     * @return The adapter, which has been created, as an instance of the type {@link
     * ExpandableListAdapter}
     */
    public static <GroupType, ChildType> NoChoiceExpandableListAdapter<GroupType, ChildType> createExpandableListAdapter(
            @NonNull final Context context,
            @NonNull final ExpandableListDecorator<GroupType, ChildType> decorator) {
        return new ExpandableListAdapterImplementation<>(context, decorator);
    }

    /**
     * Creates and returns an adapter, whose underlying data is managed as a list of arbitrary group
     * and child items, of which only one can be selected at once.
     *
     * @param <GroupType>
     *         The type of the underlying data of the adapter's group items
     * @param <ChildType>
     *         The type of the underlying data of the adapter's child items
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param decorator
     *         The The decorator, which should be used to customize the appearance of the views,
     *         which are used to visualize the group and child items of the adapter, as an instance
     *         of the type {@link SelectableExpandableListDecorator}. The decorator may not be null
     * @param choiceMode
     *         The choice mode of the adapter as a value of the enum {@link ChoiceMode}. The choice
     *         mode may either be <code>GROUPS_ONLY</code>, <code>CHILDREN_ONLY</code> or
     *         <code>GROUPS_AND_CHILDREN</code>
     * @return The adapter, which has been created, as an instance of the type {@link
     * SingleChoiceExpandableListAdapter}
     */
    public static <GroupType, ChildType> SingleChoiceExpandableListAdapter<GroupType, ChildType> createSingleChoiceExpandableListAdapter(
            @NonNull final Context context,
            @NonNull final SelectableExpandableListDecorator<GroupType, ChildType> decorator,
            @NonNull final ChoiceMode choiceMode) {
        return new SingleChoiceExpandableListAdapterImplementation<>(context, decorator,
                choiceMode);
    }

    /**
     * Creates and returns an adapter, whose underlying data is managed as a list of arbitrary group
     * and child items, of which multiple items can be selected at once.
     *
     * @param <GroupType>
     *         The type of the underlying data of the adapter's group items
     * @param <ChildType>
     *         The type of the underlying data of the adapter's child items
     * @param context
     *         The context, the adapter belongs to, as an instance of the class {@link Context}. The
     *         context may not be null
     * @param decorator
     *         The The decorator, which should be used to customize the appearance of the views,
     *         which are used to visualize the group and child items of the adapter, as an instance
     *         of the type {@link SelectableExpandableListDecorator}. The decorator may not be null
     * @param choiceMode
     *         The choice mode of the adapter as a value of the enum {@link ChoiceMode}. The choice
     *         mode may either be <code>GROUPS_ONLY</code>, <code>CHILDREN_ONLY</code> or
     *         <code>GROUPS_AND_CHILDREN</code>
     * @return The adapter, which has been created, as an instance of the type {@link
     * MultipleChoiceExpandableListAdapter}
     */
    public static <GroupType, ChildType> MultipleChoiceExpandableListAdapter<GroupType, ChildType> createMultipleChoiceExpandableListAdapter(
            @NonNull final Context context,
            @NonNull final SelectableExpandableListDecorator<GroupType, ChildType> decorator,
            @NonNull final ChoiceMode choiceMode) {
        return new MultipleChoiceExpandableListAdapterImplementation<>(context, decorator,
                choiceMode);
    }

}