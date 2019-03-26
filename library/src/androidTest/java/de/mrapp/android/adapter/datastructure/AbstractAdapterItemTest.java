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
package de.mrapp.android.adapter.datastructure;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import de.mrapp.android.adapter.Filterable;
import de.mrapp.android.adapter.FilteringNotSupportedException;
import de.mrapp.android.adapter.SortingNotSupportedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the functionality of the class {@link AbstractAdapterItem}.
 *
 * @author Michael Rapp
 */
@RunWith(AndroidJUnit4.class)
public class AbstractAdapterItemTest {

    @Test
    public final void testConstructor() {
        Object data = new Object();
        AbstractAdapterItem<Object> abstractAdapterItem =
                new AbstractAdapterItemImplementation<>(data);
        assertEquals(abstractAdapterItem.getData(), data);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsException() {
        new AbstractAdapterItemImplementation<>(null);
    }

    @Test
    public final void testSetData() {
        Object data = new Object();
        AbstractAdapterItem<Object> abstractAdapterItem =
                new AbstractAdapterItemImplementation<>(data);
        abstractAdapterItem.setData(data);
        assertEquals(abstractAdapterItem.getData(), data);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testSetDataToNullThrowsException() {
        new AbstractAdapterItemImplementation<>(null);
    }

    @Test
    public final void testCompareTo() {
        int mockedResult = 1;
        Comparable<?> data = mock(Comparable.class);
        AbstractAdapterItem<Comparable<?>> abstractAdapterItem1 =
                new AbstractAdapterItemImplementation<Comparable<?>>(data);
        AbstractAdapterItem<Comparable<?>> abstractAdapterItem2 =
                new AbstractAdapterItemImplementation<Comparable<?>>(data);
        when(abstractAdapterItem1.compareTo(abstractAdapterItem2)).thenReturn(mockedResult);
        int result = abstractAdapterItem1.compareTo(abstractAdapterItem2);
        assertEquals(result, mockedResult);
    }

    @Test(expected = SortingNotSupportedException.class)
    public final void testCompareToThrowsSortingNotSupportedException() {
        AbstractAdapterItem<Object> abstractAdapterItem =
                new AbstractAdapterItemImplementation<>(new Object());
        abstractAdapterItem.compareTo(new AbstractAdapterItemImplementation<>(new Object()));
    }

    @Test
    public final void testMatch() {
        Filterable filterable = mock(Filterable.class);
        String query = "query";
        int flags = 0;
        AbstractAdapterItem<Filterable> abstractAdapterItem =
                new AbstractAdapterItemImplementation<>(filterable);
        when(abstractAdapterItem.match(query, flags)).thenReturn(true);
        assertTrue(abstractAdapterItem.match(query, flags));
    }

    @Test(expected = FilteringNotSupportedException.class)
    public final void testMatchThrowsFilteringNotSupportedException() {
        AbstractAdapterItem<Object> abstractAdapterItem =
                new AbstractAdapterItemImplementation<>(new Object());
        abstractAdapterItem.match("query", 0);
    }

    @Test
    public final void testHashCode() {
        Object data = new Object();
        AbstractAdapterItem<Object> abstractAdapterItem1 =
                new AbstractAdapterItemImplementation<>(data);
        AbstractAdapterItem<Object> abstractAdapterItem2 =
                new AbstractAdapterItemImplementation<>(data);
        assertEquals(abstractAdapterItem1.hashCode(), abstractAdapterItem1.hashCode());
        assertEquals(abstractAdapterItem1.hashCode(), abstractAdapterItem2.hashCode());
        abstractAdapterItem1.setData(new Object());
        assertNotSame(abstractAdapterItem1.hashCode(), abstractAdapterItem2.hashCode());
    }

    @Test
    public final void testEquals() {
        Object data = new Object();
        AbstractAdapterItem<Object> abstractAdapterItem1 =
                new AbstractAdapterItemImplementation<>(data);
        AbstractAdapterItem<Object> abstractAdapterItem2 =
                new AbstractAdapterItemImplementation<>(data);
        assertTrue(abstractAdapterItem1.equals(abstractAdapterItem1));
        assertTrue(abstractAdapterItem1.equals(abstractAdapterItem2));
        assertFalse(abstractAdapterItem1.equals(null));
        assertFalse(abstractAdapterItem1.equals(new Object()));
        abstractAdapterItem1.setData(new Object());
        assertFalse(abstractAdapterItem1.equals(abstractAdapterItem2));
    }

    @Test
    public final void testDescribeContents() {
        AbstractAdapterItem<Object> abstractAdapterItem =
                new AbstractAdapterItemImplementation<>(new Object());
        assertEquals(0, abstractAdapterItem.describeContents());
    }

}