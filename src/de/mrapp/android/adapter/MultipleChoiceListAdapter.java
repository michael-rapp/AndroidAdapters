package de.mrapp.android.adapter;

import java.util.List;

public interface MultipleChoiceListAdapter<DataType> {

	/**
	 * Returns, whether the item, which belongs to a specific index, is
	 * unselected, or not.
	 * 
	 * @param index
	 *            The index of the item, whose selection state should be
	 *            returned, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method
	 *            <code>getNumberOfItems():int</code> - 1
	 * @return True, if the item, which belongs to the given index, is
	 *         unselected, false otherwise
	 */
	boolean isUnselected(int index);

	/**
	 * Returns, whether a specific item is unselected, or not.
	 * 
	 * @param item
	 *            The item, whose selection state should be returned, as an
	 *            instance of the generic type DataType. The item may not be
	 *            null
	 * @return True, if the given item is unselected, false otherwise
	 */
	boolean isUnselected(DataType item);

	/**
	 * Return the index of the first selected item.
	 * 
	 * @return The index of the first selected item or -1, if no item is
	 *         selected. The index must be between 0 and the value of the method
	 *         <code>getNumberOfItems():int</code> - 1
	 */
	int getFirstSelectedIndex();

	/**
	 * Returns the first selected item.
	 * 
	 * @return The first selected item, as an instance of the generic type
	 *         DataType or null, if no item is selected
	 */
	DataType getFirstSelectedItem();

	/**
	 * Return the index of the last selected item.
	 * 
	 * @return The index of the last selected item or -1, if no item is
	 *         selected. The index must be between 0 and the value of the method
	 *         <code>getNumberOfItems():int</code> - 1
	 */
	int getLastSelectedIndex();

	/**
	 * Returns the last selected item.
	 * 
	 * @return The last selected item, as an instance of the generic type
	 *         DataType or null, if no item is selected
	 */
	DataType getLastSelectedItem();

	/**
	 * Return the index of the first unselected item.
	 * 
	 * @return The index of the first unselected item or -1, if no item is
	 *         unselected. The index must be between 0 and the value of the
	 *         method <code>getNumberOfItems():int</code> - 1
	 */
	int getFirstUnselectedIndex();

	/**
	 * Returns the first unselected item.
	 * 
	 * @return The first unselected item, as an instance of the generic type
	 *         DataType or null, if no item is unselected
	 */
	DataType getFirstUnselectedItem();

	/**
	 * Return the index of the last unselected item.
	 * 
	 * @return The index of the last unselected item or -1, if no item is
	 *         unselected. The index must be between 0 and the value of the
	 *         method <code>getNumberOfItems():int</code> - 1
	 */
	int getLastUnselectedIndex();

	/**
	 * Returns the last unselected item.
	 * 
	 * @return The last unselected item, as an instance of the generic type
	 *         DataType or null, if no item is unselected
	 */
	DataType getLastUnselectedItem();

	/**
	 * Returns a list, which contains the indices of all selected items.
	 * 
	 * @return A list, which contains the indices of all selected items, as an
	 *         instance of the type {@link List} or an empty list, if no item is
	 *         selected
	 */
	List<Integer> getSelectedIndices();

	/**
	 * Returns a list, which contains all selected items.
	 * 
	 * @return A list, which contains all selected items, as an instance of the
	 *         type {@link List} or an empty list, if no item is selected
	 */
	List<DataType> getSelectedItems();

	/**
	 * Returns a list, which contains the indices of all unselected items.
	 * 
	 * @return A list, which contains the indices of all unselected items, as an
	 *         instance of the type {@link List} or an empty list, if no item is
	 *         selected
	 */
	List<Integer> getUnselectedIndices();

	/**
	 * Returns a list, which contains all unselected items.
	 * 
	 * @return A list, which contains all unselected items, as an instance of
	 *         the type {@link List} or an empty list, if no item is selected
	 */
	List<DataType> getUnselectedItems();

	/**
	 * Returns the number of selected items.
	 * 
	 * @return The number of selected items, as an {@link Integer} value
	 */
	int getNumberOfSelectedItems();

	/**
	 * Returns the number unselected items.
	 * 
	 * @return The number of unselected items, as an {@link Integer} value
	 */
	int getNumberOfUnselectedItems();

	/**
	 * Selects the item, which belongs to a specific index.
	 * 
	 * @param index
	 *            The index of the item, which should be selected, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1
	 */
	void select(int index);

	/**
	 * Selects a specific item.
	 * 
	 * @param item
	 *            The item, which should be selected, as an instance of the
	 *            generic type DataType. The item may not be null
	 */
	void select(DataType item);

	/**
	 * Unselects the item, which belongs to a specific index.
	 * 
	 * @param index
	 *            The index of the item, which should be unselected, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1
	 */
	void unselect(int index);

	/**
	 * Unselects a specific item.
	 * 
	 * @param item
	 *            The item, which should be unselected, as an instance of the
	 *            generic type DataType. The item may not be null
	 */
	void unselect(DataType item);

	/**
	 * Triggers the selection of the item, which belongs to a specific index.
	 * This causes the item to become unselected, if it is selected and vice
	 * versa.
	 * 
	 * @param index
	 *            The index of the item, whose selection should be triggered, as
	 *            an {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1
	 */
	void triggerSelection(int index);

	/**
	 * Triggers the selection of a specific item. This causes the item to become
	 * unselected, if it is selected and vice versa.
	 * 
	 * @param item
	 *            The item, whose selection should be triggered, as an instance
	 *            of the generic type DataType. The item may not be null
	 */
	void triggerSelection(DataType item);

	/**
	 * Selects all items.
	 */
	void selectAll();

	/**
	 * Unselects all items.
	 */
	void unselectAll();

	/**
	 * Triggers the selections of all items. This causes an item to become
	 * unselected, if it is selected and vice versa.
	 */
	void triggerAllSelections();

}