package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleIO {
    private static BufferedReader buffy = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Generate a prompt that allows the user to enter any response and returns the String.
     * This method does not accept null, empty, or whitespace-only inputs.
     *
     * @param prompt - the prompt to be displayed to the user.
     * @return the input from the user as a String
     */
    public static String promptForString(String prompt) {
        if (prompt == null || prompt.trim().isEmpty()) {
            throw new IllegalArgumentException("The prompt cannot be null, empty, or whitespaces-only.");
        }

        String input = null;
        boolean isInvalid = true;

        do {
            System.out.println(prompt);

            try {
                input = buffy.readLine();
                isInvalid = input == null || input.trim().isEmpty();

                if (isInvalid) {
                    System.out.println("Your input cannot be null, empty, or just white space. Please, try again.");
                }
            } catch (IOException ioe) {
                System.out.println("There was a technical issue. Please, try again.");
            }
        } while (isInvalid);
        return input;
    }

    /**
     * This method Loops until valid input is given.
     *
     * @param prompt - the prompt to be displayed to the user
     * @param min    - the inclusive minimum boundary
     * @param max    - the inclusive maximum boundary
     * @return the int value
     */

    public static int promptForInt(String prompt, int min, int max) {
        if (max < min) {
            throw new IllegalArgumentException("The min cannot be greater than the max.");
        }

        int userNum = 0;
        boolean isInvalid = true;

        do {
            String input = promptForString(prompt);
            try {
                userNum = Integer.parseInt(input);
                isInvalid = userNum < min || userNum > max;
            } catch (NumberFormatException nfe) {
                //No need to do anything here.
            }

            if (isInvalid) {
                System.out.println("You must enter a number between " + min + " and " + max + ", Please, try again.");
            }
        } while (isInvalid);
        return userNum;
    }

    /**
     * Generates to a prompt that expects the user to enter one of two responses that will equate
     * to a boolean value. The trueString represents the case insensitive response that will equate to ture.
     * The falseString acts similarly, but for a false boolean value.
     * Example: Assume this method is called with a trueString argument of "yes" and a falseString argument of
     * "no". If the enters "YES", the method turns true. If the user enters "no", the method returns false.
     * All other inputs are considered invalid, the user will be informed, and the prompt will repeat.
     *
     * @param prompt      - the prompt to be displayed to the user
     * @param trueString  - the case insensitive value that will evaluate to true
     * @param falseString - the case insensitive vale that will evaluate to false
     * @return the boolean value
     */
    public static boolean promptForBoolean(String prompt, String trueString, String falseString) {
        if (trueString == null || falseString == null || trueString.trim().isEmpty() || falseString.trim().isEmpty() ||
                trueString.trim().equalsIgnoreCase(falseString.trim())) {
            throw new IllegalArgumentException("The trueString and falseString values cannot be null, empty, whitespaces-only, or case-insensitively equal");
        }

        trueString = trueString.trim();
        falseString = falseString.trim();
        boolean isTrue = false;
        boolean inputIsInvalid = true;

        do {
            String input = promptForString(prompt).trim();
            inputIsInvalid = !input.equalsIgnoreCase(trueString) && !input.equalsIgnoreCase(falseString);
            if (inputIsInvalid) {
                System.out.println("You must input either \"" + trueString + "\" or \"" + falseString + "\". Please, try again.");
            } else {
                isTrue = input.equalsIgnoreCase(trueString);
            }

        } while (inputIsInvalid);

        return isTrue;
    }

    /**
     * Generates a console-based menu using the Strings in options as the menu items.
     * Reserves the number 0 for the "quit" option when withQuit is true.
     *
     * @param options  - Strings representing the menu options
     * @param withQuit - adds option 0 for "quit" when true
     * @return the int of the selection made by the user
     */
    public static int promptForMenuSelection(String[] options, boolean withQuit) {

        if (options == null) {
            options = new String[0];
        }

        if (options.length == 0 && !withQuit) {
            throw new IllegalArgumentException("There must be at least one menu option to select.");
        }


        //Build the menu String
        StringBuilder sb = new StringBuilder("Please choose one of the following:\n\n");

        for (int i = 0; i < options.length; i++) {
            //#) optionString
            sb.append(i + 1).append(") ").append(options[i]).append("\n");
        }
        if (options.length > 0) {
            sb.append("\n");
        }


        if (withQuit) {
            sb.append("0) Quit\n\n");
        }

        sb.append("Enter the number of your selection: ");

        String menuString = sb.toString();
        int min = withQuit ? 0 : 1;
        int max = options.length;

        //Call promptForInt to get the user's choice
        int userInput = promptForInt(menuString, min, max);

        //return the user's choice
        return userInput;
    }

}



