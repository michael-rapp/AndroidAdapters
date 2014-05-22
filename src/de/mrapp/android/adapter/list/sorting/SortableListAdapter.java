package de.mrapp.android.adapter.list.sorting;

import java.util.Comparator;

import de.mrapp.android.adapter.Order;

public interface SortableListAdapter<DataType> {

	/**
	 * Sorts the items of the adapter in an ascending order.
	 */
	void sort();

	/**
	 * Sorts the items of the adapter in a specific order.
	 * 
	 * @param order
	 *            The order, which should be used to sort the items, as a value
	 *            of the enum {@link Order}. The order may either be
	 *            <code>ASCENDING</code> order <code>DESCENDING</code>
	 */
	void sort(Order order);

	/**
	 * Sorts the items of the adapter by using a comparator in an ascending
	 * order.
	 * 
	 * @param comparator
	 *            The comparable, which should be used to sort the items, as an
	 *            instance of the type {@link Comparator}. The comparator may
	 *            not be null
	 */
	void sort(Comparator<DataType> comparator);

	/**
	 * Sorts the entries of the adapter by using a comparator in a specific
	 * order.
	 * 
	 * @param comparator
	 *            The comparable, which should be used to sort the entries, as
	 *            an instance of the type {@link Comparator}. The comparator may
	 *            not be null
	 * @param order
	 *            The order, which should be used to sort the entries, as a
	 *            value of the enum {@link Order}. The order may either be
	 *            <code>ASCENDING</code> order <code>DESCENDING</code>
	 */
	void sort(Order order, Comparator<DataType> comparator);

	/**
	 * Adds a new listener, which should be notified when the adapter's
	 * underlying data has been sorted.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            class {@link ListSortingListener}. The listener may not be
	 *            null
	 */
	void addSortingListner(final ListSortingListener<DataType> listener);

	/**
	 * Removes a specific listener, which should not be notified when the
	 * adapter's underlying data has been modified, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            class {@link ListSortingListener}. The listener may not be
	 *            null
	 */
	void removeSortingListener(ListSortingListener<DataType> listener);

}