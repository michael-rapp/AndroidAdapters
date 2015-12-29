# AndroidAdapters - RELEASE NOTES

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