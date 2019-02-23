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