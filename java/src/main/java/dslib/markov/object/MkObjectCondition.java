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

public class MkObjectCondition {

    private String variableName;
    private String variableValue;

    public String getVariableName() { return variableName; }
    public String getVariableValue() { return variableValue; }

    public boolean conditionSatisfied(MkObject testedObject) {
        if( testedObject.getVariableValue(variableName).equals(variableValue)) {
            return true;
        }
        return false;
    }

    public void setCondition(String varName, String varValue) {
        variableName = varName;
        variableValue = varValue;
    }

}
