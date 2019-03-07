package integration.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import integration.core.CachingFeature;
import integration.core.RestApiFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration
@ActiveProfiles("int")
public class StoreSteps {

    @Autowired
    private RestApiFeature restApiFeature;

    @Autowired
    private CachingFeature cachingFeature;

    @When("^the client calls /store$")
    public void the_client_issues_GET_participant() {
        restApiFeature.getStore();
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int status) throws IOException {
        assertThat(restApiFeature.getLastResponse().getResponse().getStatusCode().value()).isEqualTo(status);
    }

    @And("^the client receives \"([^\"]*)\" with \"([^\"]*)\" and a value of \"([^\"]*)\"$")
    public void the_client_receives_participant_attribute(String attributeName, String propertyName, String propertyValue) {
        ObjectNode bodyJsonObject = restApiFeature.getLastResponse().getBodyJsonObject();
        assertThat(bodyJsonObject.has(attributeName)).isTrue();
        JsonNode attributeJsonNode = bodyJsonObject.get(attributeName);
        if (attributeJsonNode.isObject()) {
            JsonNode valueJsonNode = attributeJsonNode.findValue(propertyName);
            assertThat(valueJsonNode).isNotNull();
            assertThat(valueJsonNode.asText()).isEqualTo(propertyValue);
        } else if (attributeJsonNode.isArray()) {
            assertThat(attributeJsonNode.findValuesAsText(propertyName)).contains(propertyValue);
        }
    }

    @And("^the participant's products is cached$")
    public void the_participant_products_resource_cached() {
        assertThat(cachingFeature.getCacheValue("products", "all")).isNotEmpty();
    }

    @And("^the participant's \"([^\"]*)\" is cached in \"([^\"]*)\"$")
    public void the_participant_resource_cached(String attributeName, String regionName) {
        assertThat(cachingFeature.getCacheValue(regionName, attributeName)).isNotEmpty();
    }

}
