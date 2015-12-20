/*
 * AndroidAdapters Copyright 2014 - 2015 Michael Rapp
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

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An abstract base class for all implementations of the type {@link List}, which should throw
 * exceptions of the type {@link UnsupportedOperationException} when attempted to be modified. Such
 * a list is intended to encapsulate an other list.
 *
 * @param <DataType>
 *         The type of the items of the list
 * @param <EncapsulatedType>
 *         The type of the items of the encapsulated array list
 * @author Michael Rapp
 * @since 0.1.0
 */
public abstract class AbstractUnmodifiableList<DataType, EncapsulatedType>
        implements List<DataType>, Unmodifiable {

    /**
     * The array list, which is encapsulated by the list.
     */
    private final List<EncapsulatedType> encapsulatedList;

    /**
     * Returns the array list, which is encapsulated by the list.
     *
     * @return The array list, which is encapsulated by the list, as an instance of the class {@link
     * ArrayList}
     */
    protected final List<EncapsulatedType> getEncapsulatedList() {
        return encapsulatedList;
    }

    /**
     * Creates a new implementation of the type {@link List}, which throws exceptions of the type
     * {@link UnsupportedOperationException} when attempted to be modified.
     *
     * @param encapsulatedList
     *         The list, which should be encapsulated by the list, as an instance of the class
     *         {@link List}. The list may not be null
     */
    public AbstractUnmodifiableList(final List<EncapsulatedType> encapsulatedList) {
        ensureNotNull(encapsulatedList, "The encapsulated list may not be null");
        this.encapsulatedList = encapsulatedList;
    }

    @Override
    public final void add(final int location, final DataType object) {
        throw new UnsupportedOperationException("List is not allowed to be modified");
    }

    @Override
    public final boolean add(final DataType object) {
        throw new UnsupportedOperationException("List is not allowed to be modified");
    }

    @Override
    public final boolean addAll(final int location,
                                final Collection<? extends DataType> collection) {
        throw new UnsupportedOperationException("List is not allowed to be modified");
    }

    @Override
    public final boolean addAll(final Collection<? extends DataType> collection) {
        throw new UnsupportedOperationException("List is not allowed to be modified");
    }

    @Override
    public final void clear() {
        throw new UnsupportedOperationException("List is not allowed to be modified");
    }

    @Override
    public final DataType remove(final int location) {
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
    public final DataType set(final int location, final DataType object) {
        throw new UnsupportedOperationException("List is not allowed to be modified");
    }

    @Override
    public final boolean contains(final Object object) {
        return indexOf(object) != -1;
    }

    @Override
    public final boolean containsAll(final Collection<?> collection) {
        boolean result = true;

        for (Object item : collection) {
            result &= contains(item);
        }

        return result;
    }

    @Override
    public final int indexOf(final Object object) {
        for (int i = 0; i < size(); i++) {
            if (get(i).equals(object)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final boolean isEmpty() {
        return encapsulatedList.isEmpty();
    }

    @Override
    public final int lastIndexOf(final Object object) {
        for (int i = size() - 1; i >= 0; i--) {
            if (get(i).equals(object)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public final int size() {
        return encapsulatedList.size();
    }

    @Override
    public final List<DataType> subList(final int start, final int end) {
        ArrayList<DataType> subList = new ArrayList<>();

        for (int i = start; i < end; i++) {
            subList.add(get(i));
        }

        return new UnmodifiableList<>(subList);
    }

    @Override
    public final Object[] toArray() {
        Object[] array = new Object[size()];

        for (int i = 0; i < array.length; i++) {
            array[i] = get(i);
        }

        return array;
    }

    @Override
    public final <T> T[] toArray(final T[] array) {
        ArrayList<DataType> list = new ArrayList<>();

        for (int i = 0; i < size(); i++) {
            list.add(get(i));
        }

        return list.toArray(array);
    }

}