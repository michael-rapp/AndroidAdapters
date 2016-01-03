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

import android.os.Parcelable;

import de.mrapp.android.adapter.datastructure.DataStructure;

/**
 * A representation of a query, which has been used to filter an adapter's underlying data.
 *
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface FilterQuery extends DataStructure, Parcelable {

    /**
     * Returns the query, which has been used to filter the adapter's data.
     *
     * @return The query, which has been used to filter the adapter's data, as a {@link String}. The
     * query may not be null
     */
    String getQuery();

    /**
     * Returns the flags, which have been used to filter the adapter's data.
     *
     * @return The flags, which have been used to filter the adapter's data, as an {@link Integer}
     * value or 0, if no flags have been used
     */
    int getFlags();

}