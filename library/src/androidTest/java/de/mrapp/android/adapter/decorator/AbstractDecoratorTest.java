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
package de.mrapp.android.adapter.decorator;

import android.test.AndroidTestCase;
import android.view.View;

import junit.framework.Assert;

import de.mrapp.android.adapter.R;

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
        View parentView1 = View.inflate(getContext(), R.layout.view, null);
        abstractDecorator.setCurrentParentView(parentView1);
        View view1 = abstractDecorator.getView(R.id.id);
        assertNotNull(view1);
        View view2 = abstractDecorator.getView(R.id.id);
        assertNotNull(view2);
        assertSame(view1, view2);
        View parentView2 = View.inflate(getContext(), R.layout.view, null);
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