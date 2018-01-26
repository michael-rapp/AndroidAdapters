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