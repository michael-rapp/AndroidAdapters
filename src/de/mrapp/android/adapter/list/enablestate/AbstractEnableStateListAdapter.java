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
package de.mrapp.android.adapter.list.enablestate;

import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import android.content.Context;
import android.widget.AbsListView;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.list.AbstractListAdapter;
import de.mrapp.android.adapter.list.ListAdapterItemClickListener;
import de.mrapp.android.adapter.list.ListAdapterListener;
import de.mrapp.android.adapter.logging.LogLevel;

/**
 * An abstract base class for all adapters, whose underlying data is managed as
 * a list of arbitrary items, which may be disabled or enabled. Such an
 * adapter's purpose is to provide the underlying data for visualization using a
 * {@link AbsListView} widget.
 * 
 * @param <DataType>
 *            The type of the adapter's underlying data
 * @param <DecoratorType>
 *            The type of the decorator, which allows to customize the
 *            appearance of the views, which are used to visualize the items of
 *            the adapter
 * 
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public abstract class AbstractEnableStateListAdapter<DataType, DecoratorType>
		extends AbstractListAdapter<DataType, DecoratorType>implements EnableStateListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A set, which contains the listeners, which should be notified, when an
	 * item has been disabled or enabled.
	 */
	private transient Set<ListEnableStateListener<DataType>> enableStateListeners;

	/**
	 * Notifies all listeners, which have been registered to be notified, when
	 * an item has been disabled or enabled, about an item, which has been
	 * enabled.
	 * 
	 * @param item
	 *            The item, which has been enabled, as an instance of the
	 *            generic type DataType. The item may not be null
	 * @param index
	 *            The index of the item, which has been enabled, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1
	 */
	private void notifyOnItemEnabled(final DataType item, final int index) {
		for (ListEnableStateListener<DataType> listener : enableStateListeners) {
			listener.onItemEnabled(this, item, index);
		}
	}

	/**
	 * Notifies all listeners, which have been registered to be notified, when
	 * an item has been disabled or enabled, about an item, which has been
	 * disabled.
	 * 
	 * @param item
	 *            The item, which has been disabled, as an instance of the
	 *            generic type DataType. The item may not be null
	 * @param index
	 *            The index of the item, which has been disabled, as an
	 *            {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1
	 */
	private void notifyOnItemDisabled(final DataType item, final int index) {
		for (ListEnableStateListener<DataType> listener : enableStateListeners) {
			listener.onItemDisabled(this, item, index);
		}
	}

	/**
	 * Returns a set, which contains the listeners, which should be notified,
	 * when an item has been disabled or enabled.
	 * 
	 * @return A set, which contains the listeners, which should be notified,
	 *         when an item has been disabled or enabled, as an instance of the
	 *         type {@link Set} or an empty set, if no listeners should be
	 *         notified
	 */
	protected final Set<ListEnableStateListener<DataType>> getEnableStateListeners() {
		return enableStateListeners;
	}

	/**
	 * Sets the set, which contains the listeners, which should be notified,
	 * when an item has been disabled or enabled.
	 * 
	 * @param enableStateListeners
	 *            The set, which should be set, as an instance of the type
	 *            {@link Set} or an empty set, if no listeners should be
	 *            notified
	 */
	protected final void setEnableStateListeners(final Set<ListEnableStateListener<DataType>> enableStateListeners) {
		ensureNotNull(enableStateListeners, "The enable state listeners may not be null");
		this.enableStateListeners = enableStateListeners;
	}

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary items, which may be disabled or enabled.
	 * 
	 * @param context
	 *            The context, the adapter should belong to, as an instance of
	 *            the class {@link Context}. The context may not be null
	 * @param inflater
	 *            The inflater, which should be used to inflate the views, which
	 *            are used to visualize the adapter's items, as an instance of
	 *            the type {@link Inflater}. The inflater may not be null
	 * @param decorator
	 *            The decorator, which should be used to customize the
	 *            appearance of the views, which are used to visualize the items
	 *            of the adapter, as an instance of the generic type
	 *            DecoratorType. The decorator may not be null
	 * @param logLevel
	 *            The log level, which should be used for logging, as a value of
	 *            the enum {@link LogLevel}. The log level may not be null
	 * @param items
	 *            A list, which contains the adapter's items, or an empty list,
	 *            if the adapter should not contain any items
	 * @param allowDuplicates
	 *            True, if duplicate items should be allowed, false otherwise
	 * @param notifyOnChange
	 *            True, if the method <code>notifyDataSetChanged():void</code>
	 *            should be automatically called when the adapter's underlying
	 *            data has been changed, false otherwise
	 * @param itemClickListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when an item of the adapter has been clicked by the user, as
	 *            an instance of the type {@link Set} or an empty set, if no
	 *            listeners should be notified
	 * @param adapterListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been modified or an
	 *            empty set, if no listeners should be notified
	 * @param enableStateListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when an item has been disabled or enabled or an empty set, if
	 *            no listeners should be notified
	 */
	protected AbstractEnableStateListAdapter(final Context context, final Inflater inflater,
			final DecoratorType decorator, final LogLevel logLevel, final ArrayList<Item<DataType>> items,
			final boolean allowDuplicates, final boolean notifyOnChange,
			final Set<ListAdapterItemClickListener<DataType>> itemClickListeners,
			final Set<ListAdapterListener<DataType>> adapterListeners,
			final Set<ListEnableStateListener<DataType>> enableStateListeners) {
		super(context, inflater, decorator, logLevel, items, allowDuplicates, notifyOnChange, itemClickListeners,
				adapterListeners);
		setEnableStateListeners(enableStateListeners);
	}

	@Override
	public final boolean isEnabled(final int index) {
		return getItems().get(index).isEnabled();
	}

	@Override
	public final boolean isEnabled(final DataType item) {
		return getItems().get(indexOf(item)).isEnabled();
	}

	@Override
	public final int getFirstEnabledIndex() {
		for (int i = 0; i < getNumberOfItems(); i++) {
			if (getItems().get(i).isEnabled()) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public final DataType getFirstEnabledItem() {
		for (Item<DataType> item : getItems()) {
			if (item.isEnabled()) {
				return item.getData();
			}
		}

		return null;
	}

	@Override
	public final int getLastEnabledIndex() {
		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			if (getItems().get(i).isEnabled()) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public final DataType getLastEnabledItem() {
		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			Item<DataType> item = getItems().get(i);

			if (item.isEnabled()) {
				return item.getData();
			}
		}

		return null;
	}

	@Override
	public final int getFirstDisabledIndex() {
		for (int i = 0; i < getNumberOfItems(); i++) {
			if (!getItems().get(i).isEnabled()) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public final DataType getFirstDisabledItem() {
		for (Item<DataType> item : getItems()) {
			if (!item.isEnabled()) {
				return item.getData();
			}
		}

		return null;
	}

	@Override
	public final int getLastDisabledIndex() {
		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			if (!getItems().get(i).isEnabled()) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public final DataType getLastDisabledItem() {
		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			Item<DataType> item = getItems().get(i);

			if (!item.isEnabled()) {
				return item.getData();
			}
		}

		return null;
	}

	@Override
	public final List<Integer> getEnabledIndices() {
		List<Integer> enabledIndices = new ArrayList<Integer>();

		for (int i = 0; i < getNumberOfItems(); i++) {
			if (getItems().get(i).isEnabled()) {
				enabledIndices.add(i);
			}
		}

		return enabledIndices;
	}

	@Override
	public final List<DataType> getEnabledItems() {
		List<DataType> enabledItems = new ArrayList<DataType>();

		for (Item<DataType> item : getItems()) {
			if (item.isEnabled()) {
				enabledItems.add(item.getData());
			}
		}

		return enabledItems;
	}

	@Override
	public final List<Integer> getDisabledIndices() {
		List<Integer> disabledIndices = new ArrayList<Integer>();

		for (int i = 0; i < getNumberOfItems(); i++) {
			if (!getItems().get(i).isEnabled()) {
				disabledIndices.add(i);
			}
		}

		return disabledIndices;
	}

	@Override
	public final List<DataType> getDisabledItems() {
		List<DataType> disabledItems = new ArrayList<DataType>();

		for (Item<DataType> item : getItems()) {
			if (!item.isEnabled()) {
				disabledItems.add(item.getData());
			}
		}

		return disabledItems;
	}

	@Override
	public final int getNumberOfEnabledItems() {
		return getEnabledItems().size();
	}

	@Override
	public final int getNumberOfDisabledItems() {
		return getDisabledItems().size();
	}

	@Override
	public final void setEnabled(final int index, final boolean enabled) {
		Item<DataType> item = getItems().get(index);
		item.setEnabled(enabled);

		if (enabled) {
			notifyOnItemEnabled(item.getData(), index);
		} else {
			notifyOnItemDisabled(item.getData(), index);
		}

		notifyOnDataSetChanged();
		String message = "Enabled item \"" + item + "\" at index " + index;
		getLogger().logInfo(getClass(), message);
	}

	@Override
	public final void setEnabled(final DataType item, final boolean enabled) {
		int index = indexOf(item);

		if (index != -1) {
			setEnabled(index, enabled);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final boolean triggerEnableState(final int index) {
		if (isEnabled(index)) {
			setEnabled(index, false);
			return false;
		} else {
			setEnabled(index, true);
			return true;
		}
	}

	@Override
	public final boolean triggerEnableState(final DataType item) {
		int index = indexOf(item);

		if (index != -1) {
			return triggerEnableState(indexOf(item));
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public final void setAllEnabled(final boolean enabled) {
		for (int i = 0; i < getNumberOfItems(); i++) {
			setEnabled(i, enabled);
		}
	}

	@Override
	public final void triggerAllEnableStates() {
		for (int i = 0; i < getNumberOfItems(); i++) {
			triggerEnableState(i);
		}
	}

	@Override
	public final void addEnableStateListner(final ListEnableStateListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		enableStateListeners.add(listener);
		String message = "Added enable state listener \"" + listener + "\"";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final void removeEnableStateListener(final ListEnableStateListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		enableStateListeners.remove(listener);
		String message = "Removed enable state listener \"" + listener + "\"";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public abstract AbstractEnableStateListAdapter<DataType, DecoratorType> clone() throws CloneNotSupportedException;

}