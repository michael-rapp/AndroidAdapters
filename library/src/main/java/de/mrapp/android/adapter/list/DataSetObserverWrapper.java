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