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
package de.mrapp.android.adapter.datastructure;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import java.io.Serializable;

import de.mrapp.android.adapter.Filterable;
import de.mrapp.android.adapter.FilteringNotSupportedException;
import de.mrapp.android.adapter.SortingNotSupportedException;

import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * An abstract base class for all data structure, which holds the data of an item of an adapter.
 *
 * @param <DataType>
 *         The type of the item's data
 * @author Michael Rapp
 * @since 0.1.0
 */
public abstract class AbstractAdapterItem<DataType>
        implements DataStructure, Parcelable, Comparable<AbstractAdapterItem<DataType>>,
        Filterable {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The item's data.
     */
    private DataType data;

    /**
     * Creates a new data structure, which holds the data on an item of an adapter.
     *
     * @param source
     *         The source, the item should be created from, as an instance of the class {@link
     *         Parcel}. The source may not be null
     */
    @SuppressWarnings("unchecked")
    protected AbstractAdapterItem(@NonNull final Parcel source) {
        if (source.readInt() >= 1) {
            Class<DataType> clazz = (Class<DataType>) source.readSerializable();
            ClassLoader classLoader = clazz.getClassLoader();
            setData((DataType) source.readParcelable(classLoader));
        }
    }

    /**
     * Creates a new data structure, which holds the data of an item of an adapter.
     *
     * @param data
     *         The item's data, as an instance of the generic type DataType. The data may not be
     *         null
     */
    public AbstractAdapterItem(@NonNull final DataType data) {
        setData(data);
    }

    /**
     * Returns the item's data.
     *
     * @return The item's data, as an instance of the generic type DataType. The data may not be
     * null
     */
    public final DataType getData() {
        return data;
    }

    /**
     * Sets the item's data.
     *
     * @param data
     *         The data, which should be set, as an instance of the generic type DataType. The data
     *         may not be null
     */
    public final void setData(@NonNull final DataType data) {
        ensureNotNull(data, "The data may not be null");
        this.data = data;
    }

    /**
     * Returns, whether the item's data implements the interface {@link Parcelable}, or not.
     *
     * @return True, if the item's data implements the interface {@link Parcelable}, false otherwise
     */
    public abstract boolean isParcelable();

    /**
     * Returns, whether the item's data implements the interface {@link Serializable}, or not.
     *
     * @return True, if the item's data implements the interface {@link Serializable}, false
     * otherwise
     */
    public abstract boolean isSerializable();

    @SuppressWarnings("unchecked")
    @Override
    public final int compareTo(@NonNull final AbstractAdapterItem<DataType> another) {
        try {
            Comparable<DataType> comparable = (Comparable<DataType>) getData();
            return comparable.compareTo(another.getData());
        } catch (ClassCastException e) {
            throw new SortingNotSupportedException();
        }
    }

    @CallSuper
    @Override
    public boolean match(@NonNull final String query, final int flags) {
        try {
            Filterable filterable = (Filterable) getData();
            return filterable.match(query, flags);
        } catch (ClassCastException e) {
            throw new FilteringNotSupportedException();
        }
    }

    @CallSuper
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + data.hashCode();
        return result;
    }

    @CallSuper
    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractAdapterItem<?> other = (AbstractAdapterItem<?>) obj;
        if (!data.equals(other.data))
            return false;
        return true;
    }

    @Override
    public final int describeContents() {
        return 0;
    }

    @CallSuper
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        if (isParcelable()) {
            dest.writeInt(1);
            dest.writeSerializable(getData().getClass());
            dest.writeParcelable((Parcelable) getData(), flags);
        } else {
            dest.writeInt(0);
        }
    }

    @Override
    public abstract AbstractAdapterItem<DataType> clone() throws CloneNotSupportedException;

}