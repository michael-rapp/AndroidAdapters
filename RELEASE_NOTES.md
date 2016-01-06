# AndroidAdapters - RELEASE NOTES

## Version 0.2.5 (Jan. 7th 2015)

A bugfix release, which fixes the following issues:

- When filtering an empty group, no `FilteringNotSupportedException` is thrown anymore, even if the group's data does not implement the interface `Filterable`.

## Version 0.2.4 (Jan. 6th 2015)

A bugfix release, which fixes the following issues:

- Possible `ClassCastException` when writing a `Group`, whose data does not implement the interface `Parcelable`, to a `Parcel`.
- Restoring the scroll position of adapter views has been fixed.
- Fixed restoring attributes in the `onRestoreInstanceState`-method of the class `AbstractExpandableListAdapter`.

## Version 0.2.3 (Jan. 6th 2015)

A bugfix release, which fixes the following issues:

- Click listeners and long-click listeners are now registered at adapter views. This enables the adapter views to highlight items when pressed. It is now mandatory to attach adapters to views using their `attach`-methods. 
- The view state of an `SelectableExpandableListAdapter`'s group and child items are now correctly adapted depending on the items' selection.
- Storing and restoring the state of expandable list adapters has been fixed.
- Restoring the scroll positions of adapter views has been improved.

## Version 0.2.2 (Jan. 5th 2015)

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