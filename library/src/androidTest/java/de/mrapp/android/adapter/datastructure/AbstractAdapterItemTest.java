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
package de.mrapp.android.adapter.datastructure;

import android.os.Parcel;

import junit.framework.Assert;
import junit.framework.TestCase;

import de.mrapp.android.adapter.Filterable;
import de.mrapp.android.adapter.FilteringNotSupportedException;
import de.mrapp.android.adapter.SortingNotSupportedException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the functionality of the class {@link AbstractAdapterItem}.
 *
 * @author Michael Rapp
 */
public class AbstractAdapterItemTest extends TestCase {

    /**
     * Tests, if all properties are set correctly by the constructor.
     */
    public final void testConstructor() {
        Object data = new Object();
        AbstractAdapterItem<Object> abstractAdapterItem =
                new AbstractAdapterItemImplementation<>(data);
        assertEquals(abstractAdapterItem.getData(), data);
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown by the constructor, if the data is
     * null.
     */
    public final void testConstructorThrowsException() {
        try {
            new AbstractAdapterItemImplementation<>(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the method, which allows to set the item's data.
     */
    public final void testSetData() {
        Object data = new Object();
        AbstractAdapterItem<Object> abstractAdapterItem =
                new AbstractAdapterItemImplementation<>(data);
        abstractAdapterItem.setData(data);
        assertEquals(abstractAdapterItem.getData(), data);
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the item's data is set to null.
     */
    public final void testSetDataToNullThrowsException() {
        try {
            new AbstractAdapterItemImplementation<>(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Tests the functionality of the compareTo-method.
     */
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

    /**
     * Ensures, that a {@link SortingNotSupportedException} is thrown, if the item is compared to
     * another item whose data does not implement the interface {@link Comparable}.
     */
    public final void testCompareToThrowsSortingNotSupportedException() {
        try {
            AbstractAdapterItem<Object> abstractAdapterItem =
                    new AbstractAdapterItemImplementation<>(new Object());
            abstractAdapterItem.compareTo(new AbstractAdapterItemImplementation<>(new Object()));
            Assert.fail();
        } catch (SortingNotSupportedException e) {

        }
    }

    /**
     * Tests the functionality of the match-method.
     */
    public final void testMatch() {
        Filterable filterable = mock(Filterable.class);
        String query = "query";
        int flags = 0;
        AbstractAdapterItem<Filterable> abstractAdapterItem =
                new AbstractAdapterItemImplementation<>(filterable);
        when(abstractAdapterItem.match(query, flags)).thenReturn(true);
        assertTrue(abstractAdapterItem.match(query, flags));
    }

    /**
     * Ensures, that a {@link FilteringNotSupportedException} is thrown, if filtering is not
     * supported by the item's data.
     */
    public final void testMatchThrowsFilteringNotSupportedException() {
        try {
            AbstractAdapterItem<Object> abstractAdapterItem =
                    new AbstractAdapterItemImplementation<>(new Object());
            abstractAdapterItem.match("query", 0);
            Assert.fail();
        } catch (FilteringNotSupportedException e) {

        }
    }

    /**
     * Tests the functionality of the hashCode-method.
     */
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

    /**
     * Tests the functionality of the equals-method.
     */
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

    /**
     * Tests the functionality of the describeContents-method.
     */
    public final void testDescribeContents() {
        AbstractAdapterItem<Object> abstractAdapterItem =
                new AbstractAdapterItemImplementation<>(new Object());
        assertEquals(0, abstractAdapterItem.describeContents());
    }

}