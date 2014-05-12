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

/**
 * Contains all possible orders, which may be used by sorting algorithms to sort
 * the entries of a list.
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public enum Order {

	/**
	 * Instructs a sorting algorithm to sort the entries of a list in an
	 * ascending order. This causes entries, which are identified to be greater
	 * than other entries, to be placed at higher indices of the list.
	 */
	ASCENDING,

	/**
	 * Instructs a sorting algorithm to sort the entries of a list in a
	 * descending order. This causes entries, which are identified to be greater
	 * than other entries, to be placed at lower indices of the list.
	 */
	DESCENDING;

}