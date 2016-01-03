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