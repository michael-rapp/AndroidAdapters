/*
 * Copyright 2014 - 2018 Michael Rapp
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
package de.mrapp.android.adapter.logging;

import androidx.annotation.NonNull;

import de.mrapp.android.util.logging.LogLevel;

/**
 * Defines the interface, a class, which should allow to use logging, must implement.
 *
 * @author Michael Rapp
 * @since 0.1.0
 */
public interface Loggable {

    /**
     * Returns the current log level. Only log messages with a rank greater or equal than the rank
     * of the currently applied log level, are written to the output.
     *
     * @return The current log level as a value of the enum LogLevel. The log level may either be
     * <code>ALL</code>, <code>DEBUG</code>, <code>INFO</code>, <code>WARN</code>,
     * <code>ERROR</code> or <code>OFF</code>
     */
    LogLevel getLogLevel();

    /**
     * Sets the log level. Only log messages with a rank greater or equal than the rank of the
     * currently applied log level, are written to the output.
     *
     * @param logLevel
     *         The log level, which should be set, as a value of the enum LogLevel. The log level
     *         may not be null
     */
    void setLogLevel(@NonNull LogLevel logLevel);

}