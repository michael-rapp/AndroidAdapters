package de.mrapp.android.adapter.expandablelist.selectable;

import java.util.LinkedHashSet;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import de.mrapp.android.adapter.ExpandableListChoiceMode;
import de.mrapp.android.adapter.MultipleChoiceListAdapter;
import de.mrapp.android.adapter.SelectableExpandableListDecorator;
import de.mrapp.android.adapter.SingleChoiceExpandableListAdapter;
import de.mrapp.android.adapter.datastructure.group.Group;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterItemClickListener;
import de.mrapp.android.adapter.expandablelist.ExpandableListAdapterListener;
import de.mrapp.android.adapter.expandablelist.ExpansionListener;
import de.mrapp.android.adapter.expandablelist.NullObjectDecorator;
import de.mrapp.android.adapter.expandablelist.enablestate.ExpandableListEnableStateListener;
import de.mrapp.android.adapter.expandablelist.filterable.ExpandableListFilterListener;
import de.mrapp.android.adapter.expandablelist.itemstate.ExpandableListItemStateListener;
import de.mrapp.android.adapter.expandablelist.sortable.ExpandableListSortingListener;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.selectable.MultipleChoiceListAdapterImplementation;
import de.mrapp.android.adapter.logging.LogLevel;
import de.mrapp.android.adapter.util.VisibleForTesting;

/**
 * An adapter, whose underlying data is managed as a list of arbitrary group and
 * child items, of which only one item can be selected at once. Such an
 * adapter's purpose is to provide the underlying data for visualization using a
 * {@link ExpandableListView} widget.
 * 
 * @param <GroupType>
 *            The type of the underlying data of the adapter's group items
 * @param <ChildType>
 *            The type of the underlying data of the adapter's child items
 * 
 * @author Michael Rapp
 * 
 * @since 0.1.0
 */
