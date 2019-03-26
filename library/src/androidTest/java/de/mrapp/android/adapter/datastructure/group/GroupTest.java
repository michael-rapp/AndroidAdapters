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

import android.os.Bundle;
import android.os.Parcel;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the functionality of the class {@link Group}.
 *
 * @author Michael Rapp
 */
@RunWith(AndroidJUnit4.class)
public class GroupTest {

    /**
     * An implementation of the interface {@link Cloneable}, which is needed for test purposes.
     */
    private class CloneableImplementation implements Cloneable {

        @Override
        public final CloneableImplementation clone() throws CloneNotSupportedException {
            return new CloneableImplementation();
        }

    }

    @Test
    public final void testConstructorWithDataParameter() {
        Object data = new Object();
        Group<Object, Object> group = new Group<>(data);
        assertEquals(data, group.getData());
        assertNull(group.getChildAdapter());
        assertFalse(group.isExpanded());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testConstructorWithDataAndChildAdapterParameter() {
        MultipleChoiceListAdapter<Object> childAdapter = mock(MultipleChoiceListAdapter.class);
        Object data = new Object();
        Group<Object, Object> group = new Group<>(data, childAdapter);
        assertEquals(data, group.getData());
        assertEquals(childAdapter, group.getChildAdapter());
        assertFalse(group.isExpanded());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testSetChildAdapter() {
        MultipleChoiceListAdapter<Object> childAdapter = mock(MultipleChoiceListAdapter.class);
        Group<Object, Object> group = new Group<>(new Object());
        group.setChildAdapter(childAdapter);
        assertEquals(childAdapter, group.getChildAdapter());
    }

    @Test
    public final void testSetExpanded() {
        boolean expanded = true;
        Group<Object, Object> group = new Group<>(new Object());
        group.setExpanded(expanded);
        assertEquals(expanded, group.isExpanded());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testClone() throws CloneNotSupportedException {
        MultipleChoiceListAdapter<CloneableImplementation> childAdapter =
                mock(MultipleChoiceListAdapter.class);
        when(childAdapter.clone()).thenReturn(mock(MultipleChoiceListAdapter.class));
        CloneableImplementation data = new CloneableImplementation();
        boolean expanded = true;
        Group<CloneableImplementation, CloneableImplementation> group =
                new Group<>(data, childAdapter);
        group.setExpanded(expanded);
        Group<CloneableImplementation, CloneableImplementation> clonedGroup = group.clone();
        assertNotNull(clonedGroup.getData());
        assertNotSame(clonedGroup.getData(), data);
        assertNotNull(clonedGroup.getChildAdapter());
        assertNotSame(clonedGroup.getChildAdapter(), childAdapter);
        assertEquals(clonedGroup.isExpanded(), expanded);
    }

    @Test
    public final void testCloneWhenChildAdapterIsNull() throws CloneNotSupportedException {
        CloneableImplementation data = new CloneableImplementation();
        boolean expanded = true;
        Group<CloneableImplementation, CloneableImplementation> group = new Group<>(data);
        group.setExpanded(expanded);
        Group<CloneableImplementation, CloneableImplementation> clonedGroup = group.clone();
        assertNotNull(clonedGroup.getData());
        assertNotSame(clonedGroup.getData(), data);
        assertNull(clonedGroup.getChildAdapter());
        assertEquals(clonedGroup.isExpanded(), expanded);
    }

    @Test(expected = CloneNotSupportedException.class)
    public final void testCloneThrowsCloneNotSupportedException()
            throws CloneNotSupportedException {
        Object data = new Object();
        Group<Object, Object> group = new Group<>(data);
        group.clone();
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testMatch() {
        Group<Object, Object> group = new Group<>(new Object());
        MultipleChoiceListAdapter<Object> childAdapter = mock(MultipleChoiceListAdapter.class);
        group.setChildAdapter(childAdapter);
        when(childAdapter.isEmpty()).thenReturn(true);
        assertFalse(group.match("", Group.FLAG_FILTER_EMPTY_GROUPS));
    }

    @Test
    public final void testMatchIfChildAdapterIsNull() {
        Group<Object, Object> group = new Group<>(new Object());
        assertFalse(group.match("", Group.FLAG_FILTER_EMPTY_GROUPS));
    }

    @Test
    public final void testToString() {
        Object data = new Object();
        boolean expanded = true;
        Group<Object, Object> group = new Group<>(data);
        group.setExpanded(expanded);
        assertEquals("Group [data=" + data + ", expanded=" + expanded + "]", group.toString());
    }

    @Test
    public final void testHashCode() {
        Object data = new Object();
        Group<Object, Object> group1 = new Group<>(data);
        Group<Object, Object> group2 = new Group<>(data);
        assertEquals(group1.hashCode(), group1.hashCode());
        assertEquals(group1.hashCode(), group2.hashCode());
        group1.setExpanded(true);
        assertNotSame(group1.hashCode(), group2.hashCode());
    }

    @Test
    public final void testEquals() {
        Object data = new Object();
        Group<Object, Object> group1 = new Group<>(data);
        Group<Object, Object> group2 = new Group<>(data);
        assertTrue(group1.equals(group1));
        assertTrue(group1.equals(group2));
        assertFalse(group1.equals(null));
        assertFalse(group1.equals(new Object()));
        group1.setExpanded(true);
        assertFalse(group1.equals(group2));
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testCreatorCreateFromParcel() {
        Bundle data = new Bundle();
        int parcelableValue = 1;
        data.putInt("key", parcelableValue);
        boolean expanded = true;
        Group<Bundle, Bundle> group = new Group<>(data, null);
        group.setExpanded(expanded);
        Parcel parcel = Parcel.obtain();
        group.writeToParcel(parcel, 1);
        parcel.setDataPosition(0);
        Group<?, ?> restoredGroup = Group.CREATOR.createFromParcel(parcel);
        assertEquals(parcelableValue, ((Bundle) restoredGroup.getData()).getInt("key"));
        assertNull(restoredGroup.getChildAdapter());
        assertEquals(expanded, restoredGroup.isExpanded());
        parcel.recycle();
    }

    @Test
    public final void testCreatorNewArray() {
        int size = 1;
        Group<?, ?>[] array = Group.CREATOR.newArray(size);
        assertEquals(size, array.length);
    }

}