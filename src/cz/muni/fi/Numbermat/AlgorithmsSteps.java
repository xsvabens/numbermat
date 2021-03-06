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

package cz.muni.fi.Numbermat;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import static cz.muni.fi.Numbermat.Utils.NEWLINE;

/**
 * Methods to generate step-by-step solutions to computations in Algorithms class.
 * 
 * @author Valdemar Svabensky <395868(at)mail(dot)muni(dot)cz>
 */
public final class AlgorithmsSteps {

    private AlgorithmsSteps() {
        throw new IllegalStateException(this.getClass().getName() +
                " class should not be instantiated.");
    }
    
    /*
    public static final String SMALL_A_ACUTE = "\u00e1";
    public static final String SMALL_E_ACUTE = "\u00e9";
    public static final String SMALL_I_ACUTE = "\u00ed";
    public static final String SMALL_U_RING = "\u016f";
    
    public static final String SMALL_C_CARON = "\u010d";
    public static final String SMALL_E_CARON = "\u011b";
    public static final String LARGE_R_CARON = "\u0158";
    public static final String SMALL_R_CARON = "\u0159";
    public static final String SMALL_S_CARON = "\u0161";
    public static final String SMALL_Z_CARON = "\u017e";
    */
    
    public static final String MULT = " * ";
    public static final String CONG = " \u2261 ";           // " ≡ "
    public static final String NOT_CONG = " \u2262 ";       // " ≢ "
    public static final String PHI = "\u03c6";              // "φ"
    public static final String PHIB = "\u03c6(";            // "φ("
    public static final String NOT_DIVIDES = " \u2224 ";    // "∤"
    public static final String TIMES = "\u00d7";            // "×"
    public static final String SIGMA = "\u03c3";            // "σ"
    public static final String CIRC = " \u25e6 ";           // " ◦ "
    
    public static final String SEPARATOR = "--------------------" + NEWLINE;
    
    public static final String NO_SOLUTION = "Neexistuje žádné řešení." + NEWLINE;
    public static final String INFINITE_SOLUTIONS = "Existuje nekonečně mnoho řešení." + NEWLINE;

    /**
     * Euclidean algorithm for finding the greatest common divisor of two integers.
     * @param a Integer
     * @param b Integer
     * @return Step-by-step solution to the greatest common divisor problem
     */
    public static String gcdSteps(int a, int b) {
        final StringBuilder gcdEquals = buildGCDEquals(a, b);
        if (a < 0) {
            if (b < 0)
                b = -b;
            return gcdEquals.append(buildGCDNewline(-a, b)).append(gcdSteps(-a, b)).toString();
        }
        if (b < 0)
            return gcdEquals.append(buildGCDNewline(a, -b)).append(gcdSteps(a, -b)).toString();
        if (a < b)
            return gcdEquals.append(buildGCDNewline(b, a)).append(gcdSteps(b, a)).toString();
        
        final StringBuilder result = new StringBuilder(128);
        while (b > 0) {
            int r = a % b;
            result.append(a).append(" = ");
            result.append(a / b).append(MULT);
            result.append(b).append(" + ");
            result.append(r).append(NEWLINE);
            a = b;
            b = r;
        }
        return result.append(gcdEquals).append(a).append(NEWLINE).toString();
    }
    
    private static StringBuilder buildGCD(final int a, final int b) {
        return new StringBuilder().append("(").append(a).append(", ").append(b).append(")");
    }
    
    /**
     * @param a Integer
     * @param b Integer
     * @return StringBuilder of form "(a, b) = "
     */
    public static StringBuilder buildGCDEquals(final int a, final int b) {
        return buildGCD(a, b).append(" = ");
    }
    
    private static StringBuilder buildGCDNewline(final int a, final int b) {
        return buildGCD(a, b).append(NEWLINE);
    }

    /**
     * Extended Euclidean algorithm for solving Bezout's identity.
     * @param a Non-negative integer, a >= b
     * @param b Non-negative integer
     * @return Step-by-step solution to solving Bezout's identity
     */
    public static String bezoutSteps(int a, int b) {
        Algorithms.notNegativeCheck(a);
        Algorithms.notNegativeCheck(b);
        Algorithms.notLessThanCheck(a, b);
        if (b == 0)
            return buildBezoutSolutionSimple(a, b, a, 1, 0).toString();
        
        final List<Integer> gcdStepsList = new ArrayList<>();
        while (b > 0) {
            int r = a % b;
            gcdStepsList.add(a);
            gcdStepsList.add(a / b);
            gcdStepsList.add(b);
            gcdStepsList.add(r);
            a = b;
            b = r;
        }
        // Convert ArrayList to array
        final Integer[] gcdSteps = gcdStepsList.toArray(
                new Integer[gcdStepsList.size()]);
        if (gcdSteps.length == 4)
            return buildBezoutSolutionSimple(a, b, gcdSteps[2], 0, 1).toString();
        
        final StringBuilder result = new StringBuilder(512);
        StringBuilder line = new StringBuilder(64);
        line.append(a).append(" = ");
        line.append(gcdSteps[gcdSteps.length - 8]).append(" - ");
        line.append(gcdSteps[gcdSteps.length - 7]).append(MULT);
        line.append(gcdSteps[gcdSteps.length - 6]).append(NEWLINE);
        result.append(line);
        
        boolean swapMinusSign = false; // minuses alternate every second iteration
        for (int i = gcdSteps.length - 9; i > -1; i -= 4) {
            final StringBuilder replacement = new StringBuilder();
            replacement.append("(").append(gcdSteps[i - 3]);
            replacement.append(" - ").append(gcdSteps[i - 2]);
            replacement.append(MULT).append(gcdSteps[i - 1]).append(")");
            final int replaceIndex = line.lastIndexOf((gcdSteps[i]).toString());
            line.replace(replaceIndex, replaceIndex + replacement.length(),
                    replacement.toString());
            
            result.append(line).append(NEWLINE);
            line = new StringBuilder(64);
            gcdSteps[i - 2] *= gcdSteps[i + 2];
            if (i == gcdSteps.length - 9) {
                gcdSteps[i - 2] += 1;
            } else {
                gcdSteps[i - 2] += gcdSteps[i + 6];
            }
            line.append(a).append(" = ");
            if (swapMinusSign) {
                line.append(gcdSteps[i + 2]);
            } else {
                line.append(gcdSteps[i + 2] * (-1));
            }
            line.append(MULT).append(gcdSteps[i - 3]);
            if (swapMinusSign) {
                line.append(" - ");
                swapMinusSign = false;
            } else {
                line.append(" + ");
                swapMinusSign = true;
            }
            line.append(gcdSteps[i - 2]).append(MULT);
            line.append(gcdSteps[i - 1]).append(NEWLINE);
            result.append(line);
        }
        result.append("x = ");
        if (swapMinusSign) {
            result.append(gcdSteps[5] * (-1));
        } else {
            if (gcdSteps.length == 8) {
                result.append(gcdSteps[6]);
            } else {
                result.append(gcdSteps[5]);
            }
        }
        result.append(", y = ");
        if (swapMinusSign) {
            result.append(gcdSteps[1]);
        } else {
            result.append(gcdSteps[1] * (-1));
        }
        return result.append(NEWLINE).toString();
    }

