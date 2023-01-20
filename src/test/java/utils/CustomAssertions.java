package utils;

import io.qameta.allure.Step;
import org.assertj.core.api.AbstractIntegerAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.AssertionsForClassTypes;

public class CustomAssertions {

    @Step("Check if an int value is correct")
    public static AbstractIntegerAssert<?> assertThat(int actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    @Step("Check if a String value is correct")
    public static AbstractStringAssert<?> assertThat(String actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }
}
