package integration.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import integration.core.CachingFeature;
import integration.core.RestApiFeature;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest
@ContextConfiguration
@ActiveProfiles("int")
public class StoreSteps {

    @Autowired
    private RestApiFeature restApiFeature;

    @Autowired
    private Optional<CachingFeature> cachingFeature;

    @Value("feature.toggle.offers-enabled")
    private String offersEnabled;

    @When("^the client calls /store$")
    public void the_client_issues_GET_participant() {
        restApiFeature.getStore();
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int status) throws IOException {
        assertThat(restApiFeature.getLastResponse().getResponse().getStatusCode().value()).isEqualTo(status);
    }

    @And("^the client receives \"([^\"]*)\" with \"([^\"]*)\" and a value of \"([^\"]*)\"$")
    public void the_client_receives_store_attribute(String attributeName, String propertyName, String propertyValue) {
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

    @And("^the client receives \"([^\"]*)\" with \"([^\"]*)\" and a value of \"([^\"]*)\" when offers is \"enabled\"$")
    public void the_client_receives_offer_attribute(String attributeName, String propertyName, String propertyValue) {

        if (!BooleanUtils.toBoolean(this.offersEnabled)) {
            return;
        }

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
        if (cachingFeature.isPresent()) {
            assertThat(cachingFeature.get().getCacheValue("products", "all")).isNotEmpty();
        }
    }

    @And("^the participant's \"([^\"]*)\" is cached in \"([^\"]*)\"$")
    public void the_participant_resource_cached(String attributeName, String regionName) {
        if (cachingFeature.isPresent()) {
            assertThat(cachingFeature.get().getCacheValue(regionName, attributeName)).isNotEmpty();
        }
    }

    @And("^verify build fails when test fails$")
    public void temp() {
        fail("verifying failures");
    }

}
