package io.pivotal.springcache.products;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@ExtendWith(SpringExtension.class)
@JsonTest
@Tag("component")
public class ProductJsonTest {

    @Autowired
    private JacksonTester<Product> jsonTester;

    @Test
    public void testSerialization() throws Exception {

        // given
        String content = "{\"id\":\"1234\",\"name\":\"Groovy\"}";

        // when
        Product product = this.jsonTester.parseObject(content);
        JsonContent<Product> productJsonContent = this.jsonTester.write(product);

        // then
        assertEquals(content, productJsonContent.getJson(), JSONCompareMode.STRICT);
    }

}