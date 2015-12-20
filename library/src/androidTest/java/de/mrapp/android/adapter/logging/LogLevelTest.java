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
package de.mrapp.android.adapter.logging;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Tests the functionality of the class {@link LogLevel}.
 *
 * @author Michael Rapp
 */
public class LogLevelTest extends TestCase {

    /**
     * Tests the functionality of the method, which allows to retrieve the log level's rank, if the
     * log level is ALL.
     */
    public final void testGetRankWhenLogLevelIsAll() {
        assertEquals(0, LogLevel.ALL.getRank());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the log level's rank, if the
     * log level is VERBOSE.
     */
    public final void testGetRankWhenLogLevelIsVerbose() {
        assertEquals(1, LogLevel.VERBOSE.getRank());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the log level's rank, if the
     * log level is DEBUG.
     */
    public final void testGetRankWhenLogLevelIsDebug() {
        assertEquals(2, LogLevel.DEBUG.getRank());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the log level's rank, if the
     * log level is INFO.
     */
    public final void testGetRankWhenLogLevelIsInfo() {
        assertEquals(3, LogLevel.INFO.getRank());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the log level's rank, if the
     * log level is WARN.
     */
    public final void testGetRankWhenLogLevelIsWarn() {
        assertEquals(4, LogLevel.WARN.getRank());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the log level's rank, if the
     * log level is ERROR.
     */
    public final void testGetRankWhenLogLevelIsError() {
        assertEquals(5, LogLevel.ERROR.getRank());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the log level's rank, if the
     * log level is OFF.
     */
    public final void testGetRankWhenLogLevelIsOff() {
        assertEquals(6, LogLevel.OFF.getRank());
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the log level, which belongs
     * to a specific rank, if the log level is ALL.
     */
    public final void testFromRankWhenLogLevelIsAll() {
        assertEquals(LogLevel.ALL, LogLevel.fromRank(0));
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the log level, which belongs
     * to a specific rank, if the log level is VERBOSE.
     */
    public final void testFromRankWhenLogLevelIsVerbose() {
        assertEquals(LogLevel.VERBOSE, LogLevel.fromRank(1));
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the log level, which belongs
     * to a specific rank, if the log level is DEBUG.
     */
    public final void testFromRankWhenLogLevelIsDebug() {
        assertEquals(LogLevel.DEBUG, LogLevel.fromRank(2));
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the log level, which belongs
     * to a specific rank, if the log level is INFO.
     */
    public final void testFromRankWhenLogLevelIsInfo() {
        assertEquals(LogLevel.INFO, LogLevel.fromRank(3));
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the log level, which belongs
     * to a specific rank, if the log level is WARN.
     */
    public final void testFromRankWhenLogLevelIsWarn() {
        assertEquals(LogLevel.WARN, LogLevel.fromRank(4));
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the log level, which belongs
     * to a specific rank, if the log level is ERROR.
     */
    public final void testFromRankWhenLogLevelIsError() {
        assertEquals(LogLevel.ERROR, LogLevel.fromRank(5));
    }

    /**
     * Tests the functionality of the method, which allows to retrieve the log level, which belongs
     * to a specific rank, if the log level is OFF.
     */
    public final void testFromRankWhenLogLevelIsOff() {
        assertEquals(LogLevel.OFF, LogLevel.fromRank(6));
    }

    /**
     * Ensures, that an {@link IllegalArgumentException} is thrown by the method, which allows to
     * retrieve the log level, which belongs to a specific rank, if the rank is invalid.
     */
    public final void testFromRankThrowsException() {
        try {
            LogLevel.fromRank(-1);
            Assert.fail();
        } catch (IllegalArgumentException e) {

        }
    }

}