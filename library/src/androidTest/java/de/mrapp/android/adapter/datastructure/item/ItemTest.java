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
package de.mrapp.android.adapter.datastructure.item;

import android.os.Bundle;
import android.os.Parcel;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

/**
 * Tests the functionality of the class {@link Item}.
 *
 * @author Michael Rapp
 */
@RunWith(AndroidJUnit4.class)
public class ItemTest {

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
    public final void testConstructor() {
        Object data = new Object();
        Item<Object> item = new Item<>(data);
        assertEquals(item.getData(), data);
        assertEquals(item.getState(), 0);
        assertEquals(item.isEnabled(), true);
        assertEquals(item.isSelected(), false);
    }

    @Test
    public final void testSetSelected() {
        boolean selected = true;
        Item<Object> item = new Item<>(new Object());
        item.setSelected(selected);
        assertEquals(item.isSelected(), selected);
    }

    @Test
    public final void testSetEnabled() {
        boolean enabled = false;
        Item<Object> item = new Item<>(new Object());
        item.setEnabled(enabled);
        assertEquals(item.isEnabled(), enabled);
    }

    @Test
    public final void testSetState() {
        int state = 1;
        Item<Object> item = new Item<>(new Object());
        item.setState(state);
        assertEquals(item.getState(), state);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testSetStateToLessThanZeroThrowsException() {
        Item<Object> item = new Item<>(new Object());
        item.setState(-1);
    }

    @Test
    public final void testClone() throws CloneNotSupportedException {
        CloneableImplementation data = new CloneableImplementation();
        boolean enabled = false;
        boolean selected = true;
        int state = 1;
        Item<CloneableImplementation> item = new Item<>(data);
        item.setEnabled(enabled);
        item.setSelected(selected);
        item.setState(state);
        Item<CloneableImplementation> clonedItem = item.clone();
        assertNotNull(clonedItem.getData());
        assertNotSame(clonedItem.getData(), data);
        assertEquals(clonedItem.isEnabled(), enabled);
        assertEquals(clonedItem.isSelected(), selected);
        assertEquals(clonedItem.getState(), state);
    }

    @Test(expected = CloneNotSupportedException.class)
    public final void testCloneThrowsCloneNotSupportedException()
            throws CloneNotSupportedException {
        Item<Object> item = new Item<>(new Object());
        item.clone();
    }

    @Test
    public final void testToString() {
        Object data = new Object();
        boolean enabled = true;
        boolean selected = true;
        int state = 1;
        Item<Object> item = new Item<>(data);
        item.setEnabled(enabled);
        item.setSelected(selected);
        item.setState(state);
        assertEquals(item.toString(),
                "Item [data=" + data + ", selected=" + selected + ", enabled=" + enabled +
                        ", state=" + state + "]");
    }

    @Test
    public final void testHashCode() {
        Object data = new Object();
        Item<Object> item1 = new Item<>(data);
        Item<Object> item2 = new Item<>(data);
        assertEquals(item1.hashCode(), item1.hashCode());
        assertEquals(item2.hashCode(), item2.hashCode());
        item1.setEnabled(false);
        assertNotSame(item1.hashCode(), item2.hashCode());
        item2.setEnabled(item1.isEnabled());
        item1.setSelected(true);
        assertNotSame(item1.hashCode(), item2.hashCode());
        item2.setSelected(item1.isSelected());
        item1.setState(1);
        assertNotSame(item1.hashCode(), item2.hashCode());
    }

    @Test
    public final void testEquals() {
        Object data = new Object();
        Item<Object> item1 = new Item<>(data);
        Item<Object> item2 = new Item<>(data);
        assertEquals(item1.equals(null), false);
        assertEquals(item1.equals(new Object()), false);
        assertEquals(item1.equals(item1), true);
        assertEquals(item1.equals(item2), true);
        item1.setEnabled(false);
        assertEquals(item1.equals(item2), false);
        item2.setEnabled(item1.isEnabled());
        item1.setSelected(true);
        assertEquals(item1.equals(item2), false);
        item2.setSelected(item1.isSelected());
        item1.setState(1);
        assertEquals(item1.equals(item2), false);
    }

    @Test
    public final void testCreatorCreateFromParcel() {
        boolean enabled = false;
        boolean selected = true;
        int state = 1;
        Bundle data = new Bundle();
        int parcelableValue = 1;
        data.putInt("key", parcelableValue);
        Item<Bundle> item = new Item<>(data);
        item.setEnabled(enabled);
        item.setSelected(selected);
        item.setState(state);
        Parcel parcel = Parcel.obtain();
        item.writeToParcel(parcel, 1);
        parcel.setDataPosition(0);
        Item<?> restoredItem = Item.CREATOR.createFromParcel(parcel);
        assertEquals(enabled, restoredItem.isEnabled());
        assertEquals(selected, restoredItem.isSelected());
        assertEquals(state, restoredItem.getState());
        assertEquals(parcelableValue, ((Bundle) restoredItem.getData()).getInt("key"));
        parcel.recycle();
    }

    @Test
    public final void testCreatorNewArray() {
        int size = 1;
        Item<?>[] array = Item.CREATOR.newArray(size);
        assertEquals(size, array.length);
    }

}