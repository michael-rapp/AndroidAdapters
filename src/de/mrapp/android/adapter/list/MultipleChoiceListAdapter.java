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
package de.mrapp.android.adapter.list;

import de.mrapp.android.adapter.list.selection.MultipleChoiceSelection;
import android.content.Context;

/**
 * An adapter, whose underlying data is managed as a list of arbitrary items, of
 * whom multiple items can be selected at once. Such an adapter is meant to
 * provide the underlying data for visualization using a @link ListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class MultipleChoiceListAdapter<DataType> extends
		AbstractListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary items, of whom only multiple items can be selected at once.
	 * 
	 * @param context
	 *            The context, the adapter should belong to, as an instance of
	 *            the class {@link Context}. The context may not be null
	 * @param viewId
	 *            The id of the view, which should be used to visualize each
	 *            item of the adapter, as an {@link Integer} value. The id must
	 *            specify a valid view from within the \res folder
	 */
	public MultipleChoiceListAdapter(final Context context, final int viewId) {
		super(context, viewId, new MultipleChoiceSelection<DataType>());
	}

	@Override
	public abstract MultipleChoiceListAdapter<DataType> clone();

}