    private static StringBuilder buildBezoutSolutionSimple(final int a, final int b,
            final int d, final int x, final int y) {
        final StringBuilder result = new StringBuilder(32);
        result.append(d).append(" = ").append(x).append(MULT).append(a);
        result.append(" + ").append(y).append(MULT).append(b).append(NEWLINE);
        result.append("x = ").append(x).append(", y = ").append(y).append(NEWLINE);
        return result;
    }
    
    /**
     * Euler's totient (phi) function.
     * @param n Positive integer
     * @return Step-by-step solution to finding the value of phi n
     */
    public static String eulerPhiSteps(final int n) {
        Algorithms.positiveCheck(n);
        if (n < 2)
            return buildPhiEquals(n).append(n).toString();
        
        String factorization = factorizeSteps(n);
        final StringBuilder result = new StringBuilder(256);
        result.append(factorization).append(NEWLINE);
        factorization = factorization.replaceFirst(n + " = ", "");
        result.append(buildPhiEquals(n)).append(buildPhi(factorization));
        
        final StringBuilder tmp = new StringBuilder(factorization.replaceAll(
                " \\* ", ") \\*" + PHIB));
        if (!tmp.toString().equals(factorization))
            result.append(buildPhiEquals(n)).append(buildPhi(tmp.toString()));
        
        result.append(buildPhiEquals(n));
        int phi = 1;
        final List<Pair<Integer, Integer>> factors = Algorithms.factorize(n);
        final int factSize = factors.size();
        for (int i = 0; i < factSize; ++i) {
            final int prime = factors.get(i).getFirst();
            final int exponent = factors.get(i).getSecond();
            phi *= prime - 1;
            phi *= Math.pow(prime, exponent - 1);
            result.append("(").append(prime - 1).append(MULT);
            result.append(buildPower(prime, exponent - 1)).append(")");
            if (!Utils.lastForCycle(i, factSize))
                result.append(MULT);
        }
        result.append(NEWLINE).append(buildPhiEquals(n));
        for (int i = 0; i < factSize; ++i) {
            final int prime = factors.get(i).getFirst();
            final int exponent = factors.get(i).getSecond();
            result.append((prime - 1) * (int)(Math.pow(prime, exponent - 1)));
            if (!Utils.lastForCycle(i, factSize))
                result.append(MULT);
        }
        result.append(NEWLINE);
        if (factSize > 1)
            result.append(buildPhiEquals(n)).append(phi).append(NEWLINE);
        return result.toString();
    }
    
    /**
     * @param n Non-negative integer (check is performed in Algorithms class)
     * @return String of form "n = p1^e1 * p2^e2 * ... * pn^en"
     */
    private static String factorizeSteps(final int n) {
        final List<Pair<Integer, Integer>> factors = Algorithms.factorize(n);
        final int factSize = factors.size();
        final StringBuilder result = new StringBuilder().append(n).append(" = ");
        for (int i = 0; i < factSize; ++i) {
            final int prime = factors.get(i).getFirst();
            final int exponent = factors.get(i).getSecond();
            result.append(buildPower(prime, exponent));
            if (!Utils.lastForCycle(i, factSize))
                result.append(MULT);
        }
        return result.toString();
    }
    
    private static StringBuilder buildPower(final int base, final int exp) {
        final StringBuilder sb = new StringBuilder().append(base).append("^");
        if (exp > 9)
            sb.append("{").append(exp).append("}");
        else
            sb.append(exp);
        return sb;
    }
    
    private static StringBuilder buildPhi(final int n) {
        return new StringBuilder().append(PHIB).append(n).append(")");
    }
    
    /**
     * @param n Integer
     * @return StringBuilder of form "φ(n) = "
     */
    public static StringBuilder buildPhiEquals(final int n) {
        return buildPhi(n).append(" = ");
    }
    
    private static StringBuilder buildPhi(final String s) {
        return new StringBuilder().append(PHIB).append(s).append(")").append(NEWLINE);
    }
    
    /**
     * Steps of solving a single linear congruence ax ≡ b (mod n).
     * @param a Integer
     * @param b Integer
     * @param n Positive integer
     * @return Step-by-step solution for a single congruence.
     */
    private static String linearCongruenceSteps(int a, int b, final int n) {
        Algorithms.positiveCheck(n);
        StringBuilder lineEnd = buildModLineEnd(n);
        final StringBuilder result = buildLinearCongruence(a, 'x', b, lineEnd);
        final int originalA = a;
        final int originalB = b;
        if (normalizeIntModuloChanges(a, b, n)) {
            a = Algorithms.normalizeIntModulo(a, n);
            b = Algorithms.normalizeIntModulo(b, n);
            result.append(buildLinearCongruence(a, 'x', b, lineEnd));
        }
        if (a == 1)
            return result.toString();
        if ((a == 0) && (b == 0))
            return result.append(INFINITE_SOLUTIONS).toString();
        if ((a == 0) && (b != 0))
            return result.append(NO_SOLUTION).toString();
        
        // Natural solution
        StringBuilder naturalSolution = linearCongruenceStepsNatural(
                originalA, originalB, n, new StringBuilder(128));
        if (naturalSolution.length() != 0)
            return naturalSolution.toString();
        
        // Bezout's identity solution
        final int numberOfSolutions = Algorithms.gcd(a, n);
        result.append(buildGCDEquals(a, n)).append(numberOfSolutions);
        if (b % numberOfSolutions != 0) {
            result.append(NOT_DIVIDES).append(b).append(NEWLINE);
            return result.append(NO_SOLUTION).toString();
        }
        result.append(" | ").append(b).append(NEWLINE);
        result.append(numberOfSolutions).append(" = ").append(a);
        result.append("r + ").append(n).append("s").append(NEWLINE);
        int bezoutCoefficientA; // coeffient r such that ra + sn = gcd(a, n)
        if (a > n) {
            bezoutCoefficientA = Algorithms.bezout(a, n).get(1);
        } else {
            bezoutCoefficientA = Algorithms.bezout(n, a).get(2);
        }
        final int shiftedModulus = n / numberOfSolutions;
        int x = (bezoutCoefficientA * b) / numberOfSolutions;
        x = Algorithms.normalizeIntModulo(x, shiftedModulus);
        result.append("r = ").append(bezoutCoefficientA).append(NEWLINE);
        lineEnd = buildModLineEnd(shiftedModulus);
        result.append("x").append(CONG).append(b).append("r / ");
        result.append(numberOfSolutions).append(lineEnd);
        result.append(buildLinearCongruence(1, 'x', x, lineEnd));
        return result.toString();
    }
    
