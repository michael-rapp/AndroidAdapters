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
package de.mrapp.android.adapter.example.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Locale;

import de.mrapp.android.adapter.Filterable;

/**
 * Represents a contact.
 *
 * @author Michael Rapp
 */
public class Contact
        implements Parcelable, Serializable, Cloneable, Comparable<Contact>, Filterable {

    /**
     * A creator, which allows to create instances of the class {@link Contact} from parcels.
     */
    public static final Creator<Contact> CREATOR = new Creator<Contact>() {

        @Override
        public Contact createFromParcel(final Parcel source) {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(final int size) {
            return new Contact[size];
        }

    };

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The contact's first name.
     */
    private String firstName;

    /**
     * The contact's last name.
     */
    private String lastName;

    /**
     * The contact's address.
     */
    private String address;

    /**
     * The contact's city.
     */
    private String city;

    /**
     * Creates a new contact.
     *
     * @param source
     *         The parcel, the contact should be created from, as an instance of the class {@link
     *         Parcel}
     */
    private Contact(final Parcel source) {
        firstName = source.readString();
        lastName = source.readString();
        address = source.readString();
        city = source.readString();
    }

    /**
     * Creates a new contact.
     *
     * @param firstName
     *         The contact's first name as a {@link String}
     * @param lastName
     *         The contact's last name as a {@link String}
     * @param address
     *         The contact's address as a {@link String}
     * @param city
     *         The contact's city as a {@link String}
     */
    public Contact(final String firstName, final String lastName, final String address,
                   final String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
    }

    /**
     * Returns the contact's first name.
     *
     * @return The contact's first name as a {@link String}
     */
    public final String getFirstName() {
        return firstName;
    }

    /**
     * Returns the contact's last name.
     *
     * @return The contact's last name as a {@link String}
     */
    public final String getLastName() {
        return lastName;
    }

    /**
     * Returns the contact's address.
     *
     * @return The contact's address as a {@link String}
     */
    public final String getAddress() {
        return address;
    }

    /**
     * Returns the contact's city.
     *
     * @return The contact's city
     */
    public final String getCity() {
        return city;
    }

    @Override
    public final String toString() {
        return "Contact [firstName=" + firstName + ", lastName=" + lastName + ", address=" +
                address + ", city=" + city + "]";
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
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
        Contact other = (Contact) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        return true;
    }

    @Override
    public final int compareTo(@NonNull final Contact another) {
        return lastName.compareTo(another.lastName);
    }

    @Override
    public final Contact clone() {
        return new Contact(firstName, lastName, address, city);
    }

    @Override
    public final boolean match(@NonNull final String query, final int flags) {
        return firstName.toLowerCase(Locale.getDefault())
                .contains(query.toLowerCase(Locale.getDefault())) ||
                lastName.toLowerCase(Locale.getDefault())
                        .contains(query.toLowerCase(Locale.getDefault()));
    }

    @Override
    public final int describeContents() {
        return 0;
    }

    @Override
    public final void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(address);
        dest.writeString(city);
    }

}