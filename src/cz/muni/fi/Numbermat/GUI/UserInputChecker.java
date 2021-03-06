/*
    This file is part of Numbermat: Math Problem Generator.
    Copyright © 2014 Valdemar Svabensky

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package cz.muni.fi.Numbermat.GUI;

import cz.muni.fi.Numbermat.Algorithms;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Methods for checking user input in GUI text fields (parameters or answer).
 * 
 * @author Valdemar Svabensky <395868(at)mail(dot)muni(dot)cz>
 */
public final class UserInputChecker {

    private UserInputChecker() {
        throw new IllegalStateException(this.getClass().getName() +
                " class should not be instantiated.");
    }
    
    /*** PARAMETER CHECKING ***/
    
    /**
     * Checks if user input is a single integer within specified range.
     * @param inputField Component (single field) where the user inputs text
     * @param mainFrame GUI frame where error message might be shown
     * @param min Lowest number allowed
     * @param max Highest number allowed
     * @param zero Is zero allowed?
     * @return Parsed number or null in case of error
     */
    public static Integer numberInput(final JTextField inputField, final MainFrame mainFrame,
            final int min, final int max, final boolean zero) {
        
        Algorithms.notLessThanCheck(max, min);
        try {
            final String input = inputField.getText();
            final Integer result = Integer.parseInt(input);
            if ((result == 0) && !zero)
                throw new NumberFormatException("Zero is not allowed.");
            if ((result < min) || (result > max))
                throw new NumberFormatException("Invalid range.");
            inputField.setBackground(Color.white);
            return result;
        } catch (NumberFormatException ex) {
            final StringBuilder msg = new StringBuilder(32);
            msg.append("Prosím zadejte celé číslo v rozsahu [").append(min);
            msg.append(", ").append(max).append("].");
            msg.append(((min < 1) && (!zero)) ? " Nula není povolena." : "");
            error(inputField, mainFrame, msg.toString());
            return null;
        }
    }
    
    /**
     * Checks if user input is a single positive integer.
     * @param inputField Component (single field) where the user inputs text
     * @param mainFrame GUI frame where error message might be shown
     * @return Parsed number or null in case of error
     */
    public static Integer positiveNumberInput(final JTextField inputField, final MainFrame mainFrame) {
        return numberInput(inputField, mainFrame, 1, Config.MAX_INT, false);
    }
    
    /**
     * Checks if user input is a single non-negative integer.
     * @param inputField Component (single field) where the user inputs text
     * @param mainFrame GUI frame where error message might be shown
     * @return Parsed number or null in case of error
     */
    public static Integer nonNegativeNumberInput(final JTextField inputField, final MainFrame mainFrame) {
        return numberInput(inputField, mainFrame, 0, Config.MAX_INT, true);
    }
    
    /**
     * Checks if user input is a single non-zero integer.
     * @param inputField Component (single field) where the user inputs text
     * @param mainFrame GUI frame where error message might be shown
     * @return Parsed number or null in case of error
     */
    public static Integer nonZeroNumberInput(final JTextField inputField, final MainFrame mainFrame) {
        return numberInput(inputField, mainFrame, Config.MIN_INT, Config.MAX_INT, false);
    }
    
    /**
     * Checks if user input is a single integer within range specified in Config class.
     * @param inputField Component (single field) where the user inputs text
     * @param mainFrame GUI frame where error message might be shown
     * @return Parsed number or null in case of error
     */
    public static Integer integerNumberInput(final JTextField inputField, final MainFrame mainFrame) {
        return numberInput(inputField, mainFrame, Config.MIN_INT, Config.MAX_INT, true);
    }
    
