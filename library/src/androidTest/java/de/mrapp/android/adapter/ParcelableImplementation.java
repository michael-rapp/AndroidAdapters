/*
 * AndroidAdapters Copyright 2014 - 2016 Michael Rapp
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