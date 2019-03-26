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

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import androidx.test.ext.junit.runners.AndroidJUnit4;

/**
 * Tests the functionality of the class {@link UnmodifiableGroupList}.
 *
 * @author Michael Rapp
 */
@RunWith(AndroidJUnit4.class)
public class UnmodifiableGroupListTest extends TestCase {

    @Test
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

    @Test
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

    @Test
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

    @Test
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