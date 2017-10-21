package org.mockito.configuration;

/**
 * Created by awhite on 21/10/17.
 */

public class MockitoConfiguration extends DefaultMockitoConfiguration {

    @Override
    public boolean enableClassCache() {
        return false;
    }
}
