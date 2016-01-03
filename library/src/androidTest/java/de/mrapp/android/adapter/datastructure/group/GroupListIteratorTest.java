/*
 * AndroidAdapters Copyright 2014 - 2016 Michael Rapp
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