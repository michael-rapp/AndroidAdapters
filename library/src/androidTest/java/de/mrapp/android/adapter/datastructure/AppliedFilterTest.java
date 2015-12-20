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
package de.mrapp.android.adapter.datastructure;

import android.os.Parcel;

import junit.framework.Assert;
import junit.framework.TestCase;

import de.mrapp.android.adapter.Filter;

import static de.mrapp.android.util.ClassUtil.getTruncatedName;

/**
 * Tests the functionality of the class {@link AppliedFilter}.
 *
 * @author Michael Rapp
 */
public class AppliedFilterTest extends TestCase {

    /**
     * Tests, if all properties are set correctly by the constructor, which expects a query and
     * flags as parameters.
     */
    public final void testConstructorWithQueryAndFlagsParameters() {
        String query = "query";
        int flags = 1;
        AppliedFilter<Object> appliedFilter = new AppliedFilter<>(query, flags);
        assertEquals(query, appliedFilter.getQuery());
        assertEquals(flags, appliedFilter.getFlags());
    }

    /**
     * Tests, if all properties are set correctly by the constructor, which expects a query, flags
     * and a filter as parameters.
     */
    public final void testConstructorWithQueryFlagsAndFilterParameters() {
        String query = "query";
        int flags = 1;
        Filter<Object> filter = new FilterImplementation();
        AppliedFilter<Object> appliedFilter = new AppliedFilter<>(query, flags, filter);
        assertEquals(query, appliedFilter.getQuery());
        assertEquals(flags, appliedFilter.getFlags());
        assertEquals(filter, appliedFilter.getFilter());
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructors, if the query,
     * which is passed as a parameter, is null.
     */
    public final void testConstructorThrowsExceptionWhenQueryIsNull() {
        try {
            new AppliedFilter<>(null, 1);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the toString-method.
     */
    public final void testToString() {
        String query = "query";
        int flags = 1;
        Filter<Object> filter = new FilterImplementation();
        AppliedFilter<Object> appliedFilter = new AppliedFilter<>(query, flags);
        assertEquals("AppliedFilter [query=" + query + ", flags=" + flags + "]",
                appliedFilter.toString());
        appliedFilter = new AppliedFilter<>(query, flags, filter);
        assertEquals("AppliedFilter [query=" + query + ", flags=" + flags + ", filter=" +
                getTruncatedName(filter.getClass()) + "]", appliedFilter.toString());
    }

    /**
     * Tests the functionality of the hashCode-method.
     */
    public final void testHashCode() {
        AppliedFilter<Object> appliedFilter1 = new AppliedFilter<>("query", 0);
        AppliedFilter<Object> appliedFilter2 = new AppliedFilter<>("query", 0);
        assertEquals(appliedFilter1.hashCode(), appliedFilter1.hashCode());
        assertEquals(appliedFilter1.hashCode(), appliedFilter2.hashCode());
        appliedFilter1 = new AppliedFilter<>("foo", 0);
        assertNotSame(appliedFilter1.hashCode(), appliedFilter2.hashCode());
        appliedFilter1 = new AppliedFilter<>("query", 1);
        assertNotSame(appliedFilter1.hashCode(), appliedFilter2.hashCode());
        appliedFilter1 = new AppliedFilter<>("query", 0, new FilterImplementation());
        assertNotSame(appliedFilter1.hashCode(), appliedFilter2.hashCode());
    }

    /**
     * Tests the functionality of the equals-method.
     */
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
        appliedFilter1 = new AppliedFilter<>("query", 0, new FilterImplementation());
        assertFalse(appliedFilter1.equals(appliedFilter2));
    }

    /**
     * Tests the functionality of the clone-method.
     */
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

    /**
     * Tests the functionality of the describeContents-method.
     */
    public final void testDescribeContents() {
        AppliedFilter<Object> appliedFilter =
                new AppliedFilter<>("query", 0, new FilterImplementation());
        assertEquals(0, appliedFilter.describeContents());
    }

    /**
     * Tests the createFromParcel-method of the creator, which allows to create instances from a
     * {@link Parcel}.
     */
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

    /**
     * Tests the newArray-method of the creator, which allows to create instances from a {@link
     * Parcel}.
     */
    public final void testCreatorNewArray() {
        int size = 1;
        AppliedFilter<?>[] array = AppliedFilter.CREATOR.newArray(size);
        assertEquals(size, array.length);
    }

}