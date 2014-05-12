package de.mrapp.android.adapter.sorting;

import java.util.Comparator;
import java.util.List;

import android.util.Pair;

public class InsertionSort extends AbstractSortingAlgorithm {

	private <T> Pair<List<T>, List<Boolean>> insertionSort(final List<T> list,
			final List<Boolean> selections, final Order order,
			final Comparator<T> comparator) {
		for (int i = 1; i < list.size(); i++) {
			T tmpListEntry = list.get(i);
			Boolean tmpSelectionEntry = selections.get(i);
			int j = i;

			while (j > 0
					&& compare(list.get(j - 1), tmpListEntry, order, comparator) >= 0) {
				list.set(j, list.get(j - 1));
				selections.set(j, selections.get(j - 1));
				j--;
			}

			list.set(j, tmpListEntry);
			selections.set(j, tmpSelectionEntry);
		}

		return new Pair<List<T>, List<Boolean>>(list, selections);
	}

	@Override
	public final <T> Pair<List<T>, List<Boolean>> sort(final List<T> list,
			final List<Boolean> selections, final Order order) {
		return insertionSort(list, selections, order, null);
	}

	@Override
	public final <T> Pair<List<T>, List<Boolean>> sort(final List<T> list,
			final List<Boolean> selections, final Order order,
			final Comparator<T> comparator) {
		return insertionSort(list, selections, order, null);
	}

}