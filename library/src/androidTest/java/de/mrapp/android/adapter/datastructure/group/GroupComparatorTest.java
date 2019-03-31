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

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Comparator;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the functionality of the class {@link GroupComparator}.
 *
 * @author Michael Rapp
 */
@RunWith(AndroidJUnit4.class)
public class GroupComparatorTest {

    @SuppressWarnings("unchecked")
    @Test
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