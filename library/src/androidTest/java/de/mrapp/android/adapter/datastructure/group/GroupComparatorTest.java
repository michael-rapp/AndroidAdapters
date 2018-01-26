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