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

import java.util.Collection;
import java.util.List;

/**
 * An abstract base class for all implementations of the type {@link List},
 * which should throw exceptions of the type
 * {@link UnsupportedOperationException} when attempted to be modified.
 *
 * @param <Type>
 *            The type of the list
 * 
 * @author Michael Rapp
 * 
 * @since 0.1.0
 */
public abstract class AbstractUnmodifiableList<Type> implements List<Type> {

	@Override
	public final void add(final int location, final Type object) {
		throw new UnsupportedOperationException("List is not allowed to be modified");
	}

	@Override
	public final boolean add(final Type object) {
		throw new UnsupportedOperationException("List is not allowed to be modified");
	}

	@Override
	public final boolean addAll(final int location, final Collection<? extends Type> collection) {
		throw new UnsupportedOperationException("List is not allowed to be modified");
	}

	@Override
	public final boolean addAll(final Collection<? extends Type> collection) {
		throw new UnsupportedOperationException("List is not allowed to be modified");
	}

	@Override
	public final void clear() {
		throw new UnsupportedOperationException("List is not allowed to be modified");
	}

	@Override
	public final Type remove(final int location) {
		throw new UnsupportedOperationException("List is not allowed to be modified");
	}

	@Override
	public final boolean remove(final Object object) {
		throw new UnsupportedOperationException("List is not allowed to be modified");
	}

	@Override
	public final boolean removeAll(final Collection<?> collection) {
		throw new UnsupportedOperationException("List is not allowed to be modified");
	}

	@Override
	public final boolean retainAll(final Collection<?> collection) {
		throw new UnsupportedOperationException("List is not allowed to be modified");
	}

	@Override
	public final Type set(final int location, final Type object) {
		throw new UnsupportedOperationException("List is not allowed to be modified");
	}

}