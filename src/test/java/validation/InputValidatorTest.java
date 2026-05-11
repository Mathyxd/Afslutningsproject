package validation;

import exceptionhandler.ValidationException;
import model.ExerciseMember;
import model.Member;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {


    // Tester, om validateName() metoden smider en "ValidationException", hvis et ExerciseMember oprettes med et tomt navn
    @Test
    void validateName() {
        assertThrows(ValidationException.class,
                () -> new ExerciseMember(null, 6, 1234, true));
    }

    // Tester, om validateAge() metoden smider en "ValidationException", hvis et ExerciseMember oprettes med en
    // alder på 0 eller mindre
    @Test
    void validateAge() {
        assertThrows(ValidationException.class,
                () -> new ExerciseMember("Alice", -6, 123, false));
    }
}

