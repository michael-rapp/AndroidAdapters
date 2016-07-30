# AndroidAdapters - RELEASE NOTES

## Version 0.6.0 (Jul. 31th 2016)

A feature release, which introduces the following changes:

- The `SingleChoiceListAdapter`'s `select`-method has been replaced with the `triggerSelection`-method. It causes an item to become unselected, if it is currently selected. 

## Version 0.5.0 (Jun. 15th 2016)

A feature release, which introduces the following changes:

- Getter and setter methods, which allow to retrieving or change an adapter's decorator, are now publicly exposed. This required to introduce the interfaces `NoChoiceListAdapter` and `NoChoiceExpandableListAdapter`, which are now used as return types by the factory `AdapterFactory`.
- Added the view `ExpandableGridView`, which can be used together with an `NoChoiceExpandableListAdapter`, `SingleChoiceExpandableListAdapter` or `MultipleChoiceExpandableListAdapter`.

## Version 0.4.12 (Jun. 2nd 2016)

A bugfix release, which fixes the following issues:

- The `indexOfGroup`-method of the class `AbstractExpandableListAdapter` does now use the `equals`-method to compare groups instead of the `==` operator.
- Updated dependency "AndroidUtil" to version 1.4.11.
- Updated AppCompat annotation support library to version 23.4.0.
- Updated RecyclerView support library to version 23.4.0.

## Version 0.4.11 (Mar. 19th 2016)

A minor release, which introduces the following changes:

- The collection `CopyOnWriteArraySet` is now used to manage listeners.

## Version 0.4.10 (Mar. 2nd 2016)

A bugfix release, which fixes the following issues:

- All properties of an `ExpandableListAdapter` are now properly stored and restored when calling its `onSaveInstanceState`- or `onRestoreInstanceState`-method.
- The child order of an `ExpandableListAdapter` is now properly updated when sorting its children using a method, which expects a `Comparator` as a parameter.

## Version 0.4.9 (Feb. 29th 2016)

A bugfix release, which fixes the following issues:

- The return value of an ExpandableListAdapter's `isChildSelectable`-method does now only depend on whether the child item is currently enabled, or not.

## Version 0.4.8 (Feb. 24th 2016)

A minor release, which introduces the following changes:

- The library is from now on distributed under the Apache License version 2.0. 
- Updated the dependency "AndroidUtil" to version 1.4.3.

## Version 0.4.7 (Feb. 1st 2016)

A bugfix release, which fixes the following issues:

- Possible `ConcurrentModificationException` when resetting the filters of an `FilterableExpandableListAdapter` has been fixed. 

## Version 0.4.6 (Jan. 29th 2016)

A bugfix release, which fixes the following issues:

- When resetting group filters, the adapter view is not synchronized anymore, if the `isNotifiedOnChange`-method returns false.

## Version 0.4.5 (Jan. 29th 2016)

A bugfix release, which fixes the following issues:

- Fixed a bug, which prevented the child filters, which are applied on all groups except of the first one, from being reset, when resetting all child filters while empty groups are filtered.

## Version 0.4.4 (Jan. 28th 2016)

A bugfix release, which fixes the following issues:

- Fixed possible `NullPointerException` when the `notifyOnDataSetChanged`-method of an `ExpandableListAdapter` is called internally.
- The adapter view is now synchronized with the adapter's underlying data when the public `notifyDataSetChanged`-method of a `ExpandableListAdapter` is called.

## Version 0.4.3 (Jan. 28th 2016)

A bugfix release, which fixes the following issues:

- When setting the expansion state of an `ExpandableListAdapter`'s group, the adapter view is not updated anymore, when the `isNotifiedOnChange`-method returns `false`. Instead, it is updated as soon as the `notifyDataSetChanged`-method is called.  

## Version 0.4.2 (Jan. 27th 2016)
 
A bugfix release, which fixes the following issues:

- Fixed 'IndexOfBoundsException', which was thrown when long-clicking a footer of an 'ExpandableListView'.
- The correct index is now passed to the listener `ExpandableListAdapterItemClickListener` or `ExpandableListAdapterItemLongClickListener` when a footer has been clicked, respectively long-clicked.

## Version 0.4.1 (Jan. 26th 2016)

A bugfix release, which fixes the following issues:

- Possible `IndexOutOfBoundException` is now avoided when adding header or footer views to an adapter view.
- The listeners `ListAdapterItemLongClickListener` and `ExpandableListAdapterItemLongClickListener` do now provide methods, which allow to observe if header or footer views are long-clicked by the user.

## Version 0.4.0 (Jan. 26th 2016)

A feature release, which introduces the following changes:

