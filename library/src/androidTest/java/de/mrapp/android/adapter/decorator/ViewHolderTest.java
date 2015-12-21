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

import de.mrapp.android.adapter.R;
import de.mrapp.android.adapter.inflater.InflaterFactory;

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
        View parentView1 =
                InflaterFactory.createInflater(R.layout.view).inflate(getContext(), null, false);
        View parentView2 =
                InflaterFactory.createInflater(R.layout.view2).inflate(getContext(), null, false);
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
        View parentView =
                InflaterFactory.createInflater(R.layout.view).inflate(getContext(), null, false);
        ViewHolder viewHolder = new ViewHolder(parentView);
        View view1 = viewHolder.getView(R.id.id, 0);
        View view2 = viewHolder.getView(R.id.id, 0);
        assertNotNull(view2);
        assertEquals(view1, view2);
    }

}