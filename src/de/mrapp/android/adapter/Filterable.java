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
package de.mrapp.android.adapter;

import java.io.Serializable;

/**
 * Defines the interface, a class, which should support filtering by using
 * regular expressions, must implement.
 * 
 * @author Michael Rapp
 *
 * @since 0.1.0
 */
public interface Filterable extends Serializable {

	/**
	 * Returns, whether the class does match a specific query, or not.
	 * 
	 * @param query
	 *            The query, as a {@link String}. The query may not be null
	 * @param flags
	 *            Optional flags as an {@link Integer} value or 0, if no flags
	 *            should be used
	 * @return True, if the class does match the given query, false otherwise
	 */
	boolean match(String query, final int flags);

}