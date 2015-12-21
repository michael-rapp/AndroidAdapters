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
package de.mrapp.android.adapter.example.decorator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import de.mrapp.android.adapter.ListAdapter;
import de.mrapp.android.adapter.ListDecorator;
import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.model.Contact;

/**
 * A decorator, which visualizes the items of a {@link ListAdapter}.
 *
 * @author Michael Rapp
 */
public class ListAdapterDecorator extends ListDecorator<Contact> {

    /**
     * Creates and returns a listener, which allows to trigger the item state of a specific item of
     * an adapter, when it is clicked.
     *
     * @param adapter
     *         The adapter, the item, whose item state should be triggered, belongs to, as an
     *         instance of the type {@link ListAdapter}
     * @param index
     *         The index of the item, whose item state should be triggered, as an {@link Integer}
     *         value
     * @return The listener, which has been created, as an {@link OnClickListener}
     */
    private OnClickListener createTriggerItemStateButtonListener(final ListAdapter<Contact> adapter,
                                                                 final int index) {
        return new OnClickListener() {

            @Override
            public void onClick(final View v) {
                adapter.triggerItemState(index);
            }

        };
    }

    @Override
    public final void onShowItem(@NonNull final Context context,
                                 @NonNull final ListAdapter<Contact> adapter,
                                 @NonNull final View view, @NonNull final Contact item,
                                 final int viewType, final int index, final boolean enabled,
                                 final int state, final boolean filtered) {
        CheckBox selectionCheckBox = getView(R.id.selection_check_box);
        selectionCheckBox.setVisibility(View.GONE);

        Button triggerItemStateButton = getView(R.id.trigger_item_state_button);
        triggerItemStateButton
                .setOnClickListener(createTriggerItemStateButtonListener(adapter, index));

        TextView label1Header = getView(R.id.label_1_header);
        TextView label1Value = getView(R.id.label_1_value);
        TextView label2Header = getView(R.id.label_2_header);
        TextView label2Value = getView(R.id.label_2_value);

        if (state == 0) {
            label1Header.setText(R.string.last_name);
            label1Value.setText(item.getLastName());
            label2Header.setText(R.string.first_name);
            label2Value.setText(item.getFirstName());
            triggerItemStateButton.setText(R.string.show_address);
        } else {
            label1Header.setText(R.string.address);
            label1Value.setText(item.getAddress());
            label2Header.setText(R.string.city);
            label2Value.setText(item.getCity());
            triggerItemStateButton.setText(R.string.show_contact);
        }

        selectionCheckBox.setEnabled(enabled);
        label1Header.setEnabled(enabled);
        label1Value.setEnabled(enabled);
        label2Header.setEnabled(enabled);
        label2Value.setEnabled(enabled);
        triggerItemStateButton.setEnabled(enabled);
    }

    @Override
    protected final boolean isViewStateAdapted() {
        return false;
    }

}