/*
 * Copyright 2014 - 2016 Michael Rapp
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package de.mrapp.android.adapter.expandablelist;

import de.mrapp.android.adapter.ExpandableListAdapter;
import de.mrapp.android.adapter.SelectableListDecorator;

/**
 * Defines the interface, a factory, which should allow to create instances of the class {@link
 * SelectableListDecorator} must implement. The decorators, which are created by such factories are
 * used to customize the appearance of the views, which are used to visualize the child items of an
 * {@link ExpandableListAdapter}. These decorators usually are wrappers, which delegate the method
 * calls to an expandable list adapter's actual decorator.
 *
 * @param <ChildType>
 *         The type of the underlying data of the expandable list adapter's child items
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface DecoratorFactory<ChildType> {

    /**
     * Creates a decorator, which allows to customize the appearance, of the views, which are used
     * to visualize the child items of an expandable list adapter.
     *
     * @return The decorator, which has been created, as an instance of the class {@link
     * SelectableListDecorator}. The decorator may not be null
     */
    SelectableListDecorator<ChildType> createDecorator();

}