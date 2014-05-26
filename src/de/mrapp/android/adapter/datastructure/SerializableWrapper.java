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
 * A wrapper, which encapsulates any type of instance and implements the
 * interface {@link Serializable}. Such a wrapper may be used to store
 * instances, which do not implement the interface {@link Serializable}, within
 * a {@link Bundle} object.
 * 
 * 
 * @param <Type>
 *            The type of the encapsulated instance
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public class SerializableWrapper<Type> implements Serializable {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The instance, which is encapsulated by the wrapper.
	 */
	private final Type wrappedInstance;

	/**
	 * Creates a new wrapper, which encapsulates a specific instance.
	 * 
	 * @param wrappedInstance
	 *            The instance, which should be encapsulated by the wrapper, as
	 *            a instance of the generic type Type or null, if no instance
	 *            should be encapsulated
	 */
	public SerializableWrapper(final Type wrappedInstance) {
		this.wrappedInstance = wrappedInstance;
	}

	/**
	 * Returns the instance, which is encapsulated by the wrapper.
	 * 
	 * @return The instance, which is encapsulated by the wrapper, as an
	 *         instance of the generic type Type or null, if no instance is
	 *         encapsulated
	 */
	public final Type getWrappedInstance() {
		return wrappedInstance;
	}

}