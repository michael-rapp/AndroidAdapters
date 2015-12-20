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
package de.mrapp.android.adapter.example.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;

import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.SingleChoiceListAdapter;
import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.model.Contact;
import de.mrapp.android.adapter.list.selectable.SelectableListAdapter;

/**
 * An utility class, which allows to show a dialog, which allows to remove all selected contacts
 * from a list.
 *
 * @author Michael Rapp
 */
public final class RemoveContactDialog {

    /**
     * Creates a new utility class, which allows to show a dialog, which allows to remove all
     * selected contacts from a list.
     */
    private RemoveContactDialog() {

    }

    /**
     * Creates and returns a listener, which allows to remove all selected contacts from an
     * expandable list, when the dialog is closed confirmatively.
     *
     * @param context
     *         The context of the dialog, which is closed, as an instance of the class {@link
     *         Context}
     * @param adapter
     *         The adapter, the contacts should be removed from, as an instance of the type {@link
     *         SelectableListAdapter}
     * @return The listener, which has been created, as an instance of the type {@link
     * OnClickListener}
     */
    private static OnClickListener createRemoveClickListener(final Context context,
                                                             final SelectableListAdapter<Contact> adapter) {
        return new OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                if (adapter instanceof MultipleChoiceListAdapter) {
                    MultipleChoiceListAdapter<Contact> castedAdapter =
                            (MultipleChoiceListAdapter<Contact>) adapter;
                    castedAdapter.removeAllItems(castedAdapter.getSelectedItems());
                } else {
                    SingleChoiceListAdapter<Contact> castedAdapter =
                            (SingleChoiceListAdapter<Contact>) adapter;
                    castedAdapter.removeItem(castedAdapter.getSelectedIndex());
                }

                Toast.makeText(context, R.string.removed_selected_items_toast, Toast.LENGTH_SHORT)
                        .show();
            }

        };
    }

    /**
     * Shows a dialog, which allows to remove all selected contacts from a list.
     *
     * @param context
     *         The context, which should be used by the dialog, as an instance of the class {@link
     *         Context}
     * @param adapter
     *         The adapter, the contacts should be removed from, as an instance of the type {@link
     *         SelectableListAdapter}
     */
    @SuppressLint("InflateParams")
    public static void show(final Context context, final SelectableListAdapter<Contact> adapter) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(R.string.remove_contact_dialog_title);
        dialogBuilder.setMessage(R.string.remove_contact_dialog_message);
        dialogBuilder.setNegativeButton(android.R.string.cancel, null);
        dialogBuilder.setPositiveButton(android.R.string.ok,
                createRemoveClickListener(context, adapter));
        dialogBuilder.create().show();
    }

}