    /**
     * Checks if user input is a single prime.
     * @param inputField Component (single field) where the user inputs text
     * @param mainFrame GUI frame where error message might be shown
     * @param odd are only odd primes allowed?
     * @return Parsed number or null in case of error
     */
    public static Integer primeInput(final JTextField inputField, final MainFrame mainFrame, final boolean odd) {
        try {
            final String input = inputField.getText();
            final Integer result = Integer.parseInt(input);
            if ((result == 2) && odd)
                throw new NumberFormatException("2 is not allowed.");
            if (!Algorithms.isPrime(result))
                throw new NumberFormatException("The input is not a prime.");
            inputField.setBackground(Color.white);
            return result;
        } catch (NumberFormatException ex) {
            final StringBuilder msg = new StringBuilder("Prosím zadejte ");
            msg.append(odd ? "liché " : "").append("prvočíslo.");
            error(inputField, mainFrame, msg.toString());
            return null;
        }
    }
    
    /**
     * Shortcut for parameter checking of several fields at once.
     * Each parameter from user input is a single integer.
     * @param inputFields Array of components where the user inputs text
     * @param mainFrame GUI frame where error message might be shown
     * @param count Amount of parameters given
     * @return Checked numbers in ArrayList or empty ArrayList in case of error
     */
    public static List<Integer> checkIntegerParameters(final JTextField[] inputFields,
            final MainFrame mainFrame, final int count) {
        
        final List<Integer> result = new ArrayList<>(count);
        for (int i = 0; i < count; ++i) {
            final Integer n = UserInputChecker.integerNumberInput(inputFields[i], mainFrame);
            if (n == null)
                return new ArrayList<>();
            result.add(n);
        }
        return result;
    }

    /**
     * Shortcut for parameter checking of several fields at once.
     * Each parameter from user input is a single non-negative integer.
     * @param inputFields Array of components where the user inputs text
     * @param mainFrame GUI frame where error message might be shown
     * @param count Amount of parameters given
     * @return Checked numbers in ArrayList or empty ArrayList in case of error
     */
    public static List<Integer> checkNonNegativeParameters(final JTextField[] inputFields,
            final MainFrame mainFrame, final int count) {
        
        final List<Integer> result = new ArrayList<>(count);
        for (int i = 0; i < count; ++i) {
            final Integer n = UserInputChecker.nonNegativeNumberInput(inputFields[i], mainFrame);
            if (n == null)
                return new ArrayList<>();
            result.add(n);
        }
        return result;
    }
    
    /**
     * Shortcut for parameter checking of several fields at once.
     * User input consists of 2 coprime integers.
     * @param inputFields Array of 2 components where the user inputs text
     * @param mainFrame GUI frame where error message might be shown
     * @param firstIsSmaller Requires that the first number is smaller
     * @return Checked numbers in ArrayList or empty ArrayList in case of error
     */
    public static List<Integer> checkCoprimeParameters(final JTextField[] inputFields,
            final MainFrame mainFrame, final boolean firstIsSmaller) {
        
        final List<Integer> result = checkIntegerParameters(inputFields, mainFrame, 2);
        if (!result.isEmpty()) {
            if (!Algorithms.isCoprime(result.get(0), result.get(1))) {
                UserInputChecker.error(inputFields[1], mainFrame,
                        "Čísla musí být nesoudělná.");
            } else {
                if ((firstIsSmaller) && (result.get(0) >= result.get(1)))
                    UserInputChecker.error(inputFields[0], mainFrame,
                            "První číslo musí být menší než druhé.");
                else
                    return result;
            }
        }
        return new ArrayList<>();
    }
    
    /**
     * Checks if user input is a list of numbers separated by space within specified range.
     * @param inputField Component (single field) where the user inputs text
     * @param mainFrame GUI frame where error message might be shown
     * @param count Expected amount of numbers given
     * @param min Lowest number allowed
     * @param zero Is zero allowed?
     * @param errorMsg Error message text
     * @return Parsed numbers in List or empty ArrayList in case of error
     */
    public static List<Integer> numberListInput(final JTextField inputField, final MainFrame mainFrame,
            final int count, final int min, final boolean zero, final String errorMsg) {
        
        final List<Integer> result = UserInputChecker.parse(inputField, mainFrame, count);
        if (!result.isEmpty()) {
            for (int i = 0; i < count; ++i) {
                if ((result.get(i) < min) || ((result.get(i).equals(0)) && !zero)) {
                    error(inputField, mainFrame, errorMsg);
                    return new ArrayList<>();
                }
            }
        }
        return result;
    }
    
