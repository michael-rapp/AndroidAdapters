/*
 * Copyright 2014 - 2018 Michael Rapp
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