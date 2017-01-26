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
package de.mrapp.android.adapter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * An implementation of the interface {@link Parcelable}, which is needed for test purposes.
 *
 * @author Michael Rapp
 */
public class ParcelableImplementation implements Parcelable {

    /**
     * A creator, which allows to create instances of the class {@link ParcelableImplementation}
     * from parcels.
     */
    @SuppressWarnings("rawtypes")
    public static final Creator<ParcelableImplementation> CREATOR =
            new Creator<ParcelableImplementation>() {

                @Override
                public ParcelableImplementation createFromParcel(final Parcel source) {
                    return new ParcelableImplementation(source.readInt());
                }

                @Override
                public ParcelableImplementation[] newArray(final int size) {
                    return new ParcelableImplementation[size];
                }

            };

    /**
     * A value, which is needed for test purposes.
     */
    private int value;

    /**
     * Creates a new parcelable implementation.
     *
     * @param value
     *         A value, which is needed for test purposes
     */
    public ParcelableImplementation(final int value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(value);
    }

}