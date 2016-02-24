/*
 * Copyright 2014 - 2016 Michael Rapp
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

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;

import de.mrapp.android.adapter.datastructure.AbstractAdapterItem;

import static de.mrapp.android.util.Condition.ensureAtLeast;

/**
 * A data structure, which holds the data of an item of an adapter. It has a state, a selection
 * state and may be enabled or disabled.
 *
 * @param <DataType>
 *         The type of the item's data
 * @author Michael Rapp
 * @since 0.1.0
 */
public class Item<DataType> extends AbstractAdapterItem<DataType> {

    /**
     * A creator, which allows to create instances of the class {@link Item} from parcels.
     */
    @SuppressWarnings("rawtypes")
    public static final Creator<Item> CREATOR = new Creator<Item>() {

        @Override
        public Item createFromParcel(final Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(final int size) {
            return new Item[size];
        }

    };

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * True, if the item is selected, false otherwise.
     */
    private boolean selected;

    /**
     * True, if the item is enabled, false otherwise.
     */
    private boolean enabled;

    /**
     * The item's state.
     */
    private int state;

    /**
     * Creates a new data structure, which holds the data on an item of an adapter.
     *
     * @param source
     *         The source, the item should be created from, as an instance of the class {@link
     *         Parcel}. The source may not be null
     */
    protected Item(@NonNull final Parcel source) {
        super(source);
        setSelected(source.readInt() == 1);
        setEnabled(source.readInt() == 1);
        setState(source.readInt());
    }

    /**
     * Creates a new data structure, which holds the data of an item of an adapter.
     *
     * @param data
     *         The item's data, as an instance of the generic type DataType. The data may not be
     *         null
     */
    public Item(@NonNull final DataType data) {
        super(data);
        setEnabled(true);
        setSelected(false);
        setState(0);
    }

    /**
     * Returns, whether the item is selected, or not.
     *
     * @return True, if the item is selected, false otherwise
     */
    public final boolean isSelected() {
        return selected;
    }

    /**
     * Sets, whether the item is selected, or not.
     *
     * @param selected
     *         True, if the item should be selected, false otherwise
     */
    public final void setSelected(final boolean selected) {
        this.selected = selected;
    }

    /**
     * Returns, whether the item is enabled, or not.
     *
     * @return True, if the item is enabled false otherwise
     */
    public final boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets, whether the item is enabled, or not.
     *
     * @param enabled
     *         True, if the item should be enabled, false otherwise
     */
    public final void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Returns the item's state.
     *
     * @return The item's state as an {@link Integer} value
     */
    public final int getState() {
        return state;
    }

    /**
     * Sets the item's state.
     *
     * @param state
     *         The state, which should be set as an {@link Integer} value. The value must be at
     *         least 0
     */
    public final void setState(final int state) {
        ensureAtLeast(state, 0, "The state must be at least 0");
        this.state = state;
    }

    @Override
    public final boolean isParcelable() {
        if (getData() instanceof AbstractAdapterItem) {
            return ((AbstractAdapterItem) getData()).isParcelable();
        } else {
            return Parcelable.class.isAssignableFrom(getData().getClass());
        }
    }

    @Override
    public final boolean isSerializable() {
        if (getData() instanceof AbstractAdapterItem) {
            return ((AbstractAdapterItem) getData()).isSerializable();
        } else {
            return Serializable.class.isAssignableFrom(getData().getClass());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Item<DataType> clone() throws CloneNotSupportedException {
        try {
            DataType clonedData =
                    (DataType) getData().getClass().getMethod("clone").invoke(getData());
            Item<DataType> clonedItem = new Item<>(clonedData);
            clonedItem.setSelected(isSelected());
            clonedItem.setEnabled(isEnabled());
            clonedItem.setState(getState());
            return clonedItem;
        } catch (Exception e) {
            throw new CloneNotSupportedException();
        }
    }

    @Override
    public final String toString() {
        return "Item [data=" + getData() + ", selected=" + isSelected() + ", enabled=" +
                isEnabled() + ", state=" + getState() + "]";
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (enabled ? 1231 : 1237);
        result = prime * result + (selected ? 1231 : 1237);
        result = prime * result + state;
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Item<?> other = (Item<?>) obj;
        if (enabled != other.enabled)
            return false;
        if (selected != other.selected)
            return false;
        if (state != other.state)
            return false;
        return true;
    }

    @Override
    public final void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(isSelected() ? 1 : 0);
        dest.writeInt(isEnabled() ? 1 : 0);
        dest.writeInt(getState());
    }

}