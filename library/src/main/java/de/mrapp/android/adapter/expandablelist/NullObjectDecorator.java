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
package de.mrapp.android.adapter.expandablelist;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.mrapp.android.adapter.SelectableListDecorator;
import de.mrapp.android.adapter.list.selectable.SelectableListAdapter;

/**
 * An implementation of the abstract class {@link SelectableListDecorator}, which may be used
 * instead of a null reference.
 *
 * @param <DataType>
 *         The type of the adapter's underlying data
 * @author Michael Rapp
 * @since 0.1.0
 */
public class NullObjectDecorator<DataType> extends SelectableListDecorator<DataType> {

    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public final View onInflateView(@NonNull final LayoutInflater inflater,
                                    @Nullable final ViewGroup parent, final int viewType) {
        return null;
    }

    @Override
    public final void onShowItem(@NonNull final Context context,
                                 @NonNull final SelectableListAdapter<DataType> adapter,
                                 @NonNull final View view, @NonNull final DataType item,
                                 final int viewType, final int index, final boolean enabled,
                                 final int state, final boolean filtered, final boolean selected) {

    }

}