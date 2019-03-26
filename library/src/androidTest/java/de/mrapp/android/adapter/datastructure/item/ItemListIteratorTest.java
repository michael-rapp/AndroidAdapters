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
 * Tests the functionality of the class {@link ItemListIterator}.
 *
 * @author Michael Rapp
 */
@RunWith(AndroidJUnit4.class)
public class ItemListIteratorTest {

    @Test
    public final void testConstructorWithIndexParameter() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null, 1);
        assertFalse(iterator.hasNext());
    }

    @Test
    public final void testConstructorWithoutIndexParameter() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        assertTrue(iterator.hasNext());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testSetListToNullThrowsException() {
        new ItemListIterator<>(null, null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testSetIndexToLessThanNegativeOneThrowsException() {
        new ItemListIterator<>(new ArrayList<Item<Object>>(), null, -2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void testSetIndexGreaterThanListSizeThrowsException() {
        new ItemListIterator<>(new ArrayList<Item<Object>>(), null, 1);
    }

    @Test
    public final void testHasNextIfListIsEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        assertFalse(iterator.hasNext());
    }

    @Test
    public final void testHasNextIfListIsNotEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        assertTrue(iterator.hasNext());
    }

    @Test
    public final void testHasNextIfEndIsReached() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    public final void testHasPreviousIfListIsEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        assertFalse(iterator.hasPrevious());
    }

    @Test
    public final void testHasPreviousIfListIsNotEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        iterator.next();
        iterator.next();
        assertTrue(iterator.hasPrevious());
    }

    @Test
    public final void testHasPreviousIfBeginningIsReached() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        assertFalse(iterator.hasPrevious());
    }

    @Test(expected = NoSuchElementException.class)
    public final void testNextThrowsExceptionIfListIsEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        iterator.next();
    }

    @Test
    public final void testNextIfListIsNotEmpty() {
        Object data = new Object();
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(data));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        Object next = iterator.next();
        assertEquals(data, next);
    }

    @Test(expected = NoSuchElementException.class)
    public final void testNextIfEndIsReached() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        iterator.next();
        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public final void testNextIndexThrowsExceptionExceptionIfListIsEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        iterator.nextIndex();
    }

    @Test
    public final void testNextIndexIfListIsNotEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        assertEquals(0, iterator.nextIndex());
        assertTrue(iterator.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public final void testNextIndexIfEndIsReached() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        iterator.next();
        iterator.nextIndex();
    }

    @Test(expected = NoSuchElementException.class)
    public final void testPreviousIndexIfListIsEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        iterator.previousIndex();
    }

    @Test
    public final void testPreviousIndexIfListIsNotEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        iterator.next();
        iterator.next();
        assertEquals(0, iterator.previousIndex());
    }

    @Test(expected = NoSuchElementException.class)
    public final void testPreviousIndexThrowsExceptionIfBeginningIsReached() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        iterator.previousIndex();
    }

    @Test(expected = NoSuchElementException.class)
    public final void testPreviousThrowsExceptionIfListIsEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        iterator.previous();
    }

    @Test
    public final void testPreviousifListIsNotEmpty() {
        Object data = new Object();
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(data));
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        iterator.next();
        iterator.next();
        Object previous = iterator.previous();
        assertEquals(data, previous);
    }

    @Test(expected = NoSuchElementException.class)
    public final void testPreviousThrowsExceptionIfBeginningIsReached() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        iterator.previous();
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testAddThrowsExceptionIfItemIsNull() {
        List<Item<Object>> items = new ArrayList<>();
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        iterator.add(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testAddThrowsExceptionIfAdapterIsNull() {
        List<Item<Object>> items = new ArrayList<>();
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        iterator.add(new Object());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddIfListIsEmpty() {
        Object data = new Object();
        List<Item<Object>> items = new ArrayList<>();
        ListAdapter<Object> adapter = mock(ListAdapter.class);
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, adapter);
        iterator.add(data);
        assertFalse(iterator.hasPrevious());
        assertFalse(iterator.hasNext());
        verify(adapter, times(1)).addItem(0, data);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAtTheBeginning() {
        Object data = new Object();
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ListAdapter<Object> adapter = mock(ListAdapter.class);
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, adapter);
        iterator.add(data);
        assertFalse(iterator.hasPrevious());
        assertTrue(iterator.hasNext());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddAtTheEnd() {
        Object data = new Object();
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ListAdapter<Object> adapter = mock(ListAdapter.class);
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, adapter);
        iterator.next();
        iterator.add(data);
        assertTrue(iterator.hasPrevious());
        assertFalse(iterator.hasNext());
        verify(adapter, times(1)).addItem(1, data);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAddInTheMiddle() {
        Object data = new Object();
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        items.add(new Item<>(new Object()));
        ListAdapter<Object> adapter = mock(ListAdapter.class);
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, adapter);
        iterator.next();
        iterator.add(data);
        assertTrue(iterator.hasPrevious());
        assertTrue(iterator.hasNext());
        verify(adapter, times(1)).addItem(1, data);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testSetThrowsExceptionIfItemIsNull() {
        List<Item<Object>> items = new ArrayList<>();
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        iterator.set(null);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = IllegalStateException.class)
    public final void testSetThrowsExceptionIfNextOrPreviousHasNotBeenCalled() {
        List<Item<Object>> items = new ArrayList<>();
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, mock(ListAdapter.class));
        iterator.set(new Object());
    }

    @SuppressWarnings("unchecked")
    @Test(expected = IllegalStateException.class)
    public final void testSetThrowsExceptionIfRemoveHasAlreadyBeenCalled() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, mock(ListAdapter.class));
        iterator.next();
        iterator.set(new Object());
        iterator.set(new Object());
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testSetThrowsExceptionIfAdapterIsNull() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        iterator.next();
        iterator.set(new Object());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSet() {
        Object data = new Object();
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        items.add(new Item<>(new Object()));
        ListAdapter<Object> adapter = mock(ListAdapter.class);
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, adapter);
        iterator.next();
        iterator.set(data);
        iterator.next();
        assertEquals(data, iterator.previous());
        verify(adapter, times(1)).replaceItem(0, data);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = IllegalStateException.class)
    public final void testRemoveThrowsExceptionIfNextOrPreviousHasNotBeenCalled() {
        List<Item<Object>> items = new ArrayList<>();
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, mock(ListAdapter.class));
        iterator.remove();
    }

    @SuppressWarnings("unchecked")
    @Test(expected = IllegalStateException.class)
    public final void testRemoveThrowsExceptionIfRemoveHasAlreadyBeenCalled() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, mock(ListAdapter.class));
        iterator.next();
        iterator.remove();
        iterator.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testRemoveThrowsExceptionIfAdapterIsNull() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        iterator.next();
        iterator.remove();
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemove() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        items.add(new Item<>(new Object()));
        ListAdapter<Object> adapter = mock(ListAdapter.class);
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, adapter);
        iterator.next();
        iterator.remove();
        assertEquals(1, items.size());
        assertTrue(iterator.hasNext());
        assertFalse(iterator.hasPrevious());
        verify(adapter, times(1)).removeItem(0);
    }

}