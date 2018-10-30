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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import de.mrapp.android.util.view.ViewHolder;

/**
 * Defines the interface, all expandable list adapters, which can be attached to a RecyclerView,
 * must implement.
 *
 * @author Michael Rapp
 * @since 0.8.0
 */
public interface ExpandableRecyclerViewAdapter extends ExpandableGridViewAdapter {

    /**
     * The view holder, which is used by a {@link ExpandableRecyclerViewAdapter}.
     */
    class ExpandableListItemViewHolder extends ViewHolder {

        /**
         * The index of the group, the view holder corresponds to.
         */
        private int groupIndex = -1;

        /**
         * The index of the child, the view holder corresponds to.
         */
        private int childIndex = -1;

        /**
         * Creates a new view holder.
         *
         * @param itemView
         *         The view, the view holder corresponds to, as an instance of the class {@link
         *         View}. The view may not be null
         */
        public ExpandableListItemViewHolder(@NonNull final View itemView) {
            super(itemView);
        }

        /**
         * Returns, whether the view holder has already been bound, or not.
         *
         * @return True, if the view holder has already been bound, false otherwise
         */
        public final boolean isBound() {
            return groupIndex != -1;
        }

        /**
         * Returns, whether the view holder corresponds to a group item, or not.
         *
         * @return True, if the view holder corresponds to a group item, false otherwise or if the
         * view holder has not already been bound
         */
        public final boolean isGroup() {
            return isBound() && childIndex == -1;
        }

        /**
         * Returns, whether the view holder corresponds to a child item, or not.
         *
         * @return True, if the view holder corresponds to a child item, false otherwise or if the
         * view holder has not already been bound
         */
        public final boolean isChild() {
            return isBound() && childIndex != -1;
        }

        /**
         * Returns the index of the group, the view holder corresponds to.
         *
         * @return The index of the group, the view holder corresponds to, as an {@link Integer}
         * value or -1, if the view holder has not be bound yet
         */
        public final int getGroupIndex() {
            return groupIndex;
        }

        /**
         * Sets the index of the group, the view holder corresponds to. This method should never be
         * called manually!
         *
         * @param groupIndex
         *         The group index, which should be set, as an {@link Integer} value
         */
        public final void setGroupIndex(final int groupIndex) {
            this.groupIndex = groupIndex;
        }

        /**
         * Returns the index of the child, the view holder corresponds to.
         *
         * @return The index of the child, the view holder corresponds to, as an {@link Integer}
         * value or -1, if the view holder has not be bound yet or if the view holder corresponds to
         * a group item
         */
        public final int getChildIndex() {
            return childIndex;
        }

