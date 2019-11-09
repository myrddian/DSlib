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

package dslib.markov.object;

import dslib.markov.variable.MkVariable;
import dslib.markov.variable.MkVarNode;

import java.util.HashMap;
import java.util.Map;

public class MkObject {

    private HashMap<String, MkVarNode> variableValues = new HashMap<>();
    private HashMap<String, String> variableStringValues = new HashMap<>();
    private HashMap<String, MkVariable> variablesMapped = new HashMap<>();

    public MkVarNode getVariableValue(MkVariable variable) {
        if(variableValues.containsKey(variable.getVariableName())) {
            return variableValues.get(variable.getVariableName());
        }
        return null;
    }

    public String getVariableValue(String variable) {
        if(variablesMapped.containsKey(variable)) {
            return variableStringValues.get(variable);
        }
        return null;
    }

    public void addVariableValue(MkVariable variable, MkVarNode value) {
        if(variableValues.containsKey(variable.getVariableName())) {
            return;
        }
        variableValues.put(variable.getVariableName(), value);
        variableStringValues.put(variable.getVariableName(), value.getValue());
        variablesMapped.put(variable.getVariableName(), variable);
    }

    public Map<String,String> getVariableMap() {
       return variableStringValues;
    }

}
