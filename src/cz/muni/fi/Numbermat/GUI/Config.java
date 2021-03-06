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

import cz.muni.fi.Numbermat.Problems.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.prefs.Preferences;

/**
 * Constants and helper methods used by other classes from package Numbermat.GUI.
 * Defines order of problems in comboboxes, string and integer constants
 * and help output in HelpFrame.
 * 
 * @author Valdemar Svabensky <395868(at)mail(dot)muni(dot)cz>
 */
public final class Config {
    
    private Config() {
        throw new IllegalStateException(this.getClass().getName() +
                " class should not be instantiated.");
    }
    
    /**
     * Constant representing class GCDProblem.
     */
    public static final int GCD = 0;
    
    /**
     * Constant representing class BezoutProblem.
     */
    public static final int BEZOUT = 1;
    
    /**
     * Constant representing class InverseModProblem.
     */
    public static final int INVERSE_MOD = 2;
    
    /**
     * Constant representing class EulerPhiProblem.
     */
    public static final int EULER_PHI = 3;
    
    /**
     * Constant representing class LinearCongruenceProblem.
     */
    public static final int LINEAR_CONG = 4;
    
    /**
     * Constant representing class LinearCongruenceSystemProblem.
     */
    public static final int LINEAR_CONG_SYSTEM = 5;
    
    /**
     * Constant representing class UnitGroupElementOrderProblem.
     */
    public static final int UNIT_GROUP_ELEMENT_ORDER = 6;
    
    /**
     * Constant representing class ModularPowerProblem.
     */
    public static final int MOD_POW = 7;
    
    /**
     * Constant representing class LegendreSymbolProblem.
     */
    public static final int LEGENDRE = 8;
    
    /**
     * Constant representing class QuadraticCongruenceSimpleProblem.
     */
    public static final int QUADRATIC_CONG_SIMPLE = 9;
    
    /**
     * Constant representing class QuadraticCongruenceGeneralProblem.
     */
    public static final int QUADRATIC_CONG_GENERAL = 10;
    
    /**
     * Constant representing class BinomialCongruenceProblem.
     */
    public static final int BINOMIAL_CONG = 11;
    
    /**
     * Constant representing class PermutationOrderProblem.
     */
    public static final int PERM_ORDER = 12;
    
    /**
     * Collection of all problem classes.
     * Used to simplify instantiation of randomly generated problems.
     * See MainFrame.generateRandomButtonActionPerformed().
     */
    public static final ArrayList<Class<? extends MathProblem>> CLASSES =
            new ArrayList<>(Arrays.asList(
            GCDProblem.class,
            BezoutProblem.class,
            InverseModProblem.class,
            EulerPhiProblem.class,
            LinearCongruenceProblem.class,
            LinearCongruenceSystemProblem.class,
            UnitGroupElementOrderProblem.class,
            ModularPowerProblem.class,
            LegendreSymbolProblem.class,
            QuadraticCongruenceSimpleProblem.class,
            QuadraticCongruenceGeneralProblem.class,
            BinomialCongruenceProblem.class,
            PermutationOrderProblem.class
    ));
    
    /**
     * Names of problems appearing in MainFrame.problemTypeSelection and in help.
     */
    public static final String[] PROBLEMS = new String[] {
        "Největší společný dělitel",
        "Bezoutova rovnost",
        "Inverze modulo n",
        "Eulerova funkce φ",
        "Lineární kongruence",
        "Soustava lineárních kongruencí",
        "Řád prvku grupy jednotek",
        "Modulární umocňování",
        "Legendreův symbol",
        "Kvadratická kongruence (binomická)",
        "Kvadratická kongruence (obecná)",
        "Binomická kongruence",
        "Řád permutace"
    };
    
    /**
     * Description of required parameters for each problem.
     */
    public static final String[][] PARAMS = new String[][] {
        // GCD
        new String[] {"Celé číslo (a)", "Celé číslo (b)"},
        // BEZOUT
        new String[] {"Nezáporné celé číslo (a)", "Nezáporné celé číslo (b)"},
        // INVERSE_MOD
        new String[] {"Kladné celé číslo (a)", "Kladné celé číslo (n)"},
        // EULER_PHI
        new String[] {"Kladné celé číslo (n)"},
        // LINEAR_CONG
        new String[] {"Celé číslo (a)", "Celé číslo (b)", "Kladné celé číslo (n)"},
        // LINEAR_CONG_SYSTEM
        new String[] {"Počet rovnic (max. 4)", "Celá čísla (a_i)",
            "Celá čísla (b_i)", "Kladná celá čísla (n_i)"},
        // UNIT_GROUP_ELEMENT_ORDER
        new String[] {"Kladné celé číslo (prvek)", "Modul (min. 2)"},
        // MOD_POW
        new String[] {"Celé číslo (b)", "Nezáporné celé číslo (e)",
            "Kladné celé číslo (m)"},
        // LEGENDRE
        new String[] {"Celé číslo (a)", "Liché prvočíslo (p)"},
        // QUADRATIC_CONG_SIMPLE
        new String[] {"Celé číslo (a)", "Kladné celé číslo (m)"},
        // QUADRATIC_CONG_GENERAL
        new String[] {"Nenulové celé číslo (a)", "Celé číslo (b)",
            "Celé číslo (c)", "Kladné celé číslo, (a, m) = 1"},
        // BINOMIAL_CONG
        new String[] {"Kladné celé číslo (n)", "Celé číslo (a)",
            "Kladné celé číslo (m)"},
        // PERM_ORDER
        new String[] {"Počet prvků (max. 10)", "Permutace"}
    };
    
