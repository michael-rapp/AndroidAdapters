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
package de.mrapp.android.adapter.example.fragment;

import android.app.Fragment;

/**
 * An abstract base class for all fragments, which are used to demonstrate the functionality of an
 * adapter.
 *
 * @author Michael Rapp
 */
public abstract class AbstractAdapterFragment extends Fragment {

    /**
     * The method, which is invoked, when a search query should be started.
     *
     * @param query
     *         The query, which should be started, as a {@link String}
     */
    public abstract void search(String query);

}