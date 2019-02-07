package io.pivotal.springcache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductApplicationServiceTests {

	@Autowired
	private MockMvc mockMvc;

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

}