    /**
     * Checks if user input is a list of numbers separated by space forming a permutation.
     * @param inputField Component (single field) where the user inputs text
     * @param mainFrame GUI frame where error message might be shown
     * @param count Expected amount of numbers given
     * @return Parsed permutation in list or empty ArrayList in case of error
     */
    public static List<Integer> permutationInput(final JTextField inputField, final MainFrame mainFrame, final int count) {
        final String msg = "Zadaná sekvence není permutací.";
        final List<Integer> input = parse(inputField, mainFrame, count);
        try {
            Algorithms.permutationCheck(input);
        } catch (IllegalArgumentException ex) {
            error(inputField, mainFrame, msg);
            return new ArrayList<>();
        }
        return input;
    }
    
    /**
     * Single linear congruence parameter checking.
     * @param inputFields Array of components where the user inputs text
     * @param mainFrame GUI frame where error message might be shown
     * @return Checked numbers for linear congruence or empty ArrayList in case of error
     */
    public static List<Integer> linearCongruenceNumberInput(final JTextField[] inputFields, final MainFrame mainFrame) {
        final Integer a = integerNumberInput(inputFields[0], mainFrame);
        final Integer b = integerNumberInput(inputFields[1], mainFrame);
        final Integer n = positiveNumberInput(inputFields[2], mainFrame);
        final List<Integer> result = new ArrayList<>(Arrays.asList(a, b, n));
        if (result.contains(null))
            return new ArrayList<>();
        return result;
    }
    
    /**
     * Linear congruence system parameter checking.
     * @param inputFields Array of components where the user inputs text
     * @param mainFrame GUI frame where error message might be shown
     * @param count number of congruences
     * @return Checked parameters for linear congruence system or empty ArrayList in case of error
     */
    public static List<List<Integer>> linearCongruenceSystemInput(
            final JTextField[] inputFields, final MainFrame mainFrame, final int count) {
        
        final List<Integer> aList = numberListInput(inputFields[1], mainFrame, count,
                        Config.MIN_INT, true, "Pole musí obsahovat jenom celá čísla.");
        final List<Integer> bList = numberListInput(inputFields[2], mainFrame, count,
                        Config.MIN_INT, true, "Pole musí obsahovat jenom celá čísla.");
        final List<Integer> nList = numberListInput(inputFields[3], mainFrame, count,
                        1, false, "Pole musí obsahovat jenom kladná čísla.");
        final List<List<Integer>> result = new ArrayList<>();
        if ((!aList.isEmpty()) && (!bList.isEmpty()) && (!nList.isEmpty())) {
            result.add(aList);
            result.add(bList);
            result.add(nList);
        }
        return result;
    }

        
    /*** USER ANSWERS CHECKING ***/
    
    /**
     * Checking user answers from userAnswerField. Any positive integer is allowed.
     * @param inputField Component (single field) where the user inputs text
     * @param mainFrame GUI frame where error message might be shown
     * @return List containing checked positive integer or empty ArrayList in case of error
     */
    public static List<Integer> positiveNumberResult(final JTextField inputField,
            final MainFrame mainFrame) {
        final List<Integer> actualResult = new ArrayList<>();
        final Integer userAnswer = positiveNumberInput(inputField, mainFrame);
        if (userAnswer != null)
            actualResult.add(userAnswer);
        return actualResult;
    }
    
