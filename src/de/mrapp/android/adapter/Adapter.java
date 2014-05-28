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

import android.content.Context;
import android.os.Bundle;
import de.mrapp.android.adapter.datastructure.DataStructure;

/**
 * Defines the interface, all adapters must implement.
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface Adapter extends DataStructure {

	/**
	 * Returns the context, the adapter belongs to.
	 * 
	 * @return The context, the adapter belongs to, as an instance of the class
	 *         {@link Context}. The context may not be null
	 */
	Context getContext();

	/**
	 * This method may be called to store the state of the adapter before an
	 * activity is killed.
	 * 
	 * @param outState
	 *            The bundle, which is used to store the saved state, as an
	 *            instance of the class {@link Bundle}. The bundle may not be
	 *            null
	 */
	void onSaveInstanceState(Bundle outState);

	/**
	 * This method may be called to restore a previously saved state of the
	 * adapter after an activity has been reinitialized.
	 * 
	 * @param savedInstanceState
	 *            The bundle, which contains the previously saved state, as an
	 *            instance of the class {@link Bundle}. The bundle may be null
	 */
	void onRestoreInstanceState(Bundle savedInstanceState);

	@Override
	Adapter clone() throws CloneNotSupportedException;

}