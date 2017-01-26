/*
 * Copyright 2014 - 2017 Michael Rapp
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

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Tests the functionality of the class {@link Item}.
 *
 * @author Michael Rapp
 */
public class ItemTest extends TestCase {

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
     * Tests, if all properties are set correctly by the constructor.
     */
    public final void testConstructor() {
        Object data = new Object();
        Item<Object> item = new Item<>(data);
        assertEquals(item.getData(), data);
        assertEquals(item.getState(), 0);
        assertEquals(item.isEnabled(), true);
        assertEquals(item.isSelected(), false);
    }

    /**
     * Tests the functionality of the method, which allows to set, whether the item is selected, or
     * not.
     */
    public final void testSetSelected() {
        boolean selected = true;
        Item<Object> item = new Item<>(new Object());
        item.setSelected(selected);
        assertEquals(item.isSelected(), selected);
    }

    /**
     * Tests the functionality of the method, which allows to set, whether the item is enabled, or
     * not.
     */
    public final void testSetEnabled() {
        boolean enabled = false;
        Item<Object> item = new Item<>(new Object());
        item.setEnabled(enabled);
        assertEquals(item.isEnabled(), enabled);
    }

    /**
     * Tests the functionality of the method, which allows to set the item's state.
     */
    public final void testSetState() {
        int state = 1;
        Item<Object> item = new Item<>(new Object());
        item.setState(state);
        assertEquals(item.getState(), state);
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown, if the item's state is set to a
     * value less than 0.
     */
    public final void testSetStateToLessThanZeroThrowsException() {
        try {
            Item<Object> item = new Item<>(new Object());
            item.setState(-1);
            Assert.fail();
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     * Tests the functionality of the clone-method.
     *
     * @throws CloneNotSupportedException
     *         The exception, which is thrown, if cloning is not supported
     */
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

    /**
     * Ensures, that a {@link CloneNotSupportedException} is thrown, if cloning is not supported by
     * the item's data.
     */
    public final void testCloneThrowsCloneNotSupportedException() {
        try {
            Item<Object> item = new Item<>(new Object());
            item.clone();
            Assert.fail();
        } catch (CloneNotSupportedException e) {

        }
    }

    /**
     * Tests the functionality of the toString-method.
     */
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

    /**
     * Tests the functionality of the hashCode-method.
     */
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

    /**
     * Tests the functionality of the equals-method.
     */
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

    /**
     * Tests the createFromParcel-method of the creator, which allows to create instances from a
     * {@link Parcel}.
     */
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

    /**
     * Tests the newArray-method of the creator, which allows to create instances from a {@link
     * Parcel}.
     */
    public final void testCreatorNewArray() {
        int size = 1;
        Item<?>[] array = Item.CREATOR.newArray(size);
        assertEquals(size, array.length);
    }

}