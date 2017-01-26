/*
 * Copyright 2014 - 2017 Michael Rapp
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
    GROUPS_AND_CHILDREN

}