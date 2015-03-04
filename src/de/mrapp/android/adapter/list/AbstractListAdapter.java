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
package de.mrapp.android.adapter.list;

import static de.mrapp.android.adapter.util.Condition.ensureAtLeast;
import static de.mrapp.android.adapter.util.Condition.ensureAtMaximum;
import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import de.mrapp.android.adapter.ListAdapter;
import de.mrapp.android.adapter.datastructure.SerializableWrapper;
import de.mrapp.android.adapter.datastructure.item.Item;
import de.mrapp.android.adapter.datastructure.item.ItemIterator;
import de.mrapp.android.adapter.datastructure.item.ItemListIterator;
import de.mrapp.android.adapter.inflater.Inflater;
import de.mrapp.android.adapter.logging.LogLevel;
import de.mrapp.android.adapter.logging.Logger;
import de.mrapp.android.adapter.util.VisibleForTesting;

/**
 * An abstract base class for all adapters, whose underlying data is managed as
 * a list of arbitrary items. Such an adapter's purpose is to provide the
 * underlying data for visualization using a {@link ListView} widget.
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
public abstract class AbstractListAdapter<DataType, DecoratorType> extends
		BaseAdapter implements ListAdapter<DataType> {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The key, which is used to store the adapter's underlying data within a
	 * bundle, if it implements the interface {@link Parcelable}.
	 */
	@VisibleForTesting
	protected static final String PARCELABLE_ITEMS_BUNDLE_KEY = AbstractListAdapter.class
			.getSimpleName() + "::ParcelableItems";

	/**
	 * The key, which is used to store the adapter's underlying data within a
	 * bundle, if it implements the interface {@link Serializable}.
	 */
	@VisibleForTesting
	protected static final String SERIALIZABLE_ITEMS_BUNDLE_KEY = AbstractListAdapter.class
			.getSimpleName() + "::SerializableItems";

	/**
	 * The key, which is used to store, whether duplicate items should be
	 * allowed, or not, within a bundle.
	 */
	@VisibleForTesting
	protected static final String ALLOW_DUPLICATES_BUNDLE_KEY = AbstractListAdapter.class
			.getSimpleName() + "::AllowDuplicates";

	/**
	 * The key, which is used to store the key value pairs, which are stored
	 * within the adapter, within a bundle.
	 */
	@VisibleForTesting
	protected static final String PARAMETERS_BUNDLE_KEY = AbstractListAdapter.class
			.getSimpleName() + "::Parameters";

	/**
	 * The key, which is used to store the log level, which is used for logging,
	 * within a bundle.
	 */
	@VisibleForTesting
	protected static final String LOG_LEVEL_BUNDLE_KEY = AbstractListAdapter.class
			.getSimpleName() + "::LogLevel";

	/**
	 * The context, the adapter belongs to.
	 */
	private final transient Context context;

	/**
	 * The inflater, which is used to inflate the views, which are used to
	 * visualize the adapter's items.
	 */
	private final transient Inflater inflater;

	/**
	 * The decorator, which allows to customize the appearance of the views,
	 * which are used to visualize the items of the adapter.
	 */
	private final transient DecoratorType decorator;

	/**
	 * The logger, which is used for logging.
	 */
	private final transient Logger logger;

	/**
	 * A set, which contains the listeners, which should be notified, when the
	 * adapter's underlying data has been modified.
	 */
	private transient Set<ListAdapterListener<DataType>> adapterListeners;

	/**
	 * A bundle, which contains key value pairs, which are stored within the
	 * adapter.
	 */
	private Bundle parameters;

	/**
	 * True, if duplicate items are allowed, false otherwise.
	 */
	private boolean allowDuplicates;

	/**
	 * A list, which contains the the adapter's underlying data.
	 */
	private ArrayList<Item<DataType>> items;

	/**
	 * Notifies all listeners, which have been registered to be notified, when
	 * the adapter's underlying data has been modified, about an item, which has
	 * been added to the adapter.
	 * 
	 * @param item
	 *            The item, which has been added to the adapter, as an instance
	 *            of the generic type DataType. The item may not be null
	 * @param index
	 *            The index of the item, which has been added to the adapter, as
	 *            an {@link Integer} value. The index must be between 0 and the
	 *            value of the method <code>getNumberOfItems():int</code> - 1
	 */
	private void notifyOnItemAdded(final DataType item, final int index) {
		for (ListAdapterListener<DataType> listener : adapterListeners) {
			listener.onItemAdded(this, item, index);
		}
	}

	/**
	 * Notifies all listeners, which have been registered to be notified, when
	 * the adapter's underlying data has been modified, about an item, which has
	 * been removed from the adapter.
	 * 
	 * @param item
	 *            The item, which has been removed from the adapter, as an
	 *            instance of the generic type DataType. The item may not be
	 *            null
	 * @param index
	 *            The index of the item, which has been removed from the
	 *            adapter, as an {@link Integer} value. The index must be
	 *            between 0 and the value of the method
	 *            <code>getNumberOfItems():int</code>
	 */
	private void notifyOnItemRemoved(final DataType item, final int index) {
		for (ListAdapterListener<DataType> listener : adapterListeners) {
			listener.onItemRemoved(this, item, index);
		}
	}

	/**
	 * Returns, whether the adapter's underlying data implements the interface
	 * {@link Parcelable}, or not.
	 * 
	 * @return True, if the adapter's underlying data implements the interface
	 *         {@link Parcelable} or if the adapter is empty, false otherwise
	 */
	private boolean isUnderlyingDataParcelable() {
		if (!isEmpty()) {
			if (!Parcelable.class.isAssignableFrom(getItem(0).getClass())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Creates and returns an {@link OnClickListener}, which is invoked, when a
	 * specific item has been clicked.
	 * 
	 * @param index
	 *            The index of the item, which should cause the listener to be
	 *            invoked, when clicked, as an {@link Integer} value
	 * @return The listener, which has been created as an instance of the type
	 *         {@link OnClickListener}
	 */
	private OnClickListener createItemOnClickListener(final int index) {
		return new OnClickListener() {

			@Override
			public void onClick(final View v) {
				onItemClicked(index);
			}

		};
	}

	/**
	 * Returns, whether the adapter's underlying data implements the interface
	 * {@link Serializable}, or not.
	 * 
	 * @return True, if the adapter's underlying data implements the interface
	 *         {@link Serializable} or if the adapter is empty, false otherwise
	 */
	private boolean isUnderlyingDataSerializable() {
		if (!isEmpty()) {
			if (!Serializable.class.isAssignableFrom(getItem(0).getClass())) {
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
	 * to visualize the adapter's items.
	 * 
	 * @return The inflater, which is used to inflate views, which are used to
	 *         visualize the adapter's items, as an instance of the type
	 *         {@link Inflater}. The inflater may not be null
	 */
	protected final Inflater getInflater() {
		return inflater;
	}

	/**
	 * Returns the decorator, which allows to customize the appearance of the
	 * views, which are used to visualize the items of the adapter.
	 * 
	 * @return The decorator, which allows to customize the appearance of the
	 *         views, which are used to visualize the items of the adapter, as
	 *         an instance of the generic type DecoratorType. The decorator may
	 *         not be null
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
	 * Returns a list, which contains the adapter's underlying data.
	 * 
	 * @return A list, which contains the adapters underlying data, as an
	 *         instance of the type {@link ArrayList} or an empty list, if the
	 *         adapter does not contain any data
	 */
	protected final ArrayList<Item<DataType>> getItems() {
		return items;
	}

	/**
	 * Sets the list, which contains the adapter's underlying data.
	 * 
	 * @param items
	 *            The list, which should be set, as an instance of the type
	 *            {@link ArrayList} or an empty list, if the adapter should not
	 *            contain any data
	 */
	protected final void setItems(final ArrayList<Item<DataType>> items) {
		ensureNotNull(items, "The items may not be null");
		this.items = items;
	}

	/**
	 * Creates and returns a deep copy of the list, which contains the adapter's
	 * underlying data.
	 * 
	 * @return A deep copy of the list, which contains the adapter's underlying
	 *         data, as an instance of the type {@link ArrayList}. The list may
	 *         not be null
	 * @throws CloneNotSupportedException
	 *             The exception, which is thrown, if cloning is not supported
	 *             by the adapter's underlying data
	 */
	protected final ArrayList<Item<DataType>> cloneItems()
			throws CloneNotSupportedException {
		ArrayList<Item<DataType>> clonedItems = new ArrayList<Item<DataType>>();

		for (Item<DataType> item : items) {
			clonedItems.add(item.clone());
		}

		return clonedItems;
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
	protected final Set<ListAdapterListener<DataType>> getAdapterListeners() {
		return adapterListeners;
	}

	/**
	 * The method, which is invoked, when an item has been clicked.
	 * 
	 * @param index
	 *            The index of the item, which has been clicked, as an
	 *            {@link Integer} value
	 */
	protected void onItemClicked(final int index) {
		return;
	}

	/**
	 * This method is invoked to apply the decorator, which allows to customize
	 * the view, which is used to visualize a specific item.
	 * 
	 * @param context
	 *            The context, the adapter belongs to, as an instance of the
	 *            class {@link Context}. The context may not be null
	 * @param view
	 *            The view, which is used to visualize the item, as an instance
	 *            of the class {@link View}. The view may not be null
	 * @param index
	 *            The index of the item, which should be visualized, as an
	 *            {@link Integer} value
	 */
	protected abstract void applyDecorator(final Context context,
			final View view, final int index);

	/**
	 * Creates a new adapter, whose underlying data is managed as a list of
	 * arbitrary items.
	 * 
	 * @param context
	 *            The context, the adapter belongs to, as an instance of the
	 *            class {@link Context}. The context may not be null
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
	 * @param adapterListeners
	 *            A set, which contains the listeners, which should be notified,
	 *            when the adapter's underlying data has been modified, as an
	 *            instance of the type {@link Set} or an empty set, if no
	 *            listeners should be notified
	 */
	protected AbstractListAdapter(final Context context,
			final Inflater inflater, final DecoratorType decorator,
			final LogLevel logLevel, final ArrayList<Item<DataType>> items,
			final boolean allowDuplicates,
			final Set<ListAdapterListener<DataType>> adapterListeners) {
		ensureNotNull(context, "The context may not be null");
		ensureNotNull(inflater, "The inflater may not be null");
		ensureNotNull(decorator, "The decorator may not be null");
		ensureNotNull(adapterListeners, "The adapter listeners may not be null");
		setItems(items);
		this.context = context;
		this.inflater = inflater;
		this.decorator = decorator;
		this.logger = new Logger(logLevel);
		this.items = items;
		this.parameters = null;
		this.allowDuplicates = allowDuplicates;
		this.adapterListeners = adapterListeners;
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
		return parameters;
	}

	@Override
	public final void setParameters(final Bundle parameters) {
		this.parameters = parameters;
	}

	@Override
	public final boolean areDuplicatesAllowed() {
		return allowDuplicates;
	}

	@Override
	public final void allowDuplicates(final boolean allowDuplicates) {
		this.allowDuplicates = allowDuplicates;
		String message = "Duplicate items are now "
				+ (allowDuplicates ? "allowed" : "disallowed");
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final void addAdapterListener(
			final ListAdapterListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		adapterListeners.add(listener);
		String message = "Added adapter listener \"" + listener + "\"";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final void removeAdapterListener(
			final ListAdapterListener<DataType> listener) {
		ensureNotNull(listener, "The listener may not be null");
		adapterListeners.remove(listener);
		String message = "Removed adapter listener \"" + listener + "\"";
		getLogger().logDebug(getClass(), message);
	}

	@Override
	public final boolean addItem(final DataType item) {
		return addItem(getNumberOfItems(), item);
	}

	@Override
	public final boolean addItem(final int index, final DataType item) {
		ensureNotNull(item, "The item may not be null");

		if (!areDuplicatesAllowed() && containsItem(item)) {
			String message = "Item \"" + item + "\" at index " + index
					+ " not added, because adapter already contains item";
			getLogger().logDebug(getClass(), message);
			return false;
		}

		items.add(index, new Item<DataType>(item));
		notifyOnItemAdded(item, index);
		notifyDataSetChanged();
		String message = "Item \"" + item + "\" added at index " + index;
		getLogger().logInfo(getClass(), message);
		return true;
	}

	@Override
	public final boolean addAllItems(final Collection<DataType> items) {
		ensureNotNull(items, "The collection may not be null");
		return addAllItems(getNumberOfItems(), items);
	}

	@Override
	public final boolean addAllItems(final int index,
			final Collection<DataType> items) {
		ensureNotNull(items, "The collection may not be null");
		boolean result = true;
		int currentIndex = index;

		for (DataType item : items) {
			result &= addItem(currentIndex, item);
			currentIndex++;
		}

		return result;
	}

	@Override
	public final boolean addAllItems(final DataType... items) {
		return addAllItems(getNumberOfItems(), items);
	}

	@Override
	public final boolean addAllItems(final int index, final DataType... items) {
		ensureNotNull(items, "The array may not be null");
		return addAllItems(index, Arrays.asList(items));
	}

	@Override
	public final DataType replaceItem(final int index, final DataType item) {
		ensureNotNull(item, "The item may not be null");
		DataType replacedItem = items.set(index, new Item<DataType>(item))
				.getData();
		notifyOnItemRemoved(replacedItem, index);
		notifyOnItemAdded(item, index);
		notifyDataSetChanged();
		String message = "Replaced item \"" + replacedItem + "\" at index "
				+ index + " with item \"" + item + "\"";
		getLogger().logInfo(getClass(), message);
		return replacedItem;
	}

	@Override
	public final DataType removeItem(final int index) {
		DataType removedItem = items.remove(index).getData();
		notifyOnItemRemoved(removedItem, index);
		notifyDataSetChanged();
		String message = "Removed item \"" + removedItem + "\" from index "
				+ index;
		getLogger().logInfo(getClass(), message);
		return removedItem;
	}

	@Override
	public final boolean removeItem(final DataType item) {
		ensureNotNull(item, "The item may not be null");
		int index = indexOf(item);

		if (index != -1) {
			items.remove(index);
			notifyOnItemRemoved(item, index);
			notifyDataSetChanged();
			String message = "Removed item \"" + item + "\" from index "
					+ index;
			getLogger().logInfo(getClass(), message);
			return true;
		}

		String message = "Item \"" + item
				+ "\" not removed, because adapter does not contain item";
		getLogger().logDebug(getClass(), message);
		return false;
	}

	@Override
	public final boolean removeAllItems(final Collection<DataType> items) {
		ensureNotNull(items, "The collection may not be null");
		int numberOfRemovedItems = 0;

		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			if (items.contains(getItem(i))) {
				removeItem(i);
				numberOfRemovedItems++;
			}
		}

		return numberOfRemovedItems == items.size();
	}

	@Override
	public final boolean removeAllItems(final DataType... items) {
		ensureNotNull(items, "The array may not be null");
		return removeAllItems(Arrays.asList(items));
	}

	@Override
	public final void retainAllItems(final Collection<DataType> items) {
		ensureNotNull(items, "The collection may not be null");

		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			if (!items.contains(getItem(i))) {
				removeItem(i);
			}
		}
	}

	@Override
	public final void retainAllItems(final DataType... items) {
		ensureNotNull(items, "The array may not be null");
		retainAllItems(Arrays.asList(items));
	}

	@Override
	public final void clearItems() {
		for (int i = getNumberOfItems() - 1; i >= 0; i--) {
			removeItem(i);
		}

		getLogger().logInfo(getClass(), "Cleared all items");
	}

	@Override
	public final Iterator<DataType> iterator() {
		return new ItemIterator<DataType>(items, this);
	}

	@Override
	public final ListIterator<DataType> listIterator() {
		return new ItemListIterator<DataType>(items, this);
	}

	@Override
	public final ListIterator<DataType> listIterator(final int index) {
		return new ItemListIterator<DataType>(items, this, index);
	}

	@Override
	public final Collection<DataType> subList(final int start, final int end) {
		Collection<DataType> subList = new ArrayList<DataType>();

		for (int i = start; i < end; i++) {
			subList.add(getItem(i));
		}

		return subList;
	}

	@Override
	public final Object[] toArray() {
		return getAllItems().toArray();
	}

	@Override
	public final <T> T[] toArray(final T[] array) {
		return getAllItems().toArray(array);
	}

	@Override
	public final int indexOf(final DataType item) {
		ensureNotNull(item, "The item may not be null");
		return getAllItems().indexOf(item);
	}

	@Override
	public final int lastIndexOf(final DataType item) {
		ensureNotNull(item, "The item may not be null");
		return getAllItems().lastIndexOf(item);
	}

	@Override
	public final boolean containsItem(final DataType item) {
		ensureNotNull(item, "The item may not be null");
		return getAllItems().contains(item);
	}

	@Override
	public final boolean containsAllItems(final Collection<DataType> items) {
		ensureNotNull(items, "The collection may not be null");
		return getAllItems().containsAll(items);
	}

	@Override
	public final boolean containsAllItems(final DataType... items) {
		ensureNotNull(items, "The array may not be null");
		return containsAllItems(Arrays.asList(items));
	}

	@Override
	public final int getNumberOfItems() {
		return items.size();
	}

	@Override
	public final DataType getItem(final int index) {
		return items.get(index).getData();
	}

	@Override
	public final List<DataType> getAllItems() {
		List<DataType> result = new ArrayList<DataType>();

		for (Item<DataType> item : items) {
			result.add(item.getData());
		}

		return result;
	}

	@Override
	public final boolean isEmpty() {
		return items.isEmpty();
	}

	@Override
	public final int getCount() {
		return getNumberOfItems();
	}

	@Override
	public final long getItemId(final int index) {
		ensureAtLeast(index, 0, "The index must be at least 0");
		ensureAtMaximum(index, items.size() - 1,
				isEmpty() ? "The index must be at maximum "
						+ (items.size() - 1) : "The adapter is empty");
		return index;
	}

	@Override
	public final View getView(final int index, final View convertView,
			final ViewGroup parent) {
		View view = convertView;

		if (view == null) {
			view = getInflater().inflate(getContext(), parent, false);
			String message = "Inflated view to visualize the item at index "
					+ index;
			getLogger().logVerbose(getClass(), message);
		}

		view.setOnClickListener(createItemOnClickListener(index));
		applyDecorator(getContext(), view, index);
		return view;
	}

	@Override
	public void onSaveInstanceState(final Bundle outState) {
		if (isUnderlyingDataParcelable()) {
			outState.putParcelableArrayList(PARCELABLE_ITEMS_BUNDLE_KEY, items);
		} else if (isUnderlyingDataSerializable()) {
			SerializableWrapper<ArrayList<Item<DataType>>> wrappedItems = new SerializableWrapper<ArrayList<Item<DataType>>>(
					getItems());
			outState.putSerializable(SERIALIZABLE_ITEMS_BUNDLE_KEY,
					wrappedItems);
		} else {
			String message = "The adapter's items can not be stored, because the "
					+ "underlying data does neither implement the interface \""
					+ Parcelable.class.getName()
					+ "\", nor the interface \""
					+ Serializable.class.getName() + "\"";
			getLogger().logWarn(getClass(), message);
		}

		outState.putBundle(PARAMETERS_BUNDLE_KEY, getParameters());
		outState.putBoolean(ALLOW_DUPLICATES_BUNDLE_KEY, areDuplicatesAllowed());
		outState.putInt(LOG_LEVEL_BUNDLE_KEY, getLogLevel().getRank());
		getLogger().logDebug(getClass(), "Saved instance state");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onRestoreInstanceState(final Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(PARCELABLE_ITEMS_BUNDLE_KEY)) {
				items = savedInstanceState
						.getParcelableArrayList(PARCELABLE_ITEMS_BUNDLE_KEY);
			} else if (savedInstanceState
					.containsKey(SERIALIZABLE_ITEMS_BUNDLE_KEY)) {
				SerializableWrapper<ArrayList<Item<DataType>>> wrappedItems = (SerializableWrapper<ArrayList<Item<DataType>>>) savedInstanceState
						.getSerializable(SERIALIZABLE_ITEMS_BUNDLE_KEY);
				items = wrappedItems.getWrappedInstance();
			}

			setParameters(savedInstanceState.getBundle(PARAMETERS_BUNDLE_KEY));
			allowDuplicates(savedInstanceState
					.getBoolean(ALLOW_DUPLICATES_BUNDLE_KEY));
			setLogLevel(LogLevel.fromRank(savedInstanceState
					.getInt(LOG_LEVEL_BUNDLE_KEY)));
			notifyDataSetChanged();
			getLogger().logDebug(getClass(), "Restored instance state");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (allowDuplicates ? 1231 : 1237);
		result = prime * result + items.hashCode();
		result = prime * result + getLogLevel().getRank();
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractListAdapter<?, ?> other = (AbstractListAdapter<?, ?>) obj;
		if (allowDuplicates != other.allowDuplicates)
			return false;
		if (!items.equals(other.items))
			return false;
		if (!getLogLevel().equals(other.getLogLevel()))
			return false;
		return true;
	}

	@Override
	public abstract AbstractListAdapter<DataType, DecoratorType> clone()
			throws CloneNotSupportedException;

}