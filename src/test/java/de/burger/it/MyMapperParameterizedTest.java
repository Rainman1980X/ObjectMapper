package de.burger.it;
import de.burger.it.transformer.DataModelToObjTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MyMapperParameterizedTest {

    private final MyMapper mapper = new MyMapper();

    @ParameterizedTest
    @MethodSource("de.burger.it.TestCaseProvider#provideTestCases")
    <T> void testMappingRoundtrip(TestCaseProvider.TestCase<T> testCase) {
        ValueModel model = mapper.forSingleType(testCase.modelClass())
                .mapToValueModel(testCase.original());
        Map<String, ValueG<?>> values = model.getValues();

        assertEquals(testCase.expectedValues().size(), values.size());

        testCase.expectedValues().forEach((key, expected) -> {
            Object actual = values.get(key).getValue();
            if (expected instanceof Double dExpected && actual instanceof Double dActual) {
                assertEquals(dExpected, dActual, 0.0001, "Mismatch on " + key);
            } else {
                assertEquals(expected, actual, "Mismatch on " + key);
            }
        });

        T reconstructed = mapper.forType(testCase.modelClass())
                .asSingleType()
                .mapFrom(model);
        assertEquals(testCase.original(), reconstructed);
    }

    @Test
    void testPrimitivesToObjectsToPrimitives() {
        DataModel original = new DataModel();
        original.setFirstStep(123);
        original.setThirdStep(true);

        ValueModel model = mapper.forType(DataModel.class).asSingleType().mapToValueModel(original);
        DataModelObjDataTypes objects = mapper
                .forType(DataModelObjDataTypes.class)
                .asSingleType()
                .mapFrom(model);
        ValueModel model2 = mapper
                .forType(DataModelObjDataTypes.class)
                .asSingleType()
                .mapToValueModel(objects);

        DataModel roundtrip = mapper
                .forType(DataModel.class)
                .asSingleType()
                .mapFrom(model2);
        assertEquals(original.getFirstStep(), roundtrip.getFirstStep());
        assertEquals(original.isThirdStep(), roundtrip.isThirdStep());
    }

    @Test
    void testPrimitivesToObjectsToPrimitivesWithTransformer() {
        MyMapper mapper = new MyMapper();

        // ➤ Ursprüngliches Primitive-Modell vorbereiten
        DataModel original = new DataModel();
        original.setFirstStep(123);
        original.setThirdStep(true);

        // ➤ Schritt 1: Primitives → ValueModel
        DataModelObjDataTypes dataModelObjDataTypes = mapper
                .forType(DataModel.class)
                .forType(DataModelObjDataTypes.class)
                .withTransformer(DataModelToObjTransformer::apply)
                .mapFrom(original); // ✅ original ist ein DataModel


        // ➤ Verifikation
        assertEquals(original.getFirstStep(), dataModelObjDataTypes.getFirstStep());
        assertEquals(original.isThirdStep(), dataModelObjDataTypes.getThirdStep());
    }


}


