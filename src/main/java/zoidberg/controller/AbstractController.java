package zoidberg.controller;

import static org.springframework.util.StringUtils.hasText;
import static org.springframework.util.CollectionUtils.isEmpty;

import org.jruby.embed.LocalContextScope;
import org.jruby.embed.LocalVariableBehavior;
import org.jruby.embed.ScriptingContainer;
import zoidberg.model.ScriptletResult;

import java.util.Collections;
import java.util.Map;

public abstract class AbstractController {
    private static final String EMPTY_STRING = "";

    protected String initScriptlet() {
        return EMPTY_STRING;
    }

    protected Map<String, ?> exposeObjects() {
        return Collections.emptyMap();
    }

    protected ScriptletResult runScriptlet(String code) {
        Object result = null;
        try {
            ScriptingContainer scriptingContainer = new ScriptingContainer(LocalContextScope.THREADSAFE, LocalVariableBehavior.PERSISTENT);
            scriptingContainer.clear();
            putExposedObjects(scriptingContainer);
            runInitScriptlet(scriptingContainer);
            result = scriptingContainer.runScriptlet(code);
            scriptingContainer.terminate();
        } catch (Exception e) {
            result = "Error: " + e.getMessage();
        }
        return new ScriptletResult(result);
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
