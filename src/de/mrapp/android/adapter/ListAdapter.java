package de.mrapp.android.adapter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

interface ListAdapter<DataType> extends Adapter {

	/**
	 * Adds a specific item to the end of the adapter.
	 * 
	 * @param item
	 *            The item, which should be added, as an instance of the generic
	 *            type DataType. The item may not be null
	 */
	void addItem(DataType item);

	/**
	 * Adds a specific item to the adapter at a specific index.
	 * 
	 * @param index
	 *            The index, the item should be added at, as an;@link Integer}
	 *            value. The index must be between 0 and the value of the method
	 *            <code>getNumberOfItems():int</code> - 1
	 * @param item
	 *            The item, which should be added, as an instance of the generic
	 *            type DataType. The item may not be null
	 */
	void addItem(int index, DataType item);

	/**
	 * Adds all items, which are contained by a specific collection, to the
	 * adapter.
	 * 
	 * @param items
	 *            The collection, which contains the items, which should be
	 *            added to the adapter, as an instance of the type ;@link
	 *            Collection} or an empty collection, if no items should be
	 *            added
	 */
	void addAllItems(Collection<DataType> items);

	/**
	 * Adds all items, which are contained by a specific collection, to the
	 * adapter, beginning at a specific index.
	 * 
	 * @param index
	 *            The index, the items should be added at, as an;@link Integer}
	 *            value. The index must be between 0 and the value of the method
	 *            <code>getNumberOfItems():int</code> - 1
	 * @param items
	 *            The collection, which contains the items, which should be
	 *            added to the adapter, as an instance of the type ;@link
	 *            Collection} or an empty collection, if no items should be
	 *            added
	 */
	void addAllItems(int index, Collection<DataType> items);

	/**
	 * Replaces the item, which belongs to a specific index, by an other item.
	 * 
	 * @param index
	 *            The index of the item, which should be replaced, as an ;@link
	 *            Integer} value. The index must be between 0 and the value of
	 *            the method <code>getNumberOfItems():int</code> - 1
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
	 *            adapter, as an;@link Integer} value. The index must be between
	 *            0 and the value of the method
	 *            <code>getNumberOfItems():int</code> - 1
	 * @return The item, which has been removed, as an instance of the generic
	 *         type DataType. The item may not be null
	 */
	DataType removeItem(int index);

	/**
	 * Removes a specific item from the adapter.
	 * 
	 * @param item
	 *            The item, which should be removed, as an instance of the
	 *            generic type DataType. The item may not be null
	 */
	void removeItem(DataType item);

	/**
	 * Removes all items, which are contained by a specific collection, from the
	 * adapter.
	 * 
	 * @param items
	 *            The collection, which contains the items, which should be
	 *            removed from the adapter, as an instance of the type ;@link
	 *            Collection} or an empty collection, if no items should be
	 *            removed
	 */
	void removeAllItems(Collection<DataType> items);

	/**
	 * Removes all items from the adapter, except of the items, which are
	 * contained by a specific collection.
	 * 
	 * @param items
	 *            The collection, which contains the items, which should be
	 *            retained, as an instance of the type;@link Collection} or an
	 *            empty collection, if no items should be retained
	 */
	void retainAllItems(Collection<DataType> items);

	/**
	 * Removes all items from the adapter.
	 */
	void clearItems();

	/**
	 * Returns an iterator, which allows to iterate over the adapter's items.
	 * 
	 * @return An iterator, which allows to iterate over the adapter's items, as
	 *         an instance of the type;@link Iterator}
	 */
	Iterator<DataType> iterator();

	/**
	 * Returns a list iterator, which allows to iterate over the adapter's
	 * items.
	 * 
	 * @return A list iterator, which allows to iterate over the adapter's
	 *         items, as an instance of the type;@link ListIterator}. The
	 *         iterator may not be null
	 */
	ListIterator<DataType> listIterator();

	/**
	 * Returns a list iterator, which allows to iterate over the adapter's
	 * items, starting at a specific index.
	 * 
	 * @param index
	 *            The index, the iterator should start at, as an;@link Integer}
	 *            value. The index must be between 0 and the value of the method
	 *            <code>getNumberOfItems():int</code> - 1
	 * @return A list iterator, which allows to iterate over the adapter's
	 *         items, starting at the given index, as an instance of the type
	 *         ;@link ListIterator}. The iterator may not be null
	 */
	ListIterator<DataType> listIterator(int index);

	/**
	 * Returns a list, which contains the adapter's items, between a specific
	 * start and end index.
	 * 
	 * @param start
	 *            The start index of the items, which should be returned, as an
	 *            ;@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1
	 * @param end
	 *            The end index of the items, which should be returned, as an
	 *            ;@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> -1 and
	 *            it must be greater than the start index
	 * @return A list, which contains the adapter's items, between a specific
	 *         start end end index, as an instance of the type;@link List} or an
	 *         empty list, if the adapter does not contain any items
	 */
	List<DataType> subList(int start, int end);

	/**
	 * Returns an array, which contains the adapter's items.
	 * 
	 * @return An array, which contains the adapter's items, as an ;@link
	 *         Object} array or an empty array, if the adapter does not contain
	 *         any items
	 */
	Object[] toArray();

	/**
	 * Returns the item, which belongs to a specific index.
	 * 
	 * @param index
	 *            The index of the item, which should be returned, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1
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
	 * @return The index of the the given item, as an;@link Integer} value or
	 *         -1, if the adapter does not contain the given adapter. The index
	 *         must be between 0 and the value of the method
	 *         <code>getNumberOfItems():int</code> - 1
	 */
	int indexOf(DataType item);

	/**
	 * Returns the last index of a specific item.
	 * 
	 * @param item
	 *            The item, whose last index should be returned, as an instance
	 *            of the generic type DataType. The item may not be null
	 * @return The last index of the given item, as an;@link Integer} value or
	 *         -1, if the adapter does not contain the given item. The index
	 *         must be between 0 and the value of the method
	 *         <code>getNumberOfItems():int</code> - 1
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
	 *            The collection, which contains the items, which whose presence
	 *            should be checked, as an instance of the type ;@link
	 *            Collection}. The collection may not be null
	 * @return True, if the adapter contains all items, which are contained by
	 *         the given collection, false otherwise
	 */
	boolean containsAllItems(Collection<DataType> items);

	/**
	 * Returns the number of the adapter's items.
	 * 
	 * @return The number of the adapter's items, as an;@link Integer} value
	 */
	int getNumberOfItems();

	/**
	 * Returns a list, which contains the adapter's items.
	 * 
	 * @return A list, which contains the adapter's items, as an instance of the
	 *         type;@link List} or an empty list, if the adapter does not
	 *         contain any items
	 */
	List<DataType> getAllItems();

	/**
	 * Returns, whether the adapter does contain any items, or not.
	 * 
	 * @return True, if the adapter does contain any items, false otherwise
	 */
	boolean isEmpty();

}