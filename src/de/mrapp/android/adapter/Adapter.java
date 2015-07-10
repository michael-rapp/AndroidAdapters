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

import de.mrapp.android.adapter.datastructure.DataStructure;
import de.mrapp.android.adapter.datastructure.Parameterizable;
import de.mrapp.android.adapter.datastructure.Restorable;
import de.mrapp.android.adapter.logging.Loggable;

/**
 * Defines the interface, all adapters must implement.
 * 
 * @author Michael Rapp
 *
 * @since 1.0.0
 */
public interface Adapter extends DataStructure, Restorable, Loggable,
		Parameterizable {

	/**
	 * Returns, whether the method <code>notifyDataSetChanged():void</code> is
	 * automatically called when the adapter's underlying data has been changed,
	 * or not.
	 * 
	 * @return True, if the method <code>notifyDataSetChanged():void</code> is
	 *         automatically called when the adapter's underlying data has been
	 *         changed, false otherwise
	 */
	boolean isNotifiedOnChange();

	/**
	 * Sets, whether the method <code>notifyDataSetChanged():void</code> should
	 * be automatically called when the adapter's underlying data has been
	 * changed, or not.
	 * 
	 * @param notifyOnChange
	 *            True, if the method <code>notifyDataSetChanged():void</code>
	 *            should be automatically called when the adapter's underlying
	 *            data has been changed, false otherwise
	 */
	void notifyOnChange(boolean notifyOnChange);

	@Override
	Adapter clone() throws CloneNotSupportedException;

}