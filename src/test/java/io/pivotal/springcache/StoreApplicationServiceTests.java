package io.pivotal.springcache;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"feature.toggle.offers-enabled=true", "integration.offers.base.url=http://localhost:7777/offers?type={0}"})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StoreApplicationServiceTests {

	@ClassRule
	public static WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(7777));

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldGetHomeWithOffers_WhenOffersIsEnabled() throws Exception {

		// given
		stubFor(get(urlEqualTo("/offers?type=banners"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
						.withBodyFile("BannerOffers.json")));

		stubFor(get(urlEqualTo("/offers?type=promotions"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
						.withBodyFile("PromotionOffers.json")));

		// when
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/offers")
				.accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse result = mockMvc.perform(requestBuilder).andReturn().getResponse();

		// then
		assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getContentAsString()).containsPattern(".*banners.*banner-1.*");
        assertThat(result.getContentAsString()).containsPattern(".*promotions.*promo-1.*");
	}

    @Test
    public void shouldGetHomeWithEmptyOffers_WhenNoneAvailable() throws Exception {

        // given
        stubFor(get(urlEqualTo("/offers?type=banners"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .withBody("[]")));

        stubFor(get(urlEqualTo("/offers?type=promotions"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .withBody("[]")));

        // when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/offers")
                .accept(MediaType.APPLICATION_JSON);
        MockHttpServletResponse result = mockMvc.perform(requestBuilder).andReturn().getResponse();

        // then
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getContentAsString()).containsPattern(".*banners.*");
        assertThat(result.getContentAsString()).containsPattern(".*promotions.*");
    }

}

