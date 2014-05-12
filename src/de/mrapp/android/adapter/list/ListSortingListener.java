package de.mrapp.android.adapter.list;

import java.util.List;

import de.mrapp.android.adapter.sorting.Order;

public interface ListSortingListener<ItemType> {

	void onSorted(List<ItemType> sortedList, List<Boolean> sortedSelections,
			Order order);

}