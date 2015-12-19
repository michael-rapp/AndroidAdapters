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
package de.mrapp.android.adapter.expandablelist;

import android.content.Context;
import android.view.View;

import de.mrapp.android.adapter.SelectableListDecorator;
import de.mrapp.android.adapter.list.selectable.SelectableListAdapter;

/**
 * An implementation of the abstract class {@link SelectableListDecorator}, which may be used
 * instead of a null reference.
 *
 * @param <DataType>
 *         The type of the adapter's underlying data
 * @author Michael Rapp
 * @since 0.1.0
 */
public class NullObjectDecorator<DataType> extends SelectableListDecorator<DataType> {

    @Override
    protected final void onShowItem(final Context context,
                                    final SelectableListAdapter<DataType> adapter, final View view,
                                    final DataType item, final int viewType, final int index,
                                    final boolean enabled, final int state, final boolean filtered,
                                    final boolean selected) {
        return;
    }

};