package DSLib.theory.category;

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
