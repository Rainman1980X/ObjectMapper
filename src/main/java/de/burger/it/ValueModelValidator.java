package de.burger.it;

@FunctionalInterface
public interface ValueModelValidator {

    /**
     * Validiert das übergebene ValueModel und schreibt Fehler in das ValidationResult.
     * Es wird erwartet, dass Fehler über result.addError(...) hinzugefügt werden.
     */
    void validate(ValueModel model, ValidationResult result);
}
