package de.burger.it;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TypeBinding<T> {

    private final Class<T> clazz;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private ValueModelTransformer<ValueModel> transformer;
    private ValueModelValidator validator;

    public TypeBinding(Class<T> clazz) {
        this.clazz = clazz;
    }

    public TypeBinding<T> withTransformer(ValueModelTransformer<ValueModel> next) {
        if (this.transformer == null) {
            this.transformer = next;
        } else {
            this.transformer = this.transformer.andThen(next);
        }
        return this;
    }

    public TypeBinding<T> withValidator(ValueModelValidator validator) {
        this.validator = validator;
        return this;
    }

    public T mapFrom(ValueModel valueModel) {
        Map<String, Object> flatMap = new HashMap<>();

        if (valueModel != null && valueModel.getValues() != null) {
            valueModel.getValues().entrySet().stream()
                    .filter(e -> e.getKey() != null)
                    .forEach(e -> {
                        ValueG<?> vg = e.getValue();
                        flatMap.put(e.getKey(), vg != null ? vg.getValue() : null);
                    });
        }

        return objectMapper.convertValue(flatMap, clazz);
    }

    public ValueModel mapToValueModel(T data) {
        Map<String, Object> rawMap = objectMapper.convertValue(
                data,
                new TypeReference<>() {
                }
        );

        Map<String, ValueG<?>> resultMap = rawMap.entrySet().stream()
                .filter(e -> e.getKey() != null)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            String key = entry.getKey();
                            Object value = entry.getValue();
                            return (value != null)
                                    ? new ValueG<>(key, value.getClass().cast(value))
                                    : new ValueG<>(key, null);
                        }
                ));

        ValueModel valueModel = new ValueModel();
        valueModel.setValues(resultMap);
        return valueModel;
    }
}

