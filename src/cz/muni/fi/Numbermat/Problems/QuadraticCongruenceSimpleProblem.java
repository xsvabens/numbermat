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

package cz.muni.fi.Numbermat.Problems;

import cz.muni.fi.Numbermat.Algorithms;
import cz.muni.fi.Numbermat.AlgorithmsSteps;
import cz.muni.fi.Numbermat.GUI.Config;
import cz.muni.fi.Numbermat.Pair;
import cz.muni.fi.Numbermat.Utils;

/**
 * Quadratic congruence of form x^2 ≡ a (mod m).
 * Useful to find quadratic residues.
 * 
 * @author Valdemar Svabensky <395868(at)mail(dot)muni(dot)cz>
 */
public final class QuadraticCongruenceSimpleProblem extends MathProblem {

    private int a;
    private int m;
    
    /**
     * Instantiate with pseudo-randomly generated parameters.
     * @param difficulty One of Config.EASY, Config.MEDIUM, Config.HARD
     */
    public QuadraticCongruenceSimpleProblem(final String difficulty) {
        easyBounds = new Pair(2, 39);
        mediumBounds = new Pair(11, 99);
        hardBounds = new Pair(11, 199);
        
        final Pair<Integer, Integer> bounds = initBounds(difficulty);
        final int lowerBound = bounds.getFirst();
        final int upperBound = bounds.getSecond();
        
        int aa, mm;
        while (true) {
            aa = Algorithms.randInt(lowerBound, upperBound);
            if (difficulty.equals(Config.EASY))
                mm = Algorithms.randPrime(lowerBound, upperBound);
            else
                mm = Algorithms.generateModulus(false); // 6 to 95
            if (!Algorithms.quadraticCongruenceSimple(aa, mm).isEmpty())
                break;
        }
        setVariables(aa, mm);
    }
    
    /**
     * Instantiate with user provided parameters.
     * @param aa Integer
     * @param mm Positive integer
     */
    public QuadraticCongruenceSimpleProblem(final int aa, final int mm) {
        setVariables(aa, mm);
    }
    
    private void setVariables(final int aa, final int mm) {
        a = aa;
        m = mm;
        result = Algorithms.quadraticCongruenceSimple(a, m);
        prepareAll();
    }
    
    @Override
    protected void prepareProblemPlaintext() {
        final StringBuilder lineEnd = AlgorithmsSteps.buildModLineEnd(m);
        problemPlaintext = AlgorithmsSteps.buildBinomialCongruence(
                1, 'x', 2, 0, -a, lineEnd).toString();
    }

    @Override
    protected void prepareProblemLaTeX() {
        final String tmp = Utils.prepareCongruencesMath(problemPlaintext);
        problemLaTeX = Utils.prepareDisplayMath(tmp).toString();
    }

    @Override
    protected void prepareSolutionPlaintext() {
        solutionPlaintext = AlgorithmsSteps.quadraticCongruenceSimpleSteps('x', a, m);
    }

    @Override
    protected void prepareSolutionLaTeX() {
        solutionLaTeX = Utils.prepareBasicMath(solutionPlaintext);
        solutionLaTeX = Utils.prepareCongruencesMath(solutionLaTeX);
        solutionLaTeX = Utils.prepareFractionsMath(solutionLaTeX);
        solutionLaTeX = Utils.prepareAlignMath(solutionLaTeX);
        solutionLaTeX = Utils.prepareAlignatMath(solutionLaTeX);
    }
}