    /**
     * Try to solve the congruence as "on paper" instead of
     * Bezout's identity solution.
     * @param a Integer (non-zero, > 1)
     * @param b Integer (non-zero)
     * @param n Positive integer (checked)
     * @param result StringBuilder from previous run (empty in the beginnning)
     * @return Step-by-step natural solution or empty StringBuilder
     */
    private static StringBuilder linearCongruenceStepsNatural(int a, int b, int n,
            StringBuilder result) {
        
        final boolean firstRun = result.length() == 0;
        StringBuilder lineEnd = buildModLineEnd(n);
        if (firstRun)
            result.append(buildLinearCongruence(a, 'x', b, lineEnd));
        final int normA = Algorithms.normalizeIntModulo(a, n);
        final int normB = Algorithms.normalizeIntModulo(b, n);
        
        /**
         * Let d be a small divisor of both (normalized) a and b.
         * Try to find this divisor and divide the input congruence by it.
         * (By 'small' we will mean between 2 and 13, inclusive.)
         */
        int d = 1;
        if (normB != 0)
            d = linearCongruenceStepsNaturalSearch(Algorithms.commonDivisors(normA, normB));
        if ((d > 1) && firstRun) {
            if ((normA != a) || (normB != b))
                result.append(buildLinearCongruence(normA, 'x', normB, lineEnd));
            a = normA;
            b = normB;
        } else { // Try to find d for original a, b.
            d = linearCongruenceStepsNaturalSearch(Algorithms.commonDivisors(a, b));
        }
        
        int gcd = Algorithms.gcd(n, d);
        if (gcd > 1)
            d = gcd;
        if (d > 1) { // We found d in either of the branches
            result = new StringBuilder(Utils.replaceLast(
                    result.toString(), NEWLINE, "   /÷" + d + NEWLINE));
            a /= d;
            b /= d;
            if (n % d == 0)
                n /= d;
            lineEnd = buildModLineEnd(n);
            result.append(buildLinearCongruence(a, 'x', b, lineEnd));
            if (a == 1)
                return result;
        }
        
        /**
         * Now a is still > 1, so let d be a small divisor of a.
         * Try to find d and then search for k such that nk ≡ -b (mod d).
         * It holds that the congruence ax ≡ b + nk (mod n) is divisible by d.
         */
        d = linearCongruenceStepsNaturalSearch(Algorithms.divisors(a));
        gcd = Algorithms.gcd(n, d);
        if (gcd > 1)
            d = gcd;
        if (d > 1) {
            final Pair<Integer, Integer> partial =
                    Algorithms.linearCongruence(n, -b, d);
            if (!partial.isEmpty()) {
                final int k = partial.getFirst();
                b = b + n*k;
                if (k != 0) {
                    result = new StringBuilder(Utils.replaceLast(
                            result.toString(), NEWLINE, "   /+" + n*k + NEWLINE));
                    result.append(buildLinearCongruence(a, 'x', b, lineEnd));
                }
                result = new StringBuilder(Utils.replaceLast(
                        result.toString(), NEWLINE, "   /÷" + d + NEWLINE));
                a /= d;
                b /= d;
                if (n % d == 0)
                    n /= d;
                lineEnd = buildModLineEnd(n);
                result.append(buildLinearCongruence(a, 'x', b, lineEnd));
                if (a == 1)
                    return result;
            }
        }
        
        if (firstRun) {
            /* Rerun the process once more. */
            StringBuilder sb = linearCongruenceStepsNatural(a, b, n, result);
            if (linearCongruenceStepsNaturalFinished(sb))
                return sb;
        
            /* 
             * Now if a is still > 1, check a +/- n
             * (according to which of these has a small divisor).
             */
            if (linearCongruenceStepsNaturalSearch(Algorithms.divisors(a + n)) > 1) {
                sb = linearCongruenceStepsNatural(a + n, b, n, result);
                if (linearCongruenceStepsNaturalFinished(sb))
                    return sb;
            }
            if (linearCongruenceStepsNaturalSearch(Algorithms.divisors(a - n)) > 1) {
                sb = linearCongruenceStepsNatural(a - n, b, n, result);
                if (linearCongruenceStepsNaturalFinished(sb))
                    return sb;
            }
        }
        return new StringBuilder();
    }
    
    /**
     * Search for highest possible d (see above).
     * @param divisors List where to search
     * @return d
     */
    private static int linearCongruenceStepsNaturalSearch(
            final List<Integer> divisors) {
        int d = divisors.remove(0); // remove 1 and initialize d
        for (int i = 0; i < divisors.size(); ++i) {
            final int di = divisors.get(i);
            if (di > 13)
                break;
            if (di > d)
                d = di;
        }
        return d;
    }
    
    private static boolean linearCongruenceStepsNaturalFinished(final StringBuilder sb) {
        if (sb.length() == 0)
            return false;
        // Solution is finished when it has the form x ≡ b (mod n)
        return !(Character.isDigit(sb.substring(
                sb.lastIndexOf("x") - 1).charAt(0)));
    }
    
    /**
     * @param a Integer
     * @param x Char with variable name
     * @param b Integer
     * @param lineEnd StringBuilder containing line end, usually " (mod n)\n"
     * @return StringBuilder of form "a'x' ≡ b (mod n)\n"
     */
    public static StringBuilder buildLinearCongruence(final int a, final char x,
            final int b, final StringBuilder lineEnd) {
        
        return new StringBuilder().append((a == 1) ? "" : a).append(x)
                .append(CONG).append(b).append(lineEnd);
    }
    
    private static StringBuilder buildLinearCongruenceIndex(final int a,
            final char x, final int i, final int b, final StringBuilder lineEnd) {
        
        final StringBuilder sb = buildLinearCongruence(a, x, b, lineEnd);
        return new StringBuilder(sb.toString().replace(x + "", x + "_" + i));
    }
    
