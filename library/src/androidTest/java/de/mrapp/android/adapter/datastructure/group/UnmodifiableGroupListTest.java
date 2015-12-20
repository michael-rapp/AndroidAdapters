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
package de.mrapp.android.adapter.datastructure.group;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Tests the functionality of the class {@link UnmodifiableGroupList}.
 *
 * @author Michael Rapp
 */
public class UnmodifiableGroupListTest extends TestCase {

    /**
     * Tests the functionality of the method, which allows to retrieve the item, which corresponds
     * to a specific index.
     */
    public final void testGet() {
        Group<Object, Object> item1 = new Group<>(new Object());
        Group<Object, Object> item2 = new Group<>(new Object());
        ArrayList<Group<Object, Object>> encapsulatedList = new ArrayList<>();
        encapsulatedList.add(item1);
        encapsulatedList.add(item2);
        UnmodifiableGroupList<Object, Object> unmodifiableGroupList =
                new UnmodifiableGroupList<>(encapsulatedList);
        assertEquals(item1.getData(), unmodifiableGroupList.get(0));
        assertEquals(item2.getData(), unmodifiableGroupList.get(1));
    }

    /**
     * Tests the functionality of the method, which allows to retrieve an iterator.
     */
    public final void testIterator() {
        Group<Object, Object> item1 = new Group<>(new Object());
        Group<Object, Object> item2 = new Group<>(new Object());
        ArrayList<Group<Object, Object>> encapsulatedList = new ArrayList<>();
        encapsulatedList.add(item1);
        encapsulatedList.add(item2);
        UnmodifiableGroupList<Object, Object> unmodifiableGroupList =
                new UnmodifiableGroupList<>(encapsulatedList);
        Iterator<Object> iterator = unmodifiableGroupList.iterator();
        assertEquals(item1.getData(), iterator.next());
        assertEquals(item2.getData(), iterator.next());
        assertFalse(iterator.hasNext());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a list iterator.
     */
    public final void testListIterator() {
        Group<Object, Object> item1 = new Group<>(new Object());
        Group<Object, Object> item2 = new Group<>(new Object());
        ArrayList<Group<Object, Object>> encapsulatedList = new ArrayList<>();
        encapsulatedList.add(item1);
        encapsulatedList.add(item2);
        UnmodifiableGroupList<Object, Object> unmodifiableGroupList =
                new UnmodifiableGroupList<>(encapsulatedList);
        ListIterator<Object> iterator = unmodifiableGroupList.listIterator();
        assertEquals(item1.getData(), iterator.next());
        assertEquals(item2.getData(), iterator.next());
        assertFalse(iterator.hasNext());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve a list iterator and expects a
     * start index as a parameter.
     */
    public final void testListIteratorWithStartParameter() {
        Group<Object, Object> item1 = new Group<>(new Object());
        Group<Object, Object> item2 = new Group<>(new Object());
        ArrayList<Group<Object, Object>> encapsulatedList = new ArrayList<>();
        encapsulatedList.add(item1);
        encapsulatedList.add(item2);
        UnmodifiableGroupList<Object, Object> unmodifiableGroupList =
                new UnmodifiableGroupList<>(encapsulatedList);
        ListIterator<Object> iterator = unmodifiableGroupList.listIterator(1);
        assertEquals(item2.getData(), iterator.next());
        assertFalse(iterator.hasNext());
    }

}