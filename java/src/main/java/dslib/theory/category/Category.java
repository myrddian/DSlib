package dslib.theory.category;

import java.util.List;

public interface Category {
    String getName();
    List<Morphism> getMorphisms();
    List<KObject> getObjects();
}
