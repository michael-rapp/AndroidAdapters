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

import de.mrapp.android.adapter.list.ListAdapter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests the functionality of the class {@link ItemListIterator}.
 *
 * @author Michael Rapp
 */
public class ItemListIteratorTest extends TestCase {

    /**
     * Ensures, that all properties are initialized correctly by the constructor, which expects the
     * iterator's start index as a parameter.
     */
    public final void testConstructorWithIndexParameter() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null, 1);
        assertFalse(iterator.hasNext());
    }

    /**
     * Ensures, that all properties are initialized correctly by the constructor, which implicitly
     * sets the iterator's start index.
     */
    public final void testConstructorWithoutIndexParameter() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        assertTrue(iterator.hasNext());
    }

    /**
     * Ensures that a {@link NullPointerException} is thrown, if the iterator's underlying list is
     * set to null.
     */
    public final void testSetListToNullThrowsException() {
        try {
            new ItemListIterator<>(null, null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that an {@link IndexOutOfBoundsException} is thrown, if the iterator's start index
     * is set to a value less than 0.
     */
    public final void testSetIndexToLessThanNegativeOneThrowsException() {
        try {
            new ItemListIterator<>(new ArrayList<Item<Object>>(), null, -2);
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Ensures, that an {@link IndexOutOfBoundsException} is thrown, if the iterator's start index
     * is set to a value greater than size of its underlying list - 1.
     */
    public final void testSetIndexGreaterThanListSizeThrowsException() {
        try {
            new ItemListIterator<>(new ArrayList<Item<Object>>(), null, 1);
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {

        }
    }

    /**
     * Tests the functionality of the hasNext-method, if the iterator's underlying list is empty.
     */
    public final void testHasNextIfListIsEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        assertFalse(iterator.hasNext());
    }

    /**
     * Tests the functionality of the hasNext-method, if the iterator's underlying list is not
     * empty.
     */
    public final void testHasNextIfListIsNotEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        assertTrue(iterator.hasNext());
    }

    /**
     * Tests the functionality of the hasNext-method, if the iterator has already reached the end of
     * its underlying list.
     */
    public final void testHasNextIfEndIsReached() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    /**
     * Tests the functionality of the hasPrevious-method, if the iterator's underlying list is
     * empty.
     */
    public final void testHasPreviousIfListIsEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        assertFalse(iterator.hasPrevious());
    }

    /**
     * Tests the functionality of the hasPrevious-method, if the iterator's underlying list is not
     * empty.
     */
    public final void testHasPreviousIfListIsNotEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        iterator.next();
        iterator.next();
        assertTrue(iterator.hasPrevious());
    }

    /**
     * Tests the functionality of the hasPrevious-method, if the iterator has reached the beginning
     * of its underlying list.
     */
    public final void testHasPreviousIfBeginningIsReached() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        assertFalse(iterator.hasPrevious());
    }

    /**
     * Ensures, that a {@link NoSuchElementException} is thrown, if the next-method is called, when
     * the iterator's underlying list is empty.
     */
    public final void testNextThrowsExceptionIfListIsEmpty() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
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
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
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
            ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
            iterator.next();
            iterator.next();
            Assert.fail();
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Ensures, that a {@link NoSuchElementException} is thrown, if the nextIndex-method is called,
     * when the iterator's underlying list is empty.
     */
    public final void testNextIndexThrowsExceptionExceptionIfListIsEmpty() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
            iterator.nextIndex();
            Assert.fail();
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Tests the functionality of the nextIndex-method, if the iterator's underlying list is not
     * empty.
     */
    public final void testNextIndexIfListIsNotEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        assertEquals(0, iterator.nextIndex());
        assertTrue(iterator.hasNext());
    }

    /**
     * Ensures, that a {@link NoSuchElementException} is thrown, if the nextIndex-method is called
     * when the iterator has reached the end of its underlying list.
     */
    public final void testNextIndexIfEndIsReached() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            items.add(new Item<>(new Object()));
            ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
            iterator.next();
            iterator.nextIndex();
            Assert.fail();
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Ensures, that a {@link NoSuchElementException} is thrown, if the previousIndex-method is
     * called when the iterator's underlying list is empty.
     */
    public final void testPreviousIndexIfListIsEmpty() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
            iterator.previousIndex();
            Assert.fail();
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Tests the functionality of the previousIndex-method, if the iterator's underlying list is not
     * empty.
     */
    public final void testPreviousIndexIfListIsNotEmpty() {
        List<Item<Object>> items = new ArrayList<>();
        items.add(new Item<>(new Object()));
        items.add(new Item<>(new Object()));
        ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
        iterator.next();
        iterator.next();
        assertEquals(0, iterator.previousIndex());
    }

    /**
     * Ensures, that a {@link NoSuchElementException} is thrown, if the iterator has reached the
     * beginning of its underlying list.
     */
    public final void testPreviousIndexThrowsExceptionIfBeginningIsReached() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            items.add(new Item<>(new Object()));
            ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
            iterator.previousIndex();
            Assert.fail();
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Ensures, that a {@link NoSuchElementException} is thrown, if the previous-method is called,
     * when the iterator's underlying list is empty.
     */
    public final void testPreviousThrowsExceptionIfListIsEmpty() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
            iterator.previous();
            Assert.fail();
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Tests the functionality of the previous-method, if the iterator's underlying list is not
     * empty.
     */
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

    /**
     * Ensures, that a {@link NoSuchElementException} is thrown, if the iterator has reached the
     * beginning of its underlying list.
     */
    public final void testPreviousThrowsExceptionIfBeginningIsReached() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            items.add(new Item<>(new Object()));
            ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
            iterator.previous();
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if an item, which is null, should be
     * added.
     */
    public final void testAddThrowsExceptionIfItemIsNull() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
            iterator.add(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that an {@link UnsupportedOperationException} is thrown, if an an item should be
     * added and the adapter is null.
     */
    public final void testAddThrowsExceptionIfAdapterIsNull() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
            iterator.add(new Object());
            Assert.fail();
        } catch (UnsupportedOperationException e) {

        }
    }

    /**
     * Tests the functionality of the add-method, if the iterator's underlying list is empty.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Tests the functionality of the add-method, if the new element is added at the beginning of
     * the iterator's underlying list.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Tests the functionality of the add-method, if the new element is added at the end of the
     * iterator's underlying list.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Tests the functionality of the add-method, if the new element is added at in the middle of
     * the iterator's underlying list.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if an item, which is null, should be
     * set.
     */
    public final void testSetThrowsExceptionIfItemIsNull() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
            iterator.set(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Ensures, that an {@link IllegalStateException} is thrown, if the set-method is called when
     * the next- or previous-method has not been called before.
     */
    @SuppressWarnings("unchecked")
    public final void testSetThrowsExceptionIfNextOrPreviousHasNotBeenCalled() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            ItemListIterator<Object> iterator =
                    new ItemListIterator<>(items, mock(ListAdapter.class));
            iterator.set(new Object());
            Assert.fail();
        } catch (IllegalStateException e) {

        }
    }

    /**
     * Ensures, that an {@link IllegalStateException} is thrown, if the set-method has already been
     * called and the next- or previous-method has not been called since then.
     */
    @SuppressWarnings("unchecked")
    public final void testSetThrowsExceptionIfRemoveHasAlreadyBeenCalled() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            items.add(new Item<>(new Object()));
            ItemListIterator<Object> iterator =
                    new ItemListIterator<>(items, mock(ListAdapter.class));
            iterator.next();
            iterator.set(new Object());
            iterator.set(new Object());
            Assert.fail();
        } catch (IllegalStateException e) {

        }
    }

    /**
     * Ensures, that an {@link UnsupportedOperationException} is thrown, if the set-method is called
     * and the adapter is null.
     */
    public final void testSetThrowsExceptionIfAdapterIsNull() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            items.add(new Item<>(new Object()));
            ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
            iterator.next();
            iterator.set(new Object());
            Assert.fail();
        } catch (UnsupportedOperationException e) {

        }
    }

    /**
     * Tests the functionality of the set-method.
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Ensures, that an {@link IllegalStateException} is thrown, if the remove-method is called when
     * the next- or previous-method has not been called before.
     */
    @SuppressWarnings("unchecked")
    public final void testRemoveThrowsExceptionIfNextOrPreviousHasNotBeenCalled() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            ItemListIterator<Object> iterator =
                    new ItemListIterator<>(items, mock(ListAdapter.class));
            iterator.remove();
            Assert.fail();
        } catch (IllegalStateException e) {

        }
    }

    /**
     * Ensures, that an {@link IllegalStateException} is thrown, if the remove-method has already
     * been called and the next- or previous-method has not been called since then.
     */
    @SuppressWarnings("unchecked")
    public final void testRemoveThrowsExceptionIfRemoveHasAlreadyBeenCalled() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            items.add(new Item<>(new Object()));
            ItemListIterator<Object> iterator =
                    new ItemListIterator<>(items, mock(ListAdapter.class));
            iterator.next();
            iterator.remove();
            iterator.remove();
            Assert.fail();
        } catch (IllegalStateException e) {

        }
    }

    /**
     * Ensures, that an {@link UnsupportedOperationException} is thrown, if the remove-method is
     * called and the adapter is null.
     */
    public final void testRemoveThrowsExceptionIfAdapterIsNull() {
        try {
            List<Item<Object>> items = new ArrayList<>();
            items.add(new Item<>(new Object()));
            ItemListIterator<Object> iterator = new ItemListIterator<>(items, null);
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