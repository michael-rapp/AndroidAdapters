# AndroidAdapters - README

*THIS LIBRARY IS NOT CONSIDERED TO BE STABLE YET. LARGE PARTS OF ITS FUNCTIONALITY ARE NOT WELL TESTED AND THE LIBRARY STILL LACKS A DETAILED DOCUMENTATION.*

[![API-Level](https://img.shields.io/badge/API-14%2B-orange.svg)](https://android-arsenal.com/api?level=14) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) [![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=X75YSLEJV3DWE)

"AndroidAdapters" is an Android-library, which provides feature-rich adapter implementations for providing the underlying data of widgets such as `ListView`, `GridView`, `ExpandableListView` and `RecyclerView`.

The library provides the following features:

- The library provides generic adapter implementations for providing the underlying data of `ListView`, `GridView`, `ExpandableListView` and `RecyclerView` widgets.
- An extensive API for modifying the underlying data of an adapter is provided.
- The adapters provide built-in support for enabling/disabling items. Furthermore, different states can be assigned to individual items.
- A simple API for sorting the items of an adapter is provided by the library.
- The items of all adapters can be easily filtered by applying multiple queries.
- The library supports different selection modes. E.g. adapters, which allow to only select one item at once or adapters, which allow multiple items to be selected at the same time.
- In order to visualize the items of an adapter, the library takes care of inflating views and uses a built-in `ViewHolder` pattern.
- Operations on the adapters can be observed by using a wide variety of listeners and by using Android's logging framework.

## License Agreement

This project is distributed under the Apache License version 2.0. For further information about this license agreement's content please refer to its full version, which is available at http://www.apache.org/licenses/LICENSE-2.0.txt.

Prior to version 0.4.8 this library was distributed under the GNU Lesser General Public License version 3.0 (GLPLv3).

## Download

The latest release of this library can be downloaded as a zip archive from the download section of the project's Github page, which is available [here](https://github.com/michael-rapp/AndroidAdapters/releases). Furthermore, the library's source code is available as a Git repository, which can be cloned using the URL https://github.com/michael-rapp/AndroidAdapters.git.

Alternatively, the library can be added to your Android app as a Gradle dependency by adding the following to the respective module's `build.gradle` file:

```groovy
dependencies {
    implementation 'com.github.michael-rapp:android-adapters:0.12.6'
}
```

## Contact information

For personal feedback or questions feel free to contact me via the mail address, which is mentioned on my [Github profile](https://github.com/michael-rapp). If you have found any bugs or want to post a feature request please use the [bugtracker](https://github.com/michael-rapp/AndroidMaterialViews/issues) to report them.
