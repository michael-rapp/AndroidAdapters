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

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import android.os.Parcel;
import android.os.Parcelable;

import de.mrapp.android.adapter.Filterable;
import de.mrapp.android.adapter.FilteringNotSupportedException;
import de.mrapp.android.adapter.SortingNotSupportedException;

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
    protected AbstractAdapterItem(final Parcel source) {
        Class<DataType> clazz = (Class<DataType>) source.readSerializable();
        ClassLoader classLoader = clazz.getClassLoader();
        setData((DataType) source.readParcelable(classLoader));
    }

    /**
     * Creates a new data structure, which holds the data of an item of an adapter.
     *
     * @param data
     *         The item's data, as an instance of the generic type DataType. The data may not be
     *         null
     */
    public AbstractAdapterItem(final DataType data) {
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
    public final void setData(final DataType data) {
        ensureNotNull(data, "The data may not be null");
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final int compareTo(final AbstractAdapterItem<DataType> another) {
        try {
            Comparable<DataType> comparable = (Comparable<DataType>) getData();
            return comparable.compareTo(another.getData());
        } catch (ClassCastException e) {
            throw new SortingNotSupportedException();
        }
    }

    @Override
    public boolean match(final String query, final int flags) {
        try {
            Filterable filterable = (Filterable) getData();
            return filterable.match(query, flags);
        } catch (ClassCastException e) {
            throw new FilteringNotSupportedException();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + data.hashCode();
        return result;
    }

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

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeSerializable(getData().getClass());
        dest.writeParcelable((Parcelable) getData(), flags);
    }

    @Override
    public abstract AbstractAdapterItem<DataType> clone() throws CloneNotSupportedException;

}