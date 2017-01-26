/*
 * Copyright 2014 - 2017 Michael Rapp
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

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * An implementation of the type {@link List}, which throws exceptions of the type {@link
 * UnsupportedOperationException} when attempted to be modified. Such a list encapsulates an other
 * list.
 *
 * @param <Type>
 *         The type of the list's items
 * @author Michael Rapp
 * @since 0.1.0
 */
public class UnmodifiableList<Type> extends AbstractUnmodifiableList<Type, Type> {

    /**
     * Creates a new implementation of the type {@link List}, which throws exceptions of the type
     * {@link UnsupportedOperationException} when attempted to be modified.
     *
     * @param encapsulatedList
     *         The list, which should be encapsulated by the list, as an instance of the type {@link
     *         List}. The list may not be null
     */
    public UnmodifiableList(@NonNull final List<Type> encapsulatedList) {
        super(encapsulatedList);
    }

    @Override
    public final Type get(final int location) {
        return getEncapsulatedList().get(location);
    }

    @Override
    public final Iterator<Type> iterator() {
        return getEncapsulatedList().iterator();
    }

    @Override
    public final ListIterator<Type> listIterator() {
        return getEncapsulatedList().listIterator();
    }

    @Override
    public final ListIterator<Type> listIterator(final int location) {
        return getEncapsulatedList().listIterator(location);
    }

}