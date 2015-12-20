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
package de.mrapp.android.adapter.datastructure.item;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import de.mrapp.android.adapter.ListAdapter;

import static de.mrapp.android.util.Condition.ensureAtLeast;
import static de.mrapp.android.util.Condition.ensureAtMaximum;
import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * A list iterator, which allows to iterate the data of items, which are contained by a list. When
 * the iterated list is modified, the underlying data of the adapter, the items belong to, is also
 * modified.
 *
 * @param <DataType>
 *         The type of the items' data
 * @author Michael Rapp
 * @since 0.1.0
 */
public class ItemListIterator<DataType> implements ListIterator<DataType> {

    /**
     * A list, which contains the items, whose data should be iterated.
     */
    private List<Item<DataType>> items;

    /**
     * The adapter, whose underlying data should be modified, when the list, which is iterated by
     * the list iterator, is modified.
     */
    private ListAdapter<DataType> adapter;

    /**
     * The current index of the iterator.
     */
    private int currentIndex;

    /**
     * The item, whose data has been returned the last time when the next- or previous-method has
     * been called.
     */
    private Item<DataType> lastReturnedItem;

    /**
     * Creates a new list iterator, which allows to iterate the data of the item's which are
     * contained by a list, starting at a specific index.
     *
     * @param items
     *         The list, which contains the items, whose data should be iterated, as an instance of
     *         the type {@link List}. The list may not be null
     * @param adapter
     *         The adapter, whose underlying data should be modified, when the list, which is
     *         iterated by the list iterator, is modified, as an instance of the type {@link
     *         ListAdapter} or null, if no adapter's underlying data should be modified
     * @param index
     *         The index, the iterator should start at, as an {@link Integer} value. The index must
     *         be at least 0 and at maximum the size of the given list - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     */
    public ItemListIterator(@NonNull final List<Item<DataType>> items,
                            @Nullable final ListAdapter<DataType> adapter, final int index) {
        ensureNotNull(items, "The items may not be null");
        ensureAtLeast(index, 0, "The index must be at least 0", IndexOutOfBoundsException.class);
        ensureAtMaximum(index, items.isEmpty() ? 0 : items.size(),
                "The index must be at maximum " + items.size(), IndexOutOfBoundsException.class);
        this.items = items;
        this.adapter = adapter;
        this.currentIndex = index - 1;
        this.lastReturnedItem = null;
    }

    /**
     * Creates a new list iterator, which allows to iterate the data of the item's which are
     * contained by a list, starting at the beginning of the list.
     *
     * @param items
     *         The list, which contains the items, whose data should be iterated, as an instance of
     *         the type {@link List}. The list may not be null
     * @param adapter
     *         The adapter, whose underlying data should be modified, when the list, which is
     *         iterated by the list iterator, is modified, as an instance of the type {@link
     *         ListAdapter} or null, if no adapter's underlying data should be modified
     */
    public ItemListIterator(@NonNull final List<Item<DataType>> items,
                            @Nullable final ListAdapter<DataType> adapter) {
        this(items, adapter, 0);
    }

    @Override
    public final void add(@NonNull final DataType item) {
        ensureNotNull(item, "The item may not be null");

        if (adapter == null) {
            throw new UnsupportedOperationException();
        }

        currentIndex++;
        items.add(currentIndex, new Item<>(item));
        adapter.addItem(currentIndex, item);
    }

    @Override
    public final boolean hasNext() {
        return currentIndex < items.size() - 1;
    }

    @Override
    public final boolean hasPrevious() {
        return currentIndex > 0;
    }

    @Override
    public final DataType next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        } else {
            currentIndex++;
            lastReturnedItem = items.get(currentIndex);
            return lastReturnedItem.getData();
        }
    }

    @Override
    public final int nextIndex() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        } else {
            return currentIndex + 1;
        }
    }

    @Override
    public final DataType previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        } else {
            currentIndex--;
            lastReturnedItem = items.get(currentIndex);
            return lastReturnedItem.getData();
        }
    }

    @Override
    public final int previousIndex() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        } else {
            return currentIndex - 1;
        }
    }

    @Override
    public final void remove() {
        if (adapter == null) {
            throw new UnsupportedOperationException();
        } else if (lastReturnedItem == null) {
            throw new IllegalStateException();
        } else {
            items.remove(currentIndex);
            adapter.removeItem(currentIndex);
            currentIndex--;
            lastReturnedItem = null;
        }
    }

    @Override
    public final void set(@NonNull final DataType item) {
        ensureNotNull(item, "The item may not be null");

        if (adapter == null) {
            throw new UnsupportedOperationException();
        } else if (lastReturnedItem == null) {
            throw new IllegalStateException();
        } else {
            int index = items.indexOf(lastReturnedItem);
            items.set(index, new Item<>(item));
            adapter.replaceItem(index, item);
            lastReturnedItem = null;
        }
    }

}