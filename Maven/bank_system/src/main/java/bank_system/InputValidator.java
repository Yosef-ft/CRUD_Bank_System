package bank_system;

public class InputValidator {
    public static boolean isFloat(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        try {
            Float.parseFloat(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInteger(String input){
        if (input == null || input.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
