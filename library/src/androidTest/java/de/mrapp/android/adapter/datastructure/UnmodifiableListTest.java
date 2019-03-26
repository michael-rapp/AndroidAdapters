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
import java.util.Iterator;
import java.util.ListIterator;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests the functionality of the class {@link UnmodifiableList}.
 *
 * @author Michael Rapp
 */
@RunWith(AndroidJUnit4.class)
public class UnmodifiableListTest {

    @Test
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

    @Test
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

    @Test
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

    @Test
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