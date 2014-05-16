package de.mrapp.android.adapter.list;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.View;
import de.mrapp.android.adapter.util.Item;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

public abstract class AbstractSingleStateListAdapter<DataType> extends
		AbstractListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A set, which contains the listeners, which should be notified when the
	 * selection of an item of the adapter has been changed.
	 */
	private Set<ListSelectionListener<DataType>> selectionListeners;

	/**
	 * Notifies all listeners, which have been registered to be notified when
	 * the selection of an item of the adapter has been changed, about an item,
	 * which has been selected.
	 * 
	 * @param index
	 *            The index of the item, which has been selected, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>size():int</code> - 1
	 */
	protected final void notifyOnItemSelected(final int index) {
		for (ListSelectionListener<DataType> listener : selectionListeners) {
			listener.onItemSelected(index);
		}
	}

	/**
	 * Notifies all listeners, which have been registered to be notified when
	 * the selection of an item of the adapter has been changed, about an item,
	 * which has been unselected.
	 * 
	 * @param index
	 *            The index of the item, which has been unselected, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>size():int</code> - 1
	 */
	protected final void notifyOnItemUnselected(final int index) {
		for (ListSelectionListener<DataType> listener : selectionListeners) {
			listener.onItemUnselected(index);
		}
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

	protected AbstractSingleStateListAdapter(final Context context,
			final int itemViewId, final View itemView,
			final List<Item<DataType>> items,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListSortingListener<DataType>> sortingListeners,
			final Set<ListSelectionListener<DataType>> selectionListeners) {
		super(context, itemViewId, itemView, items, adapterListeners,
				sortingListeners);
		this.selectionListeners = selectionListeners;
	}

	/**
	 * Adds a new listener, which should be notified when the selection of an
	 * item has been changed.
	 * 
	 * @param listener
	 *            The listener, which should be added, as an instance of the
	 *            type {@link ListSelectionListener}. The listener may not be
	 *            null
	 */
	public final void addSelectionListener(
			final ListSelectionListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		selectionListeners.add(listener);
	}

	/**
	 * Removes a specific listener, which should not be notified when the
	 * selection of an item has been changed, anymore.
	 * 
	 * @param listener
	 *            The listener, which should be removed, as an instance of the
	 *            type {@link ListSelectionListener}. The listener may not be
	 *            null
	 */
	public final void removeSelectionListener(
			final ListSelectionListener<DataType> listener) {
		selectionListeners.remove(listener);
	}

	public final boolean isSelected(final int index) {
		return getItems().get(index).getSelectionState() == 1;
	}

	public final boolean isSelected(final DataType item) {
		return isSelected(indexOf(item));
	}

	@Override
	public abstract AbstractListAdapter<DataType> clone()
			throws CloneNotSupportedException;

}