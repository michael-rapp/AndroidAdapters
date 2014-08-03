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

import java.util.regex.Pattern;

/**
 * Defines the interface, a class, which should support filtering by using
 * regular expressions, must implement.
 * 
 * @author Michael Rapp
 *
 * @since 1.0.0
 */
public interface Filterable {

	/**
	 * Returns, whether the class does match a specific regular expression, or
	 * not.
	 * 
	 * @param regularExpression
	 *            The regular expression, as an instance of the class
	 *            {@link Pattern}. The regular expression may not be null
	 * @return True, if the class does match the given regular expression, false
	 *         otherwise
	 */
	boolean match(Pattern regularExpression);

}