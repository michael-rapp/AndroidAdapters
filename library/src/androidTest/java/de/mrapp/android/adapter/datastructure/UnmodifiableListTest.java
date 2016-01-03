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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import junit.framework.TestCase;

/**
 * Tests the functionality of the class {@link UnmodifiableList}.
 *
 * @author Michael Rapp
 */
public class UnmodifiableListTest extends TestCase {

    /**
     * Tests the functionality of the method, which allows to retrieve the item, which corresponds
     * to a specific index.
     */
    public final void testGet() {
        Object item1 = new Object();
        Object item2 = new Object();
        ArrayList<Object> encapsulatedList = new ArrayList<>();
        encapsulatedList.add(item1);
        encapsulatedList.add(item2);
        UnmodifiableList<Object> unmodifiableList = new UnmodifiableList<>(encapsulatedList);
        assertEquals(item1, unmodifiableList.get(0));
        assertEquals(item2, unmodifiableList.get(1));
    }

    /**
     * Tests the functionality of the method, which allows to retrieve an iterator.
     */
    public final void testIterator() {
        Object item1 = new Object();
        Object item2 = new Object();
        ArrayList<Object> encapsulatedList = new ArrayList<>();
        encapsulatedList.add(item1);
        encapsulatedList.add(item2);
        UnmodifiableList<Object> unmodifiableList = new UnmodifiableList<>(encapsulatedList);
        Iterator<Object> iterator = unmodifiableList.iterator();
        assertEquals(item1, iterator.next());
        assertEquals(item2, iterator.next());
        assertFalse(iterator.hasNext());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a list iterator.
     */
    public final void testListIterator() {
        Object item1 = new Object();
        Object item2 = new Object();
        ArrayList<Object> encapsulatedList = new ArrayList<>();
        encapsulatedList.add(item1);
        encapsulatedList.add(item2);
        UnmodifiableList<Object> unmodifiableList = new UnmodifiableList<>(encapsulatedList);
        ListIterator<Object> iterator = unmodifiableList.listIterator();
        assertEquals(item1, iterator.next());
        assertEquals(item2, iterator.next());
        assertFalse(iterator.hasNext());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a list iterator and expects a
     * start index as a parameter.
     */
    public final void testListIteratorWithStartParameter() {
        Object item1 = new Object();
        Object item2 = new Object();
        ArrayList<Object> encapsulatedList = new ArrayList<>();
        encapsulatedList.add(item1);
        encapsulatedList.add(item2);
        UnmodifiableList<Object> unmodifiableList = new UnmodifiableList<>(encapsulatedList);
        ListIterator<Object> iterator = unmodifiableList.listIterator(1);
        assertEquals(item2, iterator.next());
        assertFalse(iterator.hasNext());
    }

}