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
package de.mrapp.android.adapter.datastructure;

import android.support.annotation.NonNull;

import de.mrapp.android.adapter.Filter;

/**
 * An implementation of the interface {@link Filter}, which is needed for test purposes.
 *
 * @author Michael Rapp
 */
public class FilterImplementation implements Filter<Object> {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public final boolean match(@NonNull final Object data, @NonNull final String query, final int flags) {
        return true;
    }

}