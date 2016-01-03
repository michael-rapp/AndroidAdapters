/*
 * AndroidAdapters Copyright 2014 - 2016 Michael Rapp
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

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import de.mrapp.android.adapter.ListAdapter;
import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.model.Contact;
import de.mrapp.android.adapter.list.ListAdapterItemLongClickListener;

/**
 * An abstract base class for all fragments, which demonstrate the functionality of adapters, which
 * populate views of the type {@link AbsListView}.
 *
 * @param <AdapterType>
 *         The type of the adapter, whose functionality is demonstrated
 * @author Michael Rapp
 */
public abstract class AbstractListViewFragment<AdapterType extends ListAdapter<Contact>>
        extends AbstractListAdapterFragment<AdapterType> {

    @Override
    protected final View inflateLayout(final LayoutInflater inflater, final ViewGroup container) {
        return inflater.inflate(R.layout.list_view, container, false);
    }

    @Override
    protected final void attachAdapter(final View rootView, final ListAdapter adapter) {
        ListView listView = (ListView) rootView.findViewById(R.id.list_view);
        adapter.attach(listView);
        listView.setLongClickable(true);
    }

}