        /**
         * Sets the index of the child, the view holder corresponds to. This method should never be
         * called manually!
         *
         * @param childIndex
         *         The child index, which should be set, as an {@link Integer} value
         */
        public final void setChildIndex(final int childIndex) {
            this.childIndex = childIndex;
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
     * Notifies any registered observers that the group at <code>groupIndex</code> has changed.
     * Equivalent to calling <code>notifyGroupChanged(groupIndex, false, null);</code>.
     * <p>
     * This is a group change event, not a structural change event. It indicates that any reflection
     * of the data at <code>groupIndex</code> is out of date and should be updated. The group at
     * <code>groupIndex</code> retains the same identity.
     * <p>
     *
     * @param groupIndex
     *         The index of the group, which has been changed, as an {@link Integer} value
     * @see #notifyGroupRangeChanged(int, int)
     */
    void notifyGroupChanged(int groupIndex);

    /**
     * Notifies any registered observers that the group at <code>groupIndex</code> has changed with
     * an optional payload object.
     * <p>
     * This is a group change event, not a structural change event. It indicates that any reflection
     * of the data at <code>groupIndex</code> is out of date and should be updated. The group at
     * <code>groupIndex</code> retains the same identity.
     * <p>
     * Optionally, if the group is currently expanded, the registered observers can be notified that
     * the children of the changed group have been changed as well. This is equivalent to calling
     * the <code>notifyChildRangeChanged</code>-method for all children of the according group.
     * <p>
     * The client can optionally pass a payload for partial change. These payloads will be merged
     * and may be passed to the adapter's <code>onBindViewHolder</code>-method, if the group is
     * already represented by a view holder and it will be rebound to the same view holder. Calling
     * the <code>notifyGroupRangeChanged</code>-method with null payload will clear all existing
     * payloads on that group and prevent future payload until the <code>onBindViewHolder</code>-method
     * is called. Adapters should not assume that the payload will always be passed to the
     * <code>onBindViewHolder</code>-method, e.g. when the view is not attached, the payload will be
     * simply dropped.
     *
     * @param index
     *         The index of the group, which has been changed, as an {@link Integer} value
     * @param payload
     *         An optional payload as an instance of the class {@link Object} or null to identify a
     *         "full" update
     * @see #notifyGroupRangeChanged(int, int)
     */
    void notifyGroupChanged(int index, Object payload);

    /**
     * Notifies any registered observers that the <code>groupCount</code> groups starting at
     * <code>startIndex</code> have changed. Equivalent to calling <code>notifyGroupRangeChanged(startIndex,
     * groupCount, null);</code>.
     * <p>
     * This is a group change event, not a structural change event. It indicates that any reflection
     * of the data in the given position range is out of date and should be updated. The groups in
     * the given range retain the same identity.
     *
     * @param startIndex
     *         The index of the first group, which has been changed, as an {@link Integer} value
     * @param groupCount
     *         The number of groups, which have been changed, as an {@link Integer} value. The
     *         number of groups must be at least 1
     * @see #notifyGroupChanged(int)
     */
    void notifyGroupRangeChanged(int startIndex, int groupCount);

    /**
     * Notifies any registered observers that the <code>groupCount</code> groups starting at
     * <code>startIndex</code> have changed. An optional payload can be passed to each changed
     * group.
     * <p>
     * This is an group change event, not a structural change event. It indicates that any
     * reflection of the data in the given position range is out of date and should be updated. The
     * groups in the given range retain the same identity.
     * <p>
     * The client can optionally pass a payload for partial change. These payloads will be merged
     * and may be passed to the adapter's <code>onBindViewHolder</code>-method, if the group is
     * already represented by a view holder and it will be rebound to the same view holder. Calling
     * the <code>notifyGroupRangeChanged</code>-method with null payload will clear all existing
     * payloads on that group and prevent future payload until the <code>onBindViewHolder</code>-method
     * is called. Adapters should not assume that the payload will always be passed to the
     * <code>onBindViewHolder</code>-method, e.g. when the view is not attached, the payload will be
     * simply dropped.
     *
     * @param startIndex
     *         The index of the first group, which has been changed, as an {@link Integer} value
     * @param groupCount
     *         The number of groups, which have been changed, as an {@link Integer} value
     * @param payload
     *         An optional payload as an instance of the class {@link Object} or null to identify a
     *         "full" update
     * @see #notifyGroupChanged(int, Object)
     */
    void notifyGroupRangeChanged(int startIndex, int groupCount, Object payload);

    /**
     * Notifies any registered observers that the group at <code>groupIndex</code> has been newly
     * inserted. The group previously at <code>groupIndex</code> is now at <code>groupIndex +
     * 1</code>.
     * <p>
     * This is a structural change event. Representations of other existing groups in the data set
     * are still considered up to date and will not be rebound, though their positions may be
     * altered.
     * <p>
     * If the group is currently expanded, the registered observers will implicitly be notified that
     * the children of the group have been inserted as well. This is equivalent to calling the
     * <code>notifyChildRangeInserted</code>-method for all children.
     *
     * @param groupIndex
     *         The index of the group, which has been inserted, as an {@link Integer} value
     * @see #notifyGroupRangeInserted(int, int)
     */
    void notifyGroupInserted(int groupIndex);

    /**
     * Notifies any registered observers that the currently reflected <code>groupCount</code> groups
     * starting at <code>startIndex</code> have been newly inserted. The groups previously located
     * at <code>startIndex</code> and beyond can now be found starting at <code>startIndex +
     * groupCount</code>.
     * <p>
     * This is a structural change event. Representations of other existing groups in the data set
     * are still considered up to date and will not be rebound, though their positions may be
     * altered.
     *
     * @param startIndex
     *         The index of the first group, which has been inserted, as an {@link Integer} value
     * @param groupCount
     *         The number of groups, which have been inserted, as an {@link Integer} value. The
     *         number of groups must at least 1
     * @see #notifyGroupInserted(int)
     */
    void notifyGroupRangeInserted(int startIndex, int groupCount);

    /**
     * Notifies any registered observers that the group previously located at
     * <code>groupIndex</code> has been removed from the data set. The groups previously located at
     * and after <code>groupIndex</code> may now be found at the <code>oldIndex - 1</code>.
     * <p>
     * This is a structural change event. Representations of other existing groups in the data set
     * are still considered up to date and will not be rebound, though their positions may be
     * altered.
     * <p>
     * If the group is currently expanded, the registered observers will implicitly be notified that
     * the children of the group have been removed as well. This is equivalent to calling the
     * <code>notifyChildRangeRemoved</code>-method for all children.
     *
     * @param groupIndex
     *         The index of the group, which has been removed, as an {@link Integer} value
     */
    void notifyGroupRemoved(int groupIndex);

    /**
     * Notifies any registered observers that the child at <code>childIndex</code> of the group at
     * <code>groupIndex</code>has changed. Equivalent to calling <code>notifyChildChanged(groupIndex,
     * childIndex, null);</code>.
     * <p>
     * This is a child change event, not a structural change event. It indicates that any reflection
     * of the data at <code>childIndex</code> is out of date and should be updated. The child at
     * <code>childIndex</code> retains the same identity.
     * <p>
     *
     * @param groupIndex
     *         The index of the group, the child, which has been changed, corresponds to, as an
     *         {@link Integer} value
     * @param childIndex
     *         The index of the child, which has been changed, as an {@link Integer} value
     * @see #notifyChildRangeChanged(int, int, int)
     */
    void notifyChildChanged(int groupIndex, int childIndex);

    /**
     * Notifies any registered observers that the child at <code>childIndex</code> of the group at
     * <code>groupIndex</code> has changed with an optional payload object.
     * <p>
     * This is a child change event, not a structural change event. It indicates that any reflection
     * of the data at <code>childIndex</code> is out of date and should be updated. The child at
     * <code>childIndex</code> retains the same identity.
     * <p>
     * The client can optionally pass a payload for partial change. These payloads will be merged
     * and may be passed to the adapter's <code>onBindViewHolder</code>-method, if the child is
     * already represented by a view holder and it will be rebound to the same view holder. Calling
     * the <code>notifyChildRangeChanged</code>-method with null payload will clear all existing
     * payloads on that child and prevent future payload until the <code>onBindViewHolder</code>-method
     * is called. Adapters should not assume that the payload will always be passed to the
     * <code>onBindViewHolder</code>-method, e.g. when the view is not attached, the payload will be
     * simply dropped.
     *
     * @param groupIndex
     *         The index of the group, the child, which has been changed, corresponds to, as an
     *         {@link Integer} value
     * @param childIndex
     *         The index of the child, which has been changed, as an {@link Integer} value
     * @param payload
     *         An optional payload as an instance of the class {@link Object} or null to identify a
     *         "full" update
     * @see #notifyChildRangeChanged(int, int, int)
     */
    void notifyChildChanged(int groupIndex, int childIndex, Object payload);

    /**
     * Notifies any registered observers that the <code>childCount</code> children starting at
     * <code>startIndex</code> of the group at <code>groupIndex</code> have changed. Equivalent to
     * calling <code>notifyChildRangeChanged(groupInex, startIndex, childCount, null);</code>.
     * <p>
     * This is a child change event, not a structural change event. It indicates that any reflection
     * of the data in the given position range is out of date and should be updated. The children in
     * the given range retain the same identity.
     *
     * @param groupIndex
     *         The index of the group, the children, which have been changed, correspond to, as an
     *         {@link Integer} value
     * @param startIndex
     *         The index of the first child, which has been changed, as an {@link Integer} value
     * @param childCount
     *         The number of children, which have been changed, as an {@link Integer} value. The
     *         number of children must be at least 1
     * @see #notifyChildChanged(int, int)
     */
    void notifyChildRangeChanged(int groupIndex, int startIndex, int childCount);

    /**
     * Notifies any registered observers that the <code>childCount</code> children starting at
     * <code>startIndex</code> of the group at <code>groupIndex</code> have changed. An optional
     * payload can be passed to each changed child.
     * <p>
     * This is a child change event, not a structural change event. It indicates that any reflection
     * of the data in the given position range is out of date and should be updated. The children in
     * the given range retain the same identity.
     * <p>
     * The client can optionally pass a payload for partial change. These payloads will be merged
     * and may be passed to the adapter's <code>onBindViewHolder</code>-method, if the child is
     * already represented by a view holder and it will be rebound to the same view holder. Calling
     * the <code>notifyChildRangeChanged</code>-method with null payload will clear all existing
     * payloads on that group and prevent future payload until the <code>onBindViewHolder</code>-method
     * is called. Adapters should not assume that the payload will always be passed to the
     * <code>onBindViewHolder</code>-method, e.g. when the view is not attached, the payload will be
     * simply dropped.
     *
     * @param groupIndex
     *         The index of the group, the children, which have been changed, corresponds to, as an
     *         {@link Integer} value
     * @param startIndex
     *         The index of the first child, which has been changed, as an {@link Integer} value
     * @param childCount
     *         The number of children, which have been changed, as an {@link Integer} value
     * @param payload
     *         An optional payload as an instance of the class {@link Object} or null to identify a
     *         "full" update
     * @see #notifyChildChanged(int, int, Object)
     */
    void notifyChildRangeChanged(int groupIndex, int startIndex, int childCount, Object payload);

    /**
     * Notifies any registered observers that the child at <code>childIndex</code> of the group at
     * <code>groupIndex</code> has been newly inserted. The child previously at
     * <code>childIndex</code> is now at <code>childIndex + 1</code>.
     * <p>
     * This is a structural change event. Representations of other existing children in the data set
     * are still considered up to date and will not be rebound, though their positions may be
     * altered.
     *
     * @param groupIndex
     *         The index of hte group, the child, which has been inserted, corresponds to, as an
     *         {@link Integer} value
     * @param childIndex
     *         The index of the child, which has been inserted, as an {@link Integer} value
     * @see #notifyChildRangeInserted(int, int, int)
     */
    void notifyChildInserted(int groupIndex, int childIndex);

    /**
     * Notifies any registered observers that the currently reflected <code>childCount</code>
     * children starting at <code>startIndex</code> of the group at <code>groupIndex</code> have
     * been newly inserted. The children previously located at <code>startIndex</code> and beyond
     * can now be found starting at <code>startIndex + childCount</code>.
     * <p>
     * This is a structural change event. Representations of other existing children in the data set
     * are still considered up to date and will not be rebound, though their positions may be
     * altered.
     *
     * @param groupIndex
     *         The index of hte group, the child, which has been inserted, corresponds to, as an
     *         {@link Integer} value
     * @param startIndex
     *         The index of the first group, which has been inserted, as an {@link Integer} value
     * @param childCount
     *         The number of groups, which have been inserted, as an {@link Integer} value. The
     *         number of groups must at least 1
     * @see #notifyChildInserted(int, int)
     */
    void notifyChildRangeInserted(int groupIndex, int startIndex, int childCount);

    /**
     * Notifies any registered observers that the child previously located at
     * <code>childIndex</code> of the group at <code>groupIndex</code> has been removed from the
     * data set. The children previously located at and after <code>childIndex</code> may now be
     * found at <code>oldIndex - 1</code>.
     * <p>
     * This is a structural change event. Representations of other existing children in the data set
     * are still considered up to date and will not be rebound, though their positions may be
     * altered.
     *
     * @param groupIndex
     *         The index of the group, the child, which has been removed, corresponds to, as an
     *         {@link Integer} value
     * @param childIndex
     *         The index of the child, which has been removed, as an {@link Integer} value
     * @see #notifyChildRangeRemoved(int, int, int)
     */
    void notifyChildRemoved(int groupIndex, int childIndex);

    /**
     * Notifies any registered observers that the <code>childCount</code> children previously
     * located at <code>startIndex</code> of the group at <code>groupIndex</code> have been removed
     * from the data set. The children previously located at and after <code>startIndex +
     * childCount</code> may now be found at <code>oldIndex - childCount</code>.
     * <p>
     * This is a structural change event. Representations of other existing children in the data set
     * are still considered up to date and will not be rebound, though their positions may be
     * altered.
     *
     * @param groupIndex
     *         The index of the group, the children, which have been removed, correspond to, as an
     *         {@link Integer} value
     * @param startIndex
     *         The previous index of the first child, which has been removed, as an {@link Integer}
     *         value
     * @param childCount
     *         The number of children, which have been removed, as an {@link Integer} value. The
     *         number of children must be at least 1
     */
    void notifyChildRangeRemoved(int groupIndex, int startIndex, int childCount);

    @Override
    ExpandableRecyclerViewAdapter clone() throws CloneNotSupportedException;

}