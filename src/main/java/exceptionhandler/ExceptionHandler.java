package exceptionhandler;

import java.io.IOException;//fejl ved filer/input-output
import java.util.InputMismatchException; //forkert datatype fra brugerinput

public class ExceptionHandler {
    //Modtager en generel Exception.
    //Denne metode er ikke static, så du skal oprette et objekt?? skal jeg lave den static??
    public void handleValidationException(Exception e) {
        System.out.println("Unexpected error occurred.");

    }

    //Den opstår typisk ved Scanner, hvis brugeren skriver forkert datatype.
    public static void handleInputMismatch(InputMismatchException e) {

        System.out.println("Error: Please enter valid info.");

    }

    //Fejl ved filer eller streams.
    public static void handleFileError(IOException e) {

        System.out.println("File error occurred.");


    }

    //Opstår når man bruger et objekt som er null.
    public static void handleNullError(NullPointerException e) {

        System.out.println("Error: Null value encountered.");

    }

    //bruges når en metode får en ugyldig værdi som argument.
    public static void handleIllegalArgument(IllegalArgumentException e) {

        System.out.println("Invalid argument provided.");

    }
}
