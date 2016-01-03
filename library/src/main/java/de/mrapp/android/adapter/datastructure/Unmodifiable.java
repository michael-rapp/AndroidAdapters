/*
 * AndroidAdapters Copyright 2014 - 2016 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package de.mrapp.android.adapter.datastructure;

/**
 * A data structure, which implements this interface, indicates that it is restricted to read-only
 * accesses. Such classes are meant to throw exceptions of the type {@link
 * UnsupportedOperationException} whenever a method, which attempts to modify data, is invoked.
 *
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface Unmodifiable {

}