    /**
     * Descriptions of problems results appearing in HelpFrame.
     */
    public static final String[] RESULTS = new String[] {
        "(a, b).",
        "Čísla d, x, y taková, že ax + by = d = (a, b).",
        "Číslo x takové, že ax ≡ 1 (mod n).",
        "φ(n).",
        "Dvojice čísel r, t takových, že x ≡ r (mod t) je řešením"
            + " kongruence ax ≡ b (mod n).",
        "Dvojice čísel r, t takových, že x ≡ r (mod t) je řešením"
            + " kongruencí a_i x ≡ b_i (mod n_i).",
        "Řád k prvku e dané grupy, t.j. číslo takové, že e^k = 1.",
        "Číslo x takové, že b^e ≡ x (mod m).",
        "Legendreův symbol (a/p).",
        "Seznam nezáporných čísel r1, r2, ..., rn, t takových, že"
            + " x ≡ r_i (mod t) je řešením kongruence x^2 ≡ a (mod m).",
        "Seznam nezáporných čísel r1, r2, ..., rn, t takových, že"
            + " x ≡ r_i (mod t) je řešením kongruence ax^2 + bx + c ≡ 0 (mod m).",
        "Seznam nezáporných čísel r1, r2, ..., rn, t takových, že"
            + " x ≡ r_i (mod t) je řešením kongruence x^n ≡ a (mod m).",
        "Řád k dané permutace σ, t.j. číslo takové, že σ^k = id.",
    };
    
    private static final String POSITIVE_NUMBER = "Kladné celé číslo.";
    private static final String NONNEGATIVE_NUMBER = "Nezáporné celé číslo.";
    private static final String NUMBER_PAIR_CONG = "Dvojice nezáporných čísel"
            + " r, t oddělených mezerou nebo čárkou.";
    private static final String NUMBER_LIST_CONG = "Seznam nezáporných čísel"
            + " r1, r2, ..., xn, t oddělených mezerou nebo čárkou.";
    
    /**
     * Description of format of inputting result for each problem.
     */
    public static final String[] RESULT_FORMATS = new String[] {
        NONNEGATIVE_NUMBER,
        "Seznam celých čísel d, x, y oddělených mezerou nebo čárkou.",
        POSITIVE_NUMBER,
        POSITIVE_NUMBER,
        NUMBER_PAIR_CONG,
        NUMBER_PAIR_CONG,
        POSITIVE_NUMBER,
        NONNEGATIVE_NUMBER,
        "Jedno z čísel 1, 0, -1",
        NUMBER_LIST_CONG,
        NUMBER_LIST_CONG,
        NUMBER_LIST_CONG,
        "Permutace kladných čísel v rozsahu 1, ..., n oddělených mezerou nebo čárkou."
    };
        
    /**
     * Highest number still accepted as a user input.
     */
    public static final int MAX_INT = 999999;
    
    /**
     * Lowest number still accepted as a user input.
     */
    public static final int MIN_INT = -999999;
    
    /**
     * Name of easy difficulty bound.
     */
    public static final String EASY = "Lehká";
    
    /**
     * Name of medium difficulty bound.
     */
    public static final String MEDIUM = "Střední";
    
    /**
     * Name of hard difficulty bound.
     */
    public static final String HARD = "Těžká";
    
    /**
     * Alias for default pdfLaTeX executable file location.
     */
    public static final String DEFAULT = "Default";
    
    /**
     * Default value for pdfLaTeX executable file.
     * Null means automatic searching for default file location.
     */
    private static File pdfLaTeXFile = null;
    
    /**
     * Default value for "export to PDF" folder.
     * Null means letting user choose in each export.
     */
    private static File pdfExportFolder = null;
    
    /**
     * Initializes application preferences when MainFrame window is created.
     * @param preferences Object with application preferences
     */
    public static void initPreferences(Preferences preferences) {
        if (pdfLaTeXFile == null)
            preferences.put("pathToPDFLaTeXFile", DEFAULT);
        else    
            preferences.put("pathToPDFLaTeXFile", pdfLaTeXFile.getAbsolutePath());
        
        if (pdfExportFolder == null)
            preferences.put("pathToPDFExportFolder", DEFAULT);
        else    
            preferences.put("pathToPDFExportFolder", pdfExportFolder.getAbsolutePath());
    }
    
    public static File getPDFLaTeXFile() {
        return pdfLaTeXFile;
    }

    public static void setPDFLaTeXFile(File pdfLaTeXFile) {
        Config.pdfLaTeXFile = pdfLaTeXFile;
    }
    
    public static File getPDFExportFolder() {
        return pdfExportFolder;
    }

    public static void setPDFExportFolder(File pdfExportFolder) {
        Config.pdfExportFolder = pdfExportFolder;
    }
}
