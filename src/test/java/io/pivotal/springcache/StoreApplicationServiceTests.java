package io.pivotal.springcache;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.geode.cache.GemFireCache;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"feature.toggle.offers-enabled=true", "integration.offers.base.url=http://localhost:7777/offers?type={0}"})
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"test", "wire"})
@ContextConfiguration(initializers = ApplicationTestContextInitializer.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // Needed so each test gets its own WireMock Server
public class StoreApplicationServiceTests {

	@Autowired
	private WireMockServer wireMockServer;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
    private GemFireCache gemFireCache;

	@BeforeEach
	void configureSystemUnderTest() {
		wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
	}

	@AfterEach
	void stopWireMockServer() {
		this.wireMockServer.stop();
	}

	@Tag("component")
    @Test
	public void shouldGetHomeWithOffers_WhenOffersIsEnabled() throws Exception {

		// given
		givenThat(get(urlEqualTo("/offers?type=banners"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
						.withBodyFile("BannerOffers.json")));

		givenThat(get(urlEqualTo("/offers?type=promotions"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
						.withBodyFile("PromotionOffers.json")));

		// when
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/store")
				.accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse result = mockMvc.perform(requestBuilder).andReturn().getResponse();

		// then
		assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getContentAsString()).containsPattern(".*banners.*banner-1.*");
        assertThat(result.getContentAsString()).containsPattern(".*promotions.*promo-1.*");
        assertThat(gemFireCache.getRegion("products")).hasSize(1);
        assertThat(gemFireCache.getRegion("offers")).hasSize(2);
	}

	@Tag("component")
	@Test
	public void shouldGetHomeWithOffers_WhenOffersCalloutFails() throws Exception {

		// given
		givenThat(get(urlEqualTo("/offers?type=banners"))
				.willReturn(aResponse().withStatus(500)));

		givenThat(get(urlEqualTo("/offers?type=promotions"))
				.willReturn(aResponse().withStatus(500)));

		// when
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/store")
				.accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse result = mockMvc.perform(requestBuilder).andReturn().getResponse();

		// then
		assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(result.getContentAsString()).containsPattern(".*banners currently unavailable.*");
		assertThat(result.getContentAsString()).containsPattern(".*promotions currently unavailable.*");
		assertThat(gemFireCache.getRegion("products")).hasSize(1);
		assertThat(gemFireCache.getRegion("offers")).hasSize(2);
	}

	@Tag("component")
    @Test
    public void shouldGetHomeWithEmptyOffers_WhenNoneAvailable() throws Exception {

        // given
		givenThat(get(urlEqualTo("/offers?type=banners"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .withBody("[]")));

		givenThat(get(urlEqualTo("/offers?type=promotions"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .withBody("[]")));

        // when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/store")
                .accept(MediaType.APPLICATION_JSON);
        MockHttpServletResponse result = mockMvc.perform(requestBuilder).andReturn().getResponse();

        // then
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getContentAsString()).containsPattern(".*banners.*");
        assertThat(result.getContentAsString()).containsPattern(".*promotions.*");
    }

}

