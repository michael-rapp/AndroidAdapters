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
import java.util.List;
import java.util.NoSuchElementException;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import de.mrapp.android.adapter.list.ListAdapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests the functionality of the class {@link ItemIterator}.
 *
 * @author Michael Rapp
 */
@RunWith(AndroidJUnit4.class)
public class ItemIteratorTest {

    @Test(expected = IllegalArgumentException.class)
    public final void testSetListToNullThrowsException() {
        new ItemIterator<>(null, null);
    }

    @Test
    public final void testHasNextIfListIsEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        ItemIterator<Object> iterator = new ItemIterator<>(items, null);
        assertFalse(iterator.hasNext());
    }

    @Test
    public final void testHasNextIfListIsNotEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemIterator<Object> iterator = new ItemIterator<>(items, null);
        assertTrue(iterator.hasNext());
    }

    @Test
    public final void testHasNextIfEndIsReached() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemIterator<Object> iterator = new ItemIterator<>(items, null);
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public final void testNextThrowExceptionIfListIsEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        ItemIterator<Object> iterator = new ItemIterator<>(items, null);
        iterator.next();
    }

    @Test
    public final void testNextIfListIsNotEmpty() {
        Object data = new Object();
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(data));
        ItemIterator<Object> iterator = new ItemIterator<>(items, null);
        Object next = iterator.next();
        assertEquals(data, next);
    }

    @Test(expected = NoSuchElementException.class)
    public final void testNextIfEndIsReached() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemIterator<Object> iterator = new ItemIterator<>(items, null);
        iterator.next();
        iterator.next();
    }

    @SuppressWarnings("unchecked")
    @Test(expected = IllegalStateException.class)
    public final void testRemoveThrowsExceptionIfNextHasNotBeenCalled() {
        List<Item<Object>> items = new ArrayList<>();
        ItemIterator<Object> iterator = new ItemIterator<>(items, mock(ListAdapter.class));
        iterator.remove();
    }

    @SuppressWarnings("unchecked")
    @Test(expected = IllegalStateException.class)
    public final void testRemoveThrowsExceptionIfRemoveHasAlreadyBeenCalled() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemIterator<Object> iterator = new ItemIterator<>(items, mock(ListAdapter.class));
        iterator.next();
        iterator.remove();
        iterator.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemoveThrowsException() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemIterator<Object> iterator = new ItemIterator<>(items, null);
        iterator.next();
        iterator.remove();
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemove() {
        Object item1 = new Object();
        Object item2 = new Object();
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(item1));
        items.add(new Item<>(item1));
        items.add(new Item<>(item2));
        ListAdapter<Object> adapter = mock(ListAdapter.class);
        ItemIterator<Object> iterator = new ItemIterator<>(items, adapter);
        Object currentItem = iterator.next();
        assertEquals(item1, currentItem);
        currentItem = iterator.next();
        assertEquals(item1, currentItem);
        iterator.remove();
        verify(adapter, times(1)).removeItem(1);
        assertEquals(2, items.size());
        assertTrue(iterator.hasNext());
        currentItem = iterator.next();
        assertEquals(item2, currentItem);
        iterator.remove();
        verify(adapter, times(2)).removeItem(1);
        assertEquals(1, items.size());
        assertFalse(iterator.hasNext());
    }

}