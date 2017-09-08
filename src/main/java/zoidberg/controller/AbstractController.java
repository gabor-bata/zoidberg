package zoidberg.controller;

import static org.springframework.util.StringUtils.hasText;
import static org.springframework.util.CollectionUtils.isEmpty;

import org.jruby.embed.LocalContextScope;
import org.jruby.embed.LocalVariableBehavior;
import org.jruby.embed.ScriptingContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zoidberg.model.ScriptletResult;

import java.util.Collections;
import java.util.Map;

public abstract class AbstractController {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractController.class);
    private static final String EMPTY_STRING = "";

    protected String initScriptlet() {
        return EMPTY_STRING;
    }

    protected Map<String, ?> exposeObjects() {
        return Collections.emptyMap();
    }

    protected ScriptletResult runScriptlet(String code) {
        Object result;
        try {
            ScriptingContainer scriptingContainer = new ScriptingContainer(LocalContextScope.THREADSAFE, LocalVariableBehavior.TRANSIENT);
            scriptingContainer.clear();
            putExposedObjects(scriptingContainer);
            runInitScriptlet(scriptingContainer);

            result = scriptingContainer.runScriptlet(code);
            scriptingContainer.terminate();
        } catch (Exception e) {
            LOG.error("Error occurred while running the script.", e);
            result = "Error: " + e.getMessage();
        }
        return ScriptletResult.create(result);
    }

    private void putExposedObjects(ScriptingContainer scriptingContainer) {
        Map<String, ?> objects = exposeObjects();
        if (!isEmpty(objects)) {
            for (Map.Entry<String, ?> entry : objects.entrySet()) {
                scriptingContainer.put(entry.getKey(), entry.getValue());
            }
        }
    }

    private void runInitScriptlet(ScriptingContainer scriptingContainer) {
        String initScriptlet = initScriptlet();
        if (hasText(initScriptlet)) {
            scriptingContainer.runScriptlet(initScriptlet);
        }
    }
}
