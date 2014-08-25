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

import de.mrapp.android.adapter.SelectableListDecorator;

/**
 * Defines the interface, a factory, which should allow to create instances of
 * the class {@link SelectableListDecorator} must implement. The decorators,
 * which are created by such factories are used to customize the appearance of
 * the views, which are used to visualize the child items of an
 * {@link ExpandableListAdapter}. These decorators usually are wrappers, which
 * delegate the method calls to an expandable list adapter's actual decorator.
 * 
 * @param <ChildType>
 *            The type of the underlying data of the expandable list adapter's
 *            child items
 *
 * @author Michael Rapp
 * 
 * @since 1.0.0
 */
public interface ChildDecoratorFactory<ChildType> {

	/**
	 * Creates a decorator, which allows to customize the appearance, of the
	 * views, which are used to visualize the child items of an expandable list
	 * adapter.
	 * 
	 * @return The decorator, which has been created, as an instance of the
	 *         class {@link SelectableListDecorator}. The decorator may not be
	 *         null
	 */
	SelectableListDecorator<ChildType> createChildDecorator();

}