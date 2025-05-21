package de.burger.it;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TestCaseProvider {

    public record TestCase<T>(
            T original,
            Map<String, Object> expectedValues,
            Class<T> modelClass
    ) {}

    public static Stream<TestCase<?>> provideTestCases() {
        return Stream.of(
                // DataModel – gefüllt
                new TestCase<>(
                        new DataModel() {{
                            setFirstStep(1);
                            setSecondStep(2);
                            setThirdStep(true);
                            setFourthStep(4.5);
                            setFifthStep(100L);
                            setName("Matthias");
                        }},
                        new HashMap<String, Object>() {{
                            put("firstStep", 1);
                            put("secondStep", 2);
                            put("thirdStep", true);
                            put("fourthStep", 4.5);
                            put("fifthStep", 100L);
                            put("name", "Matthias");
                        }},
                        DataModel.class
                ),

                // DataModel – Standardwerte
                new TestCase<>(
                        new DataModel(),
                        new HashMap<String, Object>() {{
                            put("firstStep", 0);
                            put("secondStep", 0);
                            put("thirdStep", false);
                            put("fourthStep", 0.0);
                            put("fifthStep", 0L);
                            put("name", null);
                        }},
                        DataModel.class
                ),

                // DataModelObjDataTypes – gefüllt
                new TestCase<>(
                        new DataModelObjDataTypes() {{
                            setFirstStep(1);
                            setSecondStep(2);
                            setThirdStep(true);
                            setFourthStep(4.5);
                            setFifthStep(100L);
                            setName("Matthias");
                        }},
                        new HashMap<String, Object>() {{
                            put("firstStep", 1);
                            put("secondStep", 2);
                            put("thirdStep", true);
                            put("fourthStep", 4.5);
                            put("fifthStep", 100L);
                            put("name", "Matthias");
                        }},
                        DataModelObjDataTypes.class
                ),

                // DataModelObjDataTypes – null
                new TestCase<>(
                        new DataModelObjDataTypes(),
                        new HashMap<String, Object>() {{
                            put("firstStep", null);
                            put("secondStep", null);
                            put("thirdStep", null);
                            put("fourthStep", null);
                            put("fifthStep", null);
                            put("name", null);
                        }},
                        DataModelObjDataTypes.class
                )
        );
    }
}