- Adding header and footer views to adapter views is now supported. The listeners `ListAdapterItemClickListener` and `ExpandableListAdapterItemClickListener` do now provide methods, which allow to observe if header or footer views are clicked by the user.

## Version 0.3.2 (Jan. 12th 2016)

A bugfix release, which fixes the following issues:

- The filters, which are used to filter empty groups are now properly reseted when resetting child filters.

## Version 0.3.1 (Jan. 12th 2016)

A bugfix release, which fixes the following issues:

- The `resetChildFilter`-methods of an expandable list adapter do now work correctly when empty groups are currently filtered. 
- A method, which allows to set the selection of all children, regardless of the group they belong to, has been added to the `MultipleChoiceExpandableListAdapter`.

## Version 0.3.0 (Jan. 10th 2016)

A feature release, which introduces the following changes:

- The expansion states of an `ExpandableListAdapter`'s groups are now stored separately from the view, the adapter is attached to. This enables to attach the adapter to an other view while maintaining the expansion states and to restore the expansion states when reseting a group filter. In order to implement this feature, the API for expanding/collapsing groups has been slightly changed.
- Child items of an `SingleChoiceExpandableListAdapter` or `MultipleChoiceExpandaleListAdapter` are now properly unselected when filtering the group they belong to. In order to implement this bugfix, the API for applying filters has been slightly changed.

## Version 0.2.6 (Jan. 7th 2016)

A bugfix release, which fixes the following issues:

- Fixed possible `IndexOutOfBoundsException` when resetting filters, which have been applied on an `ExpandableListAdapter`'s child items.

## Version 0.2.5 (Jan. 7th 2016)

A bugfix release, which fixes the following issues:

- When filtering an empty group, no `FilteringNotSupportedException` is thrown anymore, even if the group's data does not implement the interface `Filterable`.

## Version 0.2.4 (Jan. 6th 2016)

A bugfix release, which fixes the following issues:

- Possible `ClassCastException` when writing a `Group`, whose data does not implement the interface `Parcelable`, to a `Parcel`.
- Restoring the scroll position of adapter views has been fixed.
- Fixed restoring attributes in the `onRestoreInstanceState`-method of the class `AbstractExpandableListAdapter`.

## Version 0.2.3 (Jan. 6th 2016)

A bugfix release, which fixes the following issues:

- Click listeners and long-click listeners are now registered at adapter views. This enables the adapter views to highlight items when pressed. It is now mandatory to attach adapters to views using their `attach`-methods. 
- The view state of an `SelectableExpandableListAdapter`'s group and child items are now correctly adapted depending on the items' selection.
- Storing and restoring the state of expandable list adapters has been fixed.
- Restoring the scroll positions of adapter views has been improved.

## Version 0.2.2 (Jan. 5th 2016)

A bugfix release, which fixes the following issues:

- Groups items, which do not implement the `Serializable` or `Parcelable` interface do not cause crashes anymore, when the `onSaveInstanceState`-method is called.

## Version 0.2.1 (Jan. 3rd 2016)

A minor release, which introduces the following changes:

- Updated dependency "AndroidUtil" to version 1.3.0.

## Version 0.2.0 (Dec. 30th 2015)

A feature release, which introduces the following changes:

- Decorators are now responsible for inflating views. This allows to inflate different layouts depending on different view types.

## Version 0.1.2 (Dec. 29th 2015)

A bugfix release, which fixes the following issues:

- https://github.com/michael-rapp/AndroidAdapters/issues/2

## Version 0.1.1 (Dec. 28th 2015)

A bugfix release, which fixes the following issues:

- https://github.com/michael-rapp/AndroidAdapters/issues/1
- Updated library versions.

## Version 0.1.0 (Dec. 22th 2015)

A first unstable release of the library, which provides feature-rich adapter implementations for providing the underlying data of widgets such as `ListView`, `GridView`, `ExpandableListView` and `RecyclerView`. The implementation initially provides the following features:

- The library provides generic adapter implementations for providing the underlying data of `ListView`, `GridView`, `ExpandableListView` and `RecyclerView` widgets.
- An extensive API for modifying the underlying data of an adapter is provided.
- The adapters provide built-in support for enabling/disabling items. Furthermore, different states can be assigned to individual items.
- A simple API for sorting the items of an adapter is provided by the library.
- The items of all adapters can be easily filtered by applying multiple queries.
- The library supports different selection modes. E.g. adapters, which allow to only select one item at once or adapters, which allow multiple items to be selected at the same time.
- In order to visualize the items of an adapter, the library takes care of inflating views and uses a built-in `ViewHolder` pattern.
- Operations on the adapters can be observed by using a wide variety of listeners and by using Android's logging framework.