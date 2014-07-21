package de.mrapp.android.adapter.list.selectable;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.List;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import de.mrapp.android.adapter.SelectableListDecorator;
import de.mrapp.android.adapter.datastructure.SerializableWrapper;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.itemstate.ListItemStateListener;
import de.mrapp.android.adapter.list.sortable.AbstractSortableListAdapter;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;
import de.mrapp.android.adapter.util.VisibleForTesting;

public abstract class AbstractSelectableListAdapter<DataType> extends
		AbstractSortableListAdapter<DataType> implements
		SelectableListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The key, which is used to store the listeners, which should be notified,
	 * when an item has been selected or unselected, within a bundle.
	 */
	@VisibleForTesting
	protected static final String SELECTION_LISTENERS_BUNDLE_KEY = AbstractSelectableListAdapter.class
			.getSimpleName() + "::SelectionListeners";

	/**
	 * A set, which contains the listeners, which should be notified, when an
	 * item has been selected or unselected.
	 */
	private Set<ListSelectionListener<DataType>> selectionListeners;

	// TODO: Remove
	private SelectableListDecorator<DataType> decorator;

	/**
	 * Notifies all listeners, which have been registered to be notified when
	 * the selection of an item of the adapter has been changed, about an item,
	 * which has been selected.
	 * 
	 * @param item
	 *            The item, which has been selected, as an instance of the
	 *            generic type DataType. The item may not be null
	 * @param index
	 *            The index of the item, which has been selected, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>size():int</code> - 1
	 */
	protected final void notifyOnItemSelected(final DataType item,
			final int index) {
		for (ListSelectionListener<DataType> listener : selectionListeners) {
			listener.onItemSelected(item, index);
		}
	}

	/**
	 * Notifies all listeners, which have been registered to be notified when
	 * the selection of an item of the adapter has been changed, about an item,
	 * which has been unselected.
	 * 
	 * @param item
	 *            The item, which has been unselected, as an instance of the
	 *            generic type DataType. The item may not be null
	 * @param index
	 *            The index of the item, which has been unselected, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>size():int</code> - 1
	 */
	protected final void notifyOnItemUnselected(final DataType item,
			final int index) {
		for (ListSelectionListener<DataType> listener : selectionListeners) {
			listener.onItemUnselected(item, index);
		}
	}

	protected final SelectableListDecorator<DataType> getDecorator() {
		return decorator;
	}

	/**
	 * Returns the set, which contains the listeners, which should be notified
	 * when the selection of an item of the adapter has been changed.
	 * 
	 * @return The set, which contains the listeners, which should be notified
	 *         when the selection of an item of the adapter has been changed, as
	 *         an instance of the type {@link Set}. The set may not be null
	 */
	protected final Set<ListSelectionListener<DataType>> getSelectionListeners() {
		return selectionListeners;
	}

	protected AbstractSelectableListAdapter(final Context context,
			final Inflater inflater, final List<Item<DataType>> items,
			final boolean allowDuplicates,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListEnableStateListener<DataType>> enableStateListeners,
			final int numberOfItemStates,
			final boolean triggerItemStateOnClick,
			final Set<ListItemStateListener<DataType>> itemStateListeners,
			final Set<ListSortingListener<DataType>> sortingListeners,
			final Set<ListSelectionListener<DataType>> selectionListeners,
			final SelectableListDecorator<DataType> decorator) {
		super(context, inflater, items, allowDuplicates, adapterListeners,
				enableStateListeners, numberOfItemStates,
				triggerItemStateOnClick, itemStateListeners, sortingListeners);
		ensureNotNull(decorator, "The decorator may not be null");
		ensureNotNull(selectionListeners,
				"The selection listeners may not be null");
		this.decorator = decorator;
		this.selectionListeners = selectionListeners;
	}

	@Override
	public final void addSelectionListener(
			final ListSelectionListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		selectionListeners.add(listener);
	}

	@Override
	public final void removeSelectionListener(
			final ListSelectionListener<DataType> listener) {
		selectionListeners.remove(listener);
	}

	@Override
	public final boolean isSelected(final int index) {
		return getItems().get(index).isSelected();
	}

	@Override
	public final boolean isSelected(final DataType item) {
		return isSelected(indexOf(item));
	}

	@Override
	public final View getView(final int index, final View convertView,
			final ViewGroup parent) {
		View view = getInflater().inflate(getContext(), parent);
		decorator.onCreateItem(getContext(), view, getItem(index), index,
				isEnabled(index), getItemState(index), isSelected(index));
		return view;
	}

	@Override
	public final void onSaveInstanceState(final Bundle outState) {
		SerializableWrapper<Set<ListSelectionListener<DataType>>> wrappedSelectionListeners = new SerializableWrapper<Set<ListSelectionListener<DataType>>>(
				getSelectionListeners());
		outState.putSerializable(SELECTION_LISTENERS_BUNDLE_KEY,
				wrappedSelectionListeners);
	}

	@SuppressWarnings("unchecked")
	@Override
	public final void onRestoreInstanceState(final Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			SerializableWrapper<Set<ListSelectionListener<DataType>>> wrappedSelectionListeners = (SerializableWrapper<Set<ListSelectionListener<DataType>>>) savedInstanceState
					.getSerializable(SELECTION_LISTENERS_BUNDLE_KEY);
			selectionListeners = wrappedSelectionListeners.getWrappedInstance();

			notifyDataSetChanged();
		}
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + selectionListeners.hashCode();
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
		AbstractSelectableListAdapter<?> other = (AbstractSelectableListAdapter<?>) obj;
		if (!selectionListeners.equals(other.selectionListeners))
			return false;
		return true;
	}

	@Override
	public abstract AbstractSelectableListAdapter<DataType> clone()
			throws CloneNotSupportedException;

}