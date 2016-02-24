/*
 * Copyright 2014 - 2016 Michael Rapp
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
package de.mrapp.android.adapter.example.decorator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import de.mrapp.android.adapter.ListAdapter;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.SelectableListDecorator;
import de.mrapp.android.adapter.SingleChoiceListAdapter;
import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.model.Contact;
import de.mrapp.android.adapter.list.selectable.MultipleChoiceListAdapterImplementation;
import de.mrapp.android.adapter.list.selectable.SelectableListAdapter;

/**
 * A decorator, which visualizes the items of a {@link SingleChoiceListAdapter} or {@link
 * MultipleChoiceListAdapter}.
 *
 * @author Michael Rapp
 */
public class SelectableListAdapterDecorator extends SelectableListDecorator<Contact> {

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

    /**
     * Creates and returns a listener, which allows to change the selection of a specific item of an
     * adapter, when a check box is clicked.
     *
     * @param adapter
     *         The adapter, the item, whose selection should be changed, belongs to, as an instance
     *         of the type {@link SelectableListAdapter}
     * @param index
     *         The index of the item, whose selection should be changed, as an {@link Integer}
     *         value
     * @return The listener, which has been created, as an {@link OnClickListener}
     */
    private OnClickListener createSelectionCheckBoxClickListener(
            final SelectableListAdapter<Contact> adapter, final int index) {
        return new OnClickListener() {

            @Override
            public void onClick(final View v) {
                if (adapter instanceof MultipleChoiceListAdapterImplementation) {
                    if (((CheckBox) v).isChecked()) {
                        ((MultipleChoiceListAdapter<Contact>) adapter).setSelected(index, true);
                    } else {
                        ((MultipleChoiceListAdapter<Contact>) adapter).setSelected(index, false);
                    }
                } else if (!adapter.isSelected(index)) {
                    if (adapter instanceof SingleChoiceListAdapter) {
                        ((SingleChoiceListAdapter<Contact>) adapter).select(index);
                    } else {
                        ((MultipleChoiceListAdapter<Contact>) adapter).setSelected(index, true);
                    }
                } else {
                    ((CompoundButton) v).setChecked(true);
                }
            }

        };
    }

    @NonNull
    @Override
    public final View onInflateView(@NonNull final LayoutInflater inflater,
                                    @Nullable ViewGroup parent, final int viewType) {
        return inflater.inflate(R.layout.contact_item, parent, false);
    }

    @Override
    @SuppressWarnings("deprecation")
    public final void onShowItem(@NonNull final Context context,
                                 @NonNull final SelectableListAdapter<Contact> adapter,
                                 @NonNull final View view, @NonNull final Contact item,
                                 final int viewType, final int index, final boolean enabled,
                                 final int state, final boolean filtered, final boolean selected) {
        if (selected) {
            view.setBackgroundColor(
                    context.getResources().getColor(android.R.color.holo_blue_light));
        } else {
            view.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }

        CheckBox selectionCheckBox = getView(R.id.selection_check_box);
        selectionCheckBox.setOnClickListener(createSelectionCheckBoxClickListener(adapter, index));
        selectionCheckBox.setChecked(selected);

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