    /**
     * @param m Integer
     * @return StringBuilder of form " (mod n)\n"
     */
    public static StringBuilder buildModLineEnd(final int m) {
        return new StringBuilder(" (mod ").append(m).append(")").append(NEWLINE);
    }
    
    /*
     * Checks if normalizing integers modulo 'mod' changes the numbers.
     * @param Integers ending with modulus 'mod'
     * @return True if any of the integers changes, false if all stays the same.
     */
    private static boolean normalizeIntModuloChanges(final int... ints) {
        final int size = ints.length;
        final int mod = ints[size - 1];
        boolean changes = false;
        for (int i = 0; i < size - 1; ++i) {
            final int a = ints[i];
            changes = (changes) || (a != Algorithms.normalizeIntModulo(a, mod));
        }
        return changes;
    }
    
    /**
     * @param count Number of equations, positive integer
     * @param aList List of integers
     * @param bList List of integers
     * @param nList List of positive integers
     * @return Steps to solving a system of 'i' congruences of type a_i x ≡ b_i (mod n_i).
     */
    public static String linearCongruenceSystemSteps(final int count,
            final List<Integer> aList, final List<Integer> bList, final List<Integer> nList) {
        
        Algorithms.positiveCheck(count);
        Algorithms.listCheck(count, aList);
        Algorithms.listCheck(count, bList);
        Algorithms.listCheck(count, nList);
        if (count == 1)
            return linearCongruenceSteps(aList.get(0), bList.get(0), nList.get(0));

        final StringBuilder result = new StringBuilder(512);
        final List<StringBuilder> lineEnds = new ArrayList<>(count);
        boolean coefficentsModded = false;
        for (int i = 0; i < count; ++i) {
            final int ai = aList.get(i);
            final int bi = bList.get(i);
            final int ni = nList.get(i);
            Algorithms.positiveCheck(ni);
            aList.set(i, Algorithms.normalizeIntModulo(ai, ni));
            bList.set(i, Algorithms.normalizeIntModulo(bi, ni));
            if ((aList.get(i) != ai) || (bList.get(i) != bi))
                coefficentsModded = true;
            lineEnds.add(buildModLineEnd(ni));
            result.append(buildLinearCongruence(ai, 'x', bi, lineEnds.get(i)));
        }
        result.append(SEPARATOR);
        
        if (coefficentsModded) {
            for (int i = 0; i < count; ++i) {
                final int ai = aList.get(i);
                final int bi = bList.get(i);
                result.append(buildLinearCongruence(ai, 'x', bi, lineEnds.get(i)));
            }
            result.append(SEPARATOR);
        }
        
        final Pair<Integer, Integer> solution = new Pair<>();
        for (int i = 0; i < count; ++i) {
            final Pair<Integer, Integer> partial = Algorithms.linearCongruence(
                    aList.get(i), bList.get(i), nList.get(i));
            if (partial.isEmpty())
                return result.append(NO_SOLUTION).toString();
            if (partial.equals(new Pair<>(0, 1))) {
                if ((solution.isEmpty()) && (Utils.lastForCycle(i, count)))
                    return result.append(SEPARATOR).append(INFINITE_SOLUTIONS).toString();
                continue;
            }
            final int partialX = partial.getFirst();
            final int partialM = partial.getSecond();
            
            if (solution.isEmpty()) {
                solution.setFirst(partialX);
                solution.setSecond(partialM);
            } else {
                solution.setFirst(solution.getFirst() + solution.getSecond() * partialX);
                solution.setSecond(solution.getSecond() * partialM);
            }
            
            if (i > 0) {
                result.append((char)('j' + i)).append(" = ").append(partialX).append(" + ");
                result.append(partialM).append((char)('k' + i)).append(NEWLINE);
            }
            
            final int solutionX = solution.getFirst();
            final int solutionM = solution.getSecond();
            final StringBuilder xEquals = new StringBuilder().append(solutionX);
            xEquals.append(" + ").append(solutionM).append((char)(107 + i));
            result.append("x = ").append(xEquals).append(NEWLINE);
            
            if (!Utils.lastForCycle(i, count)) {
                result.append(SEPARATOR);
                final int nextA = aList.get(i + 1);
                if (nextA != 1)
                    result.append(nextA).append("(").append(xEquals).append(")");
                else
                    result.append(xEquals);
                result.append(CONG).append(bList.get(i + 1)).append(lineEnds.get(i + 1));
                bList.set(i + 1, bList.get(i + 1) - (aList.get(i + 1) * solutionX));
                aList.set(i + 1, aList.get(i + 1) * solutionM);
            }
        }
        result.append(buildLinearCongruence(1, 'x', solution.getFirst(),
                buildModLineEnd(solution.getSecond())));
        return result.toString();
    }
    
    /**
     * @param element Positive integer (check is performed in Algorithms class)
     * @param n Integer > 1 (check is performed in Algorithms class)
     * @return Step-by-step solution to finding order of element 'element' in group Zn×
     */
    public static String unitGroupElementOrderSteps(final int element, final int n) {
        final int elementOrder = Algorithms.unitGroupElementOrder(element, n);
        final int groupOrder = Algorithms.eulerPhi(n);
        final List<Integer> groupOrderDivisors = Algorithms.divisors(groupOrder);
        
        StringBuilder result = new StringBuilder(512);
        result.append(AlgorithmsSteps.elementsOfUnitGroupSteps(n));
        result.append("Řád grupy: ").append(buildPhiEquals(n)).append(groupOrder).append(NEWLINE);
        result.append("Možné řády prvků: ").append(groupOrderDivisors).append(NEWLINE);
        result = new StringBuilder(result.toString().replaceAll("\\[", "\\\\{"));
        result = new StringBuilder(result.toString().replaceAll("\\]", "\\\\}"));
        
        final StringBuilder lineEnd = new StringBuilder("1 ").append(buildModLineEnd(n));
        for (int i = 0; i < groupOrderDivisors.size(); ++i) {
            final int currentDivisor = groupOrderDivisors.get(i);
            if (currentDivisor == elementOrder)
                break;
            result.append(buildPower(element, currentDivisor)).append(NOT_CONG).append(lineEnd);
        }
        result.append(buildPower(element, elementOrder)).append(CONG).append(lineEnd);
        result.append("Řád prvku [").append(element).append("] je ");
        result.append(elementOrder).append(".").append(NEWLINE);
        return result.toString();
    }
    
