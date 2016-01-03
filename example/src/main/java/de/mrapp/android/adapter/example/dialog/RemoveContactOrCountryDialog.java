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
package de.mrapp.android.adapter.example.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import de.mrapp.android.adapter.MultipleChoiceExpandableListAdapter;
import de.mrapp.android.adapter.SingleChoiceExpandableListAdapter;
import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.model.Contact;
import de.mrapp.android.adapter.example.model.Country;
import de.mrapp.android.adapter.expandablelist.selectable.SelectableExpandableListAdapter;

/**
 * An utility class, which allows to show a dialog, which allows to remove all selected contacts
 * from an expandable list.
 *
 * @author Michael Rapp
 */
public final class RemoveContactOrCountryDialog {

    /**
     * Creates a new utility class, which allows to show a dialog, which allows to remove all
     * selected contacts from an expandable ist.
     */
    private RemoveContactOrCountryDialog() {

    }

    /**
     * Creates and returns a listener, which allows to remove all selected contacts from a list,
     * when the dialog is closed confirmatively.
     *
     * @param context
     *         The context of the dialog, which is closed, as an instance of the class {@link
     *         Context}
     * @param adapter
     *         The adapter, the contacts should be removed from, as an instance of the type {@link
     *         SelectableExpandableListAdapter}
     * @return The listener, which has been created, as an instance of the type {@link
     * OnClickListener}
     */
    private static OnClickListener createRemoveClickListener(final Context context,
                                                             final SelectableExpandableListAdapter<Country, Contact> adapter) {
        return new OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                if (adapter instanceof MultipleChoiceExpandableListAdapter) {
                    MultipleChoiceExpandableListAdapter<Country, Contact> castedAdapter =
                            (MultipleChoiceExpandableListAdapter<Country, Contact>) adapter;

                    for (int i = 0; i < castedAdapter.getGroupCount(); i++) {
                        if (castedAdapter.isGroupSelected(i)) {
                            castedAdapter.removeGroup(i);
                        } else {
                            for (int j = castedAdapter.getChildCount(i) - 1; j >= 0; j--) {
                                if (castedAdapter.isChildSelected(i, j)) {
                                    castedAdapter.removeChild(i, j);
                                }
                            }
                        }
                    }
                } else {
                    SingleChoiceExpandableListAdapter<Country, Contact> castedAdapter =
                            (SingleChoiceExpandableListAdapter<Country, Contact>) adapter;

                    if (castedAdapter.isChildSelected()) {
                        castedAdapter.removeChild(castedAdapter.getSelectedGroupIndex(),
                                castedAdapter.getSelectedGroupIndex());
                    } else {
                        castedAdapter.removeGroup(castedAdapter.getSelectedGroupIndex());
                    }
                }
            }

        };
    }

    /**
     * Shows a dialog, which allows to remove all selected contacts from an expandable list.
     *
     * @param context
     *         The context, which should be used by the dialog, as an instance of the class {@link
     *         Context}
     * @param adapter
     *         The adapter, the contacts should be removed from, as an instance of the type {@link
     *         SelectableExpandableListAdapter}
     */
    @SuppressLint("InflateParams")
    public static void show(final Context context,
                            final SelectableExpandableListAdapter<Country, Contact> adapter) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(R.string.remove_contact_dialog_title);
        dialogBuilder.setMessage(R.string.remove_contact_dialog_message);
        dialogBuilder.setNegativeButton(android.R.string.cancel, null);
        dialogBuilder.setPositiveButton(android.R.string.ok,
                createRemoveClickListener(context, adapter));
        dialogBuilder.create().show();
    }

}