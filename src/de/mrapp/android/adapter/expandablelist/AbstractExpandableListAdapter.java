package de.mrapp.android.adapter.expandablelist;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;
import android.content.Context;
import android.widget.BaseExpandableListAdapter;
import de.mrapp.android.adapter.datastructure.DataStructure;
import de.mrapp.android.adapter.datastructure.Restorable;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.list.AbstractListAdapter;

public abstract class AbstractExpandableListAdapter<GroupDataType, ChildDataType, GroupAdapterType extends AbstractListAdapter<Group<GroupDataType, ChildDataType, ChildAdapterType>, GroupDecoratorType>, ChildAdapterType extends AbstractListAdapter<ChildDataType, ChildDecoratorType>, ChildDecoratorType, GroupDecoratorType>
		extends BaseExpandableListAdapter implements DataStructure, Restorable {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	private GroupAdapterType groupAdapter;

	protected AbstractExpandableListAdapter(final Context context) {
		ensureNotNull(context, "The context may not be null");
	}

	@Override
	public final ChildDataType getChild(final int groupIndex,
			final int childIndex) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.getItem(childIndex);
	}

	@Override
	public final long getChildId(final int groupIndex, final int childIndex) {
		int id = childIndex;

		for (int i = 0; i < groupIndex; i++) {
			Group<GroupDataType, ChildDataType, ChildAdapterType> group = groupAdapter
					.getItem(i);
			id += group.getChildAdapter().getNumberOfItems();
		}

		return id;
	}

	@Override
	public final int getChildrenCount(final int groupIndex) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.getNumberOfItems();
	}

	@Override
	public final GroupDataType getGroup(final int groupIndex) {
		return groupAdapter.getItem(groupIndex).getData();
	}

	@Override
	public final int getGroupCount() {
		return groupAdapter.getNumberOfItems();
	}

	@Override
	public final long getGroupId(final int groupIndex) {
		return groupIndex;
	}

	@Override
	public final boolean isChildSelectable(final int groupIndex,
			final int childIndex) {
		return true;
	}

	@Override
	public final boolean hasStableIds() {
		return true;
	}

	@Override
	public abstract AbstractExpandableListAdapter<GroupDataType, ChildDataType, GroupAdapterType, ChildAdapterType, ChildDecoratorType, GroupDecoratorType> clone()
			throws CloneNotSupportedException;

}