    /**
     * @param n Integer > 1
     * @return Steps to finding elements of unit group Zn^×.
     */
    private static String elementsOfUnitGroupSteps(final int n) {
        Algorithms.notLessThanCheck(n, 2);
        final StringBuilder result = new StringBuilder(64);
        result.append("Z").append(n).append(TIMES).append(" = ");
        if (Algorithms.isPrime(n))
            return result.append("Z").append(n).append("*").append(NEWLINE).toString();
        
        result.append("\\{");
        for (int i = 1; i < n; ++i) {
            if (Algorithms.isCoprime(i, n)) {
                result.append(i);
                if (i < n - 1)
                    result.append(", ");
            }
        }
        return result.append("\\}").append(NEWLINE).toString();
    }
    
    /**
     * @param base Integer
     * @param exp Non-negative integer
     * @param mod Positive integer
     * @return Steps of finding (base^exp) mod (mod)
     */
    public static String modPowSteps(int base, int exp, final int mod) {
        Algorithms.notNegativeCheck(exp);
        Algorithms.positiveCheck(mod);
        StringBuilder lineStart = buildModPowLineStart(base, exp);
        final StringBuilder lineEnd = buildModLineEnd(mod);
        final StringBuilder result = new StringBuilder(128);
        if (normalizeIntModuloChanges(base, mod)) {
            base = Algorithms.normalizeIntModulo(base, mod);
            result.append(lineStart).append(buildPower(base, exp)).append(lineEnd);
            lineStart = buildModPowLineStart(base, exp);
        }
        if (mod == 1)
            return modPowReturn(0, result.append(CONG), lineEnd);
        if (base == 0)
            return modPowReturn((exp == 0 ? 1 : 0), result.append(CONG), lineEnd);
        if ((base == 1) || (exp == 0))
            return modPowReturn(1, result.append(CONG), lineEnd);
        if (base == -1)
            return modPowReturn((exp % 2 == 0 ? 1 : -1 + mod), result.append(CONG), lineEnd);
        if (exp == 1)
            return modPowReturn(base, result.append(CONG), lineEnd);

        // Find phi
        boolean appendCongSymbol = false;
        if (Algorithms.isCoprime(base, mod)) {
            final int phi = Algorithms.eulerPhi(mod);
            if (normalizeIntModuloChanges(exp, phi)) {
                result.append(buildPhiEquals(mod)).append(phi).append(NEWLINE);
                result.append(SEPARATOR).append(lineStart);
                
                exp = Algorithms.normalizeIntModulo(exp, phi);
                if (exp == 0)
                    return modPowReturn(1, result, lineEnd);
                if (exp == 1)
                    return modPowReturn(base, result, lineEnd);
 
                result.append(buildPower(base, exp)).append(lineEnd);
                lineStart = buildModPowLineStart(base, exp);
                appendCongSymbol = true;
            }
        }
        
        // Find element order
        try {
            final int order = Algorithms.unitGroupElementOrder(base, mod);
            if (normalizeIntModuloChanges(exp, order)) {
                if (appendCongSymbol)
                    result.append(CONG);
                else
                    result.append(lineStart);
                StringBuilder trailing = new StringBuilder();
                if (exp % order != 0)
                    trailing = buildPower(base, exp % order).append(MULT);
                result.append(trailing);
                if (exp / order > 1) {
                    result.append("(").append(buildPower(base, order)).append(")");
                    result.append("^{").append(exp / order).append("}");
                } else {
                    result.append(buildPower(base, order));
                }
                result.append(lineEnd).append(CONG).append(trailing);
                result.append(buildPower(1, exp / order)).append(lineEnd);
                result.append(CONG);
                        
                exp = Algorithms.normalizeIntModulo(exp, order);
                if (exp == 0)
                    return modPowReturn(1, result, lineEnd);
                if (exp == 1)
                    return modPowReturn(base, result, lineEnd);
                
                result.append(buildPower(base, exp)).append(lineEnd);
                lineStart = buildModPowLineStart(base, exp);
                appendCongSymbol = true;
            }
        } catch (IllegalArgumentException ex) {
        }
        
        // Try to factorize mod
        final List<Pair<Integer, Integer>> factors = Algorithms.factorize(mod);
        final int factSize = factors.size();
        if (factSize > 1) {
            final List<Integer> subMods = new ArrayList<>();
            final List<StringBuilder> lineEnds = new ArrayList<>();
            for (int i = 0; i < factSize; ++i) {
                final int p = factors.get(i).getFirst();
                final int e = factors.get(i).getSecond();
                final int m = (int)Math.pow(p, e);
                subMods.add(m);
                lineEnds.add(buildModLineEnd(m));
            }
            
            if (result.indexOf(NEWLINE) != result.lastIndexOf(NEWLINE))
                result.append(SEPARATOR);
            result.append(factorizeSteps(mod)).append(NEWLINE).append(SEPARATOR);
            
            for (int i = 0; i < factSize; ++i) {
                final int subResult = Algorithms.modPow(base, exp, subMods.get(i));
                result.append(buildModPowResult(subResult, lineStart, lineEnds.get(i)));
            }
            result.append(SEPARATOR);
            appendCongSymbol = false;
        }
        
        result.append(appendCongSymbol ? CONG : lineStart);
        return modPowReturn(Algorithms.modPow(base, exp, mod), result, lineEnd);
    }
    
    private static String modPowReturn(final int value,
            final StringBuilder result, final StringBuilder lineEnd) {
        
        return result.append(buildModPowResult(
                value, new StringBuilder(""), lineEnd)).toString();
    }
    
    private static StringBuilder buildModPowResult(final int result,
            final StringBuilder lineStart, final StringBuilder lineEnd) {
        
        return new StringBuilder().append(lineStart).append(result).append(lineEnd);
    }
    
    /**
     * @param base Integer
     * @param exp Integer
     * @return StringBuilder of form "base^exp ≡ "
     */
    public static StringBuilder buildModPowLineStart(final int base, final int exp) {
        return new StringBuilder().append(buildPower(base, exp)).append(CONG);
    }
    
