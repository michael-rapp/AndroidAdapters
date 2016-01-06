/*
 * AndroidAdapters Copyright 2014 - 2016 Michael Rapp
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
package de.mrapp.android.adapter.util;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListView;

import static de.mrapp.android.util.Condition.ensureNotEmpty;
import static de.mrapp.android.util.Condition.ensureNotNull;

/**
 * An utility class, which provides static methods, which allow to store the scroll position of
 * adapter views such as {@link ListView}, {@link GridView}, {@link RecyclerView} and {@link
 * ExpandableListView} and restore it afterwards.
 *
 * @author Michael Rapp
 * @since 0.2.3
 */
public final class AdapterViewUtil {

    /**
     * The key, which is used to store the view state of an adapter view within a bundle.
     */
    private static final String VIEW_STATE_BUNDLE_KEY =
            AdapterViewUtil.class.getSimpleName() + "::ViewState";

    /**
     * The key, which is used to store the first visible index of an adapter view within a bundle.
     */
    private static final String FIRST_VISIBLE_INDEX_BUNDLE_KEY =
            AdapterViewUtil.class.getSimpleName() + "::FirstVisibleIndex";

    /**
     * The key, which is used to store the offset of the first visible index of an adapter view
     * within a bundle.
     */
    private static final String OFFSET_BUNDLE_KEY =
            AdapterViewUtil.class.getSimpleName() + "::Offset";

    /**
     * Scrolls a specific expandable list view to the item at a specific index.
     *
     * @param expandableListView
     *         The expandable list view, which should be scrolled, as an instance of the class
     *         {@link ExpandableListView}. The expandable list view may not be null
     * @param index
     *         The index of the item, the expandable list view should be scrolled to, as an {@link
     *         Integer} value
     * @param offset
     *         The index of the item, the expandable list view should be scrolled to, as an {@link
     *         Integer} value
     */
    private static void scrollTo(@NonNull final ExpandableListView expandableListView,
                                 final int index, final int offset) {
        expandableListView.setSelectionFromTop(index, offset);
    }

    /**
     * Scrolls a specific list view to the item at a specific index.
     *
     * @param listView
     *         The list view, which should be scrolled, as an instance of the class {@link
     *         ListView}. The list view may not be null
     * @param index
     *         The index of the item, the list view should be scrolled to, as an {@link Integer}
     *         value
     * @param offset
     *         The offset of the item, the list view should be scrolled to, as an {@link Integer}
     *         value
     */
    private static void scrollTo(@NonNull final ListView listView, final int index,
                                 final int offset) {
        listView.setSelectionFromTop(index, offset);
    }

    /**
     * Scrolls a specific grid view to the item at a specific index.
     *
     * @param gridView
     *         The grid view, which should be scrolled, as an instance of the class {@link
     *         GridView}. The grid view may not be null
     * @param index
     *         The index of the item, the grid view should be scrolled to, as an {@link Integer}
     *         value
     * @param offset
     *         The offset of the item, the grid view should be scrolled to, as an {@link Integer}
     *         value
     */
    private static void scrollTo(@NonNull final GridView gridView, final int index,
                                 final int offset) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            gridView.setSelectionFromTop(index, offset);
        } else {
            gridView.setSelection(index);
        }
    }

    /**
     * Creates a new utility class, which provides static methods, which allow to store the scroll
     * position of adapter views such as {@link ListView}, {@link GridView}, {@link RecyclerView}
     * and {@link ExpandableListView} and restore it afterwards.
     */
    private AdapterViewUtil() {

    }

    /**
     * Saves the state of an adapter view within a specific bundle.
     *
     * @param adapterView
     *         The adapter view, whose state should be stored, as an instance of the class {@link
     *         AbsListView}. The adapter view may not be null
     * @param outState
     *         The bundle, the state should be stored within, as an instance of the class {@link
     *         Bundle}. The bundle may not be null
     * @param key
     *         The key, which should be used to store the state, as a {@link String}. The key may
     *         neither be null, nor empty
     */
    public static void onSaveInstanceState(@NonNull final AbsListView adapterView,
                                           @NonNull final Bundle outState,
                                           @NonNull final String key) {
        ensureNotNull(adapterView, "The adapter view may not be null");
        ensureNotNull(outState, "The bundle may not be null");
        ensureNotNull(key, "The key may not be null");
        ensureNotEmpty(key, "The key may not be empty");
        Parcelable viewState = adapterView.onSaveInstanceState();
        int firstVisibleIndex = adapterView.getFirstVisiblePosition();
        View firstVisibleView = adapterView.getChildAt(0);
        int offset = firstVisibleView == null ? 0 :
                firstVisibleView.getTop() - adapterView.getPaddingTop();
        Bundle savedState = new Bundle();
        savedState.putParcelable(VIEW_STATE_BUNDLE_KEY, viewState);
        savedState.putInt(FIRST_VISIBLE_INDEX_BUNDLE_KEY, firstVisibleIndex);
        savedState.putInt(OFFSET_BUNDLE_KEY, offset);
        outState.putBundle(key, savedState);
    }

    /**
     * Restores the previously stored state of an adapter view from a specific bundle.
     *
     * @param adapterView
     *         The adapter view, whose state should be restored, as an instance of the class {@link
     *         AbsListView}. The adapter view may not be null
     * @param savedInstanceState
     *         The bundle, the state has been stored within, as an instance of the class {@link
     *         Bundle}. The bundle may not be null
     * @param key
     *         The key, which has been used to store the state, as a {@link String}. The key may
     *         neither be null, nor empty
     */
    public static void onRestoreInstanceState(@NonNull final AbsListView adapterView,
                                              @NonNull final Bundle savedInstanceState,
                                              @NonNull final String key) {
        ensureNotNull(adapterView, "The adapter view may not be null");
        ensureNotNull(savedInstanceState, "The bundle may not be null");
        ensureNotNull(key, "The key may not be null");
        ensureNotEmpty(key, "key may not be empty");
        Bundle savedState = savedInstanceState.getBundle(key);

        if (savedState != null) {
            Parcelable viewState = savedState.getParcelable(VIEW_STATE_BUNDLE_KEY);
            int firstVisibleIndex = savedState.getInt(FIRST_VISIBLE_INDEX_BUNDLE_KEY, -1);
            int offset = savedInstanceState.getInt(OFFSET_BUNDLE_KEY, -1);

            if (viewState != null) {
                adapterView.onRestoreInstanceState(viewState);
            }

            if (firstVisibleIndex != -1 && offset != -1) {
                if (adapterView instanceof ExpandableListView) {
                    scrollTo((ExpandableListView) adapterView, firstVisibleIndex, offset);
                } else if (adapterView instanceof ListView) {
                    scrollTo((ListView) adapterView, firstVisibleIndex, offset);
                } else if (adapterView instanceof GridView) {
                    scrollTo((GridView) adapterView, firstVisibleIndex, offset);
                }
            }
        }
    }

}