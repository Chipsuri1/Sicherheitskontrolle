import main.configuration.SecurityControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class Schmöbb {

    @BeforeEach
    public void setUp() {
        SecurityControl airport = new SecurityControl();
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestsExample() {
        List<Integer> inputList01 = Arrays.asList(1, 2, 3);
        List<Integer> inputList02 = Arrays.asList(10, 20, 30);

        List<DynamicTest> dynamicTestList = new ArrayList<>();

        for (int i = 0; i < inputList01.size(); i++) {
            int x = inputList01.get(i);
            int y = inputList02.get(i);

            DynamicTest dynamicTest = dynamicTest("dynamic test for Calculator.add(" + x + "," + y + ")", () -> {
                assertEquals(x + y, calculator.add(x, y));
            });

            dynamicTestList.add(dynamicTest);
        }

        return dynamicTestList.stream();
    }


}
