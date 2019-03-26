/*
 * Copyright 2014 - 2019 Michael Rapp
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

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the functionality of the class {@link AbstractUnmodifiableList}.
 *
 * @author Michael Rapp
 */
@RunWith(AndroidJUnit4.class)
public class AbstractUnmodifiableListTest {

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

        @NonNull
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

    @Test
    public final void testConstructor() {
        ArrayList<Object> encapsulatedList = new ArrayList<>();
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(encapsulatedList);
        assertEquals(encapsulatedList, abstractUnmodifiableList.getEncapsulatedList());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsException() {
        ArrayList<Object> encapsulatedList = null;
        new AbstractUnmodifiableListImplementation(encapsulatedList);
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testAddWithIndexParameterThrowsException() {
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(new ArrayList<>());
        abstractUnmodifiableList.add(0, new Object());
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testAddThrowsException() {
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(new ArrayList<>());
        abstractUnmodifiableList.add(new Object());
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testAddAllWithIndexParameterThrowsException() {
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(new ArrayList<>());
        abstractUnmodifiableList.addAll(0, new ArrayList<>());
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testAddAllThrowsException() {
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(new ArrayList<>());
        abstractUnmodifiableList.addAll(new ArrayList<>());
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testClearThrowsException() {
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(new ArrayList<>());
        abstractUnmodifiableList.clear();
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemoveWithIndexParameterThrowsException() {
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(new ArrayList<>());
        abstractUnmodifiableList.remove(0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemoveThrowsException() {
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(new ArrayList<>());
        abstractUnmodifiableList.remove(new Object());
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemoveAllThrowsException() {
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(new ArrayList<>());
        abstractUnmodifiableList.removeAll(new ArrayList<>());
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRetainAllThrowsException() {
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(new ArrayList<>());
        abstractUnmodifiableList.retainAll(new ArrayList<>());
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testSetThrowsException() {
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(new ArrayList<>());
        abstractUnmodifiableList.set(0, new Object());
    }

    @Test
    public final void testContains() {
        Object item = new Object();
        ArrayList<Object> encapsulatedList = new ArrayList<>();
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(encapsulatedList);
        assertFalse(abstractUnmodifiableList.contains(item));
        encapsulatedList.add(item);
        assertTrue(abstractUnmodifiableList.contains(item));
    }

    @Test
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

    @Test
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

    @Test
    public final void testIsEmpty() {
        ArrayList<Object> encapsulatedList = new ArrayList<>();
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(encapsulatedList);
        assertTrue(abstractUnmodifiableList.isEmpty());
        encapsulatedList.add(new Object());
        assertFalse(abstractUnmodifiableList.isEmpty());
    }

    @Test
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

    @Test
    public final void testSize() {
        ArrayList<Object> encapsulatedList = new ArrayList<>();
        AbstractUnmodifiableListImplementation abstractUnmodifiableList =
                new AbstractUnmodifiableListImplementation(encapsulatedList);
        assertEquals(0, abstractUnmodifiableList.size());
        encapsulatedList.add(new Object());
        assertEquals(1, abstractUnmodifiableList.size());
    }

    @Test
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

    @Test
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

    @Test
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