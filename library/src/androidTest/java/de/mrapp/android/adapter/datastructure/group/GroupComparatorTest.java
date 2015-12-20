/*
 * AndroidAdapters Copyright 2014 - 2015 Michael Rapp
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

import java.util.Comparator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the functionality of the class {@link GroupComparator}.
 *
 * @author Michael Rapp
 */
public class GroupComparatorTest extends TestCase {

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the comparator's comparable is set
     * to null.
     */
    public final void testSetComparableToNullThrowsException() {
        try {
            new GroupComparator<>(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the compare-method.
     */
    @SuppressWarnings("unchecked")
    public final void testCompare() {
        Group<Object, Object> group = new Group<>(new Object());
        Comparator<Object> comparator = mock(Comparator.class);
        int mockedResult = 0;
        when(comparator.compare(group.getData(), group.getData())).thenReturn(mockedResult);
        GroupComparator<Object, Object> groupComparator = new GroupComparator<>(comparator);
        int result = groupComparator.compare(group, group);
        assertEquals(result, mockedResult);
    }

}