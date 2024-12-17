package io.marathonlabs.location.mock

import android.location.LocationManager
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.rule.GrantPermissionRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GeoLocationUITest {

    // Rule for setting up the Compose testing environment
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    // Rule for granting the location permission
    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
    )

    private lateinit var mockLocationProvider: MockLocationProvider
    private val providerName = LocationManager.GPS_PROVIDER

    @Before
    fun setup() {
        mockLocationProvider = MockLocationProvider(composeTestRule.activity)
        mockLocationProvider.enableMockProvider(providerName)
        // Set a mock location before the test starts
        mockLocationProvider.setMockLocation(providerName, 22.421998, -22.084000)
    }

    @After
    fun teardown() {
        mockLocationProvider.disableMockProvider(providerName)
    }

    @Test
    fun fetchLocation_andCheckCoordinates() {
        // Define expected location
        val expectedLatitudeStart = "22.42"
        val expectedLongitudeStart = "-22.08"

        // Verify the initial UI state
        composeTestRule.onNodeWithText("Fetching location...").assertIsDisplayed()

        // Click the "Fetch Location" button
        composeTestRule.onNodeWithText("Fetch Location").performClick()

        // Assert that the latitude and longitude start with the expected values
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText("Lat: $expectedLatitudeStart", substring = true)
                .fetchSemanticsNodes().isNotEmpty() &&
                    composeTestRule.onAllNodesWithText("Lng: $expectedLongitudeStart", substring = true)
                        .fetchSemanticsNodes().isNotEmpty()
        }

        Thread.sleep(5_000)

        composeTestRule.onNodeWithText("Lat: $expectedLatitudeStart", substring = true)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Lng: $expectedLongitudeStart", substring = true)
            .assertIsDisplayed()
    }
}