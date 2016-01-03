/*
 * AndroidAdapters Copyright 2014 - 2016 Michael Rapp
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
package de.mrapp.android.adapter;

/**
 * Contains all possible orders, which may be used to sort the items of a {@link ListAdapter}.
 *
 * @author Michael Rapp
 * @since 0.1.0
 */
public enum Order {

    /**
     * Causes the items of an adapter to be sorted in an ascending order. Therefore items, which are
     * identified to be greater than other items, will be placed at higher indices.
     */
    ASCENDING,

    /**
     * Causes the items of an adapter to be sorted in a descending order. Therefore the items, which
     * are identified to be greater than other items, will be placed at lower indices.
     */
    DESCENDING

}