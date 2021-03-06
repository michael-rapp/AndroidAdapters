/*
 * Copyright 2014 - 2019 Michael Rapp
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
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import de.mrapp.android.adapter.list.ListAdapter;
import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.model.Contact;

/**
 * An utility class, which allows to show a dialog, which allows to add contacts to a list.
 *
 * @author Michael Rapp
 */
public final class AddContactDialog {

    /**
     * Creates a new utility class, which allows to show a dialog, which allows to add contacts to a
     * list.
     */
    private AddContactDialog() {

    }

    /**
     * Creates and returns a listener, which allows to add a contact to a list, when a dialog is
     * closed confirmatively.
     *
     * @param dialog
     *         The dialog, which is closed, as an instance of the class {@link AlertDialog}
     * @param context
     *         The context, which is used by the dialog, as an instance of the class {@link
     *         Context}
     * @param lastNameEditText
     *         The edit text, which contains the last name of the contact, which should be added, as
     *         an instance of the class {@link EditText}
     * @param firstNameEditText
     *         The edit text, which contains the first name of the contact, which should be added,
     *         as an instance of the class {@link EditText}
     * @param addressEditText
     *         The edit text, which contains the address of the contact, which should be added, as
     *         an instance of the class {@link EditText}
     * @param cityEditText
     *         The edit text, which contains the city of the contact, which should be added, as an
     *         instance of the class {@link EditText}
     * @param adapter
     *         The adapter, the contact should be added to, as an instance of the type {@link
     *         ListAdapter}
     * @return The listener, which has been created, as an instance of the type {@link
     * View.OnClickListener}
     */
    private static View.OnClickListener createOkClickListener(final AlertDialog dialog,
                                                              final Context context,
                                                              final EditText lastNameEditText,
                                                              final EditText firstNameEditText,
                                                              final EditText addressEditText,
                                                              final EditText cityEditText,
                                                              final ListAdapter<Contact> adapter) {
        return new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                attemptAddingContact(dialog, context, lastNameEditText, firstNameEditText,
                        addressEditText, cityEditText, adapter);
            }
        };
    }

    /**
     * Validates the data, which has been entered into the dialog, and adds the resulting contact,
     * if valid.
     *
     * @param dialog
     *         The dialog, the data has been entered into, as an instance of the class {@link
     *         AlertDialog}
     * @param context
     *         The context, which is used by the dialog, as an instance of the class {@link
     *         Context}
     * @param lastNameEditText
     *         The edit text, which contains the last name of the contact, which should be added, as
     *         an instance of the class {@link EditText}
     * @param firstNameEditText
     *         The edit text, which contains the first name of the contact, which should be added,
     *         as an instance of the class {@link EditText}
     * @param addressEditText
     *         The edit text, which contains the address of the contact, which should be added, as
     *         an instance of the class {@link EditText}
     * @param cityEditText
     *         The edit text, which contains the city of the contact, which should be added, as an
     *         instance of the class {@link EditText}
     * @param adapter
     *         The adapter, the contact should be added to, as an instance of the type {@link
     *         ListAdapter}
     */
    private static void attemptAddingContact(final AlertDialog dialog, final Context context,
                                             final EditText lastNameEditText,
                                             final EditText firstNameEditText,
                                             final EditText addressEditText,
                                             final EditText cityEditText,
                                             final ListAdapter<Contact> adapter) {
        lastNameEditText.setError(null);
        firstNameEditText.setError(null);
        addressEditText.setError(null);
        cityEditText.setError(null);

        if (validateForm(context, lastNameEditText, firstNameEditText, addressEditText,
                cityEditText)) {
            String lastName = lastNameEditText.getText().toString();
            String firstName = firstNameEditText.getText().toString();
            String address = addressEditText.getText().toString();
            String city = cityEditText.getText().toString();
            Contact contact = new Contact(firstName, lastName, address, city);
            int index = adapter.addItem(contact);
            dialog.dismiss();

            if (index != -1) {
                Toast.makeText(context, R.string.added_item_toast, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, R.string.duplicate_items_not_allowed, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    /**
     * Creates and returns an editor listener, which allows validate the data, which has been
     * entered into the dialog, and to add the resulting contact, if valid.
     *
     * @param dialog
     *         The dialog, the data has been entered into, as an instance of the class {@link
     *         AlertDialog}
     * @param context
     *         The context, which is used by the dialog, as an instance of the class {@link
     *         Context}
     * @param lastNameEditText
     *         The edit text, which contains the last name of the contact, which should be added, as
     *         an instance of the class {@link EditText}
     * @param firstNameEditText
     *         The edit text, which contains the first name of the contact, which should be added,
     *         as an instance of the class {@link EditText}
     * @param addressEditText
     *         The edit text, which contains the address of the contact, which should be added, as
     *         an instance of the class {@link EditText}
     * @param cityEditText
     *         The edit text, which contains the city of the contact, which should be added, as an
     *         instance of the class {@link EditText}
     * @param adapter
     *         The adapter, the contact should be added to, as an instance of the type {@link
     *         ListAdapter}
     * @return The listener, which has been created, as an instance of the type {@link
     * OnEditorActionListener}
     */
    private static OnEditorActionListener createEditorActionListener(final AlertDialog dialog,
                                                                     final Context context,
                                                                     final EditText lastNameEditText,
                                                                     final EditText firstNameEditText,
                                                                     final EditText addressEditText,
                                                                     final EditText cityEditText,
                                                                     final ListAdapter<Contact> adapter) {
        return new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(final TextView v, final int actionId,
                                          final KeyEvent event) {
                if (actionId == v.getResources().getInteger(R.integer.add_contact_action) ||
                        actionId == EditorInfo.IME_NULL) {
                    attemptAddingContact(dialog, context, lastNameEditText, firstNameEditText,
                            addressEditText, cityEditText, adapter);
                    return true;
                }

                return false;
            }
        };
    }

    /**
     * Validates the data, which has been entered into the dialog.
     *
     * @param context
     *         The context, which is used by the dialog, as an instance of the class {@link
     *         Context}
     * @param lastNameEditText
     *         The edit text, which contains the last name of the contact, which should be added, as
     *         an instance of the class {@link EditText}
     * @param firstNameEditText
     *         The edit text, which contains the first name of the contact, which should be added,
     *         as an instance of the class {@link EditText}
     * @param addressEditText
     *         The edit text, which contains the address of the contact, which should be added, as
     *         an instance of the class {@link EditText}
     * @param cityEditText
     *         The edit text, which contains the city of the contact, which should be added, as an
     *         instance of the class {@link EditText}
     * @return True, if the data is valid, false otherwise
     */
    private static boolean validateForm(final Context context, final EditText lastNameEditText,
                                        final EditText firstNameEditText,
                                        final EditText addressEditText,
                                        final EditText cityEditText) {
        if (TextUtils.isEmpty(lastNameEditText.getText())) {
            lastNameEditText.setError(context.getString(R.string.field_required_error));
            lastNameEditText.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(firstNameEditText.getText())) {
            firstNameEditText.setError(context.getString(R.string.field_required_error));
            firstNameEditText.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(addressEditText.getText())) {
            addressEditText.setError(context.getString(R.string.field_required_error));
            addressEditText.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(cityEditText.getText())) {
            cityEditText.setError(context.getString(R.string.field_required_error));
            cityEditText.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Shows a dialog, which allows to add a contact to a list.
     *
     * @param context
     *         The context, which should be used by the dialog, as an instance of the class {@link
     *         Context}
     * @param adapter
     *         The adapter, the contact should be added to, as an instance of the type {@link
     *         ListAdapter}
     */
    @SuppressLint("InflateParams")
    public static void show(final Context context, final ListAdapter<Contact> adapter) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(R.string.add_contact_dialog_title);

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_contact_dialog, null);
        dialogBuilder.setView(view);

        EditText lastNameEditText = (EditText) view.findViewById(R.id.last_name_edit_text);
        EditText firstNameEditText = (EditText) view.findViewById(R.id.first_name_edit_text);
        EditText addressEditText = (EditText) view.findViewById(R.id.address_edit_text);
        EditText cityEditText = (EditText) view.findViewById(R.id.city_edit_text);

        dialogBuilder.setPositiveButton(android.R.string.ok, null);
        dialogBuilder.setNegativeButton(android.R.string.cancel, null);

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
                createOkClickListener(dialog, context, lastNameEditText, firstNameEditText,
                        addressEditText, cityEditText, adapter));
        cityEditText.setOnEditorActionListener(
                createEditorActionListener(dialog, context, lastNameEditText, firstNameEditText,
                        addressEditText, cityEditText, adapter));
    }

}