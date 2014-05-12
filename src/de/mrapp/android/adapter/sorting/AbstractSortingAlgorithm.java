package de.mrapp.android.adapter.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import de.mrapp.android.adapter.SortingNotSupportedException;

public abstract class AbstractSortingAlgorithm implements SortingAlgorithm {

	@SuppressWarnings("unchecked")
	private <T> int compare(final T entry1, final T entry2)
			throws SortingNotSupportedException {
		try {
			Comparable<T> comparable = (Comparable<T>) entry1;
			return comparable.compareTo(entry2);
		} catch (ClassCastException e) {
			throw new SortingNotSupportedException();
		}
	}

	private <T> int compareAscending(final T entry1, final T entry2,
			final Comparator<T> comparator) {
		if (comparator == null) {
			return compare(entry1, entry2);
		} else {
			return comparator.compare(entry1, entry2);
		}
	}

	private <T> int compareDescending(final T entry1, final T entry2,
			final Comparator<T> comparator) {
		if (comparator == null) {
			return compare(entry2, entry1);
		} else {
			return comparator.compare(entry2, entry1);
		}
	}

	protected <T> int compare(final T entry1, final T entry2,
			final Order order, final Comparator<T> comparator)
			throws SortingNotSupportedException {
		if (order == Order.ASCENDING) {
			return compareAscending(entry1, entry2, comparator);
		} else {
			return compareDescending(entry1, entry2, comparator);
		}
	}

	@SuppressWarnings("unchecked")
	protected final <T> List<T> instantiateList(final List<T> list) {
		try {
			return list.getClass().newInstance();
		} catch (InstantiationException e) {
			return new ArrayList<T>();
		} catch (IllegalAccessException e) {
			return new ArrayList<T>();
		}
	}

}