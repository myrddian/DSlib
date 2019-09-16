package DSLib.dataframe;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class LWDataColumn<T>  implements List<T>{

    private String colname;
    private boolean original = true;
    private List<T> values = new ArrayList<>();

    public LWDataColumn(String name, List<T> items) {
        colname = name;
        for(T item:items){
            values.add(item);
        }
    }

    public T get(int index) {
        return values.get(index);
    }

    @Override
    public T set(int index, T element) {
        return null;
    }

    @Override
    public void add(int index, T element) {
        original = false;
        values.add(index,element);
    }

    @Override
    public T remove(int index) {
        original = false;
        return values.remove(index);
    }

    @Override
    public boolean add(T t) {
        original = false;
        return values.add(t);
    }

    public List<T> get(int start, int end) {
        List<T> retVal = new ArrayList<>();
        for(int counter = start; counter < end; ++counter) {
            retVal.add(values.get(counter));
        }
        return retVal;
    }

    public String name() {
        return colname;
    }

    public boolean modified() {
        return !original;
    }

    @Override
    public void clear() {
        values.clear();
        original = false;
    }

    @NotNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return get(fromIndex,toIndex);
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return false;
    }


    @Override
    public int size() {
        return values.size();
    }


    @Override
    public int indexOf(Object o) {
        return values.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return values.lastIndexOf(o);
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator() {
        return values.listIterator();
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator(int index) {
        return values.listIterator(index);
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return values.contains(o);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return values.iterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return values.toArray();
    }

    @NotNull
    @Override
    public <T1> T1[] toArray(@NotNull T1[] a) {
        return values.toArray(a);
    }



}
