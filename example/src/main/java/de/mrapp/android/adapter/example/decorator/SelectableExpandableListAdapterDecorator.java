/*
 * Copyright 2014 - 2018 Michael Rapp
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
import android.widget.ImageButton;
import android.widget.TextView;

import de.mrapp.android.adapter.ChoiceMode;
import de.mrapp.android.adapter.MultipleChoiceExpandableListAdapter;
import de.mrapp.android.adapter.SelectableExpandableListDecorator;
import de.mrapp.android.adapter.SingleChoiceExpandableListAdapter;
import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.model.Contact;
import de.mrapp.android.adapter.example.model.Country;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapter;
import de.mrapp.android.adapter.expandablelist.selectable.SelectableExpandableListAdapter;

/**
 * Creates and returns an adapter, which visualizes the items of a {@link
 * SingleChoiceExpandableListAdapter} or {@link MultipleChoiceExpandableListAdapter}.
 *
 * @author Michael Rapp
 */
public class SelectableExpandableListAdapterDecorator
        extends SelectableExpandableListDecorator<Country, Contact> {

    /**
     * Creates and returns a listener, which allows to expand a specific group of an adapter, when a
     * button is clicked.
     *
     * @param adapter
     *         The adapter, the group, which should be expanded, belongs to, as an instance of the
     *         type {@link ExpandableListAdapter}
     * @param index
     *         The index of the group, which should be expanded, as an {@link Integer} value
     * @return The listener, which has been created, as an instance of the type {@link
     * OnClickListener}
     */
    private OnClickListener createExpandButtonListener(
            final ExpandableListAdapter<Country, Contact> adapter, final int index) {
        return new OnClickListener() {

            @Override
            public void onClick(final View v) {
                adapter.triggerGroupExpansion(index);
            }
        };
    }

    /**
     * Creates and returns a listener, which allows to trigger the item state of a specific item of
     * an adapter, when it is clicked.
     *
     * @param adapter
     *         The adapter, the item, whose item state should be triggered, belongs to, as an
     *         instance of the type {@link ExpandableListAdapter}
     * @param groupIndex
     *         The index of the group, the item, whose item state should be triggered, belongs to,
     *         as an {@link Integer} value
     * @param childIndex
     *         The index of the item, whose item state should be triggered, as an {@link Integer}
     *         value
     * @return The listener, which has been created, as an {@link OnClickListener}
     */
    private OnClickListener createTriggerItemStateButtonListener(
            final ExpandableListAdapter<Country, Contact> adapter, final int groupIndex,
            final int childIndex) {
        return new OnClickListener() {

            @Override
            public void onClick(final View v) {
                adapter.triggerChildState(groupIndex, childIndex);
            }

        };
    }

    /**
     * Creates and returns a listener, which allows to change the selection of a specific group of
     * an adapter, when a check box is clicked.
     *
     * @param adapter
     *         The adapter, the group, whose selection should be changed, belongs to, as an instance
     *         of the type {@link SelectableExpandableListAdapter}
     * @param index
     *         The index of the group, whose selection should be changed, as an {@link Integer}
     *         value as an {@link Integer} value
     * @return The listener, which has been created, as an {@link OnClickListener}
     */
    private OnClickListener createGroupSelectionCheckBoxClickListener(
            final SelectableExpandableListAdapter<Country, Contact> adapter, final int index) {
        return new OnClickListener() {

            @Override
            public void onClick(final View v) {
                if (adapter instanceof MultipleChoiceExpandableListAdapter) {
                    if (((CompoundButton) v).isChecked()) {
                        ((MultipleChoiceExpandableListAdapter<Country, Contact>) adapter)
                                .setGroupSelected(index, true);
                    } else {
                        ((MultipleChoiceExpandableListAdapter<Country, Contact>) adapter)
                                .setGroupSelected(index, false);
                    }
                } else if (!adapter.isGroupSelected(index)) {
                    if (adapter instanceof SingleChoiceExpandableListAdapter) {
                        ((SingleChoiceExpandableListAdapter<Country, Contact>) adapter)
                                .triggerGroupSelection(index);
                    }
                } else {
                    ((CompoundButton) v).setChecked(true);
                }
            }

        };
    }

    /**
     * Creates and returns a listener, which allows to change the selection of a specific child of
     * an adapter, when a check box is clicked.
     *
     * @param adapter
     *         The adapter, the item, whose selection should be changed, belongs to, as an instance
     *         of the type {@link SelectableExpandableListAdapter}
     * @param groupIndex
     *         The index of the group, the item, whose selection should be changed, belongs to, as
     *         an {@link Integer} value
     * @param childIndex
     *         The index of the child, whose selection should be triggered, as an {@link Integer}
     *         value
     * @return The listener, which has been created, as an {@link OnClickListener}
     */
    private OnClickListener createChildSelectionCheckBoxClickListener(
            final SelectableExpandableListAdapter<Country, Contact> adapter, final int groupIndex,
            final int childIndex) {
        return new OnClickListener() {

            @Override
            public void onClick(final View v) {
                if (adapter instanceof MultipleChoiceExpandableListAdapter) {
                    if (((CompoundButton) v).isChecked()) {
                        ((MultipleChoiceExpandableListAdapter<Country, Contact>) adapter)
                                .setChildSelected(groupIndex, childIndex, true);
                    } else {
                        ((MultipleChoiceExpandableListAdapter<Country, Contact>) adapter)
                                .setChildSelected(groupIndex, childIndex, false);
                    }
                } else if (!adapter.isChildSelected(groupIndex, childIndex)) {
                    if (adapter instanceof SingleChoiceExpandableListAdapter) {
                        ((SingleChoiceExpandableListAdapter<Country, Contact>) adapter)
                                .triggerChildSelection(groupIndex, childIndex);
                    }
                } else {
                    ((CompoundButton) v).setChecked(true);
                }
            }

        };
    }

    @NonNull
    @Override
    public final View onInflateGroupView(@NonNull final LayoutInflater inflater,
                                         @Nullable final ViewGroup parent, final int groupType) {
        return inflater.inflate(R.layout.country_item, parent, false);
    }

    @Override
    @SuppressWarnings("deprecation")
    public final void onShowGroup(@NonNull final Context context,
                                  @NonNull final SelectableExpandableListAdapter<Country, Contact> adapter,
                                  @NonNull final View view, @NonNull final Country group,
                                  final int viewType, final int index, final boolean expanded,
                                  final boolean enabled, final int state, final boolean filtered,
                                  final boolean selected) {
        if (selected) {
            view.setBackgroundColor(
                    context.getResources().getColor(android.R.color.holo_blue_light));
        } else {
            view.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }

        CheckBox selectionCheckBox = findViewById(R.id.selection_check_box);

        if (adapter.getChoiceMode() != ChoiceMode.CHILDREN_ONLY) {
            selectionCheckBox
                    .setOnClickListener(createGroupSelectionCheckBoxClickListener(adapter, index));
            selectionCheckBox.setChecked(selected);
        } else {
            selectionCheckBox.setVisibility(View.GONE);
        }

        TextView label = findViewById(R.id.label);
        label.setText(group.getName());
        label.setEnabled(enabled);

        ImageButton expandButton = findViewById(R.id.expand_button);
        expandButton.setOnClickListener(createExpandButtonListener(adapter, index));
        expandButton.setEnabled(enabled);

        if (expanded) {
            expandButton.setImageResource(R.drawable.ic_action_expanded);
        } else {
            expandButton.setImageResource(R.drawable.ic_action_collapsed);
        }
    }

    @NonNull
    @Override
    public final View onInflateChildView(@NonNull final LayoutInflater inflater,
                                         @Nullable final ViewGroup parent, final int childType) {
        return inflater.inflate(R.layout.contact_item, parent, false);
    }

    @Override
    @SuppressWarnings("deprecation")
    public final void onShowChild(@NonNull final Context context,
                                  @NonNull final SelectableExpandableListAdapter<Country, Contact> adapter,
                                  @NonNull final View view, @NonNull final Contact child,
                                  final int viewType, final int childIndex,
                                  @NonNull final Country group, final int groupIndex,
                                  final boolean enabled, final int state, final boolean filtered,
                                  final boolean selected) {
        if (selected) {
            view.setBackgroundColor(
                    context.getResources().getColor(android.R.color.holo_blue_light));
        } else {
            view.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }

        Button triggerItemStateButton = (Button) view.findViewById(R.id.trigger_item_state_button);
        triggerItemStateButton.setOnClickListener(
                createTriggerItemStateButtonListener(adapter, groupIndex, childIndex));

        CheckBox selectionCheckBox = findViewById(R.id.selection_check_box);

        if (adapter.getChoiceMode() != ChoiceMode.GROUPS_ONLY) {
            selectionCheckBox.setOnClickListener(
                    createChildSelectionCheckBoxClickListener(adapter, groupIndex, childIndex));
            selectionCheckBox.setChecked(selected);
        } else {
            selectionCheckBox.setVisibility(View.GONE);
        }

        TextView label1Header = findViewById(R.id.label_1_header);
        TextView label1Value = findViewById(R.id.label_1_value);
        TextView label2Header = findViewById(R.id.label_2_header);
        TextView label2Value = findViewById(R.id.label_2_value);

        if (state == 0) {
            label1Header.setText(R.string.last_name);
            label1Value.setText(child.getLastName());
            label2Header.setText(R.string.first_name);
            label2Value.setText(child.getFirstName());
            triggerItemStateButton.setText(R.string.show_address);
        } else {
            label1Header.setText(R.string.address);
            label1Value.setText(child.getAddress());
            label2Header.setText(R.string.city);
            label2Value.setText(child.getCity());
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