    /**
     * Checking user answers from userAnswerField. Any positive integer is allowed.
     * @param inputField Component (single field) where the user inputs text
     * @param mainFrame GUI frame where error message might be shown
     * @return List containing checked positive integer or empty ArrayList in case of error
     */
    public static List<Integer> nonNegativeNumberResult(final JTextField inputField,
            final MainFrame mainFrame) {
        final List<Integer> actualResult = new ArrayList<>();
        final Integer userAnswer = nonNegativeNumberInput(inputField, mainFrame);
        if (userAnswer != null)
            actualResult.add(userAnswer);
        return actualResult;
    }
    
    /**
     * Checking user answers from userAnswerField. Any positive integer within specified range is allowed.
     * @param inputField Component (single field) where the user inputs text
     * @param mainFrame GUI frame where error message might be shown
     * @param min Lowest number allowed
     * @param max Highest number allowed
     * @return List containing checked integer within specified range or empty ArrayList in case of error
     */
    public static List<Integer> numberResult(final JTextField inputField,
            final MainFrame mainFrame, final int min, final int max) {
        
        final List<Integer> actualResult = new ArrayList<>();
        final Integer userAnswer = numberInput(inputField, mainFrame, min, max, (min < 1));
        if (userAnswer != null)
            actualResult.add(userAnswer);
        return actualResult;
    }

    /**
     * Checking user answers from userAnswerField. Any <count> integers are allowed.
     * @param inputField Component (single field) where the user inputs text
     * @param mainFrame GUI frame where error message might be shown
     * @param count Required amount of integers in list, e.g. 2 for pair
     * @return List containing parsed coefficients or empty ArrayList in case of error
     */
    public static List<Integer> listResult(final JTextField inputField,
            final MainFrame mainFrame, final int count) {
        return parse(inputField, mainFrame, count);
    }
    
    /*** ADDITIONAL METHODS ***/
    
    private static String removeWhiteSpace(final String input) {
        return input.replaceAll("\\s+", "");
    }
    
    /**
     * Parses user input and checks if it is a list of integers separated by space or comma.
     * @param inputField Component (single field) where the user inputs text
     * @param mainFrame GUI frame where error message might be shown
     * @param count Number of tokens
     * @return List containing parsed input or empty ArrayList
     */
    public static List<Integer> parse(final JTextField inputField,
            final MainFrame mainFrame, final int count) {
        
        try {
            final String input = inputField.getText();
            String[] tokens = removeWhiteSpace(input).split(","); // try to split on commas
            if (tokens.length != count) {
                tokens = input.split("\\s+"); // then on whitespace
                if (tokens.length != count)
                    throw new IllegalArgumentException("Invalid number of arguments.");
            }
            
            final List<Integer> result = new ArrayList<>();
            for (int i = 0; i < count; ++i) {
                final int number = Integer.parseInt(tokens[i]);
                result.add(number);
                if ((number < Config.MIN_INT) || (number > Config.MAX_INT))
                    throw new NumberFormatException("Invalid range.");
            }
            inputField.setBackground(Color.white);
            return result;
        } catch (IllegalArgumentException ex) {
            String msg;
            if (count == 0)
                msg = "Příklad nemá řešení.";
            else
                msg = "Prosím zadejte " + count + " celočíselné koeficienty oddělené mezerou.";
            error(inputField, mainFrame, msg.toString());
            return new ArrayList<>();
        }
    }
    
    /**
     * Highlights a text field with incorrect input and opens a pop-up window with error message.
     * @param inputField Component (single field) where the user inputs text
     * @param mainFrame GUI frame where error message will be shown
     * @param msg Error message
     */
    public static void error(final JTextField inputField, final MainFrame mainFrame, final String msg) {
        inputField.setBackground(Color.red);
        error(mainFrame, "Chybný vstup", msg);
    }
    
    /**
     * Opens a pop-up window with error message.
     * @param mainFrame GUI frame where error message will be shown
     * @param header Header of error message
     * @param msg Error message
     */
    public static void error(final MainFrame mainFrame, final String header, final String msg) {
        JOptionPane.showMessageDialog(mainFrame, msg, header, JOptionPane.INFORMATION_MESSAGE);
    }
}
