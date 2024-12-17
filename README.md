# Mock Location example

## How to run

Build the app and the test app:
```bash
./gradlew :app:assembleDebug :app:assembleDebugAndroidTest
```

Run the tests on Marathon Cloud using `--mock-location` option that allows to grant mock location permission to the app:
```bash
marathon-cloud run android \
  --api-key <your_api_key> \
  --name mock_location_test \
  -a app/build/outputs/apk/debug/app-debug.apk \
  -t app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk \
  --system-image google_apis \
  --mock-location
```

## Main anchors

1. `MockLocationProvider`
2. `GeoLocationUITest`