package zoidberg.controller.weather;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static org.springframework.util.StringUtils.collectionToDelimitedString;

import com.github.fedy2.weather.YahooWeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import zoidberg.controller.AbstractController;
import zoidberg.model.ScriptletResult;

import java.util.Map;

@Controller
public class WeatherController extends AbstractController {
    private static final Logger LOG = LoggerFactory.getLogger(WeatherController.class);
    private static final String INIT_SCRIPT = collectionToDelimitedString(asList(
            "require 'java'",
            "java_import com.github.fedy2.weather.data.unit.DegreeUnit"
    ), "\n");

    @RequestMapping("/weather")
    public @ResponseBody
    ScriptletResult handleRequest(@RequestParam(value = "code", required = true, defaultValue = "{ weather_service_available: !weatherService.nil? }") String code) {
        return runScriptlet(code);
    }

    @Override
    protected Map<String, ?> exposeObjects() {
        YahooWeatherService weatherService = null;
        try {
            weatherService = new YahooWeatherService();
        } catch (Exception e) {
            LOG.error("Could not instantiate weather service.", e);
        }
        return singletonMap("weather_service", weatherService);
    }

    @Override
    protected String initScriptlet() {
        return INIT_SCRIPT;
    }
}
