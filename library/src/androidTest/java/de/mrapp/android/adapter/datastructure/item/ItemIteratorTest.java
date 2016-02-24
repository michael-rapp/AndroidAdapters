/*
 * Copyright 2014 - 2016 Michael Rapp
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

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import de.mrapp.android.adapter.ListAdapter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests the functionality of the class {@link ItemIterator}.
 *
 * @author Michael Rapp
 */
public class ItemIteratorTest extends TestCase {

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the iterator's underlying list is
     * set to null.
     */
    public final void testSetListToNullThrowsException() {
        try {
            new ItemIterator<>(null, null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the hasNext-method, if the iterator's underlying list is empty.
     */
    public final void testHasNextIfListIsEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        ItemIterator<Object> iterator = new ItemIterator<>(items, null);
        assertFalse(iterator.hasNext());
    }

    /**
     * Tests the functionality of the hasNext-method, if the iterator's underlying list is not
     * empty.
     */
    public final void testHasNextIfListIsNotEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemIterator<Object> iterator = new ItemIterator<>(items, null);
        assertTrue(iterator.hasNext());
    }

    /**
     * Tests the functionality of the hasNext-method, if the iterator already reached the end of its
     * underlying list.
     */
    public final void testHasNextIfEndIsReached() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemIterator<Object> iterator = new ItemIterator<>(items, null);
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    /**
     * Ensures, that a {@link NoSuchElementException} is thrown, if the next-method is called, when
     * the iterator's underlying list is empty.
     */
    public final void testNextThrowExceptionIfListIsEmpty() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            ItemIterator<Object> iterator = new ItemIterator<>(items, null);
            iterator.next();
            Assert.fail();
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Tests the functionality of the next-method, if the iterator's underlying list is not empty.
     */
    public final void testNextIfListIsNotEmpty() {
        Object data = new Object();
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(data));
        ItemIterator<Object> iterator = new ItemIterator<>(items, null);
        Object next = iterator.next();
        assertEquals(data, next);
    }

    /**
     * Ensures, that a {@link NoSuchElementException} is thrown, if the next-method is called, when
     * the iterator already reached the end of the underlying list.
     */
    public final void testNextIfEndIsReached() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            items.add(new Item<>(new Object()));
            ItemIterator<Object> iterator = new ItemIterator<>(items, null);
            iterator.next();
            iterator.next();
            Assert.fail();
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Ensures, that an {@link IllegalStateException} is thrown, if the remove-method is called when
     * the next-method has not been called before.
     */
    @SuppressWarnings("unchecked")
    public final void testRemoveThrowsExceptionIfNextHasNotBeenCalled() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            ItemIterator<Object> iterator = new ItemIterator<>(items, mock(ListAdapter.class));
            iterator.remove();
            Assert.fail();
        } catch (IllegalStateException e) {

        }
    }

    /**
     * Ensures, that an {@link IllegalStateException} is thrown, if the remove-method has already
     * been called and the next-method has not been called since then.
     */
    @SuppressWarnings("unchecked")
    public final void testRemoveThrowsExceptionIfRemoveHasAlreadyBeenCalled() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            items.add(new Item<>(new Object()));
            ItemIterator<Object> iterator = new ItemIterator<>(items, mock(ListAdapter.class));
            iterator.next();
            iterator.remove();
            iterator.remove();
            Assert.fail();
        } catch (IllegalStateException e) {

        }
    }

    /**
     * Ensures, that an {@link UnsupportedOperationException} is thrown, if the remove-method is
     * called and adapter is null.
     */
    public final void testRemoveThrowsException() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            items.add(new Item<>(new Object()));
            ItemIterator<Object> iterator = new ItemIterator<>(items, null);
            iterator.next();
            iterator.remove();
            Assert.fail();
        } catch (UnsupportedOperationException e) {

        }
    }

    /**
     * Tests the functionality of the remove-method.
     */
    @SuppressWarnings("unchecked")
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