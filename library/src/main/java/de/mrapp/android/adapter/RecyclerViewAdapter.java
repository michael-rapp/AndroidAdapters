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
package de.mrapp.android.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;

import de.mrapp.android.util.view.ViewHolder;

/**
 * Defines the interface, all adapters, which can be attached to a RecyclerView, must implement.
 *
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface RecyclerViewAdapter extends Adapter<AbsListView> {

    /**
     * The view holder, which is used by a {@link RecyclerViewAdapter}.
     */
    class ListItemViewHolder extends ViewHolder {

        /**
         * The index of the item, the view holder corresponds to.
         */
        private int itemIndex = -1;

        /**
         * Creates a new view holder.
         *
         * @param itemView
         *         The view, the view holder corresponds to, as an instance of the class {@link
         *         View}. The view may not be null
         */
        public ListItemViewHolder(@NonNull final View itemView) {
            super(itemView);
        }

        /**
         * Returns, whether the view holder has already been bound, or not.
         *
         * @return True, if the view holder has been bound, false otherwise
         */
        public final boolean isBound() {
            return itemIndex != -1;
        }

        /**
         * Returns the index of the item, the view holder corresponds to.
         *
         * @return The index of the item, the view holder corresponds to, as an {@link Integer}
         * value or -1, if the view holder has not been bound yet
         */
        public final int getItemIndex() {
            return itemIndex;
        }

        /**
         * Sets the index of the item, the view holder corresponds to. This method should never be
         * called manually!
         *
         * @param itemIndex
         *         The index, which should be set, as an {@link Integer} value
         */
        public final void setItemIndex(final int itemIndex) {
            this.itemIndex = itemIndex;
        }

    }

    /**
     * Attaches the adapter to a view.
     *
     * @param adapterView
     *         The view, the adapter should be attached to, as an instance of the class
     *         RecyclerView. The view may not be null
     */
    void attach(@NonNull final RecyclerView adapterView);

    /**
     * Notifies any registered observers that the item at <code>index</code> has changed. Equivalent
     * to calling <code>notifyItemChanged(index, null);</code>.
     * <p>
     * This is an item change event, not a structural change event. It indicates that any reflection
     * of the data at <code>index</code> is out of date and should be updated. The item at
     * <code>index</code> retains the same identity.
     *
     * @param index
     *         The index of the item, which has been changed, as an {@link Integer} value
     * @see #notifyItemRangeChanged(int, int)
     */
    void notifyItemChanged(int index);

    /**
     * Notifies any registered observers that the item at <code>index</code> has changed with an
     * optional payload object.
     * <p>
     * This is an item change event, not a structural change event. It indicates that any reflection
     * of the data at <code>index</code> is out of date and should be updated. The item at
     * <code>index</code> retains the same identity.
     * <p>
     * The client can optionally pass a payload for partial change. These payloads will be merged
     * and may be passed to the adapter's <code>onBindViewHolder</code>-method, if the item is
     * already represented by a view holder and it will be rebound to the same view holder. Calling
     * the <code>notifyItemRangeChanged</code>-method with null payload will clear all existing
     * payloads on that item and prevent future payload until the <code>onBindViewHolder</code>-method
     * is called. Adapters should not assume that the payload will always be passed to the
     * <code>onBindViewHolder</code>-method, e.g. when the view is not attached, the payload will be
     * simply dropped.
     *
     * @param index
     *         The index of the item, which has been changed, as an {@link Integer} value
     * @param payload
     *         An optional payload as an instance of the class {@link Object} or null to identify a
     *         "full" update
     * @see #notifyItemRangeChanged(int, int)
     */
    void notifyItemChanged(int index, Object payload);

    /**
     * Notifies any registered observers that the <code>itemCount</code> items starting at
     * <code>startIndex</code> have changed. Equivalent to calling <code>notifyItemRangeChanged(startIndex,
     * itemCount, null);</code>.
     * <p>
     * This is an item change event, not a structural change event. It indicates that any reflection
     * of the data in the given position range is out of date and should be updated. The items in
     * the given range retain the same identity.
     *
     * @param startIndex
     *         The index of the first item, which has been changed, as an {@link Integer} value
     * @param itemCount
     *         The number of items, which have been changed, as an {@link Integer} value. The number
     *         of items must be at least 1
     * @see #notifyItemChanged(int)
     */
    void notifyItemRangeChanged(int startIndex, int itemCount);

    /**
     * Notifies any registered observers that the <code>itemCount</code> items starting at
     * <code>startIndex</code> have changed. An optional payload can be passed to each changed
     * item.
     * <p>
     * This is an item change event, not a structural change event. It indicates that any reflection
     * of the data in the given position range is out of date and should be updated. The items in
     * the given range retain the same identity.
     * <p>
     * The client can optionally pass a payload for partial change. These payloads will be merged
     * and may be passed to the adapter's <code>onBindViewHolder</code>-method, if the item is
     * already represented by a view holder and it will be rebound to the same view holder. Calling
     * the <code>notifyItemRangeChanged</code>-method with null payload will clear all existing
     * payloads on that item and prevent future payload until the <code>onBindViewHolder</code>-method
     * is called. Adapters should not assume that the payload will always be passed to the
     * <code>onBindViewHolder</code>-method, e.g. when the view is not attached, the payload will be
     * simply dropped.
     *
     * @param startIndex
     *         The index of the first item, which has been changed, as an {@link Integer} value. The
     *         index must be between 0 and the value of the method <code>getCount():int</code> - 1,
     *         otherwise an {@link IndexOutOfBoundsException} will be thrown
     * @param itemCount
     *         The number of items, which have been changed, as an {@link Integer} value
     * @param payload
     *         An optional payload as an instance of the class {@link Object} or null to identify a
     *         "full" update
     * @see #notifyItemChanged(int)
     */
    void notifyItemRangeChanged(int startIndex, int itemCount, Object payload);

    /**
     * Notifies any registered observers that the item at <code>index</code> has been newly
     * inserted. The item previously at <code>index</code> is now at <code>index + 1</code>.
     * <p>
     * This is a structural change event. Representations of other existing items in the data set
     * are still considered up to date and will not be rebound, though their positions may be
     * altered.
     *
     * @param index
     *         The index of the item, which has been inserted, as an {@link Integer} value
     * @see #notifyItemRangeInserted(int, int)
     */
    void notifyItemInserted(int index);

    /**
     * Notifies any registered observers that the item reflected at <code>fromIndex</code> has been
     * moved to <code>toIndex</code>.
     * <p>
     * This is a structural change event. Representations of other existing items in the data set
     * are still considered up to date and will not be rebound, though their positions may be
     * altered.
     *
     * @param fromIndex
     *         The previous index of the item, as an {@link Integer} value
     * @param toIndex
     *         The new index of the item as an {@link Integer} value. The index must be between 0
     *         and the value of the method <code>getCount():int</code> - 1, otherwise an {@link
     *         IndexOutOfBoundsException} will be thrown
     */
    void notifyItemMoved(int fromIndex, int toIndex);

    /**
     * Notifies any registered observers that the currently reflected <code>itemCount</code> items
     * starting at <code>startIndex</code> have been newly inserted. The items previously located at
     * <code>startIndex</code> and beyond can now be found starting at <code>startIndex +
     * itemCount</code>.
     * <p>
     * This is a structural change event. Representations of other existing items in the data set
     * are still considered up to date and will not be rebound, though their positions may be
     * altered.
     *
     * @param startIndex
     *         The index of the first item, which has been inserted, as an {@link Integer} value
     * @param itemCount
     *         The number of items, which have been inserted, as an {@link Integer} value. The
     *         number of items must at least 1
     * @see #notifyItemInserted(int)
     */
    void notifyItemRangeInserted(int startIndex, int itemCount);

    /**
     * Notifies any registered observers that the item previously located at <code>index</code> has
     * been removed from the data set. The items previously located at and after <code>index</code>
     * may now be found at the <code>oldIndex - 1</code>.
     * <p>
     * This is a structural change event. Representations of other existing items in the data set
     * are still considered up to date and will not be rebound, though their positions may be
     * altered.
     *
     * @param index
     *         The index of the item, which has been removed, as an {@link Integer} value
     * @see #notifyItemRangeRemoved(int, int)
     */
    void notifyItemRemoved(int index);

    /**
     * Notifies any registered observers that the <code>itemCount</code> items previously located at
     * <code>startIndex</code> have been removed from the data set. The items previously located at
     * and after <code>startIndex + itemCount</code> may now be found at <code>oldIndex -
     * itemCount</code>.
     * <p>
     * This is a structural change event. Representations of other existing items in the data set
     * are still considered up to date and will not be rebound, though their positions may be
     * altered.
     *
     * @param positionStart
     *         The previous index of the first item, which has been removed, as an {@link Integer}
     *         value
     * @param itemCount
     *         The number of items, which has been removed, as an {@link Integer} value. The number
     *         of items must be at least 1
     */
    void notifyItemRangeRemoved(int positionStart, int itemCount);

    @Override
    RecyclerViewAdapter clone() throws CloneNotSupportedException;

}