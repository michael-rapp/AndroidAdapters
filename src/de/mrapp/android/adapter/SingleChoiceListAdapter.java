package de.mrapp.android.adapter;

public interface SingleChoiceListAdapter<DataType> extends
		ListAdapter<DataType> {

	/**
	 * Returns the currently selected index.
	 * 
	 * @return The currently selected index, as an {@link Integer} value or -1,
	 *         if the adapter does not contain any items. The index must be
	 *         between 0 and the value of the method
	 *         <code>getNumberOfItems():int</code> - 1
	 */
	int getSelectedIndex();

	/**
	 * Returns the currently selected item.
	 * 
	 * @return The currently selected item, as an instance of the generic type
	 *         DataType or null, if the adapter does not contain any items
	 */
	DataType getSelectedItem();

	/**
	 * Selects the item, which belongs to a specific index. This causes any
	 * other selected item to become unselected.
	 * 
	 * @param index
	 *            The index of the item, which should be selected, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1
	 */
	void select(int index);

	/**
	 * Selects a specific item. This causes any other selected item to become
	 * unselected.
	 * 
	 * @param item
	 *            The item, which should be selected, as an instance of the
	 *            generic type DataType. The item may not be null
	 */
	void select(DataType item);

}