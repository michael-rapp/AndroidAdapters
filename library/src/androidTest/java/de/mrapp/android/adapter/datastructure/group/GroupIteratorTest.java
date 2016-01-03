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