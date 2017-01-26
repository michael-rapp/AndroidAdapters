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