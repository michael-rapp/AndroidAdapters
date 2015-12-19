/*
 * AndroidAdapters Copyright 2014 - 2015 Michael Rapp
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package de.mrapp.android.adapter.expandablelist.selectable;

/**
 * Contains all possible choice modes of an adapter, whose underlying data is managed as a list of
 * arbitrary group and child items. The choice mode specifies what kind of items can be selected.
 *
 * @author Michael Rapp
 * @since 0.1.0
 */
public enum ChoiceMode {

    /**
     * If only groups can be selected.
     */
    GROUPS_ONLY,

    /**
     * If only children can be selected.
     */
    CHILDREN_ONLY,

    /**
     * If groups as well as children can be selected.
     */
    GROUPS_AND_CHILDREN;

}