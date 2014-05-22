package de.mrapp.android.adapter.list.selectable;

public interface SelectableListAdapter<DataType> {

	/**
	 * Adds a new listener, which should be notified when the selection of an
	 * item has been changed.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            type {@link ListSelectionListener}. The listener may not be
	 *            null
	 */
	void addSelectionListener(ListSelectionListener<DataType> listener);

	/**
	 * Removes a specific listener, which should not be notified when the
	 * selection of an item has been changed, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            type {@link ListSelectionListener}. The listener may not be
	 *            null
	 */
	void removeSelectionListener(ListSelectionListener<DataType> listener);

	/**
	 * Returns, whether the item, which belongs to a specific index, is
	 * selected, or not.
	 * 
	 * @param index
	 *            The index of the item, whose selection state should be
	 *            returned, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method
	 *            <code>getNumberOfItems():int</code> - 1
	 * @return True, if the item, which belongs to the given index, is selected,
	 *         false otherwise
	 */
	boolean isSelected(int index);

	/**
	 * Returns, whether a specific item is selected, or not.
	 * 
	 * @param item
	 *            The item, whose selection state should be returned, as an
	 *            instance of the generic type DataType. The item may not be
	 *            null <code>getNumberOfItems():int</code> - 1
	 * @return True, if the item, which belongs to the given index, is selected,
	 *         false otherwise
	 */
	boolean isSelected(DataType item);

}