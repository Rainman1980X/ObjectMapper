package de.burger.it;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ValidationResult {

    private final Map<String, String> errors = new LinkedHashMap<>();

    public void addError(String key, String message) {
        errors.put(key, message);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public Map<String, String> getErrors() {
        return Collections.unmodifiableMap(errors);
    }

    public String getError(String key) {
        return errors.get(key);
    }

    public void throwIfInvalid() {
        if (!isValid()) {
            throw new ValueModelValidationException(errors);
        }
    }
}
