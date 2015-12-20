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

/**
 * An implementation of the abstract class {@link AbstractAdapterItem}, which is needed for test
 * purposes.
 *
 * @author Michael Rapp
 */
public class AbstractAdapterItemImplementation<DataType> extends AbstractAdapterItem<DataType> {

    /**
     * A creator, which allows to create instances of the class {@link
     * AbstractAdapterItemImplementation} from parcels.
     */
    @SuppressWarnings("rawtypes")
    public static final Creator<AbstractAdapterItemImplementation> CREATOR =
            new Creator<AbstractAdapterItemImplementation>() {

                @Override
                public AbstractAdapterItemImplementation createFromParcel(final Parcel source) {
                    return new AbstractAdapterItemImplementation(source);
                }

                @Override
                public AbstractAdapterItemImplementation[] newArray(final int size) {
                    return new AbstractAdapterItemImplementation[size];
                }

            };

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new data structure, which holds the data on an item of an adapter.
     *
     * @param source
     *         The source, the item should be created from, as an instance of the class {@link
     *         Parcel}. The source may not be null
     */
    private AbstractAdapterItemImplementation(final Parcel source) {
        super(source);
    }

    /**
     * Creates a new data structure, which holds the data of an item of an adapter.
     *
     * @param data
     *         The item's data, as an instance of the generic type DataType. The data may not be
     *         null
     */
    public AbstractAdapterItemImplementation(final DataType data) {
        super(data);
    }

    @Override
    public AbstractAdapterItemImplementation<DataType> clone() throws CloneNotSupportedException {
        return null;
    }

}