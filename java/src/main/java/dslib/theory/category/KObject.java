package dslib.theory.category;

import java.util.Collection;

public interface KObject {
    String getName();
    Collection<Morphism> getMorphisms();
    Morphism getMorphism(String name);
    boolean addMorphism(KObject target);
    boolean addMorphism(Morphism newMorphism);
    Category getCategory();
}
