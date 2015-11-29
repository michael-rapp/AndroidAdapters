package de.mrapp.android.adapter.datastructure.group;

import de.mrapp.android.adapter.Filter;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

/**
 * A filter, which allows to filter groups, depending on their data.
 * 
 *
 * @param <GroupType>
 *            The type of the group's data
 * @param <ChildType>
 *            The type of the group's children
 * 
 * @author Michael Rapp
 * 
 * @since 0.1.0
 */
public class GroupFilter<GroupType, ChildType> implements Filter<Group<GroupType, ChildType>> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The filter, which is used to filter the groups, depending on their data.
	 */
	private final Filter<GroupType> filter;

	/**
	 * Creates a new filter, which allows to filter groups, depending on their
	 * data.
	 * 
	 * @param filter
	 *            The filter, which should be used to filter the groups,
	 *            depending on their data, as an instance of the type
	 *            {@link Filter}. The filter may not be null
	 */
	public GroupFilter(final Filter<GroupType> filter) {
		ensureNotNull(filter, "The filter may not be null");
		this.filter = filter;
	}

	@Override
	public final boolean match(final Group<GroupType, ChildType> data, final String query, final int flags) {
		return filter.match(data.getData(), query, flags);
	}

}