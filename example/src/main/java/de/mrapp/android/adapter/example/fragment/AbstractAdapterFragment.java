/*
 * AndroidAdapters Copyright 2014 - 2015 Michael Rapp
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