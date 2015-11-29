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
package de.mrapp.android.adapter.datastructure;

import android.os.Parcel;
import android.os.Parcelable;
import de.mrapp.android.adapter.Filter;
import de.mrapp.android.adapter.util.ClassUtil;
import static de.mrapp.android.adapter.util.Condition.ensureNotNull;

/**
 * A representation of a filter, which has been applied on an adapter's
 * underlying data.
 * 
 * @param <DataType>
 *            The type of the a filtered adapter's underlying data
 *
 * @author Michael Rapp
 * 
 * @since 0.1.0
 */
public class AppliedFilter<DataType> implements DataStructure, Parcelable {

	/**
	 * A creator, which allows to create instances of the class
	 * {@link AppliedFilter} from parcels.
	 */
	@SuppressWarnings("rawtypes")
	public static final Creator<AppliedFilter> CREATOR = new Creator<AppliedFilter>() {

		@Override
		public AppliedFilter createFromParcel(final Parcel source) {
			return new AppliedFilter(source);
		}

		@Override
		public AppliedFilter[] newArray(final int size) {
			return new AppliedFilter[size];
		}

	};

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The query, which has been used to filter the adapter's data.
	 */
	private final String query;

	/**
	 * The flags, which have been used to filter the adapter's data.
	 */
	private final int flags;

	/**
	 * The filter, which has been used to match the adapter's single items to
	 * the regular expression.
	 */
	private final Filter<DataType> filter;

	/**
	 * Creates a new representation of a filter, which has been applied on an
	 * adapter's underlying data.
	 * 
	 * @param source
	 *            The source, the filter should be created from, as an instance
	 *            of the class {@link Parcel}. The source may not be null
	 */
	@SuppressWarnings("unchecked")
	private AppliedFilter(final Parcel source) {
		this.query = source.readString();
		this.flags = source.readInt();
		this.filter = (Filter<DataType>) source.readSerializable();
	}

	/**
	 * Creates a new representation of a filter, which has been applied on an
	 * adapter's underlying data.
	 * 
	 * @param query
	 *            The query, which has been used to filter the adapter's data,
	 *            as a {@link String}. The query may not be null
	 * @param flags
	 *            The flags, which have been used to filter the adapter's data
	 *            or 0, if no flags have been used
	 */
	public AppliedFilter(final String query, final int flags) {
		this(query, flags, null);
	}

	/**
	 * Creates a new representation of a filter, which has been applied on an
	 * adapter's underlying data.
	 * 
	 * @param query
	 *            The query, which has been used to filter the adapter's data,
	 *            as a {@link String}. The query may not be null
	 * @param flags
	 *            The flags, which have been used to filter the adapter's data
	 *            as an {@link Integer} value or 0, if no flags have been used
	 * @param filter
	 *            The filter, which has been used to match the adapter's single
	 *            items to the regular expression, as an instance of the type
	 *            {@link Filter} or null, if the items' implementations of the
	 *            type {@link Filterable} have been used instead
	 */
	public AppliedFilter(final String query, final int flags,
			final Filter<DataType> filter) {
		ensureNotNull(query, "The query may not be null");
		this.query = query;
		this.flags = flags;
		this.filter = filter;
	}

	/**
	 * Returns the query, which has been used to filter the adapter's data.
	 * 
	 * @return The query, which has been used to filter the adapter's data, as a
	 *         {@link String}. The query may not be null
	 */
	public final String getQuery() {
		return query;
	}

	/**
	 * Returns the flags, which have been used to filter the adapter's data.
	 * 
	 * @return The flags, which have been used to filter the adapter's data, as
	 *         an {@link Integer} value or 0, if no flags have been used
	 */
	public final int getFlags() {
		return flags;
	}

	/**
	 * Returns the filter, which has been used to match the adapter's single
	 * items to the regular expression.
	 * 
	 * @return The filter, which has been used to match the adapter's single
	 *         items to the regular expression, as an instance of the type
	 *         {@link Filter} or null, if the items' implementations of the type
	 *         {@link Filterable} have been used instead
	 */
	public final Filter<DataType> getFilter() {
		return filter;
	}

	@Override
	public final String toString() {
		return "AppliedFilter [query="
				+ query
				+ ", flags="
				+ flags
				+ (filter != null ? ", filter="
						+ ClassUtil.getTruncatedName(filter.getClass()) : "")
				+ "]";
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filter == null) ? 0 : filter.hashCode());
		result = prime * result + flags;
		result = prime * result + query.hashCode();
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
		AppliedFilter<?> other = (AppliedFilter<?>) obj;
		if (filter == null) {
			if (other.filter != null)
				return false;
		} else if (!filter.equals(other.filter))
			return false;
		if (flags != other.flags)
			return false;
		if (!query.equals(other.query))
			return false;
		return true;
	}

	@Override
	public final AppliedFilter<DataType> clone() {
		return new AppliedFilter<DataType>(new String(query), flags, filter);
	}

	@Override
	public final int describeContents() {
		return 0;
	}

	@Override
	public final void writeToParcel(final Parcel dest, final int flags) {
		dest.writeString(query);
		dest.writeInt(flags);
		dest.writeSerializable(filter);
	}

}