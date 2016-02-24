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
package de.mrapp.android.adapter.datastructure.group;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;

import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.datastructure.AbstractAdapterItem;

/**
 * A data structure, which categorizes multiple items of an adapter. A group has all properties of
 * an item, but additionally it does contain an adapter, which manages the group's child items.
 *
 * @param <GroupType>
 *         The type of the groups's data
 * @param <ChildType>
 *         The type of the child items' data
 * @author Michael Rapp
 * @since 0.1.0
 */
public class Group<GroupType, ChildType> extends AbstractAdapterItem<GroupType> {

    /**
     * A creator, which allows to create instances of the class {@link Group} from parcels.
     */
    @SuppressWarnings("rawtypes")
    public static final Creator<Group> CREATOR = new Creator<Group>() {

        @Override
        public Group createFromParcel(final Parcel source) {
            return new Group(source);
        }

        @Override
        public Group[] newArray(final int size) {
            return new Group[size];
        }

    };

    /**
     * The flag, which allows to filter empty groups.
     */
    public static final int FLAG_FILTER_EMPTY_GROUPS = 281281382;

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The adapter, which manages the group's child items.
     */
    private transient MultipleChoiceListAdapter<ChildType> childAdapter;

    /**
     * True, if the group is expanded, false otherwise.
     */
    private boolean expanded;

    /**
     * Creates a new data structure, which categorizes multiple items of an adapter.
     *
     * @param source
     *         The source, the group should be created from, as an instance of the class {@link
     *         Parcel}. The source may not be null
     */
    private Group(@NonNull final Parcel source) {
        super(source);
        setChildAdapter(null);
        setExpanded(source.readInt() > 0);
    }

    /**
     * Creates a new data structure, which categorizes multiple items of an adapter.
     *
     * @param data
     *         The group's data, as an instance of the generic type GroupType. The data may not be
     *         null
     */
    public Group(@NonNull final GroupType data) {
        this(data, null);
    }

    /**
     * Creates a new data structure, which categorizes multiple items of an adapter.
     *
     * @param data
     *         The group's data, as an instance of the generic type GroupType. The data may not be
     *         null
     * @param childAdapter
     *         The adapter, which should be used to manage the group's child items, as an instance
     *         of the type {@link MultipleChoiceListAdapter} or null, if no adapter should be set
     */
    public Group(@NonNull final GroupType data,
                 @Nullable final MultipleChoiceListAdapter<ChildType> childAdapter) {
        super(data);
        setChildAdapter(childAdapter);
        setExpanded(false);
    }

    /**
     * Returns the adapter, which manages the group's child items.
     *
     * @return The adapter, which manages the group's child items, as an instance of the type {@link
     * MultipleChoiceListAdapter} or null, if no adapter has been set
     */
    public final MultipleChoiceListAdapter<ChildType> getChildAdapter() {
        return childAdapter;
    }

    /**
     * Sets the adapter, which manages the group's child items.
     *
     * @param childAdapter
     *         The adapter, which should be set, as an instance of the type {@link
     *         MultipleChoiceListAdapter} or null, if no adapter should be set
     */
    public final void setChildAdapter(
            @Nullable final MultipleChoiceListAdapter<ChildType> childAdapter) {
        this.childAdapter = childAdapter;
    }

    /**
     * Returns, whether the group is expanded, or not.
     *
     * @return True, if the group is expanded, false otherwise
     */
    public final boolean isExpanded() {
        return expanded;
    }

    /**
     * Sets, whether the group is expanded, or not.
     *
     * @param expanded
     *         True, if the group should be expanded, false otherwise
     */
    public final void setExpanded(final boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public final boolean isParcelable() {
        return Parcelable.class.isAssignableFrom(getData().getClass()) &&
                (childAdapter == null || childAdapter.isEmpty() ||
                        Parcelable.class.isAssignableFrom(childAdapter.getItem(0).getClass()));
    }

    @Override
    public final boolean isSerializable() {
        return Serializable.class.isAssignableFrom(getData().getClass()) &&
                (childAdapter == null || childAdapter.isEmpty() ||
                        Serializable.class.isAssignableFrom(childAdapter.getItem(0).getClass()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public final Group<GroupType, ChildType> clone() throws CloneNotSupportedException {
        try {
            GroupType clonedData =
                    (GroupType) getData().getClass().getMethod("clone").invoke(getData());
            Group<GroupType, ChildType> clonedGroup = new Group<>(clonedData);
            MultipleChoiceListAdapter<ChildType> clonedChildAdapter = null;

            if (childAdapter != null) {
                clonedChildAdapter = childAdapter.clone();
            }

            clonedGroup.setChildAdapter(clonedChildAdapter);
            clonedGroup.setExpanded(expanded);
            return clonedGroup;
        } catch (Exception e) {
            throw new CloneNotSupportedException();
        }
    }

    @Override
    public final boolean match(@NonNull final String query, final int flags) {
        if (flags == FLAG_FILTER_EMPTY_GROUPS) {
            return !(getChildAdapter() == null || getChildAdapter().isEmpty());
        } else {
            return super.match(query, flags);
        }

    }

    @Override
    public final String toString() {
        return "Group [data=" + getData() + ", expanded=" + expanded + "]";
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (expanded ? 1231 : 1237);
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Group<?, ?> other = (Group<?, ?>) obj;
        if (expanded != other.expanded)
            return false;
        return true;
    }

    @Override
    public final void writeToParcel(final Parcel dest, final int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(isExpanded() ? 1 : 0);
    }

}