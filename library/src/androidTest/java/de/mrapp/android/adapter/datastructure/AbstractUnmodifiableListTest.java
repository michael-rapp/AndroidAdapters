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

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Tests the functionality of the class {@link AbstractUnmodifiableList}.
 *
 * @author Michael Rapp
 */
public class AbstractUnmodifiableListTest extends TestCase {

    /**
     * An implementation of the abstract class {@link AbstractUnmodifiableList}, which is needed for
     * test purposes.
     */
    private class AbstractUnmodifiableListImplementation
            extends AbstractUnmodifiableList<Object, Object> {

        /**
         * Creates a new implementation of the type {@link List}, which throws exceptions of the
         * type {@link UnsupportedOperationException} when attempted to be modified.
         *
         * @param encapsulatedList
         *         The array list, which should be encapsulated by the list, as an instance of the
         *         class {@link ArrayList}. The array list may not be null
         */
        public AbstractUnmodifiableListImplementation(final ArrayList<Object> encapsulatedList) {
            super(encapsulatedList);
        }

        @Override
        @NonNull
        public Iterator<Object> iterator() {
            return getEncapsulatedList().iterator();
        }

        @Override
        public ListIterator<Object> listIterator() {
            return getEncapsulatedList().listIterator();
        }

        @Override
        @NonNull
        public ListIterator<Object> listIterator(final int location) {
            return getEncapsulatedList().listIterator(location);
        }

        @Override
        public Object get(final int location) {
            return getEncapsulatedList().get(location);
        }

    }

