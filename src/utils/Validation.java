package utils;

import exceptions.InputIntervalException;

/**
 * Class for validation
 */
public class Validation {

    /**
     * Method that checks belonging to the interval(long) for min
     * @param number
     * @param min
     * @param canBeNull
     * @param messageIfWrong
     * @return
     */
    public static boolean checkInterval(Long number, long min, boolean canBeNull, String messageIfWrong){
        try{
            if (number == null && canBeNull) return true;
            if (number == null) return false;
            if (number < min) throw new InputIntervalException();
            return true;
        } catch (InputIntervalException e){
            System.out.println(messageIfWrong);
            return false;
        }
    }

    /**
     * Method that checks belonging to the interval(long) for max
     * @param number
     * @param max
     * @param canBeNull
     * @param messageIfWrong
     * @return
     */
    public static boolean checkMax(Long number, long max, boolean canBeNull, String messageIfWrong){
        try{
            if (number == null && canBeNull) return true;
            if (number == null) return false;
            if (number > max) throw new InputIntervalException();
            return true;
        } catch (InputIntervalException e){
            System.out.println(messageIfWrong);
            return false;
        }
    }

    /**
     * Method that checks belonging to the interval(double) for min
     * @param number
     * @param min
     * @param canBeNull
     * @param messageIfWrong
     * @return
     */
    public static boolean checkIntervalDouble(Double number, long min, boolean canBeNull, String messageIfWrong){
        try{
            if (number == null && canBeNull) return true;
            if (number == null) return false;
            if (number < min) throw new InputIntervalException();
            return true;
        } catch (InputIntervalException e){
            System.out.println(messageIfWrong);
            return false;
        }
    }

    /**
     * Method that checks succeed of parsing
     * from String to long
     * @param field
     * @param messageIfWrong
     * @return
     */
    public static boolean checkStringToLong(String field, String messageIfWrong){
        try {
            Long.parseLong(field);
            return true;
        } catch (NumberFormatException e) {
            System.out.println(messageIfWrong);
            return false;
        }
    }

    /**
     * Method that checks succeed of parsing
     * from String to Integer
     * @param field
     * @param messageIfWrong
     * @return
     */
    public static boolean checkStringToInteger(String field, String messageIfWrong){
        try {
            Integer.valueOf(field);
            return true;
        } catch (NumberFormatException e) {
            System.out.println(messageIfWrong);
            return false;
        }

    }

    /**
     * Method that checks succeed of parsing
     * from String to Double
     * @param field
     * @param messageIfWrong
     * @return
     */
    public static boolean checkStringToDouble(String field, String messageIfWrong){
        try {
            Double.valueOf(field);
            return true;
        } catch (NumberFormatException e) {
            System.out.println(messageIfWrong);
            return false;
        }
    }

    /**
     * Method that checks succeed of parsing
     * from String to float
     * @param field
     * @param messageIfWrong
     * @return
     */
    public static boolean checkStringToFloat(String field, String messageIfWrong){
        try {
            Float.parseFloat(field);
            return true;
        } catch (NumberFormatException e) {
            System.out.println(messageIfWrong);
            return false;
        }

    }

    public static boolean checkChars(String field, boolean russian, boolean numbers) {
        for (char c : field.toCharArray()) {
            if (!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z') && !russian && !numbers) return false;
            if (!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z') && !(c >= 'à' && c <= 'ÿ') && !(c >= 'À' && c <= 'ß') && !numbers)
                return false;
            if (!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z') && !russian && !(c >= '0' && c <= '9')) return false;
            if (!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z') && !(c >= 'à' && c <= 'ÿ') && !(c >= 'À' && c <= 'ß') && !(c >= '0' && c <= '9'))
                return false;
        }
        return true;
    }
}
