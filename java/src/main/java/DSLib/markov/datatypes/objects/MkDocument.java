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
package DSLib.markov.datatypes.objects;

import DSLib.markov.algo.TrialTree;
import DSLib.markov.algo.objects.MkObjectCondition;
import DSLib.markov.algo.objects.MkObjectTrialTree;
import DSLib.markov.datatypes.variables.MkVariable;

import java.util.List;
import java.util.Vector;

public class MkDocument {

    private List<MkVariable> variables = new Vector<>();

    public void addVariableToSchema(MkVariable newVar) {
        variables.add(newVar);
    }

    public int schemaSize() { return variables.size(); }

    public List<MkObject> generateSamples(int samples) {
        Vector<MkObject> retList = new Vector<>();

        for(int counter=0; counter < samples; ++counter) {
            retList.add(generateSample());
        }
        return retList;
    }

    private MkObject generateSample() {
        MkObject object = new MkObject();
        for(MkVariable variable: variables) {
            object.addVariableValue(variable,variable.generateSample());
        }
        return object;
    }

    public TrialTree<MkObjectCondition> createTrialTree(int sampleSize) {
        List<MkObject> trials = generateSamples(sampleSize);
        MkObjectTrialTree retVal = new MkObjectTrialTree(trials,this);
        return retVal;
    }

    public MkObjectCondition createCondition(String variableName, String variableValue) {
        MkObjectCondition newCondition = new MkObjectCondition();
        newCondition.setCondition(variableName,variableValue);
        return newCondition;
    }

    public void parseAddVariable(String variableName, List<String> values) {
        MkVariable newVar = new MkVariable();
        newVar.setVariableName(variableName);
        newVar.parseTransitionList(values);
        addVariableToSchema(newVar);
    }

}
