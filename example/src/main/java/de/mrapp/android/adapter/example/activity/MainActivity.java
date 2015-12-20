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
package de.mrapp.android.adapter.example.activity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import de.mrapp.android.adapter.example.R;
import de.mrapp.android.adapter.example.fragment.AbstractAdapterFragment;
import de.mrapp.android.adapter.example.fragment.ExpandableListAdapterFragment;
import de.mrapp.android.adapter.example.fragment.ListAdapterFragment;
import de.mrapp.android.adapter.example.fragment.MultipleChoiceExpandableListAdapterFragment;
import de.mrapp.android.adapter.example.fragment.MultipleChoiceListAdapterFragment;
import de.mrapp.android.adapter.example.fragment.SingleChoiceExpandableListAdapterFragment;
import de.mrapp.android.adapter.example.fragment.SingleChoiceListAdapterFragment;

/**
 * The example app's main activity.
 *
 * @author Michael Rapp
 */
@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

    /**
     * A listener, which allows to change the activity's current fragment when a tab is selected.
     */
    private class TabListener implements ActionBar.TabListener {

        /**
         * The class of the fragment, which is shown when the tab is selected.
         */
        private Class<? extends Fragment> clazz;

        /**
         * The fragment, which is shown when the tab is selected.
         */
        private Fragment fragment;

        /**
         * Creates a new listener, which allows to change the activity's current fragment when a tab
         * is selected.
         *
         * @param clazz
         *         The class of the fragment, which should be shown when the tab is selected, as an
         *         instance of the class {@link Class}
         */
        public TabListener(final Class<? extends Fragment> clazz) {
            this.clazz = clazz;
            fragment = getFragmentManager().findFragmentByTag(clazz.getSimpleName());
        }

        @Override
        public void onTabSelected(final Tab tab, final FragmentTransaction ft) {
            if (fragment == null) {
                fragment = Fragment.instantiate(MainActivity.this, clazz.getName());
                ft.replace(R.id.fragment_container, fragment, clazz.getSimpleName());
            } else if (fragment.isDetached()) {
                ft.attach(fragment);
            }

            currentFragment = fragment;

            if (searchView != null) {
                searchView.setOnQueryTextListener(null);
                searchView.setQuery(null, false);
                searchView.setOnQueryTextListener(createSearchQueryListener());
            }
        }

        @Override
        public void onTabUnselected(final Tab tab, final FragmentTransaction ft) {
            if (fragment != null) {
                ft.detach(fragment);
            }
        }

        @Override
        public void onTabReselected(final Tab tab, final FragmentTransaction ft) {

        }

    }

    /**
     * The name of the extra, which is used to store the currently selected tab within a bundle.
     */
    private static final String SELECTED_TAB_EXTRA_NAME =
            MainActivity.class.getSimpleName() + "::SelectedTab";

    /**
     * The view, which allows to enter search queries.
     */
    private SearchView searchView;

    /**
     * The current fragment of the activity.
     */
    private Fragment currentFragment;

    /**
     * Handles the intent, which has been passed to the activity, when it was started.
     *
     * @param intent
     *         The intent, which has been passed to the activity, as an instance of the class {@link
     *         Intent}
     */
    private void handleIntent(final Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            search(query);
        }
    }

    /**
     * Creates and returns a listener, which allows to start a search query when the user enters
     * text.
     *
     * @return The listener, which has been created, as an instance of the type {@link
     * OnQueryTextListener}
     */
    private OnQueryTextListener createSearchQueryListener() {
        return new OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                search(newText);
                return true;
            }

        };
    }

    /**
     * Starts a search query.
     *
     * @param query
     *         The query, which should be started, as a {@link String}
     */
    private void search(final String query) {
        AbstractAdapterFragment selectedFragment = ((AbstractAdapterFragment) currentFragment);
        selectedFragment.search(query);
    }

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            Tab listAdapterTab = actionBar.newTab().setText(R.string.list_adapter);
            Tab singleChoiceListAdapterTab =
                    actionBar.newTab().setText(R.string.single_choice_list_adapter);
            Tab multipleChoiceListAdapterTab =
                    actionBar.newTab().setText(R.string.multiple_choice_list_adapter);
            Tab expandapleListAdapterTab =
                    actionBar.newTab().setText(R.string.expandable_list_adapter);
            Tab singleChoiceExpandableListAdapterTab =
                    actionBar.newTab().setText(R.string.single_choice_expandable_list_adapter);
            Tab multipleChoiceExpandableListAdapterTab =
                    actionBar.newTab().setText(R.string.multiple_choice_expandable_list_adapter);

            listAdapterTab.setTabListener(new TabListener(ListAdapterFragment.class));
            singleChoiceListAdapterTab
                    .setTabListener(new TabListener(SingleChoiceListAdapterFragment.class));
            multipleChoiceListAdapterTab
                    .setTabListener(new TabListener(MultipleChoiceListAdapterFragment.class));
            expandapleListAdapterTab
                    .setTabListener(new TabListener(ExpandableListAdapterFragment.class));
            singleChoiceExpandableListAdapterTab.setTabListener(
                    new TabListener(SingleChoiceExpandableListAdapterFragment.class));
            multipleChoiceExpandableListAdapterTab.setTabListener(
                    new TabListener(MultipleChoiceExpandableListAdapterFragment.class));

            actionBar.addTab(listAdapterTab);
            actionBar.addTab(singleChoiceListAdapterTab);
            actionBar.addTab(multipleChoiceListAdapterTab);
            actionBar.addTab(expandapleListAdapterTab);
            actionBar.addTab(singleChoiceExpandableListAdapterTab);
            actionBar.addTab(multipleChoiceExpandableListAdapterTab);
        }
    }

    @Override
    protected final void onResume() {
        super.onResume();
        handleIntent(getIntent());
    }

    @Override
    protected final void onNewIntent(final Intent intent) {
        handleIntent(intent);
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(createSearchQueryListener());

        return true;
    }

    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.preferences_menu_item:
                Intent intent = new Intent(this, PreferenceActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    @Override
    protected final void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            outState.putInt(SELECTED_TAB_EXTRA_NAME, getActionBar().getSelectedTab().getPosition());
        }
    }

    @Override
    protected final void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ActionBar actionBar = getActionBar();

        if (actionBar != null && savedInstanceState.containsKey(SELECTED_TAB_EXTRA_NAME)) {
            int selectedTab = savedInstanceState.getInt(SELECTED_TAB_EXTRA_NAME);
            actionBar.selectTab(getActionBar().getTabAt(selectedTab));
        }
    }

}