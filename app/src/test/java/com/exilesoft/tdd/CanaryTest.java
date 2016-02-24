package com.exilesoft.tdd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(manifest = Config.NONE, constants = BuildConfig.class, sdk = 21)
public class CanaryTest {

    @Test
    public void testCanary() {
        assertTrue(true);
    }
}