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
package de.mrapp.android.adapter;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Defines the interface, a class, which should allow to filter instances of a specific type by
 * using a regular expression, must implement.
 *
 * @param <DataType>
 *         The type of the instances, which should be filtered
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface Filter<DataType> extends Serializable {

    /**
     * Returns, whether a specific instance does match a regular expression, or not.
     *
     * @param data
     *         The instance, which should be filtered, as an instance of the generic type DataType.
     *         The instance may not be null
     * @param query
     *         The query, which should be used for filtering, as a {@link String}. The query may not
     *         be null
     * @param flags
     *         The flags, which should be used for filtering, as an {@link Integer} value or 0, if
     *         no flags should be used
     * @return True, if the instance does match the given regular expression, false otherwise
     */
    boolean match(@NonNull DataType data, @NonNull String query, int flags);

}