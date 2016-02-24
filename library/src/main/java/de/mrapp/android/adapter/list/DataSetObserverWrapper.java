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
package de.mrapp.android.adapter.list;

import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;

import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * An implementation of the abstract class AdapterDataObserver, which delegates all method calls to
 * an encapsulated instance of the type {@link DataSetObserver}.
 *
 * @author Michael Rapp
 * @since 0.1.0
 */
public class DataSetObserverWrapper extends RecyclerView.AdapterDataObserver {

    /**
     * The encapsulated observer.
     */
    private final DataSetObserver encapsulatedObserver;

    /**
     * Creates a new implementation of the abstract class AdapterDataObserver, which delegates all
     * method calls to an encapsulated instance of the type {@link DataSetObserver}.
     *
     * @param encapsulatedObserver
     *         The encapsulated observer as an instance of the type {@link DataSetObserver}. The
     *         observer may not be null
     */
    public DataSetObserverWrapper(final DataSetObserver encapsulatedObserver) {
        ensureNotNull(encapsulatedObserver, "The observer may not be null");
        this.encapsulatedObserver = encapsulatedObserver;
    }

    @Override
    public final void onChanged() {
        encapsulatedObserver.onChanged();
    }

    @Override
    public final void onItemRangeChanged(final int positionStart, final int itemCount) {
        encapsulatedObserver.onChanged();
    }

    @Override
    public final void onItemRangeChanged(final int positionStart, final int itemCount,
                                         final Object payload) {
        encapsulatedObserver.onChanged();
    }

    @Override
    public final void onItemRangeInserted(final int positionStart, final int itemCount) {
        encapsulatedObserver.onChanged();
    }

    @Override
    public final void onItemRangeRemoved(final int positionStart, final int itemCount) {
        encapsulatedObserver.onChanged();
    }

    @Override
    public final void onItemRangeMoved(final int fromPosition, final int toPosition,
                                       final int itemCount) {
        encapsulatedObserver.onChanged();
    }

}