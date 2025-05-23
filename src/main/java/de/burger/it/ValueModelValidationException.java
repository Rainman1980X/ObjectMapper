package de.burger.it;

import java.util.Collections;
import java.util.Map;

public class ValueModelValidationException extends RuntimeException {

    private final Map<String, String> errors;

    public ValueModelValidationException(Map<String, String> errors) {
        super("Validation failed: " + errors);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return Collections.unmodifiableMap(errors);
    }
}
