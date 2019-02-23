/*
 * Copyright 2014 - 2019 Michael Rapp
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
package de.mrapp.android.adapter.datastructure;

import java.io.Serializable;

/**
 * Defines the interface, all classes, which represent a data structure, must implement. Such data
 * structures therefore must implement the interface {@link Serializable} to support serialization,
 * as well as the interface {@link Cloneable}, to allow deep copies of class instances.
 *
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface DataStructure extends Serializable, Cloneable {

    /**
     * Creates and returns a deep copy of the object.
     *
     * @return A deep copy of the object as an instance of the class {@link Object}
     * @throws CloneNotSupportedException
     *         The exception, which is thrown, if cloning is not supported
     */
    DataStructure clone() throws CloneNotSupportedException;

    @Override
    String toString();

    @Override
    int hashCode();

    @Override
    boolean equals(Object object);

}