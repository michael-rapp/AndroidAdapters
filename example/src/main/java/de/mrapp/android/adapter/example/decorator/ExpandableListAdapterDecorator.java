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
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import de.mrapp.android.adapter.ExpandableListAdapter;
import de.mrapp.android.adapter.ExpandableListDecorator;
import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.model.Contact;
import de.mrapp.android.adapter.example.model.Country;

/**
 * A decorator, which visualizes the items of an {@link ExpandableListAdapter}.
 *
 * @author Michael Rapp
 */
public class ExpandableListAdapterDecorator extends ExpandableListDecorator<Country, Contact> {

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

    @NonNull
    @Override
    public final View onInflateGroupView(@NonNull final LayoutInflater inflater,
                                         @Nullable final ViewGroup parent, final int groupType) {
        return inflater.inflate(R.layout.country_item, parent, false);
    }

    @Override
    public final void onShowGroup(@NonNull final Context context,
                                  @NonNull final ExpandableListAdapter<Country, Contact> adapter,
                                  @NonNull final View view, @NonNull final Country group,
                                  final int viewType, final int index, final boolean expanded,
                                  final boolean enabled, final int state, final boolean filtered) {
        CheckBox selectionCheckBox = getView(R.id.selection_check_box);
        selectionCheckBox.setVisibility(View.GONE);

        TextView label = getView(R.id.label);
        label.setText(group.getName());
        label.setEnabled(enabled);

        ImageButton expandButton = getView(R.id.expand_button);
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
    public final void onShowChild(@NonNull final Context context,
                                  @NonNull final ExpandableListAdapter<Country, Contact> adapter,
                                  @NonNull final View view, @NonNull final Contact child,
                                  final int viewType, final int childIndex,
                                  @NonNull final Country group, final int groupIndex,
                                  final boolean enabled, final int state, final boolean filtered) {
        Button triggerItemStateButton = (Button) view.findViewById(R.id.trigger_item_state_button);
        triggerItemStateButton.setOnClickListener(
                createTriggerItemStateButtonListener(adapter, groupIndex, childIndex));

        CheckBox selectionCheckBox = getView(R.id.selection_check_box);
        selectionCheckBox.setVisibility(View.GONE);

        TextView label1Header = getView(R.id.label_1_header);
        TextView label1Value = getView(R.id.label_1_value);
        TextView label2Header = getView(R.id.label_2_header);
        TextView label2Value = getView(R.id.label_2_value);

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