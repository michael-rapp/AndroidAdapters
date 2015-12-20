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
package de.mrapp.android.adapter.expandablelist;

import android.test.AndroidTestCase;
import android.view.View;

import de.mrapp.android.adapter.list.selectable.SelectableListAdapter;

import static org.mockito.Mockito.mock;

/**
 * Tests the functionality of the class {@link NullObjectDecorator}.
 *
 * @author Michael Rapp
 */
public class NullObjectDecoratorTest extends AndroidTestCase {

    /**
     * Tests the functionality of the applyDecorator-method.
     */
    @SuppressWarnings("unchecked")
    public final void testApplyDecorator() {
        NullObjectDecorator<Object> nullObjectDecorator = new NullObjectDecorator<>();
        nullObjectDecorator.applyDecorator(getContext(), mock(SelectableListAdapter.class),
                new View(getContext()), new Object(), 0, true, 0, false, false);
    }

}