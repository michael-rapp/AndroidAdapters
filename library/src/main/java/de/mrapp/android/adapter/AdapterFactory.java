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

import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterImplementation;
import de.mrapp.android.adapter.expandablelist.selectable.MultipleChoiceExpandableListAdapterImplementation;
import de.mrapp.android.adapter.expandablelist.selectable.SingleChoiceExpandableListAdapterImplementation;
import de.mrapp.android.adapter.list.ListAdapterImplementation;
import de.mrapp.android.adapter.list.selectable.MultipleChoiceListAdapterImplementation;
import de.mrapp.android.adapter.list.selectable.SingleChoiceListAdapterImplementation;

/**
 * An utility class, which offers factory methods, which allow to initialize instances of the types
 * {@link ListAdapter}, {@link SingleChoiceListAdapter} or {@link MultipleChoiceListAdapter}.
 *
 * @author Michael Rapp
 * @since 0.1.0
 */
public final class AdapterFactory {

    /**
     * Creates a new utility class, which offers factory methods, which allow to initialize
     * instances of the types {@link ListAdapter}, {@link SingleChoiceListAdapter} or {@link
     * MultipleChoiceListAdapter}.
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
     * @return The adapter, which has been created, as an instance of the type {@link ListAdapter}
     */
    public static <DataType> ListAdapter<DataType> createListAdapter(@NonNull final Context context,
                                                                     @NonNull final ListDecorator<DataType> decorator) {
        return new ListAdapterImplementation<>(context, decorator);
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
    public static <GroupType, ChildType> ExpandableListAdapter<GroupType, ChildType> createExpandableListAdapter(
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