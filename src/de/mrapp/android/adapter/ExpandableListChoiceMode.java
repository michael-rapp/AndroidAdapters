package de.mrapp.android.adapter;

/**
 * Contains all possible choice modes of an adapter, whose underlying data is
 * managed as a list of arbitrary group and child items. The choice mode
 * specifies what kind of items can be selected.
 * 
 * @author Michael Rapp
 * 
 * @since 0.1.0
 */
public enum ExpandableListChoiceMode {

	/**
	 * If only groups can be selected.
	 */
	GROUPS_ONLY,

	/**
	 * If only children can be selected.
	 */
	CHILDREN_ONLY,

	/**
	 * If groups as well as children can be selected.
	 */
	GROUPS_AND_CHILDREN;

}