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
    public final boolean match(@NonNull final Object data, @NonNull final String query,
                               final int flags) {
        return true;
    }

}