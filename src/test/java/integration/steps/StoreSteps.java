package integration.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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

    @When("^the client calls /store$")
    public void the_client_issues_GET_participant() {
        restApiFeature.getStore();
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int status) throws IOException {
        assertThat(restApiFeature.getLastResponse().getResponse().getStatusCode().value()).isEqualTo(status);
    }

    @And("^the client receives \"([^\"]*)\"$")
    public void the_client_issues_GET_participant(String attributeName) {
        assertThat(restApiFeature.getLastResponse().getBodyJsonObject().has(attributeName)).isTrue();
    }

}