public class SingleChoiceExpandableListAdapterImplementation<GroupType, ChildType>
		extends AbstractSelectableExpandableListAdapter<GroupType, ChildType>
		implements SingleChoiceExpandableListAdapter<GroupType, ChildType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The key, which is used to store, whether the adapter's selection is
	 * adapted automatically, or not, within a bundle.
	 */
	@VisibleForTesting
	protected static final String ADAPT_SELECTION_AUTOMATICALLY_BUNDLE_KEY = SingleChoiceExpandableListAdapterImplementation.class
			.getSimpleName() + "::AdaptSelectionAutomatically";

	/**
	 * True, if the adapter's selection is adapted automatically, false
	 * otherwise.
	 */
	private boolean adaptSelectionAutomatically;

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary group and child items, of which only one item can be selected
	 * at once.
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
	 *            True, if duplicate child items, regardless from the group they
	 *            belong to, should be allowed, false otherwise
	 * @param notifyOnChange
	 *            True, if the method <code>notifyDataSetChanged():void</code>
	 *            should be automatically called when the adapter's underlying
	 *            data has been changed, false otherwise
	 * @param expandGroupOnClick
	 *            True, if a group should be expanded, when it is clicked by the
	 *            user, false otherwise
	 * @param itemClickListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when an item of the adapter has been clicked by the user, as
	 *            an instance of the type {@link Set}, or an empty set, if no
	 *            listeners should be notified
	 * @param adapterListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been modified, as an
	 *            instance of the type {@link Set}, or an empty set, if no
	 *            listeners should be notified
	 * @param expansionListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when a group item has been expanded or collapsed, as an
	 *            instance of the type {@link Set}, or an empty set, if no
	 *            listeners should be notified
	 * @param enableStateListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when an item has been disabled or enabled, as an instance of
	 *            the type {@link Set}, or an empty set, if no listeners should
	 *            be notified
	 * @param numberOfGroupStates
	 *            The number of states, the adapter's group items may have, as
	 *            an {@link Integer} value. The value must be at least 1
	 * @param numberOfChildStates
	 *            The number of states, the adapter's child items may have, as
	 *            an {@link Integer} value. The value must be at least 1
	 * @param triggerGroupStateOnClick
	 *            True, if the state of a group item should be triggered, when
	 *            it is clicked by the user, false otherwise
	 * @param triggerChildStateOnClick
	 *            True, if the state of a child item should be triggered, when
	 *            it is clicked by the user, false otherwise
	 * @param itemStateListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the state of an item has been changed, or an empty set,
	 *            if no listeners should be notified
	 * @param sortingListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been sorted, or an
	 *            empty set, if no listeners should be notified
	 * @param filterListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been filtered, or an
	 *            empty set, if no listeners should be notified
	 * @param selectGroupOnClick
	 *            True, if a group item should be selected, when it is clicked
	 *            by the user, false otherwise
	 * @param selectChildOnClick
	 *            True, if a group item should be selected, when it is clicked
	 *            by the user, false otherwise
	 * @param selectionListeners
	 *            A set, which contains the listener, which should be notified,
	 *            when the selection of an item has changed, or an empty set, if
	 *            no listeners should be notified
	 * @param choiceMode
	 *            The choice mode of the adapter as a value of the enum
	 *            {@link ExpandableListChoiceMode}
	 */
	protected SingleChoiceExpandableListAdapterImplementation(final Context context, final Inflater groupInflater,
			final Inflater childInflater, final SelectableExpandableListDecorator<GroupType, ChildType> decorator,
			final LogLevel logLevel, final MultipleChoiceListAdapter<Group<GroupType, ChildType>> groupAdapter,
			final boolean allowDuplicateChildren, final boolean notifyOnChange, final boolean expandGroupOnClick,
			final Set<ExpandableListAdapterItemClickListener<GroupType, ChildType>> itemClickListeners,
			final Set<ExpandableListAdapterListener<GroupType, ChildType>> adapterListeners,
			final Set<ExpansionListener<GroupType, ChildType>> expansionListeners,
			final Set<ExpandableListEnableStateListener<GroupType, ChildType>> enableStateListeners,
			final int numberOfGroupStates, final int numberOfChildStates, final boolean triggerGroupStateOnClick,
			final boolean triggerChildStateOnClick,
			final Set<ExpandableListItemStateListener<GroupType, ChildType>> itemStateListeners,
			final Set<ExpandableListSortingListener<GroupType, ChildType>> sortingListeners,
			final Set<ExpandableListFilterListener<GroupType, ChildType>> filterListeners,
			final boolean selectGroupOnClick, final boolean selectChildOnClick,
			final Set<ExpandableListSelectionListener<GroupType, ChildType>> selectionListeners,
			final ExpandableListChoiceMode choiceMode) {
		super(context, groupInflater, childInflater, decorator, logLevel, groupAdapter, allowDuplicateChildren,
				notifyOnChange, expandGroupOnClick, itemClickListeners, adapterListeners, expansionListeners,
				enableStateListeners, numberOfGroupStates, numberOfChildStates, triggerGroupStateOnClick,
				triggerChildStateOnClick, itemStateListeners, sortingListeners, filterListeners, selectGroupOnClick,
				selectChildOnClick, selectionListeners, choiceMode);
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary group and child items, of which only one item can be selected
	 * at once.
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
	 * @param choiceMode
	 *            The choice mode of the adapter as a value of the enum
	 *            {@link ExpandableListChoiceMode}
	 */
	public SingleChoiceExpandableListAdapterImplementation(final Context context, final Inflater groupInflater,
			final Inflater childInflater, final SelectableExpandableListDecorator<GroupType, ChildType> decorator,
			final ExpandableListChoiceMode choiceMode) {
		this(context, groupInflater, childInflater, decorator, LogLevel.ALL,
				new MultipleChoiceListAdapterImplementation<Group<GroupType, ChildType>>(context, groupInflater,
						new NullObjectDecorator<Group<GroupType, ChildType>>()),
				false, true, true, new LinkedHashSet<ExpandableListAdapterItemClickListener<GroupType, ChildType>>(),
				new LinkedHashSet<ExpandableListAdapterListener<GroupType, ChildType>>(),
				new LinkedHashSet<ExpansionListener<GroupType, ChildType>>(),
				new LinkedHashSet<ExpandableListEnableStateListener<GroupType, ChildType>>(), 1, 1, false, false,
				new LinkedHashSet<ExpandableListItemStateListener<GroupType, ChildType>>(),
				new LinkedHashSet<ExpandableListSortingListener<GroupType, ChildType>>(),
				new LinkedHashSet<ExpandableListFilterListener<GroupType, ChildType>>(), true, true,
				new LinkedHashSet<ExpandableListSelectionListener<GroupType, ChildType>>(), choiceMode);
	}

	@Override
	protected final void applyDecoratorOnGroup(final Context context, final View view, final int index) {
		GroupType group = getGroup(index);
		boolean expanded = isGroupExpanded(index);
		boolean enabled = isGroupEnabled(index);
		int state = getGroupState(index);
		boolean filtered = areGroupsFiltered();
		boolean selected = isGroupSelected(index);
		getDecorator().applyDecoratorOnGroup(context, this, view, group, index, expanded, enabled, state, filtered,
				selected);
	}

	@Override
	protected final void applyDecoratorOnChild(final Context context, final View view, final int groupIndex,
			final int childIndex) {
		GroupType group = getGroup(groupIndex);
		ChildType child = getChild(groupIndex, childIndex);
		boolean enabled = isChildEnabled(groupIndex, childIndex);
		int state = getChildState(groupIndex, childIndex);
		boolean filtered = areChildrenFiltered();
		boolean selected = isChildSelected(groupIndex, childIndex);
		getDecorator().applyDecoratorOnChild(context, this, view, child, childIndex, group, groupIndex, enabled, state,
				filtered, selected);
	}

	@Override
	public final int getSelectedGroupIndex() {
		int groupIndex = getGroupAdapter().getFirstSelectedIndex();

		if (groupIndex == -1) {
			for (int i = 0; i < getGroupCount(); i++) {
				int childIndex = getGroupAdapter().getItem(i).getChildAdapter().getFirstSelectedIndex();

				if (childIndex != -1) {
					return i;
				}
			}
		}

		return -1;
	}

	@Override
	public final int getSelectedChildIndex() {
		for (int i = 0; i < getGroupCount(); i++) {
			int childIndex = getGroupAdapter().getItem(i).getChildAdapter().getFirstSelectedIndex();

			if (childIndex != -1) {
				return childIndex;
			}
		}

		return -1;
	}

	@Override
	public final GroupType getSelectedGroup() {
		int groupIndex = getGroupAdapter().getFirstSelectedIndex();

		if (groupIndex != -1) {
			return getGroup(groupIndex);
		}

		return null;
	}

	@Override
	public final ChildType getSelectedChild() {
		for (int i = 0; i < getGroupCount(); i++) {
			ChildType child = getGroupAdapter().getItem(i).getChildAdapter().getFirstSelectedItem();

			if (child != null) {
				return child;
			}
		}

		return null;
	}

	@Override
	public final boolean selectGroup(final int groupIndex) {
		if (getChoiceMode() == ExpandableListChoiceMode.CHILDREN_ONLY) {
			throw new IllegalStateException(
					"Groups are not allowed to be selected when using the choice mode " + getChoiceMode());
		}

		return getGroupAdapter().setSelected(groupIndex, true);
	}

	@Override
	public final boolean selectGroup(final GroupType group) {
		return selectGroup(indexOfGroupOrThrowException(group));
	}

	@Override
	public final boolean selectChild(final GroupType group, final int childIndex) {
		return selectChild(indexOfGroupOrThrowException(group), childIndex);
	}

	@Override
	public final boolean selectChild(final GroupType group, final ChildType child) {
		return selectChild(indexOfGroupOrThrowException(group), child);
	}

	@Override
	public final boolean selectChild(final int groupIndex, final int childIndex) {
		if (getChoiceMode() == ExpandableListChoiceMode.GROUPS_ONLY) {
			throw new IllegalStateException(
					"Children are not allowed to be selected when using the choice mode " + getChoiceMode());
		}

		return getGroupAdapter().getItem(groupIndex).getChildAdapter().setSelected(childIndex, true);
	}

	@Override
	public final boolean selectChild(final int groupIndex, final ChildType child) {
		return selectChild(groupIndex, indexOfChildOrThrowException(groupIndex, child));
	}

	@Override
	public final void adaptSelectionAutomatically(final boolean adaptSelectionAutomatically) {
		this.adaptSelectionAutomatically = adaptSelectionAutomatically;

		if (adaptSelectionAutomatically) {
			if (getChoiceMode() != ExpandableListChoiceMode.CHILDREN_ONLY && getSelectedGroupIndex() == -1) {
				if (!isEmpty() && getSelectedGroupIndex() == -1) {
					selectGroup(0);
				}
			} else if (getSelectedChildIndex() == -1) {
				for (int i = 0; i < getGroupCount(); i++) {
					if (!isGroupEmpty(i)) {
						for (int j = 0; j < getChildCount(i); j++) {
							if (isChildEnabled(i, j)) {
								selectChild(j, 0);
								return;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public final boolean isSelectionAdaptedAutomatically() {
		return adaptSelectionAutomatically;
	}

	@Override
	protected final void onSaveInstanceState(final Bundle savedState) {
		super.onSaveInstanceState(savedState);
		savedState.putBoolean(ADAPT_SELECTION_AUTOMATICALLY_BUNDLE_KEY, isSelectionAdaptedAutomatically());
	}

	@Override
	protected final void onRestoreInstanceState(final Bundle savedState) {
		super.onRestoreInstanceState(savedState);
		adaptSelectionAutomatically = savedState.getBoolean(ADAPT_SELECTION_AUTOMATICALLY_BUNDLE_KEY, true);
	}

	@Override
	public final SingleChoiceExpandableListAdapterImplementation<GroupType, ChildType> clone()
			throws CloneNotSupportedException {
		return new SingleChoiceExpandableListAdapterImplementation<>(getContext(), getGroupInflater(),
				getChildInflater(), getDecorator(), getLogLevel(), cloneGroupAdapter(), areDuplicateChildrenAllowed(),
				isNotifiedOnChange(), isGroupExpandedOnClick(), getItemClickListeners(), getAdapterListeners(),
				getExpansionListeners(), getEnableStateListeners(), getNumberOfGroupStates(), getNumberOfChildStates(),
				isGroupStateTriggeredOnClick(), isChildStateTriggeredOnClick(), getItemStateListeners(),
				getSortingListeners(), getFilterListeners(), isGroupSelectedOnClick(), isChildSelectedOnClick(),
				getSelectionListeners(), getChoiceMode());
	}

}