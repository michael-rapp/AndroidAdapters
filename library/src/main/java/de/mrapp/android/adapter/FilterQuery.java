/*
 * Copyright 2014 - 2016 Michael Rapp
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