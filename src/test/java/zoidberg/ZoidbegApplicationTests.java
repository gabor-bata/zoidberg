package zoidberg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import zoidberg.model.ScriptletResult;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ZoidbegApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldExposeWeatherServiceAsInstanceVariable() {
        // given
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("code", "!@weather_service.nil?");

        // when
        ScriptletResult body = restTemplate.postForObject("/weather", map, ScriptletResult.class);

        // then
        assertThat(body, hasProperty("result", is(equalTo(true))));
    }
}
