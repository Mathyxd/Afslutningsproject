package validation;

import exceptionhandler.ValidationException;

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

    public static void validatID(int memberID){
        if (memberID < 1000){
            throw new ValidationException("Medlems ID'et skal være mindst 1000");
        }
    }

    public static String validateDiscipline(String discipline){
        if (!discipline.equals("single") && !discipline.equals("double") &&
                !discipline.equals("mix double") && !discipline.equals("mix_double")){
            throw new ValidationException("Disciplin skal være single, double eller mix double");
        }
        return discipline.replaceAll(" ","_");
    }
}
