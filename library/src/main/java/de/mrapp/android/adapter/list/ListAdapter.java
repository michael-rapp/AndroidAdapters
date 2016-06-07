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
package de.mrapp.android.adapter.list;

import android.support.annotation.NonNull;
import android.widget.AbsListView;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import de.mrapp.android.adapter.RecyclerViewAdapter;
import de.mrapp.android.adapter.list.enablestate.EnableStateListAdapter;
import de.mrapp.android.adapter.list.filterable.FilterableListAdapter;
import de.mrapp.android.adapter.list.itemstate.ItemStateListAdapter;
import de.mrapp.android.adapter.list.sortable.SortableListAdapter;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list of arbitrary items,
 * must implement. Such an adapter's purpose is to provide the underlying data for visualization
 * using a {@link AbsListView} widget.
 *
 * @param <DataType>
 *         The type of the adapter's underlying data
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface ListAdapter<DataType>
        extends RecyclerViewAdapter, android.widget.ListAdapter, EnableStateListAdapter<DataType>,
        ItemStateListAdapter<DataType>, SortableListAdapter<DataType>,
        FilterableListAdapter<DataType> {

    /**
     * Returns, whether duplicate items are allowed, or not.
     *
     * @return True, if duplicate items are allowed, false otherwise
     */
    boolean areDuplicatesAllowed();

    /**
     * Sets, whether duplicate items should be allowed, or not.
     *
     * @param allowDuplicates
     *         True, if duplicate items should be allowed, false otherwise
     */
    void allowDuplicates(boolean allowDuplicates);

    /**
     * Adds a specific item to the adapter. The item will be added at the end.
     *
     * @param item
     *         The item, which should be added to the adapter, as an instance of the generic type
     *         DataType. The item may not be null
     * @return The index of the the item, which has been added to the adapter, as an {@link Integer}
     * value or -1, if the item has not been added
     */
    int addItem(@NonNull DataType item);

    /**
     * Adds a specific item to the adapter. The item will be added at a specific index.
     *
     * @param index
     *         The index, the item should be added at, as an {@link Integer} value. The index must
     *         be between 0 and the value of the method <code>getCount():int</code>, otherwise an
     *         {@link IndexOutOfBoundsException} will be thrown
     * @param item
     *         The item, which should be added to the adapter, as an instance of the generic type
     *         DataType. The item may not be null
     * @return True, if the item has been added to the adapter, false otherwise
     */
    boolean addItem(int index, @NonNull DataType item);

    /**
     * Adds all items, which are contained by a specific collection, to the adapter. The items will
     * be added in a consecutive order at the end.
     *
     * @param items
     *         The collection, which contains the items, which should be added to the adapter, as an
     *         instance of the type {@link Collection} or an empty collection, if no items should be
     *         added
     * @return True, if all items have been added to the adapter, false otherwise
     */
    boolean addAllItems(@NonNull Collection<? extends DataType> items);

    /**
     * Adds all items, which are contained by a specific collection, to the adapter. The items will
     * be added in a consecutive order at a specific index.
     *
     * @param index
     *         The index, the items should be added at, as an {@link Integer} value. The index must
     *         be between 0 and the value of the method <code>getCount():int</code>, otherwise an
     *         {@link IndexOutOfBoundsException} will be thrown
     * @param items
     *         The collection, which contains the items, which should be added to the adapter, as an
     *         instance of the type {@link Collection} or an empty collection, if no items should be
     *         added
     * @return True, if all items have been added to the adapter, false otherwise
     */
    boolean addAllItems(int index, @NonNull Collection<? extends DataType> items);

    /**
     * Adds all items, which are contained by a specific array, to the adapter. The items will be
     * added in a consecutive order at the end.
     *
     * @param items
     *         The array, which contains the items, which should be added to the adapter, as an
     *         array of the generic type DataType or an empty array, if no items should be added
     * @return True, if all items have been added to the adapter, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean addAllItems(@NonNull DataType... items);

    /**
     * Adds all items, which are contained by a specific array, to the adapter. The items will be
     * added in a consecutive order at a specific index.
     *
     * @param index
     *         The index, the items should be added at, as an {@link Integer} value. The index must
     *         be between 0 and the value of the method <code>getCount():int</code>, otherwise an
     *         {@link IndexOutOfBoundsException} will be thrown
     * @param items
     *         The array, which contains the items, which should be added to the adapter, as an
     *         array of the generic type DataType or an empty array, if no items should be added
     * @return True, if all items have been added to the adapter, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean addAllItems(int index, @NonNull DataType... items);

    /**
     * Replaces the item, which belongs to a specific index, by an other item.
     *
     * @param index
     *         The index of the item, which should be replaced, as an {@link Integer} value. The
     *         index must be between 0 and the value of the method <code>getCount():int</code> - 1,
     *         otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @param item
     *         The item, which should replace the item at the given index, as an instance of the
     *         generic type DataType. The item may not be null
     * @return The item, which has been replaced, as an instance of the generic type DataType. The
     * item may not be null
     */
    DataType replaceItem(int index, @NonNull DataType item);

    /**
     * Removes the item, which belongs to a specific index, from the adapter.
     *
     * @param index
     *         The index of the item, which should be removed from the adapter, as an {@link
     *         Integer} value. The index must be between 0 and the value of the method
     *         <code>getCount():int</code> - 1, otherwise an {@link IndexOutOfBoundsException} will
     *         be thrown
     * @return The item, which has been removed, as an instance of the generic type DataType or
     * null, if no item has been removed
     */
    DataType removeItem(int index);

    /**
     * Removes a specific item from the adapter.
     *
     * @param item
     *         The item, which should be removed, as an instance of the generic type DataType. The
     *         item may not be null
     * @return True, if the item has been removed, false otherwise
     */
    boolean removeItem(@NonNull DataType item);

    /**
     * Removes all items, which are contained by a specific collection, from the adapter.
     *
     * @param items
     *         The collection, which contains the items, which should be removed from the adapter,
     *         as an instance of the type {@link Collection} or an empty collection, if no items
     *         should be removed
     * @return True, if all items have been removed from the adapter, false otherwise
     */
    boolean removeAllItems(@NonNull Collection<? extends DataType> items);

    /**
     * Removes all items, which are contained by a specific array, from the adapter.
     *
     * @param items
     *         The array, which contains the items, which should be removed from the adapter, as an
     *         array of the generic type DataType or an empty array, if no items should be removed
     * @return True, if all items have been removed from the adapter, false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean removeAllItems(@NonNull DataType... items);

    /**
     * Removes all items from the adapter, except of the items, which are contained by a specific
     * collection.
     *
     * @param items
     *         The collection, which contains the items, which should be retained, as an instance of
     *         the type {@link Collection} or an empty collection, if no items should be retained
     */
    void retainAllItems(@NonNull Collection<? extends DataType> items);

    /**
     * Removes all items from the adapter, except of the items, which are contained by a specific
     * array.
     *
     * @param items
     *         The array, which contains the items, which should be retained, as an array of the
     *         generic type DataType or an empty array, if no items should be retained
     */
    @SuppressWarnings("unchecked")
    void retainAllItems(@NonNull DataType... items);

    /**
     * Removes all items from the adapter.
     */
    void clearItems();

    /**
     * Returns an iterator, which allows to iterate the adapter's items.
     *
     * @return An iterator, which allows to iterate the adapter's items, as an instance of the type
     * {@link Iterator}. The iterator may not be null
     */
    Iterator<DataType> iterator();

    /**
     * Returns a list iterator, which allows to iterate the adapter's items.
     *
     * @return A list iterator, which allows to iterate the adapter's items, as an instance of the
     * type {@link ListIterator}. The iterator may not be null
     */
    ListIterator<DataType> listIterator();

    /**
     * Returns a list iterator, which allows to iterate the adapter's items, starting at a specific
     * index.
     *
     * @param index
     *         The index, the iterator should start at, as an {@link Integer} value. The index must
     *         be between 0 and the value of the method <code>getCount():int</code> - 1, otherwise
     *         an {@link IndexOutOfBoundsException} will be thrown
     * @return A list iterator, which allows to iterate the adapter's items, starting at the given
     * index, as an instance of the type {@link ListIterator}. The iterator may not be null
     */
    ListIterator<DataType> listIterator(int index);

    /**
     * Returns a list, which contains the adapter's items between a specific start and end index.
     *
     * @param start
     *         The start index of the items, which should be returned, as an {@link Integer} value.
     *         The item, which belongs to the start index will be included. The index must be
     *         between 0 and the value of the method <code>getCount():int</code> - 1, otherwise an
     *         {@link IndexOutOfBoundsException} will be thrown
     * @param end
     *         The end index of the items, which should be returned, as an {@link Integer} value.
     *         The item, which belongs to the end index, will be excluded. The index must be between
     *         0 and the value of the method <code>getCount():int</code> -1 and it must be greater
     *         than the start index, otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @return A list, which contains the adapter's items, between a specific start end end index,
     * as an instance of the type {@link List} or an empty list, if the adapter does not contain any
     * items
     */
    List<DataType> subList(int start, int end);

    /**
     * Returns an array, which contains the adapter's items.
     *
     * @return An array, which contains the adapter's items, as an {@link Object} array or an empty
     * array, if the adapter does not contain any items
     */
    Object[] toArray();

    /**
     * Returns an array, which contains all of the adapter's items. If the given array is large
     * enough to hold the items, the specified array is used, otherwise an array of the same type is
     * created. If the given array can hold more items, the array's elements, following the
     * adapter's items, are set to null.
     *
     * @param <T>
     *         The type of the array, which should be returned
     * @param array
     *         The array, which should be used, if it is large enough, as an array of the generic
     *         type T. The array may not be null
     * @return An array, which contains all of the adapter's items, as an array of the generic type
     * T or an empty array, if the adapter does not contain any items
     */
    <T> T[] toArray(@NonNull T[] array);

    /**
     * Returns the item, which belongs to a specific index.
     *
     * @param index
     *         The index of the item, which should be returned, as an {@link Integer} value. The
     *         index must be between 0 and the value of the method <code>getCount():int</code> - 1,
     *         otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @return The item, which belongs to the given index, as an instance of the generic type
     * DataType. The item may not be null
     */
    DataType getItem(int index);

    /**
     * Returns the index of a specific item.
     *
     * @param item
     *         The item, whose index should be returned, as an instance of the generic type
     *         DataType. The item may not be null
     * @return The index of the the given item, as an {@link Integer} value or -1, if the adapter
     * does not contain the given item
     */
    int indexOf(@NonNull DataType item);

    /**
     * Returns the last index of a specific item.
     *
     * @param item
     *         The item, whose last index should be returned, as an instance of the generic type
     *         DataType. The item may not be null
     * @return The last index of the given item, as an {@link Integer} value or -1, if the adapter
     * does not contain the given item
     */
    int lastIndexOf(@NonNull DataType item);

    /**
     * Returns, whether the adapter contains a specific item, or not.
     *
     * @param item
     *         The item, whose presence should be checked, as an instance of the generic type
     *         DataType. The item may not be null
     * @return True, if the adapter contains the given item, false otherwise
     */
    boolean containsItem(@NonNull DataType item);

    /**
     * Returns, whether the adapter contains all items, which are contained by a specific
     * collection, or not.
     *
     * @param items
     *         The collection, which contains the items, whose presence should be checked, as an
     *         instance of the type {@link Collection}. The collection may not be null
     * @return True, if the adapter contains all items, which are contained by the given collection,
     * false otherwise
     */
    boolean containsAllItems(@NonNull Collection<? extends DataType> items);

    /**
     * Returns, whether the adapter contains all items, which are contained by a specific array, or
     * not.
     *
     * @param items
     *         The array, which contains the items, whose presence should be checked, as an array of
     *         the generic type DataType. The array may not be null
     * @return True, if the adapter contains all items, which are contained by the given array,
     * false otherwise
     */
    @SuppressWarnings("unchecked")
    boolean containsAllItems(@NonNull DataType... items);

    /**
     * Returns the number of items, which are contained by the adapter.
     *
     * @return The number of items, which are contained by the adapter, as an {@link Integer} value
     */
    int getCount();

    /**
     * Returns a list, which contains all of the adapter's items.
     *
     * @return A list, which contains all of the adapter's items, as an instance of the type {@link
     * List} or an empty list, if the adapter does not contain any items
     */
    List<DataType> getAllItems();

    /**
     * Returns, whether the adapter is empty, or not.
     *
     * @return True, if the adapter is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Adds a new listener, which should be notified, when the adapter's underlying data has been
     * modified.
     *
     * @param listener
     *         The listener, which should be added, as an instance of the type {@link
     *         ListAdapterListener}. The listener may not be null
     */
    void addAdapterListener(@NonNull ListAdapterListener<DataType> listener);

    /**
     * Removes a specific listener, which should not be notified, when the adapter's underlying data
     * has been modified, anymore.
     *
     * @param listener
     *         The listener, which should be removed, as an instance of the type {@link
     *         ListAdapterListener}. The listener may not be null
     */
    void removeAdapterListener(@NonNull ListAdapterListener<DataType> listener);

    /**
     * Adds a new listener, which should be notified, when an item of the adapter has been clicked
     * by the user.
     *
     * @param listener
     *         The listener, which should be added, as an instance of the type {@link
     *         ListAdapterItemClickListener}. The listener may not be null
     */
    void addItemClickListener(@NonNull ListAdapterItemClickListener<DataType> listener);

    /**
     * Removes a specific listener, which should not be notified, when an item of the adapter has
     * been clicked by the user, anymore.
     *
     * @param listener
     *         The listener, which should be removed, as an instance of the type {@link
     *         ListAdapterItemClickListener}. The listener may not be null
     */
    void removeItemClickListener(@NonNull ListAdapterItemClickListener<DataType> listener);

    /**
     * Adds a new listener, which should be notified, when an item of the adapter has been
     * long-clicked by the user.
     *
     * @param listener
     *         The listener, which should be added, as an instance of the type {@link
     *         ListAdapterItemLongClickListener}. The listener may not be null
     */
    void addItemLongClickListener(@NonNull ListAdapterItemLongClickListener<DataType> listener);

    /**
     * Removes a specific listener, which should not be notified, when an item of the adapter has
     * been long-clicked by the user, anymore.
     *
     * @param listener
     *         The listener, which should be removed, as an instance of the type {@link
     *         ListAdapterItemLongClickListener}. The listener may not be null
     */
    void removeItemLongClickListener(@NonNull ListAdapterItemLongClickListener<DataType> listener);

    @Override
    ListAdapter<DataType> clone() throws CloneNotSupportedException;

}