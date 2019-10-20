package DSLib.theory.category;

public interface Morphism {
    KObject apply();
    KObject getDomain();
    KObject getCodomain();
    String  getName();
    Morphism compose(Morphism target);
}
