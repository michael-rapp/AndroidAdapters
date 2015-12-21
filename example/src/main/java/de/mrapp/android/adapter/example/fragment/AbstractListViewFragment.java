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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import de.mrapp.android.adapter.ListAdapter;
import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.model.Contact;

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

    /**
     * Creates and returns a listener, which allows to disable an item, when it is long-clicked.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * AdapterView.OnItemLongClickListener}
     */
    private AdapterView.OnItemLongClickListener createItemLongClickListener() {
        return new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view,
                                           final int position, final long id) {
                getAdapter().triggerEnableState(position);

                if (getAdapter().isEnabled(position)) {
                    Toast.makeText(getActivity(), R.string.enabled_item_toast, Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(getActivity(), R.string.disabled_item_toast, Toast.LENGTH_SHORT)
                            .show();
                }

                return true;
            }
        };
    }

    @Override
    protected final View inflateLayout(final LayoutInflater inflater, final ViewGroup container) {
        return inflater.inflate(R.layout.list_view, container, false);
    }

    @Override
    protected final void attachAdapter(final View rootView, final ListAdapter adapter) {
        ListView listView = (ListView) rootView.findViewById(R.id.list_view);
        adapter.attach(listView);
        listView.setLongClickable(true);
        listView.setOnItemLongClickListener(createItemLongClickListener());

    }

}