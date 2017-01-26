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
 * An exception, which is thrown, if the items of an adapter should be filtered, but the underlying
 * data does not implement the interface {@link Filterable}.
 *
 * @author Michael Rapp
 * @since 0.1.0
 */
public class FilteringNotSupportedException extends RuntimeException {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

}