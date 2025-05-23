
package de.burger.it;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.stream.Collectors;

public class CrossTypeBinding<S, T> {

    private final Class<S> sourceClass;
    private final Class<T> targetClass;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private ValueModelTransformer<ValueModel> transformer;

    public CrossTypeBinding(Class<S> sourceClass, Class<T> targetClass) {
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
    }

    public CrossTypeBinding<S, T> withTransformer(ValueModelTransformer<ValueModel> transformer) {
        this.transformer = transformer;
        return this;
    }

    public T mapFrom(S source) {
        if (source == null) {
            return null;
        }

        // 1. Source-Objekt in ValueModel überführen
        Map<String, Object> rawMap = objectMapper.convertValue(source, new TypeReference<>() {
        });
        Map<String, ValueG<?>> wrappedMap = rawMap.entrySet().stream()
                .filter(e -> e.getKey() != null && e.getValue() != null)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> new ValueG<>(e.getKey(), e.getValue())
                ));

        ValueModel valueModel = new ValueModel();
        valueModel.setValues(wrappedMap);

        // 2. Transformer anwenden
        if (transformer != null) {
            valueModel = transformer.apply(valueModel);
        }

        // 3. Rückkonvertieren in Zielmodell
        Map<String, Object> flatMap = valueModel.getValues().entrySet().stream()
                .filter(e ->
                        e.getKey() != null &&
                                e.getValue() != null &&
                                e.getValue().getValue() != null
                )
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> {
                            ValueG<?> vg = e.getValue();
                            return vg.getValue();
                        }
                ));

        return objectMapper.convertValue(flatMap, targetClass);
    }

}
