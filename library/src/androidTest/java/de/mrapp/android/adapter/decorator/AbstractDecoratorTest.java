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
package de.mrapp.android.adapter.decorator;

import android.test.AndroidTestCase;
import android.view.View;

import junit.framework.Assert;

import de.mrapp.android.adapter.R;
import de.mrapp.android.adapter.inflater.InflaterFactory;

/**
 * Tests the functionality of the class {@link AbstractDecorator}.
 *
 * @author Michael Rapp
 */
public class AbstractDecoratorTest extends AndroidTestCase {

    /**
     * An implementation of the abstract class {@link AbstractDecorator}, which is needed for test
     * purposes.
     */
    private class AbstractDecoratorImplementation extends AbstractDecorator {

    }

    /**
     * Tests the functionality of the method, which allows to reference a view by using the view
     * holder pattern.
     */
    public final void testGetView() {
        AbstractDecorator abstractDecorator = new AbstractDecoratorImplementation();
        View parentView1 =
                InflaterFactory.createInflater(R.layout.view).inflate(getContext(), null, false);
        abstractDecorator.setCurrentParentView(parentView1);
        View view1 = abstractDecorator.getView(R.id.id);
        assertNotNull(view1);
        View view2 = abstractDecorator.getView(R.id.id);
        assertNotNull(view2);
        assertSame(view1, view2);
        View parentView2 =
                InflaterFactory.createInflater(R.layout.view).inflate(getContext(), null, false);
        abstractDecorator.setCurrentParentView(parentView2);
        View view3 = abstractDecorator.getView(R.id.id);
        assertNotNull(view3);
        View view4 = abstractDecorator.getView(R.id.id);
        assertNotNull(view4);
        assertSame(view3, view4);
        assertNotSame(view1, view3);
        assertNotSame(view1, view4);
        assertNotSame(view2, view3);
        assertNotSame(view2, view4);
    }

    /**
     * Ensures, that a {@link NullPointerException} is thrown, if the current parent view is set to
     * null.
     */
    public final void testSetCurrentParentViewThrowsException() {
        try {
            AbstractDecorator abstractDecorator = new AbstractDecoratorImplementation();
            abstractDecorator.setCurrentParentView(null);
            Assert.fail();
        } catch (NullPointerException e) {

        }
    }

}