package ui;

public interface InterfaceUI {

    public abstract void runProgram();
    private void printError(String message) {
        System.out.println(" Fejl: " + message);
    }
}