    /**
     * @param a Integer
     * @param p Odd prime (check is performed in Algorithms class)
     * @return Steps of computing the Legendre symbol (a/p)
     */
    public static String legendreSymbolSteps(int a, int p) {
        final StringBuilder result = buildLegendreSymbolEquals(a, p);             
        if (normalizeIntModuloChanges(a, p)) {
            a = Algorithms.normalizeIntModulo(a, p);
            result.append(buildLegendreSymbolNewlineEquals(a, p));
        }
        if (legendreSymbolFinish(a))
            return legendreSymbolReturn(result, a, p);
        
        // Try to flip a, p
        boolean minus = false;
        if ((a < p) && (a != 2) && (Algorithms.isPrime(a))) {
            if ((a % 4 != 1) && (p % 4 != 1)) {
                minus = true;
                result.append("-");
            }
            result.append(buildLegendreSymbolNewlineEquals(p, a));
            int tmp = a;
            a = p;
            p = tmp;
            
            if (normalizeIntModuloChanges(a, p)) {
                a = Algorithms.normalizeIntModulo(a, p);
                if (minus)
                    result.append("-");
                result.append(buildLegendreSymbolNewlineEquals(a, p));
            }
        }
        if (legendreSymbolFinish(a))
            return legendreSymbolReturn(result, a, p);
        
        // Factorize a
        if (!Algorithms.isPrime(a)) {
            final List<Pair<Integer, Integer>> factors = Algorithms.factorize(a);
            final int factSize = factors.size();
            final List<Integer> values = new ArrayList<>();
            if (minus)
                result.append("-");
            boolean anyAppendsOnResult = false;
            for (int i = 0; i < factSize; ++i) {
                // Try to make a perfect square out of pi^ei
                int pi = factors.get(i).getFirst();
                int ei = factors.get(i).getSecond();
                int perfectPi = pi;
                int perfectEi = 1; // pi^perfectEi is perfect square
                for (int j = 0; j < ei - 1; ++j) {
                    if (Algorithms.isPerfectSquare(pi))
                        break;
                    perfectPi *= perfectPi;
                    ++perfectEi;
                }
                
                final int perfectFactor = perfectPi;
                final int restFactor = (int)Math.pow(pi, ei);
                if ((factSize > 1) && (restFactor != a)) {
                    anyAppendsOnResult = true;
                    result.append(buildLegendreSymbol(restFactor, p));
                }
                values.add(Algorithms.legendreSymbol(restFactor, p));
                if (perfectEi != ei) {
                    anyAppendsOnResult = true;
                    result.append(MULT).append(buildLegendreSymbol(perfectFactor, p));
                    values.add(Algorithms.legendreSymbol(perfectFactor, p));
                }
                if (!Utils.lastForCycle(i, factSize))
                    result.append(MULT);
            }
            if (anyAppendsOnResult) {
                result.append(NEWLINE).append(" = ");
                if (minus)
                    result.append("-");
                // Now evaluate all factors
                final int valuesSize = values.size();
                for (int i = 0; i < valuesSize; ++i) {
                    final int vi = values.get(i);
                    result.append(vi == -1 ? ("(" + vi + ")") : vi);
                    if (!Utils.lastForCycle(i, valuesSize))
                        result.append(MULT);
                }
                result.append(NEWLINE).append(" = ");
            }
        }
        return legendreSymbolReturn(result, a, p).replaceAll("--", "");
    }
    
    private static boolean legendreSymbolFinish(final int a) {
        final int absA = Math.abs(a);
        return (absA < 4) || (Algorithms.isPerfectSquare(absA));
    }
    
    private static String legendreSymbolReturn(final StringBuilder result,
            final int a, final int p) {
        
        StringBuilder sb = new StringBuilder();
        final int value = Algorithms.legendreSymbol(a, p);
        if ((a > 0) && (Algorithms.isPerfectSquare(a)))
            sb.append(a).append("\\text{ je druhou mocninou}& \\text{ čísla }")
                    .append((int)Math.sqrt(a)).append("\\text{.}").append(NEWLINE);
        return result.append(value).append(NEWLINE).append(sb).toString();
    }

    private static StringBuilder buildLegendreSymbol(final int a, final int p) {
        return new StringBuilder("(").append(a).append("/").append(p).append(")");
    }
    
    /**
     * @param a Integer
     * @param p Integer
     * @return StringBuilder of form "(a/p) = "
     */
    public static StringBuilder buildLegendreSymbolEquals(final int a, final int p) {
        return buildLegendreSymbol(a, p).append(" = ");
    }
    
    private static StringBuilder buildLegendreSymbolNewlineEquals(final int a, final int p) {
        return buildLegendreSymbol(a, p).append(NEWLINE).append(" = ");
    }
    
    
    /**
     * @param x Char, variable symbol
     * @param a Integer
     * @param m Positive integer
     * @return Steps in finding a solution for congruence of form 'x'^2 ≡ a (mod m).
     */
    public static String quadraticCongruenceSimpleSteps(final char x, int a, final int m) {
        Algorithms.positiveCheck(m);
        final StringBuilder lineEnd = buildModLineEnd(m);
        final StringBuilder result = buildQuadraticCongruence(x, a, lineEnd);
        if (normalizeIntModuloChanges(a, m)) {
            a = Algorithms.normalizeIntModulo(a, m);
            result.append(buildQuadraticCongruence(x, a, lineEnd));
        }
        final List<Integer> solution = Algorithms.quadraticCongruenceSimple(a, m);
        final StringBuilder solutionSB =
                quadraticCongruenceSimpleBuildSolution(solution, x, true);
        if ((a == 0) || (Algorithms.isPowerOf2(m)))
            return result.append(solutionSB).toString();
        
        final List<Pair<Integer, Integer>> factors = Algorithms.factorize(m);
        final int factSize = factors.size();
        if ((factSize > 1) && (factSize < 5)) {
            final List<Integer> subMods = new ArrayList<>();
            final List<StringBuilder> lineEnds = new ArrayList<>();
            for (int i = 0; i < factSize; ++i) {
                final int pi = factors.get(i).getFirst();
                if ((pi != 2) && (Algorithms.legendreSymbol(a, pi) == -1)) {
                    result.append(SEPARATOR);
                    result.append(AlgorithmsSteps.factorizeSteps(m)).append(NEWLINE);
                    result.append(AlgorithmsSteps.legendreSymbolSteps(a, pi));
                    return result.append(NO_SOLUTION).toString();
                }
                final int ei = factors.get(i).getSecond();
                final int mi = (int)Math.pow(pi, ei);
                subMods.add(mi);
                lineEnds.add(buildModLineEnd(mi));
            }
            if (m > 13) {
                result.append(SEPARATOR);
                for (int i = 0; i < lineEnds.size(); ++i)
                    result.append(buildQuadraticCongruence(x, a, lineEnds.get(i)));
                for (int i = 0; i < subMods.size(); ++i) {
                    final List<Integer> subResult =
                            Algorithms.quadraticCongruenceSimple(a, subMods.get(i));
                    result.append(quadraticCongruenceSimpleBuildSolution(
                            subResult, x, false));
                }
            }
        }
        return result.append(solutionSB).toString();
    }
    
