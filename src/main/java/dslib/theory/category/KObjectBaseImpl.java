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

        You should have received a copy of the Affero GNU General Public License
        along with this program.  If not, see <https://www.gnu.org/licenses/>
*/

package dslib.theory.category;

import java.util.Collection;

public class KObjectBaseImpl implements KObject {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public Collection<Morphism> getMorphisms() {
        return null;
    }

    @Override
    public Morphism getMorphism(String name) {
        return null;
    }

    @Override
    public boolean addMorphism(KObject target) {
        return false;
    }

    @Override
    public boolean addMorphism(Morphism newMorphism) {
        return false;
    }

    @Override
    public Category getCategory() {
        return null;
    }
}
