package de.burger.it;

import lombok.Data;

import java.util.Map;

@Data
public class ValueModel {
    private Map<String, ValueG<?>> values;
}
