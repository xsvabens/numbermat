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
import cz.muni.fi.Numbermat.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Linear congruence of type ax ≡ b (mod n).
 * Based on LinearCongruenceSystemProblem.
 * 
 * @author Valdemar Svabensky <395868(at)mail(dot)muni(dot)cz>
 */
public final class LinearCongruenceProblem extends MathProblem {
    
    private LinearCongruenceSystemProblem lcsProblem;
    
    /**
     * Instantiate with pseudo-randomly generated parameters.
     * @param difficulty One of Config.EASY, Config.MEDIUM, Config.HARD
     */
    public LinearCongruenceProblem(final String difficulty) {
        easyBounds = new Pair(11, 50);
        mediumBounds = new Pair(50, 300);
        hardBounds = new Pair(300, 700);
        
        final Pair<Integer, Integer> bounds = initBounds(difficulty);
        final int lowerBound = bounds.getFirst();
        final int upperBound = bounds.getSecond();
        
        int aa, bb, nn;
        while (true) {
            aa = Algorithms.randInt(lowerBound, upperBound);
            bb = Algorithms.randInt(lowerBound, upperBound);
            nn = Algorithms.randInt(lowerBound, upperBound);
            final Pair<Integer, Integer> solution =
                    Algorithms.linearCongruence(aa, bb, nn);
            if (!solution.isEmpty())
                break;
        }
        setVariables(aa, bb, nn);
    }
    
    /**
     * Instantiate with user provided parameters.
     * @param aa Integer
     * @param bb Integer
     * @param nn Positive integer
     */
    public LinearCongruenceProblem(final int aa, final int bb, final int nn) {
        setVariables(aa, bb, nn);
    }
    
    private void setVariables(final int aa, final int bb, final int nn) {
        final List<Integer> aList = new ArrayList<>(Arrays.asList(aa));
        final List<Integer> bList = new ArrayList<>(Arrays.asList(bb));
        final List<Integer> nList = new ArrayList<>(Arrays.asList(nn));
        lcsProblem = new LinearCongruenceSystemProblem(1, aList, bList, nList);
        result = lcsProblem.getResult();
        prepareAll();
    }

    @Override
    protected void prepareProblemPlaintext() {
        problemPlaintext = lcsProblem.getProblemPlaintext();
    }

    @Override
    protected void prepareProblemLaTeX() {
        problemLaTeX = lcsProblem.getProblemLaTeX();
    }

    @Override
    protected void prepareSolutionPlaintext() {
        solutionPlaintext = lcsProblem.getSolutionPlaintext();
    }

    @Override
    protected void prepareSolutionLaTeX() {
        solutionLaTeX = lcsProblem.getSolutionLaTeX();
    }
}