    /**
     * Tests, if all properties are set correctly by the constructor.
     */
    public final void testConstructor() {
        ArrayList<Object> encapsulatedList = new ArrayList<>();
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(encapsulatedList);
        assertEquals(encapsulatedList, abstractUnmodifiableList.getEncapsulatedList());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, if the
     * encapsulated list is null.
     */
    public final void testConstructorThrowsException() {
        try {
            ArrayList<Object> encapsulatedList = null;
            new AbstractUnmodifiableListImplementation(encapsulatedList);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that an {@link UnsupportedOperationException} is thrown by the method, which allows
     * to add an item and expects an index as a parameter.
     */
    public final void testAddWithIndexParameterThrowsException() {
        try {
            AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                    new AbstractUnmodifiableListImplementation(new ArrayList<>());
            abstractUnmodifiableList.add(0, new Object());
            Assert.fail();
        } catch (UnsupportedOperationException e) {

        }
    }

    /**
     * Ensures, that an {@link UnsupportedOperationException} is thrown by the method, which allows
     * to add an item.
     */
    public final void testAddThrowsException() {
        try {
            AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                    new AbstractUnmodifiableListImplementation(new ArrayList<>());
            abstractUnmodifiableList.add(new Object());
            Assert.fail();
        } catch (UnsupportedOperationException e) {

        }
    }

    /**
     * Ensures, that an {@link UnsupportedOperationException} is thrown by the method, which allows
     * to add all items, which are contained by a collection, and expects an index as a parameter.
     */
    public final void testAddAllWithIndexParameterThrowsException() {
        try {
            AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                    new AbstractUnmodifiableListImplementation(new ArrayList<>());
            abstractUnmodifiableList.addAll(0, new ArrayList<>());
            Assert.fail();
        } catch (UnsupportedOperationException e) {

        }
    }

    /**
     * Ensures, that an {@link UnsupportedOperationException} is thrown by the method, which allows
     * to add all items, which are contained by a collection.
     */
    public final void testAddAllThrowsException() {
        try {
            AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                    new AbstractUnmodifiableListImplementation(new ArrayList<>());
            abstractUnmodifiableList.addAll(new ArrayList<>());
            Assert.fail();
        } catch (UnsupportedOperationException e) {

        }
    }

    /**
     * Ensures, that an {@link UnsupportedOperationException} is thrown by the method, which allows
     * to remove all items.
     */
    public final void testClearThrowsException() {
        try {
            AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                    new AbstractUnmodifiableListImplementation(new ArrayList<>());
            abstractUnmodifiableList.clear();
            Assert.fail();
        } catch (UnsupportedOperationException e) {

        }
    }

    /**
     * Ensures, that an {@link UnsupportedOperationException} is thrown by the method, which allows
     * to remove the item at a specific index.
     */
    public final void testRemoveWithIndexParameterThrowsException() {
        try {
            AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                    new AbstractUnmodifiableListImplementation(new ArrayList<>());
            abstractUnmodifiableList.remove(0);
            Assert.fail();
        } catch (UnsupportedOperationException e) {

        }
    }

    /**
     * Ensures, that an {@link UnsupportedOperationException} is thrown by the method, which allows
     * to remove a specific item.
     */
    public final void testRemoveThrowsException() {
        try {
            AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                    new AbstractUnmodifiableListImplementation(new ArrayList<>());
            abstractUnmodifiableList.remove(new Object());
            Assert.fail();
        } catch (UnsupportedOperationException e) {

        }
    }

    /**
     * Ensures, that an {@link UnsupportedOperationException} is thrown by the method, which allows
     * to remove all items, which are contained by a collection.
     */
    public final void testRemoveAllThrowsException() {
        try {
            AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                    new AbstractUnmodifiableListImplementation(new ArrayList<>());
            abstractUnmodifiableList.removeAll(new ArrayList<>());
            Assert.fail();
        } catch (UnsupportedOperationException e) {

        }
    }

    /**
     * Ensures, that an {@link UnsupportedOperationException} is thrown by the method, which allows
     * to retain all items, which are contained by a collection.
     */
    public final void testRetainAllThrowsException() {
        try {
            AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                    new AbstractUnmodifiableListImplementation(new ArrayList<>());
            abstractUnmodifiableList.retainAll(new ArrayList<>());
            Assert.fail();
        } catch (UnsupportedOperationException e) {

        }
    }

    /**
     * Ensures, that an {@link UnsupportedOperationException} is thrown by the method, which allows
     * to replace the item at a specific index.
     */
    public final void testSetThrowsException() {
        try {
            AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                    new AbstractUnmodifiableListImplementation(new ArrayList<>());
            abstractUnmodifiableList.set(0, new Object());
            Assert.fail();
        } catch (UnsupportedOperationException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to test, whether a specific item is
     * contained by the list.
     */
    public final void testContains() {
        Object item = new Object();
        ArrayList<Object> encapsulatedList = new ArrayList<>();
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(encapsulatedList);
        assertFalse(abstractUnmodifiableList.contains(item));
        encapsulatedList.add(item);
        assertTrue(abstractUnmodifiableList.contains(item));
    }

    /**
     * Tests the functionality of the method, which allows to test, whether all items, which are
     * contained by a collection, are contained by the list.
     */
    public final void testContainsAll() {
        Object item1 = new Object();
        Object item2 = new Object();
        Collection<Object> collection = new ArrayList<>();
        collection.add(item1);
        collection.add(item2);
        ArrayList<Object> encapsulatedList = new ArrayList<>();
        encapsulatedList.add(item1);
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(encapsulatedList);
        assertFalse(abstractUnmodifiableList.containsAll(collection));
        encapsulatedList.add(item2);
        assertTrue(abstractUnmodifiableList.containsAll(collection));
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the index of a specific
     * item.
     */
    public final void testIndexOf() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        ArrayList<Object> encapsulatedList = new ArrayList<>();
        encapsulatedList.add(item2);
        encapsulatedList.add(item3);
        encapsulatedList.add(item2);
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(encapsulatedList);
        assertEquals(-1, abstractUnmodifiableList.indexOf(item1));
        assertEquals(0, abstractUnmodifiableList.indexOf(item2));
        assertEquals(1, abstractUnmodifiableList.indexOf(item3));
    }

    /**
     * Tests the functionality of the method, which allows to retrieve, whether the list is empty.
     */
    public final void testIsEmpty() {
        ArrayList<Object> encapsulatedList = new ArrayList<>();
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(encapsulatedList);
        assertTrue(abstractUnmodifiableList.isEmpty());
        encapsulatedList.add(new Object());
        assertFalse(abstractUnmodifiableList.isEmpty());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the last index of a specific
     * item.
     */
    public final void testLastIndexOf() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        ArrayList<Object> encapsulatedList = new ArrayList<>();
        encapsulatedList.add(item2);
        encapsulatedList.add(item3);
        encapsulatedList.add(item2);
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(encapsulatedList);
        assertEquals(-1, abstractUnmodifiableList.lastIndexOf(item1));
        assertEquals(2, abstractUnmodifiableList.lastIndexOf(item2));
        assertEquals(1, abstractUnmodifiableList.lastIndexOf(item3));
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the size of the list.
     */
    public final void testSize() {
        ArrayList<Object> encapsulatedList = new ArrayList<>();
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(encapsulatedList);
        assertEquals(0, abstractUnmodifiableList.size());
        encapsulatedList.add(new Object());
        assertEquals(1, abstractUnmodifiableList.size());
    }

    /**
     * Tests the functionality of the method, which allows to create a sub list from the list.
     */
    public final void testSubList() {
        Object item1 = new Object();
        Object item2 = new Object();
        Object item3 = new Object();
        Object item4 = new Object();
        ArrayList<Object> encapsulatedList = new ArrayList<>();
        encapsulatedList.add(item1);
        encapsulatedList.add(item2);
        encapsulatedList.add(item3);
        encapsulatedList.add(item4);
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(encapsulatedList);
        List<Object> subList = abstractUnmodifiableList.subList(1, 3);
        assertEquals(2, subList.size());
        assertEquals(item2, subList.get(0));
        assertEquals(item3, subList.get(1));
    }

    /**
     * Tests the functionality of the method, which allows to create an array from the list.
     */
    public final void testToArray() {
        Object item1 = new Object();
        Object item2 = new Object();
        ArrayList<Object> encapsulatedList = new ArrayList<>();
        encapsulatedList.add(item1);
        encapsulatedList.add(item2);
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(encapsulatedList);
        Object[] array = abstractUnmodifiableList.toArray();
        assertEquals(2, array.length);
        assertEquals(item1, array[0]);
        assertEquals(item2, array[1]);
    }

    /**
     * Tests the functionality of the method, which allows to create an array from the list and
     * expects an array as a parameter.
     */
    public final void testToArrayWithArrayParameter() {
        Object item1 = new Object();
        Object item2 = new Object();
        ArrayList<Object> encapsulatedList = new ArrayList<>();
        encapsulatedList.add(item1);
        encapsulatedList.add(item2);
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(encapsulatedList);
        Object[] array = new Object[2];
        array = abstractUnmodifiableList.toArray(array);
        assertEquals(2, array.length);
        assertEquals(item1, array[0]);
        assertEquals(item2, array[1]);
    }

}