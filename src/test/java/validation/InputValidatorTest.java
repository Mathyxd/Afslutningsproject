package validation;

import exceptionhandler.ValidationException;
import model.Member;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    @Test
    void validateName() {
        assertThrows(ValidationException.class, () -> new Member(null, 6));
    }

    @Test
    void validateAge() {
        assertThrows(ValidationException.class, () -> new Member("Alice", -6));
    }
}