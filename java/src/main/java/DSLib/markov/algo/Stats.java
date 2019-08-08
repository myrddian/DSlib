/*      DSLib - Collection of Data Science Libraries
        Copyright (C) 2019  Enzo Reyes

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <https://www.gnu.org/licenses/>
*/
package DSLib.markov.algo;

public interface Stats<GRAPH, NODE> {
    double getProbGiven(GRAPH inGraph, NODE fromState, NODE toState);
    double getProbGiven(GRAPH inGraph, NODE fromState, NODE toState, int steps);
    double getProb(GRAPH inGraph, NODE toState, int steps);
    ProbResults<NODE> getProbsGiven(GRAPH inGraph, NODE fromState, int steps);
    TrialTree<NODE> createTrialTree(GRAPH inGraph, int size);
}
