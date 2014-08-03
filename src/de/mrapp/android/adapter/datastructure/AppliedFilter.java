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

import java.util.regex.Pattern;

import de.mrapp.android.adapter.list.filterable.Filter;
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
 * @since 1.0.0
 */
public class AppliedFilter<DataType> implements DataStructure {

	/**
	 * The constant serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The regular expression, which has been used to filter the adapter's data.
	 */
	private final Pattern regularExpression;

	/**
	 * The filter, which has been used to match the adapter's single items to
	 * the regular expression.
	 */
	private final transient Filter<DataType> filter;

	/**
	 * Creates a new representation of a filter, which has been applied on an
	 * adapter's underlying data.
	 * 
	 * @param regularExpression
	 *            The regular expression, which has been used to filter the
	 *            adapter's data, as an instance of the class {@link Pattern}.
	 *            The regular expression may not be null
	 */
	public AppliedFilter(final Pattern regularExpression) {
		this(regularExpression, null);
	}

	/**
	 * Creates a new representation of a filter, which has been applied on an
	 * adapter's underlying data.
	 * 
	 * @param regularExpression
	 *            The regular expression, which has been used to filter the
	 *            adapter's data, as an instance of the class {@link Pattern}.
	 *            The regular expression may not be null
	 * @param filter
	 *            The filter, which has been used to match the adapter's single
	 *            items to the regular expression, as an instance of the type
	 *            {@link Filter} or null, if the items' implementations of the
	 *            type {@link Filterable} have been used instead
	 */
	public AppliedFilter(final Pattern regularExpression,
			final Filter<DataType> filter) {
		ensureNotNull(regularExpression,
				"The regular expression may not be null");
		this.regularExpression = regularExpression;
		this.filter = filter;
	}

	/**
	 * Returns the regular expression, which has been used to filter the
	 * adapter's data.
	 * 
	 * @return The regular expression, which has been used to filter the
	 *         adapter's data, as an instance of the class {@link Pattern}. The
	 *         regular expression may not be null
	 */
	public final Pattern getRegularExpression() {
		return regularExpression;
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
		return "AppliedFilter [regularExpression=" + regularExpression + "]";
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + regularExpression.pattern().hashCode();
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
		if (!regularExpression.pattern().equals(
				other.regularExpression.pattern()))
			return false;
		return true;
	}

	@Override
	public final AppliedFilter<DataType> clone() {
		return new AppliedFilter<DataType>(Pattern.compile(regularExpression
				.pattern()), filter);
	}

}