package zoidberg.model;

public class ScriptletResult {
    private Object result;

    private ScriptletResult() {
    }

    public Object getResult() {
        return result;
    }

    public static ScriptletResult create(Object result) {
        ScriptletResult scriptletResult = new ScriptletResult();
        scriptletResult.result = result;
        return scriptletResult;
    }
}
