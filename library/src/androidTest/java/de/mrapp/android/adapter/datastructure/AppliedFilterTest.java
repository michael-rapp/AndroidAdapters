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

import android.os.Parcel;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import de.mrapp.android.adapter.Filter;
import de.mrapp.util.ClassUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

/**
 * Tests the functionality of the class {@link AppliedFilter}.
 *
 * @author Michael Rapp
 */
@RunWith(AndroidJUnit4.class)
public class AppliedFilterTest {

    @Test
    public final void testConstructorWithQueryAndFlagsParameters() {
        String query = "query";
        int flags = 1;
        AppliedFilter<Object> appliedFilter = new AppliedFilter<>(query, flags);
        assertEquals(query, appliedFilter.getQuery());
        assertEquals(flags, appliedFilter.getFlags());
    }

    @Test
    public final void testConstructorWithQueryFlagsAndFilterParameters() {
        String query = "query";
        int flags = 1;
        Filter<Object> filter = new FilterImplementation();
        AppliedFilter<Object> appliedFilter = new AppliedFilter<>(query, flags, filter);
        assertEquals(query, appliedFilter.getQuery());
        assertEquals(flags, appliedFilter.getFlags());
        assertEquals(filter, appliedFilter.getFilter());
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testConstructorThrowsExceptionWhenQueryIsNull() {
        new AppliedFilter<>(null, 1);
    }

    @Test
    public final void testToString() {
        String query = "query";
        int flags = 1;
        Filter<Object> filter = new FilterImplementation();
        AppliedFilter<Object> appliedFilter = new AppliedFilter<>(query, flags);
        assertEquals("AppliedFilter [query=" + query + ", flags=" + flags + "]",
                appliedFilter.toString());
        appliedFilter = new AppliedFilter<>(query, flags, filter);
        assertEquals("AppliedFilter [query=" + query + ", flags=" + flags + ", filter=" +
                        ClassUtil.INSTANCE.getTruncatedName(filter.getClass()) + "]",
                appliedFilter.toString());
    }

    @Test
    public final void testHashCode() {
        AppliedFilter<Object> appliedFilter1 = new AppliedFilter<>("query", 0);
        AppliedFilter<Object> appliedFilter2 = new AppliedFilter<>("query", 0);
        assertEquals(appliedFilter1.hashCode(), appliedFilter1.hashCode());
        assertEquals(appliedFilter1.hashCode(), appliedFilter2.hashCode());
        appliedFilter1 = new AppliedFilter<>("foo", 0);
        assertNotSame(appliedFilter1.hashCode(), appliedFilter2.hashCode());
        appliedFilter1 = new AppliedFilter<>("query", 1);
        assertNotSame(appliedFilter1.hashCode(), appliedFilter2.hashCode());
    }

    @Test
    public final void testEquals() {
        AppliedFilter<Object> appliedFilter1 = new AppliedFilter<>("query", 0);
        AppliedFilter<Object> appliedFilter2 = new AppliedFilter<>("query", 0);
        assertTrue(appliedFilter1.equals(appliedFilter1));
        assertTrue(appliedFilter1.equals(appliedFilter2));
        assertFalse(appliedFilter1.equals(null));
        assertFalse(appliedFilter1.equals(new Object()));
        appliedFilter1 = new AppliedFilter<>("foo", 0);
        assertFalse(appliedFilter1.equals(appliedFilter2));
        appliedFilter1 = new AppliedFilter<>("query", 1);
        assertFalse(appliedFilter1.equals(appliedFilter2));
    }

    @Test
    public final void testClone() {
        String query = "query";
        int flags = 1;
        Filter<Object> filter = new FilterImplementation();
        AppliedFilter<Object> appliedFilter = new AppliedFilter<>(query, flags, filter);
        AppliedFilter<Object> clonedAppliedFilter = appliedFilter.clone();
        assertEquals(query, clonedAppliedFilter.getQuery());
        assertEquals(flags, clonedAppliedFilter.getFlags());
        assertEquals(filter, clonedAppliedFilter.getFilter());
    }

    @Test
    public final void testDescribeContents() {
        AppliedFilter<Object> appliedFilter =
                new AppliedFilter<>("query", 0, new FilterImplementation());
        assertEquals(0, appliedFilter.describeContents());
    }

    @Test
    public final void testCreatorCreateFromParcel() {
        String query = "query";
        int flags = 1;
        Filter<Object> filter = new FilterImplementation();
        AppliedFilter<Object> appliedFilter = new AppliedFilter<>(query, flags, filter);
        Parcel parcel = Parcel.obtain();
        appliedFilter.writeToParcel(parcel, 1);
        parcel.setDataPosition(0);
        AppliedFilter<?> restoredAppliedFilter = AppliedFilter.CREATOR.createFromParcel(parcel);
        assertEquals(query, restoredAppliedFilter.getQuery());
        assertEquals(flags, restoredAppliedFilter.getFlags());
        assertNotNull(restoredAppliedFilter.getFilter());
        parcel.recycle();
    }

    @Test
    public final void testCreatorNewArray() {
        int size = 1;
        AppliedFilter<?>[] array = AppliedFilter.CREATOR.newArray(size);
        assertEquals(size, array.length);
    }

}