package fr.sg.fls.generator;

import java.util.Map;

/**
 * Wrapper contenant une ligne brute de SCT et une map champ/valeur correspondante à la ligne brute
 *
 * @author jntakpe
 */
public class SCTWrapper {

    private final String message;

    private final Map<String, String> fields;

    public SCTWrapper(String message, Map<String, String> fields) {
        this.message = message;
        this.fields = fields;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getFields() {
        return fields;
    }
}
