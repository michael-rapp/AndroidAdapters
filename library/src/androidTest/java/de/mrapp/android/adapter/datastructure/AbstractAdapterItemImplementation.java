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
    public final boolean isParcelable() {
        return false;
    }

    @Override
    public final boolean isSerializable() {
        return false;
    }

    @Override
    public AbstractAdapterItemImplementation<DataType> clone() throws CloneNotSupportedException {
        return null;
    }

}