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
package de.mrapp.android.adapter.datastructure;

import java.io.Serializable;

/**
 * Defines the interface, all classes, which represent a data structure, must
 * implement. Such data structures therefore must implement the interface
 * {@link Serializable} to support serialization, as well as the interface
 * {@link Cloneable}, to allow deep copies of class instances.
 * 
 * @author Michael Rapp
 * 
 * @since 0.1.0
 */
public interface DataStructure extends Serializable, Cloneable {

	/**
	 * Creates and returns a deep copy of the object.
	 * 
	 * @return A deep copy of the object as an instance of the class
	 *         {@link Object}
	 * @throws CloneNotSupportedException
	 *             The exception, which is thrown, if cloning is not supported
	 */
	DataStructure clone() throws CloneNotSupportedException;

	@Override
	String toString();

	@Override
	int hashCode();

	@Override
	boolean equals(Object object);

}