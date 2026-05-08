package Validation;

import ExceptionHandler.ValidationException;

public class InputValidator {

    public static void validateName(String name) {
        if (name == null || name.isEmpty()){
            throw new ValidationException("Navn må ikke være tomt");
        }
    }

    public static void validateAge(int age) {
        if (age <= 0){
            throw new ValidationException("Alder skal være over 0");
        }
    }
}
