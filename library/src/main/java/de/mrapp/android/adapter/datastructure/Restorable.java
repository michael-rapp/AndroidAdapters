/*
 * Copyright 2014 - 2019 Michael Rapp
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
package de.mrapp.android.adapter.datastructure;

import android.os.Bundle;

import androidx.annotation.NonNull;
import de.mrapp.android.adapter.RestoreInstanceStateException;

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
     * @throws RestoreInstanceStateException
     *         The exception, which is thrown, if the state of the adapter could not be restored
     */
    void onRestoreInstanceState(@NonNull Bundle savedInstanceState, @NonNull String key)
            throws RestoreInstanceStateException;

}