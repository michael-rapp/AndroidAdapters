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
import android.support.annotation.NonNull;

/**
 * Defines the interface, a class whose state should be able to be stored, before an activity is
 * killed and restored after it has been reinitialized, must implement.
 *
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface Restorable {

    /**
     * This method may be called to store the state of the adapter within a bundle. For example,
     * this method may be used to store the state of the adapter before an activity is destroyed in
     * its <code>onSaveInstanceState</code> method.
     *
     * @param outState
     *         The bundle, which should be used to store the saved state, as an instance of the
     *         class {@link Bundle}. The bundle may not be null
     * @param key
     *         The key, which should be used to store the saved state, as a {@link String}. The
     *         string may neither be null, nor null
     */
    void onSaveInstanceState(@NonNull Bundle outState, @NonNull String key);

    /**
     * This method may be called to restore a previously saved state of the adapter after an
     * activity has been reinitialized. For example, this method may be used to restore the state of
     * the adapter within an activity's <code>onRestoreInstanceState</code> method.
     *
     * @param savedInstanceState
     *         The bundle, which contains the previously saved state, as an instance of the class
     *         {@link Bundle}. The bundle may be null
     * @param key
     *         The key, which previously has been used to store the saved state, as a {@link
     *         String}. The key may neither be null, nor null
     */
    void onRestoreInstanceState(@NonNull Bundle savedInstanceState, @NonNull String key);

}