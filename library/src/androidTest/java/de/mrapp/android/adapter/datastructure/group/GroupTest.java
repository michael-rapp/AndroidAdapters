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

import android.os.Bundle;
import android.os.Parcel;
import android.test.AndroidTestCase;

import junit.framework.Assert;

import de.mrapp.android.adapter.MultipleChoiceListAdapter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the functionality of the class {@link Group}.
 *
 * @author Michael Rapp
 */
public class GroupTest extends AndroidTestCase {

    /**
     * An implementation of the interface {@link Cloneable}, which is needed for test purposes.
     */
    private class CloneableImplementation implements Cloneable {

        @Override
        public final CloneableImplementation clone() throws CloneNotSupportedException {
            return new CloneableImplementation();
        }

    }

    /**
     * Tests, if all properties are set correctly by the constructor, which expects the group's data
     * as a parameter.
     */
    public final void testConstructorWithDataParameter() {
        Object data = new Object();
        Group<Object, Object> group = new Group<>(data);
        assertEquals(data, group.getData());
        assertNull(group.getChildAdapter());
    }

    /**
     * Tests, if all properties are set correctly by the constructor, which expects the group's data
     * and a child adapter as parameters.
     */
    @SuppressWarnings("unchecked")
    public final void testConstructorWithDataAndChildAdapterParameter() {
        MultipleChoiceListAdapter<Object> childAdapter = mock(MultipleChoiceListAdapter.class);
        Object data = new Object();
        Group<Object, Object> group = new Group<>(data, childAdapter);
        assertEquals(data, group.getData());
        assertEquals(childAdapter, group.getChildAdapter());
    }

    /**
     * Tests the functionality of the method, which allows to set the group's child adapter.
     */
    @SuppressWarnings("unchecked")
    public final void testSetChildAdapter() {
        MultipleChoiceListAdapter<Object> childAdapter = mock(MultipleChoiceListAdapter.class);
        Group<Object, Object> group = new Group<>(new Object());
        group.setChildAdapter(childAdapter);
        assertEquals(childAdapter, group.getChildAdapter());
    }

    /**
     * Tests the functionality of the clone-method.
     *
     * @throws CloneNotSupportedException
     *         The exception, which is thrown, if cloning is not supported
     */
    @SuppressWarnings("unchecked")
    public final void testClone() throws CloneNotSupportedException {
        MultipleChoiceListAdapter<CloneableImplementation> childAdapter =
                mock(MultipleChoiceListAdapter.class);
        when(childAdapter.clone()).thenReturn(mock(MultipleChoiceListAdapter.class));
        CloneableImplementation data = new CloneableImplementation();
        Group<CloneableImplementation, CloneableImplementation> group =
                new Group<>(data, childAdapter);
        Group<CloneableImplementation, CloneableImplementation> clonedGroup = group.clone();
        assertNotNull(clonedGroup.getData());
        assertNotSame(clonedGroup.getData(), data);
        assertNotNull(clonedGroup.getChildAdapter());
        assertNotSame(clonedGroup.getChildAdapter(), childAdapter);
    }

    /**
     * Tests the functionality of the clone-method, if the adapter, which manages the group's child
     * items, is null.
     *
     * @throws CloneNotSupportedException
     *         The exception, which is thrown, if cloning is not supported
     */
    public final void testCloneWhenChildAdapterIsNull() throws CloneNotSupportedException {
        CloneableImplementation data = new CloneableImplementation();
        Group<CloneableImplementation, CloneableImplementation> group = new Group<>(data);
        Group<CloneableImplementation, CloneableImplementation> clonedGroup = group.clone();
        assertNotNull(clonedGroup.getData());
        assertNotSame(clonedGroup.getData(), data);
        assertNull(clonedGroup.getChildAdapter());
    }

    /**
     * Ensures, that a {@link CloneNotSupportedException} is thrown, if cloning is not supported by
     * the item's data.
     */
    public final void testCloneThrowsCloneNotSupportedException() {
        try {
            Object data = new Object();
            Group<Object, Object> group = new Group<>(data);
            group.clone();
            Assert.fail();
        } catch (CloneNotSupportedException e) {

        }
    }

    /**
     * Tests the functionality of the match-method.
     */
    @SuppressWarnings("unchecked")
    public final void testMatch() {
        Group<Object, Object> group = new Group<>(new Object());
        MultipleChoiceListAdapter<Object> childAdapter = mock(MultipleChoiceListAdapter.class);
        group.setChildAdapter(childAdapter);
        when(childAdapter.isEmpty()).thenReturn(true);
        assertFalse(group.match("", Group.FLAG_FILTER_EMPTY_GROUPS));
    }

    /**
     * Tests the functionality of the match-method, if the child adapter is null.
     */
    public final void testMatchIfChildAdapterIsNull() {
        Group<Object, Object> group = new Group<>(new Object());
        assertFalse(group.match("", Group.FLAG_FILTER_EMPTY_GROUPS));
    }

    /**
     * Tests the functionality of the toString-method.
     */
    public final void testToString() {
        Object data = new Object();
        Group<Object, Object> group = new Group<>(data);
        assertEquals("Group [data=" + data + "]", group.toString());
    }

    /**
     * Tests the createFromParcel-method of the creator, which allows to create instances from a
     * {@link Parcel}.
     */
    @SuppressWarnings("unchecked")
    public final void testCreatorCreateFromParcel() {
        MultipleChoiceListAdapter<Bundle> childAdapter = mock(MultipleChoiceListAdapter.class);
        Bundle data = new Bundle();
        int parcelableValue = 1;
        data.putInt("key", parcelableValue);
        Group<Bundle, Bundle> group = new Group<>(data, childAdapter);
        Parcel parcel = Parcel.obtain();
        group.writeToParcel(parcel, 1);
        parcel.setDataPosition(0);
        Group<?, ?> restoredGroup = Group.CREATOR.createFromParcel(parcel);
        assertEquals(parcelableValue, ((Bundle) restoredGroup.getData()).getInt("key"));
        assertNull(restoredGroup.getChildAdapter());
        parcel.recycle();
    }

    /**
     * Tests the newArray-method of the creator, which allows to create instances from a {@link
     * Parcel}.
     */
    public final void testCreatorNewArray() {
        int size = 1;
        Group<?, ?>[] array = Group.CREATOR.newArray(size);
        assertEquals(size, array.length);
    }

}