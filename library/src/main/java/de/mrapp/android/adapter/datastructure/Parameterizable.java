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

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Defines the interface, all classes, which should allow to store key value pairs, must implement.
 *
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface Parameterizable {

    /**
     * Returns a bundle, which contains the key value pairs, which are stored within the class.
     *
     * @return The key value pairs, which are stored within the class, as an instance of the class
     * {@link Bundle} or null, if no key value pairs are stored within the class
     */
    Bundle getParameters();

    /**
     * Sets a bundle, which contains the key value pairs, which should be stored within the class.
     *
     * @param parameters
     *         The bundle, which should be set, as an instance of the class {@link Bundle} or null,
     *         if no key value pairs should be stored within the class
     */
    void setParameters(@Nullable Bundle parameters);

}