    private static StringBuilder buildQuadraticCongruence(final char x,
            final int a, final StringBuilder lineEnd) {
        return buildBinomialCongruence(1, x, 2, 0, -a, lineEnd);
    }
    
    /**
     * @param a Integer
     * @param x Char, variable symbol
     * @param exp Positive integer
     * @param b Integer
     * @param c Integer
     * @param lineEnd StringBuilder containing line end, usually " (mod n)\n"
     * @return StringBuilder of form "a'x'^{exp} + b'x' + c ≡ 0 (mod m)\n"
     */
    public static StringBuilder buildBinomialCongruence(final int a, final char x,
            final int exp, final int b, final int c, final StringBuilder lineEnd) {
        
        Algorithms.positiveCheck(exp);
        if (exp == 1)
            return buildLinearCongruence(a + b, x, -c, lineEnd);
        if (a == 0)
            return buildLinearCongruence(b, x, -c, lineEnd);
        
        final StringBuilder sb = new StringBuilder().append((a == 1) ? "" : a);
        sb.append(x).append("^");
        if (exp > 9)
            sb.append("{").append(exp).append("}");
        else
            sb.append(exp);
        
        if ((a == 1) && (b == 0))
            return sb.append(CONG).append(-c).append(lineEnd); // 'x'^2 ≡ -c (mod m)
        
        if (b != 0)
            sb.append(buildCoefficient(b, false)).append(x);
        sb.append(buildCoefficient(c, true));
        return sb.append(CONG).append("0").append(lineEnd);
    }
    
    private static StringBuilder buildCoefficient(final int n, final boolean absolute) {
        final StringBuilder sb = new StringBuilder();
        if (n == 0) 
            return sb;
        
        sb.append(n > 0 ? " + " : " - ");
        if ((absolute) || (Math.abs(n) != 1))
            sb.append((n > 0) ? n : -n);
        return sb;
    }

    private static StringBuilder quadraticCongruenceSimpleBuildSolution(
            final List<Integer> solution, final char x, final boolean subscripts) {
        
        if (solution.isEmpty())
            return new StringBuilder(NO_SOLUTION);
        
        final int lastIndex = solution.size() - 1;
        final StringBuilder lineEnd = buildModLineEnd(solution.get(lastIndex));
        final StringBuilder result = new StringBuilder(SEPARATOR);
        if (subscripts) {
            for (int i = 0; i < lastIndex; ++i)
                result.append(buildLinearCongruenceIndex(
                        1, x, i + 1, solution.get(i), lineEnd));
        } else {
            for (int i = 0; i < lastIndex; ++i)
                result.append(buildLinearCongruence(
                        1, x, solution.get(i), lineEnd));
        }
        return result;
    }
    
    /**
     * @param a Integer
     * @param b Integer
     * @param c Integer
     * @param m Positive integer coprime with a
     * @return Steps in finding a solution for congruence ax^2 + bx + c ≡ 0 (mod m)
     */
    public static String quadraticCongruenceGeneralSteps(int a, int b, int c, final int m) {
        Algorithms.positiveCheck(m);
        Algorithms.isCoprimeCheck(a, m);
        final StringBuilder lineEnd = buildModLineEnd(m);
        final StringBuilder result = buildBinomialCongruence(a, 'x', 2, b, c, lineEnd);
        if (m == 1)
            return result.append(INFINITE_SOLUTIONS).toString();
        
        if (normalizeIntModuloChanges(a, b, c, m)) {
            a = Algorithms.normalizeIntModulo(a, m);
            b = Algorithms.normalizeIntModulo(b, m);
            c = Algorithms.normalizeIntModulo(c, m);
            result.append(buildBinomialCongruence(a, 'x', 2, b, c, lineEnd));
        }
        if (a == 0)
            return linearCongruenceSteps(b, -c, m);
        if ((a == 1) && (b == 0))
            return quadraticCongruenceSimpleSteps('x', -c, m);
        
        final List<Integer> solution =
                Algorithms.quadraticCongruenceGeneral(a, b, c, m);
        final StringBuilder solutionSB =
                quadraticCongruenceSimpleBuildSolution(solution, 'x', true);
        final StringBuilder naturalSolution =
                quadraticCongruenceStepsNatural(a, b, c, m);
        if (naturalSolution.length() != 0)
            return result.append(naturalSolution).append(solutionSB).toString();
        
        final int D = b*b - 4*a*c;
        result.append("D = ").append(D).append(NEWLINE).append(SEPARATOR);
        result.append(quadraticCongruenceSimpleSteps('t', D, 4*m));
        final List<Integer> subResult = Algorithms.quadraticCongruenceSimple(D, 4*m);
        if (subResult.isEmpty())
            return result.toString();
        
        result.append(SEPARATOR);
        final List<Integer> generated =
                Algorithms.quadraticCongruenceGeneralGenerate(subResult, m);
        final int terminator = generated.size();
        for (int i = 0; i < terminator - 1; ++i) {
            final int t = generated.get(i);
            if (((t - b) % 2) != 0)
                throw new RuntimeException("Computational error.");
            
            final boolean lastCycle = Utils.lastForCycle(i, terminator);
            final int newA = (t - b)/2;
            result.append("(t-b)/2 = ").append(newA).append(NEWLINE);
            
            final Pair<Integer, Integer> partial =
                    Algorithms.linearCongruence(a, newA, m);
            result.append(buildLinearCongruence(a, 'x', newA, buildModLineEnd(m)));
            if (partial.equals(new Pair<>()))
                return result.append(NO_SOLUTION).toString();
            if ((partial.equals(new Pair<>(0, 1))) && (!lastCycle))
                continue;
            result.append(buildLinearCongruence(1, 'x', partial.getFirst(),
                    buildModLineEnd(partial.getSecond())));
            if (!lastCycle)
                result.append(SEPARATOR);
        }
        return result.append(solutionSB).toString();
    }
    
