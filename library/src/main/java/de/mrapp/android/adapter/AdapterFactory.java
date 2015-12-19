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
import android.view.View;

import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterImplementation;
import de.mrapp.android.adapter.expandablelist.selectable.ChoiceMode;
import de.mrapp.android.adapter.expandablelist.selectable.MultipleChoiceExpandableListAdapterImplementation;
import de.mrapp.android.adapter.expandablelist.selectable.SingleChoiceExpandableListAdapterImplementation;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.inflater.InflaterFactory;
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
     * @param viewId
     *         The resource id of the view, which should be used to visualize the items of the
     *         adapter, as an {@link Integer} value. The id must correspond to a valid view
     *         resource
     * @return The adapter, which has been created, as an instance of the type {@link ListAdapter}
     */
    public static <DataType> ListAdapter<DataType> createListAdapter(final Context context,
                                                                     final ListDecorator<DataType> decorator,
                                                                     final int viewId) {
        Inflater inflater = InflaterFactory.createInflater(viewId);
        return new ListAdapterImplementation<DataType>(context, inflater, decorator);
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
     * @param view
     *         The view, which should be used to visualize the items of the adapter, as an instance
     *         of the class {@link View}. The view may not be null
     * @return The adapter, which has been created, as an instance of the type {@link ListAdapter}
     */
    public static <DataType> ListAdapter<DataType> createListAdapter(final Context context,
                                                                     final ListDecorator<DataType> decorator,
                                                                     final View view) {
        Inflater inflater = InflaterFactory.createInflater(view);
        return new ListAdapterImplementation<DataType>(context, inflater, decorator);
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
     * @param viewId
     *         The resource id of the view, which should be used to visualize the items of the
     *         adapter, as an {@link Integer} value. The id must correspond to a valid view
     *         resource
     * @return The adapter, which has been created, as an instance of the type {@link
     * SingleChoiceListAdapter}
     */
    public static <DataType> SingleChoiceListAdapter<DataType> createSingleChoiceListAdapter(
            final Context context, final SelectableListDecorator<DataType> decorator,
            final int viewId) {
        Inflater inflater = InflaterFactory.createInflater(viewId);
        return new SingleChoiceListAdapterImplementation<DataType>(context, inflater, decorator);
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
     * @param view
     *         The view, which should be used to visualize the items of the adapter, as an instance
     *         of the class {@link View}. The view may not be null
     * @return The adapter, which has been created, as an instance of the type {@link
     * SingleChoiceListAdapter}
     */
    public static <DataType> SingleChoiceListAdapter<DataType> createSingleChoiceListAdapter(
            final Context context, final SelectableListDecorator<DataType> decorator,
            final View view) {
        Inflater inflater = InflaterFactory.createInflater(view);
        return new SingleChoiceListAdapterImplementation<DataType>(context, inflater, decorator);
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
     * @param viewId
     *         The resource id of the view, which should be used to visualize the items of the
     *         adapter, as an {@link Integer} value. The id must correspond to a valid view
     *         resource
     * @return The adapter, which has been created, as an instance of the type {@link
     * MultipleChoiceListAdapter}
     */
    public static <DataType> MultipleChoiceListAdapter<DataType> createMultipleChoiceListAdapter(
            final Context context, final SelectableListDecorator<DataType> decorator,
            final int viewId) {
        Inflater inflater = InflaterFactory.createInflater(viewId);
        return new MultipleChoiceListAdapterImplementation<DataType>(context, inflater, decorator);
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
     * @param view
     *         The view, which should be used to visualize the items of the adapter, as an instance
     *         of the class {@link View}. The view may not be null
     * @return The adapter, which has been created, as an instance of the type {@link
     * MultipleChoiceListAdapter}
     */
    public static <DataType> MultipleChoiceListAdapter<DataType> createMultipleChoiceListAdapter(
            final Context context, final SelectableListDecorator<DataType> decorator,
            final View view) {
        Inflater inflater = InflaterFactory.createInflater(view);
        return new MultipleChoiceListAdapterImplementation<DataType>(context, inflater, decorator);
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
     * @param groupViewId
     *         The resource id of the view, which should be used to visualize the group items of the
     *         adapter, as an {@link Integer} value. The id must correspond to a valid view
     *         resource
     * @param childViewId
     *         The resource id of the view, which should be used to visualize the child items of the
     *         adapter, as an {@link Integer} value. The id must correspond to a valid view
     *         resource
     * @return The adapter, which has been created, as an instance of the type {@link
     * ExpandableListAdapter}
     */
    public static <GroupType, ChildType> ExpandableListAdapter<GroupType, ChildType> createExpandableListAdapter(
            final Context context, final ExpandableListDecorator<GroupType, ChildType> decorator,
            final int groupViewId, final int childViewId) {
        Inflater groupInflater = InflaterFactory.createInflater(groupViewId);
        Inflater childInflater = InflaterFactory.createInflater(childViewId);
        return new ExpandableListAdapterImplementation<GroupType, ChildType>(context, groupInflater,
                childInflater, decorator);
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
     * @param groupView
     *         The view, which should be used to visualize the group items of the adapter, as an
     *         instance of the class {@link View}. The view may not be null
     * @param childView
     *         The view, which should be used to visualize the child items of the adapter, as an
     *         instance of the class {@link View}. The view may not be null
     * @return The adapter, which has been created, as an instance of the type {@link
     * ExpandableListAdapter}
     */
    public static <GroupType, ChildType> ExpandableListAdapter<GroupType, ChildType> createExpandableListAdapter(
            final Context context, final ExpandableListDecorator<GroupType, ChildType> decorator,
            final View groupView, final View childView) {
        Inflater groupInflater = InflaterFactory.createInflater(groupView);
        Inflater childInflater = InflaterFactory.createInflater(childView);
        return new ExpandableListAdapterImplementation<GroupType, ChildType>(context, groupInflater,
                childInflater, decorator);
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
     * @param groupViewId
     *         The resource id of the view, which should be used to visualize the group items of the
     *         adapter, as an {@link Integer} value. The id must correspond to a valid view
     *         resource
     * @param childViewId
     *         The resource id of the view, which should be used to visualize the child items of the
     *         adapter, as an {@link Integer} value. The id must correspond to a valid view
     *         resource
     * @param choiceMode
     *         The choice mode of the adapter as a value of the enum {@link ChoiceMode}. The choice
     *         mode may either be <code>GROUPS_ONLY</code>, <code>CHILDREN_ONLY</code> or
     *         <code>GROUPS_AND_CHILDREN</code>
     * @return The adapter, which has been created, as an instance of the type {@link
     * SingleChoiceExpandableListAdapter}
     */
    public static <GroupType, ChildType> SingleChoiceExpandableListAdapter<GroupType, ChildType> createSingleChoiceExpandableListAdapter(
            final Context context,
            final SelectableExpandableListDecorator<GroupType, ChildType> decorator,
            final int groupViewId, final int childViewId, final ChoiceMode choiceMode) {
        Inflater groupInflater = InflaterFactory.createInflater(groupViewId);
        Inflater childInflater = InflaterFactory.createInflater(childViewId);
        return new SingleChoiceExpandableListAdapterImplementation<>(context, groupInflater,
                childInflater, decorator, choiceMode);
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
     * @param groupView
     *         The view, which should be used to visualize the group items of the adapter, as an
     *         instance of the class {@link View}. The view may not be null
     * @param childView
     *         The view, which should be used to visualize the child items of the adapter, as an
     *         instance of the class {@link View}. The view may not be null
     * @param choiceMode
     *         The choice mode of the adapter as a value of the enum {@link ChoiceMode}. The choice
     *         mode may either be <code>GROUPS_ONLY</code>, <code>CHILDREN_ONLY</code> or
     *         <code>GROUPS_AND_CHILDREN</code>
     * @return The adapter, which has been created, as an instance of the type {@link
     * SingleChoiceExpandableListAdapter}
     */
    public static <GroupType, ChildType> SingleChoiceExpandableListAdapter<GroupType, ChildType> createSingleChoiceExpandableListAdapter(
            final Context context,
            final SelectableExpandableListDecorator<GroupType, ChildType> decorator,
            final View groupView, final View childView, final ChoiceMode choiceMode) {
        Inflater groupInflater = InflaterFactory.createInflater(groupView);
        Inflater childInflater = InflaterFactory.createInflater(childView);
        return new SingleChoiceExpandableListAdapterImplementation<>(context, groupInflater,
                childInflater, decorator, choiceMode);
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
     * @param groupViewId
     *         The resource id of the view, which should be used to visualize the group items of the
     *         adapter, as an {@link Integer} value. The id must correspond to a valid view
     *         resource
     * @param childViewId
     *         The resource id of the view, which should be used to visualize the child items of the
     *         adapter, as an {@link Integer} value. The id must correspond to a valid view
     *         resource
     * @param choiceMode
     *         The choice mode of the adapter as a value of the enum {@link ChoiceMode}. The choice
     *         mode may either be <code>GROUPS_ONLY</code>, <code>CHILDREN_ONLY</code> or
     *         <code>GROUPS_AND_CHILDREN</code>
     * @return The adapter, which has been created, as an instance of the type {@link
     * MultipleChoiceExpandableListAdapter}
     */
    public static <GroupType, ChildType> MultipleChoiceExpandableListAdapter<GroupType, ChildType> createMultipleChoiceExpandableListAdapter(
            final Context context,
            final SelectableExpandableListDecorator<GroupType, ChildType> decorator,
            final int groupViewId, final int childViewId, final ChoiceMode choiceMode) {
        Inflater groupInflater = InflaterFactory.createInflater(groupViewId);
        Inflater childInflater = InflaterFactory.createInflater(childViewId);
        return new MultipleChoiceExpandableListAdapterImplementation<>(context, groupInflater,
                childInflater, decorator, choiceMode);
    }

    /**
     * Creates and returns an adapter, whose underlying data is managed as a list of arbitrary group
     * and child items, of multiple items can be selected at once.
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
     * @param groupView
     *         The view, which should be used to visualize the group items of the adapter, as an
     *         instance of the class {@link View}. The view may not be null
     * @param childView
     *         The view, which should be used to visualize the child items of the adapter, as an
     *         instance of the class {@link View}. The view may not be null
     * @param choiceMode
     *         The choice mode of the adapter as a value of the enum {@link ChoiceMode}. The choice
     *         mode may either be <code>GROUPS_ONLY</code>, <code>CHILDREN_ONLY</code> or
     *         <code>GROUPS_AND_CHILDREN</code>
     * @return The adapter, which has been created, as an instance of the type {@link
     * MultipleChoiceExpandableListAdapter}
     */
    public static <GroupType, ChildType> MultipleChoiceExpandableListAdapter<GroupType, ChildType> createMultipleChoiceExpandableListAdapter(
            final Context context,
            final SelectableExpandableListDecorator<GroupType, ChildType> decorator,
            final View groupView, final View childView, final ChoiceMode choiceMode) {
        Inflater groupInflater = InflaterFactory.createInflater(groupView);
        Inflater childInflater = InflaterFactory.createInflater(childView);
        return new MultipleChoiceExpandableListAdapterImplementation<>(context, groupInflater,
                childInflater, decorator, choiceMode);
    }

}