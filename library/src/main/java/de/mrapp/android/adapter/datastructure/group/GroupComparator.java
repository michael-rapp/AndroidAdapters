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
package de.mrapp.android.adapter.datastructure.group;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.Comparator;

/**
 * A comparator, which allows to compare two groups by comparing their data.
 * 
 * @param <GroupType>
 *            The type of the groups' data
 * @param <ChildType>
 *            The type of the groups' children
 * 
 * @author Michael Rapp
 * 
 * @since 0.1.0
 */
public class GroupComparator<GroupType, ChildType> implements Comparator<Group<GroupType, ChildType>> {

	/***
	 * The comparator, which is used to compare the groups' data.
	 */
	private final Comparator<GroupType> comparator;

	/**
	 * Creates a new comparator, which should be used to compare two groups by
	 * comparing their data.
	 * 
	 * @param comparator
	 *            The comparator, which should be used to compare the groups'
	 *            data, as an instance of the type {@link Comparator}. The
	 *            comparator may not be null
	 */
	public GroupComparator(final Comparator<GroupType> comparator) {
		ensureNotNull(comparator, "The comparator may not be null");
		this.comparator = comparator;
	}

	@Override
	public final int compare(final Group<GroupType, ChildType> lhs, final Group<GroupType, ChildType> rhs) {
		return comparator.compare(lhs.getData(), rhs.getData());
	}

}