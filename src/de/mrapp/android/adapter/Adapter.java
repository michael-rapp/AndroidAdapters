/*
 * AndroidAdapters Copyright 2014 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>. 
 */
package de.mrapp.android.adapter;

import android.os.Bundle;
import de.mrapp.android.adapter.util.Entity;

/**
 * Defines the interface, all adapters must implement.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface Adapter<DataType> extends Entity {

	@Override
	Adapter<DataType> clone() throws CloneNotSupportedException;

	/**
	 * This method may be called to retrieve per-instance state from an activity
	 * before being killed so that the state can be restored.
	 * 
	 * @param outState
	 *            The bundle, which is used to store the saved state, as an
	 *            instance of the class {@link Bundle}. The bundle may not be
	 *            null
	 */
	void onSaveInstanceState(Bundle outState);

	/**
	 * This method may be called after to re-initialize an activity from a
	 * previously saved state.
	 * 
	 * @param savedInstanceState
	 *            The bundle, which contains the previously save state, as an
	 *            instance of the class {@link Bundle}. The bundle may be null
	 */
	void onRestoreInstanceState(Bundle savedInstanceState);

}