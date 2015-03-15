/*
 * AndroidAdapters Copyright 2014 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>. 
 */
package de.mrapp.android.adapter;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import android.widget.GridView;
import android.widget.ListView;
import de.mrapp.android.adapter.datastructure.DataStructure;
import de.mrapp.android.adapter.datastructure.Parameterizable;
import de.mrapp.android.adapter.datastructure.Restorable;
import de.mrapp.android.adapter.list.ListAdapterItemClickListener;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.EnableStateListAdapter;
import de.mrapp.android.adapter.list.filterable.FilterableListAdapter;
import de.mrapp.android.adapter.list.itemstate.ItemStateListAdapter;
import de.mrapp.android.adapter.list.sortable.SortableListAdapter;
import de.mrapp.android.adapter.logging.Loggable;

/**
 * Defines the interface, an adapter, whose underlying data is managed as a list
 * of arbitrary items, must implement. Such an adapter's purpose is to provide
 * the underlying data for visualization using a {@link ListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface ListAdapter<DataType> extends DataStructure, Restorable,
		Loggable, Parameterizable, android.widget.ListAdapter,
		EnableStateListAdapter<DataType>, ItemStateListAdapter<DataType>,
		SortableListAdapter<DataType>, FilterableListAdapter<DataType> {

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
	 *            True, if duplicate items should be allowed, false otherwise
	 */
	void allowDuplicates(boolean allowDuplicates);

	/**
	 * Returns, whether the method <code>notifyDataSetChanged():void</code> is
	 * automatically called when the adapter's underlying data has been changed,
	 * or not.
	 * 
	 * @return True, if the method <code>notifyDataSetChanged():void</code> is
	 *         automatically called when the adapter's underlying data has been
	 *         changed, false otherwise
	 */
	boolean isNotifiedOnChange();

	/**
	 * Sets, whether the method <code>notifyDataSetChanged():void</code> should
	 * be automatically called when the adapter's underlying data has been
	 * changed, or not.
	 * 
	 * @param notifyOnChange
	 *            True, if the method <code>notifyDataSetChanged():void</code>
	 *            should be automatically called when the adapter's underlying
	 *            data has been changed, false otherwise
	 */
	void notifyOnChange(boolean notifyOnChange);

	/**
	 * Adds a specific item to the adapter. The item will be added at the end.
	 * 
	 * @param item
	 *            The item, which should be added to the adapter, as an instance
	 *            of the generic type DataType. The item may not be null
	 * @return True, if the item has been added to the adapter, false otherwise
	 */
	boolean addItem(DataType item);

	/**
	 * Adds a specific item to the adapter. The item will be added at a specific
	 * index.
	 * 
	 * @param index
	 *            The index, the item should be added at, as an {@link Integer}
	 *            value. The index must be between 0 and the value of the method
	 *            <code>getNumberOfItems():int</code>, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param item
	 *            The item, which should be added to the adapter, as an instance
	 *            of the generic type DataType. The item may not be null
	 * @return True, if the item has been added to the adapter, false otherwise
	 */
	boolean addItem(int index, DataType item);

	/**
	 * Adds all items, which are contained by a specific collection, to the
	 * adapter. The items will be added in a consecutive order at the end.
	 * 
	 * @param items
	 *            The collection, which contains the items, which should be
	 *            added to the adapter, as an instance of the type
	 *            {@link Collection} or an empty collection, if no items should
	 *            be added
	 * @return True, if all items have been added to the adapter, false
	 *         otherwise
	 */
	boolean addAllItems(Collection<DataType> items);

	/**
	 * Adds all items, which are contained by a specific collection, to the
	 * adapter. The items will be added in a consecutive order at a specific
	 * index.
	 * 
	 * @param index
	 *            The index, the items should be added at, as an {@link Integer}
	 *            value. The index must be between 0 and the value of the method
	 *            <code>getNumberOfItems():int</code>, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param items
	 *            The collection, which contains the items, which should be
	 *            added to the adapter, as an instance of the type
	 *            {@link Collection} or an empty collection, if no items should
	 *            be added
	 * @return True, if all items have been added to the adapter, false
	 *         otherwise
	 */
	boolean addAllItems(int index, Collection<DataType> items);

	/**
	 * Adds all items, which are contained by a specific array, to the adapter.
	 * The items will be added in a consecutive order at the end.
	 * 
	 * @param items
	 *            The array, which contains the items, which should be added to
	 *            the adapter, as an array of the generic type DataType or an
	 *            empty array, if no items should be added
	 * @return True, if all items have been added to the adapter, false
	 *         otherwise
	 */
	boolean addAllItems(DataType... items);

	/**
	 * Adds all items, which are contained by a specific array, to the adapter.
	 * The items will be added in a consecutive order at a specific index.
	 * 
	 * @param index
	 *            The index, the items should be added at, as an {@link Integer}
	 *            value. The index must be between 0 and the value of the method
	 *            <code>getNumberOfItems():int</code>, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @param items
	 *            The array, which contains the items, which should be added to
	 *            the adapter, as an array of the generic type DataType or an
	 *            empty array, if no items should be added
	 * @return True, if all items have been added to the adapter, false
	 *         otherwise
	 */
	boolean addAllItems(int index, DataType... items);

	/**
	 * Replaces the item, which belongs to a specific index, by an other item.
	 * 
	 * @param index
	 *            The index of the item, which should be replaced, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param item
	 *            The item, which should replace the item at the given index, as
	 *            an instance of the generic type DataType. The item may not be
	 *            null
	 * @return The item, which has been replaced, as an instance of the generic
	 *         type DataType. The item may not be null
	 */
	DataType replaceItem(int index, DataType item);

	/**
	 * Removes the item, which belongs to a specific index, from the adapter.
	 * 
	 * @param index
	 *            The index of the item, which should be removed from the
	 *            adapter, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method
	 *            <code>getNumberOfItems():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return The item, which has been removed, as an instance of the generic
	 *         type DataType or null, if no item has been removed
	 */
	DataType removeItem(int index);

	/**
	 * Removes a specific item from the adapter.
	 * 
	 * @param item
	 *            The item, which should be removed, as an instance of the
	 *            generic type DataType. The item may not be null
	 * @return True, if the item has been removed, false otherwise
	 */
	boolean removeItem(DataType item);

	/**
	 * Removes all items, which are contained by a specific collection, from the
	 * adapter.
	 * 
	 * @param items
	 *            The collection, which contains the items, which should be
	 *            removed from the adapter, as an instance of the type
	 *            {@link Collection} or an empty collection, if no items should
	 *            be removed
	 * @return True, if all items have been removed from the adapter, false
	 *         otherwise
	 */
	boolean removeAllItems(Collection<DataType> items);

	/**
	 * Removes all items, which are contained by a specific array, from the
	 * adapter.
	 * 
	 * @param items
	 *            The array, which contains the items, which should be removed
	 *            from the adapter, as an array of the generic type DataType or
	 *            an empty array, if no items should be removed
	 * @return True, if all items have been removed from the adapter, false
	 *         otherwise
	 */
	boolean removeAllItems(DataType... items);

	/**
	 * Removes all items from the adapter, except of the items, which are
	 * contained by a specific collection.
	 * 
	 * @param items
	 *            The collection, which contains the items, which should be
	 *            retained, as an instance of the type {@link Collection} or an
	 *            empty collection, if no items should be retained
	 */
	void retainAllItems(Collection<DataType> items);

	/**
	 * Removes all items from the adapter, except of the items, which are
	 * contained by a specific array.
	 * 
	 * @param items
	 *            The array, which contains the items, which should be retained,
	 *            as an array of the generic type DataType or an empty array, if
	 *            no items should be retained
	 */
	void retainAllItems(DataType... items);

	/**
	 * Removes all items from the adapter.
	 */
	void clearItems();

	/**
	 * Returns an iterator, which allows to iterate the adapter's items.
	 * 
	 * @return An iterator, which allows to iterate the adapter's items, as an
	 *         instance of the type {@link Iterator}. The iterator may not be
	 *         null
	 */
	Iterator<DataType> iterator();

	/**
	 * Returns a list iterator, which allows to iterate the adapter's items.
	 * 
	 * @return A list iterator, which allows to iterate the adapter's items, as
	 *         an instance of the type {@link ListIterator}. The iterator may
	 *         not be null
	 */
	ListIterator<DataType> listIterator();

	/**
	 * Returns a list iterator, which allows to iterate the adapter's items,
	 * starting at a specific index.
	 * 
	 * @param index
	 *            The index, the iterator should start at, as an {@link Integer}
	 *            value. The index must be between 0 and the value of the method
	 *            <code>getNumberOfItems():int</code> - 1, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return A list iterator, which allows to iterate the adapter's items,
	 *         starting at the given index, as an instance of the type
	 *         {@link ListIterator}. The iterator may not be null
	 */
	ListIterator<DataType> listIterator(int index);

	/**
	 * Returns a collection, which contains the adapter's items between a
	 * specific start and end index.
	 * 
	 * @param start
	 *            The start index of the items, which should be returned, as an
	 *            {@link Integer} value. The item, which belongs to the start
	 *            index will be included. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @param end
	 *            The end index of the items, which should be returned, as an
	 *            {@link Integer} value. The item, which belongs to the end
	 *            index, will be excluded. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> -1 and
	 *            it must be greater than the start index, otherwise an
	 *            {@link IndexOutOfBoundsException} will be thrown
	 * @return A collection, which contains the adapter's items, between a
	 *         specific start end end index, as an instance of the type
	 *         {@link Collection} or an empty collection, if the adapter does
	 *         not contain any items
	 */
	Collection<DataType> subList(int start, int end);

	/**
	 * Returns an array, which contains the adapter's items.
	 * 
	 * @return An array, which contains the adapter's items, as an
	 *         {@link Object} array or an empty array, if the adapter does not
	 *         contain any items
	 */
	Object[] toArray();

	/**
	 * Returns an array, which contains all of the adapter's items. If the given
	 * array is large enough to hold the items, the specified array is used,
	 * otherwise an array of the same type is created. If the given array can
	 * hold more items, the array's elements, following the adapter's items, are
	 * set to null.
	 * 
	 * @param <T>
	 *            The type of the array, which should be returned
	 * @param array
	 *            The array, which should be used, if it is large enough, as an
	 *            array of the generic type T. The array may not be null
	 * @return An array, which contains all of the adapter's items, as an array
	 *         of the generic type T or an empty array, if the adapter does not
	 *         contain any items
	 */
	<T> T[] toArray(T[] array);

	/**
	 * Returns the item, which belongs to a specific index.
	 * 
	 * @param index
	 *            The index of the item, which should be returned, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1,
	 *            otherwise an {@link IndexOutOfBoundsException} will be thrown
	 * @return The item, which belongs to the given index, as an instance of the
	 *         generic type DataType. The item may not be null
	 */
	DataType getItem(int index);

	/**
	 * Returns the index of a specific item.
	 * 
	 * @param item
	 *            The item, whose index should be returned, as an instance of
	 *            the generic type DataType. The item may not be null
	 * @return The index of the the given item, as an {@link Integer} value or
	 *         -1, if the adapter does not contain the given item
	 */
	int indexOf(DataType item);

	/**
	 * Returns the last index of a specific item.
	 * 
	 * @param item
	 *            The item, whose last index should be returned, as an instance
	 *            of the generic type DataType. The item may not be null
	 * @return The last index of the given item, as an {@link Integer} value or
	 *         -1, if the adapter does not contain the given item
	 */
	int lastIndexOf(DataType item);

	/**
	 * Returns, whether the adapter contains a specific item, or not.
	 * 
	 * @param item
	 *            The item, whose presence should be checked, as an instance of
	 *            the generic type DataType. The item may not be null
	 * @return True, if the adapter contains the given item, false otherwise
	 */
	boolean containsItem(DataType item);

	/**
	 * Returns, whether the adapter contains all items, which are contained by a
	 * specific collection, or not.
	 * 
	 * @param items
	 *            The collection, which contains the items, whose presence
	 *            should be checked, as an instance of the type
	 *            {@link Collection}. The collection may not be null
	 * @return True, if the adapter contains all items, which are contained by
	 *         the given collection, false otherwise
	 */
	boolean containsAllItems(Collection<DataType> items);

	/**
	 * Returns, whether the adapter contains all items, which are contained by a
	 * specific array, or not.
	 * 
	 * @param items
	 *            The array, which contains the items, whose presence should be
	 *            checked, as an array of the generic type DataType. The array
	 *            may not be null
	 * @return True, if the adapter contains all items, which are contained by
	 *         the given array, false otherwise
	 */
	boolean containsAllItems(DataType... items);

	/**
	 * Returns the number of items, which are contained by the adapter.
	 * 
	 * @return The number of items, which are contained by the adapter, as an
	 *         {@link Integer} value
	 */
	int getNumberOfItems();

	/**
	 * Returns a collection, which contains all of the adapter's items.
	 * 
	 * @return A collection, which contains all of the adapter's items, as an
	 *         instance of the type {@link Collection} or an empty collection,
	 *         if the adapter does not contain any items
	 */
	Collection<DataType> getAllItems();

	/**
	 * Returns, whether the adapter is empty, or not.
	 * 
	 * @return True, if the adapter is empty, false otherwise
	 */
	boolean isEmpty();

	/**
	 * Attaches the adapter to a list view.
	 * 
	 * @param listView
	 *            The list view, the adapter should be attached to, as an
	 *            instance of the class {@link ListView}. The list view may not
	 *            be null
	 */
	void attach(final ListView listView);

	/**
	 * Attaches the adapter to a grid view.
	 * 
	 * @param gridView
	 *            The grid view, the adapter should be attached to, as an
	 *            instance of the class {@link GridView}. The grid view may not
	 *            be null
	 */
	void attach(final GridView gridView);

	/**
	 * Detaches the adapter from the view, it is currently attached to.
	 */
	void detach();

	/**
	 * Adds a new listener, which should be notified, when an item of the
	 * adapter has been clicked by the user.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            type {@link ListAdapterItemClickListener}. The listener may
	 *            not be null
	 */
	void addItemClickListener(ListAdapterItemClickListener<DataType> listener);

	/**
	 * Removes a specific listener, which should nmot be notified, when an item
	 * of the adapter has been clicked by the user, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            type {@link ListAdapterItemClickListener}. The listener may
	 *            not be null
	 */
	void removeItemClickListener(ListAdapterItemClickListener<DataType> listener);

	/**
	 * Adds a new listener, which should be notified, when the adapter's
	 * underlying data has been modified.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            type {@link ListAdapterListener}. The listener may not be null
	 */
	void addAdapterListener(ListAdapterListener<DataType> listener);

	/**
	 * Removes a specific listener, which should not be notified, when the
	 * adapter's underlying data has been modified, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            type {@link ListAdapterListener}. The listener may not be null
	 */
	void removeAdapterListener(ListAdapterListener<DataType> listener);

	@Override
	ListAdapter<DataType> clone() throws CloneNotSupportedException;

}