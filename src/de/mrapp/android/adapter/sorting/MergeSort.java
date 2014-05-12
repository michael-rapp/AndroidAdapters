/*
 * AndroidAdapters Copyright 2014 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>. 
 */
package de.mrapp.android.adapter.sorting;

import java.util.Comparator;
import java.util.List;

import android.util.Pair;
import de.mrapp.android.adapter.SortingNotSupportedException;

/**
 * An implementation of the sorting algorithm MergeSort.
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public class MergeSort extends AbstractSortingAlgorithm {

	/**
	 * Sorts and returns the entries of a specific list using the sorting
	 * algorithm MergeSort.
	 * 
	 * @param <T>
	 *            The type of the list entries
	 * @param list
	 *            The list, whose entries should be sorted, as an instance of
	 *            the type {@link List}. The list may not be null
	 * @param comparator
	 *            The comparator, which should be used to compare the list
	 *            entries, as an instance of the type {@link Comparator} or
	 *            null, if no comparator should be used. In such case the list
	 *            entries must implement the interface {@link Comparable},
	 *            otherwise a {@link SortingNotSupportedException} will be
	 *            thrown
	 * @return A list, which contains the sorted entries, as an instance of the
	 *         type {@link List}. The list may not be null
	 * @throws SortingNotSupportedException
	 *             The exception, which is thrown, if no {@link Comparable}
	 *             instance has been passed and the list entries do not
	 *             implement the interface {@link Comparable}
	 */
	private <T> Pair<List<T>, List<Boolean>> mergeSort(final List<T> list,
			final List<Boolean> selections, final Order order,
			final Comparator<T> comparator) {
		if (list.size() <= 1) {
			return new Pair<List<T>, List<Boolean>>(list, selections);
		}

		int pivot = list.size() / 2;
		List<T> leftList = list.subList(0, pivot);
		List<T> rightList = list.subList(pivot, list.size());

		List<Boolean> leftSelections = selections.subList(0, pivot);
		List<Boolean> rightSelections = selections.subList(pivot, list.size());

		Pair<List<T>, List<Boolean>> left = mergeSort(leftList, leftSelections,
				order, comparator);
		leftList = left.first;
		leftSelections = left.second;

		Pair<List<T>, List<Boolean>> right = mergeSort(rightList,
				rightSelections, order, comparator);
		rightList = right.first;
		rightSelections = right.second;

		return merge(leftList, rightList, leftSelections, rightSelections,
				order, comparator);
	}

	private <T> Pair<List<T>, List<Boolean>> merge(final List<T> leftList,
			final List<T> rightList, final List<Boolean> leftSelections,
			final List<Boolean> rightSelections, final Order order,
			final Comparator<T> comparator) {
		List<T> sortedList = instantiateList(leftList);
		List<Boolean> sortedSelections = instantiateList(leftSelections);

		int leftIndex = 0;
		int rightIndex = 0;

		while (leftIndex <= leftList.size() - 1
				&& rightIndex <= rightList.size() - 1) {
			if (compare(leftList.get(leftIndex), rightList.get(rightIndex),
					order, comparator) <= 0) {
				sortedList.add(leftList.get(leftIndex));
				sortedSelections.add(leftSelections.get(leftIndex));
				leftIndex++;
			} else {
				sortedList.add(rightList.get(rightIndex));
				sortedSelections.add(rightSelections.get(rightIndex));
				rightIndex++;
			}
		}

		while (leftIndex <= leftList.size() - 1) {
			sortedList.add(leftList.get(leftIndex));
			sortedSelections.add(leftSelections.get(leftIndex));
			leftIndex++;
		}

		while (rightIndex <= rightList.size() - 1) {
			sortedList.add(rightList.get(rightIndex));
			sortedSelections.add(rightSelections.get(rightIndex));
			rightIndex++;
		}

		return new Pair<List<T>, List<Boolean>>(sortedList, sortedSelections);
	}

	@Override
	public final <T> Pair<List<T>, List<Boolean>> sort(final List<T> list,
			final List<Boolean> selections, final Order order) {
		return mergeSort(list, selections, order, null);
	}

	@Override
	public final <T> Pair<List<T>, List<Boolean>> sort(final List<T> list,
			final List<Boolean> selections, final Order order,
			final Comparator<T> comparator) {
		return mergeSort(list, selections, order, comparator);
	}

}