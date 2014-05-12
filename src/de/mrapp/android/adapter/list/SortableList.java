package de.mrapp.android.adapter.list;

import de.mrapp.android.adapter.SortingNotSupportedException;
import de.mrapp.android.adapter.sorting.Order;

public interface SortableList {

	void sort(Order order) throws SortingNotSupportedException;

}
