# Third-Party Notices

This project (Pixabay Android App) is licensed under the MIT License (see [LICENSE](LICENSE)).
It also uses the following third-party libraries, services, and content. Each is the property
of its respective owner and is subject to its own license terms, reproduced/linked below.

## Libraries

| Library | License | Source |
|---|---|---|
| AndroidX AppCompat | Apache License 2.0 | https://developer.android.com/jetpack/androidx/releases/appcompat |
| Material Components for Android | Apache License 2.0 | https://github.com/material-components/material-components-android |
| AndroidX Activity | Apache License 2.0 | https://developer.android.com/jetpack/androidx/releases/activity |
| AndroidX ConstraintLayout | Apache License 2.0 | https://developer.android.com/jetpack/androidx/releases/constraintlayout |
| AndroidX SwipeRefreshLayout | Apache License 2.0 | https://developer.android.com/jetpack/androidx/releases/swiperefreshlayout |
| Android Volley | Apache License 2.0 | https://github.com/google/volley |
| Gson | Apache License 2.0 | https://github.com/google/gson |
| Glide | BSD, part MIT, part Apache License 2.0 | https://github.com/bumptech/glide |
| CircleImageView | Apache License 2.0 | https://github.com/hdodenhof/CircleImageView |
| Zoomy | Apache License 2.0 | https://github.com/imablanco/Zoomy |
| Flexbox Layout | Apache License 2.0 | https://github.com/google/flexbox-layout |
| Firebase Analytics (Firebase Android BoM) | Apache License 2.0 (SDK); usage subject to Google's terms below | https://firebase.google.com/docs/analytics |
| JUnit (test) | Eclipse Public License 1.0 | https://junit.org/junit4/ |
| AndroidX Test / Espresso (test) | Apache License 2.0 | https://developer.android.com/training/testing |

Most of the above are distributed under the **Apache License, Version 2.0**. A copy of that
license can be found at: https://www.apache.org/licenses/LICENSE-2.0

## Google / Firebase Services

This app integrates **Firebase Analytics (Google Analytics for Firebase)**, a Google-provided
SDK and service. Use of this SDK is subject to:

- [Google APIs Terms of Service](https://developers.google.com/terms)
- [Firebase Terms of Service](https://firebase.google.com/terms)
- [Google Privacy Policy](https://policies.google.com/privacy)

`app/google-services.json` contains the public client configuration for this integration
(Firebase project ID, mobile SDK app ID, and an API key restricted to this app's package name).
See the [Privacy & Analytics](README.md#-privacy--analytics) section of the README for details.

## Content / Pixabay API

Images, videos, and associated metadata (titles, tags, uploader names, etc.) fetched and
displayed by this app come from the [Pixabay API](https://pixabay.com/api/docs/) and remain the
property of their original Pixabay contributors. Use of this content is governed by the
[Pixabay Content License](https://pixabay.com/service/license-summary/), not by this project's
MIT license. This app itself is an independent client and is not affiliated with or endorsed by
Pixabay.

## App Icons / Drawable Assets

Custom vector icons (`ic_*.xml`) and launcher icons under `app/src/main/res/` were created for
this project. If any icon was adapted from a third-party icon set, ensure that set's license
permits redistribution and add an entry here before publishing.
