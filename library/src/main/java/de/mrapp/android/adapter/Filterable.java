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
package de.mrapp.android.adapter;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Defines the interface, a class, which should support filtering by using regular expressions, must
 * implement.
 *
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface Filterable extends Serializable {

    /**
     * Returns, whether the class does match a specific query, or not.
     *
     * @param query
     *         The query, as a {@link String}. The query may not be null
     * @param flags
     *         Optional flags as an {@link Integer} value or 0, if no flags should be used
     * @return True, if the class does match the given query, false otherwise
     */
    boolean match(@NonNull String query, final int flags);

}