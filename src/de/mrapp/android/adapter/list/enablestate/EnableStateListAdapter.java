package de.mrapp.android.adapter.list.enablestate;

import java.util.List;

public interface EnableStateListAdapter<DataType> {

	/**
	 * Returns, whether the item, which belongs to a specific index, is enabled,
	 * or not.
	 * 
	 * @param index
	 *            The index of the item, whose enable state should be checked,
	 *            as an;@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>size():int</code> - 1
	 * @return True, if the item, which belongs to the given index, is enabled,
	 *         false otherwise
	 */
	boolean isEnabled(int index);

	/**
	 * Returns, whether a specific item is enabled, or not.
	 * 
	 * @param item
	 *            The item, whose enable state should be checked, as an instance
	 *            of the generic type DataType. The item may not be null
	 * @return True, if the given item is enabled, false otherwise
	 */
	boolean isEnabled(DataType item);

	/**
	 * Returns, whether the item, which belongs to a specific index, is
	 * disabled, or not.
	 * 
	 * @param index
	 *            The index of the item, whose enable state should be checked,
	 *            as an;@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>size():int</code> - 1
	 * @return True, if the item, which belongs to the given index, is disabled,
	 *         false otherwise
	 */
	boolean isDisabled(int index);

	/**
	 * Returns, whether a specific item is disabled, or not.
	 * 
	 * @param item
	 *            The item, whose enable state should be checked, as an instance
	 *            of the generic type DataType. The item may not be null
	 * @return True, if the given item is disabled, false otherwise
	 */
	boolean isDisabled(DataType item);

	/**
	 * Returns the index of the first enabled item.
	 * 
	 * @return The index of the first enabled item, as an;@link Integer} value
	 *         or -1, if no item is enabled. The index must be between 0 and the
	 *         value of the method <code>size():int</code> - 1
	 */
	int getFirstEnabledIndex();

	/**
	 * Returns the first enabled item.
	 * 
	 * @return The first enabled item, as an instance of the generic type
	 *         DataType or null, if no item is enabled
	 */
	DataType getFirstEnabledItem();

	/**
	 * Returns the index of the last enabled item.
	 * 
	 * @return The index of the last enabled item, as an;@link Integer} value or
	 *         -1, if no item is enabled. The index must be between 0 and the
	 *         value of the method <code>size():int</code> - 1
	 */
	int getLastEnabledIndex();

	/**
	 * Returns the last enabled item.
	 * 
	 * @return The last enabled item, as an instance of the generic type
	 *         DataType or null, if no item is enabled
	 */
	DataType getLastEnabledItem();

	/**
	 * Returns the index of the first disabled item.
	 * 
	 * @return The index of the first disabled item, as an;@link Integer} value
	 *         or -1, if no item is disabled. The index must be between 0 and
	 *         the value of the method <code>size():int</code> - 1
	 */
	int getFirstDisabledIndex();

	/**
	 * Returns the first disabled item.
	 * 
	 * @return The first disabled item, as an instance of the generic type
	 *         DataType or null, if no item is disabled
	 */
	DataType getFirstDisabledItem();

	/**
	 * Returns the index of the last disabled item.
	 * 
	 * @return The index of the last disabled item, as an;@link Integer} value
	 *         or -1, if no item is disabled. The index must be between 0 and
	 *         the value of the method <code>size():int</code> - 1
	 */
	int getLastDisabledIndex();

	/**
	 * Returns the index of the last disabled item.
	 * 
	 * @return The index of the last disabled item, as an;@link Integer} value
	 *         or -1, if no item is disabled. The index must be between 0 and
	 *         the value of the method <code>size():int</code> - 1
	 */
	DataType getLastDisabledItem();

	/**
	 * Returns a list, which contains the indices of all enabled items.
	 * 
	 * @return A list, which contains the indices of all enabled items, as an
	 *         instance of the type;@link List} or an empty list, if no item is
	 *         enabled
	 */
	List<Integer> getEnabledIndices();

	/**
	 * Returns a list, which contains all enabled items.
	 * 
	 * @return A list, which contains all enabled items, as an instance of the
	 *         type;@link List} or an empty list, if no item is enabled
	 */
	List<DataType> getEnabledItems();

	/**
	 * Returns a list, which contains the indices of all disabled items.
	 * 
	 * @return A list, which contains the indices of all disabled items, as an
	 *         instance of the type;@link List} or an empty list, if no item is
	 *         disabled
	 */
	List<Integer> getDisabledIndices();

	/**
	 * Returns a list, which contains all disabled items.
	 * 
	 * @return A list, which contains all disabled items, as an instance of the
	 *         type;@link List} or an empty list, if no item is disabled
	 */
	List<DataType> getDisabledItems();

	/**
	 * Returns the number of enabled items.
	 * 
	 * @return The number of enabled items, as an;@link Integer} value
	 */
	int getNumberOfEnabledItems();

	/**
	 * Returns the number of disabled items.
	 * 
	 * @return The number of disabled items, as an;@link Integer} value.
	 */
	int getNumberOfDisabledItems();

	/**
	 * Enables the item, which belongs to a specific index.
	 * 
	 * @param index
	 *            The index of the item, which should be enabled, as a ;@link
	 *            Integer} value. The index must be between 0 and the value of
	 *            the method <code>size():int</code> - 1
	 */
	void enable(int index);

	/**
	 * Enables a specific item.
	 * 
	 * @param item
	 *            The item, which should be enabled, as an instance of the
	 *            generic type DataType. The item may not be null
	 */
	void enable(DataType item);

	/**
	 * Disables the item, which belongs to a specific index.
	 * 
	 * @param index
	 *            The index of the item, which should be disabled, as a ;@link
	 *            Integer} value. The index must be between 0 and the value of
	 *            the method <code>size():int</code> - 1
	 */
	void disable(int index);

	/**
	 * Disables a specific item.
	 * 
	 * @param item
	 *            The item, which should be disabled, as an instance of the
	 *            generic type DataType. The item may not be null
	 */
	void disable(DataType item);

	/**
	 * Triggers the enable state of the item, which belongs to a specific index.
	 * That means, that the item will be disabled, if it is enabled and vice
	 * versa.
	 * 
	 * @param index
	 *            The index of the item, whose enable state should be triggered,
	 *            as an;@link Integer} value. The index must be between 0 and
	 *            the value of the method <code>size():int</code> - 1
	 * @return True, if the item has been enabled, false otherwise
	 */
	boolean triggerEnableState(int index);

	/**
	 * Triggers the enable state of a specific item. That means, that the item
	 * will be disabled, if it is enabled and vice versa.
	 * 
	 * @param item
	 *            The item, whose enable state should be triggered, as an
	 *            instance of the generic type DataType. The item may not be
	 *            null
	 * @return True, if the item has been enabled, false otherwise
	 */
	boolean triggerEnableState(DataType item);

	/**
	 * Enables all items.
	 */
	void enableAll();

	/**
	 * Disables all items.
	 */
	void disableAll();

	/**
	 * Triggers the enable states of all items. That means, that the items will
	 * be disabled, if they are enabled and vice versa.
	 */
	void triggerAllEnableStates();

	/**
	 * Adds a new listener, which should be notified when an item has been
	 * disable or enabled.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            class;@link ListEnableStateListener}. The listener may not be
	 *            null
	 */
	void addEnableStateListner(ListEnableStateListener<DataType> listener);

	/**
	 * Removes a specific listener, which should not be notified when an item
	 * has been disable or enabled, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            class;@link ListEnableStateListener}. The listener may not be
	 *            null
	 */
	void removeSortingListener(ListEnableStateListener<DataType> listener);
	
}