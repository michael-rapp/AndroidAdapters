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

import de.mrapp.android.adapter.Filter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the functionality of the class {@link GroupFilter}.
 *
 * @author Michael Rapp
 */
public class GroupFilterTest extends TestCase {

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the filter's filter is set to
     * null.
     */
    public final void testSetFilterToNullThrowsException() {
        try {
            new GroupFilter<>(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the match-method.
     */
    @SuppressWarnings("unchecked")
    public final void testMatch() {
        Group<Object, Object> group = new Group<>(new Object());
        Filter<Object> filter = mock(Filter.class);
        String query = "query";
        int flags = 1;
        boolean mockedResult = true;
        when(filter.match(group.getData(), query, flags)).thenReturn(mockedResult);
        GroupFilter<Object, Object> groupFilter = new GroupFilter<>(filter);
        boolean result = groupFilter.match(group, query, flags);
        assertEquals(mockedResult, result);
    }

}