package io.pivotal.springcache;

import org.apache.geode.cache.GemFireCache;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductApplicationServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GemFireCache gemFireCache;

    @Tag("component")
    @Test
    public void shouldGetProduct_WhenGivenValidId() throws Exception {

        // given
        String productId = "9cfae4f0-e5fc-4d91-be83-3656a2776931";

        // when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/{0}", productId)
                .accept(MediaType.APPLICATION_JSON);
        MockHttpServletResponse result = mockMvc.perform(requestBuilder).andReturn().getResponse();

        // then
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getContentAsString()).containsPattern(".*\"id\".*:.*9cfae4f0-e5fc-4d91-be83-3656a2776931");
    }

    @Tag("component")
    @Test
    public void shouldGetProducts() throws Exception {

        // when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .accept(MediaType.APPLICATION_JSON);
        MockHttpServletResponse result = mockMvc.perform(requestBuilder).andReturn().getResponse();

        // then
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getContentAsString()).containsPattern(".*\"id\".*:.*9cfae4f0-e5fc-4d91-be83-3656a2776931");
        assertThat(gemFireCache.getRegion("products").get("all")).isNotNull();
    }
}

