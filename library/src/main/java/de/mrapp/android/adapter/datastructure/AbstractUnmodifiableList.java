/*
 * Copyright 2014 - 2018 Michael Rapp
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package de.mrapp.android.adapter.datastructure;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static de.mrapp.android.util.Condition.ensureNotNull;

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
    public AbstractUnmodifiableList(@NonNull final List<EncapsulatedType> encapsulatedList) {
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
                                @NonNull final Collection<? extends DataType> collection) {
        throw new UnsupportedOperationException("List is not allowed to be modified");
    }

    @Override
    public final boolean addAll(@NonNull final Collection<? extends DataType> collection) {
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
    public final boolean removeAll(@NonNull final Collection<?> collection) {
        throw new UnsupportedOperationException("List is not allowed to be modified");
    }

    @Override
    public final boolean retainAll(@NonNull final Collection<?> collection) {
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
    public final boolean containsAll(@NonNull final Collection<?> collection) {
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
    @NonNull
    public final List<DataType> subList(final int start, final int end) {
        ArrayList<DataType> subList = new ArrayList<>();

        for (int i = start; i < end; i++) {
            subList.add(get(i));
        }

        return new UnmodifiableList<>(subList);
    }

    @Override
    @NonNull
    public final Object[] toArray() {
        Object[] array = new Object[size()];

        for (int i = 0; i < array.length; i++) {
            array[i] = get(i);
        }

        return array;
    }

    @Override
    public final <T> T[] toArray(@NonNull final T[] array) {
        ArrayList<DataType> list = new ArrayList<>();

        for (int i = 0; i < size(); i++) {
            list.add(get(i));
        }

        return list.toArray(array);
    }

}