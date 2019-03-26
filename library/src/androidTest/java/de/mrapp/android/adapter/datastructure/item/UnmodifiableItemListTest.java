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
package de.mrapp.android.adapter.datastructure.item;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests the functionality of the class {@link UnmodifiableItemList}.
 *
 * @author Michael Rapp
 */
@RunWith(AndroidJUnit4.class)
public class UnmodifiableItemListTest {

    @Test
    public final void testGet() {
        Item<Object> item1 = new Item<>(new Object());
        Item<Object> item2 = new Item<>(new Object());
        ArrayList<Item<Object>> encapsulatedList = new ArrayList<>();
        encapsulatedList.add(item1);
        encapsulatedList.add(item2);
        UnmodifiableItemList<Object> unmodifiableItemList =
                new UnmodifiableItemList<>(encapsulatedList);
        assertEquals(item1.getData(), unmodifiableItemList.get(0));
        assertEquals(item2.getData(), unmodifiableItemList.get(1));
    }

    @Test
    public final void testIterator() {
        Item<Object> item1 = new Item<>(new Object());
        Item<Object> item2 = new Item<>(new Object());
        ArrayList<Item<Object>> encapsulatedList = new ArrayList<>();
        encapsulatedList.add(item1);
        encapsulatedList.add(item2);
        UnmodifiableItemList<Object> unmodifiableItemList =
                new UnmodifiableItemList<>(encapsulatedList);
        Iterator<Object> iterator = unmodifiableItemList.iterator();
        assertEquals(item1.getData(), iterator.next());
        assertEquals(item2.getData(), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public final void testListIterator() {
        Item<Object> item1 = new Item<>(new Object());
        Item<Object> item2 = new Item<>(new Object());
        ArrayList<Item<Object>> encapsulatedList = new ArrayList<>();
        encapsulatedList.add(item1);
        encapsulatedList.add(item2);
        UnmodifiableItemList<Object> unmodifiableItemList =
                new UnmodifiableItemList<>(encapsulatedList);
        ListIterator<Object> iterator = unmodifiableItemList.listIterator();
        assertEquals(item1.getData(), iterator.next());
        assertEquals(item2.getData(), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public final void testListIteratorWithStartParameter() {
        Item<Object> item1 = new Item<>(new Object());
        Item<Object> item2 = new Item<>(new Object());
        ArrayList<Item<Object>> encapsulatedList = new ArrayList<>();
        encapsulatedList.add(item1);
        encapsulatedList.add(item2);
        UnmodifiableItemList<Object> unmodifiableItemList =
                new UnmodifiableItemList<>(encapsulatedList);
        ListIterator<Object> iterator = unmodifiableItemList.listIterator(1);
        assertEquals(item2.getData(), iterator.next());
        assertFalse(iterator.hasNext());
    }

}