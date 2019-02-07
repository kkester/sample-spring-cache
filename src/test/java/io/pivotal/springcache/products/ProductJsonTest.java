package io.pivotal.springcache.products;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit4.SpringRunner;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@RunWith(SpringRunner.class)
@JsonTest
public class ProductJsonTest {

    @Autowired
    private JacksonTester<ProductEntity> jsonTester;

    @Test
    public void testSerialization() throws Exception {

        // given
        String content = "{\"productId\":\"1234\",\"productName\":\"Groovy\"}";

        // when
        ProductEntity album = this.jsonTester.parseObject(content);
        JsonContent<ProductEntity> albumJsonContent = this.jsonTester.write(album);

        // then
        assertEquals(albumJsonContent.getJson(), content, JSONCompareMode.STRICT);
    }

}