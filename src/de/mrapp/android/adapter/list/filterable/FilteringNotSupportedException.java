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
package de.mrapp.android.adapter.list.filterable;

/**
 * An exception, which is thrown, if the items of an adapter should be filtered,
 * but the underlying data does not implement the interface {@link Filterable}.
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public class FilteringNotSupportedException extends RuntimeException {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

}