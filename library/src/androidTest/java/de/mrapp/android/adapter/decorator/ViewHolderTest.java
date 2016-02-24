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

import de.mrapp.android.adapter.R;

/**
 * Tests the functionality of the class {@link ViewHolder}.
 *
 * @author Michael Rapp
 */
public class ViewHolderTest extends AndroidTestCase {

    /**
     * Tests the functionality of the method, which allows to reference a view, which is not
     * provided by the view holder.
     */
    public final void testGetViewIfViewHolderDoesNotProvideView() {
        View parentView1 = View.inflate(getContext(), R.layout.view, null);
        View parentView2 = View.inflate(getContext(), R.layout.view2, null);
        ViewHolder viewHolder1 = new ViewHolder(parentView1);
        ViewHolder viewHolder2 = new ViewHolder(parentView2);
        View view1 = viewHolder1.getView(R.id.id, 0);
        assertNotNull(view1);
        View view2 = viewHolder2.getView(R.id.id, 1);
        assertNotNull(view2);
        assertNotSame(view1, view2);
    }

    /**
     * Tests the functionality of the method, which allows to reference a view, which is provided by
     * the view holder.
     */
    public final void testGetViewIfViewHolderDoesProvideView() {
        View parentView = View.inflate(getContext(), R.layout.view, null);
        ViewHolder viewHolder = new ViewHolder(parentView);
        View view1 = viewHolder.getView(R.id.id, 0);
        View view2 = viewHolder.getView(R.id.id, 0);
        assertNotNull(view2);
        assertEquals(view1, view2);
    }

}