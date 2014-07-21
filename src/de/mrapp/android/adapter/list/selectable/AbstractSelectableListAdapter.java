package de.mrapp.android.adapter.list.selectable;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import de.mrapp.android.adapter.SelectableListDecorator;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.list.enablestate.ListEnableStateListener;
import de.mrapp.android.adapter.list.sortable.AbstractSortableListAdapter;
import de.mrapp.android.adapter.list.sortable.ListSortingListener;

public abstract class AbstractSelectableListAdapter<DataType> extends
		AbstractSortableListAdapter<DataType> implements
		SelectableListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A set, which contains the listeners, which should be notified when the
	 * selection of an item of the adapter has been changed.
	 */
	private Set<ListSelectionListener<DataType>> selectionListeners;

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
			final Set<ListSortingListener<DataType>> sortingListeners,
			final Set<ListSelectionListener<DataType>> selectionListeners,
			final SelectableListDecorator<DataType> decorator) {
		super(context, inflater, items, allowDuplicates, adapterListeners,
				enableStateListeners, numberOfItemStates,
				triggerItemStateOnClick, sortingListeners);
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
	public abstract AbstractSelectableListAdapter<DataType> clone()
			throws CloneNotSupportedException;

}