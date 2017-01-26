/*
 * Copyright 2014 - 2017 Michael Rapp
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

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.Iterator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests the functionality of the method {@link GroupIterator}.
 *
 * @author Michael Rapp
 */
public class GroupIteratorTest extends TestCase {

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the iterator, which is passed as a
     * constructor parameter, is null.
     */
    public final void testConstructorThrowsException() {
        try {
            new GroupIterator<>(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the hasNext-method.
     */
    @SuppressWarnings("unchecked")
    public final void testHasNext() {
        Iterator<Group<Object, Object>> iterator = mock(Iterator.class);
        GroupIterator<Object, Object> groupIterator = new GroupIterator<>(iterator);
        when(iterator.hasNext()).thenReturn(true);
        boolean hasNext = groupIterator.hasNext();
        assertTrue(hasNext);
        verify(iterator, times(1)).hasNext();
    }

    /**
     * Tests the functionality of the next-method.
     */
    @SuppressWarnings("unchecked")
    public final void testNext() {
        Iterator<Group<Object, Object>> iterator = mock(Iterator.class);
        GroupIterator<Object, Object> groupIterator = new GroupIterator<>(iterator);
        Group<Object, Object> nextGroup = new Group<>(new Object());
        when(iterator.next()).thenReturn(nextGroup);
        Object nextData = groupIterator.next();
        assertEquals(nextGroup.getData(), nextData);
        verify(iterator, times(1)).next();
    }

    /**
     * Tests the functionality of the remove-method.
     */
    @SuppressWarnings("unchecked")
    public final void testRemove() {
        Iterator<Group<Object, Object>> iterator = mock(Iterator.class);
        GroupIterator<Object, Object> groupIterator = new GroupIterator<>(iterator);
        groupIterator.remove();
        verify(iterator, times(1)).remove();
    }

}