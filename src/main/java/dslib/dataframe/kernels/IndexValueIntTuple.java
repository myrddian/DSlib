package dslib.dataframe.kernels;

public class IndexValueIntTuple implements Comparable<IndexValueIntTuple> {
    private int index;
    private int value;

    public IndexValueIntTuple(int index, int value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() { return index; }
    public int getValue() { return value; }

    @Override
    public int compareTo(IndexValueIntTuple o) {
        return this.value - o.value;
    }
}
