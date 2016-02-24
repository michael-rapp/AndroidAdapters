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