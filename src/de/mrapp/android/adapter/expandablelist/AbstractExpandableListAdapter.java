/*
 * AndroidAdapters Copyright 2014 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU Lesser General Public License as published 
 * by the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>. 
 */
package de.mrapp.android.adapter.expandablelist;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import de.mrapp.android.adapter.ExpandableListAdapter;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.datastructure.group.GroupIterator;
import de.mrapp.android.adapter.datastructure.group.GroupListIterator;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.selectable.MultipleChoiceListAdapterImplementation;
import de.mrapp.android.adapter.logging.LogLevel;
import de.mrapp.android.adapter.logging.Logger;
import de.mrapp.android.adapter.util.VisibleForTesting;

/**
 * An abstract base class for all adapters, whose underlying data is managed as
 * a list of arbitrary group and child items. Such an adapter's purpose is to
 * provide the underlying data for visualization using a
 * {@link ExpandableListView} widget.
 * 
 * @param <GroupType>
 *            The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *            The type of the underlying data of the adapter's child items
 * @param <DecoratorType>
 *            The type of the decorator, which allows to customize the
 *            appearance of the views, which are used to visualize the group and
 *            child items of the adapter
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class AbstractExpandableListAdapter<GroupType, ChildType, DecoratorType>
		extends BaseExpandableListAdapter implements
		ExpandableListAdapter<GroupType, ChildType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The key, which is used to store the adapter, which manages the adapter's
	 * group items, within a bundle.
	 */
	@VisibleForTesting
	protected static final String GROUP_ADAPTER_BUNDLE_KEY = AbstractExpandableListAdapter.class
			.getSimpleName() + "::GroupAdapter";

	/**
	 * The key, which is used to store the adapters, which manage the adapter's
	 * child items, within a bundle.
	 */
	@VisibleForTesting
	protected static final String CHILD_ADAPTER_BUNDLE_KEY = AbstractExpandableListAdapter.class
			.getSimpleName() + "::ChildAdapter_%s";

	/**
	 * The key, which is used to store, whether duplicate child items should be
	 * allowed, or not, within a bundle.
	 */
	@VisibleForTesting
	protected static final String ALLOW_DUPLICATE_CHILDREN_BUNDLE_KEY = AbstractExpandableListAdapter.class
			.getSimpleName() + "::AllowDuplicateChildren";

	/**
	 * The key, which is used to store, whether a group should be expanded, when
	 * it is clicked by the user, or not, within a bundle.
	 */
	@VisibleForTesting
	protected static final String EXPAND_GROUP_ON_CLICK_BUNDLE_KEY = AbstractExpandableListAdapter.class
			.getSimpleName() + "::ExpandGroupOnClick";

	/**
	 * The key, which is used to store the log level, which is used for logging,
	 * within a bundle.
	 */
	@VisibleForTesting
	protected static final String LOG_LEVEL_BUNDLE_KEY = AbstractExpandableListAdapter.class
			.getSimpleName() + "::LogLevel";

	/**
	 * The context, the adapter belongs to.
	 */
	private final transient Context context;

	/**
	 * The inflater, which is used to inflate the views, which are used to
	 * visualize the adapter's group items.
	 */
	private final transient Inflater groupInflater;

	/**
	 * The inflater, which is used to inflate the views, which are used to
	 * visualize the adapter's child items.
	 */
	private final transient Inflater childInflater;

	/**
	 * The decorator, which allows to customize the appearance of the views,
	 * which are used to visualize the group and child items of the adapter.
	 */
	private final transient DecoratorType decorator;

	/**
	 * The logger, which is used for logging.
	 */
	private final transient Logger logger;

	/**
	 * A set, which contains the listeners, which should be notified, when an
	 * item of the adapter has been clicked by the user.
	 */
	private transient Set<ExpandableListAdapterItemClickListener<GroupType, ChildType>> itemClickListeners;

	/**
	 * A set, which contains the listeners, which should be notified, when the
	 * adapter's underlying data has been modified.
	 */
	private transient Set<ExpandableListAdapterListener<GroupType, ChildType>> adapterListeners;

	/**
	 * A set, which contains the listeners, which should be notified, when a
	 * group item has been expanded or collapsed.
	 */
	private transient Set<ExpansionListener<GroupType, ChildType>> expansionListeners;

	/**
	 * The view, the adapter is currently attached to.
	 */
	private transient ExpandableListView adapterView;

	/**
	 * True, if duplicate children, regardless from the group they belong to,
	 * are allowed, false otherwise.
	 */
	private boolean allowDuplicateChildren;

	/**
	 * True, if the a group should be expanded, when it is clicked by the user,
	 * false otherwise.
	 */
	private boolean expandGroupOnClick;

	/**
	 * An adapter, which manages the adapter's group items.
	 */
	private MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter;

	/**
	 * Notifies all listeners, which have been registered to be notified, when
	 * an item of the adapter has been clicked by the user, about a group, which
	 * has been clicked.
	 * 
	 * @param group
	 *            The group, which has been clicked by the user, as an instance
	 *            of the generic type GroupType. The item may not be null
	 * @param index
	 *            The index of the group, which has been clicked by the user, as
	 *            an {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfGroups():int</code> - 1
	 */
	private void notifyOnGroupClicked(final GroupType group, final int index) {
		for (ExpandableListAdapterItemClickListener<GroupType, ChildType> listener : itemClickListeners) {
			listener.onGroupClicked(this, group, index);
		}
	}

	/**
	 * Notifies all listeners, which have been registered to be notified, when
	 * an item of the adapter has been clicked by the user, about a child, which
	 * has been clicked.
	 * 
	 * @param child
	 *            The child, which has been clicked by the user, as an instance
	 *            of the generic type ChildType. The item may not be null
	 * @param childIndex
	 *            The index of the child, which has been clicked by the user, as
	 *            an {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfChildren():int</code> - 1
	 * @param group
	 *            The group, the child, which has been clicked by the user,
	 *            belongs to, as an instance of the generic type GroupType. The
	 *            item may not be null
	 * @param groupIndex
	 *            The index of the group, the child, which has been clicked by
	 *            the user, belongs to, as an {@link Integer} value. The index
	 *            must be between 0 and the value of the method
	 *            <code>getNumberOfGroups():int</code> - 1
	 */
	private void notifyOnChildClicked(final ChildType child,
			final int childIndex, final GroupType group, final int groupIndex) {
		for (ExpandableListAdapterItemClickListener<GroupType, ChildType> listener : itemClickListeners) {
			listener.onChildClicked(this, child, childIndex, group, groupIndex);
		}
	}

	/**
	 * Creates and returns an adapter, which may be used to manage the adapter's
	 * child items.
	 * 
	 * @return The adapter, which has been created, as an instance of the type
	 *         {@link MultipleChoiceListAdapter}. The adapter may not be null
	 */
	private MultipleChoiceListAdapter<ChildType> createChildAdapter() {
		MultipleChoiceListAdapter<ChildType> childAdapter = new MultipleChoiceListAdapterImplementation<ChildType>(
				context, childInflater, new NullObjectDecorator<ChildType>());
		childAdapter.setLogLevel(LogLevel.OFF);
		return childAdapter;
	}

	/**
	 * Creates and returns a listener, which allows to trigger the expansion
	 * state of a group, when it is clicked by the user.
	 * 
	 * @return The listener, which has been created, as an instance of the type
	 *         {@link ExpandableListAdapterItemClickListener}
	 */
	private ExpandableListAdapterItemClickListener<GroupType, ChildType> createGroupClickListener() {
		return new ExpandableListAdapterItemClickListener<GroupType, ChildType>() {

			@Override
			public void onGroupClicked(
					final ExpandableListAdapter<GroupType, ChildType> adapter,
					final GroupType group, final int index) {
				if (isGroupExpandedOnClick()) {
					triggerGroupExpansion(index);
					getLogger().logVerbose(getClass(),
							"Triggering group expansion on click...");
				}
			}

			@Override
			public void onChildClicked(
					final ExpandableListAdapter<GroupType, ChildType> adapter,
					final ChildType child, final int childIndex,
					final GroupType group, final int groupIndex) {
				return;
			}

		};
	}

	/**
	 * Creates and returns an {@link OnClickListener}, which is invoked, when a
	 * specific group item has been clicked.
	 * 
	 * @param index
	 *            The index of the group item, which should cause the listener
	 *            to be invoked, when clicked, as an {@link Integer} value
	 * @return The listener, which has been created as an instance of the type
	 *         {@link OnClickListener}
	 */
	private OnClickListener createGroupItemOnClickListener(final int index) {
		return new OnClickListener() {

			@Override
			public void onClick(final View v) {
				notifyOnGroupClicked(getGroup(index), index);
			}

		};
	}

	/**
	 * Creates and returns an {@link OnClickListener}, which is invoked, when a
	 * specific child item has been clicked.
	 * 
	 * @param groupIndex
	 *            The index of the group, the child item, which should cause the
	 *            listener to be invoked, when clicked, belongs to, as an
	 *            {@link Integer} value
	 * @param childIndex
	 *            The index of the child item, which should cause the listener
	 *            to be invoked, when clicked, as an {@link Integer} value
	 * @return The listener, which has been created as an instance of the type
	 *         {@link OnClickListener}
	 */
	private OnClickListener createChildItemOnClickListener(
			final int groupIndex, final int childIndex) {
		return new OnClickListener() {

			@Override
			public void onClick(final View v) {
				notifyOnChildClicked(getChild(groupIndex, childIndex),
						childIndex, getGroup(groupIndex), groupIndex);
			}

		};
	}

	/**
	 * Returns, whether the underlying data of the adapter's group items
	 * implements the interface {@link Serializable}, or not.
	 * 
	 * @return True, if the underlying data of the adapter's group items
	 *         implements the interface {@link Serializable}, false otherwise
	 */
	private boolean isGroupDataSerializable() {
		if (!isEmpty()) {
			if (!Serializable.class.isAssignableFrom(getGroup(0).getClass())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns, whether the underlying data of the adapter's child items
	 * implements the interface {@link Serializable}, or not.
	 * 
	 * @return True, if the underlying data of the adapter's child items
	 *         implements the interface {@link Serializable}, false otherwise
	 */
	private boolean isChildDataSerializable() {
		List<ChildType> children = getAllChildren();

		if (!children.isEmpty()) {
			if (!Serializable.class
					.isAssignableFrom(children.get(0).getClass())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns, the context, the adapter belongs to.
	 * 
	 * @return The context, the adapter belongs to, as an instance of the class
	 *         {@link Context}. The context may not be null
	 */
	protected final Context getContext() {
		return context;
	}

	/**
	 * Returns the inflater, which is used to inflate the views, which are used
	 * to visualize the adapter's group items.
	 * 
	 * @return The inflater, which is used to inflate views, which are used to
	 *         visualize the adapter's group items, as an instance of the type
	 *         {@link Inflater}. The inflater may not be null
	 */
	protected final Inflater getGroupInflater() {
		return groupInflater;
	}

	/**
	 * Returns the inflater, which is used to inflate the views, which are used
	 * to visualize the adapter's child items.
	 * 
	 * @return The inflater, which is used to inflate views, which are used to
	 *         visualize the adapter's child items, as an instance of the type
	 *         {@link Inflater}. The inflater may not be null
	 */
	protected final Inflater getChildInflater() {
		return childInflater;
	}

	/**
	 * Returns the decorator, which allows to customize the appearance of the
	 * views, which are used to visualize the group and child items of the
	 * adapter.
	 * 
	 * @return The decorator, which allows to customize the appearance of the
	 *         views, which are used to visualize the group and child items of
	 *         the adapter, as an instance of the generic type DecoratorType.
	 *         The decorator may not be null
	 */
	protected final DecoratorType getDecorator() {
		return decorator;
	}

	/**
	 * Returns the logger, which is used for logging.
	 * 
	 * @return The logger, which is used for logging, as an instance of the
	 *         class {@link Logger}. The logger may not be null
	 */
	protected final Logger getLogger() {
		return logger;
	}

	/**
	 * Creates and returns a deep copy of the adapter, which manages the
	 * adapter's group items.
	 * 
	 * @return A deep copy of the adapter, which manages the adapter's group
	 *         items, as an instance of the type
	 *         {@link MultipleChoiceListAdapter}. The adapter may not be null
	 * @throws CloneNotSupportedException
	 *             The exception, which is thrown, if cloning is not supported
	 *             by the adapter's underlying data
	 */
	protected final MultipleChoiceListAdapter<Group<GroupType, ChildType>> cloneGroupAdapter()
			throws CloneNotSupportedException {
		return groupAdapter.clone();
	}

	/**
	 * Returns a set, which contains the listeners, which should be notified,
	 * when an item of the adapter has been clicked by the user.
	 * 
	 * @return A set, which contains the listeners, which should be notified,
	 *         when an item of the adapter has been clicked by the user, as an
	 *         instance of the type {@link Set} or an empty set, if no listeners
	 *         should be notified
	 */
	protected final Set<ExpandableListAdapterItemClickListener<GroupType, ChildType>> getItemClickListeners() {
		return itemClickListeners;
	}

	/**
	 * Returns a set, which contains the listeners, which should be notified,
	 * when the adapter's underlying data has been modified.
	 * 
	 * @return A set, which contains the listeners, which should be notified,
	 *         when the adapter's underlying data has been modified, as an
	 *         instance of the type {@link Set} or an empty set, if no listeners
	 *         should be notified
	 */
	protected final Set<ExpandableListAdapterListener<GroupType, ChildType>> getAdapterListeners() {
		return adapterListeners;
	}

	/**
	 * Returns a set, which contains the listeners, which should be notified,
	 * when a group item has been expanded or collapsed.
	 * 
	 * @return A set, which contains the listeners which should be notified,
	 *         when a group item has been expanded or collapsed, as an instance
	 *         of the type {@link Set} or an empty set, if no listeners should
	 *         be notified
	 */
	protected final Set<ExpansionListener<GroupType, ChildType>> getExpansionListeners() {
		return expansionListeners;
	}

	/**
	 * Returns the view, the adapter is currently attached to.
	 * 
	 * @return The view, the adapter is currently attached to, as an instance of
	 *         the class {@link ExpandableListView}, or null, if the adapter is
	 *         currently not attached to a view
	 */
	protected final ExpandableListView getAdapterView() {
		return adapterView;
	}

	/**
	 * This method is invoked to apply the decorator, which allows to customize
	 * the view, which is used to visualize a specific group item.
	 * 
	 * @param context
	 *            The context, the adapter belongs to, as an instance of the
	 *            class {@link Context}. The context may not be null
	 * @param view
	 *            The view, which is used to visualize the group item, as an
	 *            instance of the class {@link View}. The view may not be null
	 * @param index
	 *            The index of the group item, which should be visualized, as an
	 *            {@link Integer} value
	 */
	protected abstract void applyDecoratorOnGroup(final Context context,
			final View view, final int index);

	/**
	 * This method is invoked to apply the decorator, which allows to customize
	 * the view, which is used to visualize a specific child item.
	 * 
	 * @param context
	 *            The context, the adapter belongs to, as an instance of the
	 *            class {@link Context}. The context may not be null
	 * @param view
	 *            The view, which is used to visualize the child item, as an
	 *            instance of the class {@link View}. The view may not be null
	 * @param groupIndex
	 *            The index of the group, the child item, which should be
	 *            visualized, belongs to, as an {@link Integer} value
	 * @param childIndex
	 *            The index of the child item, which should be visualized, as an
	 *            {@link Integer} value
	 */
	protected abstract void applyDecoratorOnChild(final Context context,
			final View view, final int groupIndex, final int childIndex);

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary group and child items.
	 * 
	 * @param context
	 *            The context, the adapter belongs to, as an instance of the
	 *            class {@link Context}. The context may not be null
	 * @param groupInflater
	 *            The inflater, which should be used to inflate the views, which
	 *            are used to visualize the adapter's group items, as an
	 *            instance of the type {@link Inflater}. The inflater may not be
	 *            null
	 * @param childInflater
	 *            The inflater, which should be used to inflate the views, which
	 *            are used to visualize the adapter's child items, as an
	 *            instance of the type {@link Inflater}. The inflater may not be
	 *            null
	 * @param decorator
	 *            The decorator, which should be used to customize the
	 *            appearance of the views, which are used to visualize the group
	 *            and child items of the adapter, as an instance of the generic
	 *            type DecoratorType. The decorator may not be null
	 * @param logLevel
	 *            The log level, which should be used for logging, as a value of
	 *            the enum {@link LogLevel}. The log level may not be null
	 * @param groupAdapter
	 *            The adapter, which should manage the adapter's group items, as
	 *            an instance of the type {@link MultipleChoiceListAdapter}. The
	 *            adapter may not be null
	 * @param allowDuplicateChildren
	 *            True, if duplicate group items, regardless from the group they
	 *            belong to, should be allowed, false otherwise
	 * @param expandGroupOnClick
	 *            True, if a group should be expanded, when it is clicked by the
	 *            user, false otherwise
	 * @param itemClickListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when an item of the adapter has been clicked by the user, as
	 *            an instance of the type {@link Set} or an empty set, if no
	 *            listeners should be notified
	 * @param adapterListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been modified, as an
	 *            instance of the type {@link Set} or an empty set, if no
	 *            listeners should be notified
	 * @param expansionListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when a group item has been expanded or collapsed, as an
	 *            instance of the type {@link Set} or an empty set, if no
	 *            listeners should be notified
	 */
	protected AbstractExpandableListAdapter(
			final Context context,
			final Inflater groupInflater,
			final Inflater childInflater,
			final DecoratorType decorator,
			final LogLevel logLevel,
			final MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter,
			final boolean allowDuplicateChildren,
			final boolean expandGroupOnClick,
			final Set<ExpandableListAdapterItemClickListener<GroupType, ChildType>> itemClickListeners,
			final Set<ExpandableListAdapterListener<GroupType, ChildType>> adapterListeners,
			final Set<ExpansionListener<GroupType, ChildType>> expansionListeners) {
		ensureNotNull(context, "The context may not be null");
		ensureNotNull(groupInflater, "The group inflater may not be null");
		ensureNotNull(childInflater, "The child inflater may not be null");
		ensureNotNull(decorator, "The decorator may not be null");
		ensureNotNull(itemClickListeners,
				"The item click listeners may not be null");
		ensureNotNull(adapterListeners, "The adapter listeners may not be null");
		ensureNotNull(expansionListeners,
				"The expansion listeners may not be null");
		this.context = context;
		this.groupInflater = groupInflater;
		this.childInflater = childInflater;
		this.decorator = decorator;
		this.logger = new Logger(logLevel);
		this.groupAdapter = groupAdapter;
		this.groupAdapter.setLogLevel(LogLevel.OFF);
		this.allowDuplicateChildren = allowDuplicateChildren;
		this.expandGroupOnClick = expandGroupOnClick;
		this.itemClickListeners = itemClickListeners;
		this.adapterListeners = adapterListeners;
		this.expansionListeners = expansionListeners;
		addItemClickListener(createGroupClickListener());
	}

	@Override
	public final LogLevel getLogLevel() {
		return getLogger().getLogLevel();
	}

	@Override
	public final void setLogLevel(final LogLevel logLevel) {
		getLogger().setLogLevel(logLevel);
	}

	@Override
	public final Bundle getParameters() {
		return groupAdapter.getParameters();
	}

	@Override
	public final void setParameters(final Bundle parameters) {
		groupAdapter.setParameters(parameters);
		String message = "Set parameters to \"" + parameters + "\"";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final void addItemClickListener(
			final ExpandableListAdapterItemClickListener<GroupType, ChildType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		itemClickListeners.add(listener);
		String message = "Added item click listener \"" + listener + "\"";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final void removeItemClickListener(
			final ExpandableListAdapterItemClickListener<GroupType, ChildType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		itemClickListeners.remove(listener);
		String message = "Removed item click listener \"" + listener + "\"";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final void addAdapterListener(
			final ExpandableListAdapterListener<GroupType, ChildType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		adapterListeners.add(listener);
		String message = "Added adapter listener \"" + listener + "\"";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final void removeAdapterListener(
			final ExpandableListAdapterListener<GroupType, ChildType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		adapterListeners.remove(listener);
		String message = "Removed adapter listener \"" + listener + "\"";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final void addExpansionListener(
			final ExpansionListener<GroupType, ChildType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		expansionListeners.add(listener);
	}

	@Override
	public final void removeExpansionListener(
			final ExpansionListener<GroupType, ChildType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		expansionListeners.remove(listener);
	}

	@Override
	public final boolean areDuplicateGroupsAllowed() {
		return groupAdapter.areDuplicatesAllowed();
	}

	@Override
	public final void allowDuplicateGroups(final boolean allowDuplicateGroups) {
		groupAdapter.allowDuplicates(allowDuplicateGroups);
	}

	@Override
	public final boolean addGroup(final GroupType group) {
		return groupAdapter.addItem(new Group<GroupType, ChildType>(group,
				createChildAdapter()));
	}

	@Override
	public final boolean addGroup(final int index, final GroupType group) {
		return groupAdapter.addItem(index, new Group<GroupType, ChildType>(
				group, createChildAdapter()));
	}

	@Override
	public final boolean addAllGroups(final Collection<GroupType> groups) {
		ensureNotNull(groups, "The collection may not be null");
		boolean result = true;

		for (GroupType group : groups) {
			result &= groupAdapter.addItem(new Group<GroupType, ChildType>(
					group, createChildAdapter()));
		}

		return result;
	}

	@Override
	public final boolean addAllGroups(final int index,
			final Collection<GroupType> groups) {
		ensureNotNull(groups, "The collection may not be null");
		boolean result = true;
		int currentIndex = index;

		for (GroupType group : groups) {
			result &= groupAdapter
					.addItem(currentIndex, new Group<GroupType, ChildType>(
							group, createChildAdapter()));
			currentIndex++;
		}

		return result;
	}

	@Override
	public final boolean addAllGroups(final GroupType... groups) {
		return addAllGroups(getNumberOfGroups(), groups);
	}

	@Override
	public final boolean addAllGroups(final int index,
			final GroupType... groups) {
		ensureNotNull(groups, "The array may not be null");
		return addAllGroups(index, Arrays.asList(groups));
	}

	@Override
	public final GroupType replaceGroup(final int index, final GroupType group) {
		return groupAdapter.replaceItem(index,
				new Group<GroupType, ChildType>(group, createChildAdapter()))
				.getData();
	}

	@Override
	public final GroupType removeGroup(final int index) {
		return groupAdapter.removeItem(index).getData();
	}

	@Override
	public final boolean removeGroup(final GroupType group) {
		int index = indexOfGroup(group);

		if (index != -1) {
			groupAdapter.removeItem(index);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public final boolean removeAllGroups(final Collection<GroupType> groups) {
		ensureNotNull(groups, "The collection may not be null");
		boolean result = true;

		for (GroupType group : groups) {
			result &= removeGroup(group);
		}

		return result;
	}

	@Override
	public final boolean removeAllGroups(final GroupType... groups) {
		ensureNotNull(groups, "The array may not be null");
		return removeAllGroups(Arrays.asList(groups));
	}

	@Override
	public final void retainAllGroups(final Collection<GroupType> groups) {
		ensureNotNull(groups, "The collection may not be null");

		for (int i = getNumberOfGroups() - 1; i >= 0; i--) {
			if (!containsGroup(getGroup(i))) {
				removeGroup(i);
			}
		}
	}

	@Override
	public final void retainAllGroups(final GroupType... groups) {
		ensureNotNull(groups, "The array may not be null");
		retainAllGroups(Arrays.asList(groups));
	}

	@Override
	public final void clearGroups() {
		groupAdapter.clearItems();
	}

	@Override
	public final Iterator<GroupType> groupIterator() {
		return new GroupIterator<GroupType, ChildType>(groupAdapter.iterator());
	}

	@Override
	public final ListIterator<GroupType> groupListIterator() {
		return new GroupListIterator<GroupType, ChildType>(
				groupAdapter.listIterator(), context, childInflater);
	}

	@Override
	public final ListIterator<GroupType> groupListIterator(final int index) {
		return new GroupListIterator<GroupType, ChildType>(
				groupAdapter.listIterator(index), context, childInflater);
	}

	@Override
	public final Collection<GroupType> subListGroups(final int start,
			final int end) {
		Collection<GroupType> subList = new ArrayList<GroupType>();

		for (int i = start; i < end; i++) {
			subList.add(getGroup(i));
		}

		return subList;
	}

	@Override
	public final Object[] groupsToArray() {
		return getAllGroups().toArray();
	}

	@Override
	public final <T> T[] groupsToArray(final T[] array) {
		return getAllGroups().toArray(array);
	}

	@Override
	public final GroupType getGroup(final int groupIndex) {
		return groupAdapter.getItem(groupIndex).getData();
	}

	@Override
	public final int indexOfGroup(final GroupType group) {
		ensureNotNull(group, "The group may not be null");
		return getAllGroups().indexOf(group);
	}

	@Override
	public final int lastIndexOfGroup(final GroupType group) {
		ensureNotNull(group, "The group may not be null");
		return getAllGroups().lastIndexOf(group);
	}

	@Override
	public final boolean containsGroup(final GroupType group) {
		ensureNotNull(group, "The group may not be null");
		return getAllGroups().contains(group);
	}

	@Override
	public final boolean containsAllGroups(final Collection<GroupType> groups) {
		ensureNotNull(groups, "The collection may not be null");
		return getAllGroups().containsAll(groups);
	}

	@Override
	public final boolean containsAllGroups(final GroupType... groups) {
		ensureNotNull(groups, "The array may not be null");
		return containsAllGroups(Arrays.asList(groups));
	}

	@Override
	public final int getNumberOfGroups() {
		return groupAdapter.getNumberOfItems();
	}

	@Override
	public final List<GroupType> getAllGroups() {
		List<GroupType> result = new ArrayList<GroupType>();

		for (Group<GroupType, ChildType> group : groupAdapter.getAllItems()) {
			result.add(group.getData());
		}

		return result;
	}

	@Override
	public final boolean isGroupEmpty(final int groupIndex) {
		return groupAdapter.getItem(groupIndex).getChildAdapter().isEmpty();
	}

	@Override
	public final boolean isGroupEmpty(final GroupType group) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return isGroupEmpty(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean areDuplicateChildrenAllowed() {
		return allowDuplicateChildren;
	}

	@Override
	public final void allowDuplicateChildren(
			final boolean allowDuplicateChildren) {
		this.allowDuplicateChildren = true;

		if (!allowDuplicateChildren) {
			for (Group<GroupType, ChildType> group : groupAdapter.getAllItems()) {
				group.getChildAdapter().allowDuplicates(false);
			}
		}
	}

	@Override
	public final boolean areDuplicateChildrenAllowed(final int groupIndex) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.areDuplicatesAllowed();
	}

	@Override
	public final void allowDuplicateChildren(final int groupIndex,
			final boolean allowDuplicateChildren) {
		groupAdapter.getItem(groupIndex).getChildAdapter()
				.allowDuplicates(allowDuplicateChildren);
	}

	@Override
	public final boolean areDuplicateChildrenAllowed(final GroupType group) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return areDuplicateChildrenAllowed(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final void allowDuplicateChildren(final GroupType group,
			final boolean allowDuplicateChildren) {
		int index = indexOfGroup(group);

		if (index != -1) {
			allowDuplicateChildren(index, allowDuplicateChildren);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean addChild(final int groupIndex, final ChildType child) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.addItem(child);
	}

	@Override
	public final boolean addChild(final GroupType group, final ChildType child) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return addChild(index, child);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean addChild(final int groupIndex, final int index,
			final ChildType child) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.addItem(index, child);
	}

	@Override
	public final boolean addChild(final GroupType group, final int index,
			final ChildType child) {
		int groupIndex = indexOfGroup(group);

		if (groupIndex != -1) {
			return addChild(groupIndex, index, child);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean addAllChildren(final int groupIndex,
			final Collection<ChildType> children) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.addAllItems(children);
	}

	@Override
	public final boolean addAllChildren(final GroupType group,
			final Collection<ChildType> children) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return addAllChildren(index, children);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean addAllChildren(final int groupIndex, final int index,
			final Collection<ChildType> children) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.addAllItems(index, children);
	}

	@Override
	public final boolean addAllChildren(final GroupType group, final int index,
			final Collection<ChildType> children) {
		int groupIndex = indexOfGroup(group);

		if (index != -1) {
			return addAllChildren(groupIndex, index, children);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean addAllChildren(final int groupIndex,
			final ChildType... children) {
		return addAllChildren(groupIndex, Arrays.asList(children));
	}

	@Override
	public final boolean addAllChildren(final GroupType group,
			final ChildType... children) {
		return addAllChildren(group, Arrays.asList(children));
	}

	@Override
	public final boolean addAllChildren(final int groupIndex, final int index,
			final ChildType... children) {
		return addAllChildren(groupIndex, index, Arrays.asList(children));
	}

	@Override
	public final boolean addAllChildren(final GroupType group, final int index,
			final ChildType... children) {
		return addAllChildren(group, index, Arrays.asList(children));
	}

	@Override
	public final ChildType replaceChild(final int groupIndex, final int index,
			final ChildType child) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.replaceItem(index, child);
	}

	@Override
	public final ChildType replaceChild(final GroupType group, final int index,
			final ChildType child) {
		int groupIndex = indexOfGroup(group);

		if (groupIndex != -1) {
			return replaceChild(groupIndex, index, child);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final ChildType removeChild(final int groupIndex, final int index) {
		return removeChild(false, groupIndex, index);
	}

	@Override
	public final ChildType removeChild(final boolean removeEmptyGroup,
			final int groupIndex, final int index) {
		Group<GroupType, ChildType> group = groupAdapter.getItem(groupIndex);
		ChildType removedChild = group.getChildAdapter().removeItem(index);

		if (removeEmptyGroup && group.getChildAdapter().isEmpty()) {
			groupAdapter.removeItem(groupIndex);
		}

		return removedChild;
	}

	@Override
	public final ChildType removeChild(final GroupType group, final int index) {
		return removeChild(false, group, index);
	}

	@Override
	public final ChildType removeChild(final boolean removeEmptyGroup,
			final GroupType group, final int index) {
		int groupIndex = indexOfGroup(group);

		if (index != -1) {
			return removeChild(removeEmptyGroup, groupIndex, index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean removeChild(final int groupIndex, final ChildType child) {
		return removeChild(false, groupIndex, child);
	}

	@Override
	public final boolean removeChild(final boolean removeEmptyGroup,
			final int groupIndex, final ChildType child) {
		Group<GroupType, ChildType> group = groupAdapter.getItem(groupIndex);
		boolean removed = group.getChildAdapter().removeItem(child);

		if (removeEmptyGroup && group.getChildAdapter().isEmpty()) {
			groupAdapter.removeItem(groupIndex);
		}

		return removed;
	}

	@Override
	public final boolean removeChild(final GroupType group,
			final ChildType child) {
		return removeChild(false, group, child);
	}

	@Override
	public final boolean removeChild(final boolean removeEmptyGroup,
			final GroupType group, final ChildType child) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return removeChild(removeEmptyGroup, index, child);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean removeAllChildren(final Collection<ChildType> children) {
		return removeAllChildren(false, children);
	}

	@Override
	public final boolean removeAllChildren(final boolean removeEmptyGroups,
			final Collection<ChildType> children) {
		boolean result = true;

		for (int i = groupAdapter.getNumberOfItems() - 1; i >= 0; i--) {
			Group<GroupType, ChildType> group = groupAdapter.getItem(i);
			result &= group.getChildAdapter().removeAllItems(children);

			if (removeEmptyGroups && group.getChildAdapter().isEmpty()) {
				groupAdapter.removeItem(i);
			}
		}

		return result;
	}

	@Override
	public final boolean removeAllChildren(final int groupIndex,
			final Collection<ChildType> children) {
		return removeAllChildren(false, groupIndex, children);
	}

	@Override
	public final boolean removeAllChildren(final boolean removeEmptyGroup,
			final int groupIndex, final Collection<ChildType> children) {
		Group<GroupType, ChildType> group = groupAdapter.getItem(groupIndex);
		boolean removedAll = group.getChildAdapter().removeAllItems(children);

		if (removeEmptyGroup && group.getChildAdapter().isEmpty()) {
			groupAdapter.removeItem(groupIndex);
		}

		return removedAll;
	}

	@Override
	public final boolean removeAllChildren(final GroupType group,
			final Collection<ChildType> children) {
		return removeAllChildren(false, group, children);
	}

	@Override
	public final boolean removeAllChildren(final boolean removeEmptyGroup,
			final GroupType group, final Collection<ChildType> children) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return removeAllChildren(removeEmptyGroup, index, children);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean removeAllChildren(final ChildType... children) {
		return removeAllChildren(false, children);
	}

	@Override
	public final boolean removeAllChildren(final boolean removeEmptyGroups,
			final ChildType... children) {
		return removeAllChildren(removeEmptyGroups, Arrays.asList(children));
	}

	@Override
	public final boolean removeAllChildren(final int groupIndex,
			final ChildType... children) {
		return removeAllChildren(false, groupIndex, children);
	}

	@Override
	public final boolean removeAllChildren(final boolean removeEmptyGroup,
			final int groupIndex, final ChildType... children) {
		return removeAllChildren(removeEmptyGroup, groupIndex,
				Arrays.asList(children));
	}

	@Override
	public final boolean removeAllChildren(final GroupType group,
			final ChildType... children) {
		return removeAllChildren(false, group, children);
	}

	@Override
	public final boolean removeAllChildren(final boolean removeEmptyGroup,
			final GroupType group, final ChildType... children) {
		return removeAllChildren(removeEmptyGroup, group,
				Arrays.asList(children));
	}

	@Override
	public final void retainAllChildren(final Collection<ChildType> children) {
		retainAllChildren(false, children);
	}

	@Override
	public final void retainAllChildren(final boolean removeEmptyGroups,
			final Collection<ChildType> children) {
		for (int i = groupAdapter.getNumberOfItems() - 1; i >= 0; i--) {
			Group<GroupType, ChildType> group = groupAdapter.getItem(i);
			group.getChildAdapter().retainAllItems(children);

			if (removeEmptyGroups && group.getChildAdapter().isEmpty()) {
				groupAdapter.removeItem(i);
			}
		}
	}

	@Override
	public final void retainAllChildren(final int groupIndex,
			final Collection<ChildType> children) {
		retainAllChildren(false, groupIndex, children);
	}

	@Override
	public final void retainAllChildren(final boolean removeEmptyGroup,
			final int groupIndex, final Collection<ChildType> children) {
		Group<GroupType, ChildType> group = groupAdapter.getItem(groupIndex);
		group.getChildAdapter().retainAllItems(children);

		if (removeEmptyGroup && group.getChildAdapter().isEmpty()) {
			groupAdapter.removeItem(groupIndex);
		}
	}

	@Override
	public final void retainAllChildren(final GroupType group,
			final Collection<ChildType> children) {
		retainAllChildren(false, group, children);
	}

	@Override
	public final void retainAllChildren(final boolean removeEmptyGroup,
			final GroupType group, final Collection<ChildType> children) {
		int index = indexOfGroup(group);

		if (index != -1) {
			retainAllChildren(removeEmptyGroup, index, children);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final void retainAllChildren(final ChildType... children) {
		retainAllChildren(false, children);
	}

	@Override
	public final void retainAllChildren(final boolean removeEmptyGroups,
			final ChildType... children) {
		retainAllChildren(removeEmptyGroups, Arrays.asList(children));
	}

	@Override
	public final void retainAllChildren(final int groupIndex,
			final ChildType... children) {
		retainAllChildren(false, groupIndex, children);
	}

	@Override
	public final void retainAllChildren(final boolean removeEmptyGroup,
			final int groupIndex, final ChildType... children) {
		retainAllChildren(removeEmptyGroup, groupIndex, Arrays.asList(children));
	}

	@Override
	public final void retainAllChildren(final GroupType group,
			final ChildType... children) {
		retainAllChildren(false, group, children);
	}

	@Override
	public final void retainAllChildren(final boolean removeEmptyGroup,
			final GroupType group, final ChildType... children) {
		retainAllChildren(removeEmptyGroup, group, Arrays.asList(children));
	}

	@Override
	public final void clearChildren() {
		for (Group<GroupType, ChildType> group : groupAdapter.getAllItems()) {
			group.getChildAdapter().clearItems();
		}
	}

	@Override
	public final void clearChildren(final int groupIndex) {
		groupAdapter.getItem(groupIndex).getChildAdapter().clearItems();
	}

	@Override
	public final void clearChildren(final GroupType group) {
		int index = indexOfGroup(group);

		if (index != -1) {
			clearChildren(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final Iterator<ChildType> childIterator() {
		return getAllChildren().iterator();
	}

	@Override
	public final Iterator<ChildType> childIterator(final int groupIndex) {
		return groupAdapter.getItem(groupIndex).getChildAdapter().iterator();
	}

	@Override
	public final Iterator<ChildType> childIterator(final GroupType group) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return childIterator(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final ListIterator<ChildType> childListIterator(final int groupIndex) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.listIterator();
	}

	@Override
	public final ListIterator<ChildType> childListIterator(final GroupType group) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return childListIterator(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final ListIterator<ChildType> childListIterator(
			final int groupIndex, final int index) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.listIterator(index);
	}

	@Override
	public final ListIterator<ChildType> childListIterator(
			final GroupType group, final int index) {
		int groupIndex = indexOfGroup(group);

		if (index != -1) {
			return childListIterator(groupIndex, index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final Collection<ChildType> subListChildren(final int groupIndex,
			final int start, final int end) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.subList(start, end);
	}

	@Override
	public final Collection<ChildType> subListChildren(final GroupType group,
			final int start, final int end) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return subListChildren(index, start, end);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final Object[] childrenToArray() {
		return getAllChildren().toArray();
	}

	@Override
	public final Object[] childrenToArray(final int groupIndex) {
		return groupAdapter.getItem(groupIndex).getChildAdapter().toArray();
	}

	@Override
	public final Object[] childrenToArray(final GroupType group) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return childrenToArray(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final <T> T[] childrenToArray(final T[] array) {
		return getAllChildren().toArray(array);
	}

	@Override
	public final <T> T[] childrenToArray(final int groupIndex, final T[] array) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.toArray(array);
	}

	@Override
	public final <T> T[] childrenToArray(final GroupType group, final T[] array) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return childrenToArray(index, array);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final ChildType getChild(final GroupType group, final int index) {
		int groupIndex = indexOfGroup(group);

		if (groupIndex != -1) {
			return getChild(groupIndex, index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final int indexOfChild(final ChildType child) {
		for (int i = 0; i < groupAdapter.getNumberOfItems(); i++) {
			if (groupAdapter.getItem(i).getChildAdapter().containsItem(child)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public final int indexOfChild(final int groupIndex, final ChildType child) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.indexOf(child);
	}

	@Override
	public final int indexOfChild(final GroupType group, final ChildType child) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return indexOfChild(index, child);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final int lastIndexOfChild(final ChildType child) {
		for (int i = groupAdapter.getNumberOfItems() - 1; i >= 0; i--) {
			if (groupAdapter.getItem(i).getChildAdapter().containsItem(child)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public final int lastIndexOfChild(final int groupIndex,
			final ChildType child) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.lastIndexOf(child);
	}

	@Override
	public final int lastIndexOfChild(final GroupType group,
			final ChildType child) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return lastIndexOfChild(index, child);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean containsChild(final ChildType child) {
		for (Group<GroupType, ChildType> group : groupAdapter.getAllItems()) {
			if (group.getChildAdapter().containsItem(child)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public final boolean containsChild(final int groupIndex,
			final ChildType child) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.containsItem(child);
	}

	@Override
	public final boolean containsChild(final GroupType group,
			final ChildType child) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return containsChild(index, child);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean containsAllChildren(
			final Collection<ChildType> children) {
		return getAllChildren().containsAll(children);
	}

	@Override
	public final boolean containsAllChildren(final int groupIndex,
			final Collection<ChildType> children) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.containsAllItems(children);
	}

	@Override
	public final boolean containsAllChildren(final GroupType group,
			final Collection<ChildType> children) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return containsAllChildren(index, children);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean containsAllChildren(final ChildType... children) {
		return containsAllChildren(Arrays.asList(children));
	}

	@Override
	public final int getNumberOfChildren() {
		return getAllChildren().size();
	}

	@Override
	public final int getNumberOfChildren(final int groupIndex) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.getNumberOfItems();
	}

	@Override
	public final int getNumberOfChildren(final GroupType group) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return getNumberOfChildren(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final List<ChildType> getAllChildren() {
		List<ChildType> result = new ArrayList<ChildType>();

		for (Group<GroupType, ChildType> group : groupAdapter.getAllItems()) {
			result.addAll(group.getChildAdapter().getAllItems());
		}

		return result;
	}

	@Override
	public final Collection<ChildType> getAllChildren(final int groupIndex) {
		return groupAdapter.getItem(groupIndex).getChildAdapter().getAllItems();
	}

	@Override
	public final Collection<ChildType> getAllChildren(final GroupType group) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return getAllChildren(index);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean containsAllChildren(final int groupIndex,
			final ChildType... children) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.containsAllItems(children);
	}

	@Override
	public final boolean containsAllChildren(final GroupType group,
			final ChildType... children) {
		int index = indexOfGroup(group);

		if (index != -1) {
			return containsAllChildren(index, children);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean isGroupExpanded(final int index) {
		if (getAdapterView() != null) {
			return getAdapterView().isGroupExpanded(index);
		}

		return false;
	}

	@Override
	public final boolean isGroupExpanded(final GroupType group) {
		return isGroupExpanded(indexOfGroup(group));
	}

	@Override
	public final boolean isGroupCollapsed(final int index) {
		return !isGroupExpanded(index);
	}

	@Override
	public final boolean isGroupCollapsed(final GroupType group) {
		return !isGroupExpanded(group);
	}

	@Override
	public final GroupType getFirstExpandedGroup() {
		int index = getFirstExpandedGroupIndex();

		if (index != -1) {
			return getGroup(index);
		}

		return null;
	}

	@Override
	public final int getFirstExpandedGroupIndex() {
		if (getAdapterView() != null) {
			for (int i = 0; i < getNumberOfGroups(); i++) {
				if (getAdapterView().isGroupExpanded(i)) {
					return i;
				}
			}
		}

		return -1;
	}

	@Override
	public final GroupType getLastExpandedGroup() {
		int index = getLastExpandedGroupIndex();

		if (index != -1) {
			return getGroup(index);
		}

		return null;
	}

	@Override
	public final int getLastExpandedGroupIndex() {
		if (getAdapterView() != null) {
			for (int i = getNumberOfGroups() - 1; i >= 0; i--) {
				if (getAdapterView().isGroupExpanded(i)) {
					return i;
				}
			}
		}

		return -1;
	}

	@Override
	public final GroupType getFirstCollapsedGroup() {
		int index = getFirstCollapsedGroupIndex();

		if (index != -1) {
			return getGroup(index);
		}

		return null;
	}

	@Override
	public final int getFirstCollapsedGroupIndex() {
		if (getAdapterView() != null) {
			for (int i = 0; i < getNumberOfGroups(); i++) {
				if (!getAdapterView().isGroupExpanded(i)) {
					return i;
				}
			}
		}

		return -1;
	}

	@Override
	public final GroupType getLastCollapsedGroup() {
		int index = getLastCollapsedGroupIndex();

		if (index != -1) {
			return getGroup(index);
		}

		return null;
	}

	@Override
	public final int getLastCollapsedGroupIndex() {
		if (getAdapterView() != null) {
			for (int i = getNumberOfGroups() - 1; i >= 0; i--) {
				if (!getAdapterView().isGroupExpanded(i)) {
					return i;
				}
			}
		}

		return -1;
	}

	@Override
	public final Collection<GroupType> getExpandedGroups() {
		Collection<GroupType> expandedGroups = new ArrayList<GroupType>();

		if (getAdapterView() != null) {
			for (int i = 0; i < getNumberOfGroups(); i++) {
				if (getAdapterView().isGroupExpanded(i)) {
					expandedGroups.add(getGroup(i));
				}
			}
		}

		return expandedGroups;
	}

	@Override
	public final Collection<Integer> getExpandedGroupIndices() {
		Collection<Integer> expandedGroupIndices = new ArrayList<Integer>();

		if (getAdapterView() != null) {
			for (int i = 0; i < getNumberOfGroups(); i++) {
				if (getAdapterView().isGroupExpanded(i)) {
					expandedGroupIndices.add(i);
				}
			}
		}

		return expandedGroupIndices;
	}

	@Override
	public final Collection<GroupType> getCollapsedGroups() {
		Collection<GroupType> collapedGroups = new ArrayList<GroupType>();

		if (getAdapterView() != null) {
			for (int i = 0; i < getNumberOfGroups(); i++) {
				if (!getAdapterView().isGroupExpanded(i)) {
					collapedGroups.add(getGroup(i));
				}
			}
		}

		return collapedGroups;
	}

	@Override
	public final Collection<Integer> getCollapsedGroupIndices() {
		Collection<Integer> collapsedGroupIndices = new ArrayList<Integer>();

		if (getAdapterView() != null) {
			for (int i = 0; i < getNumberOfGroups(); i++) {
				if (!getAdapterView().isGroupExpanded(i)) {
					collapsedGroupIndices.add(i);
				}
			}
		}

		return collapsedGroupIndices;
	}

	@Override
	public final int getNumberOfExpandedGroups() {
		return getExpandedGroups().size();
	}

	@Override
	public final int getNumberOfCollapsedGroups() {
		return getCollapsedGroups().size();
	}

	@Override
	public final void expandGroup(final GroupType group) {
		if (getAdapterView() != null) {
			int index = indexOfGroup(group);

			if (index == -1) {
				throw new NoSuchElementException();
			}

			getAdapterView().expandGroup(index);
		} else {
			String message = "The group \"" + group
					+ "\" has not been expanded, because the "
					+ "adapter is not attached to a view";
			getLogger().logWarn(getClass(), message);
		}
	}

	@Override
	public final void expandGroup(final int index) {
		if (getAdapterView() != null) {
			getAdapterView().expandGroup(index);
		} else {
			String message = "The group at index " + index
					+ " has not been expanded, because the "
					+ "adapter is not attached to a view";
			getLogger().logWarn(getClass(), message);
		}
	}

	@Override
	public final void collapseGroup(final GroupType group) {
		if (getAdapterView() != null) {
			int index = indexOfGroup(group);

			if (index == -1) {
				throw new NoSuchElementException();
			}

			getAdapterView().collapseGroup(index);
		} else {
			String message = "The group \"" + group
					+ "\" has not been collapsed, because the "
					+ "adapter is not attached to a view";
			getLogger().logWarn(getClass(), message);
		}
	}

	@Override
	public final void collapseGroup(final int index) {
		if (getAdapterView() != null) {
			getAdapterView().collapseGroup(index);
		} else {
			String message = "The group at index " + index
					+ "has not been collapsed, because the "
					+ "adapter is not attached to a view";
			getLogger().logWarn(getClass(), message);
		}
	}

	@Override
	public final boolean triggerGroupExpansion(final GroupType group) {
		if (getAdapterView() != null) {
			int index = indexOfGroup(group);

			if (index == -1) {
				throw new NoSuchElementException();
			}

			if (getAdapterView().isGroupExpanded(index)) {
				getAdapterView().collapseGroup(index);
				return false;
			} else {
				getAdapterView().expandGroup(index);
				return true;
			}
		} else {
			String message = "The expansion of the group \"" + group
					+ "\" has not been triggered, because the "
					+ "adapter is not attached to a view";
			getLogger().logWarn(getClass(), message);
			return false;
		}
	}

	@Override
	public final boolean triggerGroupExpansion(final int index) {
		if (getAdapterView() != null) {
			if (getAdapterView().isGroupExpanded(index)) {
				getAdapterView().collapseGroup(index);
				return false;
			} else {
				getAdapterView().expandGroup(index);
				return true;
			}
		} else {
			String message = "The expansion of the group at index " + index
					+ " has not been triggered, because the "
					+ "adapter is not attached to a view";
			getLogger().logWarn(getClass(), message);
			return false;
		}
	}

	@Override
	public final void expandAllGroups() {
		if (getAdapterView() != null) {
			for (int i = 0; i < getNumberOfGroups(); i++) {
				getAdapterView().expandGroup(i);
			}
		} else {
			String message = "All groups have not been expanded, because the "
					+ "adapter is not attached to a view";
			getLogger().logWarn(getClass(), message);
		}
	}

	@Override
	public final void collapseAllGroups() {
		if (getAdapterView() != null) {
			for (int i = 0; i < getNumberOfGroups(); i++) {
				getAdapterView().expandGroup(i);
			}
		} else {
			String message = "All groups have not been collapsed, because the "
					+ "adapter is not attached to a view";
			getLogger().logWarn(getClass(), message);
		}
	}

	@Override
	public final void triggerAllGroupExpansions() {
		if (getAdapterView() != null) {
			for (int i = 0; i < getNumberOfGroups(); i++) {
				if (getAdapterView().isGroupExpanded(i)) {
					getAdapterView().collapseGroup(i);
				} else {
					getAdapterView().expandGroup(i);
				}
			}
		} else {
			String message = "The expansion states of all groups have not been "
					+ "triggered, because the adapter is not attached to a view";
			getLogger().logWarn(getClass(), message);
		}
	}

	@Override
	public final boolean isGroupExpandedOnClick() {
		return expandGroupOnClick;
	}

	@Override
	public final void expandGroupOnClick(final boolean expandGroupOnClick) {
		this.expandGroupOnClick = expandGroupOnClick;
		String message = "Groups are now " + (expandGroupOnClick ? "" : "not ")
				+ "expanded on click";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final void attach(final ExpandableListView expandableListView) {
		ensureNotNull(expandableListView,
				"The expandable list view may not be null");
		this.adapterView = expandableListView;
		expandableListView.setAdapter(this);
		String message = "Attached adapter to expandable list view \""
				+ expandableListView + "\"";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final void detach() {
		if (adapterView != null) {
			if (adapterView.getAdapter() == this) {
				adapterView
						.setAdapter((android.widget.ExpandableListAdapter) null);
				String message = "Detached adapter from view \"" + adapterView
						+ "\"";
				getLogger().logDebug(getClass(), message);
			} else {
				String message = "Adapter has not been detached, because the "
						+ "adapter of the corresponding view has been changed "
						+ "in the meantime";
				getLogger().logVerbose(getClass(), message);
			}

			adapterView = null;
		} else {
			String message = "Adapter has not been detached, because it has not "
					+ "been attached to a view yet";
			getLogger().logVerbose(getClass(), message);
		}
	}

	@Override
	public final ChildType getChild(final int groupIndex, final int childIndex) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.getItem(childIndex);
	}

	@Override
	public final long getChildId(final int groupIndex, final int childIndex) {
		int id = groupAdapter.getNumberOfItems() + childIndex;

		for (int i = 0; i < groupIndex; i++) {
			id += groupAdapter.getItem(i).getChildAdapter().getNumberOfItems();
		}

		return id;
	}

	@Override
	public final int getChildrenCount(final int groupIndex) {
		return groupAdapter.getItem(groupIndex).getChildAdapter()
				.getNumberOfItems();
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
	public final boolean hasStableIds() {
		return true;
	}

	@Override
	public final View getGroupView(final int groupIndex,
			final boolean isExpanded, final View convertView,
			final ViewGroup parent) {
		View view = convertView;

		if (view == null) {
			view = getGroupInflater().inflate(getContext(), parent, false);
			view.setOnClickListener(createGroupItemOnClickListener(groupIndex));
		}

		applyDecoratorOnGroup(getContext(), view, groupIndex);
		return view;
	}

	@Override
	public final View getChildView(final int groupIndex, final int childIndex,
			final boolean isLastChild, final View convertView,
			final ViewGroup parent) {
		View view = convertView;

		if (view == null) {
			view = getChildInflater().inflate(getContext(), parent, false);
			view.setOnClickListener(createChildItemOnClickListener(groupIndex,
					childIndex));
		}

		applyDecoratorOnChild(getContext(), view, groupIndex, childIndex);
		return view;
	}

	@Override
	public final void onSaveInstanceState(final Bundle outState) {
		if (isGroupDataSerializable()) {
			outState.putSerializable(CHILD_ADAPTER_BUNDLE_KEY, groupAdapter);
		} else {
			String message = "The adapter's items can not be stored, because the "
					+ "underlying data of the group items does not implement the "
					+ "interface \"" + Serializable.class.getName() + "\"";
			getLogger().logWarn(getClass(), message);
		}

		if (isChildDataSerializable()) {
			for (int i = 0; i < groupAdapter.getNumberOfItems(); i++) {
				Group<GroupType, ChildType> group = groupAdapter.getItem(i);
				outState.putSerializable(
						String.format(CHILD_ADAPTER_BUNDLE_KEY, i),
						group.getChildAdapter());
			}
		} else {
			String message = "The adapter's items can not be stored, because the "
					+ "underlying data of the child items does not implement the "
					+ "interface \"" + Serializable.class.getName() + "\"";
			getLogger().logWarn(getClass(), message);
		}

		outState.putBoolean(ALLOW_DUPLICATE_CHILDREN_BUNDLE_KEY,
				areDuplicateChildrenAllowed());
		outState.putBoolean(EXPAND_GROUP_ON_CLICK_BUNDLE_KEY,
				isGroupExpandedOnClick());
		outState.putInt(LOG_LEVEL_BUNDLE_KEY, getLogLevel().getRank());
		getLogger().logDebug(getClass(), "Saved instance state");
	}

	@SuppressWarnings("unchecked")
	@Override
	public final void onRestoreInstanceState(final Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(GROUP_ADAPTER_BUNDLE_KEY)) {
				groupAdapter = (MultipleChoiceListAdapter<Group<GroupType, ChildType>>) savedInstanceState
						.getSerializable(GROUP_ADAPTER_BUNDLE_KEY);
			}

			for (int i = 0; i <= Integer.MAX_VALUE; i++) {
				Serializable childAdapter = savedInstanceState
						.getSerializable(String.format(
								CHILD_ADAPTER_BUNDLE_KEY, i));

				if (childAdapter != null) {
					groupAdapter
							.getItem(i)
							.setChildAdapter(
									(MultipleChoiceListAdapter<ChildType>) childAdapter);
				} else {
					break;
				}
			}

			allowDuplicateChildren(savedInstanceState
					.getBoolean(ALLOW_DUPLICATE_CHILDREN_BUNDLE_KEY));
			expandGroupOnClick(savedInstanceState
					.getBoolean(EXPAND_GROUP_ON_CLICK_BUNDLE_KEY));
			setLogLevel(LogLevel.fromRank(savedInstanceState
					.getInt(LOG_LEVEL_BUNDLE_KEY)));
			notifyDataSetChanged();
			getLogger().logDebug(getClass(), "Restored instance state");
		}
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (allowDuplicateChildren ? 1231 : 1237);
		result = prime * result + (expandGroupOnClick ? 1231 : 1237);
		result = prime * result + groupAdapter.hashCode();
		result = prime * result + getLogLevel().getRank();
		return result;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractExpandableListAdapter<?, ?, ?> other = (AbstractExpandableListAdapter<?, ?, ?>) obj;
		if (allowDuplicateChildren != other.allowDuplicateChildren)
			return false;
		if (expandGroupOnClick != other.expandGroupOnClick)
			return false;
		if (!groupAdapter.equals(other.groupAdapter))
			return false;
		if (!getLogLevel().equals(other.getLogLevel()))
			return false;
		return true;
	}

	@Override
	public abstract AbstractExpandableListAdapter<GroupType, ChildType, DecoratorType> clone()
			throws CloneNotSupportedException;

}