/*
 * Copyright 2014 - 2017 Michael Rapp
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