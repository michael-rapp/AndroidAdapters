/*
 * Copyright 2014 - 2018 Michael Rapp
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
package de.mrapp.android.adapter.example.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Locale;

import de.mrapp.android.adapter.Filterable;

/**
 * Represents a country.
 *
 * @author Michael Rapp
 */
public class Country
        implements Parcelable, Serializable, Cloneable, Comparable<Country>, Filterable {

    /**
     * A creator, which allows to create instances of the class {@link Country} from parcels.
     */
    public static final Creator<Country> CREATOR = new Creator<Country>() {

        @Override
        public Country createFromParcel(final Parcel source) {
            return new Country(source);
        }

        @Override
        public Country[] newArray(final int size) {
            return new Country[size];
        }

    };

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The country's name.
     */
    private String name;

    /**
     * Creates a new country.
     *
     * @param source
     *         The source, the country should be created from, as an instance of the class {@link
     *         Parcel}
     */
    private Country(final Parcel source) {
        this.name = source.readString();
    }

    /**
     * Creates a new country.
     *
     * @param name
     *         The country's name
     */
    public Country(final String name) {
        this.name = name;
    }

    /**
     * Returns the country's name.
     *
     * @return The country's name as a {@link String}
     */
    public final String getName() {
        return name;
    }

    @Override
    public final String toString() {
        return name;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Country other = (Country) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public final Country clone() {
        return new Country(name);
    }

    @Override
    public final boolean match(@NonNull final String query, final int flags) {
        return name.toLowerCase(Locale.getDefault())
                .contains(query.toLowerCase(Locale.getDefault()));
    }

    @Override
    public final int compareTo(@NonNull final Country another) {
        return name.compareTo(another.name);
    }

    @Override
    public final int describeContents() {
        return 0;
    }

    @Override
    public final void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(name);
    }

}