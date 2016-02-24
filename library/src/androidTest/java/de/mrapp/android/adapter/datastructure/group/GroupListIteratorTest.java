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
package de.mrapp.android.adapter.datastructure.group;

import android.content.Context;
import android.test.AndroidTestCase;

import junit.framework.Assert;

import java.util.ListIterator;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests the functionality of the class {@link GroupListIterator}.
 *
 * @author Michael Rapp
 */
public class GroupListIteratorTest extends AndroidTestCase {

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the list iterator, which is passed
     * as a constructor parameter, is null.
     */
    public final void testConstructorThrowsExceptionIfListIteratorIsNull() {
        try {
            new GroupListIterator<>(null, getContext());
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the add-method.
     */
    @SuppressWarnings("unchecked")
    public final void testAdd() {
        ListIterator<Group<Object, Object>> listIterator = mock(ListIterator.class);
        Context context = getContext();
        GroupListIterator<Object, Object> groupListIterator =
                new GroupListIterator<>(listIterator, context);
        Object group = new Object();
        groupListIterator.add(group);
        verify(listIterator, times(1)).add(any(Group.class));
    }

    /**
     * Tests the functionality of the hasNext-method.
     */
    @SuppressWarnings("unchecked")
    public final void testHasNext() {
        ListIterator<Group<Object, Object>> listIterator = mock(ListIterator.class);
        GroupListIterator<Object, Object> groupListIterator =
                new GroupListIterator<>(listIterator, getContext());
        when(groupListIterator.hasNext()).thenReturn(true);
        boolean hasNext = groupListIterator.hasNext();
        assertTrue(hasNext);
        verify(listIterator, times(1)).hasNext();
    }

    /**
     * Tests the functionality of the hasPrevious-method.
     */
    @SuppressWarnings("unchecked")
    public final void testHasPrevious() {
        ListIterator<Group<Object, Object>> listIterator = mock(ListIterator.class);
        GroupListIterator<Object, Object> groupListIterator =
                new GroupListIterator<>(listIterator, getContext());
        when(groupListIterator.hasPrevious()).thenReturn(true);
        boolean hasPrevious = groupListIterator.hasPrevious();
        assertTrue(hasPrevious);
        verify(listIterator, times(1)).hasPrevious();
    }

    /**
     * Tests the functionality of the next-method.
     */
    @SuppressWarnings("unchecked")
    public final void testNext() {
        ListIterator<Group<Object, Object>> listIterator = mock(ListIterator.class);
        GroupListIterator<Object, Object> groupListIterator =
                new GroupListIterator<>(listIterator, getContext());
        Group<Object, Object> nextGroup = new Group<>(new Object());
        when(listIterator.next()).thenReturn(nextGroup);
        Object nextData = groupListIterator.next();
        assertEquals(nextGroup.getData(), nextData);
        verify(listIterator, times(1)).next();
    }

    /**
     * Tests the functionality of the nextIndex-method.
     */
    @SuppressWarnings("unchecked")
    public final void testNextIndex() {
        ListIterator<Group<Object, Object>> listIterator = mock(ListIterator.class);
        GroupListIterator<Object, Object> groupListIterator =
                new GroupListIterator<>(listIterator, getContext());
        int nextIndex = 1;
        when(groupListIterator.nextIndex()).thenReturn(nextIndex);
        Object next = groupListIterator.nextIndex();
        assertEquals(nextIndex, next);
        verify(listIterator, times(1)).nextIndex();
    }

    /**
     * Tests the functionality of the previous-method.
     */
    @SuppressWarnings("unchecked")
    public final void testPrevious() {
        ListIterator<Group<Object, Object>> listIterator = mock(ListIterator.class);
        GroupListIterator<Object, Object> groupListIterator =
                new GroupListIterator<>(listIterator, getContext());
        Group<Object, Object> previousGroup = new Group<>(new Object());
        when(listIterator.previous()).thenReturn(previousGroup);
        Object previousData = groupListIterator.previous();
        assertEquals(previousGroup.getData(), previousData);
        verify(listIterator, times(1)).previous();
    }

    /**
     * Tests the functionality of the previousIndex-method.
     */
    @SuppressWarnings("unchecked")
    public final void testPreviousIndex() {
        ListIterator<Group<Object, Object>> listIterator = mock(ListIterator.class);
        GroupListIterator<Object, Object> groupListIterator =
                new GroupListIterator<>(listIterator, getContext());
        int previousIndex = 1;
        when(groupListIterator.previousIndex()).thenReturn(previousIndex);
        Object previous = groupListIterator.previousIndex();
        assertEquals(previousIndex, previous);
        verify(listIterator, times(1)).previousIndex();
    }

    /**
     * Tests the functionality of the remove-method.
     */
    @SuppressWarnings("unchecked")
    public final void testRemove() {
        ListIterator<Group<Object, Object>> listIterator = mock(ListIterator.class);
        GroupListIterator<Object, Object> groupListIterator =
                new GroupListIterator<>(listIterator, getContext());
        groupListIterator.remove();
        verify(listIterator, times(1)).remove();
    }

    /**
     * Tests the functionality of the set-method.
     */
    @SuppressWarnings("unchecked")
    public final void testSet() {
        ListIterator<Group<Object, Object>> listIterator = mock(ListIterator.class);
        Context context = getContext();
        GroupListIterator<Object, Object> groupListIterator =
                new GroupListIterator<>(listIterator, context);
        Object group = new Object();
        groupListIterator.set(group);
        verify(listIterator, times(1)).set(any(Group.class));
    }

}