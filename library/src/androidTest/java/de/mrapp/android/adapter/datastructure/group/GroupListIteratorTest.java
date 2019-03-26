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

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ListIterator;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
@RunWith(AndroidJUnit4.class)
public class GroupListIteratorTest {

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionIfListIteratorIsNull() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        new GroupListIterator<>(null, context);
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testAdd() {
        ListIterator<Group<Object, Object>> listIterator = mock(ListIterator.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        GroupListIterator<Object, Object> groupListIterator =
                new GroupListIterator<>(listIterator, context);
        Object group = new Object();
        groupListIterator.add(group);
        verify(listIterator, times(1)).add(any(Group.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testHasNext() {
        ListIterator<Group<Object, Object>> listIterator = mock(ListIterator.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        GroupListIterator<Object, Object> groupListIterator =
                new GroupListIterator<>(listIterator, context);
        when(groupListIterator.hasNext()).thenReturn(true);
        boolean hasNext = groupListIterator.hasNext();
        assertTrue(hasNext);
        verify(listIterator, times(1)).hasNext();
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testHasPrevious() {
        ListIterator<Group<Object, Object>> listIterator = mock(ListIterator.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        GroupListIterator<Object, Object> groupListIterator =
                new GroupListIterator<>(listIterator, context);
        when(groupListIterator.hasPrevious()).thenReturn(true);
        boolean hasPrevious = groupListIterator.hasPrevious();
        assertTrue(hasPrevious);
        verify(listIterator, times(1)).hasPrevious();
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testNext() {
        ListIterator<Group<Object, Object>> listIterator = mock(ListIterator.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        GroupListIterator<Object, Object> groupListIterator =
                new GroupListIterator<>(listIterator, context);
        Group<Object, Object> nextGroup = new Group<>(new Object());
        when(listIterator.next()).thenReturn(nextGroup);
        Object nextData = groupListIterator.next();
        assertEquals(nextGroup.getData(), nextData);
        verify(listIterator, times(1)).next();
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testNextIndex() {
        ListIterator<Group<Object, Object>> listIterator = mock(ListIterator.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        GroupListIterator<Object, Object> groupListIterator =
                new GroupListIterator<>(listIterator, context);
        int nextIndex = 1;
        when(groupListIterator.nextIndex()).thenReturn(nextIndex);
        Object next = groupListIterator.nextIndex();
        assertEquals(nextIndex, next);
        verify(listIterator, times(1)).nextIndex();
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testPrevious() {
        ListIterator<Group<Object, Object>> listIterator = mock(ListIterator.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        GroupListIterator<Object, Object> groupListIterator =
                new GroupListIterator<>(listIterator, context);
        Group<Object, Object> previousGroup = new Group<>(new Object());
        when(listIterator.previous()).thenReturn(previousGroup);
        Object previousData = groupListIterator.previous();
        assertEquals(previousGroup.getData(), previousData);
        verify(listIterator, times(1)).previous();
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testPreviousIndex() {
        ListIterator<Group<Object, Object>> listIterator = mock(ListIterator.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        GroupListIterator<Object, Object> groupListIterator =
                new GroupListIterator<>(listIterator, context);
        int previousIndex = 1;
        when(groupListIterator.previousIndex()).thenReturn(previousIndex);
        Object previous = groupListIterator.previousIndex();
        assertEquals(previousIndex, previous);
        verify(listIterator, times(1)).previousIndex();
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testRemove() {
        ListIterator<Group<Object, Object>> listIterator = mock(ListIterator.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        GroupListIterator<Object, Object> groupListIterator =
                new GroupListIterator<>(listIterator, context);
        groupListIterator.remove();
        verify(listIterator, times(1)).remove();
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSet() {
        ListIterator<Group<Object, Object>> listIterator = mock(ListIterator.class);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        GroupListIterator<Object, Object> groupListIterator =
                new GroupListIterator<>(listIterator, context);
        Object group = new Object();
        groupListIterator.set(group);
        verify(listIterator, times(1)).set(any(Group.class));
    }

}