package de.mrapp.android.adapter.list.itemstate;

import java.util.List;

public interface ItemStateListAdapter<DataType> {

	/**
	 * Returns the number of states, the adapter's items can have.
	 * 
	 * @return The number of states, the adapter's items can have, as an ;@link
	 *         Integer} value. The value must be greater than 0
	 */
	int getNumberOfItemStates();

	/**
	 * Returns the state of the item, which belongs to a specific index.
	 * 
	 * @param index
	 *            The index of the item, whose state should be returned, as an
	 *            ;@link Integer} value. The index must be between 0 and the
	 *            value of the adapter's <code>size():int</code> method - 1
	 * @return The state of the item, which belongs to the given index, as a
	 *         ;@link Integer} value. The state must be at least 0 and less than
	 *         the value of the method <code>getNumberOfStates():int</code> - 1
	 */
	int getItemState(int index);

	/**
	 * Returns the state of a specific item.
	 * 
	 * @param item
	 *            The item, whose state should be returned, as an instance of
	 *            the generic type DataType. The item may not be null
	 * @return The state of the given item, as an;@link Integer} value. The
	 *         state must be between 0 and the value of the method
	 *         <code>getNumberOfStates():int</code> - 1
	 */
	int getItemState(DataType item);

	/**
	 * Sets the state of the item, which belongs to a specific index.
	 * 
	 * @param index
	 *            The index of the item, whose state should be set, as an ;@link
	 *            Integer} value. The index must be between 0 and the value of
	 *            the method <code>getNumberOfItems():int</code> - 1
	 * @param state
	 *            The state, which should be set, as an;@link Integer} value.
	 *            The state must be between 0 and the value of the method
	 *            <code>getNumberOfStates():int</code> - 1
	 * @return The previous state of the given item, as an;@link Integer} item.
	 *         The state must be between 0 and the value of the method
	 *         <code>getNumberOfStates():int</code> - 1
	 */
	int setItemState(int index, int state);

	/**
	 * Sets the states of all items.
	 * 
	 * @param state
	 *            The state, which should be set, as an;@link Integer} value.
	 *            The state must be between 0 and the value of the method
	 *            <code>getNumberOfStates():int</code> - 1
	 */
	void setAllItemStates(int state);

	/**
	 * Triggers the state of the item, which belongs to a specific index. This
	 * causes the state to be increased by one. If the state is already the
	 * maximum state, the state will be set to 0.
	 * 
	 * @param index
	 *            The index of the item, whose state should be triggered, as an
	 *            ;@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1
	 * @return The previous state of the item, which belongs to the given index,
	 *         as an;@link Integer} value. The state must be between 0 and the
	 *         value of the method <code>getNumberOfStates():int</code> - 1
	 */
	int triggerItemState(int index);

	/**
	 * Triggers the state of a specific item. This causes the state to be
	 * increased by one. If the state is already the maximum state, the state
	 * will be set to 0.
	 * 
	 * @param item
	 *            The item, whose state should be triggered, as an instance of
	 *            the generic type DataType. The item may not be null
	 * @return The previous state of the given item, as an;@link Integer} value.
	 *         The state must be between 0 and the value of the method
	 *         <code>getNumberOfStates():int</code> - 1
	 */
	int triggerItemState(DataType item);

	/**
	 * Triggers the states of all items. This causes the states to be increaed
	 * by one. If a state is already the maximum state, the state will be set to
	 * 0.
	 */
	void triggerAllItemStates();

	/**
	 * Sets the state of a specific item.
	 * 
	 * @param item
	 *            The item, whose state should be set, as an instance of the
	 *            generic type DataType
	 * @param state
	 *            The state, which should be set, as an;@link Integer} value.
	 *            The state must be between 0 and the value of the method
	 *            <code>getNumberOfStates():int</code> - 1
	 * @return The previous state of the given item, as an;@link Integer} value.
	 *         The state must be between 0 and the value of the method
	 *         <code>getNumberOfStates():int</code> - 1
	 */
	int setItemState(DataType item, int state);

	/**
	 * Returns the index of the first item, which has a specific state.
	 * 
	 * @param state
	 *            The state of the item, whose index should be returned, as an
	 *            ;@link Integer} value
	 * @return The index of the first item, which has the given state, as an
	 *         ;@link Integer} value or -1, if the adapter does not contain an
	 *         item with the given state
	 */
	int getFirstIndexWithSpecificState(int state);

	/**
	 * Returns the first item, which has a specific state.
	 * 
	 * @param state
	 *            The state of the item, which should be returned, as an ;@link
	 *            Integer} value
	 * @return The first item, which has the given state, as an instance of the
	 *         generic type DataType or null, if the adapter does not contain an
	 *         item with the given state
	 */
	DataType getFirstItemWithSpecificState(int state);

	/**
	 * Returns the index of the last item, which has a specific state.
	 * 
	 * @param state
	 *            The state of the item, whose index should be returned, as an
	 *            ;@link Integer} value
	 * @return The index of the last item, which has the given state, as an
	 *         ;@link Integer} value or -1, if the adapter does not contain an
	 *         item with the given state
	 */
	int getLastIndexWithSpecificState(int state);

	/**
	 * Returns the last item, which has a specific state.
	 * 
	 * @param state
	 *            The state of the item, which should be returned, as an ;@link
	 *            Integer} value
	 * @return The last item, which has the given state, as an instance of the
	 *         generic type DataType or null, if the adapter does not contain an
	 *         item with the given state
	 */
	DataType getLastItemWithSpecificState(int state);

	/**
	 * Returns a list, which contains the indices of the items, which have a
	 * specific state.
	 * 
	 * @param state
	 *            The state of the items, whose indices should be returned, as
	 *            an;@link Integer} value
	 * @return A list, which contains the indices of the items, which have a
	 *         specific state, as an instance of the type;@link List} or an
	 *         empty list, if the adapter does not contain any items with the
	 *         given state
	 */
	List<Integer> getIndicesWithSpecificState(int state);

	/**
	 * Returns a list, which contains the items, which have a specific state.
	 * 
	 * @param state
	 *            The state of the items, which should be returned, as an ;@link
	 *            Integer} value
	 * @return A list, which contains the items, which have the given state, as
	 *         an instance of the type;@link List} or an empty list, if the
	 *         adapter contains no items with the given state
	 */
	List<DataType> getItemsWithSpecificState(int state);

	/**
	 * Returns the number of items, which have a specific state.
	 * 
	 * @param state
	 *            The state of the items, which should be counted, as an ;@link
	 *            Integer} value;@link Integer} value
	 * @return The number of items, which have the given state, as an ;@link
	 *         Integer} value
	 */
	int getNumberOfItemsWithSpecificState(int state);

}