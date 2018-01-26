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