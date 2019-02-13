package io.pivotal.springcache;

import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.SocketUtils;

@ContextConfiguration
public class ApplicationTestContextInitializer extends ConfigFileApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        super.initialize(applicationContext);
        int port = SocketUtils.findAvailableTcpPort();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        TestPropertyValues.of("wiremock.dynamic.port=" + port).applyTo(environment);

        String urlProperty = environment.getProperty("integration.offers.base-url");
        if (urlProperty != null) {
            urlProperty = urlProperty.replaceAll("localhost", "localhost:"+port);
            TestPropertyValues.of("integration.offers.base-url=" + urlProperty).applyTo(environment);
        }
    }

}