    private static StringBuilder quadraticCongruenceStepsNatural(int a, int b, int c, int m) {
        final StringBuilder result = new StringBuilder(256);
        final List<Pair<Integer, Integer>> factors = Algorithms.factorize(m);
        final int factSize = factors.size();
        if ((factSize < 2) || (factSize > 4)) 
            return result;
        
        final List<Integer> subMods = new ArrayList<>();
        final List<StringBuilder> lineEnds = new ArrayList<>();
        for (int i = 0; i < factSize; ++i) {
            final int pi = factors.get(i).getFirst();
            final int ei = factors.get(i).getSecond();
            final int mi = (int)Math.pow(pi, ei);
            subMods.add(mi);
            lineEnds.add(buildModLineEnd(mi));
        }
        result.append(SEPARATOR);
        for (int i = 0; i < lineEnds.size(); ++i) {
            final StringBuilder lineEnd = lineEnds.get(i);
            int ai = a;
            int bi = b;
            int ci = c;
            final int mi = subMods.get(i);
            result.append(buildBinomialCongruence(a, 'x', 2, b, c, lineEnd));
            if (normalizeIntModuloChanges(a, b, c, mi)) {
                ai = Algorithms.normalizeIntModulo(a, mi);
                bi = Algorithms.normalizeIntModulo(b, mi);
                ci = Algorithms.normalizeIntModulo(c, mi);
                result.append(buildBinomialCongruence(ai, 'x', 2, bi, ci, lineEnd));
            }
            final List<Integer> subResult =
                    Algorithms.quadraticCongruenceGeneral(ai, bi, ci, subMods.get(i));
            result.append(quadraticCongruenceSimpleBuildSolution(subResult, 'x', false));
            if (!Utils.lastForCycle(i, lineEnds.size()))
                result.append(SEPARATOR);
        }
        return result;
    }
    
    /**
     * @param n Positive integer
     * @param a Integer
     * @param m Positive integer
     * @return Steps in finding a solution for congruence x^n ≡ a (mod m)
     */
    public static String binomialCongruenceSteps(final int n, int a, final int m) {
        Algorithms.positiveCheck(n);
        Algorithms.positiveCheck(m);
        StringBuilder lineEnd = buildModLineEnd(m);
        final StringBuilder result = buildBinomialCongruence(1, 'x', n, 0, -a, lineEnd);
        if (m == 1)
            return result.append(INFINITE_SOLUTIONS).toString();        
        if (n == 1)
            return linearCongruenceSteps(1, a, m);
        if (n == 2)
            return quadraticCongruenceSimpleSteps('x', a, m);
        
        if (normalizeIntModuloChanges(a, m)) {
            a = Algorithms.normalizeIntModulo(a, m);
            result.append(buildBinomialCongruence(1, 'x', n, 0, -a, lineEnd));
        }
        result.append(SEPARATOR);
        if (!binomialCongruenceLemma(n, a, m, result))
            return result.append(NO_SOLUTION).toString();
        
        final List<Integer> solutions = Algorithms.binomialCongruence(n, a, m);
        if (solutions.isEmpty()) {
            final List<Pair<Integer, Integer>> factors = Algorithms.factorize(m);
            result.append(factorizeSteps(m)).append(NEWLINE);
            for (int i = 0; i < factors.size(); ++i) {
                final StringBuilder subResult = new StringBuilder();
                final int mi = factors.get(i).getFirst();
                if (!binomialCongruenceLemma(n, a, mi, subResult)) {
                    result.append(subResult);
                    break;
                }
            }
            return result.append(NO_SOLUTION).toString();
        }
        
        final int lastIndex = solutions.size() - 1;
        lineEnd = buildModLineEnd(solutions.get(lastIndex));
        for (int i = 0; i < lastIndex; ++i) {
            result.append(buildLinearCongruence(1, 'x', solutions.get(i), lineEnd));
        }
        return result.toString();
    }
    
    private static boolean binomialCongruenceLemma(final int n, int a,
            final int m, final StringBuilder result) {
             
        if ((Algorithms.isCoprime(a, m)) && (Algorithms.primitiveRootsExist(m))) {
            final int phiM = Algorithms.eulerPhi(m);
            final int d = Algorithms.gcd(n, phiM);
            final int test = Algorithms.modPow(a, phiM / d, m);
            
            final StringBuilder lineEnd = buildModLineEnd(m);
            if (normalizeIntModuloChanges(a, m)) {
                a = Algorithms.normalizeIntModulo(a, m);
                result.append(buildBinomialCongruence(1, 'x', n, 0, -a, lineEnd));
            }
            result.append(buildGCDEquals(a, m)).append(1).append(NEWLINE);
            result.append(buildPhiEquals(m)).append(phiM).append(NEWLINE);
            result.append(buildGCDEquals(n, phiM)).append(d).append(NEWLINE);
            result.append(buildModPowResult(test, buildModPowLineStart(a, phiM / d),
                    lineEnd)).append(SEPARATOR);
            if (test != 1)
                return false;
        }
        return true;
    }
    
    /**
     * @param inputPerm Permutation (a subgroup of Sn for n >= 1) up to 99 elements
     * @return Representation of a permutation as a matrix
     */
    public static String permutationToMatrix(final List<Integer> inputPerm) {
        final List<Integer> sorted = new ArrayList<>(inputPerm);
        Collections.sort(sorted);
        
        final StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < sorted.size(); ++i) {
            if ((i < 9) && (inputPerm.get(i) > 9))
                sb.append(" ");
            sb.append(i + 1);
            if (!Utils.lastForCycle(i, sorted.size()))
                sb.append(", ");
        }
        sb.append(")").append(Utils.NEWLINE);
        sb.append(permutationToString(inputPerm));
        return sb.toString();
    }
    
    /**
     * @param inputPerm Permutation (a subgroup of Sn for n >= 1)
     * @return Representation of a permutation as a String of form "(a1, a2, ..., an)"
     */
    private static String permutationToString(final List<Integer> inputPerm) {
        return inputPerm.toString().replaceAll("\\[", "(").replaceAll("\\]", ")");
    }
    
    /**
     * @param inputPerm Permutation (a subgroup of Sn for n >= 1)
     * @return Decomposition into cycles and steps to finding permutation order
     */
    public static String permutationOrderSteps(final List<Integer> inputPerm) {
        final Set<List<Integer>> cycles = Algorithms.permutationCycles(inputPerm);
        StringBuilder result = new StringBuilder(64);
        StringBuilder orders = new StringBuilder("k = ");
        if (cycles.isEmpty()) {
            result.append("id");
            orders.append("1");
        } else {
            if (cycles.size() > 1)
                orders.append("[");
            int order = 1;
            for (List<Integer> cycle : cycles) {
                final int currentCycleOrder = cycle.size();
                order = Algorithms.lcm(order, currentCycleOrder);
                orders.append(currentCycleOrder).append(", ");
                result.append(permutationToString(cycle)).append(CIRC);
            }
            orders = new StringBuilder(orders.substring(0, orders.lastIndexOf(", ")));
            if (cycles.size() > 1)
                orders.append("] = ").append(order);
            result = new StringBuilder(result.substring(0, result.lastIndexOf(CIRC)));
        }
        result.append(NEWLINE).append(orders).append(NEWLINE);
        return result.toString();
    }
}
