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

/**
 * Defines the interface, all expandable list adapters, which can be attached to a RecyclerView,
 * must implement.
 *
 * @author Michael Rapp
 * @since 0.8.0
 */
public interface ExpandableRecyclerViewAdapter extends ExpandableGridViewAdapter {

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
     * Notifies any registered observers that the group at <code>groupIndex</code> has changed.
     * Equivalent to calling <code>notifyGroupChanged(groupIndex, null);</code>.
     * <p>
     * This is a group change event, not a structural change event. It indicates that any reflection
     * of the data at <code>groupIndex</code> is out of date and should be updated. The group at
     * <code>groupIndex</code> retains the same identity.
     * <p>
     * Optionally, if the group is currently expanded, the registered observers can be notified that
     * the children of the changed group have been changed as well. This is equivalent to calling
     * the <code>notifyChildRangeChanged</code>-method for all children of the according group.
     *
     * @param groupIndex
     *         The index of the group, which has been changed, as an {@link Integer} value
     * @param childrenChanged
     *         True, if the children of the group have been changed as well, false otherwise
     * @see #notifyGroupRangeChanged(int, int)
     */
    void notifyGroupChanged(int groupIndex, boolean childrenChanged);

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
     * @param childrenChanged
     *         True, if the children of the group have been changed as well, false otherwise
     * @param payload
     *         An optional payload as an instance of the class {@link Object} or null to identify a
     *         "full" update
     * @see #notifyGroupRangeChanged(int, int)
     */
    void notifyGroupChanged(int index, boolean childrenChanged, Object payload);

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
     *         number of items must be at least 1
     * @see #notifyGroupChanged(int)
     */
    void notifyGroupRangeChanged(int startIndex, int groupCount);

    /**
     * Notifies any registered observers that the <code>groupCount</code> groups starting at
     * <code>startIndex</code> have changed. Equivalent to calling <code>notifyGroupRangeChanged(startIndex,
     * groupCount, null);</code>.
     * <p>
     * This is a group change event, not a structural change event. It indicates that any reflection
     * of the data in the given position range is out of date and should be updated. The groups in
     * the given range retain the same identity.
     * <p>
     * Optionally, if the group is currently expanded, the registered observers can be notified that
     * the children of the changed groups have been changed as well. This is equivalent to calling
     * the <code>notifyChildRangeChanged</code>-method for all children of the according groups.
     *
     * @param startIndex
     *         The index of the first group, which has been changed, as an {@link Integer} value
     * @param groupCount
     *         The number of groups, which have been changed, as an {@link Integer} value. The
     *         number of items must be at least 1
     * @param childrenChanged
     *         True, if the children of the groups have been changed as well, false otherwise
     * @see #notifyGroupChanged(int, boolean)
     */
    void notifyGroupRangeChanged(int startIndex, int groupCount, boolean childrenChanged);

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
     *         The number of items, which have been changed, as an {@link Integer} value
     * @param childrenChanged
     *         True, if the children of the groups have been changed as well, false otherwise
     * @param payload
     *         An optional payload as an instance of the class {@link Object} or null to identify a
     *         "full" update
     * @see #notifyGroupChanged(int, boolean, Object)
     */
    void notifyGroupRangeChanged(int startIndex, int groupCount, boolean childrenChanged,
                                 Object payload);

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
     * Notifies any registered observers that the group reflected at <code>fromIndex</code> has been
     * moved to <code>toIndex</code>.
     * <p>
     * This is a structural change event. Representations of other existing groups in the data set
     * are still considered up to date and will not be rebound, though their positions may be
     * altered.
     * <p>
     * If the group is currently expanded, the registered observers will implicitly be notified that
     * the children of the group have been inserted as well. This is equivalent to calling the
     * <code>notifyChildMoved</code>-method for all children.
     *
     * @param fromIndex
     *         The previous index of the group, as an {@link Integer} value
     * @param toIndex
     *         The new index of the group as an {@link Integer} value
     */
    void notifyGroupMoved(int fromIndex, int toIndex);

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
     * @see #notifyGroupRangeRemoved(int, int)
     */
    void notifyGroupRemoved(int groupIndex);

    /**
     * Notifies any registered observers that the <code>groupCount</code> items previously located
     * at <code>startIndex</code> have been removed from the data set. The items previously located
     * at and after <code>startIndex + groupCount</code> may now be found at <code>oldIndex -
     * groupCount</code>.
     * <p>
     * This is a structural change event. Representations of other existing groups in the data set
     * are still considered up to date and will not be rebound, though their positions may be
     * altered.
     * <p>
     * If a group is currently expanded, the registered observers will implicitly be notified that
     * the children of the group have been removed as well. This is equivalent to calling the
     * <code>notifyChildRangeRemoved</code>-method for all children.
     *
     * @param startIndex
     *         The previous index of the first group, which has been removed, as an {@link Integer}
     *         value
     * @param groupCount
     *         The number of groups, which has been removed, as an {@link Integer} value. The number
     *         of groups must be at least 1
     */
    void notifyGroupRangeRemoved(int startIndex, int groupCount);

    @Override
    ExpandableRecyclerViewAdapter clone() throws CloneNotSupportedException;

}