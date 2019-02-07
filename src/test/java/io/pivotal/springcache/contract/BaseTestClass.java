package io.pivotal.springcache.contract;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Region;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
@AutoConfigureMockMvc
@AutoConfigureMessageVerifier
@ActiveProfiles("test")
public abstract class BaseTestClass {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

}
